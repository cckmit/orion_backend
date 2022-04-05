package br.com.live.body;

import java.util.List;

import br.com.live.model.OrdemProducao;
import br.com.live.model.OrdemProducaoItem;
import br.com.live.model.SugestaoReservaTecidosReservados;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodySugestaoReservaTecidos {

	public List<ConteudoChaveNumerica> planosMestres ;
	public List<ConteudoChaveNumerica> embarques ;
	public List<ConteudoChaveAlfaNum> referencias ;
	public List<ConteudoChaveAlfaNum> tecidos ;
	public List<ConteudoChaveNumerica> depositos ;
	public List<ConteudoChaveNumerica> estagios ;
	public List<ConteudoChaveNumerica> artigos ;	
	public List<ConteudoChaveAlfaNum> camposSelParaPriorizacao ;
	public List<OrdemProducaoItem> listaItensQtdeMaxProgramada ;
	public int periodoInicio ;
	public int periodoFim ;	
	public int priorizacao ;
	public int percentualMinimoAtender;
	public boolean isSomenteFlat;
	public List<OrdemProducao> listaOrdensLiberar;	
	public List<SugestaoReservaTecidosReservados> listaTecidosReservar;
}