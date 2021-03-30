package br.com.live.model;

public class PreOrdemProducaoItem extends PreOrdemProducao {

	public String sub;
	public String item;
	public int qtdeProgramada;	
	
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
	public int getQtdeProgramada() {
		return qtdeProgramada;
	}
	public void setQtdeProgramada(int qtdeProgramada) {
		this.qtdeProgramada = qtdeProgramada;
	}
	
}
