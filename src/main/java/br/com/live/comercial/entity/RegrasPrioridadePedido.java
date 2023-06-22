package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_330")
public class RegrasPrioridadePedido {

    @Id
    @Column(name="tipo_cliente")
    public int tipoCliente;
    public int prioridade;

    public int getTipoCliente() {
        return tipoCliente;
    }
    public void setTipoCliente(int tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public int getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public RegrasPrioridadePedido() {

    }

    public RegrasPrioridadePedido(int tipoCliente, int prioridade) {
        this.tipoCliente = tipoCliente;
        this.prioridade = prioridade;
    }
}
