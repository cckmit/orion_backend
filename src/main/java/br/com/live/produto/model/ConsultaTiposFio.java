package br.com.live.produto.model;

public class ConsultaTiposFio {
	int id;
	String descricao;
	String marca;
	int titulo;
	int centimetrosCone;
	int centimetrosCone2;
	int centimetrosCone3;
	
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
	public int getCentimetrosCone2() {
		return centimetrosCone2;
	}
	public void setCentimetrosCone2(int centimetrosCone2) {
		this.centimetrosCone2 = centimetrosCone2;
	}
	public int getCentimetrosCone3() {
		return centimetrosCone3;
	}
	public void setCentimetrosCone3(int centimetrosCone3) {
		this.centimetrosCone3 = centimetrosCone3;
	}
	public ConsultaTiposFio() {
		
	}
	
	public ConsultaTiposFio(int id, String descricao, String marca, int titulo, int centimetrosCone, int centimetrosCone2, int centimetrosCone3) {
		this.id = id;
		this.descricao = descricao;
		this.marca = marca;
		this.titulo = titulo;
		this.centimetrosCone = centimetrosCone;
		this.centimetrosCone2 = centimetrosCone2;
		this.centimetrosCone3 = centimetrosCone3;
	}
}
