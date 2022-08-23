package br.com.live.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyComercial;
import br.com.live.custom.ComercialCustom;

@RestController
@CrossOrigin
@RequestMapping("/comercial")
public class ComercialController {
	
	
	private ComercialCustom comercialCustom;
	
	@Autowired
	public ComercialController(ComercialCustom comercialCustom) {
		this.comercialCustom = comercialCustom;
	}
	
	@RequestMapping(value = "/save-envio-produtos-e-commerce", method = RequestMethod.POST)
    public void gravarEnvioProdEcommerce(@RequestBody BodyComercial body) {
		comercialCustom.gravaEnvioProdEcommerce(body.produto);
	}
}