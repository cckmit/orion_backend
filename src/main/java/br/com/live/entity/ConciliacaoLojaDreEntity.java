package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_025")
public class ConciliacaoLojaDreEntity {
    @Id
    public long id;

    @Column(name = "cnpj_loja")
    public String cnpjLoja;

    @Column(name = "ano_dre")
    public int anoDre;

    @Column(name = "mes_dre")
    public int mesDre;

    @Column(name = "val_taxa_captura")
    public double valTaxaCaptura;

    @Column(name = "val_custo_antecipacao")
    public double valCustoAntecipacao;

    public ConciliacaoLojaDreEntity(long id, String cnpjLoja, int anoDre, int mesDre, double valTaxaCaptura, double valCustoAntecipacao) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.anoDre = anoDre;
        this.mesDre = mesDre;
        this.valTaxaCaptura = valTaxaCaptura;
        this.valCustoAntecipacao = valCustoAntecipacao;
    }

    public ConciliacaoLojaDreEntity(){

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
