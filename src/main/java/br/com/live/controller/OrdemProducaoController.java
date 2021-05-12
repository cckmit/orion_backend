package br.com.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.service.OrdemProducaoService;

@RestController
@CrossOrigin
@RequestMapping("/ordens-producao")
public class OrdemProducaoController {

	@Autowired
	private OrdemProducaoService ordemProducaoService;
	

	
	
}
