package br.com.live.comercial.model;

public class ConsultaItemSugestaoCancelProducao {

	private String grupo;
	private String item;
	private String descricao;
	private int colecao;
	private String descColecao;
	private String sugCancelProducao;
		
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	public int getColecao() {
		return colecao;
	}
	public void setColecao(int colecao) {
		this.colecao = colecao;
	}
	public String getDescColecao() {
		return descColecao;
	}
	public void setDescColecao(String descColecao) {
		this.descColecao = descColecao;
	}
	public String getSugCancelProducao() {
		return sugCancelProducao;
	}
	public void setSugCancelProducao(String sugCancelProducao) {
		this.sugCancelProducao = sugCancelProducao;
	}
}
