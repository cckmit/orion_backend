package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.service.SequenciamentoDecoracoesService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;
import br.com.live.body.BodySequenciamentoDecoracoes;
import br.com.live.custom.SequenciamentoDecoracoesCustom;
import br.com.live.model.DadosSequenciamentoDecoracoes;

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
	
	@RequestMapping(value = "/estagios-distrib", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findEstagiosDistribuicao() {
		return sequenciamentoDecoracoesCustom.findEstagiosDistribuicao();
	}

	@RequestMapping(value = "/estagios-sequenciar", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findEstagiosSequenciar() {
		return sequenciamentoDecoracoesCustom.findEstagiosSequenciar();
	}

	@RequestMapping(value = "/referencias", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findTecidosEmOrdensParaLiberacao() {
		return sequenciamentoDecoracoesCustom.findReferenciasEmOrdensCentroDistrib();
	}

	@RequestMapping(value = "/ordens-sequenciadas/{codEstagio}", method = RequestMethod.GET) 
	public List<DadosSequenciamentoDecoracoes> findOrdensSequenciadas(@PathVariable("codEstagio") int codEstagio) {		
		return sequenciamentoDecoracoesCustom.findOrdensSequenciadas(codEstagio);
	}
	
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public List<DadosSequenciamentoDecoracoes> consultar(@RequestBody BodySequenciamentoDecoracoes body) {		
		return sequenciamentoDecoracoesService.consultarOrdens(
				ConteudoChaveAlfaNum.parseValueToListString(body.camposSelParaPriorizacao), 
				body.periodoInicio, 
				body.periodoFim, 
				body.estagioDistrib,
				ConteudoChaveAlfaNum.parseValueToString(body.referencias),				
				ConteudoChaveNumerica.parseValueToString(body.artigos),
				body.isSomenteFlat,
				body.isDiretoCostura, 
				body.isPossuiAgrupador);		
	}	
	
	@RequestMapping(value = "/incluir-sequenciamento", method = RequestMethod.POST)
	public void incluirOrdensNoSequenciamento(@RequestBody BodySequenciamentoDecoracoes body) {	
		sequenciamentoDecoracoesService.incluirOrdensNoSequenciamento(body.listaOrdens);		
	}
	
	@RequestMapping(value = "/calcular-sequenciamento", method = RequestMethod.POST)
	public List<DadosSequenciamentoDecoracoes> calcularSequenciamento(@RequestBody BodySequenciamentoDecoracoes body) {	
		sequenciamentoDecoracoesService.calcularSequenciamento(body.codEstagio, FormataData.parseStringToDate(body.dataInicioSeq), body.listaOrdens);
		return sequenciamentoDecoracoesCustom.findOrdensSequenciadas(body.codEstagio);
	}
	
	@RequestMapping(value = "/confirmar-sequenciamento", method = RequestMethod.POST)
	public ResponseEntity<List<DadosSequenciamentoDecoracoes>> confirmarSequenciamento(@RequestBody BodySequenciamentoDecoracoes body) {
		sequenciamentoDecoracoesService.confirmarSequenciamento(body.codEstagio, body.listaOrdens);
		return new ResponseEntity<> (sequenciamentoDecoracoesCustom.findOrdensSequenciadas(body.codEstagio), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/remover-ordem-sequenciamento/{codEstagio}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<List<DadosSequenciamentoDecoracoes>> removerSequenciamento(@PathVariable("codEstagio") int codEstagio, @PathVariable("id") int id) {
		sequenciamentoDecoracoesService.removerOrdemProducaoSequenciamento(id);
		return new ResponseEntity<> (sequenciamentoDecoracoesCustom.findOrdensSequenciadas(codEstagio), HttpStatus.OK);
	}		
}