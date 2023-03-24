package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.FinanceiroCustom;
import br.com.live.model.ConsultaTitulosComissao;
import br.com.live.service.FinanceiroService;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/financeiro")
public class FinanceiroController {
	
	private FinanceiroCustom financeiroCustom;
	private FinanceiroService financeiroService;
	
	@Autowired
	public FinanceiroController(FinanceiroCustom financeiroCustom, FinanceiroService financeiroService) {
		this.financeiroCustom = financeiroCustom;
		this.financeiroService = financeiroService;
	}
	
	@RequestMapping(value = "/find-all-representante/{codigo}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllRepresentantes(@PathVariable("codigo") int codigo) {
        return financeiroCustom.findAllRepresentantes(codigo);
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-analitico/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosAnalitico(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") int representante) {
        return financeiroService.encontraDataInicioCobrancaAtrasadas(mes, ano, representante);
    }

}
