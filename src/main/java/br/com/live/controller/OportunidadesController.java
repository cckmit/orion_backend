package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyGestaoAtivos;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.service.OportunidadesService;

@RestController
@CrossOrigin
@RequestMapping("/oportunidades")
public class OportunidadesController {
	
	private final OportunidadesService oportunidadesService;  
	
    @Autowired
    public OportunidadesController(OportunidadesService oportunidadesService) {
    	this.oportunidadesService = oportunidadesService;
        
    }
    
    @RequestMapping(value = "/find-all-oportunidades", method = RequestMethod.GET)
    public List<ConsultaGestaoAtivos> findAllOportunidades() { 
    	return oportunidadesService.findAllOportunidades(); 
    }
    
    @RequestMapping(value = "/find-consulta-coluna/{tipo}/{idOp}", method = RequestMethod.GET)
    public String findColunaConsulta(@PathVariable("tipo") String tipo, @PathVariable("idOp") String idOp) { 
    	return oportunidadesService.findColunaConsulta(tipo, idOp); 
    }
    
    @RequestMapping(value = "/save-oportunidade", method = RequestMethod.POST)
    public List<ConsultaGestaoAtivos> saveOportunidade(@RequestBody BodyGestaoAtivos oportunidade) {
    	oportunidadesService.saveOportunidade(oportunidade.id, oportunidade.tipo, oportunidade.idAtivo, oportunidade.nomeAtivo, oportunidade.dataCadastro, oportunidade.prioridade, oportunidade.descricao, oportunidade.objetivo,
        		oportunidade.contextualizacao, oportunidade.descricaoProblema, oportunidade.perguntasEmAberto, oportunidade.riscos);
        return oportunidadesService.findAllOportunidades();
    }
}
