package br.com.live.comercial.model;

public class MetaOrcamentoPorMesAno {

	private String canal;
	private int mes;
	private int ano;
	private double valor;
	
	public MetaOrcamentoPorMesAno(String canal, int mes, int ano, double valor) {
		super();
		this.canal = canal;
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
	}

	public MetaOrcamentoPorMesAno() {
		super();
		this.canal = "";
		this.mes = 0;
		this.ano = 0;
		this.valor = 0;		
	}
	
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
}
