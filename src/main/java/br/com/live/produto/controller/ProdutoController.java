package br.com.live.produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.model.ConsultaItemSugestaoCancelProducao;
import br.com.live.comercial.model.ConsultaTag;
import br.com.live.produto.body.BodyAtualizaComplemento;
import br.com.live.produto.body.BodyFiltroProduto;
import br.com.live.produto.entity.ProdutoReferencia;
import br.com.live.produto.entity.ProdutoReferenciaCor;
import br.com.live.produto.model.Alternativa;
import br.com.live.produto.model.CorProduto;
import br.com.live.produto.model.Embarque;
import br.com.live.produto.model.Produto;
import br.com.live.produto.model.Roteiro;
import br.com.live.produto.service.ProdutoService;
import br.com.live.util.CodigoGrupoItem;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

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
	
	@RequestMapping(value = "/find-produtos-by-colecao-and-artigo", method = RequestMethod.POST)
	public List<Produto> findProdutosByColecaoAndArtigo(@RequestBody BodyFiltroProduto body) {
		return produtoService.findProdutosByColecaoAndArtigo(body.colecoes, body.artigos, body.boxFim, body.cestoFim);
	}
	
	@RequestMapping(value = "/existe-produto/{nivel}/{grupo}/{sub}/{item}", method = RequestMethod.GET)
	public boolean existsProduto(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("sub") String sub, @PathVariable("item") String item) {
		return produtoService.existeProduto(nivel, grupo, sub, item);
	}
	
	@RequestMapping(value = "/find-categorias", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findCategorias() {
		return produtoService.findCategorias();
	}
 
	@RequestMapping(value = "/find-dados-by-tag/{numeroTag}", method = RequestMethod.GET)
	public ConsultaTag findDadosTags(@PathVariable("numeroTag") String numeroTag) {
		return produtoService.findDadosByTag(numeroTag);
	}
	
	@RequestMapping(value = "/find-dados-by-referencia-and-deposito/{nivel}/{grupo}/{subGrupo}/{item}/{deposito}", method = RequestMethod.GET)
	public ConsultaTag findDadosTags(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("subGrupo") String subGrupo, @PathVariable("item") String item, @PathVariable("deposito") int deposito) {
		return produtoService.findDadosRefByProdutoAndDeposito(nivel, grupo, subGrupo, item, deposito);
	}
	
	@RequestMapping(value = "/findTamanhosByGrupo/{nivel}/{grupo}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findTamanhosByGrupo(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo) {
		return produtoService.findTamanhosByGrupo(nivel, grupo);
	}
	
	@RequestMapping(value = "/findCoresByTamanho/{nivel}/{grupo}/{subGrupo}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findCoresByTamanho(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("subGrupo") String subGrupo) {
		return produtoService.findCoresByTamanho(nivel, grupo, subGrupo);
	}
	
	@RequestMapping(value = "/find-produtos-by-leitor/{leitor}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findProdutosByLeitorProduto(@PathVariable("leitor") String leitor) {		
		return produtoService.findProdutosByLeitorProduto(leitor.toUpperCase());
	}
	
	@RequestMapping(value = "/atualiza-completo-by-arquivo", method = RequestMethod.POST)
	public int atualizaComplementoByArquivo(@RequestBody BodyAtualizaComplemento body) {
		return produtoService.atualizaComplemento(body.refsAlterar, body.complemento);
	}
	
	@RequestMapping(value = "/atualiza-completo-by-colecao", method = RequestMethod.POST)
	public int atualizaComplementoByColecao(@RequestBody BodyAtualizaComplemento body) {
		return produtoService.atualizaComplementoByColecao(body.colecao, body.complemento);
	}
	
	@RequestMapping(value = "/find-grupos-by-leitor/{leitor}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findGruposByLeitorProduto(@PathVariable("leitor") String leitor) {		
		return produtoService.findGruposByLeitorProduto(leitor.toUpperCase());
	}
}