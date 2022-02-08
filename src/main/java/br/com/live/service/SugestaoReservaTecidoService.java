package br.com.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaPorOrdem;
import br.com.live.model.SugestaoReservaPorProduto;
import br.com.live.model.SugestaoReservaPorTecido;
import br.com.live.model.SugestaoReservaTecidos;
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
	private Map<String, SugestaoReservaPorProduto> mapDadosPorProduto;
	private Map<Long, List<PreOrdemProducaoItem>> mapPecasRecalculadas;
	private Map<Long, List<PreOrdemProducaoItem>> mapPecasPrevistas;
	
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

	public SugestaoReservaTecidos calcularSugestaoReserva(String planosMestres, String embarques, String referencias, String depositos, int priorizacao) {
		
		System.out.println("calcularSugestaoReserva");
		System.out.println("planosMestres: " + planosMestres);
		System.out.println("embarques: " + embarques);
		System.out.println("referencias: " + referencias);
		System.out.println("depositos: " + depositos);
		
		depositosTecidos = depositos;
		mapDadosPorTecido = new HashMap<String, SugestaoReservaPorTecido>();
		mapDadosPorOrdem = new HashMap<Long, List<SugestaoReservaPorOrdem>>();
		mapDadosPorProduto = new HashMap<String, SugestaoReservaPorProduto>();
		mapPecasPrevistas = new HashMap<Long, List<PreOrdemProducaoItem>>();
		mapPecasRecalculadas = new HashMap<Long, List<PreOrdemProducaoItem>>();	
		
		listaPriorizadaPreOrdens = planoMestreCustom
				.findPreOrdensPorOrdemPriorizacaoByPlanosEmbarquesReferencias(priorizacao, planosMestres, embarques,
						referencias);
		
		calcularNecessidades();
		reservarTecidos();	
		
		return obterDadosSugestaoReserva();
	}
	
	private SugestaoReservaTecidos obterDadosSugestaoReserva() {
		
		List<SugestaoReservaPorOrdem> ordens = new ArrayList<SugestaoReservaPorOrdem>();
		List<SugestaoReservaPorProduto> produtos = new ArrayList<SugestaoReservaPorProduto>();	
		List<SugestaoReservaPorTecido> tecidos = new ArrayList<SugestaoReservaPorTecido>();
		
		for (String produto : mapDadosPorProduto.keySet()) {
			SugestaoReservaPorProduto item = mapDadosPorProduto.get(produto);			
			produtos.add(item);			
		}
		
		for (String tecido : mapDadosPorTecido.keySet()) {
			SugestaoReservaPorTecido item = mapDadosPorTecido.get(tecido);
			tecidos.add(item);
		}

		for (Long idOrdem : mapDadosPorOrdem.keySet()) {
			List<SugestaoReservaPorOrdem> itens = mapDadosPorOrdem.get(idOrdem);
			
			for (SugestaoReservaPorOrdem item : itens) {
				ordens.add(item);
			}
		}

		return new SugestaoReservaTecidos(ordens, produtos, tecidos);
	}
	
	private void reservarTecidos() {
		//System.out.println("reservarTecidos");
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {
			
			revisarQtdesQueSeraoAtendidasNaOrdem(ordem.id);
			
			if (mapDadosPorOrdem.containsKey(ordem.id)) {			
				List<SugestaoReservaPorOrdem> tecidosOrdem = mapDadosPorOrdem.get(ordem.id);			
				for (SugestaoReservaPorOrdem tecidoOrdem : tecidosOrdem) {
					reservaTecidoParaOrdem(tecidoOrdem);
				}
			}
			
			guardarQtdesPecasAtendidas(ordem.id);			
		}
	}

	private void guardarQtdesPecasAtendidas(long idOrdem) {		
		//System.out.println("guardarQtdesPecasAtendidas");
		String chave;
		SugestaoReservaPorProduto sugestao;
		List<PreOrdemProducaoItem> pecasRecalculadas;
		List<PreOrdemProducaoItem> pecasPrevistas = mapPecasPrevistas.get(idOrdem);
		
		if (mapPecasRecalculadas.containsKey(idOrdem)) {
			pecasRecalculadas = mapPecasRecalculadas.get(idOrdem);
			
			for (PreOrdemProducaoItem peca : pecasRecalculadas) {				
				pecasPrevistas.removeIf(p -> p.sub == peca.sub && p.item == peca.item);
				
				chave = "1" + "." + peca.grupo + "." + peca.sub + "." + peca.item + "." + peca.alternativa + "." + peca.roteiro;				
				if (mapDadosPorProduto.containsKey(chave)) {
					sugestao = mapDadosPorProduto.get(chave);
					sugestao.setQtdeAtendida(sugestao.getQtdeAtendida() + peca.qtdeProgramada);
				}			
			}	
		}

 		for (PreOrdemProducaoItem peca : pecasPrevistas) {
			
			System.out.println();
			
			chave = "1" + "." + peca.grupo + "." + peca.sub + "." + peca.item + "." + peca.alternativa + "." + peca.roteiro;			
			if (mapDadosPorProduto.containsKey(chave)) {
				sugestao = mapDadosPorProduto.get(chave);
				sugestao.setQtdeAtendida(sugestao.getQtdeAtendida() + peca.qtdeProgramada);
			}			
		}	
	}
	
	private void revisarQtdesQueSeraoAtendidasNaOrdem(long idOrdem) {
		//System.out.println("revisarQtdesQueSeraoAtendidasNaOrdem");
		Map<String, Double> mapTecidosXQtdeNecessaria = new HashMap<String, Double>();

		String [] conteudo;
		double qtdeNecessidade;
		double diferenca;
		double percentual;
		
		if (mapDadosPorOrdem.containsKey(idOrdem)) {
		
			List<SugestaoReservaPorOrdem> tecidos = mapDadosPorOrdem.get(idOrdem);
		
			for (SugestaoReservaPorOrdem tecido : tecidos) {
				String chave = tecido.getNivel() + "." + tecido.getGrupo() + "." + tecido.getSub() + "." +  tecido.getItem();
				
				qtdeNecessidade = 0;	
				
				if (mapTecidosXQtdeNecessaria.containsKey(chave)) qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);
				
				qtdeNecessidade += tecido.getQtdeNecessidade(); 
				
				mapTecidosXQtdeNecessaria.put(chave, qtdeNecessidade);
			}
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
		
		// Recalcular as quantidades com base no tecido que possui o percentual mais alto.
		if (!codTecidoRecalcular.equals("")) {
			conteudo = codTecidoRecalcular.split("[.]");
			// enviar o percentual
			recalcularQtdePecasDaOrdem(idOrdem, conteudo[0], conteudo[1], conteudo[2], conteudo[3], percentualDiminuirPecas);
			recalcularNecessidadeTecidosDaOrdem(idOrdem);
		}
	}
		
	private void recalcularQtdePecasDaOrdem(long idOrdem, String nivelTecido, String grupoTecido, String subTecido, String itemTecido, double percentualDiminuir) {
		//System.out.println("recalcularQtdePecasDaOrdem");
		List<String> sortimentos = new ArrayList<String>();
		List<PreOrdemProducaoItem> itensRecalculados = new ArrayList<PreOrdemProducaoItem>();
		List<SugestaoReservaPorOrdem> tecidos = mapDadosPorOrdem.get(idOrdem);				
		
		Stream<SugestaoReservaPorOrdem> stream = tecidos.stream().filter(t -> t.getNivel() == nivelTecido && t.getGrupo() == grupoTecido && t.getSub() == subTecido && t.getItem() == itemTecido);
		List<SugestaoReservaPorOrdem> filtro = stream.collect(Collectors.toList()); 		
		
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
		
		if (itensRecalculados.size() > 0) mapPecasRecalculadas.put(idOrdem, itensRecalculados);		
	}
	
	private void recalcularNecessidadeTecidosDaOrdem(long idOrdem) {
		//System.out.println("recalcularNecessidadeTecidosDaOrdem");
				
		if (mapPecasRecalculadas.containsKey(idOrdem)) {		
			mapDadosPorOrdem.remove(idOrdem);		
			List<PreOrdemProducaoItem> itensRecalculados = mapPecasRecalculadas.get(idOrdem);		
			List<SugestaoReservaPorOrdem> necessidadesOrdem = mapDadosPorOrdem.get(idOrdem);
			
			for (PreOrdemProducaoItem recalculado : itensRecalculados) {						
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(recalculado.getGrupo(), recalculado.getSub(), recalculado.getItem(), recalculado.getAlternativa(), recalculado.getQtdeProgramada());
				
				for (NecessidadeTecidos tecido : tecidos) {								
					Stream<SugestaoReservaPorOrdem> stream = necessidadesOrdem.stream().filter(n -> n.getNivel() == tecido.getNivel() && n.getGrupo() == tecido.getGrupo() && n.getSub() == tecido.getSub() && n.getItem() == tecido.getItem());
					List<SugestaoReservaPorOrdem> filtro = stream.collect(Collectors.toList());
					
					for (SugestaoReservaPorOrdem sugestao : filtro) {
						sugestao.setQtdeNecessidadeRecalculada(sugestao.getQtdeNecessidadeRecalculada() + tecido.getQtdeKg());					
					}
				}
			}
		}
	}	
	
	private void reservaTecidoParaOrdem(SugestaoReservaPorOrdem tecidoOrdem) {			
		//System.out.println("reservaTecidoParaOrdem");
		double qtdeReservada = 0;
		double qtdeReservar = tecidoOrdem.getQtdeNecessidadeDisponivel();
		
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
		//System.out.println("calcularNecessidades");		
		int ordemReserva = 0;
		
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {						
			ordemReserva++;
			
			//System.out.println("PRIORIDADE: " + ordemReserva + " ID: " + ordem.id + " EMBARQUE: " + ordem.dataEmbarque + " QTDE EST CRITICO: " + ordem.qtdeEstagioCritico + " TEMPO PROD UNIT: " + ordem.tempoProducaoUnit);

			PlanoMestrePreOrdem dadosCapaOrdem = planoMestrePreOrdemRepository.findById(ordem.id);
			List<PlanoMestrePreOrdemItem> dadosItensOrdem = planoMestrePreOrdemItemRepository.findByIdOrdem(ordem.id);

			if (ordem.id == 20009) System.out.println("idOrdem " + ordem.id);
			
			for (PlanoMestrePreOrdemItem item : dadosItensOrdem) {
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(ordem.referencia, item.sub,
						item.item, dadosCapaOrdem.alternativa, item.quantidade);
				
				Produto peca = produtoCustom.findProduto("1", dadosCapaOrdem.grupo, item.sub, item.item);
				
				guardarQtdesOriginaisPecasPrevistas(ordem.id, dadosCapaOrdem.grupo, dadosCapaOrdem.alternativa, dadosCapaOrdem.roteiro, dadosCapaOrdem.periodo, item.sub, item.item, item.quantidade);
				guardarDadosPorProduto("1", dadosCapaOrdem.grupo, item.sub, item.item, peca.narrativa, dadosCapaOrdem.alternativa, dadosCapaOrdem.roteiro, ordem.dataEmbarque, ordem.qtdeEstagioCritico, ordem.tempoProducaoUnit, item.quantidade);
				
				for (NecessidadeTecidos tecido : tecidos) {					
					double qtdeEstoque = estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), depositosTecidos);
					
					double qtdeEmpenhada = estoqueProdutoCustom.findQtdeEmpenhadaByProduto(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem());
					
					guardarDadosPorTecido(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
					guardarDadosPorOrdem(ordemReserva, ordem.id, item.item, tecido.getSequencia(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), tecido.getQtdeKgUnit(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
				}
			}
		}
	}
 
	private void guardarQtdesOriginaisPecasPrevistas(long idOrdem, String grupo, int alternativa, int roteiro, int periodo, String sub, String item, int qtdeProgramada) {		
		//System.out.println("guardarQtdesOriginaisPecasPrevistas");
		List<PreOrdemProducaoItem> itensOrdem; 
		
		if (mapPecasPrevistas.containsKey(idOrdem)) itensOrdem = mapPecasPrevistas.get(idOrdem);
		else itensOrdem = new ArrayList<PreOrdemProducaoItem>();					
		
		if (grupo.equalsIgnoreCase("P3478") && sub.equalsIgnoreCase("G") && item.equalsIgnoreCase("00CZ55")) System.out.println("QTDE PROG => " + qtdeProgramada + " ID ORDEM: " + idOrdem); 
		
		itensOrdem.add(new PreOrdemProducaoItem((int ) idOrdem, grupo, alternativa, roteiro, periodo, sub, item, qtdeProgramada));		
		mapPecasPrevistas.put(idOrdem, itensOrdem); 		
	}
	
	private void guardarDadosPorProduto(String nivel, String grupo, String tamanho, String cor, String descricao, int alternativa, int roteiro, Date dataEmbarque, int qtdeEstagioCritico, double tempoProducaoUnit, int qtdePrevista) {
		//System.out.println("guardarDadosPorProduto");
		SugestaoReservaPorProduto sugestao;
		
		String chave = nivel + "." + grupo + "." + tamanho + "." + cor + "." + alternativa + "." + roteiro;		
		
		if (mapDadosPorProduto.containsKey(chave)) {
			sugestao = mapDadosPorProduto.get(chave);
			sugestao.setQtdePrevista(sugestao.getQtdePrevista() + qtdePrevista);			
		}
		else {
			sugestao = new SugestaoReservaPorProduto(nivel, grupo, tamanho, cor, descricao, alternativa, roteiro, dataEmbarque, qtdeEstagioCritico, tempoProducaoUnit, qtdePrevista, 0, 0);	
			mapDadosPorProduto.put(chave, sugestao);		
		}
	}
	
	private void guardarDadosPorTecido(String nivelTecido, String grupoTecido, String subTecido, String itemTecido, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {
		//System.out.println("guardarDadosPorTecido");
		String codTecido = nivelTecido + "." + grupoTecido + "." + subTecido + "." + itemTecido;

		if (mapDadosPorTecido.containsKey(codTecido)) {
			SugestaoReservaPorTecido sugestao = mapDadosPorTecido.get(codTecido);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);
		} else {			
			Produto tecido = produtoCustom.findProduto(nivelTecido, grupoTecido, subTecido, itemTecido);
			
			SugestaoReservaPorTecido sugestao = new SugestaoReservaPorTecido(nivelTecido, grupoTecido, subTecido,
					itemTecido, tecido.narrativa, tecido.unidade, qtdeKg, qtdeEstoque, qtdeEmpenhada, 0);
			mapDadosPorTecido.put(codTecido, sugestao);
		}
	}

	private void guardarDadosPorOrdem(int ordemReserva, long idOrdem, String sortimento, int sequencia, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, double qtdeKgUnit, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {
		//System.out.println("guardarDadosPorOrdem");		
		
		if (mapDadosPorOrdem.containsKey(idOrdem)) {
			
			List<SugestaoReservaPorOrdem> tecidos = mapDadosPorOrdem.get(idOrdem);
			
			Stream<SugestaoReservaPorOrdem> stream = tecidos.stream().filter(t -> t.getSortimento() == sortimento && t.getSequencia() == sequencia && t.getNivel() == nivelTecido && t.getGrupo() == grupoTecido && t.getSub() == subTecido && t.getItem() == itemTecido);			
			List<SugestaoReservaPorOrdem> filtro = stream.collect(Collectors.toList());
			
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
}