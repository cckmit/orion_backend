package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestreParametros;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.model.DadosGeracaoOrdemProducao;
import br.com.live.model.DadosGeracaoOrdemProducaoItem;
import br.com.live.repository.PlanoMestreParametrosRepository;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class OrdemProducaoPlanoMestreServiceTransaction {
	 
	private final PlanoMestreRepository planoMestreRepository;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;	 
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;	 
	private final OrdemProducaoService ordemProducaoService;
	private final PlanoMestreParametrosRepository planoMestreParametrosRepository;
	private final PlanoMestreCustom planoMestreCustom; 

	public OrdemProducaoPlanoMestreServiceTransaction(PlanoMestreRepository planoMestreRepository, PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository, 
			PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository, 
			OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom, PlanoMestreCustom planoMestreCustom, OrdemProducaoService ordemProducaoService,
			PlanoMestreParametrosRepository planoMestreParametrosRepository) {
		this.planoMestreRepository = planoMestreRepository;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;		
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
		this.ordemProducaoService = ordemProducaoService;
		this.planoMestreParametrosRepository = planoMestreParametrosRepository;
		this.planoMestreCustom = planoMestreCustom;
	}	
	
	public boolean validarDadosOrdem(PlanoMestrePreOrdem preOrdem, Map<Long, StatusGravacao> mapPreOrdensComErro) {
		
		boolean dadosOk = true;

		if (preOrdem.periodo == 0) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Período de produção não informado!"));
		}
		
		if (produtoCustom.isProdutoComprado(preOrdem.grupo)) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Referência é um produto comprado!"));
		}
		
		return dadosOk;
	}
	
	public boolean validarDadosItem(PlanoMestrePreOrdem preOrdem, List<PlanoMestrePreOrdemItem> preOrdemItens, Map<Long, StatusGravacao> mapPreOrdensComErro) {
		
		boolean dadosOk = true;
		boolean existeEstrutura = true;
		boolean existeRoteiro = true;
		boolean roteiroSequenciado = true;

		for (PlanoMestrePreOrdemItem preOrdemItem : preOrdemItens) {
			existeEstrutura = produtoCustom.existsEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa);
			existeRoteiro = produtoCustom.existsRoteiro("1", preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa, preOrdem.roteiro);
			roteiroSequenciado = produtoCustom.roteiroSequenciado(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa, preOrdem.roteiro);
			if ((!existeEstrutura)||(!existeRoteiro)||(roteiroSequenciado)) break;
		}
		
		if (!existeEstrutura) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não existe estrutura para a alternativa!"));			
		}

		if (!existeRoteiro) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não existe roteiro para a alternativa e roteiro!"));			
		}
		
		if (!roteiroSequenciado) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Roteiro do produto não está sequenciado!"));						
		}
		
		return dadosOk;
	}	
	
	public void atualizarErrosPreOrdens(Map<Long, StatusGravacao> mapPreOrdensComErro) {
		
		StatusGravacao status;
		PlanoMestrePreOrdem preOrdem;
		
		for (long idPreOrdem : mapPreOrdensComErro.keySet()) {
			preOrdem = planoMestrePreOrdemRepository.findById(idPreOrdem);
			status = mapPreOrdensComErro.get(idPreOrdem);
			if (!status.isConcluido()) {
				preOrdem.status = status.getMensagem().toUpperCase();
				preOrdem.mensagemGravacaoOrdem = status.getMensagemCompleta();
				planoMestrePreOrdemRepository.save(preOrdem); 
			}			
		}				
	}	

	private int findColecaoProduto(PlanoMestrePreOrdem preOrdem, List<PlanoMestrePreOrdemItem> preOrdemItens) {
		String colecoes = "";
		
		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(preOrdem.idPlanoMestre);
		if (parametros.colecoes != null) colecoes = parametros.colecoes;  
		
		PlanoMestrePreOrdemItem preOrdemItem = preOrdemItens.get(0);
				
		int colecaoProduto = planoMestreCustom.findColecaoProdutoByRangeColecoes("1", preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, colecoes);
		
		return colecaoProduto;
	}

	public boolean gerarOrdem(long idPreOrdem) {
		boolean ordemValida = true;			
		
		Map<Long, StatusGravacao> mapPreOrdensComErro = new HashMap<Long, StatusGravacao> ();
		List<PlanoMestrePreOrdem> listaPreOrdensConcluidas = new ArrayList<PlanoMestrePreOrdem> ();
		
		PlanoMestrePreOrdem preOrdem = planoMestrePreOrdemRepository.findById(idPreOrdem);
		List<PlanoMestrePreOrdemItem> preOrdemItens = planoMestrePreOrdemItemRepository.findByIdOrdem(idPreOrdem);			
						
		int colecaoProdutoPlanoMestre = findColecaoProduto(preOrdem, preOrdemItens);
		
		if (preOrdem.ordemGerada > 0) return false;
		
		if (!validarDadosOrdem(preOrdem, mapPreOrdensComErro)) ordemValida = false;
		if (!validarDadosItem(preOrdem, preOrdemItens, mapPreOrdensComErro)) ordemValida = false;
		
		if (ordemValida) {	
			
			try {										
				DadosGeracaoOrdemProducao dadosOrdem = new DadosGeracaoOrdemProducao(preOrdem.grupo, preOrdem.periodo, preOrdem.alternativa, preOrdem.roteiro, preOrdem.observacao, "PLANO MESTRE: " + preOrdem.idPlanoMestre, 0, colecaoProdutoPlanoMestre);

				for (PlanoMestrePreOrdemItem preOrdemItem : preOrdemItens) {
					dadosOrdem.addItem(preOrdemItem.sub, preOrdemItem.item, preOrdemItem.quantidade);
				}

				StatusGravacao status = ordemProducaoService.gerarOrdemProducao(dadosOrdem);
				if (!status.isConcluido()) throw new Exception(status.getMensagemCompleta());

				preOrdem.ordemGerada = (int) status.getObjetoRetorno();
				preOrdem.situacao = 1; // Ordem Gerada
				preOrdem.status = "ORDEM GERADA COM SUCESSO!";				
				listaPreOrdensConcluidas.add(preOrdem);

			} catch (Exception e) {				
				mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não foi possível gerar a ordem de produção!", e.getMessage()));								
			}						
		}
							
		planoMestrePreOrdemRepository.saveAll(listaPreOrdensConcluidas);		
		atualizarErrosPreOrdens(mapPreOrdensComErro);
		
		return true;
	}	
		
	public boolean validarExclusaoOrdem(PlanoMestrePreOrdem preOrdem, Map<Long, StatusGravacao> mapPreOrdensComErro) {
		boolean dadosOk = true;
				
		if (ordemProducaoCustom.isExistsApontProducao(preOrdem.ordemGerada)) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Existe apontamento de produção!"));			
		}
		
		return dadosOk;
	}
	
	public boolean excluirOrdem(long idPreOrdem) {
		
		Map<Long, StatusGravacao> mapPreOrdensComErro = new HashMap<Long, StatusGravacao> ();
		List<PlanoMestrePreOrdem> listaPreOrdensConcluidas = new ArrayList<PlanoMestrePreOrdem> ();
		
		PlanoMestrePreOrdem preOrdem = planoMestrePreOrdemRepository.findById(idPreOrdem);
					
		if (preOrdem.ordemGerada == 0) return false;
							
		if (validarExclusaoOrdem(preOrdem, mapPreOrdensComErro)) {		
			try {			
				ordemProducaoCustom.excluirOrdemProducao(preOrdem.ordemGerada);				
				preOrdem.situacao = 2; // Ordem Excluída
				preOrdem.status = "ORDEM EXCLUÍDA COM SUCESSO!";								
				listaPreOrdensConcluidas.add(preOrdem);
			} catch (Exception e) {
				mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não foi possível excluir essa ordem!", e.getMessage()));
			}
		}
		
		planoMestrePreOrdemRepository.saveAll(listaPreOrdensConcluidas);
		atualizarErrosPreOrdens(mapPreOrdensComErro);
				
		return true;
	}			
	
	public int iniciarProcessoAtualizacaoOrdemPlanoMestre(long idPlanoMestre, int processo) {
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);		
		int situacaoAtual = planoMestre.situacao; 		
		planoMestre.situacao = processo;	
		planoMestreRepository.save(planoMestre);		
		return situacaoAtual;
	}
	
	public void finalizarProcessoAtualizacaoOrdemPlanoMestre(long idPlanoMestre, int processo, int situacaoAnterior) {
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
		planoMestre.situacao = situacaoAnterior;
		if (processo == OrdemProducaoPlanoMestreService.GERACAO_EM_ANDAMENTO) {			
			List<PlanoMestrePreOrdem> ordensGeradas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndSituacaoOrdemGerada(idPlanoMestre);
			if (ordensGeradas.size() > 0) planoMestre.situacao = OrdemProducaoPlanoMestreService.ORDENS_GERADAS; 
		} else if (processo == OrdemProducaoPlanoMestreService.EXCLUSAO_EM_ANDAMENTO) {
			List<PlanoMestrePreOrdem> ordensGeradas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndSituacaoOrdemGerada(idPlanoMestre);
			List<PlanoMestrePreOrdem> ordensExcluidas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndSituacaoOrdemExcluida(idPlanoMestre);
			if (ordensGeradas.size() > 0) planoMestre.situacao = OrdemProducaoPlanoMestreService.ORDENS_GERADAS;
			else if (ordensExcluidas.size() > 0) planoMestre.situacao = OrdemProducaoPlanoMestreService.ORDENS_EXCLUIDAS;
		}		
		planoMestreRepository.save(planoMestre);
	}		
}
