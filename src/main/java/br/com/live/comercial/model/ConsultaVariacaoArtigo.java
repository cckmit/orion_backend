package br.com.live.comercial.model;

public class ConsultaVariacaoArtigo {
	public long id;
	public int artigo;
	public String descricao;
	public float variacao;
	
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
	public float getVariacao() {
		return variacao;
	}
	public void setVariacao(float variacao) {
		this.variacao = variacao;
	}
	public int getArtigo() {
		return artigo;
	}
	public void setArtigo(int artigo) {
		this.artigo = artigo;
	}
	public ConsultaVariacaoArtigo() {
		
	}

	public ConsultaVariacaoArtigo(long id, String descricao, float variacao, int artigo) {
		this.id = id;
		this.descricao = descricao;
		this.variacao = variacao;
		this.artigo = artigo;
	}
}
