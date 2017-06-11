package br.com.syshealth.gestao.relatorio.commons.relatorio;

public class VidasTitulariedade {

    private final String faixaEtaria;

    private final Integer titular;

    private final Integer dependente;

    private final Integer afastado;

    private final Integer demitido;

    private final Integer aposentado;

    private final Integer agregado;

    private final Integer total;

    public VidasTitulariedade(String faixaEtaria, Integer titular, Integer dependente, Integer afastado, Integer demitido,
            Integer aposentado, Integer agregado) {
        super();
        this.faixaEtaria = faixaEtaria;
        this.titular = titular;
        this.dependente = dependente;
        this.afastado = afastado;
        this.demitido = demitido;
        this.aposentado = aposentado;
        this.agregado = agregado;
        this.total = this.titular + this.dependente + this.afastado + this.demitido + this.aposentado + this.agregado;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public Integer getTitular() {
        return titular;
    }

    public Integer getDependente() {
        return dependente;
    }

    public Integer getAfastado() {
        return afastado;
    }

    public Integer getDemitido() {
        return demitido;
    }

    public Integer getAposentado() {
        return aposentado;
    }

    public Integer getAgregado() {
        return agregado;
    }

    public Integer getTotal() {
        return total;
    }
}
