package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity.GestaoRelatorioCadastroEntity;

public interface GestaoRelatorioCadastroRepository extends MongoRepository<GestaoRelatorioCadastroEntity, String> {

    GestaoRelatorioCadastroEntity findByCompetencia(Integer competencia);

}
