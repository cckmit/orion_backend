package br.com.live.producao.model;

import java.util.Date;

public class CalendarioSemana {

    private int mes;
    private int ano;
	private int numeroSemana;
	private Date dataInicio;
	private Date dataFim;
	private int qtdeDias;
	private int qtdeDiasUteis;
		
	public int getQtdeDiasUteis() {
		return qtdeDiasUteis;
	}
	public void setQtdeDiasUteis(int qtdeDiasUteis) {
		this.qtdeDiasUteis = qtdeDiasUteis;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getNumeroSemana() {
		return numeroSemana;
	}
	public void setNumeroSemana(int numeroSemana) {
		this.numeroSemana = numeroSemana;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public int getQtdeDias() {
		return qtdeDias;
	}
	public void setQtdeDias(int qtdeDias) {
		this.qtdeDias = qtdeDias;
	}
}
