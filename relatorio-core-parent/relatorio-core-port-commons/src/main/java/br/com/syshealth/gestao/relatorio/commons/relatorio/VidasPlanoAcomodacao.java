package br.com.syshealth.gestao.relatorio.commons.relatorio;

import br.com.syshealth.commons.utils.StringUtils;

public class VidasPlanoAcomodacao {

    private final Integer competencia;

    private final Integer basico;

    private final Integer intermediario;

    private final Integer executivo;

    private final Integer total;

    public VidasPlanoAcomodacao(Integer competencia, Integer basico, Integer intermediario, Integer executivo) {
        super();
        this.competencia = competencia;
        this.basico = basico;
        this.intermediario = intermediario;
        this.executivo = executivo;
        this.total = this.basico + this.intermediario + this.executivo;
    }

    public Integer getCompetencia() {
        return competencia;
    }

    public Integer getBasico() {
        return basico;
    }

    public Integer getIntermediario() {
        return intermediario;
    }

    public Integer getExecutivo() {
        return executivo;
    }

    public Integer getTotal() {
        return total;
    }

    public String getCompetenciaConvertida() {
        return StringUtils.converteCompetenciaEmDataAbreviada(competencia);
    }
}
