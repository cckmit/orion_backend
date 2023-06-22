package br.com.live.administrativo.model;

public class ConciliacaoLojaDreConsulta {

    public long id;
    public String cnpjLoja;
    public String nomeLoja;
    public int mesDre;
    public int anoDre;
    public double valTaxaCaptura;
    public double valCustoAntecipacao;

    public ConciliacaoLojaDreConsulta(long id, String cnpjLoja, String nomeLoja, int mesDre, int anoDre, double valTaxaCaptura, double valCustoAntecipacao) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.nomeLoja = nomeLoja;
        this.mesDre = mesDre;
        this.anoDre = anoDre;
        this.valTaxaCaptura = valTaxaCaptura;
        this.valCustoAntecipacao = valCustoAntecipacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ConciliacaoLojaDreConsulta(){}

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

    public int getMesDre() {
        return mesDre;
    }

    public void setMesDre(int mesDre) {
        this.mesDre = mesDre;
    }

    public int getAnoDre() {
        return anoDre;
    }

    public void setAnoDre(int anoDre) {
        this.anoDre = anoDre;
    }

    public double getValTaxaCaptura() {
        return valTaxaCaptura;
    }

    public void setValTaxaCaptura(double valTaxaCaptura) {
        this.valTaxaCaptura = valTaxaCaptura;
    }

    public double getValCustoAntecipacao() {
        return valCustoAntecipacao;
    }

    public void setValCustoAntecipacao(double valCustoAntecipacao) {
        this.valCustoAntecipacao = valCustoAntecipacao;
    }
}
