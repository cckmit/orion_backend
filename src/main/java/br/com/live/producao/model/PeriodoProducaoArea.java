package br.com.live.producao.model;

import java.util.Date;

public class PeriodoProducaoArea {

	private int periodo;
	private int area;
	private int empresa;
	private Date dataProdInicio;
	private Date dataProdFim;
	private Date dataFatInicio;
	private Date dataFatFim;
	private Date dataLimite;
	private int quinzena;
	
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getEmpresa() {
		return empresa;
	}
	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}
	public Date getDataProdInicio() {
		return dataProdInicio;
	}
	public void setDataProdInicio(Date dataProdInicio) {
		this.dataProdInicio = dataProdInicio;
	}
	public Date getDataProdFim() {
		return dataProdFim;
	}
	public void setDataProdFim(Date dataProdFim) {
		this.dataProdFim = dataProdFim;
	}
	public Date getDataFatInicio() {
		return dataFatInicio;
	}
	public void setDataFatInicio(Date dataFatInicio) {
		this.dataFatInicio = dataFatInicio;
	}
	public Date getDataFatFim() {
		return dataFatFim;
	}
	public void setDataFatFim(Date dataFatFim) {
		this.dataFatFim = dataFatFim;
	}
	public Date getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}
	public int getQuinzena() {
		return quinzena;
	}
	public void setQuinzena(int quinzena) {
		this.quinzena = quinzena;
	}
	
	public PeriodoProducaoArea(int periodo, int area, int empresa, Date dataProdInicio, Date dataProdFim, Date dataFatInicio, Date dataFatFim, Date dataLimite, int quinzena) {
		this.periodo = periodo;
		this.area = area;
		this.empresa = empresa;
		this.dataProdInicio = dataProdInicio;
		this.dataProdFim = dataProdFim;
		this.dataFatInicio = dataFatInicio;
		this.dataFatFim = dataFatFim;
		this.dataLimite = dataLimite;
		this.quinzena = quinzena;
	}	
}
