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
import br.com.live.model.SugestaoReservaTecidos;
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

	@RequestMapping(value = "/qtde-pc-liberada-dia/{idUsuario}", method = RequestMethod.GET)
	public Integer findQtdePecasLiberadasDia(@PathVariable("idUsuario") long idUsuario) {
		return sugestaoReservaMaterialService.findQtdePecasLiberadasDia(idUsuario);				
	}	
	
	@RequestMapping(value = "/calcular", method = RequestMethod.POST)
	public SugestaoReservaTecidos calcular(@RequestBody BodySugestaoReservaMateriais body) {
		return sugestaoReservaMaterialService.calcularSugestaoReservaPorOrdem(ConteudoChaveAlfaNum.parseValueToListString(body.camposSelParaPriorizacao),body.periodoInicio, body.periodoFim, 
				ConteudoChaveNumerica.parseValueToString(body.embarques),
				ConteudoChaveAlfaNum.parseValueToString(body.referencias), 
				ConteudoChaveNumerica.parseValueToString(body.estagios), 
				ConteudoChaveNumerica.parseValueToString(body.artigos), 
				ConteudoChaveAlfaNum.parseValueToString(body.tecidos), 
				ConteudoChaveNumerica.parseValueToString(body.depositosTecidos), 
				ConteudoChaveNumerica.parseValueToString(body.depositosAviamentos),
				body.isSomenteFlat, body.isDiretoCostura, body.isOrdensSemTecido, body.percentualMinimoAtender, body.regraReserva);		
	}
	
	@RequestMapping(value = "/liberar", method = RequestMethod.POST)
	public void liberar(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.liberarProducao(body.listaOrdensLiberar, body.listaTecidosReservar, false, body.idUsuarioOrion);
	}

	@RequestMapping(value = "/liberar-urgente", method = RequestMethod.POST)
	public void liberarUrgente(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.liberarProducao(body.listaOrdensLiberar, body.listaTecidosReservar, true, body.idUsuarioOrion);
	}	
	
	@RequestMapping(value = "/gravar-lembrete", method = RequestMethod.POST)
	public void gravarLembrete(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.gravarLembrete(body.listaOrdensComLembrete);
	}		
	
	@RequestMapping(value = "/gravar-observacao-op", method = RequestMethod.POST)
	public void gravarObservacaoOP(@RequestBody BodySugestaoReservaMateriais body) {
		sugestaoReservaMaterialService.gravarObservacaoOP(body.listaOrdensComObservacao);
	}		
}