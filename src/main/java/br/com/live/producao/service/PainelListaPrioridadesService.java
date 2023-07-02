package br.com.live.producao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.producao.body.BodyPainelListaPrioridades;
import br.com.live.producao.custom.PainelListaPrioridadesCustom;
import br.com.live.producao.model.PainelListaPrioridades;
import br.com.live.producao.model.PainelListaPrioridadesAnaliseCarteira;
import br.com.live.producao.model.PainelListaPrioridadesCarteiraPedidos;
import br.com.live.producao.model.PainelListaPrioridadesOrdensEmProducao;
import br.com.live.producao.model.PainelListaPrioridadesOrdensPorEstagio;
import br.com.live.util.CodigoProduto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Service
@Transactional
public class PainelListaPrioridadesService {

	private final PainelListaPrioridadesCustom painelListaPrioridadesCustom;
	private Map<String, PainelListaPrioridadesAnaliseCarteira> mapAnaliseCarteira;
	private List<PainelListaPrioridadesCarteiraPedidos> listCarteiraPedidos; 
	private List<PainelListaPrioridadesAnaliseCarteira> listAnaliseCarteira; 
	private List<PainelListaPrioridadesOrdensEmProducao> listOrdensEmProducao;
	private Map<Integer, PainelListaPrioridadesOrdensPorEstagio> mapOrdensPorEstagio;
	private List<PainelListaPrioridadesOrdensPorEstagio> listOrdensPorEstagio;
	
	public PainelListaPrioridadesService(PainelListaPrioridadesCustom painelListaPrioridadesCustom) {
		this.painelListaPrioridadesCustom = painelListaPrioridadesCustom;
	}

	public void inicializarListas() {
		this.mapAnaliseCarteira = new TreeMap<String, PainelListaPrioridadesAnaliseCarteira>();
		this.mapOrdensPorEstagio = new TreeMap<Integer, PainelListaPrioridadesOrdensPorEstagio>(); 
		this.listCarteiraPedidos = new ArrayList<PainelListaPrioridadesCarteiraPedidos> ();
		this.listAnaliseCarteira = new ArrayList<PainelListaPrioridadesAnaliseCarteira>();
		this.listOrdensEmProducao = new ArrayList<PainelListaPrioridadesOrdensEmProducao>();
		this.listOrdensPorEstagio = new ArrayList<PainelListaPrioridadesOrdensPorEstagio>();		
	}
	
	public PainelListaPrioridades findListaPrioridadesAtendimento(BodyPainelListaPrioridades parametros) {				
		System.out.println("findListaPrioridadesAtendimento - inicio: " + new Date());
		
		inicializarListas();
		analisarCarteiraEmAberto(parametros);
		gerarCarteiraOrdenadaPorProduto(parametros.produtosComSobra, parametros.produtoscomFalta, parametros.produtosFaltaSemProducao);
		analisarOrdensEmProducao(parametros);	
		acumularProducaoPorEstagio();
		
		System.out.println("findListaPrioridadesAtendimento - fim: " + new Date());		
		
		System.out.println("listCarteiraPedidos: " + listCarteiraPedidos.size());
		System.out.println("listAnaliseCarteira: " + listAnaliseCarteira.size());
		System.out.println("listOrdensEmProducao: " + listOrdensEmProducao.size());
		System.out.println("listOrdensPorEstagio: " + listOrdensPorEstagio.size());
		
		return new PainelListaPrioridades(listCarteiraPedidos, listAnaliseCarteira, listOrdensEmProducao, listOrdensPorEstagio);
	}

	private void analisarCarteiraEmAberto(BodyPainelListaPrioridades parametros) {
		System.out.println("analisarCarteiraEmAberto - " + new Date());
		listCarteiraPedidos = painelListaPrioridadesCustom.findCarteiraPedidos(FormataData.parseStringToDate(parametros.dtEmbarqueInicio), FormataData.parseStringToDate(parametros.dtEmbarqueFim), ConteudoChaveNumerica.parseValueToString(parametros.listPedidosComerciais), ConteudoChaveNumerica.parseValueToString(parametros.listPedidosOrdens), ConteudoChaveNumerica.parseValueToString(parametros.listSituacoesPedidos), ConteudoChaveNumerica.parseValueToString(parametros.listNumerosInterno),
				parametros.periodoProdInicio, parametros.periodoProdFim, 
				ConteudoChaveNumerica.parseValueToString(parametros.listDepositosEstoques), 
				ConteudoChaveNumerica.parseValueToString(parametros.listDepositosPedidos), 
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
			//System.out.println(item.getPedidoVenda() + " - " + item.getReferencia() + " - " + item.getQtdeCarteira());			
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
		System.out.println("gerarCarteiraOrdenadaPorProduto - " + new Date());
		for (String produto : mapAnaliseCarteira.keySet()) {
			PainelListaPrioridadesAnaliseCarteira itemAnaliseCarteira = mapAnaliseCarteira.get(produto);
			
			if (((produtosComSobra)&&(itemAnaliseCarteira.getQtdeSobraFalta()>0))||
				((produtoscomFalta)&&(itemAnaliseCarteira.getQtdeSobraFalta()<0))||	
				((produtosFaltaSemProducao)&&(itemAnaliseCarteira.getQtdeSobraFalta()<0)&&(itemAnaliseCarteira.getQtdeEmProducao()==0))) 			
				listAnaliseCarteira.add(itemAnaliseCarteira);			
		}
	}
	
	private void analisarOrdensEmProducao(BodyPainelListaPrioridades parametros) {		
		System.out.println("analisarOrdensEmProducao - " + new Date());
		for (PainelListaPrioridadesAnaliseCarteira itemCarteira : listAnaliseCarteira) {			
						
			int qtdeNecessaria = 0;
			int qtdeAtualizar = 0;
			if (itemCarteira.getQtdeSobraFalta() < 0) qtdeNecessaria = itemCarteira.getQtdeSobraFalta() * -1;
						
			List<PainelListaPrioridadesOrdensEmProducao> ordens = painelListaPrioridadesCustom.findOrdensEmProducao(itemCarteira.getReferencia(), itemCarteira.getTamanho(), itemCarteira.getCor(), parametros.periodoProdInicio, parametros.periodoProdFim, ConteudoChaveNumerica.parseValueToString(parametros.listPedidosOrdens), ConteudoChaveNumerica.parseValueToString(parametros.listFamilias), ConteudoChaveNumerica.parseValueToString(parametros.listFaccoes));			
			//System.out.println("ordens: " + ordens.size());
			
			for (PainelListaPrioridadesOrdensEmProducao ordem : ordens) {				

				// atualizar os dados do produto originados da analise da carteira.
				ordem.setQtdeCarteira(itemCarteira.getQtdeCarteira());
				ordem.setQtdeEstoque(itemCarteira.getQtdeEstoque());
				ordem.setQtdeSobraFalta(itemCarteira.getQtdeSobraFalta());
				ordem.setQtdeColetada(itemCarteira.getQtdeColetada());
				ordem.setQtdeSugerida(itemCarteira.getQtdeSugerida());
				
				if (itemCarteira.getQtdeSobraFalta() < 0) {				
					if (ordem.getQtdeEmProducaoPacote() < qtdeNecessaria)
						qtdeAtualizar = ordem.getQtdeEmProducaoPacote();
					else qtdeAtualizar = qtdeNecessaria;
										
					if (qtdeAtualizar > 0) {
						ordem.setQtdeNecessaria(qtdeAtualizar);
						listOrdensEmProducao.add(ordem);
						qtdeNecessaria -= qtdeAtualizar;
					}
				} else if (parametros.produtosComSobra) {
					ordem.setQtdeEmProducaoPacote(0);
					listOrdensEmProducao.add(ordem);
				}									
			}
		}
	}
	
	private void acumularProducaoPorEstagio() {
		System.out.println("acumularProducaoPorEstagio - " + new Date());
		int totalQtdeEmProducaoEstagio = 0;
		int totalQtdeNecessaria = 0;		
		// acumula os dados de produção por estágio
		for (PainelListaPrioridadesOrdensEmProducao ordem : listOrdensEmProducao) {
			totalQtdeEmProducaoEstagio += ordem.getQtdeEmProducaoPacote();
			totalQtdeNecessaria += ordem.getQtdeNecessaria();
			PainelListaPrioridadesOrdensPorEstagio ordemPorEstagio;
			if (mapOrdensPorEstagio.containsKey(ordem.getCodEstagio())) {
				ordemPorEstagio = mapOrdensPorEstagio.get(ordem.getCodEstagio());
				ordemPorEstagio.setQtdeEmProducaoEstagio(ordemPorEstagio.getQtdeEmProducaoEstagio() + ordem.getQtdeEmProducaoPacote());
				ordemPorEstagio.setQtdeNecessaria(ordemPorEstagio.getQtdeNecessaria() + ordem.getQtdeNecessaria());
			} else {
				ordemPorEstagio = new PainelListaPrioridadesOrdensPorEstagio(ordem.getId(), ordem.getSeqFilaEstagio(), ordem.getCodEstagio(), ordem.getDescEstagio(), 0, ordem.getQtdeEmProducaoPacote(), 0, ordem.getQtdeNecessaria(), 0); 
				mapOrdensPorEstagio.put(ordem.getCodEstagio(), ordemPorEstagio);
			}			
		}		
		// atualiza lista com os dados de produção por estágio
		for (Integer estagio : mapOrdensPorEstagio.keySet()) {
			int qtdeEmProducaoNoEstagio = painelListaPrioridadesCustom.findQtdeEmProducaoNoEstagio(estagio);
			PainelListaPrioridadesOrdensPorEstagio ordemPorEstagio = mapOrdensPorEstagio.get(estagio);
			
			System.out.println("totalQtdeEmProducaoEstagio: " + totalQtdeEmProducaoEstagio);
			System.out.println("ordemPorEstagio.getQtdeEmProducaoEstagio(): " + ordemPorEstagio.getQtdeEmProducaoEstagio());
			System.out.println(((double) ordemPorEstagio.getQtdeEmProducaoEstagio() / (double) totalQtdeEmProducaoEstagio) * 100);
			
			ordemPorEstagio.setPercentualQtdeEmProducao(((double) ordemPorEstagio.getQtdeEmProducaoEstagio() / (double) totalQtdeEmProducaoEstagio) * 100);
			ordemPorEstagio.setPercentualQtdeNecessaria(((double) ordemPorEstagio.getQtdeNecessaria() / (double) totalQtdeNecessaria) * 100);
			ordemPorEstagio.setQtdeEmProducaoOP(qtdeEmProducaoNoEstagio);
			listOrdensPorEstagio.add(ordemPorEstagio);	
		}
	}
}
