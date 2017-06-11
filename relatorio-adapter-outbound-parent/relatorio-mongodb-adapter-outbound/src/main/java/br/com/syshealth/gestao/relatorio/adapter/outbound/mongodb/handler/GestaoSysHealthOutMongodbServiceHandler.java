package br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.entity.PerfilFinanceiroEntity;
import br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.repository.GestaoSysHealthOutRepository;
import br.com.syshealth.gestao.relatorio.commons.relatorio.PerfilFinanceiro;
import br.com.syshealth.gestao.relatorio.core.port.outbound.GestaoRelatorioOutboundDbService;

@Service
public class GestaoSysHealthOutMongodbServiceHandler implements GestaoRelatorioOutboundDbService {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(GestaoSysHealthOutMongodbServiceHandler.class);

    /** The dao. */
    @Autowired
    private GestaoSysHealthOutRepository repository;

    @Override
    public void salvar(PerfilFinanceiro perfilFinanceiro) {
        log.info("Salvar PerfilFinanceiro");
        log.info("PerfilFinanceiro {}", perfilFinanceiro.toJson());
        PerfilFinanceiroEntity perfilFinanceiroEntity = repository.findByCompetencia(perfilFinanceiro.getCompetencia());
        log.info("PerfilFinanceiro Entity {}", perfilFinanceiroEntity.toJson());
        PerfilFinanceiroEntity perfilFinanceiroNew = PerfilFinanceiroEntity.fromJson(perfilFinanceiro.toJson());
        perfilFinanceiroNew.setId(perfilFinanceiroEntity.getId());
        log.info("atualizar PerfilFinanceiro {}", perfilFinanceiroNew.toJson());
        repository.save(perfilFinanceiroNew);
        log.info("salvo PerfilFinanceiro");
    }

}
