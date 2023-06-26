package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.custom.PedidoVendaCustom;
import br.com.live.comercial.custom.PoliticaVendasCustom;
import br.com.live.producao.custom.OrdemProducaoCustom;
import br.com.live.producao.custom.PainelPlanejamentoCustom;
import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/painel-lista-prioridades")
public class PainelListaPrioridadesController {

	private ProdutoCustom produtoCustom;
	private PoliticaVendasCustom politicaVendasCustom;
	private PedidoVendaCustom pedidoVendaCustom; 
	private OrdemProducaoCustom ordemProducaoCustom; 
	private PainelPlanejamentoCustom painelPlanejamentoCustom;
	
	@Autowired
	public PainelListaPrioridadesController(PainelPlanejamentoCustom painelPlanejamentoCustom, ProdutoCustom produtoCustom, PoliticaVendasCustom politicaVendasCustom, PedidoVendaCustom pedidoVendaCustom, OrdemProducaoCustom ordemProducaoCustom) {
		this.painelPlanejamentoCustom = painelPlanejamentoCustom;
		this.produtoCustom = produtoCustom;
		this.politicaVendasCustom = politicaVendasCustom;
		this.pedidoVendaCustom = pedidoVendaCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
	}	
	
	@GetMapping("/periodos")
    public List<ConteudoChaveNumerica> findPeriodos(){
        return painelPlanejamentoCustom.findAllPeriodosProducao();
    }
	
	@GetMapping("/depositos")
    public List<ConteudoChaveNumerica> findDepositos(){
        return painelPlanejamentoCustom.findAllDepositos();
    }

	@GetMapping("/colecoes")
    public List<ConteudoChaveNumerica> findColecoes(){
        return painelPlanejamentoCustom.findAllColecaoWithPermanentes();
    }
	
	@GetMapping("/linhas-produto")
    public List<ConteudoChaveNumerica> findLinhasProduto(){
        return painelPlanejamentoCustom.findAllLinhaProduto();
    }

	@GetMapping("/segmentos")
    public List<ConteudoChaveNumerica> findSegmentos(){
        return painelPlanejamentoCustom.findAllSegmento();
    }

	@GetMapping("/artigos")
    public List<ConteudoChaveNumerica> findArtigos(){
        return painelPlanejamentoCustom.findAllArtigo();
    }
	
	@GetMapping("/referencias/{search}")
    public List<ConteudoChaveAlfaNum> findReferencias(@PathVariable("search") String search){
        return produtoCustom.findAllReferenciasPecas(search);
    }

	@GetMapping("/tamanhos")
    public List<ConteudoChaveAlfaNum> findTamanhos(){
        return produtoCustom.findAllTamanhos();
    }
	
	@GetMapping("/cores")
    public List<ConteudoChaveAlfaNum> findCores(){
        return produtoCustom.findAllCores();
    }
	
	@GetMapping("/naturezas-operacao")
    public List<ConteudoChaveNumerica> findNaturezasOperacoes(){
        return politicaVendasCustom.findNaturezaOperacao();
    }
	
	@GetMapping("/situacoes-pedidos")
    public List<ConteudoChaveNumerica> findSituacoesPedidos(){
        return pedidoVendaCustom.findSituacoes();
    }

	@GetMapping("/faccoes")
    public List<ConteudoChaveNumerica> findFaccoes(){
        return ordemProducaoCustom.findFaccoes();
    }

	@GetMapping("/familias")
    public List<ConteudoChaveNumerica> findFamilias(){
        return ordemProducaoCustom.findFamiliasProducao();
    }

	@GetMapping("/pedidos-ordens")
    public List<ConteudoChaveNumerica> findPedidosOrdens(){
        return ordemProducaoCustom.findPedidosOrdens();
    }

	@GetMapping("/pedidos-comerciais/{search}")
    public List<ConteudoChaveNumerica> findPedidosComerciais(@PathVariable("search") int search){
        return pedidoVendaCustom.findPedidos(search);
    }

	@GetMapping("/numeros-interno")
    public List<ConteudoChaveNumerica> findNumerosInternos(){
        return pedidoVendaCustom.findNumerosInternos();
    }
}