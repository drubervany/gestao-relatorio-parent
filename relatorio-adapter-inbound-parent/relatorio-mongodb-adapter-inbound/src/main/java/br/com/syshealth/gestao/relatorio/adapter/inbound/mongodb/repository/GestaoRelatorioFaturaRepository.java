package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity.GestaoRelatorioPremioEntity;

public interface GestaoRelatorioFaturaRepository extends MongoRepository<GestaoRelatorioPremioEntity, String> {

    GestaoRelatorioPremioEntity findByCompetencia(Integer competencia);

}
