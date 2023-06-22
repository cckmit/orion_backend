package br.com.live.comercial.model;

public class ConsultaSugestaoColetaPorLoteArea {

	private long idLoteArea;
	private long idLote;
	private long idArea;
	private String descricaoArea;
	private int qtdePedidos;
	private int qtdePecas;
	private int qtdeSkus;
	private int qtdeEnderecos;
	private String coletores;
	private int qtdePedidosArea;
	
	public ConsultaSugestaoColetaPorLoteArea() {
		super();
	}

	public ConsultaSugestaoColetaPorLoteArea(long idLoteArea, long idLote, long idArea, String descricaoArea, int qtdePedidos,
			int qtdePecas, int qtdeSkus, int qtdeEnderecos, String coletores, int qtdePedidosArea) {
		super();
		this.idLoteArea = idLoteArea;
		this.idLote = idLote;
		this.idArea = idArea;
		this.descricaoArea = descricaoArea;
		this.qtdePedidos = qtdePedidos;
		this.qtdePecas = qtdePecas;
		this.qtdeSkus = qtdeSkus;
		this.qtdeEnderecos = qtdeEnderecos;
		this.coletores = coletores;
		this.qtdePedidosArea = qtdePedidosArea;
	}
	
	public long getIdLoteArea() {
		return idLoteArea;
	}

	public void setIdLoteArea(long idLoteArea) {
		this.idLoteArea = idLoteArea;
	}

	public long getIdLote() {
		return idLote;
	}

	public void setIdLote(long idLote) {
		this.idLote = idLote;
	}

	public long getIdArea() {
		return idArea;
	}

	public void setIdArea(long idArea) {
		this.idArea = idArea;
	}

	public String getDescricaoArea() {
		return descricaoArea;
	}

	public void setDescricaoArea(String descricaoArea) {
		this.descricaoArea = descricaoArea;
	}

	public int getQtdePedidos() {
		return qtdePedidos;
	}

	public void setQtdePedidos(int qtdePedidos) {
		this.qtdePedidos = qtdePedidos;
	}

	public int getQtdePecas() {
		return qtdePecas;
	}

	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}

	public int getQtdeSkus() {
		return qtdeSkus;
	}

	public void setQtdeSkus(int qtdeSkus) {
		this.qtdeSkus = qtdeSkus;
	}

	public int getQtdeEnderecos() {
		return qtdeEnderecos;
	}

	public void setQtdeEnderecos(int qtdeEnderecos) {
		this.qtdeEnderecos = qtdeEnderecos;
	}

	public String getColetores() {
		return coletores;
	}

	public void setColetores(String coletores) {
		this.coletores = coletores;
	}

	public int getQtdePedidosArea() {
		return qtdePedidosArea;
	}

	public void setQtdePedidosArea(int qtdePedidosArea) {
		this.qtdePedidosArea = qtdePedidosArea;
	}
}
