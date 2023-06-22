package br.com.live.producao.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cfc_270")
public class Restricoes {

    @Id
    public long id;
    public String descricao;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Restricoes() {

    }

    public Restricoes(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}
