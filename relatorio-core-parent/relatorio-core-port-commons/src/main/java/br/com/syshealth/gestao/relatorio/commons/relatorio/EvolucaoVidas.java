package br.com.syshealth.gestao.relatorio.commons.relatorio;

import br.com.syshealth.commons.utils.StringUtils;

public class EvolucaoVidas {

    private final Integer competencia;

    private final Integer vidas;

    public EvolucaoVidas(Integer competencia, Integer vidas) {
        super();
        this.competencia = competencia;
        this.vidas = vidas;
    }

    public Integer getCompetencia() {
        return competencia;
    }

    public Integer getVidas() {
        return vidas;
    }

    public String getCompetenciaConvertida() {
        return StringUtils.converteCompetenciaEmDataAbreviada(competencia);
    }

}
