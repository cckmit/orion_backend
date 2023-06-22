package br.com.live.producao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cfc_221")
public class TipoObservacao {
	
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

	TipoObservacao() {
    	
    }

	public TipoObservacao(long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
}
