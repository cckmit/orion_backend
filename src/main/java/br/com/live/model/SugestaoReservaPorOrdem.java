package br.com.live.model;

public class SugestaoReservaPorOrdem extends SugestaoReservaPorTecido {

	private int prioridade;
	private long idOrdem;
	private String sortimento;
	private int sequencia;
	private double qtdeNecessidadeUnit;
	private double qtdeNecessidadeRecalculada;
	
	public SugestaoReservaPorOrdem(int prioridade, long idOrdem, String sortimento, int sequencia, String nivel, String grupo, String sub, String item, String descricao,
			String unidade, double qtdeNecessidade, double qtdeEstoque, double qtdeEmpenhada, double qtdeSugerido, double qtdeNecessidadeUnit) {
		super(nivel, grupo, sub, item, descricao, unidade, qtdeNecessidade, qtdeEstoque, qtdeEmpenhada, qtdeSugerido);
		this.prioridade = prioridade;
		this.idOrdem = idOrdem;
		this.sortimento = sortimento;
		this.sequencia = sequencia;
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit;
	}
	
	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
		
	public long getIdOrdem() {
		return idOrdem;
	}

	public void setIdOrdem(long idOrdem) {
		this.idOrdem = idOrdem;
	}

	public String getSortimento() {
		return sortimento;
	}

	public void setSortimento(String sortimento) {
		this.sortimento = sortimento;
	}	

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
		
	public double getQtdeNecessidadeUnit() {
		return qtdeNecessidadeUnit;
	}

	public void setQtdeNecessidadeUnit(double qtdeNecessidadeUnit) {
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit;
	}
	
	public double getQtdeNecessidadeRecalculada() {
		return qtdeNecessidadeRecalculada;
	}

	public void setQtdeNecessidadeRecalculada(double qtdeNecessidadeRecalculada) {
		this.qtdeNecessidadeRecalculada = qtdeNecessidadeRecalculada;
	}
		
	public void setQtdeDisponivel(double qtdeDisponivel) {
		this.qtdeDisponivel = qtdeDisponivel;
		acertaSaldo();
	}
	
	public void setQtdeSugerido(double qtdeSugerido) {
		this.qtdeSugerido = qtdeSugerido;
		acertaSaldo();
	}	
	
	private void acertaSaldo() {
		this.qtdeSaldo = this.qtdeDisponivel - this.qtdeSugerido;
	}
	
	public double getQtdeNecessidadeDisponivel() {
		if (this.qtdeNecessidadeRecalculada > 0) return this.qtdeNecessidadeRecalculada;
		else return this.getQtdeNecessidade();
	}
}
