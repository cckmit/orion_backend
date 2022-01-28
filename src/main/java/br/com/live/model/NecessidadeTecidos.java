package br.com.live.model;

public class NecessidadeTecidos {

	private int sequencia;
	private String nivel;
	private String grupo;
	private String sub;
	private String item;	
	private double qtdeKg;
	private double qtdeMetros;
	
	public NecessidadeTecidos(int sequencia, String nivel, String grupo, String sub, String item, double qtdeKg, double qtdeMetros) {
		this.sequencia = sequencia;
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.qtdeKg = qtdeKg;
		this.qtdeMetros = qtdeMetros;
	}
	
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
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
	public double getQtdeKg() {
		return qtdeKg;
	}
	public void setQtdeKg(double qtdeKg) {
		this.qtdeKg = qtdeKg;
	}
	public double getQtdeMetros() {
		return qtdeMetros;
	}
	public void setQtdeMetros(double qtdeMetros) {
		this.qtdeMetros = qtdeMetros;
	}	
}
