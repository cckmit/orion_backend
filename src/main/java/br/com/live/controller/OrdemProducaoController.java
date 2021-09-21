package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.DadosTagChina;
import br.com.live.model.EstagioProducao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.OrdemProducao;
import br.com.live.service.OrdemProducaoService;
import br.com.live.util.BodyOrdemProducao;

@RestController
@CrossOrigin
@RequestMapping("/ordens-producao")
public class OrdemProducaoController {

	@Autowired
	private OrdemProducaoService ordemProducaoService;

    @RequestMapping(value = "/estagios-producao", method = RequestMethod.GET)
    public List<EstagioProducao> findAll() {
          return ordemProducaoService.findAllEstagios();
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
		return ordemProducaoService.gerarOrdens(body.idPlanoMestre, body.listaPreOrdens);		
	}
	
	@RequestMapping(value = "/excluir", method = RequestMethod.POST)
	public List<ConsultaPreOrdemProducao> excluir(@RequestBody BodyOrdemProducao body) {
		return ordemProducaoService.excluirOrdens(body.idPlanoMestre, body.listaPreOrdens);		
	}
	
	
}
