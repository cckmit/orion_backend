package br.com.live.service;

import java.util.List;

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

	public ConfeccaoService(TipoObservacaoRepository tipoObservacaoRepository, ConfeccaoCustom confeccaoCustom,
			ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository) {
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoCustom = confeccaoCustom;
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
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
}
