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
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.model.ConsultaDadosCompEstrutura;
import br.com.live.model.ConsultaDadosEstrutura;
import br.com.live.model.ConsultaDadosFilete;
import br.com.live.model.ConsultaDadosRoteiro;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class OrdemProducaoServiceTransaction {
	 
	private final PlanoMestreRepository planoMestreRepository;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;	 
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;	 

	public OrdemProducaoServiceTransaction(PlanoMestreRepository planoMestreRepository, PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository, 
			PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository, 
			OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom, PlanoMestreCustom planoMestreCustom) {
		this.planoMestreRepository = planoMestreRepository;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;		
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
	}	
	
	private int gravarCapa(PlanoMestrePreOrdem preOrdem) {
		int idOrdemProducao = ordemProducaoCustom.findNextIdOrdem();
		String observacao2 = "PLANO MESTRE: " + preOrdem.idPlanoMestre;		
		ordemProducaoCustom.gravarCapa(idOrdemProducao, preOrdem.grupo, preOrdem.periodo, preOrdem.alternativa, preOrdem.roteiro, preOrdem.quantidade, preOrdem.observacao, observacao2);
		return idOrdemProducao;
	}
	
	private void gravarTamanhoCor(int idOrdemProducao, PlanoMestrePreOrdemItem preOrdemItem) {
		int ordemTamanho = produtoCustom.findOrdemTamanho(preOrdemItem.sub);
		ordemProducaoCustom.gravarTamanhoCor(idOrdemProducao, preOrdemItem.sub, preOrdemItem.item, preOrdemItem.quantidade, ordemTamanho);
	}
			
	private void gravarPacotesConfeccao(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {		
		int idPacote;
		int qtdeLote;
		int estagioAnterior;
		int salvaEstagio;
		int salvaSequencia;
		int ultimoEstagio = 0;
		int qtdeTotalProgItem = preOrdemItem.quantidade;
		int loteFabricacao = produtoCustom.findLoteFabricacao(preOrdem.grupo, preOrdemItem.sub);
		
		List<ConsultaDadosRoteiro> listaDadosRoteiro = produtoCustom.findDadosRoteiro(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa, preOrdem.roteiro) ; 
		
		while (qtdeTotalProgItem > 0) {
			
			idPacote = ordemProducaoCustom.findNextIdPacote(preOrdem.periodo);
			
			if (qtdeTotalProgItem > loteFabricacao)
				qtdeLote = loteFabricacao;
			else qtdeLote = qtdeTotalProgItem;
			
			qtdeTotalProgItem -= loteFabricacao;
			
			estagioAnterior = 0;
			salvaEstagio = 0;
			salvaSequencia = 0;
			ultimoEstagio = 0;
			
			for (ConsultaDadosRoteiro dadosRoteiro : listaDadosRoteiro) {
				
				if ((salvaEstagio != dadosRoteiro.estagio)&&(dadosRoteiro.pedeProduto != 1)&&(dadosRoteiro.tipoOperCmc == 0)) {

					if (dadosRoteiro.seqEstagio != salvaSequencia)
						estagioAnterior = salvaEstagio;
					
					if (dadosRoteiro.seqEstagio == 0)
						estagioAnterior = salvaEstagio;
					
					ordemProducaoCustom.gravarPacoteConfeccao(preOrdem.periodo, idPacote, dadosRoteiro.estagio, idOrdemProducao, preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, qtdeLote, 
							estagioAnterior, dadosRoteiro.familia, dadosRoteiro.seqOperacao, dadosRoteiro.estagioDepende, dadosRoteiro.seqEstagio); 
					 
					salvaSequencia = dadosRoteiro.seqEstagio;
					salvaEstagio = dadosRoteiro.estagio;
				}
			}
			
			ultimoEstagio = salvaEstagio;
		}
		
		ordemProducaoCustom.atualizarUltimoEstagioOrdem(idOrdemProducao, ultimoEstagio);
	}
	
	private void gravarDadosTecidos(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {
		
		String subTecido = "";
		String itemTecido = "";
		double consumoTecido = 0.0;		
		double qtdeKgProg = 0.0;
		double metrosTecido = 0.0;
		double larguraRisco = 0.0;
		double larguraFilete = 0.0;
		double metrosOrdem = 0.0;
		double qtdeTotMetrosTecido = 0.0;
		double tirasLargura = 0.0;
		double qtdePerdas = 0.0;		
		int riscoPadrao = produtoCustom.findRiscoPadraoByCodigo(preOrdem.grupo);
		
		ConsultaDadosCompEstrutura dadosComponente;
		ConsultaDadosFilete dadosFileteEstrutura;
		ConsultaDadosFilete dadosFileteRisco;
		ConsultaDadosFilete dadosFileteTecido;
		
		List<ConsultaDadosEstrutura> listaDadosEstrutura = produtoCustom.findDadosEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa);
				
		for (ConsultaDadosEstrutura dadosEstrutura : listaDadosEstrutura) {
			subTecido = dadosEstrutura.subComp;
			itemTecido = dadosEstrutura.itemComp;
			consumoTecido = dadosEstrutura.consumo;			
			
			if ((subTecido.equalsIgnoreCase("000"))||(consumoTecido == 0.000000)) {
				dadosComponente = produtoCustom.findDadosComponenteEstrutura(preOrdem.grupo, preOrdemItem.sub, dadosEstrutura.itemItem, dadosEstrutura.sequencia, preOrdem.alternativa);
				subTecido = dadosComponente.sub;
				consumoTecido = dadosComponente.consumo; 
			}
			
			if (itemTecido.equalsIgnoreCase("000000")) {
				dadosComponente = produtoCustom.findDadosComponenteEstrutura(preOrdem.grupo, dadosEstrutura.subItem, preOrdemItem.item, dadosEstrutura.sequencia, preOrdem.alternativa);	
				itemTecido = dadosComponente.item;				
			}
		
			ordemProducaoCustom.gravarCapaEnfesto(idOrdemProducao, preOrdem.grupo, dadosEstrutura.sequencia, preOrdem.alternativa, preOrdem.roteiro);
			
			qtdeKgProg = (consumoTecido * (float) preOrdemItem.quantidade);
			metrosTecido = 0.0; 
			qtdeTotMetrosTecido = 0.0;
			
			dadosFileteEstrutura = produtoCustom.findDadosFileteEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, dadosEstrutura.sequencia, preOrdem.alternativa);
									 
			if (dadosFileteEstrutura.tipoCorte == 2) {				
				dadosFileteRisco = produtoCustom.findDadosFileteRisco(preOrdem.grupo, riscoPadrao, dadosEstrutura.sequencia, preOrdem.alternativa);
				
				larguraFilete = dadosFileteEstrutura.larguraFilete; 
				larguraRisco = dadosFileteRisco.larguraRisco;
				
				if (larguraRisco == 0.000) {				
					dadosFileteTecido = produtoCustom.findDadosFileteTecidos(dadosEstrutura.grupoComp, subTecido);
					
					if (dadosFileteTecido.tubularAberto == 2) larguraRisco = dadosFileteTecido.larguraTecido * 2;					
				}	
										
				if (larguraRisco == 0.000) larguraRisco = 1.000;
				if (larguraFilete == 0.000) larguraFilete = 1.000;
				
				metrosOrdem = ((double) preOrdemItem.quantidade * dadosFileteEstrutura.comprimentoFilete);					
				
				tirasLargura = larguraRisco / larguraFilete;
				
				if (tirasLargura == 0.000) tirasLargura = 1.000; 
				
				metrosTecido = metrosOrdem / tirasLargura; 
				
				if (dadosEstrutura.percPerdas > 0.000) {						
					qtdePerdas = (dadosEstrutura.percPerdas * metrosTecido) / 100;
					metrosTecido += qtdePerdas;  
				}
				
				qtdeTotMetrosTecido += metrosTecido;		            								
			}
			
			ordemProducaoCustom.gravarTecidosEnfesto(idOrdemProducao, preOrdemItem.item, dadosEstrutura.nivelComp, dadosEstrutura.grupoComp, subTecido, itemTecido, dadosEstrutura.sequencia, qtdeKgProg, qtdeTotMetrosTecido);			
		}
	}
	
	private void gravarDadosItem(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {
		gravarTamanhoCor(idOrdemProducao, preOrdemItem);
		gravarPacotesConfeccao(idOrdemProducao, preOrdem, preOrdemItem);			
		gravarDadosTecidos(idOrdemProducao, preOrdem, preOrdemItem);		
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

		for (PlanoMestrePreOrdemItem preOrdemItem : preOrdemItens) {
			existeEstrutura = produtoCustom.existsEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa);
			existeRoteiro = produtoCustom.existsRoteiro(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa, preOrdem.roteiro);
			if ((!existeEstrutura)||(!existeRoteiro)) break;
		}
		
		if (!existeEstrutura) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não existe estrutura para a alternativa!"));			
		}

		if (!existeRoteiro) {
			dadosOk = false;
			mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não existe roteiro para a alternativa e roteiro!"));			
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
				planoMestrePreOrdemRepository.save(preOrdem); 
			}			
		}				
	}	
	
	public boolean gerarOrdem(long idPreOrdem) {
		boolean ordemValida = true;	
		int idOrdemProducao=0;		
		
		Map<Long, StatusGravacao> mapPreOrdensComErro = new HashMap<Long, StatusGravacao> ();
		List<PlanoMestrePreOrdem> listaPreOrdensConcluidas = new ArrayList<PlanoMestrePreOrdem> ();
		
		PlanoMestrePreOrdem preOrdem = planoMestrePreOrdemRepository.findById(idPreOrdem);
		List<PlanoMestrePreOrdemItem> preOrdemItens = planoMestrePreOrdemItemRepository.findByIdOrdem(idPreOrdem);			
				
		if (preOrdem.ordemGerada > 0) return false;
		
		if (!validarDadosOrdem(preOrdem, mapPreOrdensComErro)) ordemValida = false;
		if (!validarDadosItem(preOrdem, preOrdemItens, mapPreOrdensComErro)) ordemValida = false;
		
		if (ordemValida) {	
			
			try {														
				idOrdemProducao = gravarCapa(preOrdem);
								
				for (PlanoMestrePreOrdemItem preOrdemItem : preOrdemItens) {																							
					gravarDadosItem(idOrdemProducao, preOrdem, preOrdemItem);					
				}
				
				preOrdem.ordemGerada = idOrdemProducao;
				preOrdem.situacao = 1; // Ordem Gerada
				preOrdem.status = "ORDEM GERADA COM SUCESSO!";
				listaPreOrdensConcluidas.add(preOrdem);

			} catch (Exception e) {				
				mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não foi possível gerar a ordem de produção!"));
				if (idOrdemProducao > 0) ordemProducaoCustom.excluirOrdemProducao(idOrdemProducao);
				System.out.println(e.getMessage());
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
				preOrdem.status = "ORDEM EXCLUÍDA COM SUCESSO!";
				preOrdem.situacao = 2; // Ordem Excluída
				listaPreOrdensConcluidas.add(preOrdem);
			} catch (Exception e) {
				mapPreOrdensComErro.put(preOrdem.id, new StatusGravacao(false, "Não foi possível excluir essa ordem!"));
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
		if (processo == OrdemProducaoService.GERACAO_EM_ANDAMENTO) {			
			List<PlanoMestrePreOrdem> ordensGeradas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndSituacaoOrdemGerada(idPlanoMestre);
			if (ordensGeradas.size() > 0) planoMestre.situacao = OrdemProducaoService.ORDENS_GERADAS; 
		} else if (processo == OrdemProducaoService.EXCLUSAO_EM_ANDAMENTO) {
			List<PlanoMestrePreOrdem> ordensGeradas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndSituacaoOrdemGerada(idPlanoMestre);
			List<PlanoMestrePreOrdem> ordensExcluidas = planoMestrePreOrdemRepository.findByIdPlanoMestreAndSituacaoOrdemExcluida(idPlanoMestre);
			if (ordensGeradas.size() > 0) planoMestre.situacao = OrdemProducaoService.ORDENS_GERADAS;
			else if (ordensExcluidas.size() > 0) planoMestre.situacao = OrdemProducaoService.ORDENS_EXCLUIDAS;
		}		
		planoMestreRepository.save(planoMestre);
	}	
}
