package br.com.live.administrativo.model;

import java.util.List;

public class ConsultaRelacionamentoLojaDre {

    Long id;
    String cnpjLoja;
    String nomeLoja;
    String cnpjSupervisor;
    String nomeSupervisor;
    String centroCustos;

    public ConsultaRelacionamentoLojaDre(Long id, String cnpjLoja, String nomeLoja, String cnpjSupervisor, String nomeSupervisor, String centroCustos) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.nomeLoja = nomeLoja;
        this.cnpjSupervisor = cnpjSupervisor;
        this.nomeSupervisor = nomeSupervisor;
        this.centroCustos = centroCustos;
    }

    public ConsultaRelacionamentoLojaDre(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public String getCnpjSupervisor() {
        return cnpjSupervisor;
    }

    public void setCnpjSupervisor(String cnpjSupervisor) {
        this.cnpjSupervisor = cnpjSupervisor;
    }

    public String getNomeSupervisor() {
        return nomeSupervisor;
    }

    public void setNomeSupervisor(String nomeSupervisor) {
        this.nomeSupervisor = nomeSupervisor;
    }

    public String getCentroCustos() {
        return centroCustos;
    }

    public void setCentroCustos(String centroCustos) {
        this.centroCustos = centroCustos;
    }
}
