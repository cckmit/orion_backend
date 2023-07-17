package br.com.live.comercial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.body.BodyComercial;
import br.com.live.comercial.custom.ComercialCustom;
import br.com.live.comercial.entity.AtributosNaturezasDeOperacao;
import br.com.live.comercial.entity.BloqueioTitulosForn;
import br.com.live.comercial.entity.CanalDistribuicao;
import br.com.live.comercial.entity.FaturamentoLiveClothing;
import br.com.live.comercial.entity.RepresentanteAntigoXNovo;
import br.com.live.comercial.entity.TipoClientePorCanal;
import br.com.live.comercial.entity.TpClienteXTabPreco;
import br.com.live.comercial.entity.TpClienteXTabPrecoItem;
import br.com.live.comercial.model.ConsultaMetasCategoria;
import br.com.live.comercial.model.ConsultaPedidosPorCliente;
import br.com.live.comercial.model.ConsultaRelacionamRepAntigoNovo;
import br.com.live.comercial.model.ConsultaTipoClientePorCanal;
import br.com.live.comercial.model.ConsultaTitulosBloqForn;
import br.com.live.comercial.model.ConsultaTpClienteXTabPreco;
import br.com.live.comercial.model.DescontoClientesImportados;
import br.com.live.comercial.model.PedidosComDescontoAConfirmar;
import br.com.live.comercial.repository.CanalDistribuicaoRepository;
import br.com.live.comercial.repository.TipoClientePorCanalRepository;
import br.com.live.comercial.repository.TpClienteXTabPrecoItemRepository;
import br.com.live.comercial.repository.TpClienteXTabPrecoRepository;
import br.com.live.comercial.service.ComercialService;
import br.com.live.comercial.service.EstacaoService;
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
	private TpClienteXTabPrecoRepository tpClienteXTabPrecoRepository;
	private TpClienteXTabPrecoItemRepository tpClienteXTabPrecoItemRepository;
	private CanalDistribuicaoRepository canaisDeDistribuicaoRepository;
	private TipoClientePorCanalRepository tipoClientePorCanalRepository;
	
	@Autowired
	public ComercialController(ComercialService comercialService, EstacaoService estacaoService, ComercialCustom comercialCustom,
			TpClienteXTabPrecoRepository tpClienteXTabPrecoRepository, TpClienteXTabPrecoItemRepository tpClienteXTabPrecoItemRepository,
			CanalDistribuicaoRepository canaisDeDistribuicaoRepository, TipoClientePorCanalRepository tipoClientePorCanalRepository) {
		this.comercialService = comercialService;
		this.estacaoService = estacaoService;
		this.comercialCustom = comercialCustom;
		this.tpClienteXTabPrecoRepository = tpClienteXTabPrecoRepository;
		this.tpClienteXTabPrecoItemRepository = tpClienteXTabPrecoItemRepository;
		this.canaisDeDistribuicaoRepository = canaisDeDistribuicaoRepository;
		this.tipoClientePorCanalRepository = tipoClientePorCanalRepository;
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
	
	@RequestMapping(value = "/find-all-atributos-naturezas-operacao", method = RequestMethod.GET)
    public List<AtributosNaturezasDeOperacao> findAllAtribNatDeOperacao() {
		return comercialService.findAllAtribNatDeOperacao();
	}
	
	@RequestMapping(value = "/find-all-relacionamento-representante-antigo-novo", method = RequestMethod.GET)
    public List<ConsultaRelacionamRepAntigoNovo> findAllRelacionamentoRepAntNovo() {
		return comercialService.findAllRelacionamentoRepAntNovo();
	}
	
	@RequestMapping(value = "/find-relacionamento-representante-antigo-novo/{id}", method = RequestMethod.GET)
    public RepresentanteAntigoXNovo findAllRelacoRepAntNovoById(@PathVariable("id") int id) {
    	return comercialService.findAllRelacoRepAntNovoById(id);
    }
	
	@RequestMapping(value = "/find-atributos-nat-oper-by-id/{id}", method = RequestMethod.GET)
    public AtributosNaturezasDeOperacao findAtributosNatOpById(@PathVariable("id") int id) {
    	return comercialService.findAtributosNatOpById(id);
    }
	
	@RequestMapping(value = "/find-all-catalogo", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllCatalogo() {
		return comercialCustom.findAllCatalogo();
	}
	
	@RequestMapping(value = "/find-all-relacionamento", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findAllRelacionam() {
		return comercialCustom.findAllRelacionam();
	}
	
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
    }
		
	// Carregar todos Tipos de Cliente
	@RequestMapping(value = "/find-all-tipo-cliente", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllTipoCliente() {
        return comercialCustom.findTipoCliente();
	}
		
	// Carregar todos Tipos de Cliente
	@RequestMapping(value = "/find-tipo-cliente-sem-canal", method = RequestMethod.GET)
    public List<ConsultaTipoClientePorCanal> findAllTipoClienteSemCanal() {
        return comercialService.findTipoClienteSemCanal();
	}
		
	// Carregar todos os Canais de Distribuição
	@RequestMapping(value = "/find-all-canais-distribuicao", method = RequestMethod.GET)
    public List<CanalDistribuicao> findAllCanaisDistribuicao() {
        return comercialService.findAllCanaisDistribuicao();
	}
		
	@RequestMapping(value = "/find-fornecedor-bloq/{idForn}", method = RequestMethod.GET)
    public BloqueioTitulosForn findAllFornBloq(@PathVariable("idForn") String idForn) {
		return comercialService.findForncedorBloq(idForn);
	}
	//
    // Encontrar Todos os Faturamentos Live Clothing
    //
	@RequestMapping(value = "/find-all-faturamento-live-clothing", method = RequestMethod.GET)
    public List<FaturamentoLiveClothing> findAllFatLiveClothing() {                  
        return comercialCustom.findAllFatLiveClothing();
    }
	// Encontrar Faturamento por ID
    //
	@RequestMapping(value = "/find-faturamento-live-clothing-by-id/{idfaturamento}", method = RequestMethod.GET)
    public FaturamentoLiveClothing findFatLiveClothingById(@PathVariable("idfaturamento") int idfaturamento) {
        return comercialService.findFatLiveClothingById(idfaturamento);
    }
	// Encontrar Canal de Distribuição por ID
    //
	@RequestMapping(value = "/find-canal-distribuicao-by-id/{id}", method = RequestMethod.GET)
    public CanalDistribuicao findCanalById(@PathVariable("id") int id) {
        return comercialService.findCanalgById(id);
    }
	// Encontrar Tipo de Cliente por Canal
    //
	@RequestMapping(value = "/find-tipo-cliente-by-canal/{id}", method = RequestMethod.GET)
    public List<ConsultaTipoClientePorCanal> findTipoClienteByCanal(@PathVariable("id") int id) {
        return comercialService.findTipoClienteByCanal(id);
    }
	// Salvar Faturamento LIVE Clothing
    //
    @RequestMapping(value = "/save-faturamento-live-clothing", method = RequestMethod.POST)
    public void saveFatLiveClothing(@RequestBody BodyComercial body) {                  
    	comercialService.saveFatLiveClothing(body.idFaturamento, body.loja, body.data, body.quantidade, body.tickets, body.conversao, body.valorDolar, body.valorReal);
    }
    // Salvar Tipo cliente no Canal
    @RequestMapping(value = "/save-tipo-cliente-canal", method = RequestMethod.POST)
    public void saveTipoClientePorCanal(@RequestBody BodyComercial body) {                  
    	comercialService.saveTipoClientePorCanal(body.idCanal, body.tipoCliente);
    }
    // Salvar Novo Canal de Distribuição
    //
    @RequestMapping(value = "/save-canal-de-distribuicao", method = RequestMethod.POST)
    public void saveCanaisDistribuicao(@RequestBody BodyComercial body) {                  
    	comercialService.saveCanaisDistribuicao(body.idCanal, body.descricao, body.modalidade);
    }
	//
    // Importar Metas Categorias de Coleções
    //
    @RequestMapping(value = "/importar-metas-estacoes", method = RequestMethod.POST)
    public List<ConsultaMetasCategoria> importarMetasCategoria(@RequestBody BodyComercial body) {                  
    	comercialService.importarMetasCategoria(body.codEstacao, body.tipoMeta, body.tabImportar);
    	return estacaoService.findMetasCategoriaGrid(body.codEstacao, body.tipoMeta);
    }
    
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
    
    // Salvar Relacionamentos Representante Antigo X Novo
    @RequestMapping(value = "/save-relacionamento-representante-antigo-novo", method = RequestMethod.POST)
    public void saveRelacionamentoRepAntXNovo(@RequestBody BodyComercial body) {                  
    	comercialService.saveRelacionamentoRepAntXNovo(body.idRelac, body.represAntigo, body.represNovo);
    }
    
    @RequestMapping(value = "/save-atributos-de-natureza-operacao", method = RequestMethod.POST)
    public void saveAtributosNatOperacao(@RequestBody BodyComercial body) {
		comercialService.saveAtributosNatOperacao(body.idAtrib, body.venda, body.devolucao, body.ranking);
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

    @RequestMapping(value = "/delete-faturamento-live-clothing/{idFaturamento}", method = RequestMethod.DELETE)
    public void deleteFatLiveClothing(@PathVariable("idFaturamento") int idFaturamento) {                  
    	comercialService.deleteFatLiveClothing(idFaturamento);        
    }
    
	@RequestMapping(value = "/importar-desconto-clientes", method = RequestMethod.POST)
	public StatusGravacao importarDescontoClientes(@RequestBody BodyComercial body) {
		return comercialService.saveDescontosClientesImportados(body.listClientesDesconto, body.usuario);
	}

	@RequestMapping(value = "/consulta-pedidos-com-desconto", method = RequestMethod.GET)
	public List<PedidosComDescontoAConfirmar> findPedidosComDesc() {
		return comercialService.prepararPedidosParaAplicarDesconto();
	}

	@RequestMapping(value = "/aplicar-descontos-pedidos", method = RequestMethod.POST)
	public StatusGravacao aplicarDescontosPedidos(@RequestBody BodyComercial body) {
		return comercialService.aplicarDescontoEspecialPedidos(body.listClientesDesconto, body.usuario, body.observacao);
	}

	@RequestMapping(value = "/consulta-hist-importacoes", method = RequestMethod.GET)
	public List<DescontoClientesImportados> findHistImportacoes() {
		return comercialService.buscarHistoricoImportacoes();
	}

	@RequestMapping(value = "/consulta-hist-descontos", method = RequestMethod.GET)
	public List<ConsultaPedidosPorCliente> findHistoricoDescontos() {
		return comercialService.buscarHistoricoDescontos();
	}

	@RequestMapping(value = "/consulta-saldos-clientes", method = RequestMethod.GET)
	public List<DescontoClientesImportados> findSaldosClientes() {
		return comercialService.buscarSaldosClientes();
	}

	@RequestMapping(value = "/find-pedidos-aplicar-saldo/{cnpjCliente}", method = RequestMethod.GET)
	public List<ConsultaPedidosPorCliente> findSaldosClientes(@PathVariable("cnpjCliente") String cnpjCliente) {

		int cnpj9 = Integer.parseInt(cnpjCliente.substring(0,8));
		int cnpj4 = Integer.parseInt(cnpjCliente.substring(8,12));
		int cnpj2 = Integer.parseInt(cnpjCliente.substring(12,14));

		return comercialCustom.findPedidosPorCliente(cnpj9,cnpj4,cnpj2);
	}

	@RequestMapping(value = "/aplicar-saldo-descontos-pedidos", method = RequestMethod.POST)
	public void findSaldosClientes(@RequestBody BodyComercial body) {

		int cnpj9 = Integer.parseInt(body.cnpjCliente.substring(0,8));
		int cnpj4 = Integer.parseInt(body.cnpjCliente.substring(8,12));
		int cnpj2 = Integer.parseInt(body.cnpjCliente.substring(12,14));

		comercialService.aplicarSaldosDescontoPedidos(body.listPedidosSel, cnpj9, cnpj4, cnpj2, body.usuario, body.observacao);
	}
	
	@RequestMapping(value = "/delete-canais-distribuicao/{idCanal}", method = RequestMethod.DELETE)
    public List<CanalDistribuicao> deleteCanaisDistribuicao(@PathVariable("idCanal") int idCanal) {                  
    	comercialService.deleteCanaisDistribuicao(idCanal);
        return canaisDeDistribuicaoRepository.findAll();
    }
	
	@RequestMapping(value = "/delete-tipo-cliente-canal/{id}", method = RequestMethod.DELETE)
    public List<TipoClientePorCanal> deleteTipoClienteCanal(@PathVariable("id") int id) {                  
    	comercialService.deleteTipoClienteCanal(id);
        return tipoClientePorCanalRepository.findAll();
    }
	
	@RequestMapping(value = "/delete-realacionamento-repres-antigo-novo/{id}", method = RequestMethod.DELETE)
    public List<ConsultaRelacionamRepAntigoNovo> deleteRelacRepAntigoXNovo(@PathVariable("id") int id) {                  
    	comercialService.deleteRelacRepAntigoXNovo(id);
    	return comercialService.findAllRelacionamentoRepAntNovo();
    }

}