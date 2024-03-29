package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.producao.body.BodyOrdemProducao;
import br.com.live.producao.model.ConsultaPreOrdemProducao;
import br.com.live.producao.model.DadosTagChina;
import br.com.live.producao.model.EstagioProducao;
import br.com.live.producao.model.OrdemConfeccao;
import br.com.live.producao.model.OrdemProducao;
import br.com.live.producao.service.OrdemProducaoPlanoMestreService;
import br.com.live.producao.service.OrdemProducaoService;
import br.com.live.util.ConteudoChaveAlfaNum;

@RestController
@CrossOrigin
@RequestMapping("/ordens-producao")
public class OrdemProducaoController {
	
	private OrdemProducaoService ordemProducaoService;
	private OrdemProducaoPlanoMestreService ordemProducaoPlanoMestreService;

	@Autowired
	public OrdemProducaoController(OrdemProducaoService ordemProducaoService, OrdemProducaoPlanoMestreService ordemProducaoPlanoMestreService) {
		this.ordemProducaoService = ordemProducaoService;
		this.ordemProducaoPlanoMestreService = ordemProducaoPlanoMestreService;
	}
	
    @RequestMapping(value = "/estagios-producao", method = RequestMethod.GET)
    public List<EstagioProducao> findAll() {
          return ordemProducaoService.findAllEstagios();
    }

	@RequestMapping(value = "/estagios-producao-decoracao-op/{ordemProducao}", method = RequestMethod.GET)
	public List<EstagioProducao> findAllEstagiosDecoracaoOrdemProducao(@PathVariable("ordemProducao") int ordemProducao) {
		return ordemProducaoService.findAllEstagiosDecoracaoOrdemProducao(ordemProducao);
	}
	
	@RequestMapping(value = "/estagios-producao-crititos/{ordemProducao}", method = RequestMethod.GET)
	public List<EstagioProducao> findEstagiosCriticos(@PathVariable("ordemProducao") int ordemProducao) {
		return ordemProducaoService.findEstagiosCriticos(ordemProducao);
	}
	    
    @RequestMapping(value = "/find-tags-china", method = RequestMethod.GET)
    public List<OrdemProducao> findAllTagsChina() {
          return ordemProducaoService.findAllTagsExportacaoChina();
    }
    
    @RequestMapping(value = "/find-pacotes/{ordemProducao}", method = RequestMethod.GET)
    public List<OrdemConfeccao> findAllPacotes(@PathVariable("ordemProducao") int ordemProducao) {
          return ordemProducaoService.findAllPacotes(ordemProducao);
    }
    
	@RequestMapping(value = "/find-dados-tag", method = RequestMethod.POST)
	public List<DadosTagChina> findDadosTag(@RequestBody BodyOrdemProducao body) {
		return ordemProducaoService.findDadosTag(body.ordemProducao);		
	}
	
	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public BodyOrdemProducao gerar(@RequestBody BodyOrdemProducao body) {
		return ordemProducaoPlanoMestreService.gerarOrdens(body.idPlanoMestre, body.listaPreOrdens);		
	}
	
	@RequestMapping(value = "/excluir", method = RequestMethod.POST)
	public List<ConsultaPreOrdemProducao> excluir(@RequestBody BodyOrdemProducao body) {
		return ordemProducaoPlanoMestreService.excluirOrdens(body.idPlanoMestre, body.listaPreOrdens);		
	}
    @RequestMapping(value = "/find-tags-async-select", method = RequestMethod.POST)
    public List<ConteudoChaveAlfaNum> findAllTagsAsync(@RequestBody BodyOrdemProducao body) {
          return ordemProducaoService.findAllOrdensAsyncComEstagio(body.listaEstagio, body.searchVar);
    }

	@RequestMapping(value = "/find-ordens-async-select-sem-estagio/{searchVar}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findAllTagsAsync(@PathVariable("searchVar") String searchVar) {
		return ordemProducaoService.findAllOrdensAsync(0, searchVar);
	}

	@RequestMapping(value = "/find-ordens-async-select-estagio-decoracao/{searchVar}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findOrdensForAsyncEstagioDecoracao(@PathVariable("searchVar") String searchVar) {
		return ordemProducaoService.findOrdensForAsyncEstagioDecoracao(searchVar);
	}
}
