package br.com.syshealth.gestao.relatorio.core.port.inbound;

import br.com.syshealth.commons.dto.Premio;
import br.com.syshealth.gestao.relatorio.commons.Relatorio;

/**
 * The Interface IdentificadorInputService.
 * 
 * @author Danilo.Rubervany
 */
public interface GestaoRelatorioInboundFaturaDbService {

    Premio find(Relatorio relatorio);
}
