package br.com.live.model;

public class Embarque {

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
	
	public Embarque () {}
	
	public Embarque (long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
}
