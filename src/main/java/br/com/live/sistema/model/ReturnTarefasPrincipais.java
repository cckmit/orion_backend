package br.com.live.sistema.model;

public class ReturnTarefasPrincipais {
	int id;
	String origem;
	String titulo;
	float horas;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public float getHoras() {
		return horas;
	}
	public void setHoras(float horas) {
		this.horas = horas;
	}
	
	public ReturnTarefasPrincipais() {
		
	}
	
	public ReturnTarefasPrincipais(int id, String origem, String titulo, float horas) {
		this.id = id;
		this.origem = origem;
		this.titulo = titulo;
		this.horas = horas;
	}
}
