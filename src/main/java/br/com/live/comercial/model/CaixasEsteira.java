package br.com.live.comercial.model;

public class CaixasEsteira {

    public int caixa;
    public String area;
    public int quantidade;

    public int getCaixa() {
        return caixa;
    }

    public void setCaixa(int caixa) {
        this.caixa = caixa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public CaixasEsteira() {

    }

    public CaixasEsteira(int caixa, String area, int quantidade) {
        this.caixa = caixa;
        this.area = area;
        this.quantidade = quantidade;
    }
}
