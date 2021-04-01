package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.PlanoMestre;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.service.PrevisaoVendasService;
import br.com.live.util.BodyPrevisaoVendas;
import br.com.live.util.ParametrosPlanoMestre;

@RestController
@CrossOrigin
@RequestMapping("/previsao-vendas")
public class PrevisaoVendasController {

	@Autowired
	private PrevisaoVendasService previsaoVendasService;
	
	@RequestMapping(value = "/{colecao}", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendas> findPrevisaoByColecao (@PathVariable("colecao") int colecao) {						
		return previsaoVendasService.findPrevisaoByColecao(colecao);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public List<ConsultaPrevisaoVendas> salvarPrevisoes(@RequestBody BodyPrevisaoVendas body) {
		
		return previsaoVendasService.findPrevisaoByColecao(body.colecao); 
	}
	
}
