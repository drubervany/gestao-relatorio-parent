package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

public class SinistroFaixaEtaria {

    private final String faixaEtaria;

    private final Integer vidas;

    private final BigDecimal sinistro;

    private final BigDecimal sinistroPmpm;

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public Integer getVidas() {
        return vidas;
    }

    public BigDecimal getSinistro() {
        return sinistro;
    }

    public BigDecimal getSinistroPmpm() {
        return sinistroPmpm;
    }

    public SinistroFaixaEtaria(String faixaEtaria, Integer vidas, BigDecimal sinistro) {
        super();
        this.faixaEtaria = faixaEtaria;
        this.vidas = vidas;
        this.sinistro = sinistro;
        this.sinistroPmpm = sinistro.divide(BigDecimal.valueOf(vidas));
    }

}
