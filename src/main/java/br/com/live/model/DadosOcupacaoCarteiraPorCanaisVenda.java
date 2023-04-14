package br.com.live.model;

public class DadosOcupacaoCarteiraPorCanaisVenda {

	private String canal;
	private int quantidade;
	private double valor;
	private double minutos;	
	private int quantidadeConfirmar;
	private double valorConfirmar;
	private double minutosConfirmar;	
	private double quantidadeOrcado;
	private double valorOrcado;
	private double minutosOrcado;
	
	public DadosOcupacaoCarteiraPorCanaisVenda(String canal, int quantidade, double valor, double minutos,
			int quantidadeConfirmar, double valorConfirmar, double minutosConfirmar, double quantidadeOrcado,
			double valorOrcado, double minutosOrcado) {
		super();
		this.canal = canal;
		this.quantidade = quantidade;
		this.valor = valor;
		this.minutos = minutos;
		this.quantidadeConfirmar = quantidadeConfirmar;
		this.valorConfirmar = valorConfirmar;
		this.minutosConfirmar = minutosConfirmar;
		this.quantidadeOrcado = quantidadeOrcado;
		this.valorOrcado = valorOrcado;
		this.minutosOrcado = minutosOrcado;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getMinutos() {
		return minutos;
	}

	public void setMinutos(double minutos) {
		this.minutos = minutos;
	}

	public int getQuantidadeConfirmar() {
		return quantidadeConfirmar;
	}

	public void setQuantidadeConfirmar(int quantidadeConfirmar) {
		this.quantidadeConfirmar = quantidadeConfirmar;
	}

	public double getValorConfirmar() {
		return valorConfirmar;
	}

	public void setValorConfirmar(double valorConfirmar) {
		this.valorConfirmar = valorConfirmar;
	}

	public double getMinutosConfirmar() {
		return minutosConfirmar;
	}

	public void setMinutosConfirmar(double minutosConfirmar) {
		this.minutosConfirmar = minutosConfirmar;
	}

	public double getQuantidadeOrcado() {
		return quantidadeOrcado;
	}

	public void setQuantidadeOrcado(double quantidadeOrcado) {
		this.quantidadeOrcado = quantidadeOrcado;
	}

	public double getValorOrcado() {
		return valorOrcado;
	}

	public void setValorOrcado(double valorOrcado) {
		this.valorOrcado = valorOrcado;
	}

	public double getMinutosOrcado() {
		return minutosOrcado;
	}

	public void setMinutosOrcado(double minutosOrcado) {
		this.minutosOrcado = minutosOrcado;
	}
}
