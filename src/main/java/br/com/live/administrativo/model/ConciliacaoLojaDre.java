package br.com.live.administrativo.model;

public class ConciliacaoLojaDre {

    public long id;
    public String cnpjLoja;
    public int anoDre;
    public int mesDre;
    public double valTaxaCaptura;
    public double valCustoAntecipacao;

    public ConciliacaoLojaDre(long id, String cnpjLoja, int anoDre, int mesDre, double valTaxaCaptura, double valCustoAntecipacao) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.anoDre = anoDre;
        this.mesDre = mesDre;
        this.valTaxaCaptura = valTaxaCaptura;
        this.valCustoAntecipacao = valCustoAntecipacao;
    }

    public ConciliacaoLojaDre(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
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
