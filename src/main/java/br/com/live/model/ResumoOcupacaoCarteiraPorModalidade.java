package br.com.live.model;

public class ResumoOcupacaoCarteiraPorModalidade {
	private String modalidade;
	private double valorOrcado;
	private double valorReal;
	private double valorConfirmar;
	private double valorTotal;
	private double percentual;
	
	public ResumoOcupacaoCarteiraPorModalidade(String modalidade, double valorOrcado, double valorReal,
			double valorConfirmar, double valorTotal, double percentual) {
		super();
		this.modalidade = modalidade;
		this.valorOrcado = valorOrcado;
		this.valorReal = valorReal;
		this.valorConfirmar = valorConfirmar;
		this.valorTotal = valorTotal;
		this.percentual = percentual;
		this.percentual = percentual;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
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
