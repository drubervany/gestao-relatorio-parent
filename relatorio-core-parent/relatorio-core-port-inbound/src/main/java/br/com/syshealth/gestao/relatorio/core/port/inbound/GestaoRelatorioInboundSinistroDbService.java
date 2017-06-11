package br.com.syshealth.gestao.relatorio.core.port.inbound;

import br.com.syshealth.commons.dto.Sinistro;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;

/**
 * The Interface IdentificadorInputService.
 * 
 * @author Danilo.Rubervany
 */
public interface GestaoRelatorioInboundSinistroDbService {

    Sinistro find(Relatorio relatorio);
}
