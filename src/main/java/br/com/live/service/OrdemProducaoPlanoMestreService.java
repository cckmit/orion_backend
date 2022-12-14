package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.live.body.BodyOrdemProducao;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.entity.PlanoMestre;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.repository.PlanoMestreRepository;

@Service
public class OrdemProducaoPlanoMestreService {
	
	public static final int ORDENS_GERADAS = 2;
	public static final int ORDENS_EXCLUIDAS = 3;
	public static final int GERACAO_EM_ANDAMENTO = 8; 
	public static final int EXCLUSAO_EM_ANDAMENTO = 9;
	private final OrdemProducaoPlanoMestreServiceTransaction ordemProducaoServiceTransaction;
	private final PlanoMestreCustom planoMestreCustom;
	private final PlanoMestreRepository planoMestreRepository;
		
	public OrdemProducaoPlanoMestreService (OrdemProducaoPlanoMestreServiceTransaction ordemProducaoServiceTransaction, PlanoMestreCustom planoMestreCustom, PlanoMestreRepository planoMestreRepository) {
		this.ordemProducaoServiceTransaction = ordemProducaoServiceTransaction;
		this.planoMestreCustom = planoMestreCustom;
		this.planoMestreRepository = planoMestreRepository;
	}	
		
	public BodyOrdemProducao gerarOrdens(long idPlanoMestre, List<Long> preOrdens) {
				
		int situacaoAnterior = ordemProducaoServiceTransaction.iniciarProcessoAtualizacaoOrdemPlanoMestre(idPlanoMestre, GERACAO_EM_ANDAMENTO);
		
		for (long idPreOrdem : preOrdens) {
			ordemProducaoServiceTransaction.gerarOrdem(idPreOrdem);
		}
		
		ordemProducaoServiceTransaction.finalizarProcessoAtualizacaoOrdemPlanoMestre(idPlanoMestre, GERACAO_EM_ANDAMENTO, situacaoAnterior);
		
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
								
		return new BodyOrdemProducao(idPlanoMestre, planoMestre.situacao, planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre));
	}	

	public List<ConsultaPreOrdemProducao> excluirOrdens(long idPlanoMestre, List<Long> preOrdens) {
		
		int situacaoAnterior = ordemProducaoServiceTransaction.iniciarProcessoAtualizacaoOrdemPlanoMestre(idPlanoMestre, EXCLUSAO_EM_ANDAMENTO);
		
		for (long idPreOrdem : preOrdens) {
			ordemProducaoServiceTransaction.excluirOrdem(idPreOrdem);
		}
		
		ordemProducaoServiceTransaction.finalizarProcessoAtualizacaoOrdemPlanoMestre(idPlanoMestre, EXCLUSAO_EM_ANDAMENTO, situacaoAnterior);
		
		return planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}	
}
