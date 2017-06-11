
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.syshealth.commons.dto.Cadastro;
import br.com.syshealth.commons.dto.EmpresaCadastro;
import br.com.syshealth.commons.dto.EmpresaSinistro;
import br.com.syshealth.commons.dto.Plano;
import br.com.syshealth.commons.dto.Segurado;
import br.com.syshealth.commons.dto.Sinistro;
import br.com.syshealth.commons.dto.SubEmpresaCadastro;
import br.com.syshealth.commons.dto.SubEmpresaSinistro;
import br.com.syshealth.commons.enums.EstadoCivilEnum;
import br.com.syshealth.commons.enums.FaixaEtariaEnum;
import br.com.syshealth.commons.enums.OperadoraEnum;
import br.com.syshealth.commons.enums.SexoEnum;
import br.com.syshealth.commons.enums.TipoBeneficiarioEnum;
import br.com.syshealth.gestao.relatorio.commons.relatorio.VidasGenero;
import br.com.syshealth.gestao.relatorio.commons.relatorio.VidasPlano;

public class CadastroTest {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        Set<Segurado> segurados = criaSegurados();
        Set<SubEmpresaCadastro> subEmpresas = criaSubEmpresas(segurados);

        Cadastro cadastro = new Cadastro(201402,
                new EmpresaCadastro(12345L, OperadoraEnum.BRADESCO,
                        subEmpresas));

        Sinistro sinistro = new Sinistro(201402, new EmpresaSinistro(null, null, null, null, null));

        Supplier<Stream<SubEmpresaCadastro>> streamSubEmpresaCadastro = () -> cadastro.getEmpresa().getSubEmpresas().stream();
        Supplier<Stream<SubEmpresaSinistro>> streamSubEmpresaSinistro = () -> sinistro.getEmpresa().getSubEmpresas().stream();

        List<VidasPlano> listaVidasPlano = criarVidasPlano(streamSubEmpresaCadastro.get(), streamSubEmpresaSinistro.get());
        List<VidasGenero> listaGenero = criarVidasGenero(streamSubEmpresaCadastro.get());
    }

    private static Set<SubEmpresaCadastro> criaSubEmpresas(Set<Segurado> segurados) {
        Set<SubEmpresaCadastro> subEmpresas = new HashSet<>();
        SubEmpresaCadastro subEmpresa1 = new SubEmpresaCadastro(1, segurados);
        SubEmpresaCadastro subEmpresa2 = new SubEmpresaCadastro(2, segurados);
        subEmpresas.add(subEmpresa1);
        subEmpresas.add(subEmpresa2);
        return subEmpresas;
    }

    private static Set<Segurado> criaSegurados() {
        Set<Segurado> segurados = new HashSet<>();
        Segurado segurado1 = new Segurado(1L, "1", "Danilo", "22222", new Date(), new Date(), null,
                TipoBeneficiarioEnum.TITULAR, SexoEnum.MASCULINO, null, EstadoCivilEnum.CASADO,
                29, new Plano("TNQ2", "TNE1"));

        Segurado segurado2 = new Segurado(2L, "2", "Eliane", "22222", new Date(), new Date(), null,
                TipoBeneficiarioEnum.DEMITIDO, SexoEnum.FEMININO, null, EstadoCivilEnum.CASADO,
                18, new Plano("TNE1", "TNE1"));
        segurados.add(segurado1);
        segurados.add(segurado2);
        return segurados;
    }

    private static List<VidasGenero> criarVidasGenero(Stream<SubEmpresaCadastro> streamSubEmpresa) {
        List<VidasGenero> listaGenero = new ArrayList<>();
        for (FaixaEtariaEnum faixaEtaria : FaixaEtariaEnum.values()) {

            Stream<Set<Segurado>> mapSegurado = streamSubEmpresa.map(se -> se.getSegurados().stream()
                    .filter(s -> s.getIdade() >= faixaEtaria.getIdadeInicio() &&
                            s.getIdade() <= faixaEtaria.getIdadeFinal())
                    .collect(Collectors.toSet()));

            Stream<Segurado> flatMapSegurado = mapSegurado.flatMap(s -> s.stream());

            Stream<SexoEnum> map = flatMapSegurado.map(p -> p.getSexo());

            Map<Integer, Long> resultVidasGenero = map.collect(
                    Collectors.groupingBy(
                            SexoEnum::getCodigo, Collectors.counting()));

            System.out.println(faixaEtaria.name());
            System.out.println(resultVidasGenero);

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

    private static List<VidasPlano> criarVidasPlano(Stream<SubEmpresaCadastro> streamSubEmpresa,
            Stream<SubEmpresaSinistro> streamSubEmpresaSinistro) {

        Stream<Set<Segurado>> mapSegurado = streamSubEmpresa.map(se -> se.getSegurados().stream().collect(Collectors.toSet()));
        Stream<Set<Segurado>> mapSinistro = streamSubEmpresa.map(se -> se.getSegurados().stream().collect(Collectors.toSet()));

        Stream<Segurado> flatMapSegurado = mapSegurado.flatMap(s -> s.stream());
        Stream<Segurado> flatMapSinistro = mapSinistro.flatMap(s -> s.stream());

        Stream<Plano> mapPlanoSegurado = flatMapSegurado.map(p -> p.getPlano());
        Stream<Plano> mapPlanoSinistro = flatMapSinistro.map(p -> p.getPlano());

        Map<String, Long> resultSinistroPlano = mapPlanoSegurado.collect(
                Collectors.groupingBy(
                        Plano::getCodigo, Collectors.counting()));

        Map<String, Long> resultVidaPlano = mapPlanoSinistro.collect(
                Collectors.groupingBy(
                        Plano::getCodigo, Collectors.counting()));

        List<VidasPlano> listaPlano = new ArrayList<>();
        for (String plano : resultVidaPlano.keySet()) {
            listaPlano.add(new VidasPlano(plano, resultVidaPlano.get(plano).intValue(), resultSinistroPlano.get(plano).intValue()));
        }

        return listaPlano;

    }
}
