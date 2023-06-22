package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cfc_271")
public class RestricoesRolo {

    @Id
    public long id;

    @Column(name = "codigo_rolo")
    public int codigoRolo;
    public int restricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodigoRolo() {
        return codigoRolo;
    }

    public void setCodigoRolo(int codigoRolo) {
        this.codigoRolo = codigoRolo;
    }

    public int getRestricao() {
        return restricao;
    }

    public void setRestricao(int restricao) {
        this.restricao = restricao;
    }

    public RestricoesRolo() {

    }

    public RestricoesRolo(long id, int codigoRolo, int restricao) {
        this.id = id;
        this.codigoRolo = codigoRolo;
        this.restricao = restricao;
    }
}
