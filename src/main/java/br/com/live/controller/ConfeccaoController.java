package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyObservacaoPorOp;
import br.com.live.body.BodyTipoObservacao;
import br.com.live.entity.ObservacaoOrdemPacote;
import br.com.live.entity.TipoObservacao;
import br.com.live.model.ConsultaObservacaoOrdemPacote;
import br.com.live.repository.ObservacaoOrdemPacoteRepository;
import br.com.live.repository.TipoObservacaoRepository;
import br.com.live.service.ConfeccaoService;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/confeccao")
public class ConfeccaoController {

	private ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository;
	private TipoObservacaoRepository tipoObservacaoRepository;
	private ConfeccaoService confeccaoService;

	@Autowired
	public ConfeccaoController(ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository,
			TipoObservacaoRepository tipoObservacaoRepository, ConfeccaoService confeccaoService) {
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoService = confeccaoService;
	}

	@RequestMapping(value = "/find-all-tipo-obs", method = RequestMethod.GET)
	public List<TipoObservacao> findAllTiposObs() {
		return tipoObservacaoRepository.findAll();
	}

	@RequestMapping(value = "/find-tipo-obs-by-id/{id}", method = RequestMethod.GET)
	public TipoObservacao findTipoObsById(@PathVariable("id") long id) {
		return tipoObservacaoRepository.findByIdLong(id);
	}

	@RequestMapping(value = "/save-tipo-observacao", method = RequestMethod.POST)
	public TipoObservacao saveTipoObservacao(@RequestBody BodyTipoObservacao body) {
		return confeccaoService.saveTipoObservacao(body.id, body.descricao);
	}

	@RequestMapping(value = "/delete-by-id/{id}", method = RequestMethod.DELETE)
	public List<TipoObservacao> deleteTipoObservacaoById(@PathVariable("id") long id) {
		confeccaoService.deleteById(id);
		return tipoObservacaoRepository.findAll();
	}

	@RequestMapping(value = "/find-all-observacoes", method = RequestMethod.GET)
	public List<ConsultaObservacaoOrdemPacote> findAllObservacoes() {
		return confeccaoService.findAllObsWithQuantidade();
	}

	@RequestMapping(value = "/find-obs-by-id/{id}", method = RequestMethod.GET)
	public ObservacaoOrdemPacote findObsByIdComposto(@PathVariable("id") String id) {
		return observacaoOrdemPacoteRepository.findByIdComposto(id);
	}

	@RequestMapping(value = "/save-observacao", method = RequestMethod.POST)
	public String saveObservacao(@RequestBody BodyObservacaoPorOp body) {
		return confeccaoService.saveObservacao(body.estagio, body.ordemProducao, body.ordemConfeccao, body.tipoObservacao, body.obsAdicional);
	}
	
	@RequestMapping(value = "/find-all-pacotes-by-ordem/{ordemProducao}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findObsByIdComposto(@PathVariable("ordemProducao") int ordemProducao) {
		return confeccaoService.findAllPacotesOrdem(ordemProducao);
	}
	
	@RequestMapping(value = "/delete-obs-by-id/{id}", method = RequestMethod.DELETE)
	public List<ConsultaObservacaoOrdemPacote> deleteObservacaoById(@PathVariable("id") String id) {
		confeccaoService.deleteObservacaoById(id);
		return confeccaoService.findAllObsWithQuantidade();
	}
}
