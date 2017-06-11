package br.com.syshealth.gestao.relatorio.commons.relatorio;

import java.math.BigDecimal;

import br.com.syshealth.commons.dto.Cadastro;
import br.com.syshealth.commons.dto.SubEmpresaCadastro;
import br.com.syshealth.commons.enums.FaixaEtariaEnum;
import br.com.syshealth.commons.enums.SexoEnum;

public class VidasGenero {

    private String faixaEtaria;

    private Integer masculino = 0;

    private Integer feminino = 0;

    private Integer total = 0;

    private BigDecimal percentual = BigDecimal.ZERO;

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public Integer getMasculino() {
        return masculino;
    }

    public void setMasculino(Integer masculino) {
        this.masculino = masculino;
    }

    public Integer getFeminino() {
        return feminino;
    }

    public void setFeminino(Integer feminino) {
        this.feminino = feminino;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public void atualizar(Cadastro cadastro, FaixaEtariaEnum faixaEtaria) {

        int totalFaixaEtaria = 0;

        for (SubEmpresaCadastro subEmpresa : cadastro.getEmpresa().getSubEmpresas()) {

            totalFaixaEtaria += subEmpresa.getSegurados().size();

            this.masculino += (int) subEmpresa.getSegurados().stream().filter(m -> m.getIdade() >= faixaEtaria.getIdadeInicio() &&
                    m.getIdade() <= faixaEtaria.getIdadeFinal() &&
                    m.getSexo().equals(SexoEnum.MASCULINO)).count();

            this.feminino += (int) subEmpresa.getSegurados().stream().filter(m -> m.getIdade() >= faixaEtaria.getIdadeInicio() &&
                    m.getIdade() <= faixaEtaria.getIdadeFinal() &&
                    m.getSexo().equals(SexoEnum.FEMININO)).count();

            this.total = this.masculino + this.feminino;
        }

        if (totalFaixaEtaria != 0)
            this.percentual = BigDecimal.valueOf(this.total / totalFaixaEtaria);
    }
}