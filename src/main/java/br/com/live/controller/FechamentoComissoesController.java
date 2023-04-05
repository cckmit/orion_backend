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
	
	@RequestMapping(value = "/find-all-estacoes", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllEstacoes() {
        return financeiroService.findAllEstacoes();
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-analitico/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosAnalitico(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante) {
        return financeiroService.findTitulosAtrasadosAnalitico(mes, ano, representante);
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-sintetico/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosSintetico(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante) {
        return financeiroService.findTitulosAtrasadosSintetico(mes, ano, representante);
    }
	
	@RequestMapping(value = "/find-lancamentos-faturamento/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findLancamentosFaturamento(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante) {
        return financeiroService.findLancamentosFaturamento(mes, ano, representante);
    }
	
	@RequestMapping(value = "/find-lancamentos-baixa-titulos/{mes}/{ano}/{representante}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findLancamentosBaixaTitulos(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante) {
        return financeiroService.findLancamentosBaixaTitulos(mes, ano, representante);
    }
	
	@RequestMapping(value = "/find-bonus/{mes}/{ano}/{representante}/{estacao}", method = RequestMethod.GET)
    public List<ConsultaTitulosComissao> findBonusPorRepresentante(@PathVariable("mes") int mes, @PathVariable("ano") int ano, @PathVariable("representante") String representante, 
    		@PathVariable("estacao") String estacao) {
        return financeiroService.findBonusPorRepresentante(mes, ano, representante, estacao);
    }

}
