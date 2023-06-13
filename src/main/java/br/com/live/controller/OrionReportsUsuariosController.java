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
import br.com.live.body.BodyUsuarioBi;
import br.com.live.entity.OrionReportsProgramas;
import br.com.live.entity.OrionReportsUsuarios;
import br.com.live.entity.ProgramaBi;
import br.com.live.entity.TipoPonto;
import br.com.live.repository.OrionReportsUsuariosRepository;
import br.com.live.service.OrionReportsUsuariosService;

@RestController
@CrossOrigin
@RequestMapping("/orion-reports-usuarios")
public class OrionReportsUsuariosController {
	
	private OrionReportsUsuariosService orionReportsUsuariosService;
	private OrionReportsUsuariosRepository orionReportsUsuariosRepository;
	
	@Autowired 
	public OrionReportsUsuariosController(OrionReportsUsuariosService orionReportsUsuariosService, OrionReportsUsuariosRepository orionReportsUsuariosRepository){
		this.orionReportsUsuariosService = orionReportsUsuariosService;		
		this.orionReportsUsuariosRepository = orionReportsUsuariosRepository;
		
	}
	
	@RequestMapping(value = "/find-all-usuarios", method = RequestMethod.GET)
    public List<OrionReportsUsuarios> findAllUsuarios() {
        return orionReportsUsuariosRepository.findAll();
    }
	
	@RequestMapping(value = "/programas-usuario/{idUsuario}", method = RequestMethod.GET)
	public List<String> findByCodUsuario(@PathVariable("idUsuario") int idUsuario) {
		return orionReportsUsuariosService.findIdsProgramasByUsuario(idUsuario);			
	}
	
	@RequestMapping(value = "/salvar-usuario", method = RequestMethod.POST)
    public List<OrionReportsUsuarios> salvarUsuario(@RequestBody BodyOrionReports body) {
    	return orionReportsUsuariosService.salvarUsuario(body.id, body.nome, body.nomeUsuario, body.email, body.senha, body.situacao, body.administrador);
    }
	
	@RequestMapping(value = "/programas-usuario", method = RequestMethod.POST)
	public void saveProgramasUsuario(@RequestBody BodyOrionReports body) {
		orionReportsUsuariosService.saveProgramas(body.id, body.listaIdsProgramas);
	}
	
	@RequestMapping(value = "/find-usuario-by-id/{id}", method = RequestMethod.GET)
    public OrionReportsUsuarios findUsuariolById(@PathVariable("id") int id) {
    	return orionReportsUsuariosService.findUsuariolById(id);
    }
	
	@RequestMapping(value = "/delete-usuario/{id}", method = RequestMethod.DELETE)
	public List<OrionReportsUsuarios> deleteUsuario(@PathVariable("id") int id) {                  
		 return orionReportsUsuariosService.deleteUsuario(id);
	}
	

}
