package br.com.live.model;

import java.util.List;

public class GeracaoCalendarioPeriodos {
	
	public int codigoPeriodo;
	public List<GeracaoCalendarioEstagios> estagios;
	
	public GeracaoCalendarioPeriodos() {
		
	}

	public GeracaoCalendarioPeriodos(int codigoPeriodo, List<GeracaoCalendarioEstagios> estagios) {
		this.codigoPeriodo = codigoPeriodo;
		this.estagios = estagios;
	}
	
}
