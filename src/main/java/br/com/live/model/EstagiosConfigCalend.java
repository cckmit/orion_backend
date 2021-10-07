package br.com.live.model;

import java.util.Date;

public class EstagiosConfigCalend extends EstagiosConfigurados {
	
	private Date dataFimDate;
	private Date dataInicioDate;
	
	public Date getDataFimDate() {
		return dataFimDate;
	}
	public void setDataFimDate(Date dataFimDate) {
		this.dataFimDate = dataFimDate;
	}
	public Date getDataInicioDate() {
		return dataInicioDate;
	}
	public void setDataInicioDate(Date dataInicioDate) {
		this.dataInicioDate = dataInicioDate;
	}
	
	public EstagiosConfigCalend() {
	}
	
	public EstagiosConfigCalend(Date dataFimDate, Date dataInicioDate) {
		super();
		this.dataFimDate = dataFimDate;
		this.dataInicioDate = dataInicioDate;
	}
}
