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
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.ConsultaPreOrdemProducaoItem;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.model.SugestaoReservaPorOrdem;
import br.com.live.model.SugestaoReservaPorTecido;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;

public class SugestaoReservaTecidoService {

	private final SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom;
	private final PlanoMestreCustom planoMestreCustom;
	private final ProdutoCustom produtoCustom;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;

	private Map<Long, List<ConsultaPreOrdemProducaoItem>> itensPorIdPreOrdem;
	private List<ConsultaPreOrdemProducaoItem> itensParaSugestaoReserva;
	private Map<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>> preOrdensPorDataEmbarque;

	private Map<String, SugestaoReservaPorTecido> mapSugestaoReservaPorTecido;
	private Map<Long, List<SugestaoReservaPorOrdem>> mapSugestaoReservaPorOrdem;

	private String depositosTecidos;

	private List<ConsultaPreOrdemProducao> listaPriorizadaPreOrdens;

	public SugestaoReservaTecidoService(SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom,
			PlanoMestreCustom planoMestreCustom, ProdutoCustom produtoCustom, EstoqueProdutoCustom estoqueProdutoCustom,
			PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository,
			PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository) {
		this.sugestaoReservaTecidoCustom = sugestaoReservaTecidoCustom;
		this.planoMestreCustom = planoMestreCustom;
		this.produtoCustom = produtoCustom;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;
	}

	// TODO - REVISAR SE MANTEM COMO PROPRIEDADE DA CLASSE OU PASSA COMO PARAMETRO
	// AS MAP, ETC...

	public void calcularSugestaoReserva(String planosMestres, String embarques, String referencias, String depositos) {

		depositosTecidos = depositos;
		mapSugestaoReservaPorTecido = new HashMap<String, SugestaoReservaPorTecido>();
		mapSugestaoReservaPorOrdem = new HashMap<Long, List<SugestaoReservaPorOrdem>>();

		listaPriorizadaPreOrdens = planoMestreCustom
				.findPreOrdensOrdenadosPorDataEmbarqueTempoCosturaByPlanosEmbarquesReferencias(planosMestres, embarques,
						referencias);
		calcularNecessidadesTecidos();

		// Calcular a necessidade total de tecidos
		// * ter uma tabela com o total de cada tecido... abater dela posteriormente a
		// reserva.
		// * ter uma tabela com o total de tecido por ordem.
		// Efetuar a reservar dos tecidos para cada OP

		// TODO - REVISAR CODIGO OLD.

		// Ler as pré-ordens calculadas sem OP
		// itensParaSugestaoReserva = planoMestreCustom
		// .findPreOrdensByPlanosEmbarquesReferencias(planosMestres, embarques,
		// referencias);

		// atualizarItensPorIdPreOrdem();

		// TODO - Priorizar as ordens (embarque e tempo produção costura)
		// priorizarPreOrdens();

		// Identificar as necessidades de tecidos (somando pelas necessidades que
		// aparecem nas ordens)
		// Identificar as quantidade disponível de tecido em estoque
		// calcularNecessidadesTecidos(itens, depositos);

		// Sugerir os tecidos conforme as pré-ordens priorizadas

	}

	private void calcularNecessidadesTecidos() {
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {
			System.out
					.println("ID: " + ordem.id + " EMBARQUE: " + ordem.dataEmbarque + " TEMPO: " + ordem.tempoCostura);

			PlanoMestrePreOrdem dadosOrdem = planoMestrePreOrdemRepository.findById(ordem.id);
			List<PlanoMestrePreOrdemItem> itens = planoMestrePreOrdemItemRepository.findByIdOrdem(ordem.id);

			for (PlanoMestrePreOrdemItem item : itens) {
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(ordem.referencia, item.sub,
						item.item, dadosOrdem.alternativa, item.quantidade);
				for (NecessidadeTecidos tecido : tecidos) {
					acumularQtdeSugestaoReservaPorTecido(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(),
							tecido.getItem(), tecido.getQtdeKg());
					acumularQtdeSugestaoReservaPorOrdem(ordem.id, tecido.getNivel(), tecido.getGrupo(), tecido.getSub(),
							tecido.getItem(), tecido.getQtdeKgUnit(), tecido.getQtdeKg());
				}
			}
		}
	}

	private void acumularQtdeSugestaoReservaPorTecido(String nivelTecido, String grupoTecido, String subTecido,
			String itemTecido, double qtdeKg) {

		String codTecido = nivelTecido + "." + grupoTecido + "." + subTecido + "." + itemTecido;

		if (mapSugestaoReservaPorTecido.containsKey(codTecido)) {
			SugestaoReservaPorTecido sugestao = mapSugestaoReservaPorTecido.get(codTecido);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);
		} else {
			double qtdeDisponivel = estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(nivelTecido, grupoTecido,
					subTecido, itemTecido, depositosTecidos);
			SugestaoReservaPorTecido sugestao = new SugestaoReservaPorTecido(nivelTecido, grupoTecido, subTecido,
					itemTecido, "", "", qtdeKg, qtdeDisponivel, 0, 0);
			mapSugestaoReservaPorTecido.put(codTecido, sugestao);
		}
	}

	private void acumularQtdeSugestaoReservaPorOrdem(long idOrdem, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, double qtdeKgUnit, double qtdeKg) {

		List<SugestaoReservaPorOrdem> tecidos;
		SugestaoReservaPorOrdem sugestao;

		if (mapSugestaoReservaPorOrdem.containsKey(idOrdem)) {

			tecidos = mapSugestaoReservaPorOrdem.get(idOrdem);

			List<SugestaoReservaPorOrdem> filtro = (List<SugestaoReservaPorOrdem>) tecidos.stream()
					.filter(p -> p.getNivel() == nivelTecido && p.getGrupo() == grupoTecido && p.getSub() == subTecido
							&& p.getItem() == itemTecido);

			if (filtro.size() == 1) {
				sugestao = filtro.get(0);
				sugestao.setQtdeNecessidadeUnit(sugestao.getQtdeNecessidadeUnit() + qtdeKgUnit);
				sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);
			} else {
				sugestao = new SugestaoReservaPorOrdem(idOrdem, nivelTecido, grupoTecido, subTecido, itemTecido, "", "",
						qtdeKg, 0, 0, 0, qtdeKgUnit);
				tecidos.add(sugestao);
			}
		} else {

			tecidos = new ArrayList<SugestaoReservaPorOrdem>();
			sugestao = new SugestaoReservaPorOrdem(idOrdem, nivelTecido, grupoTecido, subTecido, itemTecido, "", "",
					qtdeKg, 0, 0, 0, qtdeKgUnit);
			tecidos.add(sugestao);
			
			mapSugestaoReservaPorOrdem.put(idOrdem, tecidos);
		}
		
	}

	// #######################################################################
	// TODO - REVISAR METODOS QUE IRÃO PERMANECER DEPOIS DOS ULTIMOS AJUSTES.
	// #######################################################################

	private void atualizarItensPorIdPreOrdem() {

		itensPorIdPreOrdem = new HashMap<Long, List<ConsultaPreOrdemProducaoItem>>();
		List<ConsultaPreOrdemProducaoItem> listaItens;

		for (ConsultaPreOrdemProducaoItem item : itensParaSugestaoReserva) {
			if (itensPorIdPreOrdem.containsKey(item.id))
				listaItens = itensPorIdPreOrdem.get(item.id);
			else
				listaItens = new ArrayList<ConsultaPreOrdemProducaoItem>();
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

		preOrdensPorDataEmbarque = new HashMap<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>>();

		for (ConsultaPreOrdemProducaoItem item : itensParaSugestaoReserva) {
			addPreOrdensPorDataEmbarque(item);
		}

		List<Entry<Date, Map<Long, List<ConsultaPreOrdemProducaoItem>>>> datas = new ArrayList<>(
				preOrdensPorDataEmbarque.entrySet());
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
					// tempoCostura += produtoCustom.findTempoCostura(sugestao.getGrupo(),
					// sugestao.getSub(), sugestao.getItem());
				}

				// TODO - FINALIZAR
			}
		}

	}

}