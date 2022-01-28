package br.com.live.model;

public class SugestaoReservaPorTecido {

	private String nivel;
	private String grupo;
	private String sub;
	private String item;
	private String descricao;
	private String unidade;	
	private Double qtdeNecessidade;
	private Double qtdeDisponivel;
	private Double qtdeSugerido;
	private Double qtdeSaldo;

	public SugestaoReservaPorTecido(String nivel, String grupo, String sub, String item, String descricao, String unidade, double qtdeNecessidade, double qtdeDisponivel, double qtdeSugerido, double qtdeSaldo) {
		this.nivel = nivel; 
		this.grupo = grupo; 
		this.sub = sub; 
		this.item = item; 
		this.descricao = descricao;
		this.unidade = unidade;
		this.qtdeNecessidade = qtdeNecessidade; 
		this.qtdeDisponivel = qtdeDisponivel;
		this.qtdeSugerido = qtdeSugerido;
		this.qtdeSaldo = qtdeSaldo;
	}
	
	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
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

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Double getQtdeNecessidade() {
		return qtdeNecessidade;
	}

	public void setQtdeNecessidade(Double qtdeNecessidade) {
		this.qtdeNecessidade = qtdeNecessidade;
	}

	public Double getQtdeDisponivel() {
		return qtdeDisponivel;
	}

	public void setQtdeDisponivel(Double qtdeDisponivel) {
		this.qtdeDisponivel = qtdeDisponivel;
	}

	public Double getQtdeSugerido() {
		return qtdeSugerido;
	}

	public void setQtdeSugerido(Double qtdeSugerido) {
		this.qtdeSugerido = qtdeSugerido;
	}

	public Double getQtdeSaldo() {
		return qtdeSaldo;
	}

	public void setQtdeSaldo(Double qtdeSaldo) {
		this.qtdeSaldo = qtdeSaldo;
	}	
}
