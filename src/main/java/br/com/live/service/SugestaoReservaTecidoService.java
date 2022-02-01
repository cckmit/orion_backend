package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.model.SugestaoReservaPorOrdem;
import br.com.live.model.SugestaoReservaPorTecido;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;

@Service
@Transactional
public class SugestaoReservaTecidoService {

	private final PlanoMestreCustom planoMestreCustom;
	private final ProdutoCustom produtoCustom;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;

	private String depositosTecidos;
	private List<ConsultaPreOrdemProducao> listaPriorizadaPreOrdens;
	private Map<String, SugestaoReservaPorTecido> mapDadosPorTecido;
	private Map<Long, List<SugestaoReservaPorOrdem>> mapDadosPorOrdem;

	public SugestaoReservaTecidoService(PlanoMestreCustom planoMestreCustom, ProdutoCustom produtoCustom, 
			EstoqueProdutoCustom estoqueProdutoCustom,
			PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository,
			PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository) {		
		this.planoMestreCustom = planoMestreCustom;
		this.produtoCustom = produtoCustom;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;
	}

	public void calcularSugestaoReserva(String planosMestres, String embarques, String referencias, String depositos) {
		depositosTecidos = depositos;
		mapDadosPorTecido = new HashMap<String, SugestaoReservaPorTecido>();
		mapDadosPorOrdem = new HashMap<Long, List<SugestaoReservaPorOrdem>>();
		
		listaPriorizadaPreOrdens = planoMestreCustom
				.findPreOrdensOrdenadosPorDataEmbarqueQtdeEstagCriticoTempoProducao(planosMestres, embarques,
						referencias);
		
		calcular();
		//reservarTecidos();		
	}

	/*
	private void reservarTecidos() {
		System.out.println("reservarTecidos");
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {			
			List<SugestaoReservaPorOrdem> tecidosOrdem = mapDadosPorOrdem.get(ordem.id);			
			for (SugestaoReservaPorOrdem tecidoOrdem : tecidosOrdem) {
				reservaTecidoParaOrdem(tecidoOrdem);
			}
		}
	}
	*/
			
	// TODO - DEVE PRIMEIRO VERIFICAR SE TODOS OS TECIDOS NECESSÁRIOS PARA A ORDEM TEM QTDE DISPONIVEL
	// CASO ALGUM ESTEJA ZERADO DEVE PROCESSEGUIR PARA PRÓXIMA ORDEM SE EFETUAR A RESERVA
	// CASO ALGUMA TENHA QTDE MENOR, DEVE RECALCULAR A ORDEM COM QTDE MENOR PARA VERIFICAR O QUANTO ATENDE (DIVIDIR PELA QTDE UNITARIA).
	// PARA FACILITAR DEVERÁ TER TABELA DE RELACIONAMENTO: ORDEM, REF, TAM, COR, NIV_TEC, GRU_TEC, SUB_TEC, ITEM_TEC, QTDE_PREV, QTDE_KG_UNIT, QTDE_KG_NECESS. 	
	
	private void reservaTecidoParaOrdem(SugestaoReservaPorOrdem tecidoOrdem) {				
		double qtdeReservada = 0;
		double qtdeReservar = tecidoOrdem.getQtdeNecessidade();
		
		String codTecido = tecidoOrdem.getNivel() + "." + tecidoOrdem.getGrupo() + "." + tecidoOrdem.getSub() + "." +  tecidoOrdem.getItem();
		SugestaoReservaPorTecido tecido = mapDadosPorTecido.get(codTecido);
		
		if (qtdeReservar < tecido.getQtdeSaldo())
			qtdeReservada = qtdeReservar;
		else qtdeReservada = tecido.getQtdeSaldo();
		
		tecido.setQtdeSugerido(tecido.getQtdeSugerido() + qtdeReservada);		
		tecidoOrdem.setQtdeDisponivel(tecido.getQtdeSaldo());
		tecidoOrdem.setQtdeSugerido(tecidoOrdem.getQtdeSugerido() + qtdeReservada);		
	}	
	
	private void calcular() {
		System.out.println("calcularNecessidadesTecidos");
		
		int ordemReserva = 0;
		
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {						
			ordemReserva++;
			
			System.out.println("PRIORIDADE: " + ordemReserva + " ID: " + ordem.id + " EMBARQUE: " + ordem.dataEmbarque + " QTDE EST CRITICO: " + ordem.qtdeEstagioCritico + " TEMPO PROD UNIT: " + ordem.tempoProducaoUnit);

			PlanoMestrePreOrdem dadosOrdem = planoMestrePreOrdemRepository.findById(ordem.id);
			List<PlanoMestrePreOrdemItem> itens = planoMestrePreOrdemItemRepository.findByIdOrdem(ordem.id);

			for (PlanoMestrePreOrdemItem item : itens) {
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(ordem.referencia, item.sub,
						item.item, dadosOrdem.alternativa, item.quantidade);
				for (NecessidadeTecidos tecido : tecidos) {					
					double qtdeEstoque = estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), depositosTecidos);
					
					double qtdeEmpenhada = estoqueProdutoCustom.findQtdeEmpenhadaByProduto(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem());
					
					guardarDadosPorTecido(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(),
							tecido.getItem(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
					guardarDadosPorOrdem(ordemReserva, ordem.id, tecido.getSequencia(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(),
							tecido.getItem(), tecido.getQtdeKgUnit(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
				}
			}
		}
	}
 
	private void guardarDadosPorProdutoOrdem(long idOrde, int sequencia, String grupo, String tamanho, String cor, int quantidade, String nivelTecido, String grupoTecido, String subTecido,
			String itemTecido, double qtdeKg) {
		
		
		
		
	}
	
	private void guardarDadosPorTecido(String nivelTecido, String grupoTecido, String subTecido,
			String itemTecido, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {

		String codTecido = nivelTecido + "." + grupoTecido + "." + subTecido + "." + itemTecido;

		if (mapDadosPorTecido.containsKey(codTecido)) {
			SugestaoReservaPorTecido sugestao = mapDadosPorTecido.get(codTecido);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);
		} else {
			SugestaoReservaPorTecido sugestao = new SugestaoReservaPorTecido(nivelTecido, grupoTecido, subTecido,
					itemTecido, "", "", qtdeKg, qtdeEstoque, qtdeEmpenhada, 0);
			mapDadosPorTecido.put(codTecido, sugestao);
		}
	}

	// TODO - 2 CORES PODEM USAR O MESMO TECIDO, DA MANEIRA COMO ESTÁ VAI TENTAR GRAVAR 2X E VAI MANTER APENAS A ULTIMA GRAVADA 
	// REMOVER SEQUENCIA. NÃO VAI PRECISAR... AGRUPAR POR TECIDO.
	
	private void guardarDadosPorOrdem(int ordemReserva, long idOrdem, int sequencia, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, double qtdeKgUnit, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {

		List<SugestaoReservaPorOrdem> tecidos;		
		
		if (mapDadosPorOrdem.containsKey(idOrdem)) {
			tecidos = mapDadosPorOrdem.get(idOrdem);
		} else {
			tecidos = new ArrayList<SugestaoReservaPorOrdem>();	
		}		
		
		SugestaoReservaPorOrdem sugestao = new SugestaoReservaPorOrdem(ordemReserva, idOrdem, sequencia, nivelTecido, grupoTecido, subTecido, itemTecido, "", "",
				qtdeKg, qtdeEstoque, qtdeEmpenhada, 0, qtdeKgUnit);
		
		tecidos.add(sugestao);
		
		mapDadosPorOrdem.put(idOrdem, tecidos);
	}
}