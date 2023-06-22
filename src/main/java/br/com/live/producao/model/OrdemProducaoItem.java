package br.com.live.producao.model;

public class OrdemProducaoItem extends OrdemProducao {

	public int ordemTamanho;
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
	public int getOrdemTamanho() {
		return ordemTamanho;
	}
	public void setOrdemTamanho(int ordemTamanho) {
		this.ordemTamanho = ordemTamanho;
	}

	public OrdemProducaoItem(int ordemProducao, String referencia, int Periodo, int nrAlternativa, int nrRoteiro, int qtdePecasProgramada, String tamanho, String cor, int ordemTamanho) {
		super(ordemProducao, referencia, Periodo, nrAlternativa, nrRoteiro, qtdePecasProgramada);
		this.tamanho = tamanho;
		this.cor = cor;
		this.ordemTamanho = ordemTamanho;
	}
}
