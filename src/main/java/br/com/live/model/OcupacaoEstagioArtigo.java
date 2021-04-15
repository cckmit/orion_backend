package br.com.live.model;

public class OcupacaoEstagioArtigo {

	public int estagio;
	public int artigo;
	public int qtdePecas;
	public double qtdeMinutos;
	
	public OcupacaoEstagioArtigo() {
		this.estagio = 0;
		this.artigo = 0;
		this.qtdePecas = 0;
		this.qtdeMinutos = 0.000;
	}
	
	public int getEstagio() {
		return estagio;
	}
	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}
	public int getArtigo() {
		return artigo;
	}
	public void setArtigo(int artigo) {
		this.artigo = artigo;
	}
	public int getQtdePecas() {
		return qtdePecas;
	}
	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}
	public double getQtdeMinutos() {
		return qtdeMinutos;
	}
	public void setQtdeMinutos(double qtdeMinutos) {
		this.qtdeMinutos = qtdeMinutos;
	}
		
}
