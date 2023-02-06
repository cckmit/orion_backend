package br.com.live.body;

import java.util.List;

import br.com.live.model.OrdemProducao;
import br.com.live.model.OrdemProducaoItem;
import br.com.live.model.SugestaoReservaMateriaisReservados;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodySugestaoReservaMateriais {

	public List<ConteudoChaveNumerica> planosMestres ;
	public List<ConteudoChaveNumerica> embarques ;
	public List<ConteudoChaveAlfaNum> referencias ;
	public List<ConteudoChaveAlfaNum> tecidos ;
	public List<ConteudoChaveNumerica> depositosTecidos ;
	public List<ConteudoChaveNumerica> depositosAviamentos ;
	public List<ConteudoChaveNumerica> estagios ;
	public List<ConteudoChaveNumerica> artigos ;	
	public List<ConteudoChaveAlfaNum> camposSelParaPriorizacao ;
	public List<OrdemProducaoItem> listaItensQtdeMaxProgramada ;
	public long idUsuarioOrion;
	public int periodoInicio ;
	public int periodoFim ;	
	public int priorizacao ;
	public int percentualMinimoAtender;	
	public boolean isSomenteFlat;
	public boolean isDiretoCostura;
	public boolean isOrdensSemTecido;
	public List<OrdemProducao> listaOrdensLiberar;
	public List<OrdemProducao> listaOrdensComLembrete;
	public List<OrdemProducao> listaOrdensComObservacao;
	public List<SugestaoReservaMateriaisReservados> listaMateriaisReservar;
	public int regraReserva;
}