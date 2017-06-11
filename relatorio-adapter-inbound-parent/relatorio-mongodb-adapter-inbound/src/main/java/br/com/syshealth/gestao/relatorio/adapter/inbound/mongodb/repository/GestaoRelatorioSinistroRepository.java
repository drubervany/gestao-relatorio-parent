package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity.GestaoRelatorioSinistroEntity;

public interface GestaoRelatorioSinistroRepository extends MongoRepository<GestaoRelatorioSinistroEntity, String> {

    GestaoRelatorioSinistroEntity findByCompetencia(Integer competencia);

}
