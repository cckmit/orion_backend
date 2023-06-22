package br.com.live.comercial.model;

public class ResumoOcupacaoCarteira {
	protected double valorOrcado;
	protected double valorReal;
	protected double valorConfirmar;
	protected double valorTotal;
	protected double percentual;
	
	public ResumoOcupacaoCarteira() {
		super();
		this.valorOrcado = 0;
		this.valorReal = 0;
		this.valorConfirmar = 0;
		this.valorTotal = 0;
		this.percentual = 0;		
	}	
	public ResumoOcupacaoCarteira(double valorOrcado, double valorReal, double valorConfirmar) {
		super();		
		this.valorOrcado = valorOrcado;
		this.valorReal = valorReal;
		this.valorConfirmar = valorConfirmar;
		somarTotal();
	}
	public double getValorOrcado() {
		return valorOrcado;
	}
	public void setValorOrcado(double valorOrcado) {		
		this.valorOrcado = valorOrcado;
		this.somarTotal();
	}
	public double getValorReal() {
		return valorReal;
	}
	public void setValorReal(double valorReal) {
		this.valorReal = valorReal;
		this.somarTotal();
	}
	public double getValorConfirmar() {
		return valorConfirmar;
	}
	public void setValorConfirmar(double valorConfirmar) {
		this.valorConfirmar = valorConfirmar;
		this.somarTotal();
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
	private void somarTotal() {
		this.valorTotal = this.valorReal + this.valorConfirmar;
		if (this.valorOrcado > 0)
			this.percentual = (this.valorTotal / this.valorOrcado) * 100;
		else this.percentual = 0;
	}
}
