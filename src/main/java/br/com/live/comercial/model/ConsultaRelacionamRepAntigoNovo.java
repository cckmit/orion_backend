package br.com.live.comercial.model;


public class ConsultaRelacionamRepAntigoNovo {
	
	public int id;
	public String represAntigo;
	public String represNovo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRepresAntigo() {
		return represAntigo;
	}
	public void setRepresAntigo(String represAntigo) {
		this.represAntigo = represAntigo;
	}
	public String getRepresNovo() {
		return represNovo;
	}
	public void setRepresNovo(String represNovo) {
		this.represNovo = represNovo;
	}
	
	public ConsultaRelacionamRepAntigoNovo() {
		
	}
	public ConsultaRelacionamRepAntigoNovo(int id, String represAntigo, String represNovo) {
		
		this.id = id;
		this.represAntigo = represAntigo;
		this.represNovo = represNovo;
	}

	
}
