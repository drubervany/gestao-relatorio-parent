package br.com.syshealth.gestao.relatorio.outbound.producer;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.syshealth.gestao.relatorio.commons.exception.CoreValidationException;
import br.com.syshealth.gestao.relatorio.core.port.outbound.GestaoRelatorioJmsClient;

@Service
public class GestaoRelatorioProducer implements GestaoRelatorioJmsClient {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Override
    public void send(String contrato) throws CoreValidationException {
        this.jmsMessagingTemplate.convertAndSend(this.queue, contrato.toString());
    }

}