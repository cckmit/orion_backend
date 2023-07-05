package br.com.live.producao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.producao.body.BodyPainelListaPrioridades;
import br.com.live.producao.custom.PainelListaPrioridadesCustom;
import br.com.live.producao.custom.SequenciamentoDecoracoesCustom;
import br.com.live.producao.model.PainelListaPrioridades;
import br.com.live.producao.model.PainelListaPrioridadesAnaliseCarteira;
import br.com.live.producao.model.PainelListaPrioridadesCarteiraPedidos;
import br.com.live.producao.model.PainelListaPrioridadesOrdensEmProducao;
import br.com.live.producao.model.PainelListaPrioridadesEmProducaoPorEstagio;
import br.com.live.util.CodigoProduto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Service
@Transactional
public class PainelListaPrioridadesService {

	private final PainelListaPrioridadesCustom painelListaPrioridadesCustom;
	private final SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom;
	
	private Map<String, PainelListaPrioridadesAnaliseCarteira> mapAnaliseCarteira;
	private List<PainelListaPrioridadesCarteiraPedidos> listCarteiraPedidos; 
	private List<PainelListaPrioridadesAnaliseCarteira> listAnaliseCarteira; 
	private List<PainelListaPrioridadesOrdensEmProducao> listOrdensEmProducao;
	private Map<Integer, PainelListaPrioridadesEmProducaoPorEstagio> mapOrdensPorEstagio;
	private List<PainelListaPrioridadesEmProducaoPorEstagio> listOrdensPorEstagio;	
	private Map<String, Integer> mapTotalEmProducaoPorOrdemEstagio;
	private Map<Integer, Integer> mapTotalEmProducaoPorEstagio;	
	
	public PainelListaPrioridadesService(PainelListaPrioridadesCustom painelListaPrioridadesCustom,
										 SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom) {
		this.painelListaPrioridadesCustom = painelListaPrioridadesCustom;
		this.sequenciamentoDecoracoesCustom = sequenciamentoDecoracoesCustom;
	}

	public void inicializarListas() {
		this.mapAnaliseCarteira = new TreeMap<String, PainelListaPrioridadesAnaliseCarteira>();
		this.mapOrdensPorEstagio = new TreeMap<Integer, PainelListaPrioridadesEmProducaoPorEstagio>(); 
		this.listCarteiraPedidos = new ArrayList<PainelListaPrioridadesCarteiraPedidos> ();
		this.listAnaliseCarteira = new ArrayList<PainelListaPrioridadesAnaliseCarteira>();
		this.listOrdensEmProducao = new ArrayList<PainelListaPrioridadesOrdensEmProducao>();
		this.listOrdensPorEstagio = new ArrayList<PainelListaPrioridadesEmProducaoPorEstagio>();	
		this.mapTotalEmProducaoPorOrdemEstagio = new HashMap<String, Integer>();
		this.mapTotalEmProducaoPorEstagio = new HashMap<Integer, Integer>();	
	}
	
	public PainelListaPrioridades findListaPrioridadesAtendimento(BodyPainelListaPrioridades parametros) {				
		System.out.println("findListaPrioridadesAtendimento");		
		
		System.out.println("inicializarListas - " + new Date());
		inicializarListas();
		System.out.println("analisarCarteiraEmAberto - "  + new Date());
		analisarCarteiraEmAberto(parametros);
		System.out.println("gerarCarteiraOrdenadaPorProduto - "  + new Date());
		gerarCarteiraOrdenadaPorProduto(parametros.produtosComSobra, parametros.produtoscomFalta, parametros.produtosFaltaSemProducao);
		System.out.println("analisarOrdensEmProducao - "  + new Date());
		analisarOrdensEmProducao(parametros);	
		System.out.println("acumularProducaoPorEstagio - "  + new Date());
		acumularProducaoPorEstagio();		
		
		System.out.println("findListaPrioridadesAtendimento");				
		return new PainelListaPrioridades(listCarteiraPedidos, listAnaliseCarteira, listOrdensEmProducao, listOrdensPorEstagio);
	}

	private void analisarCarteiraEmAberto(BodyPainelListaPrioridades parametros) {		
		listCarteiraPedidos = painelListaPrioridadesCustom.findCarteiraPedidos(FormataData.parseStringToDate(parametros.dtEmbarqueInicio), FormataData.parseStringToDate(parametros.dtEmbarqueFim), ConteudoChaveNumerica.parseValueToString(parametros.listPedidosComerciais), ConteudoChaveNumerica.parseValueToString(parametros.listPedidosOrdens), ConteudoChaveNumerica.parseValueToString(parametros.listSituacoesPedidos), ConteudoChaveNumerica.parseValueToString(parametros.listNumerosInterno),
				parametros.periodoProdInicio, parametros.periodoProdFim, 
				ConteudoChaveNumerica.parseValueToString(parametros.listDepositosEstoques), 
				ConteudoChaveNumerica.parseValueToString(parametros.listDepositosPedidos), 
				ConteudoChaveNumerica.parseValueToString(parametros.listEstagios),
				ConteudoChaveNumerica.parseValueToString(parametros.listColecoes), 
				ConteudoChaveNumerica.parseValueToString(parametros.listLinhasProdutos), 
				ConteudoChaveNumerica.parseValueToString(parametros.listArtigos), 
				ConteudoChaveAlfaNum.parseValueToString(parametros.listReferencias), 
				ConteudoChaveAlfaNum.parseValueToString(parametros.listTamanhos), 
				ConteudoChaveAlfaNum.parseValueToString(parametros.listCores), 
				ConteudoChaveNumerica.parseValueToString(parametros.listSegmentos), 
				ConteudoChaveNumerica.parseValueToString(parametros.listFamilias), 
				ConteudoChaveNumerica.parseValueToString(parametros.listFaccoes), 
				ConteudoChaveAlfaNum.parseValueToString(parametros.listNaturezas));							
		for (PainelListaPrioridadesCarteiraPedidos item : listCarteiraPedidos) {
			// cria um mapa de dados, para acumular os dados por produto					
			acumularPorProduto(item);	
		}					
	}
	
	private void acumularPorProduto(PainelListaPrioridadesCarteiraPedidos item) {
		PainelListaPrioridadesAnaliseCarteira itemAnaliseCarteira;
		String produto = new CodigoProduto("1", item.getReferencia(), item.getTamanho(), item.getCor()).getCodigo(); 
		if (mapAnaliseCarteira.containsKey(produto)) {
			itemAnaliseCarteira = mapAnaliseCarteira.get(produto); 
			itemAnaliseCarteira.setQtdeCarteira(itemAnaliseCarteira.getQtdeCarteira() + item.getQtdeCarteira());
			itemAnaliseCarteira.setQtdeColetada(itemAnaliseCarteira.getQtdeColetada() + item.getQtdeColetada());
			itemAnaliseCarteira.setQtdeSugerida(itemAnaliseCarteira.getQtdeSugerida() + item.getQtdeSugerida());
		} else {						
			itemAnaliseCarteira = new PainelListaPrioridadesAnaliseCarteira(item.getId(), item.getReferencia(), item.getDescReferencia(), item.getTamanho(), item.getCor(), item.getDescCor(), item.getQtdeCarteira(), item.getQtdeEstoque(), item.getQtdeEmProducao(), item.getQtdeSugerida(), item.getQtdeColetada());
			mapAnaliseCarteira.put(produto, itemAnaliseCarteira);
		}
	}
	
	private void gerarCarteiraOrdenadaPorProduto(boolean produtosComSobra, boolean produtoscomFalta, boolean produtosFaltaSemProducao) {		
		for (String produto : mapAnaliseCarteira.keySet()) {
			PainelListaPrioridadesAnaliseCarteira itemAnaliseCarteira = mapAnaliseCarteira.get(produto);
			
			if (((produtosComSobra)&&(itemAnaliseCarteira.getQtdeSobraFalta()>0))||
				((produtoscomFalta)&&(itemAnaliseCarteira.getQtdeSobraFalta()<0))||	
				((produtosFaltaSemProducao)&&(itemAnaliseCarteira.getQtdeSobraFalta()<0)&&(itemAnaliseCarteira.getQtdeEmProducao()==0))) 			
				listAnaliseCarteira.add(itemAnaliseCarteira);			
		}
	}
	
	private void analisarOrdensEmProducao(BodyPainelListaPrioridades parametros) {				
		int idRegOrdem = 0;
		for (PainelListaPrioridadesAnaliseCarteira itemCarteira : listAnaliseCarteira) {			
			// analisa apenas ordens para produtos que estão com o estoque negativo.
			if (itemCarteira.getQtdeSobraFalta() >= 0) continue;			
			
			int qtdeAtualizar = 0;
			int qtdeNecessaria = itemCarteira.getQtdeSobraFalta() * -1;
			
			List<PainelListaPrioridadesOrdensEmProducao> ordens = painelListaPrioridadesCustom.findOrdensEmProducao(itemCarteira.getReferencia(), itemCarteira.getTamanho(), itemCarteira.getCor(), parametros.periodoProdInicio, parametros.periodoProdFim, ConteudoChaveNumerica.parseValueToString(parametros.listPedidosOrdens), ConteudoChaveNumerica.parseValueToString(parametros.listFamilias), ConteudoChaveNumerica.parseValueToString(parametros.listFaccoes), ConteudoChaveNumerica.parseValueToString(parametros.listEstagios));			
			
			for (PainelListaPrioridadesOrdensEmProducao ordem : ordens) {				
				idRegOrdem++;								
				String id = String.format("%02d", ordem.getSeqFilaEstagio()) + String.format("%02d", ordem.getCodEstagio()) + String.format("%09d", idRegOrdem); 				
				// atualizar os dados do produto originados da analise da carteira.
				ordem.setId(id);
				ordem.setQtdeCarteira(itemCarteira.getQtdeCarteira());
				ordem.setQtdeEstoque(itemCarteira.getQtdeEstoque());
				ordem.setQtdeSobraFalta(itemCarteira.getQtdeSobraFalta());
				ordem.setQtdeColetada(itemCarteira.getQtdeColetada());
				ordem.setQtdeSugerida(itemCarteira.getQtdeSugerida());
												
				if (ordem.getQtdeEmProducaoPacote() < qtdeNecessaria)
					qtdeAtualizar = ordem.getQtdeEmProducaoPacote();
				else qtdeAtualizar = qtdeNecessaria;
									
				if (qtdeAtualizar > 0) {						
					adicionarOrdemProducao(ordem, qtdeAtualizar);
					qtdeNecessaria -= qtdeAtualizar;
				}
			}
		}
	}
	
	private void adicionarOrdemProducao(PainelListaPrioridadesOrdensEmProducao ordem, int qtdeAtualizar) {
		String estagiosDecoracoes = sequenciamentoDecoracoesCustom.findDescEstagiosDecoracoesConcatenadosAProduzir(ordem.getOrdemProducao(), ordem.getOrdemConfeccao());
		ordem.setDescEstagiosDecoracoes(estagiosDecoracoes);
		ordem.setQtdeNecessaria(qtdeAtualizar);
		listOrdensEmProducao.add(ordem);
	}
	
	private void totalizarQtdeEmProducaoNoEstagio(PainelListaPrioridadesOrdensEmProducao ordem) {		
		String chave = ordem.getOrdemProducao() + "." + ordem.getCodEstagio();
		if (!mapTotalEmProducaoPorOrdemEstagio.containsKey(chave)) {
			mapTotalEmProducaoPorOrdemEstagio.put(chave, ordem.getQtdeEmProducaoOrdem());
			if (!mapTotalEmProducaoPorEstagio.containsKey(ordem.getCodEstagio()))			
				mapTotalEmProducaoPorEstagio.put(ordem.getCodEstagio(), ordem.getQtdeEmProducaoOrdem());
			else {
				int qtdeEmProducao = mapTotalEmProducaoPorEstagio.get(ordem.getCodEstagio());
				mapTotalEmProducaoPorEstagio.put(ordem.getCodEstagio(), ordem.getQtdeEmProducaoOrdem() + qtdeEmProducao);
			}
		} 
	}
	
	private int getTotalQtdeEmProducaoNoEstagio(int codEstagio) {
		return mapTotalEmProducaoPorEstagio.get(codEstagio);
	}
	
	private void acumularProducaoPorEstagio() {		
		int totalQtdeEmProducao = 0;
		int totalQtdeNecessaria = 0;				
		// acumula os dados de produção por estágio
		for (PainelListaPrioridadesOrdensEmProducao ordem : listOrdensEmProducao) {
			totalizarQtdeEmProducaoNoEstagio(ordem);
			totalQtdeEmProducao += ordem.getQtdeEmProducaoPacote();
			totalQtdeNecessaria += ordem.getQtdeNecessaria();
			PainelListaPrioridadesEmProducaoPorEstagio ordemPorEstagio;
			if (mapOrdensPorEstagio.containsKey(ordem.getCodEstagio())) {
				ordemPorEstagio = mapOrdensPorEstagio.get(ordem.getCodEstagio());
				ordemPorEstagio.setQtdeEmProducaoEstagio(ordemPorEstagio.getQtdeEmProducaoEstagio() + ordem.getQtdeEmProducaoPacote());
				ordemPorEstagio.setQtdeNecessaria(ordemPorEstagio.getQtdeNecessaria() + ordem.getQtdeNecessaria());
			} else {
				String id = String.format("%02d", ordem.getSeqFilaEstagio()) + String.format("%02d", ordem.getCodEstagio());
				ordemPorEstagio = new PainelListaPrioridadesEmProducaoPorEstagio(id, ordem.getSeqFilaEstagio(), ordem.getCodEstagio(), ordem.getDescEstagio(), 0, ordem.getQtdeEmProducaoPacote(), 0, ordem.getQtdeNecessaria(), 0); 
				mapOrdensPorEstagio.put(ordem.getCodEstagio(), ordemPorEstagio);
			}			
		}		
		// atualiza lista com os dados de produção por estágio
		for (Integer estagio : mapOrdensPorEstagio.keySet()) {
			double percEmProducao = 0;
			double percNecessario = 0;
			int qtdeEmProducaoNoEstagio = getTotalQtdeEmProducaoNoEstagio(estagio);
			PainelListaPrioridadesEmProducaoPorEstagio ordemPorEstagio = mapOrdensPorEstagio.get(estagio);			
			if (totalQtdeEmProducao > 0) percEmProducao = ((double) ordemPorEstagio.getQtdeEmProducaoEstagio() / (double) totalQtdeEmProducao) * 100;
			if (totalQtdeNecessaria > 0) percNecessario = ((double) ordemPorEstagio.getQtdeNecessaria() / (double) totalQtdeNecessaria) * 100;						
			ordemPorEstagio.setPercentualQtdeEmProducao(percEmProducao);			
			ordemPorEstagio.setPercentualQtdeNecessaria(percNecessario);
			ordemPorEstagio.setQtdeEmProducaoOP(qtdeEmProducaoNoEstagio);
			listOrdensPorEstagio.add(ordemPorEstagio);	
		}
	}
}
