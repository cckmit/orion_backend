package br.com.live.model;

import java.util.Date;

public class SugestaoReservaPorProduto {

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
	private int qtdePrevista;
	private int qtdeAtendida;
	private double percentualAtendido;
	
	public SugestaoReservaPorProduto(String nivel, String grupo, String tamanho, String cor, String descricao, int alternativa, int roteiro, Date dataEmbarque, int qtdeEstagioCritico, double tempoProducaoUnit, int qtdePrevista, int qtdeAtendida, double percentualAtendido) {
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
		this.qtdePrevista = qtdePrevista; 
		this.qtdeAtendida = qtdeAtendida; 
		this.percentualAtendido = percentualAtendido;
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
	public int getQtdePrevista() {
		return qtdePrevista;
	}
	public void setQtdePrevista(int qtdePrevista) {
		this.qtdePrevista = qtdePrevista;
		this.calcularPercentualAtendido();
	}
	public int getQtdeAtendida() {
		return qtdeAtendida;
	}
	public void setQtdeAtendida(int qtdeAtendida) {
		this.qtdeAtendida = qtdeAtendida;
		this.calcularPercentualAtendido();
	}
	public double getPercentualAtendido() {
		return percentualAtendido;
	}
	public void setPercentualAtendido(double percentualAtendido) {
		this.percentualAtendido = percentualAtendido;
	}
	private void calcularPercentualAtendido() {
		this.percentualAtendido = ((double) this.qtdeAtendida / (double) this.qtdePrevista) * 100;
	}	
}