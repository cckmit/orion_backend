package br.com.live.producao.service;

import java.math.BigDecimal;
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

import br.com.live.producao.custom.OrdemProducaoCustom;
import br.com.live.producao.custom.SugestaoReservaMaterialCustom;
import br.com.live.producao.custom.SuprimentoCustom;
import br.com.live.producao.model.NecessidadeAviamentos;
import br.com.live.producao.model.NecessidadeTecidos;
import br.com.live.producao.model.OrdemProducao;
import br.com.live.producao.model.OrdemProducaoItem;
import br.com.live.producao.model.SugestaoMaterialDetalhaGradeTamanhos;
import br.com.live.producao.model.SugestaoMaterialDetalhaSortimentos;
import br.com.live.producao.model.SugestaoReservaMateriais;
import br.com.live.producao.model.SugestaoReservaMateriaisReservados;
import br.com.live.producao.model.SugestaoReservaPorMaterial;
import br.com.live.producao.model.SugestaoReservaPorOrdemMaterial;
import br.com.live.producao.model.SugestaoReservaPorOrdemSortimento;
import br.com.live.producao.model.SugestaoReservaPorProduto;
import br.com.live.produto.custom.EstoqueProdutoCustom;
import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.produto.model.Produto;
import br.com.live.util.CodigoProduto;

@Service
@Transactional
public class SugestaoReservaMaterialPorOrdensService {
	
	private static final int RESERVAR_QTDE_RECALCULADA = 0; 
	private static final int TECIDO = 1;
	private static final int AVIAMENTO = 2;
	
	private final SugestaoReservaMaterialCustom sugestaoReservaMaterialCustom;
	private final ProdutoCustom produtoCustom;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
	private final OrdemProducaoCustom ordemProducaoCustom;	
	private final SuprimentoCustom suprimentoCustom; 

	private int periodoMostruario;
	private int periodoMostruarioCompras;
	private int regraReserva;
	private String depositosTecidos;
	private String depositosAviamentos;
	private int percentualMinimoAtender;
	private boolean isMostruario;
	private List<OrdemProducao> listaPriorizadaOrdens;
	private List<SugestaoMaterialDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento;
	private List<SugestaoMaterialDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho;	
	private Map<String, SugestaoReservaPorMaterial> mapDadosPorTecido;	
	private Map<String, SugestaoReservaPorMaterial> mapDadosPorAviamento;
	private Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosTecidosPorOrdem;
	private Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosAviamentosPorOrdem;
	private Map<String, SugestaoReservaPorProduto> mapDadosPorProduto;
	private Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadasPorTecidos;
	private Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadasPorAviamentos;
	private Map<Integer, List<OrdemProducaoItem>> mapPecasPrevistas;
	private Map<Integer, List<SugestaoReservaMateriaisReservados>> mapMateriaisReservados;	
	
	public SugestaoReservaMaterialPorOrdensService(SugestaoReservaMaterialCustom sugestaoReservaMaterialCustom, 
			ProdutoCustom produtoCustom, 
			EstoqueProdutoCustom estoqueProdutoCustom,
			OrdemProducaoCustom ordemProducaoCustom, SuprimentoCustom suprimentoCustom) {						
		this.sugestaoReservaMaterialCustom = sugestaoReservaMaterialCustom;
		this.produtoCustom = produtoCustom;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;		
		this.suprimentoCustom = suprimentoCustom;
	}

	public SugestaoReservaMateriais calcularSugestaoReserva(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String embarques, String referencias, String estagios, String artigos, String tecidos, String depositosTecidos, String depositosAviamentos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isOrdensSemTecido, int percentualMinimoAtender, int regraReserva, boolean isMostruario) {		
		// System.out.println("calcularSugestaoReserva");		

		System.out.println("Inicio do calculo de sugestao de reserva de tecidos " + new Date());
		
		iniciarListasAuxiliares();
		
		this.periodoMostruario=parsePeriodoToPeriodoMostruario(periodoFinal); // A análise de mostruário sempre vai se basear pelo periodo inicial informado na tela.
		this.periodoMostruarioCompras=parsePeriodoToPeriodoMostruarioCompras();
		this.regraReserva = regraReserva;		
		this.depositosTecidos = depositosTecidos;
		this.depositosAviamentos = depositosAviamentos;
		this.percentualMinimoAtender = percentualMinimoAtender;
		this.isMostruario = isMostruario;
		
		listaGradeDetPrevistoAtendidoPorSortimento = new ArrayList<SugestaoMaterialDetalhaSortimentos>();
		listaGradeDetPrevistoAtendidoPorTamanho = new ArrayList<SugestaoMaterialDetalhaGradeTamanhos>();
		listaPriorizadaOrdens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao, periodoInicial, periodoFinal, Integer.toString(SugestaoReservaMaterialCustom.ESTAGIO_ANALISE_MATERIAIS), embarques, referencias, estagios, artigos, tecidos, isSomenteFlat, isDiretoCostura, isOrdensSemTecido, false); 
		
		System.out.println("calcularNecessidades " + new Date());				
		calcularNecessidades();
		
		System.out.println("reservarMateriais - Tecidos " + new Date());		
		reservarMateriais(TECIDO);
		
		System.out.println("reservarMateriais - Aviamentos " + new Date());
		reservarMateriais(AVIAMENTO);
		
		System.out.println("obter dados calculados " + new Date());
		SugestaoReservaMateriais sugestao = obterDadosSugestaoReserva();
				
		limparListasAuxiliares();		
		System.out.println("fim do calculo de sugestao de reserva de tecidos " + new Date());
		
		return sugestao;
	}
	
	private void iniciarListasAuxiliares() {		
		mapDadosPorTecido = new HashMap<String, SugestaoReservaPorMaterial>();		
		mapDadosPorAviamento = new HashMap<String, SugestaoReservaPorMaterial>();
		mapDadosTecidosPorOrdem = new HashMap<Integer, List<SugestaoReservaPorOrdemSortimento>>();		
		mapDadosAviamentosPorOrdem = new HashMap<Integer, List<SugestaoReservaPorOrdemSortimento>>();
		mapDadosPorProduto = new HashMap<String, SugestaoReservaPorProduto>();
		mapPecasPrevistas = new HashMap<Integer, List<OrdemProducaoItem>>();		
		mapPecasRecalculadasPorTecidos = new HashMap<Integer, List<OrdemProducaoItem>>();
		mapPecasRecalculadasPorAviamentos = new HashMap<Integer, List<OrdemProducaoItem>>();
		mapMateriaisReservados = new HashMap<Integer, List<SugestaoReservaMateriaisReservados>>() ;					
	}
	
	private void limparListasAuxiliares() {		
		mapDadosPorTecido.clear();
		mapDadosPorAviamento.clear();
		mapDadosTecidosPorOrdem.clear();		
		mapDadosAviamentosPorOrdem.clear();
		mapDadosPorProduto.clear();
		mapPecasPrevistas.clear();
		mapPecasRecalculadasPorTecidos.clear();	
		mapPecasRecalculadasPorAviamentos.clear();
		mapMateriaisReservados.clear();
	}
	
	private SugestaoReservaMateriais obterDadosSugestaoReserva() {
		List<SugestaoReservaPorOrdemMaterial> listaSugestaoTecidoPorOrdens = obterListaSugestaoReservaPorOrdemTecido(TECIDO);
		List<SugestaoReservaPorOrdemMaterial> listaSugestaoAviamentoPorOrdens = obterListaSugestaoReservaPorOrdemTecido(AVIAMENTO);	
		List<SugestaoReservaPorProduto> listaSugestaoPorProdutos = obterListaSugestaoReservaPorProduto();	
		List<SugestaoReservaPorMaterial> listaSugestaoPorTecidos = obterListaSugestaoReservaPorTecido();
		List<SugestaoReservaPorMaterial> listaSugestaoPorAviamentos = obterListaSugestaoReservaPorAviamento();
		List<SugestaoReservaMateriaisReservados> listaDetalhaMateriaisReservados = obterListaDetalhadaMateriaisReservados();
		return new SugestaoReservaMateriais(listaPriorizadaOrdens, listaGradeDetPrevistoAtendidoPorSortimento, listaGradeDetPrevistoAtendidoPorTamanho, listaSugestaoTecidoPorOrdens, listaSugestaoAviamentoPorOrdens, listaSugestaoPorProdutos, listaSugestaoPorTecidos, listaSugestaoPorAviamentos, listaDetalhaMateriaisReservados);
	}
	
 	private List<SugestaoReservaPorOrdemMaterial> obterListaSugestaoReservaPorOrdemTecido(int tipoMaterial) {
		//System.out.println("obterListaSugestaoReservaPorOrdemTecido");
		
 		Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
 		List<SugestaoReservaPorOrdemMaterial> sugestao = new ArrayList<SugestaoReservaPorOrdemMaterial>();				
		
		if (tipoMaterial == TECIDO) mapDadosPorOrdem = mapDadosTecidosPorOrdem;
		else mapDadosPorOrdem = mapDadosAviamentosPorOrdem;
		
		for (Integer idOrdem : mapDadosPorOrdem.keySet()) {
			List<SugestaoReservaPorOrdemSortimento> itens = mapDadosPorOrdem.get(idOrdem);
			
			for (SugestaoReservaPorOrdemSortimento item : itens) {
				//System.out.println(item.getSequencia() + " - COR: " + item.getItem() + " - KG: " + item.getQtdeNecessidade() + " - SUGERIDO: " + item.getQtdeSugerido() + " - SALDO: " + item.getQtdeSaldo() + " - DISPONIVEL: " + item.getQtdeDisponivel());
				
				Stream<SugestaoReservaPorOrdemMaterial> stream = sugestao.stream().filter(s -> s.getIdOrdem() == idOrdem && s.getNivel().equals(item.getNivel()) && s.getGrupo().equals(item.getGrupo()) && s.getSub().equals(item.getSub()) && s.getItem().equals(item.getItem()));
				List<SugestaoReservaPorOrdemMaterial> filtro = stream.collect(Collectors.toList()); 		

				if (filtro.size() > 0) {				
					for (SugestaoReservaPorOrdemMaterial sugestaoReservaPorOrdemMaterial : filtro) {				
						sugestaoReservaPorOrdemMaterial.setQtdeNecessidadeUnit(sugestaoReservaPorOrdemMaterial.getQtdeNecessidadeUnit() + item.getQtdeNecessidadeUnit());
						sugestaoReservaPorOrdemMaterial.setQtdeNecessidade(sugestaoReservaPorOrdemMaterial.getQtdeNecessidade() + item.getQtdeNecessidade());
						sugestaoReservaPorOrdemMaterial.setQtdeSugerido(sugestaoReservaPorOrdemMaterial.getQtdeSugerido() + item.getQtdeSugerido());
 					    //System.out.println("PASSOU POR AQUI: " + idOrdem + " - " + item.getQtdeDisponivel());						
						//if (item.getQtdeDisponivel() > sugestaoReservaPorOrdemTecido.getQtdeDisponivel()) sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());
					}
				} else {
					SugestaoReservaPorOrdemMaterial sugestaoReservaPorOrdemMaterial = new SugestaoReservaPorOrdemMaterial(idOrdem, item.getNivel(), item.getGrupo(), item.getSub(), item.getItem(), item.getDescricao(), item.getUnidade(), item.getQtdeNecessidadeUnit(), item.getQtdeNecessidade(), item.getQtdeEstoque(), item.getQtdeEmpenhada(), item.getQtdeSugerido(), item.getPedidoCompraAberto(), item.getDataEntregaPedidoCompra(), item.getQtdeReceber());
					sugestaoReservaPorOrdemMaterial.setQtdeDisponivel(item.getQtdeDisponivel());
					sugestaoReservaPorOrdemMaterial.setQtdeDisponivelTecidoSubstituto(item.getQtdeDisponivelTecidoSubstituto());
					//if (sugestaoReservaPorOrdemTecido.getQtdeDisponivel() > item.getQtdeDisponivel()) sugestaoReservaPorOrdemTecido.setQtdeDisponivel(item.getQtdeDisponivel());
					//if (sugestaoReservaPorOrdemTecido.getQtdeDisponivelTecidoSubstituto() > item.getQtdeDisponivelTecidoSubstituto()) sugestaoReservaPorOrdemTecido.setQtdeDisponivelTecidoSubstituto(item.getQtdeDisponivelTecidoSubstituto());					
					sugestao.add(sugestaoReservaPorOrdemMaterial);
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
	
	private List<SugestaoReservaPorMaterial> obterListaSugestaoReservaPorTecido() {		
		List<SugestaoReservaPorMaterial> sugestao = new ArrayList<SugestaoReservaPorMaterial>();		
		for (String tecido : mapDadosPorTecido.keySet()) {
			SugestaoReservaPorMaterial item = mapDadosPorTecido.get(tecido);
			sugestao.add(item);
		}
		
		Collections.sort(sugestao);
		
		return sugestao;
	}

	private List<SugestaoReservaPorMaterial> obterListaSugestaoReservaPorAviamento() {		
		List<SugestaoReservaPorMaterial> sugestao = new ArrayList<SugestaoReservaPorMaterial>();		
		for (String tecido : mapDadosPorAviamento.keySet()) {
			SugestaoReservaPorMaterial item = mapDadosPorAviamento.get(tecido);
			sugestao.add(item);
		}
		
		Collections.sort(sugestao);
		
		return sugestao;
	}

	private List<SugestaoReservaMateriaisReservados> obterListaDetalhadaMateriaisReservados() {
		List<SugestaoReservaMateriaisReservados> materiais = new ArrayList<SugestaoReservaMateriaisReservados>();
		for (Integer idOrdem : mapMateriaisReservados.keySet()) {
			List<SugestaoReservaMateriaisReservados> reservados = mapMateriaisReservados.get(idOrdem); 
			for (SugestaoReservaMateriaisReservados reservado : reservados) {
				materiais.add(reservado);
			}
		}
		return materiais;
	}

	private void reservarMateriais(int tipoMaterial) {
		//System.out.println("reservarMateriais");
		
		Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
		
		if (tipoMaterial == TECIDO) mapDadosPorOrdem = mapDadosTecidosPorOrdem;
		else mapDadosPorOrdem = mapDadosAviamentosPorOrdem;
		
		for (OrdemProducao ordem : listaPriorizadaOrdens) {
			
			calcularQtdesPecasSeraoAtendidas(ordem, tipoMaterial);
			
			if (mapDadosPorOrdem.containsKey(ordem.ordemProducao)) {			
				List<SugestaoReservaPorOrdemSortimento> materiaisOrdem = mapDadosPorOrdem.get(ordem.ordemProducao);			
				for (SugestaoReservaPorOrdemSortimento materialOrdem : materiaisOrdem) {					
					//System.out.println("RESERVA - ORDEM: " + ordem.id + " - SORTIMENTO: " + tecidoOrdem.getSortimento() + " - SEQ: " + tecidoOrdem.getSequencia() + " - TECIDO: " + tecidoOrdem.getGrupo() + "." + tecidoOrdem.getSub() + "." + tecidoOrdem.getItem() + " = " + tecidoOrdem.getQtdeDisponivel() + " - " + tecidoOrdem.getQtdeNecessidadeCalculada());					
					reservaMaterialParaOrdem(tipoMaterial, materialOrdem);
				}
			}
			
			atenderQtdesPecas(ordem, tipoMaterial);
		}
	}

	private void atenderQtdesPecas(OrdemProducao ordem, int tipoMaterial) {		
		//System.out.println("guardarQtdesPecasAtendidas");
		String chave;
		int qtdeTotalAtendida = 0;
		SugestaoReservaPorProduto sugestao;
		Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadas;
		List<OrdemProducaoItem> pecasRecalculadas;			
		List<String> listPecasRecalculadas = new ArrayList<String>();		
		
		if (tipoMaterial == TECIDO) mapPecasRecalculadas = mapPecasRecalculadasPorTecidos;
		else mapPecasRecalculadas = mapPecasRecalculadasPorAviamentos;		
		
		if (mapPecasPrevistas.containsKey(ordem.ordemProducao)) {
			
			List<OrdemProducaoItem> pecasPrevistas = mapPecasPrevistas.get(ordem.ordemProducao);
			
			// inicializa qtdes por sortimento e grade
			for (OrdemProducaoItem peca : pecasPrevistas) {
				guardarQtdesPrevAtendPorSortimentoEGrade(peca.getOrdemProducao(), peca.getCor(), peca.getOrdemTamanho(), peca.getTamanho(), peca.getQtdePecasProgramada(), 0, 0);
			}
			
			if (mapPecasRecalculadas.containsKey(ordem.ordemProducao)) {
				pecasRecalculadas = mapPecasRecalculadas.get(ordem.ordemProducao);
				
				for (OrdemProducaoItem peca : pecasRecalculadas) {									
					//if (ordem.ordemProducao == 317395) System.out.println("REMOVE: " + peca.tamanho + "  -  " + peca.cor);
					chave = chaveMapDadosPorProduto("1", peca.referencia, peca.tamanho, peca.cor, peca.nrAlternativa, peca.nrRoteiro);
					
					if (mapDadosPorProduto.containsKey(chave)) {
						sugestao = mapDadosPorProduto.get(chave);
						if (tipoMaterial == TECIDO) sugestao.setQtdeAtendidaPorTecido(sugestao.getQtdeAtendidaPorTecido() + peca.qtdePecasProgramada);
						else sugestao.setQtdeAtendidaPorAviamento(sugestao.getQtdeAtendidaPorAviamento() + peca.qtdePecasProgramada);
						qtdeTotalAtendida += peca.qtdePecasProgramada;												
					}										
					
					if (tipoMaterial == TECIDO) guardarQtdesPrevAtendTecidoPorSortimentoEGrade(peca.getOrdemProducao(), peca.getCor(), peca.getOrdemTamanho(), peca.getTamanho(), peca.getQtdePecasProgramada());
					else guardarQtdesPrevAtendAviamentoPorSortimentoEGrade(peca.getOrdemProducao(), peca.getCor(), peca.getOrdemTamanho(), peca.getTamanho(), peca.getQtdePecasProgramada());
				
					listPecasRecalculadas.add(chave);
				}	
			}
	
			// Se item não foi recalculado, então é porque tem tecido que atende as quantidades previstas (programadas).							
	 		for (OrdemProducaoItem peca : pecasPrevistas) {			
	 			
	 			chave = chaveMapDadosPorProduto("1", peca.referencia, peca.tamanho, peca.cor, peca.nrAlternativa, peca.nrRoteiro);
	 			
	 			if (!listPecasRecalculadas.contains(chave)) { 		 					 								
					if (mapDadosPorProduto.containsKey(chave)) {
						sugestao = mapDadosPorProduto.get(chave);
						if (tipoMaterial == TECIDO) sugestao.setQtdeAtendidaPorTecido(sugestao.getQtdeAtendidaPorTecido() + peca.qtdePecasProgramada);
						else sugestao.setQtdeAtendidaPorAviamento(sugestao.getQtdeAtendidaPorAviamento() + peca.qtdePecasProgramada);
						qtdeTotalAtendida += peca.qtdePecasProgramada;
					}			
					
					if (tipoMaterial == TECIDO) guardarQtdesPrevAtendTecidoPorSortimentoEGrade(peca.getOrdemProducao(), peca.getCor(), peca.getOrdemTamanho(), peca.getTamanho(), peca.getQtdePecasProgramada());
					else guardarQtdesPrevAtendAviamentoPorSortimentoEGrade(peca.getOrdemProducao(), peca.getCor(), peca.getOrdemTamanho(), peca.getTamanho(), peca.getQtdePecasProgramada());					
	 			}
			}
			
	 		//if (ordem.ordemProducao == 300799) System.out.println("qtdeTotalAtendida: " + qtdeTotalAtendida);
	 		if (tipoMaterial == TECIDO) ordem.setQtdeMaxPecasReservaTecidoAtende(qtdeTotalAtendida);
	 		else ordem.setQtdeMaxPecasReservaAviamentoAtende(qtdeTotalAtendida);
		}
	}
	
 	private void calcularQtdesPecasSeraoAtendidas(OrdemProducao ordem, int tipoMaterial) {
		//System.out.println("revisarQtdesQueSeraoAtendidasNaOrdem");
		
		Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
		Map<String, Double> mapMateriaisXQtdeNecessaria = new HashMap<String, Double>();

		if (tipoMaterial == TECIDO) mapDadosPorOrdem = mapDadosTecidosPorOrdem;
		else mapDadosPorOrdem = mapDadosAviamentosPorOrdem;
		
		String [] conteudo;
		double qtdeNecessidade;		
		double percentual;
		
		if (mapDadosPorOrdem.containsKey(ordem.ordemProducao)) {		
			List<SugestaoReservaPorOrdemSortimento> materiais = mapDadosPorOrdem.get(ordem.ordemProducao);
		
			for (SugestaoReservaPorOrdemSortimento material : materiais) {
				String chave = new CodigoProduto(material.getNivel(), material.getGrupo(), material.getSub(), material.getItem()).getCodigo();
				
				qtdeNecessidade = 0;	
				
				if (mapMateriaisXQtdeNecessaria.containsKey(chave)) qtdeNecessidade = mapMateriaisXQtdeNecessaria.get(chave);
				
				qtdeNecessidade += material.getQtdeNecessidade(); 				
				//System.out.println("chave: " + chave + " - qtdeNecessidade: " + qtdeNecessidade);				
				mapMateriaisXQtdeNecessaria.put(chave, qtdeNecessidade);
			}
		}
		
		boolean primeiroPercentual = true;
		double saldoDisponivel = 0.0;
		double menorPercentual = 0.0;		
		Map<String, Double> mapMaterialXPercentual = new HashMap<String, Double>(); 
		
		for (String chave : mapMateriaisXQtdeNecessaria.keySet()) {
			conteudo = chave.split("[.]");
			CodigoProduto codigoProduto = new CodigoProduto(conteudo[0], conteudo[1], conteudo[2], conteudo[3]);
			
			qtdeNecessidade = mapMateriaisXQtdeNecessaria.get(chave);			
			
			SugestaoReservaPorMaterial dadosMaterial; 			
			
			if (tipoMaterial == TECIDO) dadosMaterial = mapDadosPorTecido.get(chave);
			else dadosMaterial = mapDadosPorAviamento.get(chave);
			
			saldoDisponivel = dadosMaterial.getQtdeSaldo();
			
			if (saldoDisponivel < 0.0) saldoDisponivel = 0.0; 
			
			//System.out.println("codTecido: " + codTecido + " - qtdeNecessidade: " + qtdeNecessidade + " - saldo: " + saldoDisponivel);
				
			if (qtdeNecessidade > saldoDisponivel) {
				if (saldoDisponivel > 0) percentual = (saldoDisponivel / qtdeNecessidade) * 100;
				else percentual = 0.0;
				
				mapMaterialXPercentual.put(codigoProduto.getCodigo(), percentual);				
				//System.out.println("PERCENTUAL: " + percentual);

				if ((primeiroPercentual) || (percentual <= menorPercentual)) { 
					menorPercentual = percentual;
					primeiroPercentual=false;
				}	
			}
		}		
				
		// Mantém apenas os Materiais com menor percentual para recalculo
		if (mapMaterialXPercentual.size() > 0) {
			List<String> chavesExcluir = new ArrayList<String>();
			for (String chave : mapMaterialXPercentual.keySet()) {
				percentual = mapMaterialXPercentual.get(chave);				
				//System.out.println("VERIFICA EXCLUIR TECIDO - percentual: " + percentual + " - menorPercentual: " + menorPercentual);				
				if (percentual > menorPercentual) chavesExcluir.add(chave); 
			}
			chavesExcluir.forEach(chave -> mapMaterialXPercentual.remove(chave));
			chavesExcluir.clear();
		}				
		
		// Recalcular as quantidades com base nos tecidos que possuem maior percentual
		if (mapMaterialXPercentual.size() > 0) {			
			for (String codMaterialRecalcular : mapMaterialXPercentual.keySet()) {		
				conteudo = codMaterialRecalcular.split("[.]");
			    percentual = mapMaterialXPercentual.get(codMaterialRecalcular);					    
			    //System.out.println("RECALCULAR: codTecidoRecalcular: " + codTecidoRecalcular + " - PERCENTUAL: " + percentual);			    
				recalcularQtdePecasDaOrdem(ordem, tipoMaterial, conteudo[0], conteudo[1], conteudo[2], conteudo[3], percentual);				
			}			
			confirmaRecalculoApenasSeAtendeQtdeMinima(ordem, tipoMaterial);
			recalcularNecessidadeTecidosDaOrdem(ordem, tipoMaterial);
		}
	}

	private void recalcularQtdePecasDaOrdem(OrdemProducao ordem, int tipoMaterial, String nivelMaterial, String grupoMaterial, String subMaterial, String itemMaterial, double percentualMaximoPecas) {
		//System.out.println("recalcularQtdePecasDaOrdem");
		List<OrdemProducaoItem> itensRecalculados;
		List<String> sortimentos = new ArrayList<String>();		
		Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
		Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadas;
		
		if (tipoMaterial == TECIDO) { 			
			mapDadosPorOrdem = mapDadosTecidosPorOrdem;
			mapPecasRecalculadas = mapPecasRecalculadasPorTecidos;
		} else {
			mapDadosPorOrdem = mapDadosAviamentosPorOrdem;
			mapPecasRecalculadas = mapPecasRecalculadasPorAviamentos;
		}
		
		List<SugestaoReservaPorOrdemSortimento> materiais = mapDadosPorOrdem.get(ordem.ordemProducao);				
		
		//if (ordem.ordemProducao == 300799) System.out.println("recalcularQtdePecasDaOrdem"); 
		
		if (mapPecasRecalculadas.containsKey(ordem.ordemProducao))
			itensRecalculados = mapPecasRecalculadas.get(ordem.ordemProducao);
		else itensRecalculados = new ArrayList<OrdemProducaoItem>();
				
		Stream<SugestaoReservaPorOrdemSortimento> stream = materiais.stream().filter(t -> t.getNivel().equals(nivelMaterial) && t.getGrupo().equals(grupoMaterial) && t.getSub().equals(subMaterial) && t.getItem().equals(itemMaterial));
		List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList()); 		
				
		//System.out.println(nivelTecido + "." + grupoTecido  + "." + subTecido  + "." + itemTecido + " - " + percentualDiminuir);
		
		// localiza os sortimentos que usam o tecido
		for (SugestaoReservaPorOrdemSortimento materialOrdem : filtro) {			
			if (!sortimentos.contains(materialOrdem.getSortimento())) sortimentos.add(materialOrdem.getSortimento());			
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
				
				OrdemProducaoItem itemRecalculado = new OrdemProducaoItem(ordem.ordemProducao, ordem.referencia, ordem.periodo, ordem.nrAlternativa, ordem.nrRoteiro, novaQtde, item.tamanho, item.cor, item.ordemTamanho);
				
				//if (ordem.ordemProducao == 300799) System.out.println("gravou-> " + itemRecalculado.qtdePecasProgramada);
				
				itensRecalculados.add(itemRecalculado);
			}
		}
		
		if (itensRecalculados.size() > 0) mapPecasRecalculadas.put(ordem.ordemProducao, itensRecalculados);		
	}
 
	private void confirmaRecalculoApenasSeAtendeQtdeMinima(OrdemProducao ordem, int tipoMaterial) {

		List<OrdemProducaoItem> recalculados;
		List<OrdemProducaoItem> previstas = mapPecasPrevistas.get(ordem.ordemProducao);						
		Map<String, Integer> mapProdutoXQtde = new HashMap<String, Integer>();
		Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadas;
		
		if (tipoMaterial == TECIDO) mapPecasRecalculadas = mapPecasRecalculadasPorTecidos;
		else mapPecasRecalculadas = mapPecasRecalculadasPorAviamentos;
		
		recalculados = mapPecasRecalculadas.get(ordem.ordemProducao);
				
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
				OrdemProducaoItem itemRecalculado = new OrdemProducaoItem(ordem.ordemProducao, ordem.referencia, ordem.periodo, ordem.nrAlternativa, ordem.nrRoteiro, 0, previsto.tamanho, previsto.cor, previsto.ordemTamanho);
				itensRecalculados.add(itemRecalculado);
			}
			mapPecasRecalculadas.put(ordem.ordemProducao, itensRecalculados);				
		}
	}	
 
	private void recalcularNecessidadeTecidosDaOrdem(OrdemProducao ordem, int tipoMaterial) {
		//System.out.println("recalcularNecessidadeTecidosDaOrdem");
		
		Map<Integer, List<OrdemProducaoItem>> mapPecasRecalculadas;
		Map<Integer, List<SugestaoReservaPorOrdemSortimento>> mapDadosPorOrdem;
		
		if (tipoMaterial == TECIDO) {
			mapPecasRecalculadas = mapPecasRecalculadasPorTecidos;
			mapDadosPorOrdem = mapDadosTecidosPorOrdem;
		}
		else {
			mapPecasRecalculadas = mapPecasRecalculadasPorAviamentos;
			mapDadosPorOrdem = mapDadosAviamentosPorOrdem;
		}				
		
 		if (mapPecasRecalculadas.containsKey(ordem.ordemProducao)) {						
			List<OrdemProducaoItem> itensRecalculados = mapPecasRecalculadas.get(ordem.ordemProducao);		
			List<SugestaoReservaPorOrdemSortimento> necessidadesOrdem = mapDadosPorOrdem.get(ordem.ordemProducao);
			
			for (OrdemProducaoItem recalculado : itensRecalculados) {										
				if (tipoMaterial == TECIDO) {
					
					// ***** RECALCULA TECIDO *****
					
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
				} else {
					
					// ***** RECALCULA AVIAMENTO *****
					
					List<NecessidadeAviamentos> aviamentos = produtoCustom.calcularNecessidadeAviamento(recalculado.getReferencia(), recalculado.getTamanho(), recalculado.getCor(), recalculado.getNrAlternativa(), recalculado.getQtdePecasProgramada());									
					//System.out.println("recalculado: " + recalculado.sub + " programado: " + recalculado.qtdeProgramada);					
					for (NecessidadeAviamentos aviamento : aviamentos) {								
						Stream<SugestaoReservaPorOrdemSortimento> stream = necessidadesOrdem.stream().filter(n -> n.getSequencia() == aviamento.getSequencia() && n.getNivel().equals(aviamento.getNivel()) && n.getGrupo().equals(aviamento.getGrupo()) && n.getSub().equals(aviamento.getSub()) && n.getItem().equals(aviamento.getItem()));
						List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList());			
						//System.out.println("necessidade tecido: seq: " + tecido.getSequencia() + " kg: " + tecido.getQtdeKg() + " qtde tec: " + filtro.size());						
						for (SugestaoReservaPorOrdemSortimento sugestao : filtro) {
							sugestao.setRecalculado(true);
							sugestao.setQtdeNecessidadeRecalculada(sugestao.getQtdeNecessidadeRecalculada() + aviamento.getQuantidade());					
						}
					}					
				}				
			}			
		}		
	}	
		
	private double getQtdeReservar(double qtdeOriginal, double qtdeRecalculada) {
		if (this.regraReserva == RESERVAR_QTDE_RECALCULADA) return qtdeRecalculada;
		return qtdeOriginal;
	}	
	
	private void reservaMaterialParaOrdem(int tipoMaterial, SugestaoReservaPorOrdemSortimento materialOrdem) {			
		//System.out.println("reservaMaterialParaOrdem");
		
		Map<String, SugestaoReservaPorMaterial> mapDadosPorMaterial; 

		if (tipoMaterial == TECIDO) mapDadosPorMaterial = mapDadosPorTecido;
		else mapDadosPorMaterial = mapDadosPorAviamento;
		
		double qtdeReservada = 0;
		double qtdeReservar = 0; 
		
		qtdeReservar = getQtdeReservar(materialOrdem.getQtdeNecessidade(), materialOrdem.getQtdeNecessidadeCalculada());
		
		//System.out.println("ORDEM DE PRODUCAO: " + tecidoOrdem.getIdOrdem());		
		
		SugestaoReservaPorMaterial material = mapDadosPorMaterial.get(new CodigoProduto(materialOrdem.getNivel(), materialOrdem.getGrupo(), materialOrdem.getSub(), materialOrdem.getItem()).getCodigo());
				
		//System.out.println(tecidoOrdem.getSequencia() + " saldo: "+ tecido.getQtdeSaldo() + " - PREVISTO: " + tecidoOrdem.getQtdeNecessidade() + " CALC: " + tecidoOrdem.getQtdeNecessidadeCalculada() + " - RECALC: " + tecidoOrdem.getQtdeNecessidadeRecalculada()); 
			
		double qtdeFaltaTecido = materialOrdem.getQtdeNecessidade() - materialOrdem.getQtdeNecessidadeCalculada();
		double qtdeSaldoTecido = material.getQtdeSaldo();		
		
		if (qtdeSaldoTecido < 0) qtdeSaldoTecido = 0;
		double qtdeSaldoTotalTecido = qtdeSaldoTecido; 		
		
		// System.out.println("qtdeSaldoTotalTecido: " + qtdeSaldoTotalTecido + "  - qtdeReservar: " + qtdeReservar);		
		// if (tecidoOrdem.getIdOrdem() == 343831) System.out.println("qtdeSaldoTotalTecido: " + qtdeSaldoTotalTecido + "  - qtdeReservar: " + qtdeReservar); 				
		materialOrdem.setQtdeDisponivel(qtdeSaldoTecido);
		
		if (qtdeSaldoTotalTecido > 0) {		
			if (qtdeReservar < qtdeSaldoTotalTecido)
				qtdeReservada = qtdeReservar;
			else qtdeReservada = qtdeSaldoTotalTecido;
			
			materialOrdem.setQtdeSugerido(materialOrdem.getQtdeSugerido() + qtdeReservada);
			
			// System.out.println("qtdeReservada: " + qtdeReservada);			
			if (qtdeReservada <= qtdeSaldoTecido) {
				material.setQtdeSugerido(material.getQtdeSugerido() + qtdeReservada);
				guardarDadosMateriaisReservados(materialOrdem.getIdOrdem(), material.getNivel(), material.getGrupo(), material.getSub(), material.getItem(), qtdeReservada);
			} else {

				// Se houver saldo do principal, sugere o saldo.
				if (qtdeSaldoTecido > 0.0) {
					material.setQtdeSugerido(material.getQtdeSugerido() + qtdeSaldoTecido);
					guardarDadosMateriaisReservados(materialOrdem.getIdOrdem(), material.getNivel(), material.getGrupo(), material.getSub(), material.getItem(), qtdeSaldoTecido);
				}								
			}
		}				
		//System.out.println("Qtde falta tecido: " + qtdeFaltaTecido);		
		// Se ficar um saldo a ser atendido do tecido principal, deve ser considerado também para a reserva, caso contrário ocorrerá erros nos próximos calculos.
		if (qtdeFaltaTecido > 0.0) guardarDadosMateriaisReservados(materialOrdem.getIdOrdem(), materialOrdem.getNivel(), materialOrdem.getGrupo(), materialOrdem.getSub(), materialOrdem.getItem(), qtdeFaltaTecido);				
	}	
	
	private void calcularNecessidades() {
		//System.out.println("calcularNecessidades");
		
		int contadorOP = 0;
		int prioridade = ordemProducaoCustom.findUltimaSeqPrioridadeDia();		
		for (OrdemProducao ordem : listaPriorizadaOrdens) {						
			contadorOP++ ;
			System.out.println(contadorOP + " - Ordem: " + ordem.ordemProducao + " - " + new Date());
			//System.out.println("PRIORIDADE: " + prioridade + " ID: " + ordem.ordemProducao + " EMBARQUE: " + ordem.dataEmbarque + " QTDE EST CRITICO: " + ordem.qtdeEstagioCritico + " TEMPO PROD UNIT: " + ordem.tempoProducaoUnit);
			prioridade++;			
			ordem.setSeqPrioridade(prioridade);
			ordem.setCores(ordemProducaoCustom.getCoresOrdem(ordem.ordemProducao));			
			ordem.setLembreteSugestao(sugestaoReservaMaterialCustom.findLembreteByOrdem(ordem.ordemProducao));			
			List<OrdemProducaoItem> dadosItensOrdem = ordemProducaoCustom.findItensByOrdemProducao(ordem.ordemProducao);			
			//if (ordem.id == 20009) System.out.println("idOrdem " + ordem.id);			
			for (OrdemProducaoItem item : dadosItensOrdem) {
				List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(ordem.referencia, item.tamanho, item.cor, ordem.nrAlternativa, item.qtdePecasProgramada);				
				List<NecessidadeAviamentos> aviamentos = produtoCustom.calcularNecessidadeAviamento(ordem.referencia, item.tamanho, item.cor, ordem.nrAlternativa, item.qtdePecasProgramada);								
				Produto peca = produtoCustom.findProduto("1", ordem.referencia, item.tamanho, item.cor);

				guardarQtdesOriginaisPecasPrevistas(ordem.ordemProducao, ordem.referencia, ordem.nrAlternativa, ordem.nrRoteiro, ordem.periodo, item.tamanho, item.cor, item.qtdePecasProgramada, item.ordemTamanho);
				guardarDadosPorProduto(prioridade, "1", ordem.referencia, item.tamanho, item.cor, peca.seqTamanho, peca.narrativa, ordem.nrAlternativa, ordem.nrRoteiro, ordem.dataEmbarque, ordem.qtdeEstagioCritico, ordem.tempoProducaoUnit, ordem.tempoCosturaUnit, item.qtdePecasProgramada);
				
				for (NecessidadeTecidos tecido : tecidos) {															
					guardarDadosPorTecidoOrdem(ordem, item, tecido);
				}											
				for (NecessidadeAviamentos aviamento : aviamentos) {
					guardarDadosPorAviamentoOrdem(ordem, item, aviamento);
				}	
			}
		}
	}

	private Map<String, Object> ObterQtdeEstoqueQtdeEmpenhada(String nivelMaterial, String grupoMaterial, String subMaterial, String itemMaterial, String depositos) {
		Map<String, Object> mapQtdeEstoqueQtdeEmpenhada = new HashMap<String, Object>();

		double qtdeEstoque = estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos(nivelMaterial, grupoMaterial, subMaterial, itemMaterial, depositos);					
		double qtdeEmpenhada = sugestaoReservaMaterialCustom.findQtdeReservadaByProduto(nivelMaterial, grupoMaterial, subMaterial, itemMaterial, this.isMostruario, this.periodoMostruario);
		
		mapQtdeEstoqueQtdeEmpenhada.put("qtdeEstoque", qtdeEstoque);
		mapQtdeEstoqueQtdeEmpenhada.put("qtdeEmpenhada", qtdeEmpenhada);
		
		return mapQtdeEstoqueQtdeEmpenhada;
	}

	private Map<String, Object> obterDadosComplementares(String nivelMaterial, String grupoMaterial, String subMaterial, String itemMaterial, String depositos) {
		
		Map<String, Object> mapQtdeEstoqueQtdeEmpenhada = ObterQtdeEstoqueQtdeEmpenhada(nivelMaterial, grupoMaterial, subMaterial, itemMaterial, depositos);
		Map<String, Object> mapPedidoAndDataEntrega = suprimentoCustom.findDadosPedidoCompraMaisProximoByItem(nivelMaterial, grupoMaterial, subMaterial, itemMaterial, this.isMostruario, this.periodoMostruarioCompras);
		Map<String, Object> mapRetorno = new HashMap<String, Object> ();
		
		double qtdeEstoque = mapQtdeEstoqueQtdeEmpenhada.containsKey("qtdeEstoque") ? (double) mapQtdeEstoqueQtdeEmpenhada.get("qtdeEstoque") : 0;					
		double qtdeEmpenhada = mapQtdeEstoqueQtdeEmpenhada.containsKey("qtdeEmpenhada") ?  (double) mapQtdeEstoqueQtdeEmpenhada.get("qtdeEmpenhada") : 0;
		BigDecimal pedidoCompra = mapPedidoAndDataEntrega.containsKey("num_ped_compra") ? (BigDecimal) mapPedidoAndDataEntrega.get("num_ped_compra") : new BigDecimal(0);
		BigDecimal qtdeSaldoReceber = mapPedidoAndDataEntrega.containsKey("qtde_saldo_item") ? (BigDecimal) mapPedidoAndDataEntrega.get("qtde_saldo_item") : new BigDecimal(0);
		Date dataEntrega = mapPedidoAndDataEntrega.containsKey("data_prev_entr") ?  (Date) mapPedidoAndDataEntrega.get("data_prev_entr") : null;
				
		mapRetorno.put("qtdeEstoque", qtdeEstoque);
		mapRetorno.put("qtdeEmpenhada", qtdeEmpenhada);
		mapRetorno.put("pedidoCompra", pedidoCompra.intValue());
		mapRetorno.put("qtdeSaldoReceber", qtdeSaldoReceber.doubleValue());
		mapRetorno.put("dataEntrega", dataEntrega);
						
		return mapRetorno;
	}
	
	private void guardarDadosPorMaterialOrdem(OrdemProducao ordem, OrdemProducaoItem item, int seqMaterial, String nivelMaterial, String grupoMaterial, String subMaterial, String itemMaterial, double qtdeNecessaria, double qtdeUnitaria) {
		Produto dadosMaterial = produtoCustom.findProduto(nivelMaterial, grupoMaterial, subMaterial, itemMaterial);							
		if (nivelMaterial.equalsIgnoreCase("2")) guardarDadosPorTecido(nivelMaterial, grupoMaterial, subMaterial, itemMaterial, dadosMaterial.getNarrativa(), dadosMaterial.getUnidade(), qtdeNecessaria);
		else guardarDadosPorAviamento(nivelMaterial, grupoMaterial, subMaterial, itemMaterial, dadosMaterial.getNarrativa(), dadosMaterial.getUnidade(), qtdeNecessaria); 		
		guardarDadosPorOrdem(ordem.ordemProducao, item.cor, seqMaterial, nivelMaterial, grupoMaterial, subMaterial, itemMaterial, dadosMaterial.getNarrativa(), dadosMaterial.getUnidade(), qtdeUnitaria, qtdeNecessaria);
	}
	
	private void guardarDadosPorTecidoOrdem(OrdemProducao ordem, OrdemProducaoItem item, NecessidadeTecidos tecido) {
		guardarDadosPorMaterialOrdem(ordem, item, tecido.getSequencia(), tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), tecido.getQtdeKg(), tecido.getQtdeKgUnit());		
	}
 
	private void guardarDadosPorAviamentoOrdem(OrdemProducao ordem, OrdemProducaoItem item, NecessidadeAviamentos aviamento) {
		guardarDadosPorMaterialOrdem(ordem, item, aviamento.getSequencia(), aviamento.getNivel(), aviamento.getGrupo(), aviamento.getSub(), aviamento.getItem(), aviamento.getQuantidade(), aviamento.getQuantidadeUnit());		
	}
		
	private void guardarQtdesOriginaisPecasPrevistas(int idOrdem, String grupo, int alternativa, int roteiro, int periodo, String sub, String item, int qtdeProgramada, int ordemTamanho) {		
		//System.out.println("guardarQtdesOriginaisPecasPrevistas");
		List<OrdemProducaoItem> itensOrdem; 
		
		if (mapPecasPrevistas.containsKey(idOrdem)) itensOrdem = mapPecasPrevistas.get(idOrdem);
		else itensOrdem = new ArrayList<OrdemProducaoItem>();					
		
		//if (grupo.equalsIgnoreCase("P3478") && sub.equalsIgnoreCase("G") && item.equalsIgnoreCase("00CZ55")) System.out.println("QTDE PROG => " + qtdeProgramada + " ID ORDEM: " + idOrdem); 
		
		itensOrdem.add(new OrdemProducaoItem(idOrdem, grupo, periodo, alternativa, roteiro, qtdeProgramada, sub, item, ordemTamanho));		
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
			sugestao = new SugestaoReservaPorProduto(prioridade, nivel, grupo, tamanho, cor, seqTamanho, descricao, alternativa, roteiro, dataEmbarque, qtdeEstagioCritico, tempoProducaoUnit, tempoCosturaUnit, qtdePrevista, 0, 0);	
			mapDadosPorProduto.put(chave, sugestao);		
		}
	} 

	private void guardarDadosPorTecido(String nivelTecido, String grupoTecido, String subTecido, String itemTecido, String descricaoTecido, String unidadeTecido, double qtdeKg) {
		//System.out.println("guardarDadosPorTecido");		
		String codTecido = new CodigoProduto(nivelTecido, grupoTecido, subTecido, itemTecido).getCodigo();
		
		if (mapDadosPorTecido.containsKey(codTecido)) {			
			SugestaoReservaPorMaterial sugestao = mapDadosPorTecido.get(codTecido);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + qtdeKg);
		} else {			
			
			Map<String, Object> mapDados = obterDadosComplementares(nivelTecido, grupoTecido, subTecido, itemTecido, this.depositosTecidos);
			double qtdeEstoque = (double) mapDados.get("qtdeEstoque");
			double qtdeEmpenhada = (double) mapDados.get("qtdeEmpenhada");
			int pedidoCompra = (int) mapDados.get("pedidoCompra");
			double qtdeSaldoReceber = (double) mapDados.get("qtdeSaldoReceber");
			Date dataEntrega = (Date) mapDados.get("dataEntrega");			
			
			SugestaoReservaPorMaterial sugestao = new SugestaoReservaPorMaterial(nivelTecido, grupoTecido, subTecido,
					itemTecido, descricaoTecido, unidadeTecido, qtdeKg, qtdeEstoque, qtdeEmpenhada, 0, pedidoCompra, dataEntrega, qtdeSaldoReceber);			
			mapDadosPorTecido.put(codTecido, sugestao);			
		}
	}
 
	private void guardarDadosPorAviamento(String nivelAviamento, String grupoAviamento, String subAviamento, String itemAviamento, String descricaoAviamento, String unidadeAviamento, double quantidade) {
		//System.out.println("guardarDadosPorAviamento");
		String codAviamento = new CodigoProduto(nivelAviamento, grupoAviamento, subAviamento, itemAviamento).getCodigo();
		
		if (mapDadosPorAviamento.containsKey(codAviamento)) {			
			SugestaoReservaPorMaterial sugestao = mapDadosPorAviamento.get(codAviamento);
			sugestao.setQtdeNecessidade(sugestao.getQtdeNecessidade() + quantidade);
		} else {			

			Map<String, Object> mapDados = obterDadosComplementares(nivelAviamento, grupoAviamento, subAviamento, itemAviamento, this.depositosAviamentos);
			double qtdeEstoque = (double) mapDados.get("qtdeEstoque");
			double qtdeEmpenhada = (double) mapDados.get("qtdeEmpenhada");
			int pedidoCompra = (int) mapDados.get("pedidoCompra");
			double qtdeSaldoReceber = (double) mapDados.get("qtdeSaldoReceber");
			Date dataEntrega = (Date) mapDados.get("dataEntrega");
			
			SugestaoReservaPorMaterial sugestao = new SugestaoReservaPorMaterial(nivelAviamento, grupoAviamento, subAviamento,
					itemAviamento, descricaoAviamento, unidadeAviamento, quantidade, qtdeEstoque, qtdeEmpenhada, 0, pedidoCompra, dataEntrega, qtdeSaldoReceber);			
			mapDadosPorAviamento.put(codAviamento, sugestao);			
		}
	}
	
	private boolean contemMaterialNaOrdem(int idOrdem, String nivelMaterial) {
		if (nivelMaterial.equalsIgnoreCase("2")) return mapDadosTecidosPorOrdem.containsKey(idOrdem);
		else return mapDadosAviamentosPorOrdem.containsKey(idOrdem);				
	}
	
	private List<SugestaoReservaPorOrdemSortimento> obterListaMateriaisPorNivel(int idOrdem, String nivelMaterial){
		if (nivelMaterial.equalsIgnoreCase("2")) return mapDadosTecidosPorOrdem.get(idOrdem);
		return mapDadosAviamentosPorOrdem.get(idOrdem);		
	}
	
	private void guardarListaMateriaisNaOrdem(int idOrdem, String nivelMaterial, List<SugestaoReservaPorOrdemSortimento> materiais) {
		if (nivelMaterial.equalsIgnoreCase("2")) mapDadosTecidosPorOrdem.put(idOrdem, materiais);
		else mapDadosAviamentosPorOrdem.put(idOrdem, materiais);
	}

	private void guardarDadosPorOrdem(int idOrdem, String sortimento, int sequencia, String nivelMaterial, String grupoMaterial,
			String subMaterial, String itemMaterial, String descricaoMaterial, String unidadeMaterial, double qtdeKgUnit, double qtdeKg) {
		//System.out.println("guardarDadosPorOrdem");		

		String codMaterial = new CodigoProduto(nivelMaterial, grupoMaterial, subMaterial, itemMaterial).getCodigo();
		SugestaoReservaPorMaterial dadosMaterial;

		if (nivelMaterial.equalsIgnoreCase("2")) dadosMaterial = mapDadosPorTecido.get(codMaterial);
		else dadosMaterial = mapDadosPorAviamento.get(codMaterial);		

		if (contemMaterialNaOrdem(idOrdem, nivelMaterial)) {
			List<SugestaoReservaPorOrdemSortimento> materiais = obterListaMateriaisPorNivel(idOrdem, nivelMaterial);
			Stream<SugestaoReservaPorOrdemSortimento> stream = materiais.stream().filter(t -> t.getSortimento().equals(sortimento) && t.getSequencia() == sequencia && t.getNivel().equals(nivelMaterial) && t.getGrupo().equals(grupoMaterial) && t.getSub().equals(subMaterial) && t.getItem().equals(itemMaterial));			
			List<SugestaoReservaPorOrdemSortimento> filtro = stream.collect(Collectors.toList());		
			if (filtro.size() > 0) {
				for (SugestaoReservaPorOrdemSortimento item : filtro) {					
					item.setQtdeNecessidade(item.getQtdeNecessidade() + qtdeKg);					
				}
			} else {
				SugestaoReservaPorOrdemSortimento sugestao = new SugestaoReservaPorOrdemSortimento(idOrdem, sortimento, sequencia, nivelMaterial, grupoMaterial, subMaterial, itemMaterial, descricaoMaterial, unidadeMaterial,
						qtdeKg, dadosMaterial.getQtdeEstoque(), dadosMaterial.getQtdeEmpenhada(), 0, qtdeKgUnit, dadosMaterial.getPedidoCompraAberto(), dadosMaterial.getDataEntregaPedidoCompra(), dadosMaterial.getQtdeReceber());
				materiais.add(sugestao);
			}
		} else {
			List<SugestaoReservaPorOrdemSortimento> materiais = new ArrayList<SugestaoReservaPorOrdemSortimento>();
			
			SugestaoReservaPorOrdemSortimento sugestao = new SugestaoReservaPorOrdemSortimento(idOrdem, sortimento, sequencia, nivelMaterial, grupoMaterial, subMaterial, itemMaterial, descricaoMaterial, unidadeMaterial,
					qtdeKg, dadosMaterial.getQtdeEstoque(), dadosMaterial.getQtdeEmpenhada(), 0, qtdeKgUnit, dadosMaterial.getPedidoCompraAberto(), dadosMaterial.getDataEntregaPedidoCompra(), dadosMaterial.getQtdeReceber());
			materiais.add(sugestao);			
			guardarListaMateriaisNaOrdem(idOrdem, nivelMaterial, materiais);
		}
	}
	
	private void guardarDadosMateriaisReservados(int idOrdem, String nivelMaterial, String grupoMaterial, String subMaterial, String itemMaterial, double qtdeReservado) {				
		//if (idOrdem == 319584) System.out.println("guardarDadosDetalhadosTecidosReservados " + idOrdem + " - " + grupoTecido + " - " + qtdeReservado);
		
		if (mapMateriaisReservados.containsKey(idOrdem)) {
			List<SugestaoReservaMateriaisReservados> materiaisReservados = mapMateriaisReservados.get(idOrdem);			
			Stream<SugestaoReservaMateriaisReservados> stream = materiaisReservados.stream().filter(t -> t.nivelMaterial.equals(nivelMaterial) && t.grupoMaterial.equals(grupoMaterial) && t.subMaterial.equals(subMaterial) && t.itemMaterial.equals(itemMaterial));			
			List<SugestaoReservaMateriaisReservados> filtro = stream.collect(Collectors.toList());
			if (filtro.size() > 0) {
				for (SugestaoReservaMateriaisReservados item : filtro) {
					item.qtdeReservado += qtdeReservado;
				}				
			} else {
				SugestaoReservaMateriaisReservados reserva = new SugestaoReservaMateriaisReservados(idOrdem, nivelMaterial, grupoMaterial, subMaterial, itemMaterial, qtdeReservado);		
				materiaisReservados.add(reserva);	
			}
		} else {
			List<SugestaoReservaMateriaisReservados> tecidosReservados = new ArrayList<SugestaoReservaMateriaisReservados>();
			SugestaoReservaMateriaisReservados reserva = new SugestaoReservaMateriaisReservados(idOrdem, nivelMaterial, grupoMaterial, subMaterial, itemMaterial, qtdeReservado);
			tecidosReservados.add(reserva);
			mapMateriaisReservados.put(idOrdem, tecidosReservados);			
		}				 		
	}
	
	private void guardarQtdePrevistaAtendidaPorGradeTamanhos (int idOrdem, String cor, int ordemTamanho, String tamanho, int qtdePrevista, int qtdeAtendidaPorTecido, int qtdeAtendidaPorAviamento) {					
		Stream<SugestaoMaterialDetalhaGradeTamanhos> stream = listaGradeDetPrevistoAtendidoPorTamanho.stream().filter(p -> p.getIdOrdem() == idOrdem && p.getCor().equalsIgnoreCase(cor) && p.getTamanho().equalsIgnoreCase(tamanho));			
		List<SugestaoMaterialDetalhaGradeTamanhos> filtro = stream.collect(Collectors.toList());				
		SugestaoMaterialDetalhaGradeTamanhos itemGrade = null;
		
		if (filtro.size() > 0) {
			for (SugestaoMaterialDetalhaGradeTamanhos itemGradeFiltro : filtro) {
				itemGrade = itemGradeFiltro;
				if (qtdePrevista > 0) itemGrade.setQtdePrevista(itemGrade.getQtdePrevista() + qtdePrevista);
				if (qtdeAtendidaPorTecido > 0) itemGrade.setQtdeAtendidaPorTecido(itemGrade.getQtdeAtendidaPorTecido() + qtdeAtendidaPorTecido);
				if (qtdeAtendidaPorAviamento > 0) itemGrade.setQtdeAtendidaPorAviamento(itemGrade.getQtdeAtendidaPorAviamento() + qtdeAtendidaPorAviamento);
			}
		} else {
			itemGrade = new SugestaoMaterialDetalhaGradeTamanhos(idOrdem, cor, ordemTamanho, tamanho, qtdePrevista, qtdeAtendidaPorTecido, qtdeAtendidaPorAviamento);
			listaGradeDetPrevistoAtendidoPorTamanho.add(itemGrade);
		}		
	}

	private void guardarQtdePrevistaAtendidaPorSortimento (int idOrdem, String cor, int qtdePrevista, int qtdeAtendidaPorTecido, int qtdeAtendidaPorAviamento) {					
		Stream<SugestaoMaterialDetalhaSortimentos> stream = listaGradeDetPrevistoAtendidoPorSortimento.stream().filter(p -> p.getIdOrdem() == idOrdem && p.getCor().equalsIgnoreCase(cor));			
		List<SugestaoMaterialDetalhaSortimentos> filtro = stream.collect(Collectors.toList());				
		SugestaoMaterialDetalhaSortimentos itemSort = null;
		
		if (filtro.size() > 0) {
			for (SugestaoMaterialDetalhaSortimentos itemSortimFiltro : filtro) {
				itemSort = itemSortimFiltro;
				if (qtdePrevista > 0) itemSort.setQtdePrevista(itemSort.getQtdePrevista() + qtdePrevista);
				if (qtdeAtendidaPorTecido > 0) itemSort.setQtdeAtendidaPorTecido(itemSort.getQtdeAtendidaPorTecido() + qtdeAtendidaPorTecido);
				if (qtdeAtendidaPorAviamento > 0) itemSort.setQtdeAtendidaPorAviamento(itemSort.getQtdeAtendidaPorAviamento() + qtdeAtendidaPorAviamento);
			}
		} else {
			itemSort = new SugestaoMaterialDetalhaSortimentos(idOrdem, cor, qtdePrevista, qtdeAtendidaPorTecido, qtdeAtendidaPorAviamento);
			listaGradeDetPrevistoAtendidoPorSortimento.add(itemSort);
		}		
	}

	private void guardarQtdesPrevAtendTecidoPorSortimentoEGrade(int idOrdem, String cor, int ordemTamanho, String tamanho, int qtdeAtendida) {
		guardarQtdesPrevAtendPorSortimentoEGrade(idOrdem, cor, ordemTamanho, tamanho, 0, qtdeAtendida, 0);
	}

	private void guardarQtdesPrevAtendAviamentoPorSortimentoEGrade(int idOrdem, String cor, int ordemTamanho, String tamanho, int qtdeAtendida) {
		guardarQtdesPrevAtendPorSortimentoEGrade(idOrdem, cor, ordemTamanho, tamanho, 0, 0, qtdeAtendida);
	}
		
	private void guardarQtdesPrevAtendPorSortimentoEGrade(int idOrdem, String cor, int ordemTamanho, String tamanho, int qtdePrevista, int qtdeAtendidaPorTecido, int qtdeAtendidaPorAviamento) {
		guardarQtdePrevistaAtendidaPorSortimento(idOrdem, cor, qtdePrevista, qtdeAtendidaPorTecido, qtdeAtendidaPorAviamento);
		guardarQtdePrevistaAtendidaPorGradeTamanhos(idOrdem, cor, ordemTamanho, tamanho, qtdePrevista, qtdeAtendidaPorTecido, qtdeAtendidaPorAviamento);
	}
		
	private String chaveMapDadosPorProduto(String nivel, String grupo, String sub, String item, int alternativa, int roteiro) {
		return nivel + "." + grupo + "." + sub + "." + item + "." + alternativa + "." + roteiro;
	}	
	
	private int parsePeriodoToPeriodoMostruario(int periodo) {
		// Exemplo: se periodo for 5350 irá retornar 5300
		int valor = periodo / 100;
		return valor = valor * 100;
	}
	
	private int parsePeriodoToPeriodoMostruarioCompras() {		
		// O período de mostruário para compras é o 2200
		return 2200;
	}
}	