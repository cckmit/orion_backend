package br.com.live.model;

import java.math.BigDecimal;

public class ConciliacaoLojaDreConsulta {

    public String cnpjLoja;
    public String nomeLoja;
    public int mesDre;
    public int anoDre;
    public BigDecimal valTaxaCaptura;
    public BigDecimal valCustoAntecipacao;

    public ConciliacaoLojaDreConsulta(String cnpjLoja, String nomeLoja, int mesDre, int anoDre, BigDecimal valTaxaCaptura, BigDecimal valCustoAntecipacao) {
        this.cnpjLoja = cnpjLoja;
        this.nomeLoja = nomeLoja;
        this.mesDre = mesDre;
        this.anoDre = anoDre;
        this.valTaxaCaptura = valTaxaCaptura;
        this.valCustoAntecipacao = valCustoAntecipacao;
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
