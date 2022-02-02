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
import br.com.live.model.PreOrdemProducaoItem;
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
	private Map<Long, List<PreOrdemProducaoItem>> mapOrdensRecalculadas;	

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
		mapOrdensRecalculadas = new HashMap<Long, List<PreOrdemProducaoItem>>();	
		
		listaPriorizadaPreOrdens = planoMestreCustom
				.findPreOrdensOrdenadosPorDataEmbarqueQtdeEstagCriticoTempoProducao(planosMestres, embarques,
						referencias);
		
		calcularNecessidades();
		reservarTecidos();		
	}
	
	private void reservarTecidos() {
		System.out.println("reservarTecidos");
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {			
			List<SugestaoReservaPorOrdem> tecidosOrdem = mapDadosPorOrdem.get(ordem.id);			
			for (SugestaoReservaPorOrdem tecidoOrdem : tecidosOrdem) {
				reservaTecidoParaOrdem(tecidoOrdem);
			}
		}
	}
			
	// TODO - REVISAR NOME METODO
	private void confirmarQtdeTecidoSuficiente(long idOrdem) {
		
		// TODO - VERIFICAR COMO DEVE SER A CONTA
		// VERIFICAR A CONTA PARA DIMININUIR PROPORCIONALMENTE PARA TODOS OS TAMANHOS DA COR
		// RECALCULAR TODA A NECESSIDADE DA ORDEM NOVAMENTE COM BASE NA NOVA QTDE
		
		Map<String, Double> mapTecidosXQtdeNecessaria = new HashMap<String, Double>();

		List<SugestaoReservaPorOrdem> tecidos = mapDadosPorOrdem.get(idOrdem);

		String [] conteudo;
		double qtdeNecessidade;
		double diferenca;
		double percentual;
		
		for (SugestaoReservaPorOrdem tecido : tecidos) {
			String chave = tecido.getNivel() + "." + tecido.getGrupo() + "." + tecido.getSub() + "." +  tecido.getItem();
			
			qtdeNecessidade = 0;	
			
			if (mapTecidosXQtdeNecessaria.containsKey(chave)) qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);
			
			qtdeNecessidade += tecido.getQtdeNecessidade(); 
			
			mapTecidosXQtdeNecessaria.put(chave, qtdeNecessidade);
		}
		
		double percentualDiminuirPecas = 0.0;
		String codTecidoRecalcular = "";
		
		for (String chave : mapTecidosXQtdeNecessaria.keySet()) {
			conteudo = chave.split("[.]");
			String codTecido = conteudo[0] + "." + conteudo[1] + "." + conteudo[2] + "." + conteudo[3];
		
			qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);
			SugestaoReservaPorTecido dadosTecido = mapDadosPorTecido.get(codTecido);
			 			
			if (qtdeNecessidade > dadosTecido.getQtdeSaldo()) {
			
				diferenca = qtdeNecessidade - dadosTecido.getQtdeSaldo(); 
				
				percentual = (diferenca / dadosTecido.getQtdeSaldo()) * 100;
				
				if (percentual > percentualDiminuirPecas) {				
					codTecidoRecalcular = codTecido;
					percentualDiminuirPecas = percentual; 
				}
			}
		}		
		
		// Recalcula as quantidades com base no tecido que possui o percentual mais alto.
		if (!codTecidoRecalcular.equals("")) {
			conteudo = codTecidoRecalcular.split("[.]");
			// enviar o percentual
			recalcularQtdePecasDaOrdem(idOrdem, conteudo[0], conteudo[1], conteudo[2], conteudo[3], percentualDiminuirPecas);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void recalcularQtdePecasDaOrdem(long idOrdem, String nivelTecido, String grupoTecido, String subTecido, String itemTecido, double percentualDiminuir) {
		
		List<String> sortimentos = new ArrayList<String>();
		List<PreOrdemProducaoItem> itensRecalculados = new ArrayList<PreOrdemProducaoItem>();
		List<SugestaoReservaPorOrdem> tecidos = mapDadosPorOrdem.get(idOrdem);				
		List<SugestaoReservaPorOrdem> filtro = (List<SugestaoReservaPorOrdem>) tecidos.stream().filter(t -> t.getNivel() == nivelTecido && t.getGrupo() == grupoTecido && t.getSub() == subTecido && t.getItem() == itemTecido);

		// localiza os sortimentos que usam o tecido
		for (SugestaoReservaPorOrdem tecidoOrdem : filtro) {			
			if (!sortimentos.contains(tecidoOrdem.getSortimento())) sortimentos.add(tecidoOrdem.getSortimento());			
		}

		// para cada sortimento irá diminuir as quantidades conforme O percentual
		for (String sortimento : sortimentos) {			
			PlanoMestrePreOrdem ordem = planoMestrePreOrdemRepository.findById(idOrdem);
			List<PlanoMestrePreOrdemItem> itens = planoMestrePreOrdemItemRepository.findByIdOrdemAndItem(idOrdem, sortimento);			
				
			int novaQtde;
			
			for (PlanoMestrePreOrdemItem item : itens) {
				novaQtde = (int) (item.quantidade - (item.quantidade * (percentualDiminuir / 100)));
				PreOrdemProducaoItem itemRecalculado = new PreOrdemProducaoItem((int) idOrdem, ordem.grupo, ordem.alternativa, ordem.roteiro, ordem.periodo, item.sub, item.item, novaQtde);
				itensRecalculados.add(itemRecalculado);
			}
		}
		
		if (itensRecalculados.size() > 0) mapOrdensRecalculadas.put(idOrdem, itensRecalculados);		
	}
	
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
	
	private void calcularNecessidades() {
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
					guardarDadosPorOrdem(ordemReserva, ordem.id, item.item, tecido.getSequencia(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(),
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

	private void guardarDadosPorOrdem(int ordemReserva, long idOrdem, String sortimento, int sequencia, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, double qtdeKgUnit, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {

		if (mapDadosPorOrdem.containsKey(idOrdem)) {
			List<SugestaoReservaPorOrdem> tecidos = mapDadosPorOrdem.get(idOrdem);			
			
			@SuppressWarnings({ "unused", "unchecked" })
			List<SugestaoReservaPorOrdem> filtro = (List<SugestaoReservaPorOrdem>) tecidos.stream().filter(t -> t.getSortimento() == sortimento && t.getSequencia() == sequencia && t.getNivel() == nivelTecido && t.getGrupo() == grupoTecido && t.getSub() == subTecido && t.getItem() == itemTecido);

			if (filtro.size() > 0) {
				for (SugestaoReservaPorOrdem item : filtro) {					
					item.setQtdeNecessidade(item.getQtdeNecessidade() + qtdeKgUnit);					
				}
			} else {
				SugestaoReservaPorOrdem sugestao = new SugestaoReservaPorOrdem(ordemReserva, idOrdem, sortimento, sequencia, nivelTecido, grupoTecido, subTecido, itemTecido, "", "",
						qtdeKg, qtdeEstoque, qtdeEmpenhada, 0, qtdeKgUnit);
				
				tecidos.add(sugestao);
			}
		} else {
			List<SugestaoReservaPorOrdem> tecidos = new ArrayList<SugestaoReservaPorOrdem>();
			
			SugestaoReservaPorOrdem sugestao = new SugestaoReservaPorOrdem(ordemReserva, idOrdem, sortimento, sequencia, nivelTecido, grupoTecido, subTecido, itemTecido, "", "",
					qtdeKg, qtdeEstoque, qtdeEmpenhada, 0, qtdeKgUnit);
			
			tecidos.add(sugestao);
			
			mapDadosPorOrdem.put(idOrdem, tecidos);
		}				
	}
	
	public static void main(String args[]) {
		
		System.out.println("testes");
		
		double percentual = 20.5;
		
		int qtde = 10;
		int novaqtde = 0;
		
		novaqtde = (int) (qtde - (qtde * (20.0 / 100))); 
		
		System.out.println("qtde calculada: " + novaqtde);
	}
	
	
}