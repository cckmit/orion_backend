package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyConsultaIntegFluxogama;
import br.com.live.custom.IntegracaoFluxogamaCustom;
import br.com.live.model.ConsultaReferenciasIntegradas;

@RestController
@CrossOrigin
@RequestMapping("/integ-fluxogama")
public class IntegracaoFluxogamaController {
	
	private IntegracaoFluxogamaCustom integracaoFluxogamaCustom;
	
	@Autowired
	public IntegracaoFluxogamaController(IntegracaoFluxogamaCustom integracaoFluxogamaCustom) {
		this.integracaoFluxogamaCustom = integracaoFluxogamaCustom;
	}
	
	@RequestMapping(value = "/find-refs-integradas", method = RequestMethod.POST)
    public List<ConsultaReferenciasIntegradas> findRefsIntegradas(@RequestBody BodyConsultaIntegFluxogama body) {
        return integracaoFluxogamaCustom.findRefsIntegradas(body.dataInicio, body.dataFim, body.referencia);
    }
}
