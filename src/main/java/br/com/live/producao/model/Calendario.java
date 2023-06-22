package br.com.live.producao.model;

import java.util.Date;

public class Calendario {
	
	public static final int DIA_UTIL = 0;
	
	private Date data;
	private int diaUtil;
	private int diaSemana;
	private int numeroSemana;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getDiaUtil() {
		return diaUtil;
	}
	public void setDiaUtil(int diaUtil) {
		this.diaUtil = diaUtil;
	}
	public int getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	public int getNumeroSemana() {
		return numeroSemana;
	}
	public void setNumeroSemana(int numeroSemana) {
		this.numeroSemana = numeroSemana;
	}
}
