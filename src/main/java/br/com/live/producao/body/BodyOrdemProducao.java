package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.model.ConsultaPreOrdemProducao;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyOrdemProducao {

	public Long idPlanoMestre; 
	public List<ConteudoChaveAlfaNum> listaEstagio;
	public List<Long> listaPreOrdens;
	public List<Integer> listaOrdens;
	public int estagio;
	public String searchVar;
	
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
