package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyPoliticaVendas;
import br.com.live.custom.PoliticaVendasCustom;
import br.com.live.model.ConsultaPoliticaVendas;
import br.com.live.model.DivergenciasPoliticaVendas;
import br.com.live.model.RegrasPoliticaVendas;
import br.com.live.service.PoliticaVendasService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/politica-vendas")
public class PoliticaVendasController {
	
	private PoliticaVendasCustom politicaVendasCustom;
	private PoliticaVendasService politicaVendasService;
	
	@Autowired
	public PoliticaVendasController(PoliticaVendasCustom politicaVendasCustom, PoliticaVendasService politicaVendasService) {
		this.politicaVendasCustom = politicaVendasCustom;
		this.politicaVendasService = politicaVendasService;
		
	}
	// Carregar todas Formas de Pagamento
	@RequestMapping(value = "/find-all-forma-pagamento", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllFormaPagamento() {
        return politicaVendasCustom.findFormaPagamentos();
    }
	// Carregar todos Codigos de Portador
	@RequestMapping(value = "/find-all-portador", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllPortador() {
        return politicaVendasCustom.findPortador();
    }
	// Carregar todos Codigos de Funcionários
	@RequestMapping(value = "/find-all-cod-funcionario", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllCodFuncionario() {
        return politicaVendasCustom.findCodFuncionario();
    }
	// Carrregar todas Natureza da Operação
	@RequestMapping(value = "/find-all-natureza-operacao", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllNaturezaOperacao() {
        return politicaVendasCustom.findNaturezaOperacao();
    }
	// Carrregar todas Tabela de Preços
	@RequestMapping(value = "/find-all-tabela-preco-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllAsyncTabelas(@PathVariable("leitor") String leitor) {
          return politicaVendasService.findAllTabelasAsync(leitor);
    }
	// Carrregar todos os CNPJ dos Cliente de tipo Cliente 4
	@RequestMapping(value = "/find-all-cnpj/{cnpj}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllCnpj(@PathVariable("cnpj") String cnpj) {
        return politicaVendasCustom.findCnpj(cnpj);
    }
	// Carrregar todos os Depósitos
	@RequestMapping(value = "/find-all-deposito", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllDepositos() {
        return politicaVendasCustom.findDeposito();
    }
	// Carrregar todos os Depósitos
	@RequestMapping(value = "/find-all-tipo-cliente", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllTipoDeCliente() {
        return politicaVendasCustom.findAllTipoDeCliente();
	}
	// Carrregar todos os Depósitos
	@RequestMapping(value = "/find-all-cond-pagamento", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllCondPgto() {
        return politicaVendasCustom.findCondPagamento();
	    }
	// Carrregar todos os Pedidos com Divergências
	@RequestMapping(value = "/find-pedidos-divergencias", method = RequestMethod.POST)
    public List<DivergenciasPoliticaVendas> findPedidosDivergencias(@RequestBody BodyPoliticaVendas body) {
        return politicaVendasService.findPedidosDivergencias(body.regra1, body.regra2, body.regra3, body.regra4, body.regra5, body.regra6, body.regra7, 
        		body.regra8, body.regra9, body.regra10);
	    }
	
	@RequestMapping(value = "/find-grupo-embarque-divergencias", method = RequestMethod.POST)
    public List<DivergenciasPoliticaVendas> findDivergenciasGrupoEmbarque(@RequestBody BodyPoliticaVendas body) {
        return politicaVendasService.findDivergenciasGrupoEmbarque(body.estacao);
    }
	
	// Carregar GRID Regra (Forma de Pagamento X Portador)	
	@RequestMapping(value = "/find-all-regra/{tipo}", method = RequestMethod.GET)
    public List<RegrasPoliticaVendas> findAllPoliticaVendas(@PathVariable("tipo") int tipo) {
        return politicaVendasCustom.findAllRegrasByTipo(tipo);
    }

    // Salvar Regra
    @RequestMapping(value = "/save-regra", method = RequestMethod.POST)
    public void saveRegra(@RequestBody BodyPoliticaVendas body) {                  
    	politicaVendasService.saveRegra(body.id, body.tipo, body.formaPagamento, body.portador, body.cnpj, body.codFuncionario, body.descCapa, body.tipoPedido, 
    			body.depositoItens, body.descMaxCliente, body.comissao, body.condPgto, body.tipoCliente, body.naturezaOperacao,  body.desconto, body.tabelaPreco);
    }
    
    @RequestMapping(value = "/delete-regra/{id}", method = RequestMethod.DELETE)
    public List<RegrasPoliticaVendas> deletePoliticaVendas(@PathVariable("id") int id) {                  
    	politicaVendasService.deleteRegraById(id);
        return politicaVendasCustom.findAllRegrasByTipo(id);
    }
    
    @RequestMapping(value = "/importar-regras", method = RequestMethod.POST)
    public ConsultaPoliticaVendas importarRegras(@RequestBody BodyPoliticaVendas body) {                  
    	return politicaVendasService.importarRegras(body.tabImportarRegras, body.tipo);	 
    }
    
}