package br.com.live.model;

import java.util.Date;

public class SugestaoReservaPorProduto implements Comparable<SugestaoReservaPorProduto> {

	private int prioridade;
	private String nivel;
	private String grupo;
	private String tamanho;	
	private String cor;
	private String descricao;
	private int alternativa;
	private int roteiro;
	private Date dataEmbarque;
	private int qtdeEstagioCritico;
	private double tempoProducaoUnit;
	private double tempoCosturaUnit;
	private int qtdePrevista;
	private int qtdeAtendida;	
	private int seqTamanho;
	
	public SugestaoReservaPorProduto(int prioridade, String nivel, String grupo, String tamanho, String cor, int seqTamanho, String descricao, int alternativa, int roteiro, Date dataEmbarque, int qtdeEstagioCritico, double tempoProducaoUnit, double tempoCosturaUnit, int qtdePrevista, int qtdeAtendida) {
		this.prioridade = prioridade;
		this.nivel = nivel; 
		this.grupo = grupo; 
		this.tamanho = tamanho; 
		this.cor = cor; 
		this.descricao = descricao; 
		this.alternativa = alternativa; 
		this.roteiro = roteiro; 
		this.dataEmbarque = dataEmbarque; 
		this.qtdeEstagioCritico = qtdeEstagioCritico; 
		this.tempoProducaoUnit = tempoProducaoUnit;
		this.tempoCosturaUnit = tempoCosturaUnit;
		this.qtdePrevista = qtdePrevista; 
		this.qtdeAtendida = qtdeAtendida; 		
		this.seqTamanho = seqTamanho;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
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
	public int getQtdePrevista() {
		return qtdePrevista;
	}
	public void setQtdePrevista(int qtdePrevista) {
		this.qtdePrevista = qtdePrevista;		
	}
	public int getQtdeAtendida() {
		return qtdeAtendida;
	}
	public void setQtdeAtendida(int qtdeAtendida) {
		this.qtdeAtendida = qtdeAtendida;		
	}
	public int getSeqTamanho() {
		return seqTamanho;
	}
	public void setSeqTamanho(int seqTamanho) {
		this.seqTamanho = seqTamanho;
	}
	
	@Override
	public int compareTo(SugestaoReservaPorProduto outroProduto) {
		
		if (outroProduto.getPrioridade() < getPrioridade())
			return 1;
		else if (outroProduto.getPrioridade() > getPrioridade())
			return -1;
		else if (outroProduto.getSeqTamanho() < getSeqTamanho())
			return 1;
		if (outroProduto.getSeqTamanho() > getSeqTamanho())
			return -1;
		
		return 0;		
	}
}