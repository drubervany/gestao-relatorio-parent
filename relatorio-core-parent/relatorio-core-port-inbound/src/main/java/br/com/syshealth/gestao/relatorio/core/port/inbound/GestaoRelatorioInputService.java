package br.com.syshealth.gestao.relatorio.core.port.inbound;

import br.com.syshealth.gestao.relatorio.commons.Relatorio;
import br.com.syshealth.gestao.relatorio.commons.exception.CoreValidationException;

/**
 * The Interface IdentificadorInputService.
 * 
 * @author Danilo.Rubervany
 */
public interface GestaoRelatorioInputService {

    void processaMensagem(Relatorio relatorio) throws CoreValidationException;
}
