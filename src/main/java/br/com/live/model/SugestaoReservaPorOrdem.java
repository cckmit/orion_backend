package br.com.live.model;

public class SugestaoReservaPorOrdem extends SugestaoReservaPorTecido {

	private long idOrdem;
	private double qtdeNecessidadeUnit;
	
	public SugestaoReservaPorOrdem(long idOrdem, String nivel, String grupo, String sub, String item, String descricao,
			String unidade, double qtdeNecessidade, double qtdeDisponivel, double qtdeSugerido, double qtdeSaldo, double qtdeNecessidadeUnit) {
		super(nivel, grupo, sub, item, descricao, unidade, qtdeNecessidade, qtdeDisponivel, qtdeSugerido, qtdeSaldo);
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
