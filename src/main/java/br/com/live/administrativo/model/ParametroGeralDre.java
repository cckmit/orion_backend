package br.com.live.administrativo.model;

public class ParametroGeralDre {
    public long id;
    public int anoDre;
    public int mesDre;
    public double percEncargos;
    public double valImpostoPlanejamento;
    public double valCustoVendaProduto;

    public ParametroGeralDre(long id, int anoDre, int mesDre, double percEncargos, double valImpostoPlanejamento, double valCustoVendaProduto) {
        this.id = id;
        this.anoDre = anoDre;
        this.mesDre = mesDre;
        this.percEncargos = percEncargos;
        this.valImpostoPlanejamento = valImpostoPlanejamento;
        this.valCustoVendaProduto = valCustoVendaProduto;
    }

    public ParametroGeralDre(){}

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
