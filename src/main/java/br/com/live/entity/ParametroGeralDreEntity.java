package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_020")
public class ParametroGeralDreEntity {
    @Id
    private long id;

    @Column(name = "ano_dre")
    private int anoDre;

    @Column(name = "mes_dre")
    private int mesDre;

    @Column(name = "perc_encargos")
    private double percEncargos;

    @Column(name = "val_imposto_planejamento")
    private double valImpostoPlanejamento;

    @Column(name = "val_custo_venda_produto")
    private double valCustoVendaProduto;

    public ParametroGeralDreEntity(long id, int anoDre, int mesDre, double percEncargos, double valImpostoPlanejamento, double valCustoVendaProduto) {
        this.id = id;
        this.anoDre = anoDre;
        this.mesDre = mesDre;
        this.percEncargos = percEncargos;
        this.valImpostoPlanejamento = valImpostoPlanejamento;
        this.valCustoVendaProduto = valCustoVendaProduto;
    }
    public ParametroGeralDreEntity(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getPercEncargos() {
        return percEncargos;
    }

    public void setPercEncargos(double percEncargos) {
        this.percEncargos = percEncargos;
    }

    public double getValImpostoPlanejamento() {
        return valImpostoPlanejamento;
    }

    public void setValImpostoPlanejamento(double valImpostoPlanejamento) {
        this.valImpostoPlanejamento = valImpostoPlanejamento;
    }

    public double getValCustoVendaProduto() {
        return valCustoVendaProduto;
    }

    public void setValCustoVendaProduto(double valCustoVendaProduto) {
        this.valCustoVendaProduto = valCustoVendaProduto;
    }
}
