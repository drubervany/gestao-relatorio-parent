package br.com.syshealth.gestao.relatorio.adapter.outbound.mongodb.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.GsonBuilder;
import com.querydsl.core.annotations.QueryEntity;

import br.com.syshealth.commons.utils.Sistema;
import br.com.syshealth.commons.utils.StringUtils;

@QueryEntity
@Document(collection = "perfilFinanceiro")
public class PerfilFinanceiroEntity {

    private String id;

    private Integer competencia;

    private Integer vidas;

    private BigDecimal premio = BigDecimal.ZERO;

    private BigDecimal premioPmpm = BigDecimal.ZERO;

    private BigDecimal aporte = BigDecimal.ZERO;

    private BigDecimal sinistro = BigDecimal.ZERO;

    private BigDecimal sinistroPmpm = BigDecimal.ZERO;

    private BigDecimal copay = BigDecimal.ZERO;

    private BigDecimal sinistralidade = BigDecimal.ZERO;

    public Integer getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Integer competencia) {
        this.competencia = competencia;
    }

    public Integer getVidas() {
        return vidas;
    }

    public void setVidas(Integer vidas) {
        this.vidas = vidas;
    }

    public BigDecimal getPremio() {
        return premio;
    }

    public void setPremio(BigDecimal premio) {
        this.premio = premio;
    }

    public BigDecimal getPremioPmpm() {
        return premioPmpm;
    }

    public void setPremioPmpm(BigDecimal premioPmpm) {
        this.premioPmpm = premioPmpm;
    }

    public BigDecimal getSinistro() {
        return sinistro;
    }

    public void setSinistro(BigDecimal sinistro) {
        this.sinistro = sinistro;
    }

    public BigDecimal getSinistroPmpm() {
        return sinistroPmpm;
    }

    public void setSinistroPmpm(BigDecimal sinistroPmpm) {
        this.sinistroPmpm = sinistroPmpm;
    }

    public BigDecimal getCopay() {
        return copay;
    }

    public void setCopay(BigDecimal copay) {
        this.copay = copay;
    }

    public BigDecimal getSinistralidade() {
        return sinistralidade;
    }

    public void setSinistralidade(BigDecimal sinistralidade) {
        this.sinistralidade = sinistralidade;
    }

    public String getCompetenciaConvertida() {
        return StringUtils.converteCompetenciaEmDataAbreviada(competencia);
    }

    public BigDecimal getAporte() {
        return aporte;
    }

    public void setAporte(BigDecimal aporte) {
        this.aporte = aporte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toJson() {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().toJson(this);
    }

    public static PerfilFinanceiroEntity fromJson(String json) {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().fromJson(json,
                PerfilFinanceiroEntity.class);
    }

}
