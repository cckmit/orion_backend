package br.com.live.producao.model;

public class ConsultaRestricoesRolo {
    public long id;
    public int ordemProd;
    public int rolo;
    public int deposito;
    public String restricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrdemProd() {
        return ordemProd;
    }

    public void setOrdemProd(int ordemProd) {
        this.ordemProd = ordemProd;
    }

    public int getRolo() {
        return rolo;
    }

    public void setRolo(int rolo) {
        this.rolo = rolo;
    }

    public int getDeposito() {
        return deposito;
    }

    public void setDeposito(int deposito) {
        this.deposito = deposito;
    }

    public String getRestricao() {
        return restricao;
    }

    public void setRestricao(String restricao) {
        this.restricao = restricao;
    }

    public ConsultaRestricoesRolo() {

    }

    public ConsultaRestricoesRolo(long id, int ordemProd, int rolo, int deposito, String restricao) {
        this.id = id;
        this.ordemProd = ordemProd;
        this.rolo = rolo;
        this.deposito = deposito;
        this.restricao = restricao;
    }
}
