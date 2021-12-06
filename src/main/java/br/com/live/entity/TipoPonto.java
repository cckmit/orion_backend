package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_082")
public class TipoPonto {

    @Id
    public int id;

    public String descricao;

    public TipoPonto() {

    }

    public TipoPonto(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}
