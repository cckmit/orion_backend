package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.InspecaoQualidade;
import br.com.live.entity.InspecaoQualidadeLanctoMedida;
import br.com.live.model.ConsultaInspecaoQualidLanctoPecas;
import br.com.live.model.MotivoRejeicao;
import br.com.live.model.TipoMedida;
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
	
	@RequestMapping(value = "/inspecao/{id}", method = RequestMethod.GET)
	public InspecaoQualidade findInspecaoQualidadeById(@PathVariable("id") int id) {
		return inspecaoQualidadeService.findInspecaoQualidadeById(id);
	}
	
	@RequestMapping(value = "/inspecoes-ordem-estagio/{ordemProducao}/{ordemConfeccao}/{codEstagio}/{tipo}", method = RequestMethod.GET)
	public List<InspecaoQualidade> findInspecoesQualidadeByOrdemEstagioTipo(@PathVariable("ordemProducao") int ordemProducao, @PathVariable("ordemConfeccao") int ordemConfeccao, @PathVariable("codEstagio") int codEstagio, @PathVariable("tipo") int tipo) {
		return inspecaoQualidadeService.findInspecoesQualidadeByOrdemEstagioTipo(ordemProducao, ordemConfeccao, codEstagio, tipo);
	}
	
	@RequestMapping(value = "/tipos-medida/{referencia}", method = RequestMethod.GET)
	public List<TipoMedida> findTiposMedidasByReferencia(@PathVariable("referencia") String referencia) {
		return inspecaoQualidadeService.findTiposMedidasByReferencia(referencia);
	}
	
	@RequestMapping(value = "/medidas/{referencia}/{tamanho}/{tipoMedida}", method = RequestMethod.GET)
	public List<InspecaoQualidadeLanctoMedida> findMedidasByReferenciaTamanhoTipo(@PathVariable("referencia") String referencia, @PathVariable("tamanho") String tamanho, @PathVariable("tipoMedida") int tipoMedida) {
		return inspecaoQualidadeService.findMedidasByReferenciaTamanhoTipo(referencia, tamanho, tipoMedida);
	}
	
	@RequestMapping(value = "/lanctos-pecas/{idInspecao}", method = RequestMethod.GET)
	public List<ConsultaInspecaoQualidLanctoPecas> findLancamentoPecasByIdInspecao(@PathVariable("idInspecao") long idInspecao) {		
		return inspecaoQualidadeService.findLancamentoPecasByIdInspecao(idInspecao);
	}
	
	@RequestMapping(value = "/salvar-inspecao-peca", method = RequestMethod.POST)	
	public InspecaoQualidade saveInspecaoQualidadePeca(@RequestBody BodyInspecaoQualidade body) {
		return inspecaoQualidadeService.saveInspecaoQualidadePeca(body.inspecaoQualidade, body.inspecaoQualidadeLanctoPeca, body.dataInspecao);		
	}
		
	@RequestMapping(value = "lancto-peca/{idInspecao}/{idLancamento}", method = RequestMethod.DELETE)	
	public InspecaoQualidade deleteInspecaoQualidadeLanctoPeca(@PathVariable("idInspecao") long idInspecao, @PathVariable("idLancamento") long idLancamento) {
		return inspecaoQualidadeService.deleteInspecaoQualidadeLanctoPeca(idInspecao, idLancamento);		 
	}	
	
	@RequestMapping(value = "inspecao/{idInspecao}", method = RequestMethod.DELETE)
	public List<InspecaoQualidade> deleteInspecaoQualidade(@PathVariable("idInspecao") long idInspecao) {
		return inspecaoQualidadeService.deleteInspecaoQualidade(idInspecao);
	}	
}