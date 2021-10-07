package br.com.live.util;

import java.util.List;

import br.com.live.model.PeriodoProducao;

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
