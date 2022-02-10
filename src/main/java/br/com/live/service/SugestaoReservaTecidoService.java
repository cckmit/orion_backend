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
import br.com.live.model.SugestaoReservaPorOrdemSortimento;
import br.com.live.model.SugestaoReservaPorOrdemTecido;
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
	private Map<Long, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
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
		System.out.println("<=====================>");
		
		depositosTecidos = depositos;
		mapDadosPorTecido = new HashMap<String, SugestaoReservaPorTecido>();
		mapDadosPorOrdem = new HashMap<Long, List<SugestaoReservaPorOrdemSortimento>>();
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
		
		List<SugestaoReservaPorOrdemTecido> listaSugestaoPorOrdens = obterListaSugestaoReservaPorOrdemTecido();
		List<SugestaoReservaPorProduto> listaSugestaoPorProdutos = obterListaSugestaoReservaPorProduto();	
		List<SugestaoReservaPorTecido> listaSugestaoPorTecidos = obterListaSugestaoReservaPorTecido();
				
		return new SugestaoReservaTecidos(listaPriorizadaPreOrdens, listaSugestaoPorOrdens, listaSugestaoPorProdutos, listaSugestaoPorTecidos);
	}

	private List<SugestaoReservaPorOrdemTecido> obterListaSugestaoReservaPorOrdemTecido() {
		System.out.println("obterListaSugestaoReservaPorOrdemTecido");
		
		List<SugestaoReservaPorOrdemTecido> sugestao = new ArrayList<SugestaoReservaPorOrdemTecido>();
		
		for (Long idOrdem : mapDadosPorOrdem.keySet()) {
			List<SugestaoReservaPorOrdemSortimento> itens = mapDadosPorOrdem.get(idOrdem);
			
			for (SugestaoReservaPorOrdemSortimento item : itens) {
				if (item.getItem().equals("00MR44")) System.out.println("COR: " + item.getItem() + " - KG: " + item.getQtdeNecessidade() + " - SUGERIDO: " + item.getQtdeSugerido() + " - SALDO: " + item.getQtdeSaldo() + " - DISPONIVEL: " + item.getQtdeDisponivel());
				
				Stream<SugestaoReservaPorOrdemTecido> stream = sugestao.stream().filter(s -> s.getIdOrdem() == idOrdem && s.getNivel().equals(item.getNivel()) && s.getGrupo().equals(item.getGrupo()) && s.getSub().equals(item.getSub()) && s.getItem().equals(item.getItem()));
				List<SugestaoReservaPorOrdemTecido> filtro = stream.collect(Collectors.toList()); 		

				if (filtro.size() > 0) {				
					for (SugestaoReservaPorOrdemTecido sugestaoReservaPorOrdemTecido : filtro) {				
						sugestaoReservaPorOrdemTecido.setQtdeNecessidadeUnit(sugestaoReservaPorOrdemTecido.getQtdeNecessidadeUnit() + item.getQtdeNecessidadeUnit());
						sugestaoReservaPorOrdemTecido.setQtdeNecessidade(sugestaoReservaPorOrdemTecido.getQtdeNecessidade() + item.getQtdeNecessidade());
						sugestaoReservaPorOrdemTecido.setQtdeSugerido(sugestaoReservaPorOrdemTecido.getQtdeSugerido() + item.getQtdeSugerido());
						// TODO - TESTAR ESSA SITUACAO
						if (sugestaoReservaPorOrdemTecido.getQtdeDisponivel() > item.getQtdeDisponivel()) sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());
					}
				} else {
					SugestaoReservaPorOrdemTecido sugestaoReservaPorOrdemTecido = new SugestaoReservaPorOrdemTecido(idOrdem, item.getNivel(), item.getGrupo(), item.getSub(), item.getItem(), item.getDescricao(), item.getUnidade(), item.getQtdeNecessidadeUnit(), item.getQtdeNecessidade(), item.getQtdeEstoque(), item.getQtdeEmpenhada(), item.getQtdeSugerido());
					if (sugestaoReservaPorOrdemTecido.getQtdeDisponivel() > item.getQtdeDisponivel()) sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());					
					sugestao.add(sugestaoReservaPorOrdemTecido);
				}				
			}
		}	
		
		return sugestao;
	}
	
	private List<SugestaoReservaPorProduto> obterListaSugestaoReservaPorProduto() {		
		List<SugestaoReservaPorProduto> sugestao = new ArrayList<SugestaoReservaPorProduto>();		
		for (String produto : mapDadosPorProduto.keySet()) {
			SugestaoReservaPorProduto item = mapDadosPorProduto.get(produto);			
			sugestao.add(item);			
		}
		return sugestao;		
	}
	
	private List<SugestaoReservaPorTecido> obterListaSugestaoReservaPorTecido() {		
		List<SugestaoReservaPorTecido> sugestao = new ArrayList<SugestaoReservaPorTecido>();
		
		for (String tecido : mapDadosPorTecido.keySet()) {
			SugestaoReservaPorTecido item = mapDadosPorTecido.get(tecido);
			sugestao.add(item);
		}
		return sugestao;
	}
		
	private void reservarTecidos() {
		//System.out.println("reservarTecidos");
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {
			
			// TODO - Se houver um tecido que estiver zerado, não pode atender nenhuma qtde para os sortimentos que utilizem.
			// TODO - Se zerar as quantidades a serem atendidas. também não poderá consumir tecido, não faz sentido que qtdes minimas de tecido sejam reservadas caso não atenda nenhuma peça.
			
			revisarQtdesQueSeraoAtendidasNaOrdem(ordem.id);
			
			if (mapDadosPorOrdem.containsKey(ordem.id)) {			
				List<SugestaoReservaPorOrdemSortimento> tecidosOrdem = mapDadosPorOrdem.get(ordem.id);			
				for (SugestaoReservaPorOrdemSortimento tecidoOrdem : tecidosOrdem) {					
					//System.out.println("ORDEM: " + ordem.id + " - SORTIMENTO: " + tecidoOrdem.getSortimento() + " - SEQ: " + tecidoOrdem.getSequencia() + " - TECIDO: " + tecidoOrdem.getGrupo() + "." + tecidoOrdem.getSub() + "." + tecidoOrdem.getItem() + " = " + tecidoOrdem.getQtdeDisponivel());					
					reservaTecidoParaOrdem(tecidoOrdem);
				}
			}
			
			// TODO - REVISAR ESSA LÓGICA
			
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
		
		//System.out.println("PONTO 1");
		
		if (mapDadosPorOrdem.containsKey(idOrdem)) {		
			List<SugestaoReservaPorOrdemSortimento> tecidos = mapDadosPorOrdem.get(idOrdem);
		
			for (SugestaoReservaPorOrdemSortimento tecido : tecidos) {
				String chave = tecido.getNivel() + "." + tecido.getGrupo() + "." + tecido.getSub() + "." +  tecido.getItem();
				
				qtdeNecessidade = 0;	
				
				if (mapTecidosXQtdeNecessaria.containsKey(chave)) qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);
				
				qtdeNecessidade += tecido.getQtdeNecessidade(); 				
				//System.out.println("chave: " + chave + " - qtdeNecessidade: " + qtdeNecessidade);				
				mapTecidosXQtdeNecessaria.put(chave, qtdeNecessidade);
			}
		}
		
		double saldoDisponivel = 0.0;
		double percentualDiminuirPecas = 0.0;
		String codTecidoRecalcular = "";
		
		//System.out.println("PONTO 2");
		
		for (String chave : mapTecidosXQtdeNecessaria.keySet()) {
			conteudo = chave.split("[.]");
			String codTecido = conteudo[0] + "." + conteudo[1] + "." + conteudo[2] + "." + conteudo[3];
		
			qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);
			SugestaoReservaPorTecido dadosTecido = mapDadosPorTecido.get(codTecido);
			saldoDisponivel = dadosTecido.getQtdeSaldo();
			
			if (saldoDisponivel < 0.0) saldoDisponivel = 0.0; 
			
			//System.out.println("codTecido: " + codTecido + " - qtdeNecessidade: " + qtdeNecessidade + " - " + dadosTecido.getQtdeSaldo());
			
			if (qtdeNecessidade > saldoDisponivel) {
			
				diferenca = qtdeNecessidade - saldoDisponivel; 
				
				if (saldoDisponivel > 0) percentual = (diferenca / saldoDisponivel) * 100;
				else percentual = 0.0;
				
				// TODO - E SE HOUVEREM 2 TECIDOS COM O MESMO PERCENTUAL PARA DIMINUIR, MAS DE SORTIMENTOS DIFERENTES.
				// TALVEZ TENHA QUE GRAVAR EM UMA LISTA.
				// SE O PERCENTUAL ESTIVER ZERO, TAMBÉM TEM QUE RECALCULAR... PARA FICAR COM QTDE ZERO MESMO O RECALCULADO... 
				
				if (percentual > percentualDiminuirPecas) {				
					codTecidoRecalcular = codTecido;
					percentualDiminuirPecas = percentual;					
					//System.out.println("TECIDO RECALCULAR: " + codTecidoRecalcular + " - percentualDiminuirPecas: " + percentualDiminuirPecas);
				}
			}
		}		
		
		//System.out.println("PONTO 3");
		
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
		List<SugestaoReservaPorOrdemSortimento> tecidos = mapDadosPorOrdem.get(idOrdem);				
		
		Stream<SugestaoReservaPorOrdemSortimento> stream = tecidos.stream().filter(t -> t.getNivel().equals(nivelTecido) && t.getGrupo().equals(grupoTecido) && t.getSub().equals(subTecido) && t.getItem().equals(itemTecido));
		List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList()); 		
				
		//System.out.println(nivelTecido + "." + grupoTecido  + "." + subTecido  + "." + itemTecido + " - " + percentualDiminuir);
		
		// localiza os sortimentos que usam o tecido
		for (SugestaoReservaPorOrdemSortimento tecidoOrdem : filtro) {			
			if (!sortimentos.contains(tecidoOrdem.getSortimento())) sortimentos.add(tecidoOrdem.getSortimento());			
		}

		// para cada sortimento irá diminuir as quantidades conforme O percentual
		for (String sortimento : sortimentos) {			
			PlanoMestrePreOrdem ordem = planoMestrePreOrdemRepository.findById(idOrdem);
			List<PlanoMestrePreOrdemItem> itens = planoMestrePreOrdemItemRepository.findByIdOrdemAndItem(idOrdem, sortimento);			
				
			int novaQtde;
			
			for (PlanoMestrePreOrdemItem item : itens) {
				novaQtde = (int) (item.quantidade - (item.quantidade * (percentualDiminuir / 100)));
				
				if (novaQtde < 0) novaQtde = 0;  				
					
				PreOrdemProducaoItem itemRecalculado = new PreOrdemProducaoItem((int) idOrdem, ordem.grupo, ordem.alternativa, ordem.roteiro, ordem.periodo, item.sub, item.item, novaQtde);
				itensRecalculados.add(itemRecalculado);				
			}
		}
		
		if (itensRecalculados.size() > 0) mapPecasRecalculadas.put(idOrdem, itensRecalculados);		
	}
	
	private void recalcularNecessidadeTecidosDaOrdem(long idOrdem) {
		//System.out.println("recalcularNecessidadeTecidosDaOrdem");
				
		if (mapPecasRecalculadas.containsKey(idOrdem)) {						
			List<PreOrdemProducaoItem> itensRecalculados = mapPecasRecalculadas.get(idOrdem);		
			List<SugestaoReservaPorOrdemSortimento> necessidadesOrdem = mapDadosPorOrdem.get(idOrdem);
			
			for (PreOrdemProducaoItem recalculado : itensRecalculados) {						
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(recalculado.getGrupo(), recalculado.getSub(), recalculado.getItem(), recalculado.getAlternativa(), recalculado.getQtdeProgramada());
				
				for (NecessidadeTecidos tecido : tecidos) {								
					Stream<SugestaoReservaPorOrdemSortimento> stream = necessidadesOrdem.stream().filter(n -> n.getNivel().equals(tecido.getNivel()) && n.getGrupo().equals(tecido.getGrupo()) && n.getSub().equals(tecido.getSub()) && n.getItem().equals(tecido.getItem()));
					List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList());
					
					for (SugestaoReservaPorOrdemSortimento sugestao : filtro) {
						sugestao.setQtdeNecessidadeRecalculada(sugestao.getQtdeNecessidadeRecalculada() + tecido.getQtdeKg());					
					}
				}
			}
		}
	}	
	
	private void reservaTecidoParaOrdem(SugestaoReservaPorOrdemSortimento tecidoOrdem) {			
		//System.out.println("reservaTecidoParaOrdem");
		double qtdeReservada = 0;
		double qtdeReservar = tecidoOrdem.getQtdeNecessidadeDisponivel();
		
		String codTecido = tecidoOrdem.getNivel() + "." + tecidoOrdem.getGrupo() + "." + tecidoOrdem.getSub() + "." +  tecidoOrdem.getItem();
		SugestaoReservaPorTecido tecido = mapDadosPorTecido.get(codTecido);
		
		//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("codTecido-> " + codTecido); 
		
		if (tecido.getQtdeSaldo() > 0) {		
			if (qtdeReservar < tecido.getQtdeSaldo())
				qtdeReservada = qtdeReservar;
			else qtdeReservada = tecido.getQtdeSaldo();
					
			//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("qtdeReservar-> " + qtdeReservar);
			//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("qtdeSaldo-> " + tecido.getQtdeSaldo() );
			//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("qtdeReservada-> " + qtdeReservada );
							
			tecidoOrdem.setQtdeDisponivel(tecido.getQtdeSaldo());
			tecidoOrdem.setQtdeSugerido(tecidoOrdem.getQtdeSugerido() + qtdeReservada);
			tecido.setQtdeSugerido(tecido.getQtdeSugerido() + qtdeReservada);
			
			//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("tecido ordem disponivel-> " + tecidoOrdem.getQtdeDisponivel());
			//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("tecido ordem sugerido-> " + tecidoOrdem.getQtdeSugerido());
			//if (tecidoOrdem.getItem().equals("00MR44")) System.out.println("tecido ordem saldo-> " + tecidoOrdem.getQtdeSaldo());
		}
	}	
	
	private void calcularNecessidades() {
		//System.out.println("calcularNecessidades");		
		
		for (ConsultaPreOrdemProducao ordem : listaPriorizadaPreOrdens) {						
			//System.out.println("PRIORIDADE: " + ordemReserva + " ID: " + ordem.id + " EMBARQUE: " + ordem.dataEmbarque + " QTDE EST CRITICO: " + ordem.qtdeEstagioCritico + " TEMPO PROD UNIT: " + ordem.tempoProducaoUnit);

			PlanoMestrePreOrdem dadosCapaOrdem = planoMestrePreOrdemRepository.findById(ordem.id);
			List<PlanoMestrePreOrdemItem> dadosItensOrdem = planoMestrePreOrdemItemRepository.findByIdOrdem(ordem.id);

			//if (ordem.id == 20009) System.out.println("idOrdem " + ordem.id);
			
			for (PlanoMestrePreOrdemItem item : dadosItensOrdem) {
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(ordem.referencia, item.sub,
						item.item, dadosCapaOrdem.alternativa, item.quantidade);
				
				Produto peca = produtoCustom.findProduto("1", dadosCapaOrdem.grupo, item.sub, item.item);
				
				guardarQtdesOriginaisPecasPrevistas(ordem.id, dadosCapaOrdem.grupo, dadosCapaOrdem.alternativa, dadosCapaOrdem.roteiro, dadosCapaOrdem.periodo, item.sub, item.item, item.quantidade);
				guardarDadosPorProduto("1", dadosCapaOrdem.grupo, item.sub, item.item, peca.narrativa, dadosCapaOrdem.alternativa, dadosCapaOrdem.roteiro, ordem.dataEmbarque, ordem.qtdeEstagioCritico, ordem.tempoProducaoUnit, item.quantidade);
				
				for (NecessidadeTecidos tecido : tecidos) {					
					
					Produto dadosTecido = produtoCustom.findProduto(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem());					
					double qtdeEstoque = estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), depositosTecidos);					
					double qtdeEmpenhada = estoqueProdutoCustom.findQtdeEmpenhadaByProduto(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem());
					
					guardarDadosPorTecido(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), dadosTecido.getNarrativa(), dadosTecido.getUnidade(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
					guardarDadosPorOrdem(ordem.id, item.item, tecido.getSequencia(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), dadosTecido.getNarrativa(), dadosTecido.getUnidade(), tecido.getQtdeKgUnit(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
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
	
	private void guardarDadosPorTecido(String nivelTecido, String grupoTecido, String subTecido, String itemTecido, String descricaoTecido, String unidadeTecido, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {
		//System.out.println("guardarDadosPorTecido");
		
		String codTecido = nivelTecido + "." + grupoTecido + "." + subTecido + "." + itemTecido;

		//System.out.println(codTecido + " -> kg: " + qtdeKg); 
		
		if (mapDadosPorTecido.containsKey(codTecido)) {
			SugestaoReservaPorTecido sugestao = mapDadosPorTecido.get(codTecido);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);
		} else {			
			SugestaoReservaPorTecido sugestao = new SugestaoReservaPorTecido(nivelTecido, grupoTecido, subTecido,
					itemTecido, descricaoTecido, unidadeTecido, qtdeKg, qtdeEstoque, qtdeEmpenhada, 0);
			mapDadosPorTecido.put(codTecido, sugestao);
		}
	}

	private void guardarDadosPorOrdem(long idOrdem, String sortimento, int sequencia, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, String descricaoTecido, String unidadeTecido, double qtdeKgUnit, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada) {
		//System.out.println("guardarDadosPorOrdem");		
		
		if (mapDadosPorOrdem.containsKey(idOrdem)) {
			
			List<SugestaoReservaPorOrdemSortimento> tecidos = mapDadosPorOrdem.get(idOrdem);

			Stream<SugestaoReservaPorOrdemSortimento> stream = tecidos.stream().filter(t -> t.getSortimento().equals(sortimento) && t.getSequencia() == sequencia && t.getNivel().equals(nivelTecido) && t.getGrupo().equals(grupoTecido) && t.getSub().equals(subTecido) && t.getItem().equals(itemTecido));			
			List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList());
		
			if (filtro.size() > 0) {
				for (SugestaoReservaPorOrdemSortimento item : filtro) {					
					item.setQtdeNecessidade(item.getQtdeNecessidade() + qtdeKg);					
				}
			} else {
				SugestaoReservaPorOrdemSortimento sugestao = new SugestaoReservaPorOrdemSortimento(idOrdem, sortimento, sequencia, nivelTecido, grupoTecido, subTecido, itemTecido, descricaoTecido, unidadeTecido,
						qtdeKg, qtdeEstoque, qtdeEmpenhada, 0, qtdeKgUnit);
				tecidos.add(sugestao);
			}
		} else {
			List<SugestaoReservaPorOrdemSortimento> tecidos = new ArrayList<SugestaoReservaPorOrdemSortimento>();
			
			SugestaoReservaPorOrdemSortimento sugestao = new SugestaoReservaPorOrdemSortimento(idOrdem, sortimento, sequencia, nivelTecido, grupoTecido, subTecido, itemTecido, descricaoTecido, unidadeTecido,
					qtdeKg, qtdeEstoque, qtdeEmpenhada, 0, qtdeKgUnit);
			
			tecidos.add(sugestao);
			
			mapDadosPorOrdem.put(idOrdem, tecidos);
		}				
	}
}