package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.CorProduto;
import br.com.live.entity.Produto;
import br.com.live.entity.ProdutoReferCor;
import br.com.live.model.Alternativa;
import br.com.live.model.Embarque;
import br.com.live.model.Roteiro;
import br.com.live.service.ProdutoService;
import br.com.live.util.CodigoGrupoItem;
import br.com.live.util.FiltroProduto;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value = "/produtos-colecoes", method = RequestMethod.POST)
	public List<Produto> findByColecoes(@RequestBody FiltroProduto filtro) {

		List<Produto> produtos = null;

		if (!filtro.colecoes.isEmpty() || !filtro.colecoesPermanentes.isEmpty()) {
			produtos = produtoService.findProdutosByParameters(filtro);
		}

		return produtos;
	}

	@RequestMapping(value = "/cores-colecoes", method = RequestMethod.POST)
	public List<CorProduto> findCoresByColecoes(@RequestBody FiltroProduto filtro) {

		List<CorProduto> cores = null;

		if (!filtro.colecoes.isEmpty()) {
			cores = produtoService.findCoresByParameters(filtro);
		}

		return cores;
	}

	@RequestMapping(value = "/itens-colecoes", method = RequestMethod.POST)
	public List<ProdutoReferCor> findItensByFiltro(@RequestBody FiltroProduto filtro) {

		List<ProdutoReferCor> itens = null;

		if (!filtro.colecoes.isEmpty() || !filtro.colecoesPermanentes.isEmpty() || !filtro.referencias.isEmpty()
				|| !filtro.cores.isEmpty()) {
			itens = produtoService.findItensByParameters(filtro);
		}

		return itens;
	}

	@RequestMapping(value = "/item/{codigo}", method = RequestMethod.GET)
	public ProdutoReferCor findItemByCodigo(@PathVariable String codigo) {
		return produtoService.findItemByCodigo(CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo));
	}

	@RequestMapping(value = "/alternativas/{codigo}", method = RequestMethod.GET)
	public List<Alternativa> findAlternativasByCodigo(@PathVariable String codigo) {
		return produtoService.findAlternativasByCodigo(CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo));
	}
	
	@RequestMapping(value = "/roteiros/{codigo}/{alternativa}", method = RequestMethod.GET)
	public List<Roteiro> findRoteirosByCodigo(@PathVariable String codigo, @PathVariable int alternativa) {
		return produtoService.findRoteirosByCodigo(CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo), alternativa);
	}
	
	@RequestMapping(value = "/embarques", method = RequestMethod.GET)
	public List<Embarque> findEmbarques() {
		return produtoService.findEmbarques();
	}

}
