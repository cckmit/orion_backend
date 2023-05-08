package br.com.live.model;

public class DreLojaConsulta {

    long id;
    String cnpjLoja;
    String nomeLoja;
    String nomeSupervisor;
    int anoDre;
    int mesDre;

    public DreLojaConsulta(long id, String cnpjLoja, String nomeLoja, String nomeSupervisor, int anoDre, int mesDre) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.nomeLoja = nomeLoja;
        this.nomeSupervisor = nomeSupervisor;
        this.anoDre = anoDre;
        this.mesDre = mesDre;
    }

    public String getNomeSupervisor() {
        return nomeSupervisor;
    }

    public void setNomeSupervisor(String nomeSupervisor) {
        this.nomeSupervisor = nomeSupervisor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DreLojaConsulta(){}

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

    public int getAnoDre() {
        return anoDre;
    }

    public void setAnoDre(int anoDre) {
        this.anoDre = anoDre;
    }

    public int getMesDre() {
        return mesDre;
    }

    public void setMesDre(int mesDre) {
        this.mesDre = mesDre;
    }
}
