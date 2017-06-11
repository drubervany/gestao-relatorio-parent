package br.com.syshealth.gestao.relatorio.core.port.outbound;

import br.com.syshealth.gestao.relatorio.commons.relatorio.PerfilFinanceiro;

/**
 * The Interface IdentificadorOutboundDbService.
 */
public interface GestaoRelatorioOutboundDbService {

    void salvar(PerfilFinanceiro atualizar);
}
