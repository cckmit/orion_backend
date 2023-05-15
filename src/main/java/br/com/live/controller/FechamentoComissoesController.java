package br.com.live.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyChamado;
import br.com.live.body.BodyContabilidade;
import br.com.live.body.BodyExpedicao;
import br.com.live.body.BodyFinanceiro;
import br.com.live.custom.FechamentoComissaoCustom;
import br.com.live.model.ConsultaFechamentoComissoes;
import br.com.live.model.RetornoLancamentoCont;
import br.com.live.service.FechamentoComissaoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import net.sf.jasperreports.engine.JRException;


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
    public List<ConsultaFechamentoComissoes> findTitulosAtrasadosAnalitico(@RequestBody BodyFinanceiro body) {
        return financeiroService.findTitulosAtrasadosAnalitico(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-titulos-atrasado-sintetico", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findTitulosAtrasadosSintetico(@RequestBody BodyFinanceiro body) {
        return financeiroService.findTitulosAtrasadosSintetico(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-lancamentos-faturamento", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findLancamentosFaturamento(@RequestBody BodyFinanceiro body) {
        return financeiroService.findLancamentosFaturamento(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-lancamentos-baixa-titulos", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findLancamentosBaixaTitulos(@RequestBody BodyFinanceiro body) {
        return financeiroService.findLancamentosBaixaTitulos(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-bonus", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findBonusPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findBonusPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-devolucao", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findDevolucaoPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findDevolucaoPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-lancamentos-manuais", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findLanctoManuaisPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findLanctoManuaisPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-totais-lancamentos-manuais", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findTotaisLanctoManuaisPorRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findTotaisLanctoManuaisPorRepresentante(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-mostruario-adquirido", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findMostruarioAdquirido(@RequestBody BodyFinanceiro body) {
        return financeiroService.findMostruarioAdquirido(body.mes, body.ano, body.listRepresentante);
    }
	
	@RequestMapping(value = "/find-mostruario-devolvido", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findMostruarioDevolvido(@RequestBody BodyFinanceiro body) {
        return financeiroService.findMostruarioDevolvido(body.listRepresentante, body.estacao);
    }
	
	@RequestMapping(value = "/find-mostruario-analitico", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findMostruarioAnalitico(@RequestBody BodyFinanceiro body) {
        return financeiroService.ExistsMostruarioDevolvido(body.listRepresentante, body.mes, body.ano, body.estacao);
    }
	
	@RequestMapping(value = "/find-mostruario-sintetico", method = RequestMethod.POST)
    public List<ConsultaFechamentoComissoes> findMostruarioSintetico(@RequestBody BodyFinanceiro body) {
        return financeiroService.findMostruarioSintetico(body.listRepresentante, body.estacao);
    }
	
	@RequestMapping(value = "/find-cargo-representante", method = RequestMethod.POST)
    public int findCargoRepresentante(@RequestBody BodyFinanceiro body) {
        return financeiroService.findCargoRepresentante(body.listRepresentante);
    }
	
	@RequestMapping(value = "/pagar-parcela-by-id", method = RequestMethod.POST)
    public int pagarParcelaMostruario(@RequestBody BodyFinanceiro body) {
        return financeiroService.pagarParcelaMostruario(body.id);
    }
	
	@RequestMapping(value = "/gerar-pdf", method = RequestMethod.POST)
    public String gerarPdfChamados(@RequestBody BodyFinanceiro body) throws JRException, FileNotFoundException {
        return financeiroService.gerarPdf(body.listRepresentante, body.listFechamento, body.listFechamento2, body.mes, body.ano, body.valorAReceber);
    }
	
	@RequestMapping(value = "/importar-devolucoes-mostruario", method = RequestMethod.POST)
    public void importarDevolucoesMostruario(@RequestBody BodyFinanceiro body) {                  
    	financeiroService.importarDevolucoesMostruario(body.listRepresentante, body.estacao, body.tabImportDevMostruario);	 
    }

}
