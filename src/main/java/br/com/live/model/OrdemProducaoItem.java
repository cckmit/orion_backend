package br.com.live.model;

public class OrdemProducaoItem extends OrdemProducao {

	public String tamanho;
	public String cor;

	public OrdemProducaoItem() {
		this.tamanho = "";
		this.cor = "";
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
	
	public OrdemProducaoItem(int ordemProducao, String referencia, int Periodo, int nrAlternativa, int nrRoteiro, int qtdePecasProgramada, String tamanho, String cor) {
		super(ordemProducao, referencia, Periodo, nrAlternativa, nrRoteiro, qtdePecasProgramada);
		this.tamanho = tamanho;
		this.cor = cor;
	}
}
