package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.syshealth.commons.dto.Sinistro;
import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity.GestaoRelatorioSinistroEntity;
import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.repository.GestaoRelatorioSinistroRepository;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInboundSinistroDbService;

@Service
public class GestaoRelatorioSinistroDbServiceHandler implements GestaoRelatorioInboundSinistroDbService {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(GestaoRelatorioSinistroDbServiceHandler.class);

    /** The dao. */
    @Autowired
    private GestaoRelatorioSinistroRepository repository;

    @Override
    public Sinistro find(Relatorio relatorio) {
        log.info("findSinistro {}", relatorio.toString());

        GestaoRelatorioSinistroEntity sinistroEntity = repository.findByCompetencia(relatorio.getCompetenciaInicial());

        log.info("findSinistro {}", sinistroEntity);

        if (sinistroEntity == null)
            return null;

        return Sinistro.fromJson(sinistroEntity.toJson());
    }

}
