package br.com.live.administrativo.model;

public class LancamentoLojaMesAno {

    public String id;
    public String cnpjLoja;
    public int mesLancamento;
    public int anoLancamento;
    public int qtdPecaFaturada;
    public int qtdPecaConsumo;
    public double valFaturamento;
    public double valImpostoFaturamento;

    public LancamentoLojaMesAno(String id, String cnpjLoja, int mesLancamento, int anoLancamento, int qtdPecaFaturada, int qtdPecaConsumo, double valFaturamento, double valImpostoFaturamento) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.mesLancamento = mesLancamento;
        this.anoLancamento = anoLancamento;
        this.qtdPecaFaturada = qtdPecaFaturada;
        this.qtdPecaConsumo = qtdPecaConsumo;
        this.valFaturamento = valFaturamento;
        this.valImpostoFaturamento = valImpostoFaturamento;
    }

    public LancamentoLojaMesAno(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public int getMesLancamento() {
        return mesLancamento;
    }

    public void setMesLancamento(int mesLancamento) {
        this.mesLancamento = mesLancamento;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public int getQtdPecaFaturada() {
        return qtdPecaFaturada;
    }

    public void setQtdPecaFaturada(int qtdPecaFaturada) {
        this.qtdPecaFaturada = qtdPecaFaturada;
    }

    public int getQtdPecaConsumo() {
        return qtdPecaConsumo;
    }

    public void setQtdPecaConsumo(int qtdPecaConsumo) {
        this.qtdPecaConsumo = qtdPecaConsumo;
    }

    public double getValFaturamento() {
        return valFaturamento;
    }

    public void setValFaturamento(double valFaturamento) {
        this.valFaturamento = valFaturamento;
    }

    public double getValImpostoFaturamento() {
        return valImpostoFaturamento;
    }

    public void setValImpostoFaturamento(double valImpostoFaturamento) {
        this.valImpostoFaturamento = valImpostoFaturamento;
    }
}
