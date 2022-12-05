package br.com.live.model;

public class CapacidadeCotasVendasTipoCliente {

	private int tipoCliente;
	private String descricaoTipo;
	private int qtdePecas;
	private double valorBruto;
	private double valorLiqItens;
	private double valorLiqTotal;
	private double tempo;
	private double percentualPecas;
	private double percentualMinutos;
	private double percentualValor;
	
	public CapacidadeCotasVendasTipoCliente() {
		super();
	}

	public CapacidadeCotasVendasTipoCliente(int tipoCliente, String descricaoTipo, int qtdePecas, double valorBruto, double valorLiqItens,
			double valorLiqTotal, double tempo) {
		super();
		this.tipoCliente = tipoCliente;
		this.descricaoTipo = descricaoTipo;
		this.qtdePecas = qtdePecas;
		this.valorBruto = valorBruto;
		this.valorLiqItens = valorLiqItens;
		this.valorLiqTotal = valorLiqTotal;
		this.tempo = tempo;
	}

	public int getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getDescricaoTipo() {
		return descricaoTipo;
	}

	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}

	public int getQtdePecas() {
		return qtdePecas;
	}

	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}

	public double getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(double valorBruto) {
		this.valorBruto = valorBruto;
	}

	public double getValorLiqItens() {
		return valorLiqItens;
	}

	public void setValorLiqItens(double valorLiqItens) {
		this.valorLiqItens = valorLiqItens;
	}

	public double getValorLiqTotal() {
		return valorLiqTotal;
	}

	public void setValorLiqTotal(double valorLiqTotal) {
		this.valorLiqTotal = valorLiqTotal;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

	public double getPercentualPecas() {
		return percentualPecas;
	}

	public void setPercentualPecas(double percentualPecas) {
		this.percentualPecas = percentualPecas;
	}

	public double getPercentualMinutos() {
		return percentualMinutos;
	}

	public void setPercentualMinutos(double percentualMinutos) {
		this.percentualMinutos = percentualMinutos;
	}

	public double getPercentualValor() {
		return percentualValor;
	}

	public void setPercentualValor(double percentualValor) {
		this.percentualValor = percentualValor;
	}		
}
