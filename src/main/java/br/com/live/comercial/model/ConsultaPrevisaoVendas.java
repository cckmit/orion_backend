package br.com.live.comercial.model;

public class ConsultaPrevisaoVendas {

	public long id;
	public String descricao;
	public String descricaoColecao;
	public String descricaoTabPrecoSellIn;
	public String descricaoTabPrecoSellOut;
	
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
	public String getDescricaoColecao() {
		return descricaoColecao;
	}
	public void setDescricaoColecao(String descricaoColecao) {
		this.descricaoColecao = descricaoColecao;
	}
	public String getDescricaoTabPrecoSellIn() {
		return descricaoTabPrecoSellIn;
	}
	public void setDescricaoTabPrecoSellIn(String descricaoTabPrecoSellIn) {
		this.descricaoTabPrecoSellIn = descricaoTabPrecoSellIn;
	}
	public String getDescricaoTabPrecoSellOut() {
		return descricaoTabPrecoSellOut;
	}
	public void setDescricaoTabPrecoSellOut(String descricaoTabPrecoSellOut) {
		this.descricaoTabPrecoSellOut = descricaoTabPrecoSellOut;
	}	
}
