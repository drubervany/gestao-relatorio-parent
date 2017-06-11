package br.com.syshealth.gestao.relatorio.adapter.inbound.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.GsonBuilder;
import com.querydsl.core.annotations.QueryEntity;

import br.com.syshealth.commons.dto.EmpresaSinistro;
import br.com.syshealth.commons.dto.Sinistro;
import br.com.syshealth.commons.utils.Sistema;

@QueryEntity
@Document(collection = "sinistro")
public class GestaoRelatorioSinistroEntity {

    private String id;

    private Integer competencia;

    private EmpresaSinistro empresa;

    public GestaoRelatorioSinistroEntity() {

    }

    public GestaoRelatorioSinistroEntity(Sinistro sinistro) {
        this.setCompetencia(sinistro.getCompetencia());
        this.setEmpresa(sinistro.getEmpresa());
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

    public EmpresaSinistro getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaSinistro empresa) {
        this.empresa = empresa;
    }

    public String toJson() {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().toJson(this);
    }

    public static GestaoRelatorioSinistroEntity fromJson(String json) {
        return new GsonBuilder().setDateFormat(Sistema.FORMATO_DATA.getValue()).create().fromJson(json,
                GestaoRelatorioSinistroEntity.class);
    }
}
