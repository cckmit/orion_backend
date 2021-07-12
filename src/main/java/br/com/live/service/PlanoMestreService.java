package br.com.live.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.bo.CalculoDistribuicaoPecas;
import br.com.live.bo.CopiarPlanoMestre;
import br.com.live.bo.GeracaoPlanoMestre;
import br.com.live.bo.GeracaoPreOrdens;
import br.com.live.bo.Multiplicador;
import br.com.live.custom.CapacidadeProducaoCustom;
import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.OcupacaoPlanoMestreCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.custom.PrevisaoVendasCustom;
import br.com.live.custom.ProcessoProdutoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.DemandaProdutoPlano;
import br.com.live.entity.EstoqueProduto;
import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestreOcupacaoArtigo;
import br.com.live.entity.PlanoMestreOcupacaoEstagio;
import br.com.live.entity.PlanoMestreParamProgItem;
import br.com.live.entity.PlanoMestreParametros;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.entity.ProcessoProdutoPlano;
import br.com.live.entity.ProdutoPlanoMestrePorCor;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.ArtigoCapacidadeProducao;
import br.com.live.model.ConsultaProgramadoReferencia;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.ConsultaPrevisaoVendasItemTam;
import br.com.live.model.EstagioCapacidadeProducao;
import br.com.live.model.GradeDistribuicaoGrupoItem;
import br.com.live.model.MarcacaoRisco;
import br.com.live.model.OcupacaoEstagioArtigo;
import br.com.live.model.OcupacaoPlanoMestre;
import br.com.live.model.ConsultaItensPlanoMestre;
import br.com.live.model.ConsultaItensTamPlanoMestre;
import br.com.live.model.PreOrdemProducaoIndicadores;
import br.com.live.model.ProdutoCompleto;
import br.com.live.model.ProgramacaoPlanoMestre;
import br.com.live.entity.ProdutoPlanoMestre;
import br.com.live.repository.PlanoMestreOcupacaoArtigoRepository;
import br.com.live.repository.PlanoMestreOcupacaoEstagioRepository;
import br.com.live.repository.PlanoMestreParamProgItemRepository;
import br.com.live.repository.PlanoMestreParametrosRepository;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.repository.ProdutoPlanoMestrePorCorRepository;
import br.com.live.repository.ProdutoPlanoMestreRepository;
import br.com.live.util.ParametrosPlanoMestre;
import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class PlanoMestreService {

	private final PlanoMestreRepository planoMestreRepository;
	private final ProdutoPlanoMestreRepository produtoPlanoMestreRepository;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
	private final DemandaProdutoCustom demandaProdutoCustom;
	private final ProcessoProdutoCustom processoProdutoCustom;
	private final PlanoMestreParametrosRepository planoMestreParametrosRepository;
	private final ProdutoPlanoMestrePorCorRepository produtoPlanoMestrePorCorRepository;
	private final ProdutoCustom produtoRepository;
	private final PlanoMestreParamProgItemRepository planoMestreParamProgItemRepository;
	private final OcupacaoPlanoMestreCustom ocupacaoPlanoMestreRepository;
	private final PlanoMestreCustom planoMestreCustom;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;
	private final PrevisaoVendasCustom previsaoVendasCustom;
	private final CapacidadeProducaoCustom capacidadeProducaoCustom;
	private final OcupacaoPlanoMestreCustom ocupacaoPlanoMestreCustom;
	private final PlanoMestreOcupacaoEstagioRepository planoMestreOcupacaoEstagioRepository;
	private final PlanoMestreOcupacaoArtigoRepository planoMestreOcupacaoArtigoRepository;
	private final OrdemProducaoService ordemProducaoService;

	public PlanoMestreService(PlanoMestreRepository planoMestreRepository,
			ProdutoPlanoMestreRepository produtoPlanoMestreRepository, EstoqueProdutoCustom estoqueProdutoCustom,
			DemandaProdutoCustom demandaProdutoCustom, ProcessoProdutoCustom processoProdutoCustom,
			PlanoMestreParametrosRepository planoMestreParametrosRepository,
			ProdutoPlanoMestrePorCorRepository produtoPlanoMestrePorCorRepository, ProdutoCustom produtoRepository,
			PlanoMestreParamProgItemRepository planoMestreParamProgItemRepository,
			OcupacaoPlanoMestreCustom ocupacaoPlanoMestreRepository, PlanoMestreCustom planoMestreCustom,
			PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository,
			PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository,
			PrevisaoVendasCustom previsaoVendasCustom, CapacidadeProducaoCustom capacidadeProducaoCustom,
			OcupacaoPlanoMestreCustom ocupacaoPlanoMestreCustom,
			PlanoMestreOcupacaoEstagioRepository planoMestreOcupacaoEstagioRepository,
			PlanoMestreOcupacaoArtigoRepository planoMestreOcupacaoArtigoRepository,
			OrdemProducaoService ordemProducaoService) {
		this.planoMestreRepository = planoMestreRepository;
		this.produtoPlanoMestreRepository = produtoPlanoMestreRepository;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.demandaProdutoCustom = demandaProdutoCustom;
		this.processoProdutoCustom = processoProdutoCustom;
		this.planoMestreParametrosRepository = planoMestreParametrosRepository;
		this.produtoPlanoMestrePorCorRepository = produtoPlanoMestrePorCorRepository;
		this.produtoRepository = produtoRepository;
		this.planoMestreParamProgItemRepository = planoMestreParamProgItemRepository;
		this.ocupacaoPlanoMestreRepository = ocupacaoPlanoMestreRepository;
		this.planoMestreCustom = planoMestreCustom;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;
		this.previsaoVendasCustom = previsaoVendasCustom;
		this.capacidadeProducaoCustom = capacidadeProducaoCustom;
		this.ocupacaoPlanoMestreCustom = ocupacaoPlanoMestreCustom;
		this.planoMestreOcupacaoEstagioRepository = planoMestreOcupacaoEstagioRepository;
		this.planoMestreOcupacaoArtigoRepository = planoMestreOcupacaoArtigoRepository;
		this.ordemProducaoService = ordemProducaoService;
	}

	public List<PlanoMestre> findAll() {
		return planoMestreRepository.findAll();
	}

	public List<ConsultaItensPlanoMestre> findProdutos(long idPlanoMestre) {
		return planoMestreCustom.findItensPorRefCorByIdPlanoMestre(idPlanoMestre);
	}

	public List<ConsultaItensTamPlanoMestre> findTamanhos(long idPlanoMestre, String grupo, String item) {
		return planoMestreCustom.findItensPorTamByIdPlanoMestreGrupoItem(idPlanoMestre, grupo, item);
	}

	public PlanoMestreParametros findParametros(long idPlanoMestre) {
		return planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);
	}

	public PlanoMestreParamProgItem findParamProgItem(long idPlanoMestre, String grupo, String item) {
		ProdutoPlanoMestrePorCor produtoPlanoMestrePorCor = produtoPlanoMestrePorCorRepository
				.findByCodigo(idPlanoMestre, grupo, item);
		return planoMestreParamProgItemRepository.findByIdItemPlanoMestre(produtoPlanoMestrePorCor.id);
	}

	public OcupacaoPlanoMestre findOcupacaoCalculadaByPlanoEstagio(long idPlanoMestre, int estagio) {
		return ocupacaoPlanoMestreRepository.findOcupacaoCalculadaByPlanoEstagio(idPlanoMestre, estagio);
	}

	public List<OcupacaoPlanoMestre> findOcupacaoCalculadaArtigoByPlanoEstagio(long idPlanoMestre, int estagio) {
		return ocupacaoPlanoMestreRepository.findOcupacaoCalculadaArtigoByPlanoEstagio(idPlanoMestre, estagio);
	}

	public List<ConsultaPreOrdemProducao> findPreOrdensByIdPlanoMestre(long idPlanoMestre) {
		return planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}

	public PlanoMestreParametros calcularOcupacaoPlano(long idPlanoMestre, int periodoInicio, int periodoFim) {

		double percentualPecas;
		double percentualMinutos;
		int qtdeFaltaSobraPecas;
		double qtdeFaltaSobraMinutos;

		int percentualPecasFormat;
		int percentualMinutosFormat;

		planoMestreOcupacaoEstagioRepository.deleteByIdPlanoMestre(idPlanoMestre);
		planoMestreOcupacaoArtigoRepository.deleteByIdPlanoMestre(idPlanoMestre);

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);
		parametros.periodoInicioOcupacao = periodoInicio;
		parametros.periodoFimOcupacao = periodoFim;
		planoMestreParametrosRepository.save(parametros);

		List<EstagioCapacidadeProducao> capacidades = capacidadeProducaoCustom
				.findEstagiosCapacidadeConfiguradaByPeriodo(periodoInicio, periodoFim);

		for (EstagioCapacidadeProducao capacidade : capacidades) {

			percentualPecas = 0.000;
			percentualMinutos = 0.000;
			qtdeFaltaSobraPecas = 0;
			qtdeFaltaSobraMinutos = 0.000;

			OcupacaoEstagioArtigo ocupacaoEstagioPlanoMestre = ocupacaoPlanoMestreCustom
					.findOcupacaoPlanoMestreByEstagio(idPlanoMestre, capacidade.estagio);
			OcupacaoEstagioArtigo ocupacaoEstagioProgramada = ocupacaoPlanoMestreCustom
					.findOcupacaoProgramadaByPeriodoEstagio(periodoInicio, periodoFim, capacidade.estagio);
			List<ArtigoCapacidadeProducao> artigosCapacidades = capacidadeProducaoCustom
					.findArtigosCapacidadeConfiguradaByPeriodoEstagio(periodoInicio, periodoFim, capacidade.estagio);

			if (capacidade.qtdePecas > 0)
				percentualPecas = (((double) ocupacaoEstagioPlanoMestre.qtdePecas
						+ (double) ocupacaoEstagioProgramada.qtdePecas) / (double) capacidade.qtdePecas * 100);

			if (capacidade.qtdeMinutos > 0)
				percentualMinutos = ((ocupacaoEstagioPlanoMestre.qtdeMinutos + ocupacaoEstagioProgramada.qtdeMinutos)
						/ capacidade.qtdeMinutos * 100);

			percentualPecasFormat = (int) percentualPecas;
			percentualMinutosFormat = (int) percentualMinutos;

			qtdeFaltaSobraPecas = capacidade.qtdePecas
					- (ocupacaoEstagioPlanoMestre.qtdePecas + ocupacaoEstagioProgramada.qtdePecas);
			qtdeFaltaSobraMinutos = capacidade.qtdeMinutos
					- (ocupacaoEstagioPlanoMestre.qtdeMinutos + ocupacaoEstagioProgramada.qtdeMinutos);

			PlanoMestreOcupacaoEstagio planoMestreOcupacaoEstagio = new PlanoMestreOcupacaoEstagio(idPlanoMestre,
					capacidade.estagio, capacidade.qtdePecas, ocupacaoEstagioPlanoMestre.qtdePecas,
					ocupacaoEstagioProgramada.qtdePecas, percentualPecasFormat, qtdeFaltaSobraPecas,
					capacidade.qtdeMinutos, ocupacaoEstagioPlanoMestre.qtdeMinutos,
					ocupacaoEstagioProgramada.qtdeMinutos, percentualMinutosFormat, qtdeFaltaSobraMinutos);

			planoMestreOcupacaoEstagioRepository.save(planoMestreOcupacaoEstagio);

			for (ArtigoCapacidadeProducao artigoCapacidade : artigosCapacidades) {

				percentualPecas = 0.000;
				percentualMinutos = 0.000;
				qtdeFaltaSobraPecas = 0;
				qtdeFaltaSobraMinutos = 0.000;

				OcupacaoEstagioArtigo ocupacaoArtigoPlanoMestre = ocupacaoPlanoMestreCustom
						.findOcupacaoPlanoMestreArtigoByEstagioArtigo(idPlanoMestre, capacidade.estagio,
								artigoCapacidade.artigo);
				OcupacaoEstagioArtigo ocupacaoArtigoProgramado = ocupacaoPlanoMestreCustom
						.findOcupacaoProgramadaArtigoByPeriodoEstagioArtigo(periodoInicio, periodoFim,
								capacidade.estagio, artigoCapacidade.artigo);

				if (artigoCapacidade.qtdePecas > 0)
					percentualPecas = (((double) ocupacaoArtigoPlanoMestre.qtdePecas
							+ (double) ocupacaoArtigoProgramado.qtdePecas) / (double) artigoCapacidade.qtdePecas * 100);

				if (artigoCapacidade.qtdeMinutos > 0)
					percentualMinutos = ((ocupacaoArtigoPlanoMestre.qtdeMinutos + ocupacaoArtigoProgramado.qtdeMinutos)
							/ artigoCapacidade.qtdeMinutos * 100);

				percentualPecasFormat = (int) percentualPecas;
				percentualMinutosFormat = (int) percentualMinutos;

				qtdeFaltaSobraPecas = artigoCapacidade.qtdePecas
						- (ocupacaoArtigoPlanoMestre.qtdePecas + ocupacaoArtigoProgramado.qtdePecas);
				qtdeFaltaSobraMinutos = artigoCapacidade.qtdeMinutos
						- (ocupacaoArtigoPlanoMestre.qtdeMinutos + ocupacaoArtigoProgramado.qtdeMinutos);

				PlanoMestreOcupacaoArtigo planoMestreOcupacaoArtigo = new PlanoMestreOcupacaoArtigo(idPlanoMestre,
						capacidade.estagio, artigoCapacidade.artigo, artigoCapacidade.qtdePecas,
						ocupacaoArtigoPlanoMestre.qtdePecas, ocupacaoArtigoProgramado.qtdePecas, percentualPecasFormat,
						qtdeFaltaSobraPecas, artigoCapacidade.qtdeMinutos, ocupacaoArtigoPlanoMestre.qtdeMinutos,
						ocupacaoArtigoProgramado.qtdeMinutos, percentualMinutosFormat, qtdeFaltaSobraMinutos);

				planoMestreOcupacaoArtigoRepository.save(planoMestreOcupacaoArtigo);
			}
		}

		return parametros;
	}

	public PreOrdemProducaoIndicadores findIndicadoresByPreOrdens(long idPlanoMestre, List<Integer> preOrdens) {
		List<PlanoMestrePreOrdem> preOrdensComOpsGeradas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndOrdemGerada(idPlanoMestre);
				
		if (preOrdensComOpsGeradas.size() > 0) {
			preOrdens.clear();			
			for (PlanoMestrePreOrdem preOrdemComOp : preOrdensComOpsGeradas) {
				preOrdens.add((int) preOrdemComOp.id);	
			}
		}
		
		long idMaiorOP = planoMestreCustom.findIDMaiorOrdemByPreOrdens(preOrdens);
		long idMenorOP = planoMestreCustom.findIDMenorOrdemByPreOrdens(preOrdens);

		PlanoMestrePreOrdem preOrdemMaior = planoMestrePreOrdemRepository.findById(idMaiorOP);
		PlanoMestrePreOrdem preOrdemMenor = planoMestrePreOrdemRepository.findById(idMenorOP);

		PreOrdemProducaoIndicadores indicadores = new PreOrdemProducaoIndicadores();

		indicadores.qtdePecasProgramadas = planoMestreCustom.findQtdePecasProgByPreOrdens(preOrdens);
		indicadores.qtdeMinutosProgramados = planoMestreCustom.findQtdeMinutosProgByPreOrdens(preOrdens);
		indicadores.qtdeReferencias = planoMestreCustom.findQtdeReferenciasProgByPreOrdens(preOrdens);
		indicadores.qtdeSKUs = planoMestreCustom.findQtdeSKUsProgByPreOrdens(preOrdens);
		indicadores.qtdeLoteMedio = planoMestreCustom.findQtdeLoteMedioProgByPreOrdens(preOrdens);
		if (idMaiorOP > 0)
			indicadores.detMaiorOrdem = "Id: " + preOrdemMaior.id + " - Ref: " + preOrdemMaior.grupo + " - Qtde Peças: "
					+ preOrdemMaior.quantidade;
		if (idMenorOP > 0)
			indicadores.detMenorOrdem = "Id: " + preOrdemMenor.id + " - Ref: " + preOrdemMenor.grupo + " - Qtde Peças: "
					+ preOrdemMenor.quantidade;

		return indicadores;
	}

	public void salvarSituacao(long idPlanoMestre, int newSituacao) {
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
		planoMestre.situacao = newSituacao;
		planoMestreRepository.save(planoMestre);
	}

	public void salvarItem(ConsultaItensPlanoMestre itemAlterado) {
		
		ProdutoPlanoMestrePorCor produtoCor = produtoPlanoMestrePorCorRepository
				.findByCodigo(itemAlterado.idPlanoMestre, itemAlterado.grupo, itemAlterado.item);
		
		if (produtoCor.qtdeProgramada != itemAlterado.qtdeProgramada) {

			PlanoMestreParametros parametros = planoMestreParametrosRepository
					.findByIdPlanoMestre(itemAlterado.idPlanoMestre);

			produtoCor.qtdeProgramada = itemAlterado.qtdeProgramada;
			produtoPlanoMestrePorCorRepository.save(produtoCor);

			List<ProdutoPlanoMestre> produtos = null;

			if (parametros.tipoDistribuicao == 1)
				produtos = calcularGradePadrao(produtoCor);

			if (parametros.tipoDistribuicao == 2)
				produtos = calcularGradeVenda(produtoCor);

			if (parametros.tipoDistribuicao == 3)
				produtos = calcularGradeNegativa(produtoCor); 

			if (parametros.tipoDistribuicao == 4)
				produtos = calcularGradePrevisao(produtoCor, parametros.previsoes);

			produtoPlanoMestreRepository.saveAll(produtos);

			aplicarMultiplicador(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);
		}		
	}
	
	public void salvarItens(List<ConsultaItensPlanoMestre> itensAlterados) {
		for (ConsultaItensPlanoMestre itemAlterado : itensAlterados) {
			salvarItem(itemAlterado);			
		}
	}

	public void salvarParametrosProgramacaoItem(long idPlanoMestre, String grupo, String item, int alternativa,
			int roteiro, int periodo, int multiplicador) {
		ProdutoPlanoMestrePorCor produtoPlanoMestrePorCor = produtoPlanoMestrePorCorRepository
				.findByCodigo(idPlanoMestre, grupo, item);
		PlanoMestreParamProgItem planoMestreParamProgItem = planoMestreParamProgItemRepository
				.findByIdItemPlanoMestre(produtoPlanoMestrePorCor.id);

		planoMestreParamProgItem.alternativa = alternativa;
		planoMestreParamProgItem.roteiro = roteiro;
		planoMestreParamProgItem.periodo = periodo;
		planoMestreParamProgItem.multiplicador = multiplicador;
		planoMestreParamProgItemRepository.save(planoMestreParamProgItem);

		aplicarMultiplicadorItem(idPlanoMestre, multiplicador, produtoPlanoMestrePorCor);
	}

	public void salvarGrade(long idPlanoMestre, String grupo, String item,
			List<ConsultaItensTamPlanoMestre> gradeAlterada) {

		for (ConsultaItensTamPlanoMestre itemAlterado : gradeAlterada) {
			ProdutoPlanoMestre produto = produtoPlanoMestreRepository.findByIdPlanoCodGrupoSubCor(
					itemAlterado.idPlanoMestre, itemAlterado.grupo, itemAlterado.sub, itemAlterado.item);

			if (produto == null) {
				produto = new ProdutoPlanoMestre();
				produto.idPlanoMestre = idPlanoMestre;
				produto.nivel = "1";
				produto.grupo = itemAlterado.grupo;
				produto.sub = itemAlterado.sub;
				produto.item = itemAlterado.item;
			}

			produto.qtdeProgramada = itemAlterado.qtdeProgramada;
			produtoPlanoMestreRepository.save(produto);
		}

		aplicarMultiplicador(idPlanoMestre, grupo, item);
	}

	public List<PlanoMestre> copiar(long idPlanoMestre) {

		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
		PlanoMestreParametros planoMestreParametros = planoMestreParametrosRepository
				.findByIdPlanoMestre(idPlanoMestre);
		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository.findByIdPlanoMestre(idPlanoMestre);
		List<ProdutoPlanoMestrePorCor> produtosPorCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		PlanoMestre planoMestreCopia = CopiarPlanoMestre.getCopiaPlanoMestre(planoMestre);
		planoMestreRepository.save(planoMestreCopia);

		PlanoMestreParametros planoMestreParametrosCopia = CopiarPlanoMestre
				.getCopiaPlanoMestreParametros(planoMestreParametros, planoMestreCopia.id);
		planoMestreParametrosRepository.save(planoMestreParametrosCopia);

		for (ProdutoPlanoMestre produto : produtos) {
			ProdutoPlanoMestre produtoCopia = CopiarPlanoMestre.getCopiaProdutoPlanoMestre(produto,
					planoMestreCopia.id);
			produtoPlanoMestreRepository.save(produtoCopia);
		}

		for (ProdutoPlanoMestrePorCor produtoCor : produtosPorCor) {
			ProdutoPlanoMestrePorCor produtoCorCopia = CopiarPlanoMestre.getCopiaProdutoPlanoMestrePorCor(produtoCor,
					planoMestreCopia.id);
			produtoPlanoMestrePorCorRepository.save(produtoCorCopia);

			PlanoMestreParamProgItem parametroProgramacaoItem = planoMestreParamProgItemRepository
					.findByIdItemPlanoMestre(produtoCor.id);
			PlanoMestreParamProgItem parametroProgramacaoItemCopia = CopiarPlanoMestre.getCopiaPlanoMestreParamProgItem(
					parametroProgramacaoItem, produtoCorCopia.idPlanoMestre, produtoCorCopia.id);
			planoMestreParamProgItemRepository.save(parametroProgramacaoItemCopia);
		}

		return findAll();
	}

	public List<PlanoMestre> gerar(ParametrosPlanoMestre parametros) {

		long idPlanoMestre = 0;

		System.out.println("Inicio geração do plano mestre");

		List<EstoqueProduto> estoques = estoqueProdutoCustom.findByParameters(parametros);
		List<DemandaProdutoPlano> demandas = demandaProdutoCustom.findByParameters(parametros);
		List<ProcessoProdutoPlano> processos = processoProdutoCustom.findByParameters(parametros);
		List<ProdutoCompleto> produtos = planoMestreCustom.findProdutosByParameters(parametros);

		System.out.println("Criando o plano mestre");
		GeracaoPlanoMestre geracao = new GeracaoPlanoMestre(parametros, estoques, demandas, processos, produtos);

		System.out.println("Gravando o plano mestre");
		idPlanoMestre = gravar(geracao);

		System.out.println("Equalizando as quantidades");
		equalizarDistribuicao(idPlanoMestre, geracao.getParametrosPlanoMestre().tipoDistribuicao);

		System.out.println("Equalizando as quantidades mínimas das referencias");
		equalizarQtdeMinimaReferencia(idPlanoMestre, parametros.tipoDistribuicao, parametros.qtdeMinimaReferencia);

		System.out.println("Fim da geração do plano mestre");
		return findAll();
	}

	private long gravar(GeracaoPlanoMestre geracao) {

		PlanoMestre planoMestre = geracao.getCapaPlanoMestre();
		List<ProdutoPlanoMestre> produtos = geracao.getProdutosPlanoMestre();

		planoMestreRepository.save(planoMestre);

		PlanoMestreParametros planoMestreParametros = geracao.getParametrosPlanoMestre();
		planoMestreParametros.idPlanoMestre = planoMestre.id;
		planoMestreParametrosRepository.save(planoMestreParametros);

		for (ProdutoPlanoMestre produtoPlanoMestre : produtos) {
			produtoPlanoMestre.idPlanoMestre = planoMestre.id;			
			produtoPlanoMestre.qtdePrevisao = previsaoVendasCustom.findQtdePrevisaoByIdPrevisaoVendasGrupoItem(planoMestreParametros.previsoes, produtoPlanoMestre.grupo, produtoPlanoMestre.item);			
			produtoPlanoMestreRepository.save(produtoPlanoMestre);
		}

		List<ProdutoPlanoMestrePorCor> produtosPorCor = geracao.getProdutosPorCorPlanoMestre(produtos);

		for (ProdutoPlanoMestrePorCor produtoPlanoMestrePorCor : produtosPorCor) {
			produtoPlanoMestrePorCor.idPlanoMestre = planoMestre.id;
			produtoPlanoMestrePorCorRepository.save(produtoPlanoMestrePorCor);
			AlternativaRoteiroPadrao alternativaRoteiroPadrao = produtoRepository.findAlternativaRoteiroPadraoByCodigo(
					produtoPlanoMestrePorCor.grupo, produtoPlanoMestrePorCor.item);
			PlanoMestreParamProgItem parametroProgramacaoItem = geracao.getParametrosProgramacaoItem(planoMestre.id,
					produtoPlanoMestrePorCor.id, alternativaRoteiroPadrao);
			planoMestreParamProgItemRepository.save(parametroProgramacaoItem);
		}

		return (planoMestre.id);
	}

	private void equalizarDistribuicao(long idPlanoMestre, int tipoDistribuicao) {
		if (tipoDistribuicao == 1) // Grade Padrão
			calcularGradePadraoParaPlano(idPlanoMestre);

		if (tipoDistribuicao == 2) // Grade Venda
			calcularGradeVendaParaPlano(idPlanoMestre);

		if (tipoDistribuicao == 3) // Grade Negativa
			calcularGradeNegativaParaPlano(idPlanoMestre);

		if (tipoDistribuicao == 4) // Previsão de Vendas
			calcularGradePrevisaoParaPlano(idPlanoMestre);
	}

	private void calcularGradePadraoParaPlano(long idPlanoMestre) {

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			List<ProdutoPlanoMestre> produtos = calcularGradePadrao(produtoCor);
			produtoPlanoMestreRepository.saveAll(produtos);
			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradePadrao(ProdutoPlanoMestrePorCor produtoCor) {
		PlanoMestreParamProgItem parametrosProgramacao = planoMestreParamProgItemRepository
				.findByIdItemPlanoMestre(produtoCor.id);

		int riscoPadrao = produtoRepository.findRiscoPadraoByCodigo(produtoCor.grupo);
		int seqRisco = produtoRepository.findSequenciaPrincipalRisco(produtoCor.grupo, produtoCor.item,
				parametrosProgramacao.alternativa);

		List<MarcacaoRisco> marcacoesRisco = produtoRepository.findMarcacoesRisco(produtoCor.grupo, riscoPadrao,
				seqRisco, parametrosProgramacao.alternativa);

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);

		return CalculoDistribuicaoPecas.distribuirPelaGradePadrao(produtoCor.qtdeProgramada, produtos, marcacoesRisco);
	}
	
	private void calcularGradeVendaParaPlano(long idPlanoMestre) {

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			List<ProdutoPlanoMestre> produtos = calcularGradeVenda(produtoCor);
			produtoPlanoMestreRepository.saveAll(produtos);

			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradeVenda(ProdutoPlanoMestrePorCor produtoCor) {

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);

		return CalculoDistribuicaoPecas.distribuirPelaGradeVenda(produtoCor.qtdeProgramada, produtos);
	}

	private void calcularGradeNegativaParaPlano(long idPlanoMestre) {

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradeNegativa(ProdutoPlanoMestrePorCor produtoCor) {

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);

		return CalculoDistribuicaoPecas.distribuirPelaQtdeSugerida(produtoCor.qtdeProgramada, produtos);
	}

	private void calcularGradePrevisaoParaPlano(long idPlanoMestre) {
		
		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			List<ProdutoPlanoMestre> produtos = calcularGradePrevisao(produtoCor, parametros.previsoes);
			produtoPlanoMestreRepository.saveAll(produtos);

			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradePrevisao(ProdutoPlanoMestrePorCor produtoCor, String idsPrevisoes) {
		
		List<ConsultaPrevisaoVendasItemTam> previsaoTamanhos = previsaoVendasCustom.findPrevisaoVendasItemTamByIdsPrevisaoVendasGrupoItem(idsPrevisoes, produtoCor.grupo, produtoCor.item);

		int qtdePrevisao = 0;
		
		for (ConsultaPrevisaoVendasItemTam previsao : previsaoTamanhos) {
			qtdePrevisao += previsao.qtdePrevisao;
		}
		
		if (qtdePrevisao == 0) return calcularGradePadrao(produtoCor);
		
		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);
		
		return CalculoDistribuicaoPecas.distribuirPelaGradePrevisao(produtoCor.qtdeProgramada, produtos, previsaoTamanhos);
	}	
	
	private void aplicarMultiplicadorItem(long idPlanoMestre, int multiplicador, ProdutoPlanoMestrePorCor produtoCor) {

		int qtdeProgramada = 0;

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository.findByIdPlanoCodGrupoCor(idPlanoMestre,
				produtoCor.grupo, produtoCor.item);

		for (ProdutoPlanoMestre produto : produtos) {
			produto.qtdeEqualizadoSugestao = Multiplicador.ajustarQuantidade(multiplicador, produto.qtdeProgramada);
			produto.qtdeDiferencaSugestao = produto.qtdeSugestao - produto.qtdeEqualizadoSugestao;
			produto.qtdeProgramada = produto.qtdeEqualizadoSugestao;
			produtoPlanoMestreRepository.save(produto);
			qtdeProgramada += produto.qtdeProgramada;			
		}

		produtoCor.qtdeEqualizadoSugestao = qtdeProgramada;
		produtoCor.qtdeDiferencaSugestao = produtoCor.qtdeSugestao - produtoCor.qtdeEqualizadoSugestao;
		produtoCor.qtdeProgramada = produtoCor.qtdeEqualizadoSugestao;

		produtoPlanoMestrePorCorRepository.save(produtoCor);
	}

	public void aplicarMultiplicador(long idPlanoMestre, String grupo, String item) {

		ProdutoPlanoMestrePorCor produtoPorCor = produtoPlanoMestrePorCorRepository.findByCodigo(idPlanoMestre, grupo,
				item);

		PlanoMestreParamProgItem parametrosProgramacao = planoMestreParamProgItemRepository
				.findByIdItemPlanoMestre(produtoPorCor.id);

		int multiplicador = parametrosProgramacao.multiplicador;

		if (multiplicador <= 0)
			multiplicador = 1;

		aplicarMultiplicadorItem(idPlanoMestre, multiplicador, produtoPorCor);
	}

	public void equalizarQtdeMinimaReferencia(long idPlanoMestre, int tipoDistribuicao, int qtdeMinima) {
		
		if (qtdeMinima > 0) {

			double proporcao = 0;
			int qtdeProgramar = 0;
			
			List<GradeDistribuicaoGrupoItem> gradeCorProduto = null;			
			List<ConsultaProgramadoReferencia> programados = planoMestreCustom.findProgramacaoPorReferenciaByIdPlanoMestre(idPlanoMestre);
			
			for (ConsultaProgramadoReferencia programado : programados) {
				
				if (programado.quantidade <= 0) continue;
				
				if (programado.quantidade < qtdeMinima) {

					if ((tipoDistribuicao == 1)||(tipoDistribuicao == 4)) gradeCorProduto = planoMestreCustom.findDistribuicaoPadraoCorByIdPlanoMestreGrupo(idPlanoMestre, programado.grupo);  
					if (tipoDistribuicao == 2) gradeCorProduto = planoMestreCustom.findDemandaByIdPlanoMestreGrupo(idPlanoMestre, programado.grupo);				
					if (tipoDistribuicao == 3) gradeCorProduto = planoMestreCustom.findSugestaoByIdPlanoMestreGrupo(idPlanoMestre, programado.grupo);
					
					for (GradeDistribuicaoGrupoItem grade : gradeCorProduto) {

						qtdeProgramar = 0;
						
						proporcao = ((double) grade.qtdeItem / (double) grade.qtdeGrupo);
						qtdeProgramar = (int) Math.ceil(((double) qtdeMinima * proporcao));
						
						ConsultaItensPlanoMestre item = planoMestreCustom.findItensPorRefCorByIdPlanoMestreGrupoItem(idPlanoMestre, grade.grupo, grade.item);
						item.qtdeProgramada = qtdeProgramar;						
						
						//System.out.println("ref: " + grade.grupo + "." + grade.item + " - qtde programar: " + item.qtdeProgramada);
						
						salvarItem(item);						
					}
				}
			}			
		}
	}

	public void gerarPreOrdens(ParametrosPlanoMestre parametros) {

		PlanoMestre planoMestre = planoMestreRepository.findById(parametros.idPlanoMestre);
		
		// Se tiver ordens geradas, não recria as pré-ordens
		
		if (planoMestre.situacao < 2) {		
			// Atualiza os parâmetros do plano mestre.
			PlanoMestreParametros planoMestreParametros = planoMestreParametrosRepository
					.findByIdPlanoMestre(parametros.idPlanoMestre);
			planoMestreParametros.agrupaOpPorRefer = parametros.agrupaOpPorRefer;
			planoMestreParametros.qtdeMaximaOP = parametros.qtdeMaximaOP;
			planoMestreParametros.qtdeMinimaOP = parametros.qtdeMinimaOP;
			planoMestreParametros.periodoOP = parametros.periodoOP;
			planoMestreParametros.depositoOP = parametros.depositoOP;
			planoMestreParametros.observacaoOP = parametros.observacaoOP;
			planoMestreParametrosRepository.save(planoMestreParametros);
	
			// Elimina as pré-ordens geradas anteriormente para o plano mestre.
			planoMestrePreOrdemItemRepository.deleteByIdPlanoMestre(parametros.idPlanoMestre);
			planoMestrePreOrdemItemRepository.flush();
			planoMestrePreOrdemRepository.deleteByIdPlanoMestre(parametros.idPlanoMestre);			
			planoMestrePreOrdemRepository.flush();
	
			// Calcula e gera as pré-ordens
			List<ProgramacaoPlanoMestre> programacao = planoMestreCustom
					.findProgramacaoByIdPlanoMestre(parametros.idPlanoMestre);
	
			GeracaoPreOrdens geracaoPreOrdens = new GeracaoPreOrdens(parametros.idPlanoMestre, parametros.agrupaOpPorRefer,
					parametros.qtdeMaximaOP, parametros.qtdeMinimaOP, parametros.periodoOP, parametros.depositoOP,
					parametros.observacaoOP, programacao);
	
			Map<Integer, PlanoMestrePreOrdem> mapPreOrdens = geracaoPreOrdens.getMapPreOrdens();
			List<PlanoMestrePreOrdemItem> listPreOrdemItens;
	
			PlanoMestrePreOrdem preOrdem;
			Map<Long, StatusGravacao> mapPreOrdensComErro = new HashMap<Long, StatusGravacao> ();
			
			for (Integer idMap : mapPreOrdens.keySet()) {
	
				preOrdem = mapPreOrdens.get(idMap);
				preOrdem.id = planoMestreCustom.findNextIdPreOrdem();
				preOrdem = planoMestrePreOrdemRepository.saveAndFlush(preOrdem);				
				listPreOrdemItens = geracaoPreOrdens.getListPreOrdemItens(idMap);

				for (PlanoMestrePreOrdemItem preOrdemItem : listPreOrdemItens) {
					preOrdemItem.id = planoMestreCustom.findNextIdPreOrdemItem();
					preOrdemItem.idOrdem = preOrdem.id;
					planoMestrePreOrdemItemRepository.saveAndFlush(preOrdemItem);					
				}
				
				ordemProducaoService.validarDadosOrdem(preOrdem, mapPreOrdensComErro);
				ordemProducaoService.validarDadosItem(preOrdem, listPreOrdemItens, mapPreOrdensComErro);
			}
			
			ordemProducaoService.atualizarErrosPreOrdens(mapPreOrdensComErro);
		}
	}

	public List<PlanoMestre> delete(long idPlanoMestre) {

		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
		
		if (planoMestre.situacao == 0) {  
			planoMestreOcupacaoEstagioRepository.deleteByIdPlanoMestre(idPlanoMestre);
			planoMestreOcupacaoArtigoRepository.deleteByIdPlanoMestre(idPlanoMestre);
			planoMestrePreOrdemItemRepository.deleteByIdPlanoMestre(idPlanoMestre);
			planoMestrePreOrdemRepository.deleteByIdPlanoMestre(idPlanoMestre);
			planoMestreParamProgItemRepository.deleteByIdPlanoMestre(idPlanoMestre);
			produtoPlanoMestrePorCorRepository.deleteByIdPlanoMestre(idPlanoMestre);
			produtoPlanoMestreRepository.deleteByIdPlanoMestre(idPlanoMestre);
			planoMestreParametrosRepository.deleteById(idPlanoMestre);
			planoMestreRepository.deleteById(idPlanoMestre);
		}
		
		return findAll();
	}

};