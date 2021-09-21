package br.com.live.util;

import java.util.List;

import br.com.live.model.ConsultaPreOrdemProducao;

public class BodyOrdemProducao {

	public Long idPlanoMestre;
	public List<Long> listaPreOrdens;
	
	public List<ConteudoChaveNumerica> ordemProducao;
	
	// Retorno 
	public int sitPlanoMestre;
	public List<ConsultaPreOrdemProducao> listaConsPreOrdens;
		
	public BodyOrdemProducao() {}
	
	public BodyOrdemProducao(Long idPlanoMestre, int sitPlanoMestre, List<ConsultaPreOrdemProducao> listaConsPreOrdens) {
		this.idPlanoMestre = idPlanoMestre;
		this.sitPlanoMestre = sitPlanoMestre;
		this.listaConsPreOrdens = listaConsPreOrdens;
	}	
}
