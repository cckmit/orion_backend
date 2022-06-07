package br.com.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/suprimentos")
public class SuprimentoController {

	private SuprimentoCustom suprimentoCustom;
	private EstoqueProdutoCustom estoqueProdutoCustom;
	private EmpresaCustom empresaCustom;
	
	@Autowired
    public SuprimentoController (SuprimentoCustom suprimentoCustom, EstoqueProdutoCustom estoqueProdutoCustom, EmpresaCustom empresaCustom) {
		this.suprimentoCustom = suprimentoCustom;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.empresaCustom = empresaCustom;
	}

	
	
	
	
}
