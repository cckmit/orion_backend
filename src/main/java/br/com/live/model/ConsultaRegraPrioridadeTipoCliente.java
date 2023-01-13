package br.com.live.model;

public class ConsultaRegraPrioridadeTipoCliente {
    public String tipoCliente;
    public int prioridade;
    public int id;

    public String getTipoCliente() {
        return tipoCliente;
    }
    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public int getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ConsultaRegraPrioridadeTipoCliente() {

    }

    public ConsultaRegraPrioridadeTipoCliente(String tipoCliente, int prioridade, int id) {
        this.tipoCliente = tipoCliente;
        this.prioridade = prioridade;
        this.id = id;
    }
}
