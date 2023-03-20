package br.com.live.model;

import java.util.Date;

public class ConsultaPedidosPorCliente {
    public int pedido;
    public int natureza;
    public float valorSaldo;
    public int cnpj9;
    public int cnpj4;
    public int cnpj2;
    public Date dataEmbarque;
    public String usuario;
    public String cnpjCliente;
    public String observacao;
    public String pedidoCliente;

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getNatureza() {
        return natureza;
    }

    public void setNatureza(int natureza) {
        this.natureza = natureza;
    }

    public float getValorSaldo() {
        return valorSaldo;
    }

    public void setValorSaldo(float valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

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

    public Date getDataEmbarque() {
        return dataEmbarque;
    }

    public void setDataEmbarque(Date dataEmbarque) {
        this.dataEmbarque = dataEmbarque;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCnpjCliente() {
        return cnpjCliente;
    }

    public void setCnpjCliente(String cnpjCliente) {
        this.cnpjCliente = cnpjCliente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(String pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }

    public ConsultaPedidosPorCliente() {
    }

    public ConsultaPedidosPorCliente(int pedido, int natureza, float valorSaldo, int cnpj9, int cnpj4, int cnpj2, Date dataEmbarque) {
        this.pedido = pedido;
        this.natureza = natureza;
        this.valorSaldo = valorSaldo;
        this.cnpj9 = cnpj9;
        this.cnpj4 = cnpj4;
        this.cnpj2 = cnpj2;
        this.dataEmbarque = dataEmbarque;
    }

    public ConsultaPedidosPorCliente(int pedido, float valorSaldo, String usuario, String cnpjCliente, String observacao) {
        this.pedido = pedido;
        this.valorSaldo = valorSaldo;
        this.usuario = usuario;
        this.cnpjCliente = cnpjCliente;
        this.observacao = observacao;
    }
}
