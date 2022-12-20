package br.com.live.model;

public class CaixasEsteira {

    public int caixa;
    public String area;

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

    public CaixasEsteira() {

    }

    public CaixasEsteira(int caixa, String area) {
        this.caixa = caixa;
        this.area = area;
    }
}
