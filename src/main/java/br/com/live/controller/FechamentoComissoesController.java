package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyExpedicao;
import br.com.live.body.BodyFinanceiro;
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
	
	@RequestMapping(value = "/find-titulos-atrasado-analitico", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosAnalitico(@RequestBody BodyFinanceiro body) {
        return financeiroService.findTitulosAtrasadosAnalitico(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-sintetico", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findTitulosAtrasadosSintetico(@RequestBody BodyFinanceiro body) {
        return financeiroService.findTitulosAtrasadosSintetico(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-lancamentos-faturamento", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findLancamentosFaturamento(@RequestBody BodyFinanceiro body) {
        return financeiroService.findLancamentosFaturamento(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-lancamentos-baixa-titulos", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findLancamentosBaixaTitulos(@RequestBody BodyFinanceiro body) {
        return financeiroService.findLancamentosBaixaTitulos(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-bonus", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findBonusPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findBonusPorRepresentante(body.mes, body.ano, body.listRepresentante, body.estacao);
    }
	
	@RequestMapping(value = "/find-devolucao", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findDevolucaoPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findDevolucaoPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-lancamentos-manuais", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findLanctoManuaisPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findLanctoManuaisPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-totais-lancamentos-manuais", method = RequestMethod.POST)
    public List<ConsultaTitulosComissao> findTotaisLanctoManuaisPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findTotaisLanctoManuaisPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }

}
