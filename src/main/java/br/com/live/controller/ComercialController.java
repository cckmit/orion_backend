package br.com.live.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyComercial;
import br.com.live.custom.ComercialCustom;
import br.com.live.entity.BloqueioTitulosForn;
//import br.com.live.entity.TpClienteXTabPreco;
//import br.com.live.entity.TpClienteXTabPrecoItem;
import br.com.live.model.ConsultaMetasCategoria;
import br.com.live.model.ConsultaTitulosBloqForn;
//import br.com.live.model.ConsultaTpClienteXTabPreco;
//import br.com.live.repository.TpClienteXTabPrecoItemRepository;
//import br.com.live.repository.TpClienteXTabPrecoRepository;
import br.com.live.service.ComercialService;
import br.com.live.service.EstacaoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;

@RestController
@CrossOrigin
@RequestMapping("/comercial")
public class ComercialController {
	
	private ComercialService comercialService;
	private EstacaoService estacaoService;
	private ComercialCustom comercialCustom;
	//private TpClienteXTabPrecoRepository tpClienteXTabPrecoRepository;
	//private TpClienteXTabPrecoItemRepository tpClienteXTabPrecoItemRepository;
	
	@Autowired
	public ComercialController(ComercialService comercialService, EstacaoService estacaoService, ComercialCustom comercialCustom) {
		this.comercialService = comercialService;
		this.estacaoService = estacaoService;
		this.comercialCustom = comercialCustom;
		//this.tpClienteXTabPrecoRepository = tpClienteXTabPrecoRepository;
		//this.tpClienteXTabPrecoItemRepository = tpClienteXTabPrecoItemRepository;
	}
	
	@RequestMapping(value = "/save-envio-produtos-e-commerce", method = RequestMethod.POST)
    public void gravarEnvioProdEcommerce(@RequestBody BodyComercial body) {
		comercialService.saveProdutosIntegracaoEcom(body.referencia, body.tamanho, body.cor);
	}
	
	@RequestMapping(value = "/save-bloqueio-fornecedor", method = RequestMethod.POST)
    public void saveBloqueioFornecedor(@RequestBody BodyComercial body) {
		comercialService.saveBloqueio(body.fornecedor, body.motivo, body.editMode);
	}
	
	@RequestMapping(value = "/liberar-bloqueio-fornecedor", method = RequestMethod.POST)
    public List<ConsultaTitulosBloqForn> liberarBloqueioFornecedor(@RequestBody BodyComercial body) {
		comercialService.liberarBloqueio(body.fornecedor);
		return comercialService.findAllFornBloq();
	}
	
	@RequestMapping(value = "/find-all-titulos-bloq", method = RequestMethod.GET)
    public List<ConsultaTitulosBloqForn> findAllFornBloq() {
		return comercialService.findAllFornBloq();
	}
	
	@RequestMapping(value = "/find-all-catalogo", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllCatalogo() {
		return comercialCustom.findAllCatalogo();
	}
	
	@RequestMapping(value = "/find-all-relacionamento", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findAllRelacionam() {
		return comercialCustom.findAllRelacionam();
	}
	
	/*
	@RequestMapping(value = "/find-relacionamentos/{id}", method = RequestMethod.GET)
    public TpClienteXTabPreco findTpClienteTabPreco(@PathVariable("id") String id) {
    	return tpClienteXTabPrecoRepository.findByIdTpCliTabPreco(id);
    }
	
	@RequestMapping(value = "/find-relacionamentos-item/{idCapa}", method = RequestMethod.GET)
    public List<ConsultaTpClienteXTabPreco> findTpClienteTabPrecoItem(@PathVariable("idCapa") String idCapa) {
    	return comercialCustom.findByIdTpCliTabPrecoItem(idCapa);
    }
	
	@RequestMapping(value = "/find-relacionamentos-grid/{idCapa}/{id}", method = RequestMethod.GET)
    public ConsultaTpClienteXTabPreco findAllGridItem(@PathVariable("idCapa") String idCapa, @PathVariable("id") long id) {
    	return comercialCustom.findAllGridItem(idCapa, id);
    }*/
		
	// Carregar todos Tipos de Cliente
		@RequestMapping(value = "/find-all-tipo-cliente", method = RequestMethod.GET)
	    public List<ConteudoChaveNumerica> findAllTipoCliente() {
	        return comercialCustom.findTipoCliente();
	    }
	
	@RequestMapping(value = "/find-fornecedor-bloq/{idForn}", method = RequestMethod.GET)
    public BloqueioTitulosForn findAllFornBloq(@PathVariable("idForn") String idForn) {
		return comercialService.findForncedorBloq(idForn);
	}
	//
    // Importar Metas Categorias de Coleções
    //
    @RequestMapping(value = "/importar-metas-estacoes", method = RequestMethod.POST)
    public List<ConsultaMetasCategoria> importarMetasCategoria(@RequestBody BodyComercial body) {                  
    	comercialService.importarMetasCategoria(body.codEstacao, body.tipoMeta, body.tabImportar);
    	return estacaoService.findMetasCategoriaGrid(body.codEstacao, body.tipoMeta);
    }
    
    /*
    // Salvar Relacionamentos Tipo Cliente e Tabela de Preço
    //
    @RequestMapping(value = "/save-relacionamento", method = RequestMethod.POST)
    public String saveRelacionamento(@RequestBody BodyComercial body) {                  
    	return comercialService.saveRelacionamento(body.id, body.catalogo, body.tipoCliente, body.tabela, body.numDias, body.numInterno);
    }
    
    // Salvar Relacionamentos Tipo Cliente e Tabela de Preço
    //
    @RequestMapping(value = "/save-relacionamento-item", method = RequestMethod.POST)
    public StatusGravacao saveRelacionamentoItem(@RequestBody BodyComercial body) {                  
    	return comercialService.saveRelacionamentoItem(body.idItem, body.idCapa, body.catalogo, body.tipoCliente, body.tabela, body.periodoIni, body.periodoFim);
    }
    
    
    @RequestMapping(value = "/delete-relacionamento-item/{idItem}", method = RequestMethod.DELETE)
    public List<TpClienteXTabPrecoItem> deleteItemRelac(@PathVariable("idItem") int idItem) {                  
    	comercialService.deleteItemRelacionamento(idItem);
        return tpClienteXTabPrecoItemRepository.findAll();
    }
    
    @RequestMapping(value = "/delete-relacionamento/{idCapa}", method = RequestMethod.DELETE)
    public List<TpClienteXTabPrecoItem> deleteCapaRelac(@PathVariable("idCapa") String idCapa) {                  
    	comercialService.deleteRelacionamento(idCapa);
    	comercialService.deleteRelacCapa(idCapa);
        return tpClienteXTabPrecoItemRepository.findAll();
    }
    */
}