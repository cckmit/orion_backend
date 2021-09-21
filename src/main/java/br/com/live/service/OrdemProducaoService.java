package br.com.live.service;

import java.util.List;
import org.springframework.stereotype.Service;

import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.DadosTagChina;
import br.com.live.model.EstagioProducao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.OrdemProducao;
import br.com.live.repository.PlanoMestrePreOrdemRepository;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.util.BodyOrdemProducao;
import br.com.live.util.ConteudoChaveNumerica;

@Service
public class OrdemProducaoService {
	
	private final OrdemProducaoServiceTransaction ordemProducaoServiceTransaction;
	private final PlanoMestreCustom planoMestreCustom;
	private final PlanoMestreRepository planoMestreRepository;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final OrdemProducaoCustom ordemProducaoCustom; 
		
	public OrdemProducaoService (OrdemProducaoServiceTransaction ordemProducaoServiceTransaction, PlanoMestreCustom planoMestreCustom, PlanoMestreRepository planoMestreRepository, PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository, OrdemProducaoCustom ordemProducaoCustom) {
		this.ordemProducaoServiceTransaction = ordemProducaoServiceTransaction;
		this.planoMestreCustom = planoMestreCustom;
		this.planoMestreRepository = planoMestreRepository;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.ordemProducaoCustom = ordemProducaoCustom;
	}	
	
	public List<EstagioProducao> findAllEstagios() {
		return ordemProducaoCustom.findAllEstagios();
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
		
		atualizarStatusPlanoMestre(idPlanoMestre);
		
		return planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}
	
	private void atualizarStatusPlanoMestre(long idPlanoMestre){
		int todasOrdensExcluidas = 1;
		
		List<PlanoMestrePreOrdem> allPreOrdens = planoMestrePreOrdemRepository.findByIdPlanoMestre(idPlanoMestre);
		
		for (PlanoMestrePreOrdem preOrdem : allPreOrdens) {
			if (preOrdem.situacao != 2) todasOrdensExcluidas = 0;
		}
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);
		
		if (todasOrdensExcluidas == 1) planoMestre.situacao = 3; // 3 - Ordens Excluidas
		
		planoMestreRepository.save(planoMestre);
		
	}
	
	public List<OrdemProducao> findAllTagsExportacaoChina() {
		return ordemProducaoCustom.findAllTagsExportacaoChina();
	}
	
	public List<OrdemConfeccao> findAllPacotes(int ordemProducao) {
		return ordemProducaoCustom.findAllOrdensConfeccao(ordemProducao);
	}
	
	public List<DadosTagChina> findDadosTag(List<ConteudoChaveNumerica> ordemProducao) {
		return ordemProducaoCustom.findDadosTagChina(ConteudoChaveNumerica.parseValueToString(ordemProducao));
	}
	
}
