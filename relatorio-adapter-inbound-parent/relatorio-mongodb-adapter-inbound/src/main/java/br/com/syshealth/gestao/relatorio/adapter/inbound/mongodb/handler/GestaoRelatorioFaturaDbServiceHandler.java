package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.syshealth.commons.dto.Premio;
import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity.GestaoRelatorioPremioEntity;
import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.repository.GestaoRelatorioFaturaRepository;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInboundFaturaDbService;

@Service
public class GestaoRelatorioFaturaDbServiceHandler implements GestaoRelatorioInboundFaturaDbService {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(GestaoRelatorioFaturaDbServiceHandler.class);

    /** The dao. */
    @Autowired
    private GestaoRelatorioFaturaRepository repository;

    @Override
    public Premio find(Relatorio relatorio) {
        log.info("findPremio {}", relatorio.toString());

        GestaoRelatorioPremioEntity premioEntity = repository.findByCompetencia(relatorio.getCompetenciaInicial());

        log.info("findPremio {}", premioEntity);

        if (premioEntity == null)
            return null;

        return Premio.fromJson(premioEntity.toJson());
    }

}
