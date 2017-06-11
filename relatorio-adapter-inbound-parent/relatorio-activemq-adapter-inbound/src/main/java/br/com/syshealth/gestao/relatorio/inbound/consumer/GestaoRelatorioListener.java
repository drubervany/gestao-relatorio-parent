package br.com.syshealth.gestao.relatorio.inbound.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.syshealth.gestao.relatorio.commons.Relatorio;
import br.com.syshealth.gestao.relatorio.commons.exception.CoreValidationException;
import br.com.syshealth.gestao.relatorio.core.port.inbound.GestaoRelatorioInputService;

@Component
public class GestaoRelatorioListener {

    private static final Logger log = LoggerFactory.getLogger(GestaoRelatorioListener.class);

    @Autowired
    private GestaoRelatorioInputService service;

    @JmsListener(destination = "${jms.queue.destination.relatorio}")
    public void receive(String msg) {

        long tempoInicial = System.currentTimeMillis();

        log.info("Processar mensagem");

        Relatorio relatorio = new Gson().fromJson(msg, Relatorio.class);

        try {
            service.processaMensagem(relatorio);
        } catch (CoreValidationException e) {
            log.error("Erro mensagem {}", e);
        }

        log.info("Mensagem processada");

        log.info("a msg executou em {}", System.currentTimeMillis() - tempoInicial);

    }
}
