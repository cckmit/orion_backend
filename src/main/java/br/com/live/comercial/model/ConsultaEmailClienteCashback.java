package br.com.live.comercial.model;

public class ConsultaEmailClienteCashback {
    public int pedidoCliente;
    public String emailCliente;

    public String loja;

    public int getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(int pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public ConsultaEmailClienteCashback() {}

    public ConsultaEmailClienteCashback(int pedidoCliente, String emailCliente, String loja) {
        this.pedidoCliente = pedidoCliente;
        this.emailCliente = emailCliente;
        this.loja = loja;
    }
}
