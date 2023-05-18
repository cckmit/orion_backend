package br.com.live.controller;

import java.util.List;

import br.com.live.repository.GestaoAtivosOportunidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.live.body.BodyGestaoAtivos;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.service.OportunidadesService;

@RestController
@CrossOrigin
@RequestMapping("/oportunidades")
public class OportunidadesController {
	
	private final OportunidadesService oportunidadesService;
    private final GestaoAtivosOportunidadeRepository oportunidadeRepository;
	
    @Autowired
    public OportunidadesController(OportunidadesService oportunidadesService, GestaoAtivosOportunidadeRepository oportunidadeRepository) {
    	this.oportunidadesService = oportunidadesService;
        this.oportunidadeRepository = oportunidadeRepository;
    }
    
    @RequestMapping(value = "/find-all-oportunidades", method = RequestMethod.GET)
    public List<ConsultaGestaoAtivos> findAllOportunidades() { 
    	return oportunidadesService.findAllOportunidades(); 
    }

    @RequestMapping(value = "/find-oportunidades-tipo/{tipo}", method = RequestMethod.GET)
    public List<ConsultaGestaoAtivos> findportunidadesTipo(@PathVariable("tipo") String tipo) {
        return oportunidadesService.findOportunidadesTipo(tipo);
    }
    
    @RequestMapping(value = "/find-consulta-coluna/{tipo}/{idOp}", method = RequestMethod.GET)
    public String findColunaConsulta(@PathVariable("tipo") String tipo, @PathVariable("idOp") String idOp) { 
    	return oportunidadesService.findColunaConsulta(tipo, idOp); 
    }
    
    @RequestMapping(value = "/save-oportunidade", method = RequestMethod.POST)
    public List<ConsultaGestaoAtivos> saveOportunidade(@RequestBody BodyGestaoAtivos oportunidade) {
    	oportunidadesService.saveOportunidade(oportunidade.id, oportunidade.tipo, oportunidade.dataCadastro, oportunidade.prioridade, oportunidade.descricao, oportunidade.objetivo,
        		oportunidade.contextualizacao, oportunidade.descricaoProblema, oportunidade.perguntasEmAberto, oportunidade.riscos);
        return oportunidadesService.findAllOportunidades();
    }

    @DeleteMapping("/delete-oportunidades/{id}")
    public List<ConsultaGestaoAtivos> deleteOportunidade(@PathVariable("id") String id ){
        oportunidadeRepository.deleteById(id);
        return oportunidadesService.findAllOportunidades();
    }
}
