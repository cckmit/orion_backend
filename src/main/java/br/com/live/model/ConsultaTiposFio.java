package br.com.live.model;

public class ConsultaTiposFio {
	int id;
	String descricao;
	String marca;
	int titulo;
	int centimetrosCone;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getTitulo() {
		return titulo;
	}
	public void setTitulo(int titulo) {
		this.titulo = titulo;
	}
	public int getCentimetrosCone() {
		return centimetrosCone;
	}
	public void setCentimetrosCone(int centimetrosCone) {
		this.centimetrosCone = centimetrosCone;
	}
	
	public ConsultaTiposFio() {
		
	}
	
	public ConsultaTiposFio(int id, String descricao, String marca, int titulo, int centimetrosCone) {
		this.id = id;
		this.descricao = descricao;
		this.marca = marca;
		this.titulo = titulo;
		this.centimetrosCone = centimetrosCone;
	}
}
