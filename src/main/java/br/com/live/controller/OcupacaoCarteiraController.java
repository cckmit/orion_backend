package br.com.live.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyCapacidadeCotasVendas;
import br.com.live.body.BodyOcupacaoCarteira;
import br.com.live.service.CapacidadeCotasVendasService;
import br.com.live.service.OcupacaoCarteiraService;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/ocupacao-carteira")
public class OcupacaoCarteiraController {

	private OcupacaoCarteiraService ocupacaoCarteiraService;

	public OcupacaoCarteiraController(OcupacaoCarteiraService ocupacaoCarteiraService) {
		this.ocupacaoCarteiraService = ocupacaoCarteiraService;
	}

	@GetMapping("/consultar")	
	public BodyOcupacaoCarteira consultar(@RequestBody BodyOcupacaoCarteira body) {
		return ocupacaoCarteiraService.consultar(body.mes, body.ano, body.tipoOrcamento, body.pedidosDisponibilidade, body.pedidosProgramados, body.pedidosProntaEntrega);
	}
}
