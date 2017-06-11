package br.com.syshealth.gestao.relatorio.core.port.outbound;

import br.com.syshealth.gestao.relatorio.commons.exception.CoreValidationException;

public interface GestaoRelatorioJmsClient {

    void send(String contrato) throws CoreValidationException;

}
