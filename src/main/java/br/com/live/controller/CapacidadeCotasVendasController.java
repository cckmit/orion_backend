package br.com.live.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.CapacidadeCotasVendasCapa;
import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.model.ProdutosCapacidadeProd;
import br.com.live.repository.CapacidadeCotasVendasRepository;
import br.com.live.service.CapacidadeCotasVendasService;
import br.com.live.util.BodyCapacidadeCotasVendas;

@RestController
@CrossOrigin
@RequestMapping("/cotas-vendas")
public class CapacidadeCotasVendasController {
	
	private CapacidadeCotasVendasService capacidadeCotasVendasService;
	private CapacidadeCotasVendasRepository capacidadeCotasVendasRepository;
	
	public CapacidadeCotasVendasController(CapacidadeCotasVendasService capacidadeCotasVendasService, CapacidadeCotasVendasRepository capacidadeCotasVendasRepository) {
		this.capacidadeCotasVendasService = capacidadeCotasVendasService;
		this.capacidadeCotasVendasRepository = capacidadeCotasVendasRepository;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {
		return capacidadeCotasVendasService.findAllCapacidadesCotasVendas();
	}
	
	@RequestMapping(value = "/id-cotas/capa/{idCapacidadeCotas}", method = RequestMethod.GET)
	public CapacidadeCotasVendasCapa findCapacidadesCotasVendasById(@PathVariable("idCapacidadeCotas") String idCapacidadeCotas) {
		return capacidadeCotasVendasRepository.findByIdCapacidadeCotasVendas(idCapacidadeCotas);
	}
	
	@RequestMapping(value = "/categorias", method = RequestMethod.GET)
	public List<Categoria> findCategoriasProd() {
		return capacidadeCotasVendasService.findCategoriasProd();
	}
	
	@RequestMapping(value = "/itens/{colecao}/{linha}/{periodo}/{listarComQtde}/{listarTempUnit}", method = RequestMethod.GET)
	public List<ProdutosCapacidadeProd> findProdutosByCategoriaLinha(@PathVariable("colecao") int colecao, @PathVariable("linha") int linha, @PathVariable("periodo") int periodo,  @PathVariable("listarComQtde") boolean listarComQtde, @PathVariable("listarTempUnit") boolean listarTempUnit) {
		return capacidadeCotasVendasService.findProdutosByCategoriaLinha(colecao, linha, periodo, listarComQtde, listarTempUnit);
	}
	
	@RequestMapping(value = "/id-capacidade-cotas/{idCapacidadeCotas}", method = RequestMethod.GET)
	public List<ProdutosCapacidadeProd> findProdutosByIdCapacidadeCotas(@PathVariable("idCapacidadeCotas") String idCapacidadeCotas) {
		return capacidadeCotasVendasService.findProdutosByIdCapacidadeCotas(idCapacidadeCotas);
	}
	
	@RequestMapping(value = "/{idCapacidadeCotas}", method = RequestMethod.DELETE)
	public List<CapacidadesCotasVendas> deleteById(@PathVariable("idCapacidadeCotas") String idCapacidadeCotas) {
		capacidadeCotasVendasService.deleteById(idCapacidadeCotas);
		return capacidadeCotasVendasService.findAllCapacidadesCotasVendas();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public List<ProdutosCapacidadeProd> saveCapacidadeCotasVendas(@RequestBody BodyCapacidadeCotasVendas body) {
		capacidadeCotasVendasService.saveCapacidadeCotasVendas(body.periodo, body.colecao, body.linha, body.modelos, body.minDistribuir);
		return capacidadeCotasVendasService.findProdutosByCategoriaLinha(body.colecao, body.linha, body.periodo, body.listarComQtde, body.listarTempUnit);
	}

}
