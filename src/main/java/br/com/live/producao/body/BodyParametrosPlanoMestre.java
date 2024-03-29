package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.model.ConsultaItensPlanoMestre;
import br.com.live.producao.model.ConsultaItensTamPlanoMestre;
import br.com.live.produto.body.BodyFiltroProduto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyParametrosPlanoMestre {
	
	public String descricao;
	public String usuario;
    public int periodoPadrao; 
    public int tipoDistribuicao; 
    public int multiplicador;
    public int qtdeMinimaReferencia;
	
	public List<ConteudoChaveNumerica> colecoes;
	public List<ConteudoChaveNumerica> colecoesPermanentes;	
	public List<ConteudoChaveNumerica> linhasProduto;
	public List<ConteudoChaveNumerica> artigosProduto;
	public List<ConteudoChaveNumerica> artigosCotas;
	public List<ConteudoChaveNumerica> publicosAlvos;
	public List<ConteudoChaveNumerica> embarques;
	public List<ConteudoChaveAlfaNum> produtos;
	public List<ConteudoChaveAlfaNum> cores;
	public List<ConteudoChaveNumerica> origProdutos;
	public List<ConteudoChaveNumerica> depositos; 	
	public List<ConteudoChaveNumerica> pedidos;
	public List<ConteudoChaveNumerica> naturezas;
	public List<ConteudoChaveNumerica> previsoes;
	
	public int perDemInicio01;
	public int perDemInicio02;
	public int perDemInicio03;
	public int perDemInicio04;
	public int perDemInicio05;
	public int perDemInicio06;
	public int perDemInicio07;
	public int perDemInicio08;
	
	public int perDemFim01;
	public int perDemFim02;
	public int perDemFim03;
	public int perDemFim04;
	public int perDemFim05;
	public int perDemFim06;
	public int perDemFim07;
	public int perDemFim08;

	public int perProcInicio01;
	public int perProcInicio02;
	public int perProcInicio03;
	public int perProcInicio04;
	public int perProcInicio05;
	public int perProcInicio06;
	public int perProcInicio07;
	public int perProcInicio08;
	
	public int perProcFim01;
	public int perProcFim02;
	public int perProcFim03;
	public int perProcFim04;
	public int perProcFim05;
	public int perProcFim06;
	public int perProcFim07;
	public int perProcFim08;
	
	public int planoAcumProgInicio;
	public int planoAcumProgFim;
	
	public int consideraDepositos; 
	public int mostraProdSemEstoques; 
	public int mostraProdSemProcessos; 
	public int consideraPedBloqueados; 
	public int mostraProdSemPedidos;
	public int nrInternoPedido;
		
	public long idPlanoMestre;
	public int situacaoPlanoMestre;
	public String codigoGrupoCor;
	public List<ConsultaItensPlanoMestre> itensPlanoMestre;
	public List<ConsultaItensTamPlanoMestre> gradeTamanhosItem;
		
	// Parâmetros Programação
	public String codGrupoItemProg;
	public int alternativaProg;
	public int roteiroProg;
	public int periodoProg;
	public int multiplicadorProg;
	public int planoInicio;
	public int planoFim;
	
	// Pré-Ordens
    public int agrupaOpPorRefer;
    public int qtdeMaximaOP;
    public int qtdeMinimaOP;
    public int periodoOP;
    public int depositoOP;
    public String observacaoOP;	
    public List<Integer> preOrdensSelected;
    
    // Ocupação 
    public int periodoOcupacaoInicio;
    public int periodoOcupacaoFim;
    
	public BodyFiltroProduto getFiltroProduto () {
		
		BodyFiltroProduto filtro = new BodyFiltroProduto();
		
		filtro.colecoes = this.colecoes;
		filtro.colecoesPermanentes = this.colecoesPermanentes;
		filtro.cores = this.cores;
		filtro.referencias = this.produtos;		
		
		return filtro;
	}	
}
