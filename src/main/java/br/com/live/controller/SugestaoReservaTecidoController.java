package br.com.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodySugestaoReservaTecidos;
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

	@RequestMapping(value = "/calcular", method = RequestMethod.POST)
	public SugestaoReservaTecidos gerarPreOrdens(@RequestBody BodySugestaoReservaTecidos body) {
		return sugestaoReservaTecidoService.calcularSugestaoReserva(ConteudoChaveNumerica.parseValueToString(body.planosMestres),
				ConteudoChaveNumerica.parseValueToString(body.embarques),
				ConteudoChaveAlfaNum.parseValueToString(body.referencias),
				ConteudoChaveNumerica.parseValueToString(body.depositos),
				body.priorizacao);		
	}
}