package br.com.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SugestaoReservaTecidoCustom;
import br.com.live.model.ConsultaPreOrdemProducaoItem;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.model.SugestaoReservaPorTecido;

public class SugestaoReservaTecidoService {

	private final SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom;
	private final PlanoMestreCustom planoMestreCustom;
	private final ProdutoCustom produtoCustom;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
		
	private Map<Long, List<ConsultaPreOrdemProducaoItem>> itensPorIdPreOrdem; 
	private List<ConsultaPreOrdemProducaoItem> itensParaSugestaoReserva;
	private Map<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>> preOrdensPorDataEmbarque;
	private Map<String, SugestaoReservaPorTecido> mapSugestaoReservaPorTecido; 
	private String depositosTecidos;
	
	public SugestaoReservaTecidoService(SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom,
			PlanoMestreCustom planoMestreCustom, ProdutoCustom produtoCustom,
			EstoqueProdutoCustom estoqueProdutoCustom) {
		this.sugestaoReservaTecidoCustom = sugestaoReservaTecidoCustom;
		this.planoMestreCustom = planoMestreCustom;
		this.produtoCustom = produtoCustom;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
	}

	public void calcularSugestaoReserva(String planosMestres, String embarques, String referencias, String depositos) {

		mapSugestaoReservaPorTecido = new HashMap<String, SugestaoReservaPorTecido>();
		depositosTecidos = depositos;

		
		planoMestreCustom.findPreOrdensOrdenadosPorDataEmbarqueTempoCosturaByPlanosEmbarquesReferencias(planosMestres, embarques, referencias);
		
		// Ler as pré-ordens calculadas sem OP
		//itensParaSugestaoReserva = planoMestreCustom
		//		.findPreOrdensByPlanosEmbarquesReferencias(planosMestres, embarques, referencias);

		atualizarItensPorIdPreOrdem();
		
		
		// TODO - Priorizar as ordens (embarque e tempo produção costura)
		priorizarPreOrdens();
		
		// Identificar as necessidades de tecidos (somando pelas necessidades que aparecem nas ordens)
		// Identificar as quantidade disponível de tecido em estoque
		//calcularNecessidadesTecidos(itens, depositos);

		// Sugerir os tecidos conforme as pré-ordens priorizadas

	}

	private void atualizarItensPorIdPreOrdem() {
		
		itensPorIdPreOrdem = new HashMap<Long, List<ConsultaPreOrdemProducaoItem>>();
		List<ConsultaPreOrdemProducaoItem> listaItens;
		
		for (ConsultaPreOrdemProducaoItem item : itensParaSugestaoReserva) {			
			if (itensPorIdPreOrdem.containsKey(item.id)) listaItens = itensPorIdPreOrdem.get(item.id);
			else listaItens = new ArrayList<ConsultaPreOrdemProducaoItem>();			
			listaItens.add(item);
			itensPorIdPreOrdem.put(item.id, listaItens);
		}
	}
	
	private void priorizarPreOrdens() {
		
		efetuarOrdenacaoPrincipalPorDataEmbarque();
		efetuarOrdenacaoSecundariaPorTempoCostura();
		
		
				
	}
	
	private void addPreOrdensPorDataEmbarque(ConsultaPreOrdemProducaoItem item) {
		
		Map<Long, List<ConsultaPreOrdemProducaoItem>> ordens = null;
		List<ConsultaPreOrdemProducaoItem> lista = null;
		
		if (preOrdensPorDataEmbarque.containsKey(item.dataEmbarque)) {			
			ordens = preOrdensPorDataEmbarque.get(item.getDataEmbarque());
		} else {
			ordens = new HashMap<Long, List<ConsultaPreOrdemProducaoItem>>();
		}
		
		if (ordens.containsKey(item.id)) {
			lista = ordens.get(item.id);
		} else {
			lista = new ArrayList<ConsultaPreOrdemProducaoItem>(); 
		}
		
		lista.add(item);
		ordens.put(item.id, lista);
		
		preOrdensPorDataEmbarque.put(item.dataEmbarque, ordens);
	}
	
	private void efetuarOrdenacaoPrincipalPorDataEmbarque() {
		
		preOrdensPorDataEmbarque = new HashMap<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>> ();

		for (ConsultaPreOrdemProducaoItem item : itensParaSugestaoReserva) {			
			addPreOrdensPorDataEmbarque(item);
		}
		
		List<Entry<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>>> datas = new ArrayList<>(preOrdensPorDataEmbarque.entrySet());
		datas.sort(Entry.comparingByKey());

		preOrdensPorDataEmbarque = (Map<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>>) datas;			
	}
	
	private void efetuarOrdenacaoSecundariaPorTempoCostura() {
		
		Map<Double, Long> preOrdensPorTempoCostura;
		Map<Long, List<ConsultaPreOrdemProducaoItem>> ordens = null;
		
		for (Date dataEmbarque : preOrdensPorDataEmbarque.keySet()) {
			ordens = preOrdensPorDataEmbarque.get(dataEmbarque);
			
			for (Long idPreOrdem : ordens.keySet()) {				
				List<ConsultaPreOrdemProducaoItem> lista = ordens.get(idPreOrdem);
				
				double tempoCostura = 0;
				
				for (ConsultaPreOrdemProducaoItem item : lista) {
					//tempoCostura += produtoCustom.findTempoCostura(sugestao.getGrupo(), sugestao.getSub(), sugestao.getItem()); 
				}
				
				// TODO - FINALIZAR
			}
		}
		
	}	
	
	private void calcularNecessidadesTecidos(List<ConsultaPreOrdemProducaoItem> itens, String depositos) {
		for (ConsultaPreOrdemProducaoItem item : itens) {
			List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(item.referencia, item.tamanho,
					item.cor, item.alternativaItem, item.quantidade);
			
			for (NecessidadeTecidos tecido : tecidos) {												
				acumularQtdeSugestaoReservaPorTecido(new SugestaoReservaPorTecido(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), "", "", tecido.getQtdeKg(), 0, 0, 0));
			}
		}
	}
	
	private void acumularQtdeSugestaoReservaPorTecido(SugestaoReservaPorTecido sugestao) {
		
		String codTecido = sugestao.getNivel() + "." + sugestao.getGrupo() + "." + sugestao.getSub() + "." + sugestao.getItem();
		
		// pegar o tempo de costura
		
		//double tempoCostura = produtoCustom.findTempoCostura(sugestao.getGrupo(), sugestao.getSub(), sugestao.getItem());
		
		if (mapSugestaoReservaPorTecido.containsKey(codTecido)) {
			SugestaoReservaPorTecido sugestaoGravada = mapSugestaoReservaPorTecido.get(codTecido); 
			sugestaoGravada.setQtdeNecessidade(sugestaoGravada.getQtdeNecessidade() + sugestao.getQtdeNecessidade());
		} else {
			sugestao.setQtdeDisponivel(estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(sugestao.getNivel(), sugestao.getGrupo(), sugestao.getSub(), sugestao.getItem(), depositosTecidos));
			mapSugestaoReservaPorTecido.put(codTecido, sugestao);
		}
	}
		
}