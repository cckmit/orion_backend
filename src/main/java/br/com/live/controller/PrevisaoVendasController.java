package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.PrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendasItens;
import br.com.live.service.PrevisaoVendasService;
import br.com.live.util.BodyPrevisaoVendas;

@RestController
@CrossOrigin
@RequestMapping("/previsao-vendas")
public class PrevisaoVendasController {

	@Autowired
	private PrevisaoVendasService previsaoVendasService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendas> findPrevisoesVendas () {						
		return previsaoVendasService.findPrevisoesVendas();
	}

	@RequestMapping(value = "/{idPrevisaoVendas}/{colecao}", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendasItens> findPrevisoesVendasItensByIdPrevisaoVendaColecao (@PathVariable("idPrevisaoVendas") long idPrevisaoVendas, @PathVariable("colecao") int colecao) {						
		return previsaoVendasService.findPrevisoesVendasItensByIdPrevisaoVendaColecao(idPrevisaoVendas, colecao);
	}
		
	@RequestMapping(value = "", method = RequestMethod.POST)
	public PrevisaoVendas savePrevisaoVendas(@RequestBody BodyPrevisaoVendas body) {		
		return previsaoVendasService.savePrevisaoVendas(body.id, body.descricao, body.colecao, body.getColTabPrecoSellIn(), body.getMesTabPrecoSellIn(), body.getSeqTabPrecoSellIn(), body.getColTabPrecoSellOut(), body.getMesTabPrecoSellOut(), body.getSeqTabPrecoSellOut(), body.previsoesVendasItens); 
	}	
	
	@RequestMapping(value = "/id-tabela-sell-in/{colecao}", method = RequestMethod.GET)
	public String findIdTabelaSellIn (@PathVariable("colecao") int colecao) {						
		return previsaoVendasService.findIdTabelaSellIn(colecao);
	}

	@RequestMapping(value = "/id-tabela-sell-out/{colecao}", method = RequestMethod.GET)
	public String findIdTabelaSellOut (@PathVariable("colecao") int colecao) {						
		return previsaoVendasService.findIdTabelaSellOut(colecao);
	}
		
}
