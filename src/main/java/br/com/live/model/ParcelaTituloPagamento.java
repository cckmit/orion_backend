package br.com.live.model;

import java.util.Date;

public class ParcelaTituloPagamento {
    private int nrParcela;
    private double valorParcela;
    private String dataVencimento;

    public ParcelaTituloPagamento(int nrParcela, double valorParcela, String dataVencimento) {
        this.nrParcela = nrParcela;
        this.valorParcela = valorParcela;
        this.dataVencimento = dataVencimento;
    }

    public ParcelaTituloPagamento(){

    }

    public int getNrParcela() {
        return nrParcela;
    }

    public void setNrParcela(int nrParcela) {
        this.nrParcela = nrParcela;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimaneto) {
        this.dataVencimento = dataVencimaneto;
    }
}
