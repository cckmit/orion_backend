package br.com.live.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.live.body.BodyOcupacaoCarteira;
import br.com.live.service.OcupacaoCarteiraService;

@RestController
@CrossOrigin
@RequestMapping("/ocupacao-carteira")
public class OcupacaoCarteiraController {

	private OcupacaoCarteiraService ocupacaoCarteiraService;

	public OcupacaoCarteiraController(OcupacaoCarteiraService ocupacaoCarteiraService) {
		this.ocupacaoCarteiraService = ocupacaoCarteiraService;
	}

	@PostMapping("/consultar")	
	public BodyOcupacaoCarteira consultar(@RequestBody BodyOcupacaoCarteira body) {
		return ocupacaoCarteiraService.consultar(body.valorResumir, body.mes, body.ano, body.tipoOrcamento, body.pedidosDisponibilidade, body.pedidosProgramados, body.pedidosProntaEntrega);
	}
}
