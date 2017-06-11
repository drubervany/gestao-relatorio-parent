package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

public class VidasPlano {

    private String plano;

    private Integer vidas = 0;

    private Integer utilizacao = 0;

    private BigDecimal percentual = BigDecimal.ZERO;

    public VidasPlano(String plano, int vidas, int utilizacao) {
        this.plano = plano;
        this.vidas = vidas;
        this.utilizacao = utilizacao;
    }

    public String getPlano() {
        return plano;
    }

    public Integer getVidas() {
        return vidas;
    }

    public Integer getUtilizacao() {
        return utilizacao;
    }

    public BigDecimal getPercentual() {

        if (vidas != null || vidas != 0) {
            percentual = BigDecimal.valueOf(utilizacao / vidas);
        }

        return percentual;
    }

}
