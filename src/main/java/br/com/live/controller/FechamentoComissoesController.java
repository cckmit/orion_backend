package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.FechamentoComissaoCustom;
import br.com.live.model.ConsultaTitulosComissao;
import br.com.live.service.FechamentoComissaoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/fechamento-comissoes")
public class FechamentoComissoesController {
	
	private FechamentoComissaoCustom financeiroCustom;
	private FechamentoComissaoService financeiroService;
	
	@Autowired
	public FechamentoComissoesController(FechamentoComissaoCustom financeiroCustom, FechamentoComissaoService financeiroService) {
		this.financeiroCustom = financeiroCustom;
		this.financeiroService = financeiroService;
	}
	
	@RequestMapping(value = "/find-all-representante/{representante}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllRepresentantes(@PathVariable("representante") String representante) {
        return financeiroCustom.findAllRepresentantes(representante);
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-analitico/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosAnalitico(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante) {
        return financeiroService.findTitulosAtrasadosAnalitico(mes, ano, representante);
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-sintetico/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosSintetico(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante) {
        return financeiroService.findTitulosAtrasadosSintetico(mes, ano, representante);
    }

}
