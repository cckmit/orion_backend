package br.com.live.model;

public class ItemAColetarPorPedido {

	private int pedido;
	private String nivel;
	private String grupo;
	private String sub;
	private String item;
	private String endereco;
	private int deposito;
	private int qtdeColetar;
	
	public ItemAColetarPorPedido(int pedido, String nivel, String grupo, String sub, String item, String endereco, int deposito, int qtdeColetar) {
		super();
		this.pedido = pedido;
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.endereco = endereco;
		this.deposito = deposito;
		this.qtdeColetar = qtdeColetar;
	}

	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

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

	public int getQtdeColetar() {
		return qtdeColetar;
	}

	public void setQtdeColetar(int qtdeColetar) {
		this.qtdeColetar = qtdeColetar;
	}	
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getDeposito() {
		return deposito;
	}

	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}	
}
