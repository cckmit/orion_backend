package br.com.live.service;

import java.util.List;

import org.hibernate.metamodel.model.convert.internal.OrdinalEnumValueConverter;
import org.springframework.stereotype.Service;

import br.com.live.body.BodyOrdemProducao;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.entity.PlanoMestre;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.DadosTagChina;
import br.com.live.model.EstagioProducao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.OrdemProducao;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.util.ConteudoChaveNumerica;

@Service
public class OrdemProducaoService {
	
	public static final int ORDENS_GERADAS = 2;
	public static final int ORDENS_EXCLUIDAS = 3;
	public static final int GERACAO_EM_ANDAMENTO = 8; 
	public static final int EXCLUSAO_EM_ANDAMENTO = 9;
	private final OrdemProducaoServiceTransaction ordemProducaoServiceTransaction;
	private final PlanoMestreCustom planoMestreCustom;
	private final PlanoMestreRepository planoMestreRepository;
	private final OrdemProducaoCustom ordemProducaoCustom; 
		
	public OrdemProducaoService (OrdemProducaoServiceTransaction ordemProducaoServiceTransaction, PlanoMestreCustom planoMestreCustom, PlanoMestreRepository planoMestreRepository, OrdemProducaoCustom ordemProducaoCustom) {
		this.ordemProducaoServiceTransaction = ordemProducaoServiceTransaction;
		this.planoMestreCustom = planoMestreCustom;
		this.planoMestreRepository = planoMestreRepository;
		this.ordemProducaoCustom = ordemProducaoCustom;
	}	
	
	public List<EstagioProducao> findAllEstagios() {
		return ordemProducaoCustom.findAllEstagios();
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
	
	public List<OrdemProducao> findAllTagsExportacaoChina() {
		return ordemProducaoCustom.findAllTagsExportacaoChina();
	}
	
	public List<OrdemConfeccao> findAllPacotes(int ordemProducao) {
		return ordemProducaoCustom.findAllOrdensConfeccao(ordemProducao);
	}
	
	public List<DadosTagChina> findDadosTag(List<ConteudoChaveNumerica> ordemProducao) {
		return ordemProducaoCustom.findDadosTagChina(ConteudoChaveNumerica.parseValueToString(ordemProducao));
	}
	
	public void baixarEstagioProducao(List<Integer> listaOrdensProducao, int estagio) {
		for (Integer ordemProducao : listaOrdensProducao) {
			baixarEstagioProducao(ordemProducao, estagio);
		}
	}
	
	public void baixarEstagioProducao(int ordemProducao, int estagio) {		
		ordemProducaoServiceTransaction.baixarEstagioProducao(ordemProducao, estagio);		
	}
}
