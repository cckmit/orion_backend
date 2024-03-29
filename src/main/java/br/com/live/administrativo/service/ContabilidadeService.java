package br.com.live.administrativo.service;

import java.util.List;

import javax.transaction.Transactional;

import br.com.live.administrativo.entity.ImportacaoParametroCustoEntity;
import br.com.live.administrativo.repository.ImportacaoParametroCustoRepository;
import br.com.live.produto.custom.ProdutoCustom;
import org.springframework.stereotype.Service;

import br.com.live.administrativo.custom.ContabilidadeCustom;
import br.com.live.administrativo.entity.LancamentoContabeisImport;
import br.com.live.administrativo.model.ConsultaLanctoContabeis;
import br.com.live.administrativo.model.RetornoLancamentoCont;
import br.com.live.administrativo.repository.LanctoContabilImportacaoRepository;
import br.com.live.util.FormataData;


@Transactional
@Service
public class ContabilidadeService {
	
	private final LanctoContabilImportacaoRepository lanctoContabilImportacaoRepository;
	private final ContabilidadeCustom contabilidadeCustom;
	private final ImportacaoParametroCustoRepository importacaoParametroCustoRepository;
	private final ProdutoCustom produtoCustom;

	public ContabilidadeService(LanctoContabilImportacaoRepository lanctoContabilImportacaoRepository, ContabilidadeCustom contabilidadeCustom,
								ImportacaoParametroCustoRepository importacaoParametroCustoRepository, ProdutoCustom produtoCustom) {
		this.lanctoContabilImportacaoRepository = lanctoContabilImportacaoRepository;
		this.contabilidadeCustom = contabilidadeCustom;
		this.importacaoParametroCustoRepository = importacaoParametroCustoRepository;
		this.produtoCustom = produtoCustom;
	}
	
	public String validarEmpresa(int empresa){
		
		int extistsEmpresa = contabilidadeCustom.findEmpresa(empresa);
		String mensagem = "";
		
		if(extistsEmpresa == 0) {
			mensagem = "Empresa não Cadastrada";						
		}
		return mensagem;
	}
	
	public String validarCentroCusto(int centroCusto){
		
		int extistsCCusto = contabilidadeCustom.findCentroCusto(centroCusto);
		String mensagem = "";
		
		if(extistsCCusto == 0) {
			mensagem = "Centro de Custo não Cadastrado";						
		}
		return mensagem;
	}
	
	public String validarCentroCustoInativo(int centroCusto){
		
		int extistsCCustoIn = contabilidadeCustom.findCentroCustoInativo(centroCusto);
		String mensagem = "";
		
		if(extistsCCustoIn == 0) {
			mensagem = "Centro de Custo Inativo";						
		}
		return mensagem;
	}
	
	public String validarOrigem(int origem){
		
		int extistsOrigem = contabilidadeCustom.findOrigem(origem);
		String mensagem = "";
		
		if(extistsOrigem == 0) {
			mensagem = "Origem não Cadastrada";						
		}
		return mensagem;
	}
	
	public String validarContaContabil(int contaReduzida){
		
		int extistsContaReduzida = contabilidadeCustom.findContaContabil(contaReduzida);
		String mensagem = "";
		
		if(extistsContaReduzida == 0) {
			mensagem = "Conta Contábil não Cadastrada";						
		}
		return mensagem;
	}
	
	public String validarHistoricoContabil(int historicoContabil){
		
		int extistsHistContabil = contabilidadeCustom.findHistoricoContabil(historicoContabil);
		String mensagem = "";
		
		if(extistsHistContabil == 0) {
			mensagem = "Histórico Contábil não Cadastrado";						
		}
		return mensagem;
	}
	
	public String validarExigeSubConta(int contaRezuzida, int centroCusto){
		
		int exigeSubConta =  contabilidadeCustom.findSubConta(contaRezuzida);
		String mensagem = "";
		
		if(exigeSubConta == 1 && centroCusto == 0) {
			mensagem = "Conta Reduzida Exige Centro de Custo Informado";						
		}
		return mensagem;
	}
	
	public String validarCentroCustoByEmpresa(int centroCusto, int filial){
		
		String mensagem = "";
		
		if(centroCusto != 0) {
			int centroCustoByEmpresa =  contabilidadeCustom.findCentroCustoByEmpresa(centroCusto, filial);
			mensagem = "";
			
			if(centroCustoByEmpresa == 0) {
				mensagem = " Centro de Custo Não Pertence a Filial Informada ";						
			}						
		}
		return mensagem;
	}
	
	public int gerarNumLote(int empresa, int exercicio, int origem, String dataLancto, int situacao) {
		
		int numLote = 0;

		int loteExistente = contabilidadeCustom.findLoteExistenteParaParametros(empresa, exercicio, origem, dataLancto);
		
		if(loteExistente == 0) {
			numLote = contabilidadeCustom.novoLote(empresa, exercicio, origem, dataLancto);	
		} else {
			numLote = loteExistente;
		}		
		return numLote;
		
	}
	
	public RetornoLancamentoCont importarLancamentosContabeis(List<ConsultaLanctoContabeis> listLancto, String usuario, String datainsercao) {
		// Deletando tudo na Tabela orion_cnt_010
		lanctoContabilImportacaoRepository.deleteByUsuario(usuario);
		RetornoLancamentoCont listRetorno = null;
		
		LancamentoContabeisImport lanctoContabil = null;
		int sequencia = 1;
		int status = 1;
        
		for (ConsultaLanctoContabeis dadosLancto : listLancto) {
			int id = contabilidadeCustom.findNextId();
			String[] periodoConcat = dadosLancto.dataLancto.split("[/]");
	        String periodo = periodoConcat[1];
			String criticasByLancto = "";
			String origem = validarOrigem(dadosLancto.origem);
			String contaReduzida = validarContaContabil(dadosLancto.contaReduzida);
			String empresa = validarEmpresa(dadosLancto.filialLancto);
			String centroCusto = validarCentroCusto(dadosLancto.centroCusto);
			String centroCustoInativo = validarCentroCustoInativo(dadosLancto.centroCusto);
			String histContabil = validarHistoricoContabil(dadosLancto.histContabil);
			String exigeSubConta = validarExigeSubConta(dadosLancto.contaReduzida, dadosLancto.centroCusto);
			String centroCustoByEmpresa = validarCentroCustoByEmpresa(dadosLancto.centroCusto, dadosLancto.filialLancto);
			criticasByLancto = incrementarCriticas(empresa, origem, contaReduzida, centroCusto, histContabil, centroCustoInativo, exigeSubConta, centroCustoByEmpresa);
			if(criticasByLancto.length() <= 7) {
				status = 0;
			}
			try {
				lanctoContabil = new LancamentoContabeisImport(id, dadosLancto.filialLancto, dadosLancto.exercicio, dadosLancto.origem, dadosLancto.contaReduzida,
						dadosLancto.debitoCredito, dadosLancto.valorLancto, dadosLancto.centroCusto, dadosLancto.histContabil, FormataData.parseStringToDate(dadosLancto.dataLancto), dadosLancto.complHistor1,
						FormataData.parseStringToDate(datainsercao), usuario, 0, 0, sequencia, Integer.parseInt(periodo), status, criticasByLancto.strip());
				lanctoContabilImportacaoRepository.saveAndFlush(lanctoContabil);
				sequencia += 1;				
			} catch (Exception e) {
				System.out.println(e);
			}
			status = 1;
		};
		
		listRetorno = new RetornoLancamentoCont(contabilidadeCustom.findStatusByLancto(usuario), contabilidadeCustom.findAllLanctoContabeis(usuario));
		return listRetorno;
	}
	
	public String incrementarCriticas(String empresa, String origem, String contaReduzida, String centroCusto, String histContabil, String centroCustoInativo, String exigeSubConta,
			String centroCustoByEmpresa) {
		
		String criticas = "";
		
		if(empresa != null) {
			criticas = criticas.strip() + "\n" + empresa + "\n";
		} if(origem != null) {
			criticas = criticas.strip() + "\n" + origem + "\n";	
		} if(contaReduzida != null) {
			criticas = criticas.strip() + "\n" + contaReduzida + "\n";
		} if(centroCusto != null) {
			criticas = criticas.strip() + "\n" + centroCusto + "\n";
		} if(histContabil != null) {
			criticas = criticas.strip() + "\n" + histContabil + "\n";
		} if(centroCustoInativo != null) {
			criticas = criticas.strip() + "\n" + centroCustoInativo + "\n";
		} if(exigeSubConta != null) {
			criticas = criticas.strip() + "\n" + exigeSubConta + "\n";
		} if(centroCustoByEmpresa != null) {
			criticas = criticas.strip() + "\n" + centroCustoByEmpresa + "\n";
		}
		return criticas;
	}
	
	public int salvarSystextil(String usuario) {
		
		int mensagem = 0;
		
		List<ConsultaLanctoContabeis> listEmpresa =  contabilidadeCustom.findEmpresaExercicioPorUsuario(usuario);
		
		String programa = "Orion";
		
		try {
			for (ConsultaLanctoContabeis lancamento : listEmpresa) {
				int numLancto = contabilidadeCustom.findNextNumLancto(lancamento.codEmpresa, lancamento.exercicio);
				List<ConsultaLanctoContabeis> listfiliais =  contabilidadeCustom.findFiliaisPorEmpresaUsuario(usuario, lancamento.codEmpresa);
				for (ConsultaLanctoContabeis filial : listfiliais) {
					
					List<ConsultaLanctoContabeis> listDados =  contabilidadeCustom.findByUserFilial(usuario, filial.filialLancto, lancamento.exercicio);
					
					for (ConsultaLanctoContabeis dados : listDados) {
						
						String contaContabil = contabilidadeCustom.findContaContabByContaRed(dados.contaReduzida);
						int lote = gerarNumLote(lancamento.codEmpresa, dados.exercicio, dados.origem, dados.dataLancto, 0);
						contabilidadeCustom.inserirLanctoContabilSystextil(lancamento.codEmpresa, dados.filialLancto, dados.exercicio, dados.origem, contaContabil, dados.contaReduzida, 
								dados.debitoCredito, dados.valorLancto, dados.centroCusto, dados.histContabil, FormataData.parseStringToDate(dados.dataLancto), dados.complHistor1, 
								FormataData.parseStringToDate(dados.datainsercao), programa, dados.usuario, lote, numLancto, dados.seqLanc, dados.periodo, dados.status);
					}
				}
			}
			mensagem = 1;
			lanctoContabilImportacaoRepository.deleteByUsuario(usuario);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return mensagem;
	}

	public String validarParametrosCusto(List<ImportacaoParametroCustoEntity> parametrosCusto) {
		String mensagemErro = "";
		int produtoValido = 0;
		int estagioValido = 0;

		for (ImportacaoParametroCustoEntity parametro : parametrosCusto) {
			produtoValido = produtoCustom.validateProdutoExists(parametro.nivel, parametro.grupo, parametro.sub, parametro.item);
			estagioValido = produtoCustom.validateEstagio(parametro.estagio);

			if (produtoValido != 1) {
				mensagemErro += " Produto " + parametro.nivel + "." + parametro.grupo + "." + parametro.sub + "." + parametro.item + " não cadastrado! \n";
			}

			if (estagioValido != 1) {
				mensagemErro += " Estágio " + parametro.estagio + " não cadastrado! \n";
			}
		}
		return mensagemErro;
	}

	public String carregarArquivoParametrosCusto(List<ImportacaoParametroCustoEntity> parametrosCusto, int usuario) {
		String mensagemErro = "";

		importacaoParametroCustoRepository.deleteByUsuario(usuario);

		mensagemErro = validarParametrosCusto(parametrosCusto);
		if (!mensagemErro.equalsIgnoreCase("")) return mensagemErro;

		for (ImportacaoParametroCustoEntity parametro : parametrosCusto) {

			ImportacaoParametroCustoEntity dadosSalvos = new ImportacaoParametroCustoEntity(parametro.usuario, parametro.seqImportacao, parametro.empresa,
					parametro.nivel,parametro.grupo, parametro.sub, parametro.item, parametro.tipoParametro, parametro.sequencia, parametro.descricao,
					parametro.consumo, parametro.estagio, parametro.mes, parametro.ano);
			importacaoParametroCustoRepository.saveAndFlush(dadosSalvos);
		}

		return mensagemErro;
	}

	public void gravarParametrosCusto(int usuario) {
		List<ImportacaoParametroCustoEntity> listIntegrar = importacaoParametroCustoRepository.findByUsuario(usuario);

		for (ImportacaoParametroCustoEntity custoItem : listIntegrar) {
			contabilidadeCustom.inserirParametrosCusto(custoItem);
			importacaoParametroCustoRepository.deleteById(custoItem.id);
		}
	}

	public List<ImportacaoParametroCustoEntity> findParametrosNaoImportados(int usuario) {
		return importacaoParametroCustoRepository.findByUsuario(usuario);
	}
}
