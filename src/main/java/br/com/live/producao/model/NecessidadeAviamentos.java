package br.com.live.producao.model;

public class NecessidadeAviamentos {

	private int sequencia;
	private String nivel;
	private String grupo;
	private String sub;
	private String item;	
	private double quantidade;
	private double quantidadeUnit;
	
	public NecessidadeAviamentos(int sequencia, String nivel, String grupo, String sub, String item,
			double quantidade, double quantidadeUnit) {
		super();
		this.sequencia = sequencia;
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.quantidade = quantidade;
		this.quantidadeUnit = quantidadeUnit;
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

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getQuantidadeUnit() {
		return quantidadeUnit;
	}

	public void setQuantidadeUnit(double quantidadeUnit) {
		this.quantidadeUnit = quantidadeUnit;
	}
}