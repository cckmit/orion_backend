package br.com.live.model;

import java.util.Date;

public class GeracaoCalendarioEstagios {
	
	public int codigoEstagio;
	public Date dataInicio;
	public Date dataFim;
	public int diaSemanaInicio;
	public int diaSemanaFim;
	public int lead;
	
	public GeracaoCalendarioEstagios() {
		
	}

	public GeracaoCalendarioEstagios(int codigoEstagio, Date dataInicio, Date dataFim, int diaSemanaInicio, int lead, int diaSemanaFim) {
		this.codigoEstagio = codigoEstagio;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.diaSemanaInicio = diaSemanaInicio;
		this.lead = lead;
		this.diaSemanaFim = diaSemanaFim;
	}
}
