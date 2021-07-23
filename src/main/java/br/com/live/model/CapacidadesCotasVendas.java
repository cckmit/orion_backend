package br.com.live.model;

import java.util.Date;

public class CapacidadesCotasVendas {
	private int periodo;
	private Date dataInicial;
	private Date dataFinal;
	private int linha;
	private String descLinha;
	private int categoria;
	private String descCategoria;
	private int minutos;
	private int pecas;
	private String id;
	
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public String getDescLinha() {
		return descLinha;
	}
	public void setDescLinha(String descLinha) {
		this.descLinha = descLinha;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public String getDescCategoria() {
		return descCategoria;
	}
	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getPecas() {
		return pecas;
	}
	public void setPecas(int pecas) {
		this.pecas = pecas;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
