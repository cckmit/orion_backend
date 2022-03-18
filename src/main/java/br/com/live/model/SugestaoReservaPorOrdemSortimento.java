package br.com.live.model;

public class SugestaoReservaPorOrdemSortimento extends SugestaoReservaPorTecido {

	private long idOrdem;
	private String sortimento;
	private int sequencia;
	private double qtdeNecessidadeUnit;
	private double qtdeNecessidadeRecalculada;
	private double qtdeDisponivelTecidoSubstituto;
	private boolean recalculado;	
	
	
	public SugestaoReservaPorOrdemSortimento(long idOrdem, String sortimento, int sequencia, String nivel, String grupo, String sub, String item, String descricao,
			String unidade, double qtdeNecessidade, double qtdeEstoque, double qtdeEmpenhada, double qtdeSugerido, double qtdeNecessidadeUnit) {
		super(nivel, grupo, sub, item, descricao, unidade, qtdeNecessidade, qtdeEstoque, qtdeEmpenhada, qtdeSugerido);
		this.idOrdem = idOrdem;
		this.sortimento = sortimento;
		this.sequencia = sequencia;
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit;
		this.recalculado = false;		
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
		double qtdeDispTecido = 0;
		double qtdeDispSubstituto = 0;
		
		if (this.qtdeDisponivel > 0 ) qtdeDispTecido = this.qtdeDisponivel;   
		if (this.qtdeDisponivelTecidoSubstituto > 0 ) qtdeDispSubstituto = this.qtdeDisponivelTecidoSubstituto;
		
		this.qtdeSaldo = (qtdeDispTecido + qtdeDispSubstituto) - this.qtdeSugerido;
	}
	
	public double getQtdeNecessidadeCalculada() {
		if (this.recalculado || this.qtdeNecessidadeRecalculada > 0) return this.qtdeNecessidadeRecalculada;
		else return this.getQtdeNecessidade();
	}
	
	public boolean isRecalculado() {
		return recalculado;
	}

	public void setRecalculado(boolean recalculado) {
		this.recalculado = recalculado;
	}

	public double getQtdeDisponivelTecidoSubstituto() {
		return qtdeDisponivelTecidoSubstituto;		
	}

	public void setQtdeDisponivelTecidoSubstituto(double qtdeTecidoSubstituto) {
		this.qtdeDisponivelTecidoSubstituto = qtdeTecidoSubstituto;
		acertaSaldo();
	}
}
