package br.com.live.comercial.model;

import java.util.Date;

public class PedidosComDescontoAConfirmar {
    public int cnpj9;
    public int cnpj4;
    public int cnpj2;
    public String cnpjCliente;
    public float valorPedido;
    public float valorDesconto;
    public String observacao;
    public Date dataPedido;
    public int pedido;

    public PedidosComDescontoAConfirmar() {
    }

    public PedidosComDescontoAConfirmar(int cnpj9, int cnpj4, int cnpj2, String cnpjCliente, float valorPedido, float valorDesconto, String observacao, Date dataPedido, int pedido) {
        this.cnpj9 = cnpj9;
        this.cnpj4 = cnpj4;
        this.cnpj2 = cnpj2;
        this.cnpjCliente = cnpjCliente;
        this.valorPedido = valorPedido;
        this.valorDesconto = valorDesconto;
        this.observacao = observacao;
        this.dataPedido = dataPedido;
        this.pedido = pedido;
    }
}
