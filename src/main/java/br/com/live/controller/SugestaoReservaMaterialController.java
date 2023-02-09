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
		
		List<SugestaoReservaConfigArtigos> artigos = sugestaoReservaMaterialService.findConfigArtigos();
		int[] qtdesProduzidas = sugestaoReservaMaterialService.findQtdePecasLiberadasDiaPorArtigo();
		
		BodySugestaoReservaMateriais bodyRetorno = new BodySugestaoReservaMateriais(); 
		
		bodyRetorno.qtdeFlatProduzida = sugestaoReservaMaterialService.findQtdeFlatPecasLiberadasDia();
		bodyRetorno.qtdeOutros = qtdesProduzidas[0];
		bodyRetorno.qtdeProduzida1 = qtdesProduzidas[1];
		bodyRetorno.qtdeProduzida2 = qtdesProduzidas[2];
		bodyRetorno.qtdeProduzida3 = qtdesProduzidas[3];
		bodyRetorno.qtdeProduzida4 = qtdesProduzidas[4];
		bodyRetorno.qtdeProduzida5 = qtdesProduzidas[5];
		bodyRetorno.qtdeProduzida6 = qtdesProduzidas[6];
		bodyRetorno.qtdeProduzida7 = qtdesProduzidas[7];
		bodyRetorno.qtdeProduzida8 = qtdesProduzidas[8];
		bodyRetorno.qtdeProduzida9 = qtdesProduzidas[9];
		
		for (SugestaoReservaConfigArtigos artigo : artigos) {
			if (artigo.getColuna() == 1) {
				bodyRetorno.descricao1 = artigo.getDescricao();
				bodyRetorno.meta1 = artigo.getMeta();
			} else if (artigo.getColuna() == 2) {
				bodyRetorno.descricao2 = artigo.getDescricao();
				bodyRetorno.meta2 = artigo.getMeta();
			} else if (artigo.getColuna() == 3) {
				bodyRetorno.descricao3 = artigo.getDescricao();
				bodyRetorno.meta3 = artigo.getMeta();
			} else if (artigo.getColuna() == 4) {
				bodyRetorno.descricao4 = artigo.getDescricao();
				bodyRetorno.meta4 = artigo.getMeta();
			} else if (artigo.getColuna() == 5) {
				bodyRetorno.descricao5 = artigo.getDescricao();
				bodyRetorno.meta5 = artigo.getMeta();
			} else if (artigo.getColuna() == 6) {
				bodyRetorno.descricao6 = artigo.getDescricao();
				bodyRetorno.meta6 = artigo.getMeta();
			} else if (artigo.getColuna() == 7) {
				bodyRetorno.descricao7 = artigo.getDescricao();
				bodyRetorno.meta7 = artigo.getMeta();
			} else if (artigo.getColuna() == 8) {
				bodyRetorno.descricao8 = artigo.getDescricao();
				bodyRetorno.meta8 = artigo.getMeta();
			} else if (artigo.getColuna() == 9) {
				bodyRetorno.descricao9 = artigo.getDescricao();
				bodyRetorno.meta9 = artigo.getMeta();
			}
		}
		
		return bodyRetorno;
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
				body.isDiretoCostura, body.isOrdensSemTecido, body.percentualMinimoAtender, body.regraReserva);
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
				ConteudoChaveNumerica.parseValueToString(body.artigos1));
		sugestaoReservaMaterialService.gravarConfigArtigos(2, body.descricao2, body.meta2,
				ConteudoChaveNumerica.parseValueToString(body.artigos2));
		sugestaoReservaMaterialService.gravarConfigArtigos(3, body.descricao3, body.meta3,
				ConteudoChaveNumerica.parseValueToString(body.artigos3));
		sugestaoReservaMaterialService.gravarConfigArtigos(4, body.descricao4, body.meta4,
				ConteudoChaveNumerica.parseValueToString(body.artigos4));
		sugestaoReservaMaterialService.gravarConfigArtigos(5, body.descricao5, body.meta5,
				ConteudoChaveNumerica.parseValueToString(body.artigos5));
		sugestaoReservaMaterialService.gravarConfigArtigos(6, body.descricao6, body.meta6,
				ConteudoChaveNumerica.parseValueToString(body.artigos6));
		sugestaoReservaMaterialService.gravarConfigArtigos(7, body.descricao7, body.meta7,
				ConteudoChaveNumerica.parseValueToString(body.artigos7));
		sugestaoReservaMaterialService.gravarConfigArtigos(8, body.descricao8, body.meta8,
				ConteudoChaveNumerica.parseValueToString(body.artigos8));
		sugestaoReservaMaterialService.gravarConfigArtigos(9, body.descricao9, body.meta9,
				ConteudoChaveNumerica.parseValueToString(body.artigos9));
	}

	@RequestMapping(value = "/find-config-artigos", method = RequestMethod.GET)
	public BodySugestaoReservaMateriais findConfigArtigos() {
		List<SugestaoReservaConfigArtigos> listaConfigArtigos = sugestaoReservaMaterialService.findConfigArtigos();
		BodySugestaoReservaMateriais bodyRetorno = new BodySugestaoReservaMateriais();

		for (SugestaoReservaConfigArtigos configuracao : listaConfigArtigos) {			
			if (configuracao.getColuna() == 1) {
				bodyRetorno.artigos1 = configuracao.getListaArtigos();
				bodyRetorno.descricao1 = configuracao.getDescricao();
				bodyRetorno.meta1 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 2) {
				bodyRetorno.artigos2 = configuracao.getListaArtigos();
				bodyRetorno.descricao2 = configuracao.getDescricao();
				bodyRetorno.meta2 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 3) {
				bodyRetorno.artigos3 = configuracao.getListaArtigos();
				bodyRetorno.descricao3 = configuracao.getDescricao();
				bodyRetorno.meta3 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 4) {
				bodyRetorno.artigos4 = configuracao.getListaArtigos();
				bodyRetorno.descricao4 = configuracao.getDescricao();
				bodyRetorno.meta4 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 5) {
				bodyRetorno.artigos5 = configuracao.getListaArtigos();
				bodyRetorno.descricao5 = configuracao.getDescricao();
				bodyRetorno.meta5 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 6) {
				bodyRetorno.artigos6 = configuracao.getListaArtigos();
				bodyRetorno.descricao6 = configuracao.getDescricao();
				bodyRetorno.meta6 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 7) {
				bodyRetorno.artigos7 = configuracao.getListaArtigos();
				bodyRetorno.descricao7 = configuracao.getDescricao();
				bodyRetorno.meta7 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 8) {
				bodyRetorno.artigos8 = configuracao.getListaArtigos();
				bodyRetorno.descricao8 = configuracao.getDescricao();
				bodyRetorno.meta8 = configuracao.getMeta();
			} else if (configuracao.getColuna() == 9) {
				bodyRetorno.artigos9 = configuracao.getListaArtigos();
				bodyRetorno.descricao9 = configuracao.getDescricao();
				bodyRetorno.meta9 = configuracao.getMeta();
			}
		}
		return bodyRetorno;
	}
}