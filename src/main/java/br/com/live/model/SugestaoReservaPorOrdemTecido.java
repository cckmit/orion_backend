package br.com.live.model;

public class SugestaoReservaPorOrdemTecido extends SugestaoReservaPorTecido {

	private long idOrdem;
	private double qtdeNecessidadeUnit;
	
	public SugestaoReservaPorOrdemTecido(long idOrdem, String nivel, String grupo, String sub, String item, String descricao,
			String unidade, double qtdeNecessidadeUnit, double qtdeNecessidade, double qtdeEstoque, double qtdeEmpenhada, double qtdeSugerido) {
		super(nivel, grupo, sub, item, descricao, unidade, qtdeNecessidade, qtdeEstoque, qtdeEmpenhada, qtdeSugerido);
		this.idOrdem = idOrdem;
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit; 
	}
	public long getIdOrdem() {
		return idOrdem;
	}
	public void setIdOrdem(long idOrdem) {
		this.idOrdem = idOrdem;
	}
	public double getQtdeNecessidadeUnit() {
		return qtdeNecessidadeUnit;
	}
	public void setQtdeNecessidadeUnit(double qtdeNecessidadeUnit) {
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit;
	}
}
