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
	// Configuracao das colunas de artigos	
	public List<ConteudoChaveNumerica> artigos1;
	public List<ConteudoChaveNumerica> artigos2;
	public List<ConteudoChaveNumerica> artigos3;
	public List<ConteudoChaveNumerica> artigos4;
	public List<ConteudoChaveNumerica> artigos5;
	public List<ConteudoChaveNumerica> artigos6;
	public List<ConteudoChaveNumerica> artigos7;
	public List<ConteudoChaveNumerica> artigos8;
	public List<ConteudoChaveNumerica> artigos9;
    public String descricao1;
    public String descricao2;
    public String descricao3;
    public String descricao4;
    public String descricao5;
    public String descricao6;
    public String descricao7;
    public String descricao8;
    public String descricao9;
    public int meta1;
    public int meta2;
    public int meta3;
    public int meta4;
    public int meta5;
    public int meta6;
    public int meta7;
    public int meta8;
    public int meta9;
    // Retorno das qtdes produzidas por artigos configurados
    public int qtdeProduzida1;
    public int qtdeProduzida2;
    public int qtdeProduzida3;
    public int qtdeProduzida4;
    public int qtdeProduzida5;
    public int qtdeProduzida6;
    public int qtdeProduzida7;
    public int qtdeProduzida8;
    public int qtdeProduzida9;
    public int qtdeOutros;
    public int qtdeFlatProduzida;
}