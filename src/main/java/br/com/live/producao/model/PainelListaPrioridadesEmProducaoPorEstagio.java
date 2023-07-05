package br.com.live.producao.model;

public class PainelListaPrioridadesEmProducaoPorEstagio {
	private String id;
	private int seqFila;
	private int codEstagio;
	private String descEstagio;
	private int qtdeEmProducaoOP;
	private int qtdeEmProducaoEstagio;
	private double percentualQtdeEmProducao;
	private int qtdeNecessaria;
	private double percentualQtdeNecessaria;
	
	public PainelListaPrioridadesEmProducaoPorEstagio() {
		super();
	}

	public PainelListaPrioridadesEmProducaoPorEstagio(String id, int seqFila, int codEstagio, String descEstagio, int qtdeEmProducaoOP,
			int qtdeEmProducaoEstagio, double percentualQtdeEmProducao, int qtdeNecessaria,
			double percentualQtdeNecessaria) {
		super();
		this.id = id;
		this.seqFila = seqFila;
		this.codEstagio = codEstagio;
		this.descEstagio = descEstagio;
		this.qtdeEmProducaoOP = qtdeEmProducaoOP;
		this.qtdeEmProducaoEstagio = qtdeEmProducaoEstagio;
		this.percentualQtdeEmProducao = percentualQtdeEmProducao;
		this.qtdeNecessaria = qtdeNecessaria;
		this.percentualQtdeNecessaria = percentualQtdeNecessaria;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeqFila() {
		return seqFila;
	}

	public void setSeqFila(int seqFila) {
		this.seqFila = seqFila;
	}

	public int getCodEstagio() {
		return codEstagio;
	}

	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}

	public String getDescEstagio() {
		return descEstagio;
	}

	public void setDescEstagio(String descEstagio) {
		this.descEstagio = descEstagio;
	}

	public int getQtdeEmProducaoOP() {
		return qtdeEmProducaoOP;
	}

	public void setQtdeEmProducaoOP(int qtdeEmProducaoOP) {
		this.qtdeEmProducaoOP = qtdeEmProducaoOP;
	}

	public int getQtdeEmProducaoEstagio() {
		return qtdeEmProducaoEstagio;
	}

	public void setQtdeEmProducaoEstagio(int qtdeEmProducaoEstagio) {
		this.qtdeEmProducaoEstagio = qtdeEmProducaoEstagio;
	}

	public double getPercentualQtdeEmProducao() {
		return percentualQtdeEmProducao;
	}

	public void setPercentualQtdeEmProducao(double percentualQtdeEmProducao) {
		this.percentualQtdeEmProducao = percentualQtdeEmProducao;
	}

	public int getQtdeNecessaria() {
		return qtdeNecessaria;
	}

	public void setQtdeNecessaria(int qtdeNecessaria) {
		this.qtdeNecessaria = qtdeNecessaria;
	}

	public double getPercentualQtdeNecessaria() {
		return percentualQtdeNecessaria;
	}

	public void setPercentualQtdeNecessaria(double percentualQtdeNecessaria) {
		this.percentualQtdeNecessaria = percentualQtdeNecessaria;
	}
}
