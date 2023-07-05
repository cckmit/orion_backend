package br.com.live.sistema.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_050")
public class FuncaoPessoaProjetoEntity {

    @Id
    private Long id;
    private String descricao;

    public FuncaoPessoaProjetoEntity(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public FuncaoPessoaProjetoEntity(){}

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