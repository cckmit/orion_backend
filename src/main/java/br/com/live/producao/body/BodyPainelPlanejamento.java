package br.com.live.producao.body;

import java.util.List;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyPainelPlanejamento {
	
	public List<ConteudoChaveAlfaNum> listNivel;
	public List<ConteudoChaveAlfaNum> listReferencia;
	public List<ConteudoChaveAlfaNum> listTamanho;
	public List<ConteudoChaveAlfaNum> listCor;
	public List<ConteudoChaveNumerica> listColecao;
	public List<ConteudoChaveNumerica> listSubColecao;
	public List<ConteudoChaveNumerica> listLinhaProduto;
	public List<ConteudoChaveNumerica> listArtigo;
	public List<ConteudoChaveNumerica> listArtigoCota;
	public List<ConteudoChaveNumerica> listContaEstoq;
	public List<ConteudoChaveNumerica> listPublicoAlvo;
	public List<ConteudoChaveNumerica> listSegmento;
	public List<ConteudoChaveNumerica> listFaixaEtaria;
	public List<ConteudoChaveAlfaNum> listComplemento;
	public List<ConteudoChaveNumerica> listDeposito;
	public List<ConteudoChaveAlfaNum> listPerEmbarque;
	public List<ConteudoChaveNumerica> listNumInterno;
	public List<ConteudoChaveNumerica> listOrdemProducao;
	public List<ConteudoChaveNumerica> listEstagio;
	public String periodoProdInicio;
	public String periodoProdFim;
	public String periodoCartInicio;
	public String periodoCartFim;
	public String periodoAReceberInicio;
	public String periodoAReceberFim;
	public String periodoReservaInicio;
	public String periodoReservaFim;
	public int bloqueado;
}
