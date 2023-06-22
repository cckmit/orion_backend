package br.com.live.comercial.model;

import java.util.Date;

public class DescontoClientesImportados {

    public long id;
    public String cnpjCliente;
    public float valor;
    public String dataInsercao;
    public String observacao;
    public int pedido;
    public String usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCnpjCliente() {
        return cnpjCliente;
    }

    public void setCnpjCliente(String cnpjCliente) {
        this.cnpjCliente = cnpjCliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(String dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public DescontoClientesImportados() {
    }

    public DescontoClientesImportados(long id, String cnpjCliente, float valor, String dataInsercao, String observacao, int pedido, String usuario) {
        this.id = id;
        this.cnpjCliente = cnpjCliente;
        this.valor = valor;
        this.dataInsercao = dataInsercao;
        this.observacao = observacao;
        this.pedido = pedido;
        this.usuario = usuario;
    }
}
