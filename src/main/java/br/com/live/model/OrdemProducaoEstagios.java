package br.com.live.model;

public class OrdemProducaoEstagios {

	private int ordemProducao;
	private int seqEstagio; 
	private int codEstagio; 
	private int codEstagioAnterior; 
	private int codEstagioDepende; 
	private int qtdeAProduzir;
	
	public OrdemProducaoEstagios(int ordemProducao, int seqEstagio, int codEstagio, int codEstagioAnterior,
			int codEstagioDepende, int qtdeAProduzir) {
		super();
		this.ordemProducao = ordemProducao;
		this.seqEstagio = seqEstagio;
		this.codEstagio = codEstagio;
		this.codEstagioAnterior = codEstagioAnterior;
		this.codEstagioDepende = codEstagioDepende;
		this.qtdeAProduzir = qtdeAProduzir;
	}

	public int getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public int getSeqEstagio() {
		return seqEstagio;
	}

	public void setSeqEstagio(int seqEstagio) {
		this.seqEstagio = seqEstagio;
	}

	public int getCodEstagio() {
		return codEstagio;
	}

	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}

	public int getCodEstagioAnterior() {
		return codEstagioAnterior;
	}

	public void setCodEstagioAnterior(int codEstagioAnterior) {
		this.codEstagioAnterior = codEstagioAnterior;
	}

	public int getCodEstagioDepende() {
		return codEstagioDepende;
	}

	public void setCodEstagioDepende(int codEstagioDepende) {
		this.codEstagioDepende = codEstagioDepende;
	}

	public int getQtdeAProduzir() {
		return qtdeAProduzir;
	}

	public void setQtdeAProduzir(int qtdeAProduzir) {
		this.qtdeAProduzir = qtdeAProduzir;
	}
}
