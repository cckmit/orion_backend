package br.com.live.administrativo.model;

public class ParcelaInfoNfsFranchising {

    private int numeroParcela;
    private String dataVencimento;
    private double valor;

    public ParcelaInfoNfsFranchising(int numeroParcela, String dataVencimento, double valor) {
        this.numeroParcela = numeroParcela;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    public ParcelaInfoNfsFranchising(){}

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
