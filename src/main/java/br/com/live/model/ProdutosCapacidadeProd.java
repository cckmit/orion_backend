package br.com.live.model;

public class ProdutosCapacidadeProd {
	
	private String modelo;
	private String descricao;
	private String categoria;
	private float tempoUnitario;
	private int minutos;
	private int pecas;
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getPecas() {
		return pecas;
	}
	public void setPecas(int pecas) {
		this.pecas = pecas;
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
	
	
}
