package br.com.live.comercial.model;

public class ConsultaEmailClienteCashback {
    public int pedidoCliente;
    public String emailCliente;

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

    public ConsultaEmailClienteCashback() {}

    public ConsultaEmailClienteCashback(int pedidoCliente, String emailCliente) {
        this.pedidoCliente = pedidoCliente;
        this.emailCliente = emailCliente;
    }
}
