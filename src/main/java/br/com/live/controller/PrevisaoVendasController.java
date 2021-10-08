package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyPrevisaoVendas;
import br.com.live.entity.PrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendasItem;
import br.com.live.model.ConsultaPrevisaoVendasItemTam;
import br.com.live.service.PrevisaoVendasService;
import br.com.live.util.CodigoGrupoItem;

@RestController
@CrossOrigin
@RequestMapping("/previsao-vendas")
public class PrevisaoVendasController {

	@Autowired
	private PrevisaoVendasService previsaoVendasService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendas> findPrevisoesVendas() {
		return previsaoVendasService.findPrevisoesVendas();
	}

	@RequestMapping(value = "/{idPrevisaoVendas}", method = RequestMethod.GET)
	public PrevisaoVendas findPrevisaoVendas(@PathVariable("idPrevisaoVendas") long idPrevisaoVendas) {
		return previsaoVendasService.findPrevisaoVendas(idPrevisaoVendas);
	}

	@RequestMapping(value = "/{idPrevisaoVendas}/{colecao}", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendasItem> findPrevisaoVendasItensByIdPrevisaoVendaColecao(
			@PathVariable("idPrevisaoVendas") long idPrevisaoVendas, @PathVariable("colecao") int colecao) {
		return previsaoVendasService.findPrevisaoVendasItensByIdPrevisaoVendaColecao(idPrevisaoVendas, colecao);
	}

	@RequestMapping(value = "/tamanhos/{idPrevisaoVendas}/{codigo}", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendasItemTam> findPrevisaoVendasItensTamByIdPrevisaoVendasGrupoItem(
			@PathVariable("idPrevisaoVendas") long idPrevisaoVendas, @PathVariable("codigo") String codigo) {
		return previsaoVendasService.findPrevisaoVendasItemTamByIdPrevisaoVendasGrupoItem(idPrevisaoVendas, CodigoGrupoItem.getGrupo(codigo), CodigoGrupoItem.getItem(codigo));
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public PrevisaoVendas savePrevisaoVendas(@RequestBody BodyPrevisaoVendas body) {
		return previsaoVendasService.savePrevisaoVendas(body.id, body.descricao, body.colecao,
			body.getColTabPrecoSellIn(), body.getMesTabPrecoSellIn(), body.getSeqTabPrecoSellIn(),
			body.getColTabPrecoSellOut(), body.getMesTabPrecoSellOut(), body.getSeqTabPrecoSellOut(),
			body.previsaoVendasItens);
	}

	@RequestMapping(value = "/tamanhos", method = RequestMethod.POST)
	public List<ConsultaPrevisaoVendasItemTam> saveTamanhosPrevisaoVendas(@RequestBody BodyPrevisaoVendas body) {
		previsaoVendasService.saveTamanhosPrevisaoVendas(body.id, CodigoGrupoItem.getGrupo(body.codigoGrupoItem), CodigoGrupoItem.getItem(body.codigoGrupoItem), body.previsaoVendasItemTamanhos);
		return previsaoVendasService.findPrevisaoVendasItemTamByIdPrevisaoVendasGrupoItem(body.id, CodigoGrupoItem.getGrupo(body.codigoGrupoItem), CodigoGrupoItem.getItem(body.codigoGrupoItem)); 
	}
	
	@RequestMapping(value = "/{idPrevisaoVendas}", method = RequestMethod.DELETE)
	public List<ConsultaPrevisaoVendas> deletePlanoMestre(@PathVariable("idPrevisaoVendas") long idPrevisaoVendas) {
		previsaoVendasService.deletePrevisaoVendas(idPrevisaoVendas);
		return findPrevisoesVendas();
	}
	
}
