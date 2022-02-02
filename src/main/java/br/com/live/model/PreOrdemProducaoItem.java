package br.com.live.model;

public class PreOrdemProducaoItem extends PreOrdemProducao {

	public String sub;
	public String item;
	public int qtdeProgramada;	
	
	public PreOrdemProducaoItem() {
		super();
	};
	
	public PreOrdemProducaoItem(int id, String grupo, int alternativa, int roteiro, int periodo, String sub, String item, int qtdeProgramada) {
		this.id = id; 
		this.grupo = grupo; 
		this.alternativa = alternativa; 
		this.roteiro = roteiro; 
		this.periodo = periodo; 
		this.sub = sub; 
		this.item = item; 
		this.qtdeProgramada = qtdeProgramada;
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
	public int getQtdeProgramada() {
		return qtdeProgramada;
	}
	public void setQtdeProgramada(int qtdeProgramada) {
		this.qtdeProgramada = qtdeProgramada;
	}
	
}
