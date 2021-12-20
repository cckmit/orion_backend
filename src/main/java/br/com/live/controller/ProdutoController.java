package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyFiltroProduto;
import br.com.live.entity.ProdutoReferencia;
import br.com.live.entity.ProdutoReferenciaCor;
import br.com.live.model.Alternativa;
import br.com.live.model.ConsultaItemSugestaoCancelProducao;
import br.com.live.model.CorProduto;
import br.com.live.model.Embarque;
import br.com.live.model.Produto;
import br.com.live.model.Roteiro;
import br.com.live.service.ProdutoService;
import br.com.live.util.CodigoGrupoItem;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value = "/produtos-colecoes", method = RequestMethod.POST)
	public List<ProdutoReferencia> findByColecoes(@RequestBody BodyFiltroProduto filtro) {

		List<ProdutoReferencia> produtos = null;

		if (!filtro.colecoes.isEmpty() || !filtro.colecoesPermanentes.isEmpty()) {
			produtos = produtoService.findProdutosByParameters(filtro);
		}

		return produtos;
	}

	@RequestMapping(value = "/cores-colecoes", method = RequestMethod.POST)
	public List<CorProduto> findCoresByColecoes(@RequestBody BodyFiltroProduto filtro) {

		List<CorProduto> cores = null;

		if (!filtro.colecoes.isEmpty()) {
			cores = produtoService.findCoresByParameters(filtro);
		}

		return cores;
	}

	// TODO - ELIMINAR MÉTODO
	@RequestMapping(value = "/itens-colecoes", method = RequestMethod.POST)
	public List<ProdutoReferenciaCor> findItensByFiltro(@RequestBody BodyFiltroProduto filtro) {

		List<ProdutoReferenciaCor> itens = null;

		if (!filtro.colecoes.isEmpty() || !filtro.colecoesPermanentes.isEmpty() || !filtro.referencias.isEmpty()
				|| !filtro.cores.isEmpty()) {
			itens = produtoService.findItensByParameters(filtro);
		}

		return itens;
	}
	
	@RequestMapping(value = "/itens-sug-cancelamento", method = RequestMethod.POST)
	public List<ConsultaItemSugestaoCancelProducao> findItensSugestaoCancelProducaoByFiltro(@RequestBody BodyFiltroProduto filtro) {
		
		List<ConsultaItemSugestaoCancelProducao> itens = null;
		
		if (!filtro.colecoes.isEmpty() || !filtro.colecoesPermanentes.isEmpty() || !filtro.referencias.isEmpty()
				|| !filtro.cores.isEmpty()) {
			itens = produtoService.findItensSugestaoCancelamentoByParameters(filtro);
		}
		
		return itens;
	}	

	@RequestMapping(value = "/item/{codigo}", method = RequestMethod.GET)
	public ProdutoReferenciaCor findItemByCodigo(@PathVariable("codigo") String codigo) {
		return produtoService.findItemByCodigo(CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo));
	}

	@RequestMapping(value = "/alternativas/{codigo}", method = RequestMethod.GET)
	public List<Alternativa> findAlternativasByCodigo(@PathVariable("codigo") String codigo) {
		return produtoService.findAlternativasByCodigo(CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo));
	}
	
	@RequestMapping(value = "/roteiros/{codigo}/{alternativa}", method = RequestMethod.GET)
	public List<Roteiro> findRoteirosByCodigo(@PathVariable("codigo") String codigo, @PathVariable("alternativa") int alternativa) {
		return produtoService.findRoteirosByCodigo(CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo), alternativa);
	}
	
	@RequestMapping(value = "/embarques", method = RequestMethod.GET)
	public List<Embarque> findEmbarques() {
		return produtoService.findEmbarques();
	}
	
	@RequestMapping(value = "/prod-com-roteiros/{niveis}", method = RequestMethod.GET)
	public List<Produto> findProdutosComRoteiroByNiveis(@PathVariable("niveis") String niveis) {
		return produtoService.findProdutosComRoteiroByNiveis(niveis);
	}

	@RequestMapping(value = "/cores-by-nivel-grupo-sub/{nivel}/{grupo}/{sub}", method = RequestMethod.GET)
	public  List<CorProduto> findCoresByNivelGrupoSub(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("sub") String sub) {
		return produtoService.findCoresByNivelGrupoSub(nivel, grupo, sub);
	}
	
	@RequestMapping(value = "/existe-roteiro/{nivel}/{grupo}/{sub}/{item}/{alternativa}/{roteiro}", method = RequestMethod.GET)
	public boolean existsRoteiro(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("sub") String sub, @PathVariable("item") String item, @PathVariable("alternativa") int alternativa, @PathVariable("roteiro") int roteiro) {
		return produtoService.existsRoteiro(nivel, grupo, sub, item, alternativa, roteiro);
	}

	@RequestMapping(value = "/existe-roteiro-requisicao/{nivel}/{grupo}/{sub}/{item}/{alternativa}/{roteiro}", method = RequestMethod.GET)
	public boolean existsRoteiroRequisicao(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("sub") String sub, @PathVariable("item") String item, @PathVariable("alternativa") int alternativa, @PathVariable("roteiro") int roteiro) {
		return produtoService.existsRoteiroRequisicao(nivel, grupo, sub, item, alternativa, roteiro);
	}
}