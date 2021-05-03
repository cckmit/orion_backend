package br.com.live.model;

public class ConsultaPrevisaoVendasItemTam {

	public String id;
	public long idPrevisaoVendas;
	public String idItemPrevisaoVendas;	
	public String sub;	
	public int ordem;
	public int qtdePrevisao;	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getIdPrevisaoVendas() {
		return idPrevisaoVendas;
	}
	public void setIdPrevisaoVendas(long idPrevisaoVendas) {
		this.idPrevisaoVendas = idPrevisaoVendas;
	}
	public String getIdItemPrevisaoVendas() {
		return idItemPrevisaoVendas;
	}
	public void setIdItemPrevisaoVendas(String idItemPrevisaoVendas) {
		this.idItemPrevisaoVendas = idItemPrevisaoVendas;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public int getQtdePrevisao() {
		return qtdePrevisao;
	}
	public void setQtdePrevisao(int qtdePrevisao) {
		this.qtdePrevisao = qtdePrevisao;
	}
	
}
