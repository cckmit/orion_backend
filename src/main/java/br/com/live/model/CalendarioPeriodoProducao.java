package br.com.live.model;

import java.util.List;

public class CalendarioPeriodoProducao {
	
	public int codigoPeriodo;
	public List<CalendarioEstagiosProducao> estagios;
	
	public CalendarioPeriodoProducao() {
		
	}

	public CalendarioPeriodoProducao(int codigoPeriodo, List<CalendarioEstagiosProducao> estagios) {
		this.codigoPeriodo = codigoPeriodo;
		this.estagios = estagios;
	}
	
}
