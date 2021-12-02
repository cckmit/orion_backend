package br.com.live.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.live.entity.DemandaProdutoPlano;
import br.com.live.entity.EstoqueProduto;
import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestreParamProgItem;
import br.com.live.entity.PlanoMestreParametros;
import br.com.live.entity.ProcessoProdutoPlano;
import br.com.live.entity.ProdutoPlanoMestre;
import br.com.live.entity.ProdutoPlanoMestrePorCor;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.Produto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataParametrosPlanoMestre;
import br.com.live.util.ParametrosPlanoMestre;

public class GeracaoPlanoMestre {

	private ParametrosPlanoMestre parametros;

	private List<EstoqueProduto> estoques;
	private List<DemandaProdutoPlano> demandas;
	private List<ProcessoProdutoPlano> processos;
	private List<Produto> produtos;

	private Map<String, ProdutoPlanoMestre> mapProdutos;

	private final static int PRODUTO = 0;
	private final static int ESTOQUE = 1;
	private final static int DEMANDA = 2;
	private final static int PROCESSO = 3;

	public GeracaoPlanoMestre(ParametrosPlanoMestre parametros, List<EstoqueProduto> estoques,
			List<DemandaProdutoPlano> demandas, List<ProcessoProdutoPlano> processos, List<Produto> produtos) {

		this.parametros = parametros;
		this.estoques = estoques;
		this.demandas = demandas;
		this.processos = processos;
		this.produtos = produtos;
		this.mapProdutos = new HashMap<String, ProdutoPlanoMestre>();

		if ((parametros.tipoDistribuicao == 1) || (parametros.tipoDistribuicao == 4))
			atualizarProduto(parametros.tipoDistribuicao);
		atualizarEstoque();
		atualizarDemanda();
		atualizarProcesso();
		atualizarSaldos(parametros.tipoDistribuicao);
	}

	private void atualizarProduto(int tipo, int plano, String nivel, String grupo, String sub, String item,
			int quantidade) {

		ProdutoPlanoMestre produto = new ProdutoPlanoMestre(nivel, grupo, sub, item);

		if (mapProdutos.containsKey(produto.getCodProduto())) {
			produto = mapProdutos.get(produto.getCodProduto());
		}

		if (tipo == GeracaoPlanoMestre.PRODUTO) {
			produto.qtdePrevisao += quantidade;
		}

		if (tipo == GeracaoPlanoMestre.ESTOQUE) {
			produto.qtdeEstoque += quantidade;
		}

		if (tipo == GeracaoPlanoMestre.DEMANDA) {

			produto.qtdeDemAcumulado += quantidade;

			if ((plano >= parametros.planoAcumProgInicio) && (plano <= parametros.planoAcumProgFim))
				produto.qtdeDemAcumProg += quantidade;

			if (plano == 1)
				produto.qtdeDemPlano1 += quantidade;
			if (plano == 2)
				produto.qtdeDemPlano2 += quantidade;
			if (plano == 3)
				produto.qtdeDemPlano3 += quantidade;
			if (plano == 4)
				produto.qtdeDemPlano4 += quantidade;
			if (plano == 5)
				produto.qtdeDemPlano5 += quantidade;
			if (plano == 6)
				produto.qtdeDemPlano6 += quantidade;
			if (plano == 7)
				produto.qtdeDemPlano7 += quantidade;
			if (plano == 8)
				produto.qtdeDemPlano8 += quantidade;
		}

		if (tipo == GeracaoPlanoMestre.PROCESSO) {

			produto.qtdeProcAcumulado += quantidade;

			if ((plano >= parametros.planoAcumProgInicio) && (plano <= parametros.planoAcumProgFim))
				produto.qtdeProcAcumProg += quantidade;

			if (plano == 1)
				produto.qtdeProcPlano1 += quantidade;
			if (plano == 2)
				produto.qtdeProcPlano2 += quantidade;
			if (plano == 3)
				produto.qtdeProcPlano3 += quantidade;
			if (plano == 4)
				produto.qtdeProcPlano4 += quantidade;
			if (plano == 5)
				produto.qtdeProcPlano5 += quantidade;
			if (plano == 6)
				produto.qtdeProcPlano6 += quantidade;
			if (plano == 7)
				produto.qtdeProcPlano7 += quantidade;
			if (plano == 8)
				produto.qtdeProcPlano8 += quantidade;
		}

		mapProdutos.put(produto.getCodProduto(), produto);
	}

	private void atualizarProduto(int tipoDistribuicao) {
		if (produtos != null) {
			for (Produto produto : produtos) {
				/*if (tipoDistribuicao == 4 && produto.qtdePrevisaoVendas == 0)
					continue;*/
				atualizarProduto(GeracaoPlanoMestre.PRODUTO, 0, produto.nivel, produto.grupo, produto.sub, produto.item,
						produto.qtdePrevisaoVendas);
			}
		}
	}

	private void atualizarEstoque() {
		if (estoques != null) {
			for (EstoqueProduto estoque : estoques) {
				atualizarProduto(GeracaoPlanoMestre.ESTOQUE, 0, estoque.nivel, estoque.grupo, estoque.sub, estoque.item,
						estoque.quantidade);
			}

		}
	}

	private void atualizarDemanda() {
		if (demandas != null) {
			for (DemandaProdutoPlano demanda : demandas) {
				if (demanda.plano > 0) {
					atualizarProduto(GeracaoPlanoMestre.DEMANDA, demanda.plano, demanda.nivel, demanda.grupo,
							demanda.sub, demanda.item, demanda.quantidade);
				}
			}
		}
	}

	private void atualizarProcesso() {
		if (processos != null) {
			for (ProcessoProdutoPlano processo : processos) {
				if (processo.plano > 0) {
					atualizarProduto(GeracaoPlanoMestre.PROCESSO, processo.plano, processo.nivel, processo.grupo,
							processo.sub, processo.item, processo.quantidade);
				}
			}
		}
	}

	private void atualizarSaldos(int tipoDistribuicao) {

		Set<String> chaves = mapProdutos.keySet();
		
		for (String chave : chaves) {
			mapProdutos.get(chave).qtdeSaldoPlano1 = mapProdutos.get(chave).qtdeEstoque
					+ mapProdutos.get(chave).qtdeProcPlano1 - mapProdutos.get(chave).qtdeDemPlano1;
			mapProdutos.get(chave).qtdeSaldoPlano2 = mapProdutos.get(chave).qtdeSaldoPlano1
					+ mapProdutos.get(chave).qtdeProcPlano2 - mapProdutos.get(chave).qtdeDemPlano2;
			mapProdutos.get(chave).qtdeSaldoPlano3 = mapProdutos.get(chave).qtdeSaldoPlano2
					+ mapProdutos.get(chave).qtdeProcPlano3 - mapProdutos.get(chave).qtdeDemPlano3;
			mapProdutos.get(chave).qtdeSaldoPlano4 = mapProdutos.get(chave).qtdeSaldoPlano3
					+ mapProdutos.get(chave).qtdeProcPlano4 - mapProdutos.get(chave).qtdeDemPlano4;
			mapProdutos.get(chave).qtdeSaldoPlano5 = mapProdutos.get(chave).qtdeSaldoPlano4
					+ mapProdutos.get(chave).qtdeProcPlano5 - mapProdutos.get(chave).qtdeDemPlano5;
			mapProdutos.get(chave).qtdeSaldoPlano6 = mapProdutos.get(chave).qtdeSaldoPlano5
					+ mapProdutos.get(chave).qtdeProcPlano6 - mapProdutos.get(chave).qtdeDemPlano6;
			mapProdutos.get(chave).qtdeSaldoPlano7 = mapProdutos.get(chave).qtdeSaldoPlano6
					+ mapProdutos.get(chave).qtdeProcPlano7 - mapProdutos.get(chave).qtdeDemPlano7;
			mapProdutos.get(chave).qtdeSaldoPlano8 = mapProdutos.get(chave).qtdeSaldoPlano7
					+ mapProdutos.get(chave).qtdeProcPlano8 - mapProdutos.get(chave).qtdeDemPlano8;
			mapProdutos.get(chave).qtdeSaldoAcumulado = mapProdutos.get(chave).qtdeEstoque
					+ mapProdutos.get(chave).qtdeProcAcumulado - mapProdutos.get(chave).qtdeDemAcumulado;
			mapProdutos.get(chave).qtdeSaldoAcumProg = mapProdutos.get(chave).qtdeEstoque
					+ mapProdutos.get(chave).qtdeProcAcumProg - mapProdutos.get(chave).qtdeDemAcumProg;
			
			if (mapProdutos.get(chave).qtdeSaldoAcumProg < 0)
				mapProdutos.get(chave).qtdeSugestao = (mapProdutos.get(chave).qtdeSaldoAcumProg * -1);
			
			mapProdutos.get(chave).qtdeEqualizadoSugestao = mapProdutos.get(chave).qtdeSugestao;
			mapProdutos.get(chave).qtdeDiferencaSugestao = mapProdutos.get(chave).qtdeEqualizadoSugestao
					- mapProdutos.get(chave).qtdeSugestao;
			
			if (tipoDistribuicao > 0) mapProdutos.get(chave).qtdeProgramada = mapProdutos.get(chave).qtdeEqualizadoSugestao;
		}
	}

	public List<ProdutoPlanoMestre> getProdutosPlanoMestre() {
		return new ArrayList<ProdutoPlanoMestre>(mapProdutos.values());
	}

	public List<ProdutoPlanoMestrePorCor> getProdutosPorCorPlanoMestre() {
		List<ProdutoPlanoMestre> itens = new ArrayList<ProdutoPlanoMestre>(mapProdutos.values());
		return new AgrupadorReferCorPlanoMestre(false, itens, parametros.tipoDistribuicao).getProdutos();
	}

	public List<ProdutoPlanoMestrePorCor> getProdutosPorCorPlanoMestre(List<ProdutoPlanoMestre> itens) {
		boolean consideraPrevisaoVendas = false;
		if (parametros.tipoDistribuicao == 4)
			consideraPrevisaoVendas = true;
		return new AgrupadorReferCorPlanoMestre(consideraPrevisaoVendas, itens, parametros.tipoDistribuicao).getProdutos();
	}

	public PlanoMestreParamProgItem getParametrosProgramacaoItem(long idPlanoMestre, long idItemPlanoMestre,
			AlternativaRoteiroPadrao alternativaRoteiroPadrao) {

		PlanoMestreParamProgItem paramProgramacaoItem = new PlanoMestreParamProgItem();
		paramProgramacaoItem.idPlanoMestre = idPlanoMestre;
		paramProgramacaoItem.idItemPlanoMestre = idItemPlanoMestre;
		paramProgramacaoItem.multiplicador = parametros.multiplicador;
		paramProgramacaoItem.periodo = parametros.periodoPadrao;
		paramProgramacaoItem.alternativa = alternativaRoteiroPadrao.alternativa;
		paramProgramacaoItem.roteiro = alternativaRoteiroPadrao.roteiro;
		paramProgramacaoItem.planoInicio = parametros.planoAcumProgInicio;
		paramProgramacaoItem.planoFim = parametros.planoAcumProgFim;

		return paramProgramacaoItem;
	}

	public PlanoMestre getCapaPlanoMestre() {
		return new PlanoMestre(parametros.descricao.toUpperCase(), parametros.usuario);
	}

	public PlanoMestreParametros getParametrosPlanoMestre() {

		FormataParametrosPlanoMestre parametrosFormatados = new FormataParametrosPlanoMestre(parametros);

		PlanoMestreParametros planoMestreParam = new PlanoMestreParametros();

		// Global
		planoMestreParam.tipoDistribuicao = parametros.tipoDistribuicao;
		planoMestreParam.descTipoDistribuicao = parametrosFormatados.getDescTipoDistribuicao();
		planoMestreParam.periodoPadrao = parametros.periodoPadrao;
		planoMestreParam.multiplicador = parametros.multiplicador;
		planoMestreParam.qtdeMinimaReferencia = parametros.qtdeMinimaReferencia;
		planoMestreParam.previsoes = concatChaveNumerica(parametros.previsoes);

		// Análise
		planoMestreParam.colecoes = concatChaveNumerica(parametros.colecoes);
		planoMestreParam.colecoes_permanentes = concatChaveNumerica(parametros.colecoesPermanentes);
		planoMestreParam.linhas_produtos = concatChaveNumerica(parametros.linhasProduto);
		planoMestreParam.artigos_produtos = concatChaveNumerica(parametros.artigosProduto);
		planoMestreParam.artigos_cotas = concatChaveNumerica(parametros.artigosCotas);
		planoMestreParam.publicos_alvos = concatChaveNumerica(parametros.publicosAlvos);
		planoMestreParam.embarques = concatConteudoChaveNumerica(parametros.embarques);
		planoMestreParam.referencias = concatChaveAlfaNum(parametros.produtos);
		planoMestreParam.cores = concatChaveAlfaNum(parametros.cores);
		planoMestreParam.origens_produtos = concatConteudoChaveNumerica(parametros.origProdutos);

		// Planejamento
		planoMestreParam.plano1_dem_inicio = parametros.perDemInicio01;
		planoMestreParam.plano2_dem_inicio = parametros.perDemInicio02;
		planoMestreParam.plano3_dem_inicio = parametros.perDemInicio03;
		planoMestreParam.plano4_dem_inicio = parametros.perDemInicio04;
		planoMestreParam.plano5_dem_inicio = parametros.perDemInicio05;
		planoMestreParam.plano6_dem_inicio = parametros.perDemInicio06;
		planoMestreParam.plano7_dem_inicio = parametros.perDemInicio07;
		planoMestreParam.plano8_dem_inicio = parametros.perDemInicio08;

		planoMestreParam.plano1_dem_fim = parametros.perDemFim01;
		planoMestreParam.plano2_dem_fim = parametros.perDemFim02;
		planoMestreParam.plano3_dem_fim = parametros.perDemFim03;
		planoMestreParam.plano4_dem_fim = parametros.perDemFim04;
		planoMestreParam.plano5_dem_fim = parametros.perDemFim05;
		planoMestreParam.plano6_dem_fim = parametros.perDemFim06;
		planoMestreParam.plano7_dem_fim = parametros.perDemFim07;
		planoMestreParam.plano8_dem_fim = parametros.perDemFim08;

		planoMestreParam.plano1_proc_inicio = parametros.perProcInicio01;
		planoMestreParam.plano2_proc_inicio = parametros.perProcInicio02;
		planoMestreParam.plano3_proc_inicio = parametros.perProcInicio03;
		planoMestreParam.plano4_proc_inicio = parametros.perProcInicio04;
		planoMestreParam.plano5_proc_inicio = parametros.perProcInicio05;
		planoMestreParam.plano6_proc_inicio = parametros.perProcInicio06;
		planoMestreParam.plano7_proc_inicio = parametros.perProcInicio07;
		planoMestreParam.plano8_proc_inicio = parametros.perProcInicio08;

		planoMestreParam.plano1_proc_fim = parametros.perProcFim01;
		planoMestreParam.plano2_proc_fim = parametros.perProcFim02;
		planoMestreParam.plano3_proc_fim = parametros.perProcFim03;
		planoMestreParam.plano4_proc_fim = parametros.perProcFim04;
		planoMestreParam.plano5_proc_fim = parametros.perProcFim05;
		planoMestreParam.plano6_proc_fim = parametros.perProcFim06;
		planoMestreParam.plano7_proc_fim = parametros.perProcFim07;
		planoMestreParam.plano8_proc_fim = parametros.perProcFim08;

		planoMestreParam.planoAcumProgInicio = parametros.planoAcumProgInicio;
		planoMestreParam.planoAcumProgFim = parametros.planoAcumProgFim;

		// Estoque
		planoMestreParam.considera_deposito = parametros.consideraDepositos;
		planoMestreParam.considera_prod_sem_estq = parametros.mostraProdSemEstoques;
		planoMestreParam.depositos = concatChaveNumerica(parametros.depositos);

		// Em processo
		planoMestreParam.considera_prod_sem_proc = parametros.mostraProdSemProcessos;

		// Demanda
		planoMestreParam.considera_pedido_bloq = parametros.consideraPedBloqueados;
		planoMestreParam.considera_prod_sem_pedi = parametros.mostraProdSemPedidos;
		planoMestreParam.numero_interno = parametros.nrInternoPedido;
		planoMestreParam.pedidos = concatChaveNumerica(parametros.pedidos);

		// Pré-Ordens
		planoMestreParam.agrupaOpPorRefer = 1;
		planoMestreParam.qtdeMaximaOP = 999999;
		planoMestreParam.qtdeMinimaOP = 0;
		planoMestreParam.qtdeMaximaCor = 0;
		planoMestreParam.periodoOP = 0;
		planoMestreParam.observacaoOP = "";
		planoMestreParam.depositoOP = 504;

		// Ocupação
		planoMestreParam.periodoInicioOcupacao = 0;
		planoMestreParam.periodoFimOcupacao = 0;

		return planoMestreParam;
	}

	private String concatChaveNumerica(List<ConteudoChaveNumerica> listaChaves) {

		String texto = "";

		if (listaChaves != null) {
			for (ConteudoChaveNumerica conteudo : listaChaves) {
				if (texto.equalsIgnoreCase(""))
					texto = Integer.toString(conteudo.value);
				else
					texto = texto + ", " + Integer.toString(conteudo.value);
			}
		}

		return texto;
	}

	private String concatChaveAlfaNum(List<ConteudoChaveAlfaNum> listaChaves) {

		String texto = "";

		if (listaChaves != null) {
			for (ConteudoChaveAlfaNum conteudo : listaChaves) {
				if (texto.equalsIgnoreCase(""))
					texto = conteudo.value;
				else
					texto = texto + ", " + conteudo.value;
			}
		}

		return texto;
	}

	private String concatConteudoChaveNumerica(List<ConteudoChaveNumerica> listaChaves) {

		String texto = "";

		if (listaChaves != null) {
			for (ConteudoChaveNumerica conteudo : listaChaves) {
				if (texto.equalsIgnoreCase(""))
					texto = conteudo.label;
				else
					texto = texto + ", " + conteudo.label;
			}
		}

		return texto;
	}

	public static void recalcularQtdesAcumuladasProdutos(int tipoDistribuicao, int planoInicio, int planoFim, List<ProdutoPlanoMestre> produtos) {	
		int[][] qtdes;
		int qtdeDemanda=0;
		int qtdeProcesso=0;
				
		for (ProdutoPlanoMestre produto : produtos) {			
			qtdeDemanda = 0; 	
			qtdeProcesso = 0;

			qtdes = parseQtdesDemProcToArray(produto);
			
			for (int i = planoInicio-1; i < planoFim; i++) {				
				qtdeDemanda += qtdes[0][i]; 	
				qtdeProcesso += qtdes[1][i];											
			}
		
			produto.qtdeSugestao = 0;
			produto.qtdeProgramada = 0;
			produto.qtdeDemAcumProg = qtdeDemanda;
			produto.qtdeProcAcumProg = qtdeProcesso;
			produto.qtdeSaldoAcumProg = produto.qtdeEstoque + produto.qtdeProcAcumProg - produto.qtdeDemAcumProg;   
			
			if (produto.qtdeSaldoAcumProg < 0) 
				produto.qtdeSugestao = (produto.qtdeSaldoAcumProg * -1);				 			
			
			produto.qtdeEqualizadoSugestao = produto.qtdeSugestao;
			produto.qtdeDiferencaSugestao = produto.qtdeEqualizadoSugestao - produto.qtdeSugestao;

			if (tipoDistribuicao > 0)
				produto.qtdeProgramada = produto.qtdeEqualizadoSugestao;	
		}				
	}			
	 
	private static int[][] parseQtdesDemProcToArray(ProdutoPlanoMestre produto) {		
		int[][] qtdes = new int[2] [8];
		
		qtdes [0] [0] = produto.qtdeDemPlano1; 
		qtdes [0] [1] = produto.qtdeDemPlano2;
		qtdes [0] [2] = produto.qtdeDemPlano3;
		qtdes [0] [3] = produto.qtdeDemPlano4;
		qtdes [0] [4] = produto.qtdeDemPlano5;
		qtdes [0] [5] = produto.qtdeDemPlano6;
		qtdes [0] [6] = produto.qtdeDemPlano7;
		qtdes [0] [7] = produto.qtdeDemPlano8;
		
		qtdes [1] [0] = produto.qtdeProcPlano1; 
		qtdes [1] [1] = produto.qtdeProcPlano2;
		qtdes [1] [2] = produto.qtdeProcPlano3;
		qtdes [1] [3] = produto.qtdeProcPlano4;
		qtdes [1] [4] = produto.qtdeProcPlano5;
		qtdes [1] [5] = produto.qtdeProcPlano6;
		qtdes [1] [6] = produto.qtdeProcPlano7;
		qtdes [1] [7] = produto.qtdeProcPlano8;
		
		return qtdes;
	}
}
