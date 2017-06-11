package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

import br.com.syshealth.commons.utils.StringUtils;

public class SinistroRedeReembolso {

    private Integer competencia;

    private BigDecimal rede;

    private BigDecimal reembolso;

    private BigDecimal total;

    private BigDecimal percentual;

    public Integer getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Integer competencia) {
        this.competencia = competencia;
    }

    public BigDecimal getRede() {
        return rede;
    }

    public void setRede(BigDecimal rede) {
        this.rede = rede;
    }

    public BigDecimal getReembolso() {
        return reembolso;
    }

    public void setReembolso(BigDecimal reembolso) {
        this.reembolso = reembolso;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public String getCompetenciaConvertida() {
        return StringUtils.converteCompetenciaEmDataAbreviada(competencia);
    }
}
