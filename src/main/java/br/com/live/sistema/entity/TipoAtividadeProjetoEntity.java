package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_035")
public class TipoAtividadeProjetoEntity {

    @Id
    private Long id;

    private String descricao;

    @Column(name = "exige_doc")
    private int exigeDoc;

    public TipoAtividadeProjetoEntity(Long id, String descricao, int exigeDoc) {
        this.id = id;
        this.descricao = descricao;
        this.exigeDoc = exigeDoc;
    }

    public TipoAtividadeProjetoEntity(){

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

    public int getExigeDoc() {
        return exigeDoc;
    }

    public void setExigeDoc(int exigeDoc) {
        this.exigeDoc = exigeDoc;
    }
}
