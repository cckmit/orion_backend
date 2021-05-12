package br.com.live.model;

public class ConsultaDadosCompEstrutura {

	public String sub;
	public String item;
	public double consumo;
	public int alternativa;
	
	public ConsultaDadosCompEstrutura() {
		this.sub = "000";
		this.item = "000000";
		this.consumo = 0.0;
		this.alternativa = 0;		
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
	public double getConsumo() {
		return consumo;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}	
}
