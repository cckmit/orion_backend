package br.com.live.model;

public class ConsultaDadosEstrutura {

	public int sequencia;
	public int qtdeCamadas; 
	public String subItem; 
	public String itemItem; 
	public String nivelComp; 
	public String grupoComp; 
	public String subComp; 
	public String itemComp; 
	public double consumo; 
	public int alternativaComp; 
	public double percPerdas;
	
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public int getQtdeCamadas() {
		return qtdeCamadas;
	}
	public void setQtdeCamadas(int qtdeCamadas) {
		this.qtdeCamadas = qtdeCamadas;
	}
	public String getSubItem() {
		return subItem;
	}
	public void setSubItem(String subItem) {
		this.subItem = subItem;
	}
	public String getItemItem() {
		return itemItem;
	}
	public void setItemItem(String itemItem) {
		this.itemItem = itemItem;
	}
	public String getNivelComp() {
		return nivelComp;
	}
	public void setNivelComp(String nivelComp) {
		this.nivelComp = nivelComp;
	}
	public String getGrupoComp() {
		return grupoComp;
	}
	public void setGrupoComp(String grupoComp) {
		this.grupoComp = grupoComp;
	}
	public String getSubComp() {
		return subComp;
	}
	public void setSubComp(String subComp) {
		this.subComp = subComp;
	}
	public String getItemComp() {
		return itemComp;
	}
	public void setItemComp(String itemComp) {
		this.itemComp = itemComp;
	}
	public double getConsumo() {
		return consumo;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	public int getAlternativaComp() {
		return alternativaComp;
	}
	public void setAlternativaComp(int alternativaComp) {
		this.alternativaComp = alternativaComp;
	}
	public double getPercPerdas() {
		return percPerdas;
	}
	public void setPercPerdas(double percPerdas) {
		this.percPerdas = percPerdas;
	}
}
