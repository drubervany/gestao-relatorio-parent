package br.com.syshealth.gestao.relatorio.core.port.inbound;

import br.com.syshealth.commons.dto.Cadastro;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;

/**
 * The Interface IdentificadorInputService.
 * 
 * @author Danilo.Rubervany
 */
public interface GestaoRelatorioInboundCadastroDbService {

    Cadastro find(Relatorio relatorio);
}
