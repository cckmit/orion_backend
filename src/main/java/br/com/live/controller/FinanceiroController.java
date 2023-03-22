package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.FinanceiroCustom;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/financeiro")
public class FinanceiroController {
	
	private FinanceiroCustom financeiroCustom;
	
	@Autowired
	public FinanceiroController(FinanceiroCustom financeiroCustom) {
		this.financeiroCustom = financeiroCustom;
	}
	
	@RequestMapping(value = "/find-all-representante/{cod}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllRepresentantes(@PathVariable("cod") int cod) {
        return financeiroCustom.findAllRepresentantes(cod);
    }

}
