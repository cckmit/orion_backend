package br.com.live.producao.model;

public class LayoutCabecalhoCalendProd {
	public String estagio;
	public String diaSemanaInicio;
	public String diaSemanaFim;
	
	
	public LayoutCabecalhoCalendProd() {		
	}

	public LayoutCabecalhoCalendProd(String estagio, String diaSemanaInicio, String diaSemanaFim) {
		super();
		this.estagio = estagio;
		this.diaSemanaInicio = diaSemanaInicio;
		this.diaSemanaFim = diaSemanaFim;
	}
}
