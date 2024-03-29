package br.com.live.comercial.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.body.BodyCapacidadeCotasVendas;
import br.com.live.comercial.service.CapacidadeCotasVendasService;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/cotas-vendas")
public class CapacidadeCotasVendasController {

	private CapacidadeCotasVendasService capacidadeCotasVendasService;

	public CapacidadeCotasVendasController(CapacidadeCotasVendasService capacidadeCotasVendasService) {
		this.capacidadeCotasVendasService = capacidadeCotasVendasService;
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public BodyCapacidadeCotasVendas findItens(@RequestBody BodyCapacidadeCotasVendas body) {
		return capacidadeCotasVendasService.findItens(body.periodoProgInicio, body.periodoProgFinal, body.periodoAnaliseInicio, body.periodoAnaliseFinal,
				ConteudoChaveNumerica.parseValueToString(body.colecoes),
				ConteudoChaveNumerica.parseValueToString(body.depositos));
	}
}
