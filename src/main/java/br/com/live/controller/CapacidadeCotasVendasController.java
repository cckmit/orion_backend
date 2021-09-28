package br.com.live.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.model.CapacidadeCotasVendasCapaItens;
import br.com.live.model.CapacidadeCotasVendasDadosItem;
import br.com.live.service.CapacidadeCotasVendasService;
import br.com.live.util.BodyCapacidadeCotasVendas;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/cotas-vendas")
public class CapacidadeCotasVendasController {

	private CapacidadeCotasVendasService capacidadeCotasVendasService;

	public CapacidadeCotasVendasController(CapacidadeCotasVendasService capacidadeCotasVendasService) {
		this.capacidadeCotasVendasService = capacidadeCotasVendasService;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {
		return capacidadeCotasVendasService.findAllCapacidadesCotasVendas();
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.GET)
	public List<Categoria> findCategoriasProd() {
		return capacidadeCotasVendasService.findCategoriasProd();
	}

	@RequestMapping(value = "/itens", method = RequestMethod.POST)
	public List<CapacidadeCotasVendasDadosItem> findItensByFiltros(@RequestBody BodyCapacidadeCotasVendas body) {
		return capacidadeCotasVendasService.findItensByFiltros(body.idCapacidadeCotas,
				body.periodoAtualInicio, body.periodoAtualFinal, body.periodoAnaliseInicio, body.periodoAnaliseFinal,
				ConteudoChaveNumerica.parseValueToString(body.colecoes),
				ConteudoChaveNumerica.parseValueToString(body.depositos), body.listarTempUnit);
	}

	@RequestMapping(value = "/{idCapacidadeCotas}", method = RequestMethod.DELETE)
	public List<CapacidadesCotasVendas> deleteById(@PathVariable("idCapacidadeCotas") long idCapacidadeCotas) {
		capacidadeCotasVendasService.deleteById(idCapacidadeCotas);
		return capacidadeCotasVendasService.findAllCapacidadesCotasVendas();
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public CapacidadeCotasVendasCapaItens saveCapacidadeCotasVendas(@RequestBody BodyCapacidadeCotasVendas body) {
		return capacidadeCotasVendasService.saveCapacidadeCotasVendas(body.idCapacidadeCotas, body.descricao,
				body.periodoAtualInicio, body.periodoAtualFinal, body.periodoAnaliseInicio, body.periodoAnaliseFinal,
				body.minutosPeriodo, ConteudoChaveNumerica.parseValueToString(body.colecoes),
				ConteudoChaveNumerica.parseValueToString(body.depositos), body.itens);		
	}
}
