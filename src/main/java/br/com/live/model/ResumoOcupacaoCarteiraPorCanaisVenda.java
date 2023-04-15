package br.com.live.model;

public class ResumoOcupacaoCarteiraPorCanaisVenda {
	private String canal;
	private double valorOrcado;
	private double valorReal;
	private double valorConfirmar;
	private double valorTotal;
	private double percentual;
	
	public ResumoOcupacaoCarteiraPorCanaisVenda(String canal, double valorOrcado, double valorReal,
			double valorConfirmar, double valorTotal, double percentual) {
		super();
		this.canal = canal;
		this.valorOrcado = valorOrcado;
		this.valorReal = valorReal;
		this.valorConfirmar = valorConfirmar;
		this.valorTotal = valorTotal;
		this.percentual = percentual;
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public double getValorOrcado() {
		return valorOrcado;
	}
	public void setValorOrcado(double valorOrcado) {
		this.valorOrcado = valorOrcado;
	}
	public double getValorReal() {
		return valorReal;
	}
	public void setValorReal(double valorReal) {
		this.valorReal = valorReal;
	}
	public double getValorConfirmar() {
		return valorConfirmar;
	}
	public void setValorConfirmar(double valorConfirmar) {
		this.valorConfirmar = valorConfirmar;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getPercentual() {
		return percentual;
	}
	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}
}
