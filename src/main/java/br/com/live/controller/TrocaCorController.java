package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyTrocaCor;
import br.com.live.model.TrocaCor;
import br.com.live.service.TrocaCorService;

@RestController
@CrossOrigin
@RequestMapping("/troca-cor")
public class TrocaCorController {

	private TrocaCorService trocaCorService;

	@Autowired
	public TrocaCorController(TrocaCorService trocaCorService) {
		this.trocaCorService = trocaCorService;
	}

	@RequestMapping(value = "/find-grupo/{ordemProducao}", method = RequestMethod.GET)
	public TrocaCor findGrupoByOrdem(@PathVariable("ordemProducao") int ordemProducao) {
		return trocaCorService.findGrupoOrdemProducao(ordemProducao);
	}
	
	@RequestMapping(value = "/find-tamamhos/{nivel}/{grupo}", method = RequestMethod.GET)
	public List<TrocaCor> findTamanhosByRef(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo) {
		return trocaCorService.findTamanhosByRef(nivel, grupo);
	}
	
	@RequestMapping(value = "/find-cores/{nivel}/{grupo}/{subGrupo}", method = RequestMethod.GET)
	public List<TrocaCor> findTamanhosByRef(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("subGrupo") String subGrupo) {
		return trocaCorService.findCoresByTamanhos(nivel, grupo, subGrupo);
	}
	
	@RequestMapping(value = "/confirmar-troca/", method = RequestMethod.POST)
	public void confirmarTrocaCor(@RequestBody BodyTrocaCor body) {
		trocaCorService.confirmarTrocaCor(body.ordemProducao, body.subGrupo, body.item);
	}
	
	@RequestMapping(value = "/validar-ordem/{ordemProducao}", method = RequestMethod.GET)
	public boolean validarOrdemProduzida(@PathVariable("ordemProducao") int ordemProducao) {
		return trocaCorService.validarOrdemProduzida(ordemProducao);
	}
	
}
