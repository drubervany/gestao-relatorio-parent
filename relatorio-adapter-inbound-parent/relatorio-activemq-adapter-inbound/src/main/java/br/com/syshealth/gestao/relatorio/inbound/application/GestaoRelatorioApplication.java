package br.com.syshealth.gestao.relatorio.inbound.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "br.com.syshealth.gestao.relatorio" })
@SpringBootApplication
public class GestaoRelatorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoRelatorioApplication.class, args);
    }
}
