package br.com.live.model;

public class ProdutosCapacidadeProd {

	private String referencia;
	private String tamanho;
	private String cor;	
	private String descricao;
	private String categoria;
	private float tempoUnitario;	
	private int qtdeEstoque; 
	private int qtdeDemanda; 
	private int qtdeProcesso;  
	private int qtdeSaldo; 
	private float qtdeMinutos; 
	private int qtdePecas; 
	private int bloqueioVenda; 
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public float getTempoUnitario() {
		return tempoUnitario;
	}
	public void setTempoUnitario(float tempoUnitario) {
		this.tempoUnitario = tempoUnitario;
	}
	public int getQtdeEstoque() {
		return qtdeEstoque;
	}
	public void setQtdeEstoque(int qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}
	public int getQtdeDemanda() {
		return qtdeDemanda;
	}
	public void setQtdeDemanda(int qtdeDemanda) {
		this.qtdeDemanda = qtdeDemanda;
	}
	public int getQtdeProcesso() {
		return qtdeProcesso;
	}
	public void setQtdeProcesso(int qtdeProcesso) {
		this.qtdeProcesso = qtdeProcesso;
	}
	public int getQtdeSaldo() {
		return qtdeSaldo;
	}
	public void setQtdeSaldo(int qtdeSaldo) {
		this.qtdeSaldo = qtdeSaldo;
	}
	public float getQtdeMinutos() {
		return qtdeMinutos;
	}
	public void setQtdeMinutos(float qtdeMinutos) {
		this.qtdeMinutos = qtdeMinutos;
	}
	public int getQtdePecas() {
		return qtdePecas;
	}
	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}
	public int getBloqueioVenda() {
		return bloqueioVenda;
	}
	public void setBloqueioVenda(int bloqueioVenda) {
		this.bloqueioVenda = bloqueioVenda;
	}
}
