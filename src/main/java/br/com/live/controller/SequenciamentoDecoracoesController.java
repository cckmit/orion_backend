package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.service.SequenciamentoDecoracoesService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.body.BodySequenciamentoDecoracoes;
import br.com.live.custom.SequenciamentoDecoracoesCustom;
import br.com.live.model.OrdemProducao;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaMateriais;

@RestController
@CrossOrigin
@RequestMapping("/sequenciamento-decoracoes")
public class SequenciamentoDecoracoesController {

	private SequenciamentoDecoracoesService sequenciamentoDecoracoesService;
	private SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom; 
	
	@Autowired
	public SequenciamentoDecoracoesController(SequenciamentoDecoracoesService sequenciamentoDecoracoesService, SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom) {
		this.sequenciamentoDecoracoesService = sequenciamentoDecoracoesService;
		this.sequenciamentoDecoracoesCustom = sequenciamentoDecoracoesCustom;
	}	
	
	@RequestMapping(value = "/referencias", method = RequestMethod.GET)
	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		return sequenciamentoDecoracoesCustom.findReferenciasEmOrdensCentroDistrib();
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public List<OrdemProducao> consultar(@RequestBody BodySequenciamentoDecoracoes body) {		
		return sequenciamentoDecoracoesService.consultarOrdens(
				ConteudoChaveAlfaNum.parseValueToListString(body.camposSelParaPriorizacao), 
				body.periodoInicio, 
				body.periodoFim, 				
				ConteudoChaveAlfaNum.parseValueToString(body.referencias),				
				ConteudoChaveNumerica.parseValueToString(body.artigos),
				body.isSomenteFlat,
				body.isDiretoCostura, 
				body.isPossuiAgrupador);		
	}
	
}
