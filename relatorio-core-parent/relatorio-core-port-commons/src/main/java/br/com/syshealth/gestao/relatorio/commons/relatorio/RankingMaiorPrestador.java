package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

public class RankingMaiorPrestador {

	private Integer ranking;
	private String prestador;
	private BigDecimal sinistro;
	private BigDecimal percSinistro;
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getPrestador() {
		return prestador;
	}
	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}
	public BigDecimal getSinistro() {
		return sinistro;
	}
	public void setSinistro(BigDecimal sinistro) {
		this.sinistro = sinistro;
	}
	public BigDecimal getPercSinistro() {
		return percSinistro;
	}
	public void setPercSinistro(BigDecimal percSinistro) {
		this.percSinistro = percSinistro;
	}	
}
