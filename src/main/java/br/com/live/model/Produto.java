package br.com.live.model;

public class Produto {

	public String nivel;
	public String grupo;
	public String sub;
	public String item;
	public String narrativa;	
	public int qtdePrevisaoVendas;	
	
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
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
	public String getNarrativa() {
		return narrativa;
	}
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}
	public int getQtdePrevisaoVendas() {
		return qtdePrevisaoVendas;
	}
	public void setQtdePrevisaoVendas(int qtdePrevisaoVendas) {
		this.qtdePrevisaoVendas = qtdePrevisaoVendas;
	}
}
