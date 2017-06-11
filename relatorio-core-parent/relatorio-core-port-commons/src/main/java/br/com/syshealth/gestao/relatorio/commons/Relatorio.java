package br.com.syshealth.gestao.relatorio.commons;

/**
 * @author Danilo.Rubervany
 */
public class Relatorio {

    private final Long contrato;

    private final Integer competenciaInicial;

    private final Integer competenciaFinal;

    public Relatorio(Long contrato, Integer competenciaInicial, Integer competenciaFinal) {
        super();
        this.contrato = contrato;
        this.competenciaInicial = competenciaInicial;
        this.competenciaFinal = competenciaFinal;
    }

    public Long getContrato() {
        return contrato;
    }

    public Integer getCompetenciaInicial() {
        return competenciaInicial;
    }

    public Integer getCompetenciaFinal() {
        return competenciaFinal;
    }

    @Override
    public String toString() {
        return "Relatorio [contrato=" + contrato + ", competenciaInicial=" + competenciaInicial + ", competenciaFinal=" + competenciaFinal
                + "]";
    }
}
