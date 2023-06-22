package br.com.live.comercial.model;

public class ConsultaColecoesAgrupador {
	public String id;
	public int codAgrupador;
	public int colecao;
	public int subColecao;
	public String descColecao;
	public String descSubColecao;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCodAgrupador() {
		return codAgrupador;
	}
	public void setCodAgrupador(int codAgrupador) {
		this.codAgrupador = codAgrupador;
	}
	public int getColecao() {
		return colecao;
	}
	public void setColecao(int colecao) {
		this.colecao = colecao;
	}
	public int getSubColecao() {
		return subColecao;
	}
	public void setSubColecao(int subColecao) {
		this.subColecao = subColecao;
	}
	public String getDescColecao() {
		return descColecao;
	}
	public void setDescColecao(String descColecao) {
		this.descColecao = descColecao;
	}
	public String getDescSubColecao() {
		return descSubColecao;
	}
	public void setDescSubColecao(String descSubColecao) {
		this.descSubColecao = descSubColecao;
	}
	
	public ConsultaColecoesAgrupador() {
		
	}
	
	public ConsultaColecoesAgrupador(String id, int codAgrupador, int colecao, int subColecao, String descColecao,
			String descSubColecao) {
		this.id = id;
		this.codAgrupador = codAgrupador;
		this.colecao = colecao;
		this.subColecao = subColecao;
		this.descColecao = descColecao;
		this.descSubColecao = descSubColecao;
	}
}
