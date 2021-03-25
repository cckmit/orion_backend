package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.model.ArtigoCapacidadeProducao;
import br.com.live.model.EstagioCapacidadeProducao;
import br.com.live.service.CapacidadeProducaoService;
import br.com.live.util.BodyCapacidadeProducao;

@RestController
@CrossOrigin
@RequestMapping("/capacidade-producao")
public class CapacidadeProducaoController {

	@Autowired
	private CapacidadeProducaoService CapacidadeProducaoService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<EstagioCapacidadeProducao> findEstagios() {
		return CapacidadeProducaoService.findEstagios();
	}

	@RequestMapping(value = "/artigos/{estagio}", method = RequestMethod.GET)
	public List<ArtigoCapacidadeProducao> findArtigosByEstagio(@PathVariable("estagio") int estagio) {
		return CapacidadeProducaoService.findArtigosByEstagio(estagio);
	}
	
	@RequestMapping(value = "/estagios-configurados", method = RequestMethod.GET)
	public List<EstagioCapacidadeProducao> findByCapacidadeConfigurada() {
		return CapacidadeProducaoService.findEstagiosCapacidadeConfigurada();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public List<EstagioCapacidadeProducao> saveEstagios(@RequestBody BodyCapacidadeProducao bodyCapacidadeProducao) {
		CapacidadeProducaoService.saveEstagios(bodyCapacidadeProducao.estagiosCapacidadeProducao);
		return CapacidadeProducaoService.findEstagios();
	}

	@RequestMapping(value = "/artigos", method = RequestMethod.POST) 
	public List<ArtigoCapacidadeProducao> saveArtigos(@RequestBody BodyCapacidadeProducao bodyCapacidadeProducao) {
		CapacidadeProducaoService.saveArtigos(bodyCapacidadeProducao.estagio, bodyCapacidadeProducao.artigosCapacidadeProducao);
		return CapacidadeProducaoService.findArtigosByEstagio(bodyCapacidadeProducao.estagio);
	}
}
