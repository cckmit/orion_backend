package br.com.live.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SugestaoReservaTecidoCustom;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.model.OrdemProducao;
import br.com.live.model.OrdemProducaoItem;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaTecidosReservados;
import br.com.live.model.SugestaoReservaPorOrdemSortimento;
import br.com.live.model.SugestaoReservaPorOrdemTecido;
import br.com.live.model.SugestaoReservaPorProduto;
import br.com.live.model.SugestaoReservaPorTecido;
import br.com.live.model.SugestaoReservaTecidos;
import br.com.live.util.CodigoProduto;

@Service
@Transactional
public class SugestaoReservaTecidoPorOrdensService {

	private final SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom;
	private final ProdutoCustom produtoCustom;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
	private final OrdemProducaoCustom ordemProducaoCustom;	

	private String depositosTecidos;
	private int percentualMinimoAtender;
	private List<OrdemProducao> listaPriorizadaOrdens;
	private Map<String, SugestaoReservaPorTecido> mapDadosPorTecido;	
	private Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
	private Map<String, SugestaoReservaPorProduto> mapDadosPorProduto;
	private Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadas;
	private Map<Integer, List<OrdemProducaoItem>> mapPecasPrevistas;
	private Map<Integer, List<SugestaoReservaTecidosReservados>> mapTecidosReservados;	
	
	public SugestaoReservaTecidoPorOrdensService(SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom, 
			ProdutoCustom produtoCustom, 
			EstoqueProdutoCustom estoqueProdutoCustom,
			OrdemProducaoCustom ordemProducaoCustom) {						
		this.sugestaoReservaTecidoCustom = sugestaoReservaTecidoCustom;
		this.produtoCustom = produtoCustom;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;		
	}

	public SugestaoReservaTecidos calcularSugestaoReserva(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String embarques, String referencias, String estagios, String artigos, String tecidos, String depositos, boolean isSomenteFlat, int percentualMinimoAtender) {		
		// System.out.println("calcularSugestaoReserva");		

		System.out.println("Inicio do calculo de sugestao de reserva de tecidos");
		
		iniciarListasAuxiliares();
		
		this.depositosTecidos = depositos;
		this.percentualMinimoAtender = percentualMinimoAtender;
		
		listaPriorizadaOrdens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao, periodoInicial, periodoFinal, embarques, referencias, estagios, artigos, tecidos, isSomenteFlat); 
		
		System.out.println("calcularNecessidades");		
		calcularNecessidades();
		System.out.println("reservarTecidos");
		reservarTecidos();	
		
		SugestaoReservaTecidos sugestao = obterDadosSugestaoReserva();
				
		limparListasAuxiliares();
		
		System.out.println("fim do calculo de sugestao de reserva de tecidos");
		
		return sugestao;
	}
	
	private void iniciarListasAuxiliares() {
		mapDadosPorTecido = new HashMap<String, SugestaoReservaPorTecido>();
		mapDadosPorOrdem = new HashMap<Integer, List<SugestaoReservaPorOrdemSortimento>>();
		mapDadosPorProduto = new HashMap<String, SugestaoReservaPorProduto>();
		mapPecasPrevistas = new HashMap<Integer, List<OrdemProducaoItem>>();
		mapPecasRecalculadas = new HashMap<Integer, List<OrdemProducaoItem>>();
		mapTecidosReservados = new HashMap<Integer, List<SugestaoReservaTecidosReservados>>() ;					
	}
	
	private void limparListasAuxiliares() {
		mapDadosPorTecido.clear();
		mapDadosPorOrdem.clear();
		mapDadosPorProduto.clear();
		mapPecasPrevistas.clear();
		mapPecasRecalculadas.clear();	
		mapTecidosReservados.clear();
	}
	
	private SugestaoReservaTecidos obterDadosSugestaoReserva() {
		List<SugestaoReservaPorOrdemTecido> listaSugestaoPorOrdens = obterListaSugestaoReservaPorOrdemTecido();
		List<SugestaoReservaPorProduto> listaSugestaoPorProdutos = obterListaSugestaoReservaPorProduto();	
		List<SugestaoReservaPorTecido> listaSugestaoPorTecidos = obterListaSugestaoReservaPorTecido();
		List<SugestaoReservaTecidosReservados> listaDetalhaTecidosReservados = obterListaDetalhadaTecidosReservados();
		return new SugestaoReservaTecidos(listaPriorizadaOrdens, listaSugestaoPorOrdens, listaSugestaoPorProdutos, listaSugestaoPorTecidos, listaDetalhaTecidosReservados);
	}

	private List<SugestaoReservaPorOrdemTecido> obterListaSugestaoReservaPorOrdemTecido() {
		//System.out.println("obterListaSugestaoReservaPorOrdemTecido");
		
		List<SugestaoReservaPorOrdemTecido> sugestao = new ArrayList<SugestaoReservaPorOrdemTecido>();
		
		for (Integer idOrdem : mapDadosPorOrdem.keySet()) {
			List<SugestaoReservaPorOrdemSortimento> itens = mapDadosPorOrdem.get(idOrdem);
			
			for (SugestaoReservaPorOrdemSortimento item : itens) {
				//System.out.println(item.getSequencia() + " - COR: " + item.getItem() + " - KG: " + item.getQtdeNecessidade() + " - SUGERIDO: " + item.getQtdeSugerido() + " - SALDO: " + item.getQtdeSaldo() + " - DISPONIVEL: " + item.getQtdeDisponivel());
				
				Stream<SugestaoReservaPorOrdemTecido> stream = sugestao.stream().filter(s -> s.getIdOrdem() == idOrdem && s.getNivel().equals(item.getNivel()) && s.getGrupo().equals(item.getGrupo()) && s.getSub().equals(item.getSub()) && s.getItem().equals(item.getItem()));
				List<SugestaoReservaPorOrdemTecido> filtro = stream.collect(Collectors.toList()); 		

				if (filtro.size() > 0) {				
					for (SugestaoReservaPorOrdemTecido sugestaoReservaPorOrdemTecido : filtro) {				
						sugestaoReservaPorOrdemTecido.setQtdeNecessidadeUnit(sugestaoReservaPorOrdemTecido.getQtdeNecessidadeUnit() + item.getQtdeNecessidadeUnit());
						sugestaoReservaPorOrdemTecido.setQtdeNecessidade(sugestaoReservaPorOrdemTecido.getQtdeNecessidade() + item.getQtdeNecessidade());
						sugestaoReservaPorOrdemTecido.setQtdeSugerido(sugestaoReservaPorOrdemTecido.getQtdeSugerido() + item.getQtdeSugerido());
 					    //System.out.println("PASSOU POR AQUI: " + idOrdem + " - " + item.getQtdeDisponivel());						
						//if (item.getQtdeDisponivel() > sugestaoReservaPorOrdemTecido.getQtdeDisponivel()) sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());
					}
				} else {
					SugestaoReservaPorOrdemTecido sugestaoReservaPorOrdemTecido = new SugestaoReservaPorOrdemTecido(idOrdem, item.getNivel(), item.getGrupo(), item.getSub(), item.getItem(), item.getDescricao(), item.getUnidade(), item.getQtdeNecessidadeUnit(), item.getQtdeNecessidade(), item.getQtdeEstoque(), item.getQtdeEmpenhada(), item.getQtdeSugerido());
					sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());
					sugestaoReservaPorOrdemTecido.setQtdeDisponivelTecidoSubstituto(item.getQtdeDisponivelTecidoSubstituto());
					//if (sugestaoReservaPorOrdemTecido.getQtdeDisponivel() > item.getQtdeDisponivel()) sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());
					//if (sugestaoReservaPorOrdemTecido.getQtdeDisponivelTecidoSubstituto() > item.getQtdeDisponivelTecidoSubstituto()) sugestaoReservaPorOrdemTecido.setQtdeDisponivelTecidoSubstituto(item.getQtdeDisponivelTecidoSubstituto());					
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

		Collections.sort(sugestao);
		
		return sugestao;		
	}
	
	private List<SugestaoReservaPorTecido> obterListaSugestaoReservaPorTecido() {		
		List<SugestaoReservaPorTecido> sugestao = new ArrayList<SugestaoReservaPorTecido>();		
		for (String tecido : mapDadosPorTecido.keySet()) {
			SugestaoReservaPorTecido item = mapDadosPorTecido.get(tecido);
			sugestao.add(item);
		}
		
		Collections.sort(sugestao);
		
		return sugestao;
	}
		
	private List<SugestaoReservaTecidosReservados> obterListaDetalhadaTecidosReservados() {
		List<SugestaoReservaTecidosReservados> tecidos = new ArrayList<SugestaoReservaTecidosReservados>();
		for (Integer idOrdem : mapTecidosReservados.keySet()) {
			List<SugestaoReservaTecidosReservados> reservados = mapTecidosReservados.get(idOrdem); 
			for (SugestaoReservaTecidosReservados reservado : reservados) {
				tecidos.add(reservado);
			}
		}
		return tecidos;
	}
	
	private void reservarTecidos() {
		//System.out.println("reservarTecidos");
		for (OrdemProducao ordem : listaPriorizadaOrdens) {
			
			calcularQtdesPecasSeraoAtendidas(ordem);
			
			if (mapDadosPorOrdem.containsKey(ordem.ordemProducao)) {			
				List<SugestaoReservaPorOrdemSortimento> tecidosOrdem = mapDadosPorOrdem.get(ordem.ordemProducao);			
				for (SugestaoReservaPorOrdemSortimento tecidoOrdem : tecidosOrdem) {					
					//System.out.println("RESERVA - ORDEM: " + ordem.id + " - SORTIMENTO: " + tecidoOrdem.getSortimento() + " - SEQ: " + tecidoOrdem.getSequencia() + " - TECIDO: " + tecidoOrdem.getGrupo() + "." + tecidoOrdem.getSub() + "." + tecidoOrdem.getItem() + " = " + tecidoOrdem.getQtdeDisponivel() + " - " + tecidoOrdem.getQtdeNecessidadeCalculada());					
					reservaTecidoParaOrdem(tecidoOrdem);
				}
			}
			
			atenderQtdesPecas(ordem);			
		}
	}

	private void atenderQtdesPecas(OrdemProducao ordem) {		
		//System.out.println("guardarQtdesPecasAtendidas");
		String chave;
		int qtdeTotalAtendida = 0;
		SugestaoReservaPorProduto sugestao;
		List<OrdemProducaoItem> pecasRecalculadas;
				
		if (mapPecasPrevistas.containsKey(ordem.ordemProducao)) {
			
			List<OrdemProducaoItem> pecasPrevistas = mapPecasPrevistas.get(ordem.ordemProducao);
			
			if (mapPecasRecalculadas.containsKey(ordem.ordemProducao)) {
				pecasRecalculadas = mapPecasRecalculadas.get(ordem.ordemProducao);
				
				for (OrdemProducaoItem peca : pecasRecalculadas) {				
					
					if (ordem.ordemProducao == 317395) System.out.println("REMOVE: " + peca.tamanho + "  -  " + peca.cor);
					
					pecasPrevistas.removeIf(p -> p.tamanho.equalsIgnoreCase(peca.tamanho) && p.cor.equalsIgnoreCase(peca.cor));
					
					chave = chaveMapDadosPorProduto("1", peca.referencia, peca.tamanho, peca.cor, peca.nrAlternativa, peca.nrRoteiro);
					
					if (mapDadosPorProduto.containsKey(chave)) {
						sugestao = mapDadosPorProduto.get(chave);
						sugestao.setQtdeAtendida(sugestao.getQtdeAtendida() + peca.qtdePecasProgramada);
						qtdeTotalAtendida += peca.qtdePecasProgramada;					
					}			
				}	
			}
	
			if (pecasPrevistas.size() > 0) {
				
		 		for (OrdemProducaoItem peca : pecasPrevistas) {			
		 			chave = chaveMapDadosPorProduto("1", peca.referencia, peca.tamanho, peca.cor, peca.nrAlternativa, peca.nrRoteiro);					
					if (mapDadosPorProduto.containsKey(chave)) {
						sugestao = mapDadosPorProduto.get(chave);
						sugestao.setQtdeAtendida(sugestao.getQtdeAtendida() + peca.qtdePecasProgramada);
						qtdeTotalAtendida += peca.qtdePecasProgramada;
					}			
				}
			}
			
	 		//if (ordem.ordemProducao == 300799) System.out.println("qtdeTotalAtendida: " + qtdeTotalAtendida);
	 		ordem.setQtdeMaxPecasReservaAtende(qtdeTotalAtendida);
		}
	}
	
	private void calcularQtdesPecasSeraoAtendidas(OrdemProducao ordem) {
		//System.out.println("revisarQtdesQueSeraoAtendidasNaOrdem");
		
		Map<String, Double> mapTecidosXQtdeNecessaria = new HashMap<String, Double>();

		String [] conteudo;
		double qtdeNecessidade;		
		double percentual;
		
		if (mapDadosPorOrdem.containsKey(ordem.ordemProducao)) {		
			List<SugestaoReservaPorOrdemSortimento> tecidos = mapDadosPorOrdem.get(ordem.ordemProducao);
		
			for (SugestaoReservaPorOrdemSortimento tecido : tecidos) {
				String chave = new CodigoProduto(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem()).getCodigo();
				
				qtdeNecessidade = 0;	
				
				if (mapTecidosXQtdeNecessaria.containsKey(chave)) qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);
				
				qtdeNecessidade += tecido.getQtdeNecessidade(); 				
				//System.out.println("chave: " + chave + " - qtdeNecessidade: " + qtdeNecessidade);				
				mapTecidosXQtdeNecessaria.put(chave, qtdeNecessidade);
			}
		}
		
		boolean primeiroPercentual = true;
		double saldoDisponivel = 0.0;
		double menorPercentual = 0.0;		
		Map<String, Double> mapTecidoXPercentual = new HashMap<String, Double>(); 
		
		for (String chave : mapTecidosXQtdeNecessaria.keySet()) {
			conteudo = chave.split("[.]");
			CodigoProduto codigoProduto = new CodigoProduto(conteudo[0], conteudo[1], conteudo[2], conteudo[3]);
			
			qtdeNecessidade = mapTecidosXQtdeNecessaria.get(chave);			
			SugestaoReservaPorTecido dadosTecido = mapDadosPorTecido.get(chave); //(codigoProduto.getCodigo());
			
			saldoDisponivel = dadosTecido.getQtdeSaldo();
			
			if (saldoDisponivel < 0.0) saldoDisponivel = 0.0; 
			
			//System.out.println("codTecido: " + codTecido + " - qtdeNecessidade: " + qtdeNecessidade + " - saldo: " + saldoDisponivel);			
			if (qtdeNecessidade > saldoDisponivel) saldoDisponivel += obterQtdeSaldoTecidosSubstitutos(codigoProduto.getNivel(), codigoProduto.getGrupo(), codigoProduto.getSub(), codigoProduto.getItem());
			
			if (qtdeNecessidade > saldoDisponivel) {
				if (saldoDisponivel > 0) percentual = (saldoDisponivel / qtdeNecessidade) * 100;
				else percentual = 0.0;
				
				mapTecidoXPercentual.put(codigoProduto.getCodigo(), percentual);				
				//System.out.println("PERCENTUAL: " + percentual);

				if ((primeiroPercentual) || (percentual <= menorPercentual)) { 
					menorPercentual = percentual;
					primeiroPercentual=false;
				}	
			}
		}		
				
		// Mantém apenas os tecidos com menor percentual para recalculo
		if (mapTecidoXPercentual.size() > 0) {
			List<String> chavesExcluir = new ArrayList<String>();
			for (String chave : mapTecidoXPercentual.keySet()) {
				percentual = mapTecidoXPercentual.get(chave);				
				//System.out.println("VERIFICA EXCLUIR TECIDO - percentual: " + percentual + " - menorPercentual: " + menorPercentual);				
				if (percentual > menorPercentual) chavesExcluir.add(chave); 
			}
			chavesExcluir.forEach(chave -> mapTecidoXPercentual.remove(chave));
			chavesExcluir.clear();
		}				
		
		// Recalcular as quantidades com base nos tecidos que possuem maior percentual
		if (mapTecidoXPercentual.size() > 0) {			
			for (String codTecidoRecalcular : mapTecidoXPercentual.keySet()) {		
				conteudo = codTecidoRecalcular.split("[.]");
			    percentual = mapTecidoXPercentual.get(codTecidoRecalcular);					    
			    //System.out.println("RECALCULAR: codTecidoRecalcular: " + codTecidoRecalcular + " - PERCENTUAL: " + percentual);			    
				recalcularQtdePecasDaOrdem(ordem, conteudo[0], conteudo[1], conteudo[2], conteudo[3], percentual);				
			}			
			confirmaRecalculoApenasSeAtendeQtdeMinima(ordem);
			recalcularNecessidadeTecidosDaOrdem(ordem);
		}
	}

	private double obterQtdeSaldoTecidosSubstitutos(String nivelTecido, String grupoTecido, String subTecido, String itemTecido) {
		
		double qtdeSubstitutos = 0;
		
		List<Produto> substitutos = produtoCustom.findTecidosSubstitutos(nivelTecido, grupoTecido, subTecido, itemTecido);
		
		for (Produto substituto : substitutos) {			
			String codTecido = new CodigoProduto(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem()).getCodigo();				
			SugestaoReservaPorTecido dadosTecido = mapDadosPorTecido.get(codTecido);			
			if (dadosTecido.getQtdeSaldo() > 0) qtdeSubstitutos += dadosTecido.getQtdeSaldo();
		}
		
		return qtdeSubstitutos;
	}
	
	private void recalcularQtdePecasDaOrdem(OrdemProducao ordem, String nivelTecido, String grupoTecido, String subTecido, String itemTecido, double percentualMaximoPecas) {
		//System.out.println("recalcularQtdePecasDaOrdem");
		List<OrdemProducaoItem> itensRecalculados;
		List<String> sortimentos = new ArrayList<String>();		
		List<SugestaoReservaPorOrdemSortimento> tecidos = mapDadosPorOrdem.get(ordem.ordemProducao);				
		
		//if (ordem.ordemProducao == 300799) System.out.println("recalcularQtdePecasDaOrdem"); 
		
		if (mapPecasRecalculadas.containsKey(ordem.ordemProducao))
			itensRecalculados = mapPecasRecalculadas.get(ordem.ordemProducao);
		else itensRecalculados = new ArrayList<OrdemProducaoItem>();
				
		Stream<SugestaoReservaPorOrdemSortimento> stream = tecidos.stream().filter(t -> t.getNivel().equals(nivelTecido) && t.getGrupo().equals(grupoTecido) && t.getSub().equals(subTecido) && t.getItem().equals(itemTecido));
		List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList()); 		
				
		//System.out.println(nivelTecido + "." + grupoTecido  + "." + subTecido  + "." + itemTecido + " - " + percentualDiminuir);
		
		// localiza os sortimentos que usam o tecido
		for (SugestaoReservaPorOrdemSortimento tecidoOrdem : filtro) {			
			if (!sortimentos.contains(tecidoOrdem.getSortimento())) sortimentos.add(tecidoOrdem.getSortimento());			
		}

		// para cada sortimento irá diminuir as quantidades conforme O percentual
		for (String sortimento : sortimentos) {			
			
			List<OrdemProducaoItem> itens = ordemProducaoCustom.findItensByOrdemProducaoAndCor(ordem.ordemProducao, sortimento);
				
			int novaQtde;
			
			for (OrdemProducaoItem item : itens) {				
				// mantem apenas um item recalculado, pois em tese se houverem mais que um o percentual será o mesmo.
				itensRecalculados.removeIf(r -> r.tamanho.equals(item.tamanho) && r.cor.equals(item.cor));
				
				//if (ordem.ordemProducao == 300799) System.out.println(item.tamanho + " - " + item.cor + " - % " + percentualMaximoPecas + " - " + item.qtdePecasProgramada);
				
				novaQtde = (int) ((double) item.qtdePecasProgramada * (percentualMaximoPecas / 100.0));
				
				//if (ordem.ordemProducao == 300799) System.out.println("novaQtde: " + novaQtde);
				
				if (novaQtde < 0) novaQtde = 0;  									
				
				OrdemProducaoItem itemRecalculado = new OrdemProducaoItem(ordem.ordemProducao, ordem.referencia, ordem.periodo, ordem.nrAlternativa, ordem.nrRoteiro, novaQtde, item.tamanho, item.cor);
				
				//if (ordem.ordemProducao == 300799) System.out.println("gravou-> " + itemRecalculado.qtdePecasProgramada);
				
				itensRecalculados.add(itemRecalculado);
			}
		}
		
		if (itensRecalculados.size() > 0) mapPecasRecalculadas.put(ordem.ordemProducao, itensRecalculados);		
	}
		
	private void confirmaRecalculoApenasSeAtendeQtdeMinima(OrdemProducao ordem) {

		List<OrdemProducaoItem> previstas = mapPecasPrevistas.get(ordem.ordemProducao);	
		List<OrdemProducaoItem> recalculados = mapPecasRecalculadas.get(ordem.ordemProducao);
				
		Map<String, Integer> mapProdutoXQtde = new HashMap<String, Integer>();
		
		String codProduto = "";
		int qtdeTotalPecas = 0;
		int quantidade = 0;
		
		for (OrdemProducaoItem recalculado : recalculados) {
			codProduto = recalculado.referencia + "." + recalculado.tamanho + "." + recalculado.cor;
			mapProdutoXQtde.put(codProduto, recalculado.qtdePecasProgramada);
		}
		
		for (OrdemProducaoItem previsto : previstas) {
			quantidade = previsto.qtdePecasProgramada;			
			codProduto = previsto.referencia + "." + previsto.tamanho + "." + previsto.cor;
			if (mapProdutoXQtde.containsKey(codProduto)) quantidade = mapProdutoXQtde.get(codProduto); 						
			qtdeTotalPecas += quantidade; 
		}
		
		int percentualAtendido = (int) (((double) qtdeTotalPecas / (double) ordem.qtdePecasProgramada) * 100);				

		//if (ordem.ordemProducao == 300799) System.out.println("CONFIRMA QTDES: percentualAtendido: " + percentualAtendido + " <= percentualMinimoAtender: " + percentualMinimoAtender);		
		
		if (percentualAtendido < percentualMinimoAtender) {			
			mapPecasRecalculadas.remove(ordem.ordemProducao);
			ArrayList<OrdemProducaoItem> itensRecalculados = new ArrayList<OrdemProducaoItem>();
			
			for (OrdemProducaoItem previsto : previstas) {			
				OrdemProducaoItem itemRecalculado = new OrdemProducaoItem(ordem.ordemProducao, ordem.referencia, ordem.periodo, ordem.nrAlternativa, ordem.nrRoteiro, 0, previsto.tamanho, previsto.cor);
				itensRecalculados.add(itemRecalculado);
			}
			mapPecasRecalculadas.put(ordem.ordemProducao, itensRecalculados);
		}
	}	
	
	private void recalcularNecessidadeTecidosDaOrdem(OrdemProducao ordem) {
		//System.out.println("recalcularNecessidadeTecidosDaOrdem");
		
		// DEVE RECALCULAR APENAS OS TECIDOS 
		
		if (mapPecasRecalculadas.containsKey(ordem.ordemProducao)) {						
			List<OrdemProducaoItem> itensRecalculados = mapPecasRecalculadas.get(ordem.ordemProducao);		
			List<SugestaoReservaPorOrdemSortimento> necessidadesOrdem = mapDadosPorOrdem.get(ordem.ordemProducao);
			
			for (OrdemProducaoItem recalculado : itensRecalculados) {						
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(recalculado.getReferencia(), recalculado.getTamanho(), recalculado.getCor(), recalculado.getNrAlternativa(), recalculado.getQtdePecasProgramada());
				
				//System.out.println("recalculado: " + recalculado.sub + " programado: " + recalculado.qtdeProgramada);
				
				for (NecessidadeTecidos tecido : tecidos) {								
					Stream<SugestaoReservaPorOrdemSortimento> stream = necessidadesOrdem.stream().filter(n -> n.getSequencia() == tecido.getSequencia() && n.getNivel().equals(tecido.getNivel()) && n.getGrupo().equals(tecido.getGrupo()) && n.getSub().equals(tecido.getSub()) && n.getItem().equals(tecido.getItem()));
					List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList());
		
					//System.out.println("necessidade tecido: seq: " + tecido.getSequencia() + " kg: " + tecido.getQtdeKg() + " qtde tec: " + filtro.size());
					
					for (SugestaoReservaPorOrdemSortimento sugestao : filtro) {
						sugestao.setRecalculado(true);
						sugestao.setQtdeNecessidadeRecalculada(sugestao.getQtdeNecessidadeRecalculada() + tecido.getQtdeKg());					
					}
				}
			}			
		}		
	}	
	
	private void reservaTecidoParaOrdem(SugestaoReservaPorOrdemSortimento tecidoOrdem) {			
		//System.out.println("reservaTecidoParaOrdem");
		double qtdeReservada = 0;
		double qtdeReservar = tecidoOrdem.getQtdeNecessidadeCalculada();
		
		//System.out.println("ORDEM DE PRODUCAO: " + tecidoOrdem.getIdOrdem());
		
		SugestaoReservaPorTecido tecido = mapDadosPorTecido.get(new CodigoProduto(tecidoOrdem.getNivel(), tecidoOrdem.getGrupo(), tecidoOrdem.getSub(), tecidoOrdem.getItem()).getCodigo());
				
		//System.out.println(tecidoOrdem.getSequencia() + " - codTecido-> " + codTecido + " - saldo: "+ tecido.getQtdeSaldo() + " - PREVISTO: " + tecidoOrdem.getQtdeNecessidade() + " - RECALC: " + tecidoOrdem.getQtdeNecessidadeRecalculada()); 

		double qtdeSaldoTecido = tecido.getQtdeSaldo();		
		double qtdeSaldoSubstitutos = obterQtdeSaldoTecidosSubstitutos(tecidoOrdem.getNivel(), tecidoOrdem.getGrupo(), tecidoOrdem.getSub(), tecidoOrdem.getItem());		
		
		if (qtdeSaldoTecido < 0) qtdeSaldoTecido = 0;
		if (qtdeSaldoSubstitutos < 0) qtdeSaldoSubstitutos = 0;
		
		double qtdeSaldoTotalTecido = qtdeSaldoTecido + qtdeSaldoSubstitutos; 		
		tecidoOrdem.setQtdeDisponivelTecidoSubstituto(qtdeSaldoSubstitutos);
		
		//System.out.println("qtdeSaldoTotalTecido: " + qtdeSaldoTotalTecido);
		
		if (qtdeSaldoTotalTecido > 0) {		
			if (qtdeReservar < qtdeSaldoTotalTecido)
				qtdeReservada = qtdeReservar;
			else qtdeReservada = qtdeSaldoTotalTecido;

			tecidoOrdem.setQtdeDisponivel(qtdeSaldoTecido);
			tecidoOrdem.setQtdeSugerido(tecidoOrdem.getQtdeSugerido() + qtdeReservada);
			
			//System.out.println("qtdeReservada: " + qtdeReservada);
			
			if (qtdeReservada <= qtdeSaldoTecido) {
				tecido.setQtdeSugerido(tecido.getQtdeSugerido() + qtdeReservada);
				guardarDadosTecidosReservados(tecidoOrdem.getIdOrdem(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), qtdeReservada);
			} else {

				// Se houver saldo do principal, sugere o saldo.
				if (qtdeSaldoTecido > 0.0) {
					tecido.setQtdeSugerido(tecido.getQtdeSugerido() + qtdeSaldoTecido);
					guardarDadosTecidosReservados(tecidoOrdem.getIdOrdem(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), qtdeSaldoTecido);
				}
				
				double qtdeSugerirSubstitutos = qtdeReservada - qtdeSaldoTecido;
				
				double qtdeSugerirSubstituto = 0;
				List<Produto> substitutos = produtoCustom.findTecidosSubstitutos(tecidoOrdem.getNivel(), tecidoOrdem.getGrupo(), tecidoOrdem.getSub(), tecidoOrdem.getItem());
				
				//if (tecidoOrdem.getIdOrdem() == 321338) System.out.println(tecidoOrdem.getNivel() + "." + tecidoOrdem.getGrupo() + "." + tecidoOrdem.getSub() + "." + tecidoOrdem.getItem()); 									
				//if (tecidoOrdem.getIdOrdem() == 319584) System.out.println("AVALIA SUBSTITUTOS");
				
				for (Produto substituto : substitutos) {
					if (qtdeSugerirSubstitutos <= 0.0) break;					
					//if (tecidoOrdem.getIdOrdem() == 321338) System.out.println(tecidoOrdem.getIdOrdem() + " - " + substituto.getGrupo());										
					qtdeSugerirSubstituto = qtdeSugerirSubstitutos; 					
					SugestaoReservaPorTecido dadosSubstituto = mapDadosPorTecido.get(new CodigoProduto(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem()).getCodigo());
					if (qtdeSugerirSubstituto > dadosSubstituto.getQtdeSaldo()) qtdeSugerirSubstituto = dadosSubstituto.getQtdeSaldo();
					if (qtdeSugerirSubstituto <= 0.0) continue;
					//if (tecidoOrdem.getIdOrdem() == 319584) System.out.println("substitutos: " + new CodigoProduto(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem()).getCodigo() + " - qtdeSugerirSubstituto: " + qtdeSugerirSubstituto );					
					//System.out.println("substituto: " + new CodigoProduto(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem()).getCodigo() + " - qtdeSugerirSubstituto: " + qtdeSugerirSubstituto + " - dadosSubstituto.getQtdeSaldo(): " + dadosSubstituto.getQtdeSaldo());															
					dadosSubstituto.setQtdeSugerido(dadosSubstituto.getQtdeSugerido() + qtdeSugerirSubstituto);					
					guardarDadosTecidosReservados(tecidoOrdem.getIdOrdem(), dadosSubstituto.getNivel(), dadosSubstituto.getGrupo(), dadosSubstituto.getSub(), dadosSubstituto.getItem(), qtdeSugerirSubstituto);
					qtdeSugerirSubstitutos -= qtdeSugerirSubstituto;
				}
			}
			
			//System.out.println("tecido ordem disponivel-> " + tecidoOrdem.getQtdeDisponivel());
			//System.out.println("tecido ordem sugerido-> " + tecidoOrdem.getQtdeSugerido());
			//System.out.println("tecido ordem saldo-> " + tecidoOrdem.getQtdeSaldo());
		}
	}	
	
	private void calcularNecessidades() {
		//System.out.println("calcularNecessidades");
		
		int prioridade = ordemProducaoCustom.findUltimaSeqPrioridadeDia();
				
		for (OrdemProducao ordem : listaPriorizadaOrdens) {						
			//System.out.println("PRIORIDADE: " + prioridade + " ID: " + ordem.ordemProducao + " EMBARQUE: " + ordem.dataEmbarque + " QTDE EST CRITICO: " + ordem.qtdeEstagioCritico + " TEMPO PROD UNIT: " + ordem.tempoProducaoUnit);
			prioridade++;
			
			ordem.setSeqPrioridade(prioridade);
			
			List<OrdemProducaoItem> dadosItensOrdem = ordemProducaoCustom.findItensByOrdemProducao(ordem.ordemProducao);

			//if (ordem.id == 20009) System.out.println("idOrdem " + ordem.id);
			
			for (OrdemProducaoItem item : dadosItensOrdem) {
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(ordem.referencia, item.tamanho, item.cor, ordem.nrAlternativa, item.qtdePecasProgramada);
				
				Produto peca = produtoCustom.findProduto("1", ordem.referencia, item.tamanho, item.cor);
				
				guardarQtdesOriginaisPecasPrevistas(ordem.ordemProducao, ordem.referencia, ordem.nrAlternativa, ordem.nrRoteiro, ordem.periodo, item.tamanho, item.cor, item.qtdePecasProgramada);
				guardarDadosPorProduto(prioridade, "1", ordem.referencia, item.tamanho, item.cor, peca.seqTamanho, peca.narrativa, ordem.nrAlternativa, ordem.nrRoteiro, ordem.dataEmbarque, ordem.qtdeEstagioCritico, ordem.tempoProducaoUnit, ordem.tempoCosturaUnit, item.qtdePecasProgramada);
				
				for (NecessidadeTecidos tecido : tecidos) {															
					guardarDadosPorTecidoOrdem(ordem, item, tecido);
				}				
			}
		}
	}

	private Map<String, Double> ObtemQtdeEstoqueQtdeEmpenhada(String nivelTecido, String grupoTecido, String subTecido, String itemTecido) {
		Map<String, Double> mapQtdeEstoqueQtdeEmpenhada = new HashMap<String, Double>();
		
		double qtdeEstoque = estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(nivelTecido, grupoTecido, subTecido, itemTecido, depositosTecidos);					
		double qtdeEmpenhada = sugestaoReservaTecidoCustom.findQtdeReservadaByProduto(nivelTecido, grupoTecido, subTecido, itemTecido);
		
		mapQtdeEstoqueQtdeEmpenhada.put("qtdeEstoque", qtdeEstoque);
		mapQtdeEstoqueQtdeEmpenhada.put("qtdeEmpenhada", qtdeEmpenhada);
		
		return mapQtdeEstoqueQtdeEmpenhada;
	}
	
	private void guardarDadosPorTecidoOrdem(OrdemProducao ordem, OrdemProducaoItem item, NecessidadeTecidos tecido) {
		Produto dadosTecido = produtoCustom.findProduto(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem());					
		
		Map<String, Double> mapQtdeEstoqueQtdeEmpenhada = ObtemQtdeEstoqueQtdeEmpenhada(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem());
		
		double qtdeEstoque = mapQtdeEstoqueQtdeEmpenhada.get("qtdeEstoque");					
		double qtdeEmpenhada = mapQtdeEstoqueQtdeEmpenhada.get("qtdeEmpenhada");
		
		guardarDadosPorTecido(tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), dadosTecido.getNarrativa(), dadosTecido.getUnidade(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada, true);
		guardarDadosPorOrdem(ordem.ordemProducao, item.cor, tecido.getSequencia(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), dadosTecido.getNarrativa(), dadosTecido.getUnidade(), tecido.getQtdeKgUnit(), tecido.getQtdeKg(), qtdeEstoque, qtdeEmpenhada);
	}
 
	private void guardarQtdesOriginaisPecasPrevistas(int idOrdem, String grupo, int alternativa, int roteiro, int periodo, String sub, String item, int qtdeProgramada) {		
		//System.out.println("guardarQtdesOriginaisPecasPrevistas");
		List<OrdemProducaoItem> itensOrdem; 
		
		if (mapPecasPrevistas.containsKey(idOrdem)) itensOrdem = mapPecasPrevistas.get(idOrdem);
		else itensOrdem = new ArrayList<OrdemProducaoItem>();					
		
		//if (grupo.equalsIgnoreCase("P3478") && sub.equalsIgnoreCase("G") && item.equalsIgnoreCase("00CZ55")) System.out.println("QTDE PROG => " + qtdeProgramada + " ID ORDEM: " + idOrdem); 
		
		itensOrdem.add(new OrdemProducaoItem(idOrdem, grupo, periodo, alternativa, roteiro, qtdeProgramada, sub, item));		
		mapPecasPrevistas.put(idOrdem, itensOrdem); 		
	}
 
	private void guardarDadosPorProduto(int prioridade, String nivel, String grupo, String tamanho, String cor, int seqTamanho, String descricao, int alternativa, int roteiro, Date dataEmbarque, int qtdeEstagioCritico, double tempoProducaoUnit, double tempoCosturaUnit, int qtdePrevista) {
		//System.out.println("guardarDadosPorProduto");
		SugestaoReservaPorProduto sugestao;
		
		String chave = chaveMapDadosPorProduto(nivel, grupo, tamanho, cor, alternativa, roteiro);
		
		if (mapDadosPorProduto.containsKey(chave)) {
			sugestao = mapDadosPorProduto.get(chave);
			sugestao.setQtdePrevista(sugestao.getQtdePrevista() + qtdePrevista);			
		}
		else {
			sugestao = new SugestaoReservaPorProduto(prioridade, nivel, grupo, tamanho, cor, seqTamanho, descricao, alternativa, roteiro, dataEmbarque, qtdeEstagioCritico, tempoProducaoUnit, tempoCosturaUnit, qtdePrevista, 0);	
			mapDadosPorProduto.put(chave, sugestao);		
		}
	}
	
	private void guardarDadosPorTecido(String nivelTecido, String grupoTecido, String subTecido, String itemTecido, String descricaoTecido, String unidadeTecido, double qtdeKg, double qtdeEstoque, double qtdeEmpenhada, boolean guardarDadosSubstituto) {
		//System.out.println("guardarDadosPorTecido");
		
		String codTecido = new CodigoProduto(nivelTecido, grupoTecido, subTecido, itemTecido).getCodigo();
		
		//System.out.println(codTecido + " -> kg: " + qtdeKg); 
		
		if (mapDadosPorTecido.containsKey(codTecido)) {			
			SugestaoReservaPorTecido sugestao = mapDadosPorTecido.get(codTecido);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);

			if (guardarDadosSubstituto) {
				if (!sugestao.isSubstitutosLocalizados()) {
					guardarDadosPorTecidoSubstituto(nivelTecido, grupoTecido, subTecido, itemTecido);
					sugestao.setSubstitutosLocalizados(true);
				}
			}
		} else {			
			SugestaoReservaPorTecido sugestao = new SugestaoReservaPorTecido(nivelTecido, grupoTecido, subTecido,
					itemTecido, descricaoTecido, unidadeTecido, qtdeKg, qtdeEstoque, qtdeEmpenhada, 0);			
			mapDadosPorTecido.put(codTecido, sugestao);
			
			if (guardarDadosSubstituto) { 
				guardarDadosPorTecidoSubstituto(nivelTecido, grupoTecido, subTecido, itemTecido);
				sugestao.setSubstitutosLocalizados(true);
			}
		}
	}
	
	private void guardarDadosPorTecidoSubstituto(String nivelTecido, String grupoTecido, String subTecido, String itemTecido) {
		
		List<Produto> substitutos = produtoCustom.findTecidosSubstitutos(nivelTecido, grupoTecido, subTecido, itemTecido);
		
		for (Produto substituto : substitutos) {						
			Produto dadosTecido = produtoCustom.findProduto(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem());					
			Map<String, Double> mapQtdeEstoqueQtdeEmpenhada = ObtemQtdeEstoqueQtdeEmpenhada(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem());			
			guardarDadosPorTecido(substituto.getNivel(), substituto.getGrupo(), substituto.getSub(), substituto.getItem(), dadosTecido.getNarrativa(), dadosTecido.getUnidade(), 0, (double) mapQtdeEstoqueQtdeEmpenhada.get("qtdeEstoque"), (double) mapQtdeEstoqueQtdeEmpenhada.get("qtdeEmpenhada"), false);
		}
	}

	private void guardarDadosPorOrdem(int idOrdem, String sortimento, int sequencia, String nivelTecido, String grupoTecido,
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
	
	private void guardarDadosTecidosReservados(int idOrdem, String nivelTecido, String grupoTecido, String subTecido, String itemTecido, double qtdeReservado) {				
		//if (idOrdem == 319584) System.out.println("guardarDadosDetalhadosTecidosReservados " + idOrdem + " - " + grupoTecido + " - " + qtdeReservado);
		
		if (mapTecidosReservados.containsKey(idOrdem)) {
			List<SugestaoReservaTecidosReservados> tecidosReservados = mapTecidosReservados.get(idOrdem);			
			Stream<SugestaoReservaTecidosReservados> stream = tecidosReservados.stream().filter(t -> t.nivelTecido.equals(nivelTecido) && t.grupoTecido.equals(grupoTecido) && t.subTecido.equals(subTecido) && t.itemTecido.equals(itemTecido));			
			List<SugestaoReservaTecidosReservados> filtro = stream.collect(Collectors.toList());
			if (filtro.size() > 0) {
				for (SugestaoReservaTecidosReservados item : filtro) {
					item.qtdeReservado += qtdeReservado;
				}				
			} else {
				SugestaoReservaTecidosReservados reserva = new SugestaoReservaTecidosReservados(idOrdem, nivelTecido, grupoTecido, subTecido, itemTecido, qtdeReservado);		
				tecidosReservados.add(reserva);	
			}
		} else {
			List<SugestaoReservaTecidosReservados> tecidosReservados = new ArrayList<SugestaoReservaTecidosReservados>();
			SugestaoReservaTecidosReservados reserva = new SugestaoReservaTecidosReservados(idOrdem, nivelTecido, grupoTecido, subTecido, itemTecido, qtdeReservado);
			tecidosReservados.add(reserva);
			mapTecidosReservados.put(idOrdem, tecidosReservados);			
		}				 		
	}
	
	private String chaveMapDadosPorProduto(String nivel, String grupo, String sub, String item, int alternativa, int roteiro) {
		return nivel + "." + grupo + "." + sub + "." + item + "." + alternativa + "." + roteiro;
	}	
}	