package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/{idPrevisaoVendas}", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendasItens> findPrevisoesVendasItensByIdPrevisaoVenda (@PathVariable("idPrevisaoVendas") long idPrevisaoVendas) {						
		return previsaoVendasService.findPrevisoesVendasItensByIdPrevisaoVenda(idPrevisaoVendas);
	}
		
	// TODO - REVISAR OS METODOS QUE CONTINUARAO EXISTINDO
	// TODO - PASSAR O ID E A COLECAO PARA LOCALIZAR OS DADOS	
	@RequestMapping(value = "/{colecao}", method = RequestMethod.GET)
	public List<ConsultaPrevisaoVendasItens> findPrevisaoByColecao (@PathVariable("colecao") int colecao) {						
		return null; //previsaoVendasService.findPrevisaoByColecao(colecao);
	}

	@RequestMapping(value = "/id-tabela-sell-in/{colecao}", method = RequestMethod.GET)
	public String findIdTabelaSellIn (@PathVariable("colecao") int colecao) {						
		return previsaoVendasService.findIdTabelaSellIn(colecao);
	}

	@RequestMapping(value = "/id-tabela-sell-out/{colecao}", method = RequestMethod.GET)
	public String findIdTabelaSellOut (@PathVariable("colecao") int colecao) {						
		return previsaoVendasService.findIdTabelaSellOut(colecao);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public List<ConsultaPrevisaoVendasItens> savePrevisoes(@RequestBody BodyPrevisaoVendas body) {
		previsaoVendasService.savePrevisoes(body.colecao, body.getColTabPrecoSellIn(), body.getMesTabPrecoSellIn(), body.getSeqTabPrecoSellIn(), body.getColTabPrecoSellOut(), body.getMesTabPrecoSellOut(), body.getSeqTabPrecoSellOut(), body.previsoesVendas);
		return null; //previsaoVendasService.findPrevisaoByColecao(body.colecao); 
	}
	
}
