package br.com.live.model;

import java.util.List;

public class LayoutCalendarioProducao {

	public List<PeriodoProducao> periodos;	
	public List<LayoutCabecalhoCalendProd> cabecalho;
	public List<LayoutCorpoCalendProd> corpo;
	
	public LayoutCalendarioProducao(List<PeriodoProducao> periodos, List<LayoutCabecalhoCalendProd> cabecalho, List<LayoutCorpoCalendProd> corpo) {
		this.periodos = periodos;
		this.cabecalho = cabecalho;
		this.corpo = corpo;
	}
}
