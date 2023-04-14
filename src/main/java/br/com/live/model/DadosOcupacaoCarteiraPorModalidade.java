package br.com.live.model;

public class DadosOcupacaoCarteiraPorModalidade {
	private String modalidade;
	private double valorOrcado;
	private double valorReal;
	private double valorConfirmar;
	private double qtdeOrcado;
	private double qtdeReal;
	private double qtdeConfirmar;	
	private double minutosOrcado;
	private double minutosqtdeReal;
	private double minutosqtdeConfirmar;
	
	public DadosOcupacaoCarteiraPorModalidade(String modalidade, double valorOrcado, double valorReal,
			double valorConfirmar, double qtdeOrcado, double qtdeReal, double qtdeConfirmar, double minutosOrcado,
			double minutosqtdeReal, double minutosqtdeConfirmar) {
		super();
		this.modalidade = modalidade;
		this.valorOrcado = valorOrcado;
		this.valorReal = valorReal;
		this.valorConfirmar = valorConfirmar;
		this.qtdeOrcado = qtdeOrcado;
		this.qtdeReal = qtdeReal;
		this.qtdeConfirmar = qtdeConfirmar;
		this.minutosOrcado = minutosOrcado;
		this.minutosqtdeReal = minutosqtdeReal;
		this.minutosqtdeConfirmar = minutosqtdeConfirmar;
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

	public double getQtdeOrcado() {
		return qtdeOrcado;
	}

	public void setQtdeOrcado(double qtdeOrcado) {
		this.qtdeOrcado = qtdeOrcado;
	}

	public double getQtdeReal() {
		return qtdeReal;
	}

	public void setQtdeReal(double qtdeReal) {
		this.qtdeReal = qtdeReal;
	}

	public double getQtdeConfirmar() {
		return qtdeConfirmar;
	}

	public void setQtdeConfirmar(double qtdeConfirmar) {
		this.qtdeConfirmar = qtdeConfirmar;
	}

	public double getMinutosOrcado() {
		return minutosOrcado;
	}

	public void setMinutosOrcado(double minutosOrcado) {
		this.minutosOrcado = minutosOrcado;
	}

	public double getMinutosqtdeReal() {
		return minutosqtdeReal;
	}

	public void setMinutosqtdeReal(double minutosqtdeReal) {
		this.minutosqtdeReal = minutosqtdeReal;
	}

	public double getMinutosqtdeConfirmar() {
		return minutosqtdeConfirmar;
	}

	public void setMinutosqtdeConfirmar(double minutosqtdeConfirmar) {
		this.minutosqtdeConfirmar = minutosqtdeConfirmar;
	}
}
