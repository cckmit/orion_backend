package br.com.live.integracao.cigam.model;

public class ParametrosRetorno {
    public String data_inicial;
    public String data_fim;
    public String cnpj;

    public String getData_inicial() {
        return data_inicial;
    }
    public void setData_inicial(String data_inicial) {
        this.data_inicial = data_inicial;
    }
    public String getData_fim() {
        return data_fim;
    }
    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public ParametrosRetorno() {

    }

    public ParametrosRetorno(String data_inicial, String data_fim) {
        this.data_inicial = data_inicial;
        this.data_fim = data_fim;
    }

    public ParametrosRetorno(String cnpj) {
        this.cnpj = cnpj;
    }
}
