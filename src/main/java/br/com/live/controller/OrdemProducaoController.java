package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.service.OrdemProducaoService;
import br.com.live.util.BodyOrdemProducao;

@RestController
@CrossOrigin
@RequestMapping("/ordens-producao")
public class OrdemProducaoController {

	@Autowired
	private OrdemProducaoService ordemProducaoService;
	
	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public List<ConsultaPreOrdemProducao> gerar(@RequestBody BodyOrdemProducao body) {
		
		System.out.println("geracao de ordens de producao");
		
		return ordemProducaoService.gerarOrdens(body.idPlanoMestre, body.listaPreOrdens);		
	}
}
