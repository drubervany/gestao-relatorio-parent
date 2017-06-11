package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.GsonBuilder;
import com.querydsl.core.annotations.QueryEntity;

import br.com.syshealth.commons.dto.EmpresaPremio;
import br.com.syshealth.commons.dto.Premio;
import br.com.syshealth.commons.utils.Sistema;

@QueryEntity
@Document(collection = "fatura")
public class GestaoRelatorioPremioEntity {

    private String id;

    private Integer competencia;

    private EmpresaPremio empresa;

    public GestaoRelatorioPremioEntity() {

    }

    public GestaoRelatorioPremioEntity(Premio premio) {
        this.setCompetencia(premio.getCompetencia());
        this.setEmpresa(premio.getEmpresa());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Integer competencia) {
        this.competencia = competencia;
    }

    public EmpresaPremio getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaPremio premio) {
        this.empresa = premio;
    }

    public String toJson() {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().toJson(this);
    }

    public static GestaoRelatorioPremioEntity fromJson(String json) {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().fromJson(json, GestaoRelatorioPremioEntity.class);
    }

}
