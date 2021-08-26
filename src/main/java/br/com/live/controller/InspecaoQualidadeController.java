package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.InspecaoQualidadePeca;
import br.com.live.model.MotivoRejeicao;
import br.com.live.service.InspecaoQualidadeService;
import br.com.live.util.BodyInspecaoQualidade;

@RestController
@CrossOrigin
@RequestMapping("/inspecao-qualidade")
public class InspecaoQualidadeController {

	@Autowired
	private InspecaoQualidadeService inspecaoQualidadeService;

	@RequestMapping(value = "/ordem-confeccao/{talao}", method = RequestMethod.GET)
	public BodyInspecaoQualidade findOrdemConfeccaoByTalao(@PathVariable("talao") String talao) {
		return inspecaoQualidadeService.findOrdemConfeccaoByTalao(talao);
	}
	
	@RequestMapping(value = "/motivos", method = RequestMethod.GET)
	public List<MotivoRejeicao> findAllMotivos() {
		return inspecaoQualidadeService.findAllMotivos();
	}
	
	@RequestMapping(value = "/salvar-inspecao-peca", method = RequestMethod.POST)	
	public InspecaoQualidadePeca saveInspecaoQualidadePeca(@RequestBody BodyInspecaoQualidade body) {
		return inspecaoQualidadeService.saveInspecaoQualidadePeca(body.inspecaoQualidadePeca, body.inspecaoQualidadePecaLancto, body.dataInspecao);		
	}
}
