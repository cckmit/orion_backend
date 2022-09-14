package br.com.live.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.live.body.BodyConfeccao;
import br.com.live.custom.ConfeccaoCustom;
import br.com.live.entity.Restricoes;
import br.com.live.model.ConsultaRestricoesRolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyObservacaoPorOp;
import br.com.live.body.BodyTipoObservacao;
import br.com.live.entity.MetasProducao;
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
	private ConfeccaoCustom confeccaoCustom;

	@Autowired
	public ConfeccaoController(ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository,
			TipoObservacaoRepository tipoObservacaoRepository, ConfeccaoService confeccaoService, ConfeccaoCustom confeccaoCustom) {
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoService = confeccaoService;
		this.confeccaoCustom = confeccaoCustom;
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

	@RequestMapping(value = "/find-all-restricoes", method = RequestMethod.GET)
	public List<Restricoes> findAllRestricoes() {
		return confeccaoService.findAllRestricoes();
	}

	@RequestMapping(value = "/find-restricoes-by-id/{idRestricao}", method = RequestMethod.GET)
	public Restricoes findAllRestricoes(@PathVariable("idRestricao") long idRestricao) {
		return confeccaoService.findRestricaoById(idRestricao);
	}

	@RequestMapping(value = "/save-restricoes", method = RequestMethod.POST)
	public List<Restricoes> saveRestricoes(@RequestBody BodyConfeccao body) {
		confeccaoService.saveRestricoes(body.id, body.descricao);
		return confeccaoService.findAllRestricoes();
	}

	@RequestMapping(value = "/delete-restricoes-by-id/{idRestricao}", method = RequestMethod.DELETE)
	public List<Restricoes> deleteRestricao(@PathVariable("idRestricao") long idRestricao) {
		confeccaoService.deleteByIdRestricao(idRestricao);
		return confeccaoService.findAllRestricoes();
	}

	@RequestMapping(value = "/delete-restricoes-rolo-by-id/{idSeq}", method = RequestMethod.DELETE)
	public List<ConsultaRestricoesRolo> deleteRestricaoRolo(@PathVariable("idSeq") long idSeq) {
		confeccaoService.deleteBySeqRestricao(idSeq);
		return confeccaoCustom.findAllRestricoesPorRolo();
	}

	@RequestMapping(value = "/find-options-restricao", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findOptionRestricao() {
		return confeccaoCustom.findOptionsRestricao();
	}

	@RequestMapping(value = "/find-option-leitor-ordens/{leitor}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findOrdensLeitor(@PathVariable("leitor") String leitor) {
		return confeccaoCustom.findOptionLeitorOrdensBeneficiamento(leitor);
	}

	@RequestMapping(value = "/find-option-leitor-rolos/{leitor}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findRolosLeitor(@PathVariable("leitor") String leitor) {
		return confeccaoCustom.findOptionLeitorRolos(leitor);
	}

	@RequestMapping(value = "/consulta-restricoes-teste", method = RequestMethod.GET)
		public List<ConsultaRestricoesRolo> consultaRestricoesRolos() {
		return confeccaoCustom.findAllRestricoesPorRolo();
	}

	@RequestMapping(value = "/save-restricoes-por-rolo", method = RequestMethod.POST)
	public void saveRestricoesPorRolo(@RequestBody BodyConfeccao body) {
		confeccaoService.proxySaveRestricoesRolo(body.rolos, body.restricoes);
	}

	@RequestMapping(value = "/save-restricoes-por-ordens", method = RequestMethod.POST)
	public void saveRestricoesPorOrdens(@RequestBody BodyConfeccao body) {
		confeccaoService.proxySaveRestricoesPorOrdemBenef(body.ordens, body.restricoes);
	}

	@RequestMapping(value = "/find-dias-uteis", method = RequestMethod.POST)
	public int findDiasUteis(@RequestBody BodyConfeccao body) {
		return confeccaoCustom.findDiasUteis(body.dataMeta);
	}
	
	@RequestMapping(value = "/find-metas-producao/{idMeta}", method = RequestMethod.GET)
    public List<MetasProducao> findMetasProducao(@PathVariable("idMeta") String idMeta) {
    	return confeccaoCustom.findMetasProducao(idMeta);
    }
	
	@RequestMapping(value = "/save-meta-producao", method = RequestMethod.POST)
	public String saveMetaProducao(@RequestBody BodyConfeccao body) {
		return confeccaoService.saveMetaProducao(body.idMetaMes, body.mes, body.ano, body.codEstagio, body.metaMes, body.diasUteis, body.metaDiaria, body.metaAjustada);
		
	}
	
	@RequestMapping(value = "/save-meta-producao-semana", method = RequestMethod.POST)
	public long saveMetaSemana(@RequestBody BodyConfeccao body) {
		
		return confeccaoService.saveMetaSemana(body.idMetaSemana, body.idMes, body.nrSemana, body.diasUteis, body.dataInicio, body.dataFim, body.metaReal, body.metaRealTurno, body.metaAjustada, body.metaAjustadaTurno);
		
	}       
	
}
