package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

public class SinistroGrupoConsolidado {

	private String grupoServico;
	private Integer quantidade;
	private BigDecimal sinistro;
	private BigDecimal frequencia;
	private BigDecimal custoMedio;

	public String getGrupoServico() {
		return grupoServico;
	}
	public void setGrupoServico(String grupoServico) {
		this.grupoServico = grupoServico;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getSinistro() {
		return sinistro;
	}
	public void setSinistro(BigDecimal sinistro) {
		this.sinistro = sinistro;
	}
	public BigDecimal getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(BigDecimal frequencia) {
		this.frequencia = frequencia;
	}
	public BigDecimal getCustoMedio() {
		return custoMedio;
	}
	public void setCustoMedio(BigDecimal custoMedio) {
		this.custoMedio = custoMedio;
	}

}
