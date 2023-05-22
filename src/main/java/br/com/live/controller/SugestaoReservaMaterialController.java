package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodySugestaoReservaMateriais;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaConfigArtigos;
import br.com.live.model.SugestaoReservaMateriais;
import br.com.live.service.SugestaoReservaMaterialService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/sugestao-reserva-material")
public class SugestaoReservaMaterialController {

	@Autowired
	private SugestaoReservaMaterialService sugestaoReservaMaterialService;

	@RequestMapping(value = "/tecidos", method = RequestMethod.GET)
	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		return sugestaoReservaMaterialService.findTecidosEmOrdensParaLiberacao();
	}

	@RequestMapping(value = "/referencias", method = RequestMethod.GET)
	public List<Produto> findReferenciasEmOrdensParaLiberacao() {
		return sugestaoReservaMaterialService.findReferenciasEmOrdensParaLiberacao();
	}

	@RequestMapping(value = "/qtde-pc-liberada-dia", method = RequestMethod.GET)
	public Integer findQtdePecasLiberadasDia() {
		return sugestaoReservaMaterialService.findQtdePecasLiberadasDia();
	}

	@RequestMapping(value = "/qtde-pc-liberada-dia-artigos", method = RequestMethod.GET)
	public BodySugestaoReservaMateriais findQtdePecasLiberadasDiaPorArtigos() {
		return sugestaoReservaMaterialService.findQtdePecasLiberadasDiaPorArtigos();		
	}
	
	@RequestMapping(value = "/calcular", method = RequestMethod.POST)
	public SugestaoReservaMateriais calcular(@RequestBody BodySugestaoReservaMateriais body) {
		return sugestaoReservaMaterialService.calcularSugestaoReservaPorOrdem(
				ConteudoChaveAlfaNum.parseValueToListString(body.camposSelParaPriorizacao), body.periodoInicio,
				body.periodoFim, ConteudoChaveNumerica.parseValueToString(body.embarques),
				ConteudoChaveAlfaNum.parseValueToString(body.referencias),
				ConteudoChaveNumerica.parseValueToString(body.estagios),
				ConteudoChaveNumerica.parseValueToString(body.artigos),
				ConteudoChaveAlfaNum.parseValueToString(body.tecidos),
				ConteudoChaveNumerica.parseValueToString(body.depositosTecidos),
				ConteudoChaveNumerica.parseValueToString(body.depositosAviamentos), body.isSomenteFlat,
				body.isDiretoCostura, body.isOrdensSemTecido, body.percentualMinimoAtender, body.regraReserva, body.isMostruario);
	}

	@RequestMapping(value = "/liberar", method = RequestMethod.POST)
	public void liberar(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.liberarProducao(body.listaOrdensLiberar, body.listaMateriaisReservar, false,
				body.idUsuarioOrion);
	}

	@RequestMapping(value = "/liberar-urgente", method = RequestMethod.POST)
	public void liberarUrgente(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.liberarProducao(body.listaOrdensLiberar, body.listaMateriaisReservar, true,
				body.idUsuarioOrion);
	}

	@RequestMapping(value = "/gravar-lembrete", method = RequestMethod.POST)
	public void gravarLembrete(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.gravarLembrete(body.listaOrdensComLembrete);
	}

	@RequestMapping(value = "/gravar-observacao-op", method = RequestMethod.POST)
	public void gravarObservacaoOP(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.gravarObservacaoOP(body.listaOrdensComObservacao);
	}

	@RequestMapping(value = "/gravar-config-artigos", method = RequestMethod.POST)
	public void gravarConfigArtigos(@RequestBody BodySugestaoReservaMateriais body) {		
		sugestaoReservaMaterialService.gravarConfigArtigos(1, body.descricao1, body.meta1,
				ConteudoChaveNumerica.parseValueToString(body.artigos1), body.metaMinutos1);
		sugestaoReservaMaterialService.gravarConfigArtigos(2, body.descricao2, body.meta2,
				ConteudoChaveNumerica.parseValueToString(body.artigos2), body.metaMinutos2);
		sugestaoReservaMaterialService.gravarConfigArtigos(3, body.descricao3, body.meta3,
				ConteudoChaveNumerica.parseValueToString(body.artigos3), body.metaMinutos3);
		sugestaoReservaMaterialService.gravarConfigArtigos(4, body.descricao4, body.meta4,
				ConteudoChaveNumerica.parseValueToString(body.artigos4), body.metaMinutos4);
		sugestaoReservaMaterialService.gravarConfigArtigos(5, body.descricao5, body.meta5,
				ConteudoChaveNumerica.parseValueToString(body.artigos5), body.metaMinutos5);
		sugestaoReservaMaterialService.gravarConfigArtigos(6, body.descricao6, body.meta6,
				ConteudoChaveNumerica.parseValueToString(body.artigos6), body.metaMinutos6);
		sugestaoReservaMaterialService.gravarConfigArtigos(7, body.descricao7, body.meta7,
				ConteudoChaveNumerica.parseValueToString(body.artigos7), body.metaMinutos7);
		sugestaoReservaMaterialService.gravarConfigArtigos(8, body.descricao8, body.meta8,
				ConteudoChaveNumerica.parseValueToString(body.artigos8), body.metaMinutos8);
		sugestaoReservaMaterialService.gravarConfigArtigos(9, body.descricao9, body.meta9,
				ConteudoChaveNumerica.parseValueToString(body.artigos9), body.metaMinutos9);
	}

	@RequestMapping(value = "/find-config-artigos", method = RequestMethod.GET)
	public BodySugestaoReservaMateriais findConfigArtigos() {		
		return sugestaoReservaMaterialService.findConfiguracoesPorArtigos();
	}
}