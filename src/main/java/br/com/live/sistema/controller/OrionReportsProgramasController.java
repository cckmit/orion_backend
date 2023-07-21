package br.com.live.sistema.controller;

import java.util.List;

import br.com.live.sistema.entity.RelacEmailsProgramasOrionReports;
import br.com.live.sistema.repository.RelacEmailsProgramasOrionReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.sistema.body.BodyOrionReports;
import br.com.live.sistema.entity.OrionReportsProgramas;
import br.com.live.sistema.entity.OrionReportsUsuarios;
import br.com.live.sistema.model.ConsultaProgramasOrionReport;
import br.com.live.sistema.repository.OrionReportsProgramasRepository;
import br.com.live.sistema.service.OrionReportsProgramasService;
import br.com.live.util.ConteudoChaveAlfaNum;

@RestController
@CrossOrigin
@RequestMapping("/orion-reports-programas")
public class OrionReportsProgramasController {
	
	private OrionReportsProgramasRepository orionReportsProgramasRepository;
	private OrionReportsProgramasService orionReportsProgramasService;
	private RelacEmailsProgramasOrionReportsRepository relacEmailsProgramasOrionReportsRepository;
	
	@Autowired 
	public OrionReportsProgramasController(OrionReportsProgramasRepository orionReportsProgramasRepository, OrionReportsProgramasService orionReportsProgramasService,
										   RelacEmailsProgramasOrionReportsRepository relacEmailsProgramasOrionReportsRepository){
		this.orionReportsProgramasRepository = orionReportsProgramasRepository;
		this.orionReportsProgramasService = orionReportsProgramasService;
		this.relacEmailsProgramasOrionReportsRepository = relacEmailsProgramasOrionReportsRepository;
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

	@RequestMapping(value = "/find-emails-by-programa/{idPrograma}", method = RequestMethod.GET)
	public List<RelacEmailsProgramasOrionReports> findEmailsByPrograma(@PathVariable("idPrograma") String idPrograma) {
		return relacEmailsProgramasOrionReportsRepository.findEmailsByPrograma(idPrograma);
	}

	@RequestMapping(value = "/delete-email-programa/{id}/{idPrograma}", method = RequestMethod.DELETE)
	public List<RelacEmailsProgramasOrionReports> deleteEmailPrograma(@PathVariable("id") String id, @PathVariable("idPrograma") String idPrograma) {
		relacEmailsProgramasOrionReportsRepository.deleteById(id);
		return relacEmailsProgramasOrionReportsRepository.findEmailsByPrograma(idPrograma);
	}

	@RequestMapping(value = "/salvar-email-programa", method = RequestMethod.POST)
	public List<RelacEmailsProgramasOrionReports> salvarEmailsPrograma(@RequestBody BodyOrionReports body) {
		return orionReportsProgramasService.salvarEmailsPrograma(body.email, body.idPrograma);
	}

}