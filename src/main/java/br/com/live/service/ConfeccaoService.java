package br.com.live.service;

import java.util.Date;
import java.util.List;

import br.com.live.entity.Restricoes;
import br.com.live.entity.RestricoesRolo;
import br.com.live.model.ConsultaRestricoesRolo;
import br.com.live.repository.RestricoesRepository;
import br.com.live.repository.RestricoesRoloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CalendarioCustom;
import br.com.live.custom.ConfeccaoCustom;
import br.com.live.entity.MetasProducao;
import br.com.live.entity.MetasProducaoSemana;
import br.com.live.entity.ObservacaoOrdemPacote;
import br.com.live.entity.TipoObservacao;
import br.com.live.model.CalendarioSemana;
import br.com.live.model.ConsultaObservacaoOrdemPacote;
import br.com.live.repository.MetasProducaoRepository;
import br.com.live.repository.MetasProducaoSemanaRepository;
import br.com.live.repository.ObservacaoOrdemPacoteRepository;
import br.com.live.repository.TipoObservacaoRepository;
import br.com.live.util.ConteudoChaveNumerica;

@Service
@Transactional
public class ConfeccaoService {
	private final TipoObservacaoRepository tipoObservacaoRepository;
	private final ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository;
	private final ConfeccaoCustom confeccaoCustom;
	private final RestricoesRepository restricoesRepository;
	private final RestricoesRoloRepository restricoesRoloRepository;
	private final MetasProducaoRepository metasProducaoRepository;
	private final MetasProducaoSemanaRepository metasProducaoSemanaRepository;
	private final CalendarioCustom calendarioCustom; 

	public ConfeccaoService(TipoObservacaoRepository tipoObservacaoRepository, ConfeccaoCustom confeccaoCustom,
			ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository, RestricoesRepository restricoesRepository, RestricoesRoloRepository restricoesRoloRepository,
			MetasProducaoRepository metasProducaoRepository, MetasProducaoSemanaRepository metasProducaoSemanaRepository, CalendarioCustom calendarioCustom) {
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoCustom = confeccaoCustom;
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
		this.restricoesRepository = restricoesRepository;
		this.restricoesRoloRepository = restricoesRoloRepository;
		this.metasProducaoRepository = metasProducaoRepository;
		this.metasProducaoSemanaRepository = metasProducaoSemanaRepository;
		this.calendarioCustom = calendarioCustom;
	}

	public TipoObservacao saveTipoObservacao(long id, String descricao) {

		TipoObservacao dadosId = tipoObservacaoRepository.findByIdLong(id);

		if (dadosId != null) {
			dadosId.descricao = descricao;
		} else {
			dadosId = new TipoObservacao(confeccaoCustom.findNextIdOrdem(), descricao);
		}
		tipoObservacaoRepository.save(dadosId);

		return dadosId;
	}

	public void deleteById(long id) {
		tipoObservacaoRepository.deleteById(id);
	}
	
	public void deleteObservacaoById(String id) {
		observacaoOrdemPacoteRepository.deleteById(id);
	}

	public String saveObservacao(int estagio, int ordemProducao, int ordemConfeccao, int tipoObservacao, String obsAdicional) {
		ObservacaoOrdemPacote dadosObs = observacaoOrdemPacoteRepository.findByIdComposto(estagio + "-" + ordemProducao + "-" + ordemConfeccao);
		String msgErro = "";

		if (dadosObs == null) {
				ObservacaoOrdemPacote dadosObsSave = new ObservacaoOrdemPacote(estagio, ordemProducao, ordemConfeccao, tipoObservacao, obsAdicional);
				observacaoOrdemPacoteRepository.save(dadosObsSave);
		} else {
			dadosObs.observacaoAdicional = obsAdicional;
			observacaoOrdemPacoteRepository.save(dadosObs);
		}

		return msgErro;
	}
	
	public List<ConteudoChaveNumerica> findAllPacotesOrdem(int ordemProducao) {
		return confeccaoCustom.findPacotesOrdem(ordemProducao);
	}
	
	public List<ConsultaObservacaoOrdemPacote> findAllObsWithQuantidade() {
		return confeccaoCustom.findAllObsWithQuantidade();
	}

	public List<Restricoes> findAllRestricoes() {
		return restricoesRepository.findAll();
	}

	public Restricoes findRestricaoById(long idRestricao) {
		return restricoesRepository.findByIdRestricao(idRestricao);
	}

	public void saveRestricoes(long idRestricao, String descricao) {
		Restricoes dadosRestricao = restricoesRepository.findByIdRestricao(idRestricao);

		if (dadosRestricao == null) {
			dadosRestricao = new Restricoes(confeccaoCustom.findNextIdRestricao(), descricao);
		} else {
			dadosRestricao.descricao = descricao;
		}
		restricoesRepository.save(dadosRestricao);
	}

	public void deleteByIdRestricao(long idRestricao) {
		restricoesRepository.deleteById(idRestricao);
	}

	public void deleteBySeqRestricao(long idSeq) {
		restricoesRoloRepository.deleteById(idSeq);
	}

	public void proxySaveRestricoesPorOrdemBenef(List<ConteudoChaveNumerica> ordens, List<ConteudoChaveNumerica> restricoes) {
		for (ConteudoChaveNumerica dadosOrdens : ordens) {
			List<Integer> rolos = confeccaoCustom.findRolosByOrdem(dadosOrdens.value);
			for (Integer rolo : rolos) {
				for (ConteudoChaveNumerica dadosRestricao : restricoes) {
					saveRestricaoRolo(rolo, dadosRestricao.value);
				}
			}
		}
	}

	public void proxySaveRestricoesRolo(List<ConteudoChaveNumerica> rolos, List<ConteudoChaveNumerica> restricoes) {
		for (ConteudoChaveNumerica dadosRolo : rolos) {
			for ( ConteudoChaveNumerica dadosRestricao : restricoes) {
				saveRestricaoRolo(dadosRolo.value, dadosRestricao.value);
			}
		}
	}

	public void saveRestricaoRolo(int codigoRolo, int restricao) {
		int existeRestricao = confeccaoCustom.validarRestricaoRolo(codigoRolo, restricao);

		if (existeRestricao == 1) return;

		RestricoesRolo dadosSave = new RestricoesRolo(confeccaoCustom.findNextIdRestricaoRolo(), codigoRolo, restricao);
		restricoesRoloRepository.save(dadosSave);
		restricoesRoloRepository.flush();
	}
	
	public String saveMetaProducao(String id, int mes, int ano, int codEstagio, int metaMes, int diasUteis, int metaDiaria, int metaAjustada) {
		MetasProducao dadosMeta = metasProducaoRepository.findByIdMeta(id);
		
		if (dadosMeta == null) {
			dadosMeta = new MetasProducao(mes, ano, codEstagio, metaMes, diasUteis, metaDiaria, metaAjustada);
		} else {
			dadosMeta.metaMes = metaMes; 
			dadosMeta.diasUteis = diasUteis;
			dadosMeta.metaDiaria = metaDiaria;
			dadosMeta.metaAjustada = metaAjustada; 					
		}						
		metasProducaoRepository.save(dadosMeta);		
		saveMetaSemana(dadosMeta.id, mes, ano, metaDiaria, metaAjustada);		
		return id;
	}
	
	public void saveMetaSemana(String idMesAno, int mes, int ano, int qtdePecasMetaDiaria, int qtdePecasMetaAjustDiaria) {
		
		metasProducaoSemanaRepository.deleteByIdMes(idMesAno);
		
		List<CalendarioSemana> semanas = calendarioCustom.getSemanasByMes(mes, ano);
		
		long id = 0;
		int numSemanaMes = 0;
		int qtdePecasMetaSemana = 0;
		int qtdePecasMetaAjustSemana = 0;
		int qtdePecasMetaTurno = 0;
		int qtdePecasMetaAjustTurno = 0;
		
		for (CalendarioSemana semana : semanas) {
			numSemanaMes++;			
			qtdePecasMetaSemana = semana.getQtdeDiasUteis() * qtdePecasMetaDiaria;
			qtdePecasMetaAjustSemana = semana.getQtdeDiasUteis() * qtdePecasMetaAjustDiaria;
			// DIVIDE POR 2 TURNOS DE PRODUÇÃO (ATUALMENTE SÓ EXISTEM 2 TURNOS)
			qtdePecasMetaTurno = (int) qtdePecasMetaSemana / 2; 
			qtdePecasMetaAjustTurno = (int) qtdePecasMetaAjustSemana / 2; 
			id = metasProducaoSemanaRepository.findNextId();
			MetasProducaoSemana metaProducaoSemana = new MetasProducaoSemana(id, idMesAno, numSemanaMes, semana.getQtdeDiasUteis(), semana.getDataInicio(), semana.getDataFim(), qtdePecasMetaSemana, qtdePecasMetaTurno, qtdePecasMetaAjustSemana, qtdePecasMetaAjustTurno);
			metasProducaoSemanaRepository.save(metaProducaoSemana);
		}				
	}	
	
	/*
	public long saveMetaSemana(long id, String idMes, int nrSemana, int diasUteis, Date dataInicio, Date dataFim, int metaReal, int metaRealTurno, int metaAjustada, int metaAjustadaTurno) {
		MetasProducaoSemana dadosSemana = null; //metasProducaoSemanaRepository.findByIdMetaSemana(id);
		
		if (dadosSemana == null) {
			dadosSemana = new MetasProducaoSemana(id, idMes, nrSemana, diasUteis, dataInicio, dataFim, metaReal, metaRealTurno, metaAjustada, metaAjustadaTurno);
		} else {
			dadosSemana.id = id;
			dadosSemana.idMes = idMes; 
			dadosSemana.nrSemana = nrSemana;
			dadosSemana.diasUteis = diasUteis;
			dadosSemana.dataInicio = dataInicio;
			dadosSemana.dataFim = dataFim;
			dadosSemana.metaReal = metaReal; 
			dadosSemana.metaRealTurno = metaRealTurno;
			dadosSemana.metaAjustada = metaAjustada;
			dadosSemana.metaAjustadaTurno = metaAjustadaTurno; 					
		}						
		metasProducaoSemanaRepository.save(dadosSemana);
		
		return id;
	}*/
}
