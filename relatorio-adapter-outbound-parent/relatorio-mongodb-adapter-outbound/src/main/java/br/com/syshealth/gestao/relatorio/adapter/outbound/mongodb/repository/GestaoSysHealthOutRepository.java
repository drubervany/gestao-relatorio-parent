package br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.entity.PerfilFinanceiroEntity;

public interface GestaoSysHealthOutRepository extends MongoRepository<PerfilFinanceiroEntity, String> {

    PerfilFinanceiroEntity findByCompetencia(Integer competencia);
}
