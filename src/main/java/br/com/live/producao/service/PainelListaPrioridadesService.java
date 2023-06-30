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
	Map<String, PainelListaPrioridadesAnaliseCarteira> mapAnaliseCarteira;
	List<PainelListaPrioridadesCarteiraPedidos> listCarteiraPedidos; 
	List<PainelListaPrioridadesAnaliseCarteira> listAnaliseCarteira; 
	List<PainelListaPrioridadesOrdensEmProducao> listOrdensEmProducao;
	Map<Integer, PainelListaPrioridadesOrdensPorEstagio> mapOrdensPorEstagio;
	List<PainelListaPrioridadesOrdensPorEstagio> listOrdensPorEstagio;
	Map<String, Integer> mapQtdeEmProducaoPorOrdemEstagio;
	
	public PainelListaPrioridadesService(PainelListaPrioridadesCustom painelListaPrioridadesCustom) {
		this.painelListaPrioridadesCustom = painelListaPrioridadesCustom;
		this.mapAnaliseCarteira = new TreeMap<String, PainelListaPrioridadesAnaliseCarteira>();
		this.mapOrdensPorEstagio = new TreeMap<Integer, PainelListaPrioridadesOrdensPorEstagio>(); 
		this.listCarteiraPedidos = new ArrayList<PainelListaPrioridadesCarteiraPedidos> ();
		this.listAnaliseCarteira = new ArrayList<PainelListaPrioridadesAnaliseCarteira>();
		this.listOrdensEmProducao = new ArrayList<PainelListaPrioridadesOrdensEmProducao>();
		this.listOrdensPorEstagio = new ArrayList<PainelListaPrioridadesOrdensPorEstagio>();
	}

	public PainelListaPrioridades findListaPrioridadesAtendimento(BodyPainelListaPrioridades parametros) {				
		System.out.println("findListaPrioridadesAtendimento - inicio: " + new Date());
		analisarCarteiraEmAberto(parametros);
		gerarCarteiraOrdenadaPorProduto(parametros.produtosComSobra, parametros.produtoscomFalta, parametros.produtosFaltaSemProducao);
		analisarOrdensEmProducao(parametros);	
		acumularProducaoPorEstagio();
		System.out.println("findListaPrioridadesAtendimento - fim: " + new Date());		
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
			itemAnaliseCarteira = new PainelListaPrioridadesAnaliseCarteira(item.getReferencia(), item.getDescReferencia(), item.getTamanho(), item.getCor(), item.getDescCor(), item.getQtdeCarteira(), item.getQtdeEstoque(), item.getQtdeEmProducao(), item.getQtdeSugerida(), item.getQtdeColetada());
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
			if (itemCarteira.getQtdeEstoque() < 0) qtdeNecessaria = itemCarteira.getQtdeEstoque() * -1;
						
			List<PainelListaPrioridadesOrdensEmProducao> ordens = painelListaPrioridadesCustom.findOrdensEmProducao(itemCarteira.getReferencia(), itemCarteira.getTamanho(), itemCarteira.getCor(), parametros.periodoProdInicio, parametros.periodoProdFim, ConteudoChaveNumerica.parseValueToString(parametros.listPedidosOrdens), ConteudoChaveNumerica.parseValueToString(parametros.listFamilias), ConteudoChaveNumerica.parseValueToString(parametros.listFaccoes));
			
			if (ordens.size() > 0) System.out.println("ordens: " + ordens.size());
			
			for (PainelListaPrioridadesOrdensEmProducao ordem : ordens) {				

				// atualiza a qtde coletada e sugerida conforme a analise da carteira
				ordem.setQtdeColetada(itemCarteira.getQtdeColetada());
				ordem.setQtdeSugerida(itemCarteira.getQtdeSugerida());
				
				if (itemCarteira.getQtdeEstoque() < 0) {				
					if (ordem.getQtdeEmProducaoPacote() < qtdeNecessaria)
						qtdeNecessaria -= ordem.getQtdeEmProducaoPacote();
					else qtdeNecessaria -= qtdeNecessaria;
										
					if (qtdeNecessaria > 0) {
						ordem.setQtdeNecessaria(qtdeNecessaria);
						listOrdensEmProducao.add(ordem);
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
				ordemPorEstagio.setQtdeEmProducaoEstagio(ordem.getQtdeEmProducaoPacote());
				ordemPorEstagio.setQtdeNecessaria(ordem.getQtdeNecessaria());
			} else {
				ordemPorEstagio = new PainelListaPrioridadesOrdensPorEstagio(ordem.getSeqFilaEstagio(), ordem.getCodEstagio(), ordem.getDescEstagio(), 0, ordem.getQtdeEmProducaoPacote(), 0, ordem.getQtdeNecessaria(), 0); 
			}			
		}		
		// atualiza lista com os dados de produção por estágio
		for (Integer estagio : mapOrdensPorEstagio.keySet()) {
			int qtdeEmProducaoNoEstagio = painelListaPrioridadesCustom.findQtdeEmProducaoNoEstagio(estagio);
			PainelListaPrioridadesOrdensPorEstagio ordemPorEstagio = mapOrdensPorEstagio.get(estagio);
			ordemPorEstagio.setPercentualQtdeEmProducao(((double) ordemPorEstagio.getQtdeEmProducaoOP() / (double) totalQtdeEmProducaoEstagio) * 100);
			ordemPorEstagio.setPercentualQtdeNecessaria(((double) ordemPorEstagio.getQtdeNecessaria() / (double) totalQtdeNecessaria) * 100);
			ordemPorEstagio.setQtdeEmProducaoOP(qtdeEmProducaoNoEstagio);
			listOrdensPorEstagio.add(ordemPorEstagio);	
		}
	}
}
