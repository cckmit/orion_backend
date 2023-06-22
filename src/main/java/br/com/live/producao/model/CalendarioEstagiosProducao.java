package br.com.live.producao.model;

import java.util.Date;

public class CalendarioEstagiosProducao {
	
	public int codigoEstagio;
	public Date dataInicio;
	public Date dataFim;
	public int diaSemanaInicio;
	public int diaSemanaFim;
	public int lead;
	
	public CalendarioEstagiosProducao() {
		
	}

	public CalendarioEstagiosProducao(int codigoEstagio, Date dataInicio, Date dataFim, int diaSemanaInicio, int lead, int diaSemanaFim) {
		this.codigoEstagio = codigoEstagio;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.diaSemanaInicio = diaSemanaInicio;
		this.lead = lead;
		this.diaSemanaFim = diaSemanaFim;
	}
}
