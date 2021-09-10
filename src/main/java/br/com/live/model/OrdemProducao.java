package br.com.live.model;

public class OrdemProducao {

	public int ordemProducao;
	public String referencia;
	public int periodo;
	public int qtdePecasProgramada;
	public int nrAlternativa;

	public int getNrAlternativa() {
		return nrAlternativa;
	}
	public void setNrAlternativa(int nrAlternativa) {
		this.nrAlternativa = nrAlternativa;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}	
	public int getPeriodo() {
		return periodo;
	}
	public int getQtdePecasProgramada() {
		return qtdePecasProgramada;
	}
	public void setQtdePecasProgramada(int qtdePecasProgramada) {
		this.qtdePecasProgramada = qtdePecasProgramada;
	}

	public OrdemProducao() {
		this.ordemProducao = 0;
		this.referencia = "";
		this.periodo = 0;
		this.qtdePecasProgramada = 0;
	}	
}
