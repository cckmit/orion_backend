package br.com.live.model;

public class EstagioCapacidadeProducao extends EstagioProducao {
	
	public int qtdePecas;
	public int qtdeMinutos;

	public int getQtdePecas() {
		return qtdePecas;
	}
	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}
	public int getQtdeMinutos() {
		return qtdeMinutos;
	}
	public void setQtdeMinutos(int qtdeMinutos) {
		this.qtdeMinutos = qtdeMinutos;
	}
}
