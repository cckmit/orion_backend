package br.com.live.model;

public class Alternativa {

	public long id;
	public int alternativa;
	public String descricao;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
