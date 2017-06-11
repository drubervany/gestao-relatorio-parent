package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.syshealth.commons.dto.Cadastro;
import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity.GestaoRelatorioCadastroEntity;
import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.repository.GestaoRelatorioCadastroRepository;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInboundCadastroDbService;

@Service
public class GestaoRelatorioCadastroDbServiceHandler implements GestaoRelatorioInboundCadastroDbService {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(GestaoRelatorioCadastroDbServiceHandler.class);

    /** The dao. */
    @Autowired
    private GestaoRelatorioCadastroRepository repository;

    @Override
    public Cadastro find(Relatorio relatorio) {
        log.info("findCadastro {}", relatorio.toString());

        GestaoRelatorioCadastroEntity cadastroEntity = repository.findByCompetencia(relatorio.getCompetenciaInicial());

        log.info("findCadastro {}", cadastroEntity);

        if (cadastroEntity == null)
            return null;

        return Cadastro.fromJson(cadastroEntity.toJson());
    }

}
