package br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.syshealth.gestao.relatorio")
@ComponentScan(basePackages = { "br.com.syshealth.gestao.relatorio" })
public class GestaoSysHealthOutMongodbConfiguration {

}

// {"competenciaInicial":201404}