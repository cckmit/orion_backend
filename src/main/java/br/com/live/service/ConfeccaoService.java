package br.com.live.service;

import java.util.List;

import br.com.live.entity.Restricoes;
import br.com.live.entity.RestricoesRolo;
import br.com.live.model.ConsultaRestricoesRolo;
import br.com.live.repository.RestricoesRepository;
import br.com.live.repository.RestricoesRoloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ConfeccaoCustom;
import br.com.live.entity.ObservacaoOrdemPacote;
import br.com.live.entity.TipoObservacao;
import br.com.live.model.ConsultaObservacaoOrdemPacote;
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

	public ConfeccaoService(TipoObservacaoRepository tipoObservacaoRepository, ConfeccaoCustom confeccaoCustom,
			ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository, RestricoesRepository restricoesRepository, RestricoesRoloRepository restricoesRoloRepository) {
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoCustom = confeccaoCustom;
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
		this.restricoesRepository = restricoesRepository;
		this.restricoesRoloRepository = restricoesRoloRepository;
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
}
