package br.com.live.body;

import java.util.List;

import br.com.live.model.DadosSequenciamentoDecoracoes;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodySequenciamentoDecoracoes {		
	public int codEstagio;
	public List<ConteudoChaveAlfaNum> referencias;	
	public List<ConteudoChaveNumerica> artigos;	
	public List<ConteudoChaveAlfaNum> camposSelParaPriorizacao;	
	public boolean isSomenteFlat;
	public boolean isDiretoCostura;
	public boolean isPossuiAgrupador;
	public int periodoInicio;
	public int periodoFim; 		
	public int estagioDistrib;
	public String dataInicioSeq;
	public List<DadosSequenciamentoDecoracoes> listaOrdens;
}
