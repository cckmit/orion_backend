package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodySugestaoReservaTecidos;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaTecidos;
import br.com.live.service.SugestaoReservaTecidoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/sugestao-reserva-tecido")
public class SugestaoReservaTecidoController {

	@Autowired
	private SugestaoReservaTecidoService sugestaoReservaTecidoService;

	@RequestMapping(value = "/tecidos", method = RequestMethod.GET)
	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		return sugestaoReservaTecidoService.findTecidosEmOrdensParaLiberacao();
	}
	
	@RequestMapping(value = "/referencias", method = RequestMethod.GET)
	public List<Produto> findReferenciasEmOrdensParaLiberacao() {
		return sugestaoReservaTecidoService.findReferenciasEmOrdensParaLiberacao();				
	}	

	@RequestMapping(value = "/calcular-ordens", method = RequestMethod.POST)
	public SugestaoReservaTecidos gerarPreOrdens(@RequestBody BodySugestaoReservaTecidos body) {
		return sugestaoReservaTecidoService.calcularSugestaoReservaPorOrdem(ConteudoChaveAlfaNum.parseValueToListString(body.camposSelParaPriorizacao),body.periodoInicio, body.periodoFim, 
				ConteudoChaveNumerica.parseValueToString(body.embarques),
				ConteudoChaveAlfaNum.parseValueToString(body.referencias), 
				ConteudoChaveNumerica.parseValueToString(body.estagios), 
				ConteudoChaveNumerica.parseValueToString(body.artigos), 
				ConteudoChaveAlfaNum.parseValueToString(body.tecidos), 
				ConteudoChaveNumerica.parseValueToString(body.depositos), 
				body.isSomenteFlat, body.percentualMinimoAtender);		
	}	
}