package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.live.custom.PlanoMestreCustom;
import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.util.BodyOrdemProducao;
import br.com.live.util.StatusGravacao;

@Service
public class OrdemProducaoService {
	
	private final OrdemProducaoServiceTransaction ordemProducaoServiceTransaction;
	private final PlanoMestreCustom planoMestreCustom;
	private final PlanoMestreRepository planoMestreRepository;
		
	public OrdemProducaoService (OrdemProducaoServiceTransaction ordemProducaoServiceTransaction, PlanoMestreCustom planoMestreCustom, PlanoMestreRepository planoMestreRepository) {
		this.ordemProducaoServiceTransaction = ordemProducaoServiceTransaction;
		this.planoMestreCustom = planoMestreCustom;
		this.planoMestreRepository = planoMestreRepository;
	}	
	
	public BodyOrdemProducao gerarOrdens(long idPlanoMestre, List<Long> preOrdens) {
				
		for (long idPreOrdem : preOrdens) {
			ordemProducaoServiceTransaction.gerarOrdem(idPreOrdem);
		}
		
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
								
		return new BodyOrdemProducao(idPlanoMestre, planoMestre.situacao, planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre));
	}	

	public List<ConsultaPreOrdemProducao> excluirOrdens(long idPlanoMestre, List<Long> preOrdens) {
		
		for (long idPreOrdem : preOrdens) {
			ordemProducaoServiceTransaction.excluirOrdem(idPreOrdem);			
		}
			
		return planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}
	
}
