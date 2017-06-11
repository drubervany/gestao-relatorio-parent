package br.com.syshealth.gestao.relatorio.core.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.syshealth.commons.dto.Aporte;
import br.com.syshealth.commons.dto.Cadastro;
import br.com.syshealth.commons.dto.Copay;
import br.com.syshealth.commons.dto.Plano;
import br.com.syshealth.commons.dto.Premio;
import br.com.syshealth.commons.dto.Segurado;
import br.com.syshealth.commons.dto.SeguradoPremio;
import br.com.syshealth.commons.dto.SeguradoSinistro;
import br.com.syshealth.commons.dto.Sinistro;
import br.com.syshealth.commons.dto.SubEmpresaCadastro;
import br.com.syshealth.commons.dto.SubEmpresaPremio;
import br.com.syshealth.commons.dto.SubEmpresaSinistro;
import br.com.syshealth.commons.enums.AcomodacaoEnum;
import br.com.syshealth.commons.enums.FaixaEtariaEnum;
import br.com.syshealth.commons.enums.SexoEnum;
import br.com.syshealth.commons.enums.TipoBeneficiarioEnum;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;
import br.com.syshealth.gestao.relatorio.commons.exception.CoreValidationException;
import br.com.syshealth.gestao.relatorio.commons.relatorio.EvolucaoVidas;
import br.com.syshealth.gestao.relatorio.commons.relatorio.PerfilFinanceiro;
import br.com.syshealth.gestao.relatorio.commons.relatorio.SinistroFaixaEtaria;
import br.com.syshealth.gestao.relatorio.commons.relatorio.VidasGenero;
import br.com.syshealth.gestao.relatorio.commons.relatorio.VidasPlano;
import br.com.syshealth.gestao.relatorio.commons.relatorio.VidasPlanoAcomodacao;
import br.com.syshealth.gestao.relatorio.commons.relatorio.VidasTitulariedade;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInboundCadastroDbService;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInboundFaturaDbService;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInboundSinistroDbService;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInputService;
import br.com.syshealth.gestao.relatorio.core.port.outbound.GestaoRelatorioOutboundDbService;

@Service
public class GestaoRelatorioInputServiceHandler implements GestaoRelatorioInputService {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(GestaoRelatorioInputServiceHandler.class);

    @Autowired
    GestaoRelatorioInboundCadastroDbService cadastroService;

    @Autowired
    GestaoRelatorioInboundFaturaDbService premioService;

    @Autowired
    GestaoRelatorioInboundSinistroDbService sinistroService;

    @Autowired
    GestaoRelatorioOutboundDbService relatorioService;

    @Override
    public void processaMensagem(Relatorio relatorio) throws CoreValidationException {

        Cadastro cadastro = cadastroService.find(relatorio);
        Premio premio = premioService.find(relatorio);
        Sinistro sinistro = sinistroService.find(relatorio);

        Supplier<Stream<SubEmpresaCadastro>> streamSubEmpresaCadastro = () -> cadastro.getEmpresa().getSubEmpresas().stream();
        Supplier<Stream<SubEmpresaPremio>> streamSubEmpresaPremio = () -> premio.getEmpresa().getSubEmpresas().stream();
        Supplier<Stream<SubEmpresaSinistro>> streamSubEmpresaSinistro = () -> sinistro.getEmpresa().getSubEmpresas().stream();

        Supplier<Stream<Set<Segurado>>> mapCadastro = () -> mapSegurado(streamSubEmpresaCadastro.get());
        Supplier<Stream<Set<SeguradoPremio>>> mapPremio = () -> mapPremio(streamSubEmpresaPremio.get());
        Supplier<Stream<Set<SeguradoSinistro>>> mapSinistro = () -> mapSinistro(streamSubEmpresaSinistro.get());

        Supplier<Stream<Segurado>> flatMapCadastro = () -> flatMapSegurado(mapCadastro.get());
        Supplier<Stream<SeguradoPremio>> flatMapPremio = () -> flatMapPremio(mapPremio.get());
        Supplier<Stream<SeguradoSinistro>> flatMapSinistro = () -> flatMapSinistro(mapSinistro.get());

        EvolucaoVidas criarEvolucaoVidas = criarEvolucaoVidas(relatorio.getCompetenciaInicial(), flatMapCadastro.get());

        PerfilFinanceiro criarPerfilFinanceiro = criarPerfilFinanceiro(relatorio.getCompetenciaInicial(),
                flatMapCadastro.get(),
                flatMapPremio.get(),
                flatMapSinistro.get(),
                valorCopay(streamSubEmpresaPremio.get()),
                valorAporte(streamSubEmpresaPremio.get()));

        List<VidasPlano> listaVidasPlano = criarVidasPlano(flatMapCadastro.get(), flatMapSinistro.get());
        List<VidasGenero> listaGenero = criarVidasGenero(mapCadastro.get());
        VidasPlanoAcomodacao vidasPlanoAcomodacao = criarVidasPlanoAcomodacao(relatorio.getCompetenciaInicial(),
                flatMapCadastro.get());

        List<VidasTitulariedade> vidasTitulariedade = criarVidasTitulariedade(mapCadastro.get());

        List<SinistroFaixaEtaria> sinistroFaixaEtarias = criarSinistroFaixaEtaria(mapCadastro.get(), mapSinistro.get());

    }

    private List<SinistroFaixaEtaria> criarSinistroFaixaEtaria(Stream<Set<Segurado>> mapCadastro,
            Stream<Set<SeguradoSinistro>> mapSinistro) {

        List<SinistroFaixaEtaria> listaSinistroFaixaEtaria = new ArrayList<>();
        for (FaixaEtariaEnum faixaEtaria : FaixaEtariaEnum.values()) {

            Stream<SeguradoSinistro> mapSinistroFaixa = mapSinistroFaixaEtaria(mapSinistro, faixaEtaria);

            Stream<Segurado> mapCadastroFaixa = mapCadastroFaixaEtaria(mapCadastro, faixaEtaria);

            Integer vidas = vidasSegurado(mapCadastroFaixa);
            BigDecimal sinistro = valorSinistro(mapSinistroFaixa);

            listaSinistroFaixaEtaria.add(new SinistroFaixaEtaria(faixaEtaria.getFaixaEtaria(), vidas, sinistro));
        }

        return listaSinistroFaixaEtaria;

    }

    private List<VidasTitulariedade> criarVidasTitulariedade(Stream<Set<Segurado>> mapCadastro) {

        List<VidasTitulariedade> listaVidasTitulariedade = new ArrayList<>();
        for (FaixaEtariaEnum faixaEtaria : FaixaEtariaEnum.values()) {

            Stream<Segurado> flatMapSegurado = mapCadastroFaixaEtaria(mapCadastro, faixaEtaria);

            Map<TipoBeneficiarioEnum, Long> resultVidasGenero = flatMapSegurado.collect(
                    Collectors.groupingBy(
                            Segurado::getTipoBeneficiario, Collectors.counting()));

            VidasTitulariedade vidasGenero = new VidasTitulariedade(faixaEtaria.getFaixaEtaria(),
                    resultVidasGenero.get(TipoBeneficiarioEnum.TITULAR) == null ? 0
                            : resultVidasGenero.get(TipoBeneficiarioEnum.TITULAR).intValue(),
                    resultVidasGenero.get(TipoBeneficiarioEnum.DEPENDENTE) == null ? 0
                            : resultVidasGenero.get(TipoBeneficiarioEnum.DEPENDENTE).intValue(),
                    resultVidasGenero.get(TipoBeneficiarioEnum.AFASTADO) == null ? 0
                            : resultVidasGenero.get(TipoBeneficiarioEnum.AFASTADO).intValue(),
                    resultVidasGenero.get(TipoBeneficiarioEnum.DEMITIDO) == null ? 0
                            : resultVidasGenero.get(TipoBeneficiarioEnum.DEMITIDO).intValue(),
                    resultVidasGenero.get(TipoBeneficiarioEnum.APOSENTADO) == null ? 0
                            : resultVidasGenero.get(TipoBeneficiarioEnum.APOSENTADO).intValue(),
                    resultVidasGenero.get(TipoBeneficiarioEnum.AGREGADO) == null ? 0
                            : resultVidasGenero.get(TipoBeneficiarioEnum.AGREGADO).intValue());

            listaVidasTitulariedade.add(vidasGenero);
        }

        return listaVidasTitulariedade;
    }

    private VidasPlanoAcomodacao criarVidasPlanoAcomodacao(Integer competencia, Stream<Segurado> flatMapSegurado) {

        Stream<Plano> map = flatMapSegurado.map(p -> p.getPlano());

        Map<AcomodacaoEnum, Long> resultVidasAcomodacao = map.collect(
                Collectors.groupingBy(
                        Plano::getAcomodacao, Collectors.counting()));

        return new VidasPlanoAcomodacao(competencia,
                resultVidasAcomodacao.get(AcomodacaoEnum.BASICO) == null ? null
                        : resultVidasAcomodacao.get(AcomodacaoEnum.BASICO).intValue(),
                resultVidasAcomodacao.get(AcomodacaoEnum.INTERMEDIARIO) == null ? null
                        : resultVidasAcomodacao.get(AcomodacaoEnum.INTERMEDIARIO).intValue(),
                resultVidasAcomodacao.get(AcomodacaoEnum.EXECUTIVO) == null ? null
                        : resultVidasAcomodacao.get(AcomodacaoEnum.EXECUTIVO).intValue());
    }

    private EvolucaoVidas criarEvolucaoVidas(Integer competencia, Stream<Segurado> flatMapSegurado) {
        return new EvolucaoVidas(competencia, vidasSegurado(flatMapSegurado));
    }

    private List<VidasGenero> criarVidasGenero(Stream<Set<Segurado>> mapCadastro) {

        List<VidasGenero> listaGenero = new ArrayList<>();
        for (FaixaEtariaEnum faixaEtaria : FaixaEtariaEnum.values()) {

            Stream<SexoEnum> mapSegurado = mapCadastroFaixaEtaria(mapCadastro, faixaEtaria).map(p -> p.getSexo());

            Map<Integer, Long> resultVidasGenero = mapSegurado.collect(
                    Collectors.groupingBy(
                            SexoEnum::getCodigo, Collectors.counting()));

            VidasGenero vidasGenero = new VidasGenero();
            vidasGenero.setFaixaEtaria(faixaEtaria.getFaixaEtaria());
            vidasGenero.setFeminino(
                    resultVidasGenero.get(SexoEnum.FEMININO) == null ? 0 : resultVidasGenero.get(SexoEnum.FEMININO).intValue());
            vidasGenero.setMasculino(
                    resultVidasGenero.get(SexoEnum.MASCULINO) == null ? 0 : resultVidasGenero.get(SexoEnum.MASCULINO).intValue());

            listaGenero.add(vidasGenero);
        }
        return listaGenero;
    }

    private List<VidasPlano> criarVidasPlano(Stream<Segurado> flatMapCadastro,
            Stream<SeguradoSinistro> flatMapSinistro) {

        Stream<Plano> mapSegurado = flatMapCadastro.map(p -> p.getPlano());
        Stream<Plano> mapSinistro = flatMapSinistro.map(p -> p.getPlano());

        Map<String, Long> resultSinistroPlano = mapSegurado.collect(
                Collectors.groupingBy(
                        Plano::getCodigo, Collectors.counting()));

        Map<String, Long> resultVidaPlano = mapSinistro.collect(
                Collectors.groupingBy(
                        Plano::getCodigo, Collectors.counting()));

        List<VidasPlano> listaPlano = new ArrayList<>();
        for (String plano : resultVidaPlano.keySet()) {
            listaPlano.add(new VidasPlano(plano, resultVidaPlano.get(plano).intValue(), resultSinistroPlano.get(plano).intValue()));
        }

        return listaPlano;

    }

    private PerfilFinanceiro criarPerfilFinanceiro(Integer competencia,
            Stream<Segurado> flatMapCadastro,
            Stream<SeguradoPremio> flatMapPremio,
            Stream<SeguradoSinistro> flatMapSinistro,
            BigDecimal copay,
            BigDecimal aporte) {

        Integer vidas = vidasSegurado(flatMapCadastro);

        BigDecimal premio = valorPremio(flatMapPremio);

        BigDecimal sinistro = valorSinistro(flatMapSinistro);

        return new PerfilFinanceiro(competencia, vidas.intValue(), premio, aporte, sinistro, copay);
    }

    private Integer vidasSegurado(Stream<Segurado> mapSegurado) {

        return mapSegurado.collect(Collectors.counting()).intValue();
    }

    private BigDecimal valorAporte(Stream<SubEmpresaPremio> streamSubEmpresaPremio) {

        Stream<Aporte> streamAporte = streamSubEmpresaPremio.map(s -> s.getAporte());

        if (!Objects.nonNull(streamAporte))
            return streamAporte.map(Aporte::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BigDecimal.ZERO;

    }

    private BigDecimal valorCopay(Stream<SubEmpresaPremio> streamSubEmpresaPremio) {

        Stream<Copay> streamCopay = streamSubEmpresaPremio.map(s -> s.getCopay());

        if (!Objects.nonNull(streamCopay))
            return streamCopay.map(Copay::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BigDecimal.ZERO;

    }

    private BigDecimal valorPremio(Stream<SeguradoPremio> flatMapPremio) {

        return flatMapPremio.map(SeguradoPremio::getValorPremio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private BigDecimal valorSinistro(Stream<SeguradoSinistro> mapSinistro) {

        return mapSinistro.map(SeguradoSinistro::getValorSinistro)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Stream<Segurado> mapCadastroFaixaEtaria(Stream<Set<Segurado>> mapSegurado, FaixaEtariaEnum faixaEtaria) {

        Stream<Set<Segurado>> map = mapSegurado
                .map(s -> s.stream().filter(ss -> ss.getIdade() >= faixaEtaria.getIdadeInicio() &&
                        ss.getIdade() >= faixaEtaria.getIdadeFinal())
                        .collect(Collectors.toSet()));

        return map.flatMap(s -> s.stream());
    }

    private Stream<SeguradoPremio> mapPremioFaixaEtaria(Stream<Set<SeguradoPremio>> mapPremio, FaixaEtariaEnum faixaEtaria) {

        Stream<Set<SeguradoPremio>> map = mapPremio
                .map(s -> s.stream().filter(ss -> ss.getIdade() >= faixaEtaria.getIdadeInicio() &&
                        ss.getIdade() >= faixaEtaria.getIdadeFinal())
                        .collect(Collectors.toSet()));

        return map.flatMap(s -> s.stream());
    }

    private Stream<SeguradoSinistro> mapSinistroFaixaEtaria(Stream<Set<SeguradoSinistro>> mapSinistro,
            FaixaEtariaEnum faixaEtaria) {

        Stream<Set<SeguradoSinistro>> map = mapSinistro
                .map(s -> s.stream().filter(ss -> ss.getIdade() >= faixaEtaria.getIdadeInicio() &&
                        ss.getIdade() >= faixaEtaria.getIdadeFinal())
                        .collect(Collectors.toSet()));

        return map.flatMap(s -> s.stream());
    }

    private Stream<Set<Segurado>> mapSegurado(Stream<SubEmpresaCadastro> streamSubEmpresaCadastro) {
        return streamSubEmpresaCadastro.map(se -> se.getSegurados().stream()
                .collect(Collectors.toSet()));
    }

    private Stream<Set<SeguradoPremio>> mapPremio(Stream<SubEmpresaPremio> streamSubEmpresaPremio) {
        return streamSubEmpresaPremio.map(se -> se.getSegurados().stream()
                .collect(Collectors.toSet()));
    }

    private Stream<Set<SeguradoSinistro>> mapSinistro(Stream<SubEmpresaSinistro> streamSubEmpresaSinistro) {
        return streamSubEmpresaSinistro.map(se -> se.getSegurados().stream()
                .collect(Collectors.toSet()));
    }

    private Stream<Segurado> flatMapSegurado(Stream<Set<Segurado>> mapCadastro) {
        return mapCadastro.flatMap(s -> s.stream());
    }

    private Stream<SeguradoPremio> flatMapPremio(Stream<Set<SeguradoPremio>> mapPremio) {
        return mapPremio.flatMap(s -> s.stream());
    }

    private Stream<SeguradoSinistro> flatMapSinistro(Stream<Set<SeguradoSinistro>> mapSinistro) {
        return mapSinistro.flatMap(s -> s.stream());
    }

}
