package br.com.live.comercial.model;

public class ClientesImportados {
    public int cnpj9;
    public int cnpj4;
    public int cnpj2;
    public float valor;
    public String observacao;

    public int getCnpj9() {
        return cnpj9;
    }

    public void setCnpj9(int cnpj9) {
        this.cnpj9 = cnpj9;
    }

    public int getCnpj4() {
        return cnpj4;
    }

    public void setCnpj4(int cnpj4) {
        this.cnpj4 = cnpj4;
    }

    public int getCnpj2() {
        return cnpj2;
    }

    public void setCnpj2(int cnpj2) {
        this.cnpj2 = cnpj2;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ClientesImportados() {
    }

    public ClientesImportados(int cnpj9, int cnpj4, int cnpj2, float valor, String observacao) {
        this.cnpj9 = cnpj9;
        this.cnpj4 = cnpj4;
        this.cnpj2 = cnpj2;
        this.valor = valor;
        this.observacao = observacao;
    }
}
