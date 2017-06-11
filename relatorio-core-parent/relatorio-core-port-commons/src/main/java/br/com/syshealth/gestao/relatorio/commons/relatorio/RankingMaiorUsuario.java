package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

public class RankingMaiorUsuario {

	private Integer ranking;
	private String titularidade;
	private String genero;
	private Integer idade;
	private String status;
	private BigDecimal sinistro;
	private BigDecimal percSinistro;

	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getTitularidade() {
		return titularidade;
	}
	public void setTitularidade(String titularidade) {
		this.titularidade = titularidade;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
