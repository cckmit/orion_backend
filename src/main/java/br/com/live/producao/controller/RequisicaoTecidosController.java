package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.producao.body.BodyRequisicaoTecidos;
import br.com.live.producao.custom.RequisicaoTecidosCustom;
import br.com.live.producao.entity.RequisicaoTecidos;
import br.com.live.producao.entity.RequisicaoTecidosItem;
import br.com.live.producao.model.ConsultaRequisicaoTecidosItem;
import br.com.live.producao.repository.RequisicaoTecidosItemRepository;
import br.com.live.producao.repository.RequisicaoTecidosRepository;
import br.com.live.producao.service.RequisicaoTecidosService;

@RestController
@CrossOrigin
@RequestMapping("/requisicao-tecidos")
public class RequisicaoTecidosController {

	private final RequisicaoTecidosService requisicaoTecidosService;
	private final RequisicaoTecidosRepository requisicaoTecidosRepository;
	private final RequisicaoTecidosItemRepository requisicaoTecidosItemRepository;
	private final RequisicaoTecidosCustom requisicaoTecidosCustom;

	@Autowired
	public RequisicaoTecidosController(RequisicaoTecidosService requisicaoTecidosService,
			RequisicaoTecidosRepository requisicaoTecidosRepository,
			RequisicaoTecidosItemRepository requisicaoTecidosItemRepository,
			RequisicaoTecidosCustom requisicaoTecidosCustom) {
		this.requisicaoTecidosService = requisicaoTecidosService;
		this.requisicaoTecidosRepository = requisicaoTecidosRepository;
		this.requisicaoTecidosItemRepository = requisicaoTecidosItemRepository;
		this.requisicaoTecidosCustom = requisicaoTecidosCustom;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<RequisicaoTecidos> findAllRequisicoes() {
		return requisicaoTecidosRepository.findAll();
	}

	@RequestMapping(value = "liberadas/{consideraConfirmadas}", method = RequestMethod.GET)
	public List<RequisicaoTecidos> findAllRequisicoesLiberadas(
			@PathVariable("consideraConfirmadas") boolean consideraConfirmadas) {
		if (consideraConfirmadas)
			return requisicaoTecidosRepository.findByNotSituacaoDigitada();
		return requisicaoTecidosRepository.findBySituacao(1);
	}

	@RequestMapping(value = "/capa-itens/{idRequisicao}", method = RequestMethod.GET)
	public BodyRequisicaoTecidos findRequisicaoByIdRequisicao(@PathVariable("idRequisicao") long idRequisicao) {
		BodyRequisicaoTecidos body = new BodyRequisicaoTecidos();
		body.requisicaoTecidos = requisicaoTecidosRepository.findById(idRequisicao);
		body.requisicaoTecidosItens = requisicaoTecidosCustom.findItensByIdRequisicao(idRequisicao);
		return body;
	}

	@RequestMapping(value = "/item/{idRequisicaoItem}", method = RequestMethod.GET)
	public RequisicaoTecidosItem findRequisicaoItemByIdItem(@PathVariable("idRequisicaoItem") long idRequisicaoItem) {
		return requisicaoTecidosItemRepository.findById(idRequisicaoItem);
	}

	@RequestMapping(value = "/itens/{idRequisicao}", method = RequestMethod.GET)
	public List<ConsultaRequisicaoTecidosItem> findRequisicaoItensByIdRequisicao(
			@PathVariable("idRequisicao") long idRequisicao) {
		return requisicaoTecidosCustom.findItensByIdRequisicao(idRequisicao);
	}
	
	@RequestMapping(value = "/liberar/{idRequisicao}", method = RequestMethod.GET)
	public RequisicaoTecidos liberarRequisicao(
			@PathVariable("idRequisicao") long idRequisicao) {
		return requisicaoTecidosService.liberarRequisicao(idRequisicao);
	}

	@RequestMapping(value = "/salvar-requisicao", method = RequestMethod.POST)
	public RequisicaoTecidos saveRequisicao(@RequestBody BodyRequisicaoTecidos body) {
		return requisicaoTecidosService.saveRequisicao(body.requisicaoTecidos.id, body.requisicaoTecidos.descricao,
				body.requisicaoTecidos.situacao, body.requisicaoTecidos.usuario);
	}

	@RequestMapping(value = "/salvar-item", method = RequestMethod.POST)
	public List<ConsultaRequisicaoTecidosItem> saveRequisicaoItem(@RequestBody BodyRequisicaoTecidos body) {
		requisicaoTecidosService.saveRequisicaoItem(body.requisicaoTecidosItem.id,
				body.requisicaoTecidosItem.idRequisicao, body.requisicaoTecidosItem.nivel,
				body.requisicaoTecidosItem.grupo, body.requisicaoTecidosItem.sub, body.requisicaoTecidosItem.item,
				body.requisicaoTecidosItem.alternativa, body.requisicaoTecidosItem.roteiro,
				body.requisicaoTecidosItem.quantidade, body.requisicaoTecidosItem.observacao);
		return requisicaoTecidosCustom.findItensByIdRequisicao(body.requisicaoTecidosItem.idRequisicao);
	}

	@RequestMapping(value = "/confirmar", method = RequestMethod.POST)
	public List<RequisicaoTecidos> confirmarRequisicao(@RequestBody BodyRequisicaoTecidos body) {
		requisicaoTecidosService.confirmarRequisicao(body.idRequisicao);
		if (body.consideraConfirmados)
			return requisicaoTecidosRepository.findByNotSituacaoDigitada();
		return requisicaoTecidosRepository.findBySituacao(1);
	}

	@RequestMapping(value = "/requisicao/{idRequisicao}", method = RequestMethod.DELETE)
	public List<RequisicaoTecidos> deleteRequisicao(@PathVariable("idRequisicao") long idRequisicao) {
		requisicaoTecidosService.deleteRequisicao(idRequisicao);
		return requisicaoTecidosRepository.findAll();
	}

	@RequestMapping(value = "/requisicao-item/{idRequisicao}/{idRequisicaoItem}", method = RequestMethod.DELETE)
	public List<ConsultaRequisicaoTecidosItem> deleteRequisicaoItem(@PathVariable("idRequisicao") long idRequisicao,
			@PathVariable("idRequisicaoItem") long idRequisicaoItem) {
		requisicaoTecidosService.deleteRequisicaoItem(idRequisicaoItem);
		return requisicaoTecidosCustom.findItensByIdRequisicao(idRequisicao);
	}
}