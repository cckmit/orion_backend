package br.com.live.model;

import java.util.Date;

public class OrdemProducao implements Comparable<OrdemProducao> {

	public int ordemProducao;
	public String referencia;
	public int periodo;
	public int qtdePecasProgramada;
	public int nrAlternativa;
	public int nrRoteiro;
	public String descrReferencia;
	public Date dataEmbarque;
	public int qtdeEstagioCritico;
	public double tempoProducaoUnit;
	public double tempoCosturaUnit;	
	private int qtdeMaxPecasReservaTecidoAtende;
	private int qtdeMaxPecasReservaAviamentoAtende;
	private int seqPrioridade;
	private String cores;
	private String lembreteSugestao;	
	private String observacao;
		
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
	public int getNrRoteiro() {
		return nrRoteiro;
	}
	public void setNrRoteiro(int nrRoteiro) {
		this.nrRoteiro = nrRoteiro;
	}
	public String getDescrReferencia() {
		return descrReferencia;
	}
	public void setDescrReferencia(String descrReferencia) {
		this.descrReferencia = descrReferencia;
	}
	public Date getDataEmbarque() {
		return dataEmbarque;
	}
	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}
	public int getQtdeEstagioCritico() {
		return qtdeEstagioCritico;
	}
	public void setQtdeEstagioCritico(int qtdeEstagioCritico) {
		this.qtdeEstagioCritico = qtdeEstagioCritico;
	}
	public double getTempoProducaoUnit() {
		return tempoProducaoUnit;
	}
	public void setTempoProducaoUnit(double tempoProducaoUnit) {
		this.tempoProducaoUnit = tempoProducaoUnit;
	}
	public double getTempoCosturaUnit() {
		return tempoCosturaUnit;
	}
	public void setTempoCosturaUnit(double tempoCosturaUnit) {
		this.tempoCosturaUnit = tempoCosturaUnit;
	}
	public int getQtdeMaxPecasReservaTecidoAtende() {
		return qtdeMaxPecasReservaTecidoAtende;
	}
	public void setQtdeMaxPecasReservaTecidoAtende(int qtdeMaxPecasReservaAtende) {
		this.qtdeMaxPecasReservaTecidoAtende = qtdeMaxPecasReservaAtende;
	}
	public int getSeqPrioridade() {
		return seqPrioridade;
	}
	public void setSeqPrioridade(int seqPrioridade) {
		this.seqPrioridade = seqPrioridade;
	}
	public String getCores() {
		return cores;
	}
	public void setCores(String cores) {
		this.cores = cores;
	}
	public String getLembreteSugestao() {
		return lembreteSugestao;
	}
	public void setLembreteSugestao(String lembreteSugestao) {
		this.lembreteSugestao = lembreteSugestao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public int getQtdeMaxPecasReservaAviamentoAtende() {
		return qtdeMaxPecasReservaAviamentoAtende;
	}
	public void setQtdeMaxPecasReservaAviamentoAtende(int qtdeMaxPecasReservaAviamentoAtende) {
		this.qtdeMaxPecasReservaAviamentoAtende = qtdeMaxPecasReservaAviamentoAtende;
	}
	public OrdemProducao() {
		this.ordemProducao = 0;
		this.referencia = "";
		this.periodo = 0;
		this.qtdePecasProgramada = 0;
		this.nrAlternativa = 0;
		this.nrRoteiro = 0;
		this.descrReferencia = "";
		this.dataEmbarque = new Date();
		this.qtdeEstagioCritico = 0;
		this.tempoProducaoUnit = 0;
		this.tempoCosturaUnit = 0;	
		this.qtdeMaxPecasReservaTecidoAtende = 0;
		this.qtdeMaxPecasReservaAviamentoAtende = 0;
	}	
	public OrdemProducao(int ordemProducao, String referencia, int Periodo, int nrAlternativa, int nrRoteiro, int qtdePecasProgramada) {
		super();
		this.ordemProducao = ordemProducao;
		this.referencia = referencia;
		this.periodo = Periodo;		
		this.nrAlternativa = nrAlternativa;
		this.nrRoteiro = nrRoteiro;
		this.qtdePecasProgramada = qtdePecasProgramada;
	}
	
	@Override
	public int compareTo(OrdemProducao outraOrdem) {
		
		if (outraOrdem.getSeqPrioridade() < getSeqPrioridade())
			return 1;
		else if (outraOrdem.getSeqPrioridade() > getSeqPrioridade())
			return -1;
		
		return 0;		
	}
}