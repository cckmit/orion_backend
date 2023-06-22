package br.com.live.comercial.model;

public class ImportarTabPreco {
	public int id;
	public String referencia;
	public String cor;
	public float valor;
	public int status;
	public String descricao;
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public ImportarTabPreco() {
		
	}
	
	public ImportarTabPreco(int id, String referencia, String cor, float valor, int status, String descricao) {
		this.id =id;
		this.referencia = referencia;
		this.cor = cor;
		this.valor = valor;
		this.status = status;
		this.descricao = descricao;
	}
}
