package br.com.live.model;

public class OrdemConfeccao extends OrdemProducao {

	public int ordemConfeccao;
	public String tamanho;
	public String cor;
	public int qtdePecas;
	
	public int getOrdemConfeccao() {
		return ordemConfeccao;
	}
	public void setOrdemConfeccao(int ordemConfeccao) {
		this.ordemConfeccao = ordemConfeccao;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public int getQtdePecas() {
		return qtdePecas;
	}
	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}
	
	public OrdemConfeccao() {
		super();
		this.ordemConfeccao = 0;
		this.tamanho="";
		this.cor="";
		this.qtdePecas=0;
	}	
}
