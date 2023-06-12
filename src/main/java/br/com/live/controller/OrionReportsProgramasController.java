package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyOrionReports;
import br.com.live.entity.OrionReportsProgramas;
import br.com.live.entity.OrionReportsUsuarios;
import br.com.live.model.ConsultaProgramasOrionReport;
import br.com.live.repository.OrionReportsProgramasRepository;
import br.com.live.service.OrionReportsProgramasService;
import br.com.live.util.ConteudoChaveAlfaNum;

@RestController
@CrossOrigin
@RequestMapping("/orion-reports-programas")
public class OrionReportsProgramasController {
	
	private OrionReportsProgramasRepository orionReportsProgramasRepository;
	private OrionReportsProgramasService orionReportsProgramasService;
	
	@Autowired 
	public OrionReportsProgramasController(OrionReportsProgramasRepository orionReportsProgramasRepository, OrionReportsProgramasService orionReportsProgramasService){
		this.orionReportsProgramasRepository = orionReportsProgramasRepository;
		this.orionReportsProgramasService = orionReportsProgramasService;				
	}
	
	@RequestMapping(value = "/find-all-programas", method = RequestMethod.GET)
    public List<ConsultaProgramasOrionReport> findAllProgramas() {
        return orionReportsProgramasService.findAllProgramas();
    }
	
	@RequestMapping(value = "/find-all-areas", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllAreas() {
        return orionReportsProgramasService.findAllAreas();
    }
	
	@RequestMapping(value = "/find-all-modulos", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllModulos() {
        return orionReportsProgramasService.findAllModulos();
    }
	
	@RequestMapping(value = "/find-all-modulos/{area}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllModulosPorArea(@PathVariable("area") String area) {
        return orionReportsProgramasService.findAllModulosPorArea(area);
    }
	
	@RequestMapping(value = "/salvar-programa", method = RequestMethod.POST)
    public List<ConsultaProgramasOrionReport> salvarPrograma(@RequestBody BodyOrionReports body) {
    	return orionReportsProgramasService.salvarPrograma(body.idPrograma, body.area, body.modulo, body.descricao, body.situacao, body.powerbi, body.linkPowerbi);
    }
	
	@RequestMapping(value = "/find-area-by-id/{idPrograma}", method = RequestMethod.GET)
    public String findAreaaById(@PathVariable("idPrograma") String idPrograma) {
    	return orionReportsProgramasService.findAreaById(idPrograma);
    }
	
	@RequestMapping(value = "/find-programa-by-id/{idPrograma}", method = RequestMethod.GET)
    public OrionReportsProgramas findProgramaById(@PathVariable("idPrograma") String idPrograma) {
    	return orionReportsProgramasService.findProgramaById(idPrograma);
    }
	
	@RequestMapping(value = "/delete-programa/{id}", method = RequestMethod.DELETE)
	public List<ConsultaProgramasOrionReport> deletePrograma(@PathVariable("id") String id) {                  
		 return orionReportsProgramasService.deletePrograma(id);
	}

}