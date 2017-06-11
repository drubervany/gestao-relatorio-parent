package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

import com.google.gson.GsonBuilder;

import br.com.syshealth.commons.utils.Sistema;

public class PerfilFinanceiro {

    private final Integer competencia;

    private final Integer vidas;

    private final BigDecimal premio;

    private BigDecimal premioPmpm;

    private final BigDecimal aporte;

    private final BigDecimal sinistro;

    private BigDecimal sinistroPmpm;

    private final BigDecimal copay;

    private BigDecimal sinistralidade;

    public PerfilFinanceiro(Integer competencia, Integer vidas, BigDecimal premio, BigDecimal aporte,
            BigDecimal sinistro, BigDecimal copay) {
        super();
        this.competencia = competencia;
        this.vidas = vidas;
        this.premio = premio;
        this.aporte = aporte;
        this.sinistro = sinistro;
        this.copay = copay;
    }

    public Integer getCompetencia() {
        return competencia;
    }

    public Integer getVidas() {
        return vidas;
    }

    public BigDecimal getPremio() {
        return premio;
    }

    public BigDecimal getPremioPmpm() {
        return premioPmpm;
    }

    public BigDecimal getAporte() {
        return aporte;
    }

    public BigDecimal getSinistro() {
        return sinistro;
    }

    public BigDecimal getSinistroPmpm() {
        return sinistroPmpm;
    }

    public BigDecimal getCopay() {
        return copay;
    }

    public BigDecimal getSinistralidade() {
        return sinistralidade;
    }

    public String toJson() {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().toJson(this);
    }

    public static PerfilFinanceiro fromJson(String json) {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().fromJson(json,
                PerfilFinanceiro.class);
    }
}
