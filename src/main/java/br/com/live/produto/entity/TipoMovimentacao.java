package br.com.live.produto.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_096")
public class TipoMovimentacao {
	
	@Id
	public int id;
	public String descricao;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoMovimentacao() {
	}

	public TipoMovimentacao(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
}
