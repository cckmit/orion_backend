package br.com.live.model;

import java.util.Date;

public class PeriodoProducao {

	public int periodo;	
	public String dataIniPeriodo;
	public String dataFimPeriodo; 
	public String dataIniFaturamento; 
	public String dataFimFaturamento;
	
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getDataIniPeriodo() {
		return dataIniPeriodo;
	}
	public void setDataIniPeriodo(String dataIniPeriodo) {
		this.dataIniPeriodo = dataIniPeriodo;
	}
	public String getDataFimPeriodo() {
		return dataFimPeriodo;
	}
	public void setDataFimPeriodo(String dataFimPeriodo) {
		this.dataFimPeriodo = dataFimPeriodo;
	}
	public String getDataIniFaturamento() {
		return dataIniFaturamento;
	}
	public void setDataIniFaturamento(String dataIniFaturamento) {
		this.dataIniFaturamento = dataIniFaturamento;
	}
	public String getDataFimFaturamento() {
		return dataFimFaturamento;
	}
	public void setDataFimFaturamento(String dataFimFaturamento) {
		this.dataFimFaturamento = dataFimFaturamento;
	}	
}