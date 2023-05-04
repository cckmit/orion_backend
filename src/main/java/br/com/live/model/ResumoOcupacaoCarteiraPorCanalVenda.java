package br.com.live.model;

public class ResumoOcupacaoCarteiraPorCanalVenda {
	private String canal;
	private double valorOrcado;
	private double valorReal;
	private double valorConfirmar;
	private double valorTotal;
	private double percentual;
	
	public ResumoOcupacaoCarteiraPorCanalVenda() {
		super();
		this.canal = "";
		this.valorOrcado = 0;
		this.valorReal = 0;
		this.valorConfirmar = 0;
		this.valorTotal = 0;
		this.percentual = 0;		
	}
	
	public ResumoOcupacaoCarteiraPorCanalVenda(String canal, double valorOrcado, double valorReal,
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
