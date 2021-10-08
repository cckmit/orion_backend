package br.com.live.model;

public class LayoutCorpoCalendProd {
	
	public int periodo;
	public String dataInicio;
	public String dataFim;
	
	public LayoutCorpoCalendProd() {
		
	}

	public LayoutCorpoCalendProd(int periodo, String dataInicio, String dataFim) {
		super();
		this.periodo = periodo;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}
}
