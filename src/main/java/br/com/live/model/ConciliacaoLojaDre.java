package br.com.live.model;

import java.math.BigDecimal;

public class ConciliacaoLojaDre {

    public long id;
    public String cnpjLoja;
    public int anoDre;
    public int mesDre;
    public BigDecimal valTaxaCaptura;
    public BigDecimal valCustoAntecipacao;

    public ConciliacaoLojaDre(long id, String cnpjLoja, int anoDre, int mesDre, BigDecimal valTaxaCaptura, BigDecimal valCustoAntecipacao) {
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

    public BigDecimal getValTaxaCaptura() {
        return valTaxaCaptura;
    }

    public void setValTaxaCaptura(BigDecimal valTaxaCaptura) {
        this.valTaxaCaptura = valTaxaCaptura;
    }

    public BigDecimal getValCustoAntecipacao() {
        return valCustoAntecipacao;
    }

    public void setValCustoAntecipacao(BigDecimal valCustoAntecipacao) {
        this.valCustoAntecipacao = valCustoAntecipacao;
    }
}
