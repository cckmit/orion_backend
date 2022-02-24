package br.com.live.body;

import java.util.List;

import br.com.live.model.PreOrdemProducaoItem;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodySugestaoReservaTecidos {

	public List<ConteudoChaveNumerica> planosMestres ;
	public List<ConteudoChaveNumerica> embarques ;
	public List<ConteudoChaveAlfaNum> referencias ; 	
	public List<ConteudoChaveNumerica> depositos ;
	public int priorizacao ;
	public int percentualMinimoAtender;
	public List<PreOrdemProducaoItem> listaItensComQtdesAtendidas ;
	
}