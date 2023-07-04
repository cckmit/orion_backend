package br.com.live.sistema.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_030")
public class FaseProjetoEntity {

    @Id
    private Long id;
    private String descricao;

    public FaseProjetoEntity(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public FaseProjetoEntity(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
