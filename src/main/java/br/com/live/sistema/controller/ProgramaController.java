package br.com.live.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.live.sistema.body.BodyPrograma;
import br.com.live.sistema.entity.Programa;
import br.com.live.sistema.model.ProgramaConsulta;
import br.com.live.sistema.repository.ProgramaRepository;
import br.com.live.sistema.service.ProgramaService;

@RestController
@CrossOrigin
@RequestMapping("/programas")
public class ProgramaController {

    private final ProgramaRepository programaRepository;
    private final ProgramaService programaService;

    @Autowired
    public ProgramaController(ProgramaRepository programaRepository, ProgramaService programaService) {
          this.programaRepository = programaRepository;
          this.programaService = programaService;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Programa> findAll() {
          return programaRepository.findAll();
    }
    
	@RequestMapping(value = "/{idPrograma}", method = RequestMethod.GET)
	public Programa findByIdPrograma(@PathVariable("idPrograma") long idPrograma) {
		return programaRepository.findByIdPrograma(idPrograma);
	}
	
	@RequestMapping(value = "/path/{path}", method = RequestMethod.GET)
	public Programa findByPath(@PathVariable("path") String path) {
		return programaRepository.findProgramaByPath("/" + path);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Programa saveHelpPrograma(@RequestBody BodyPrograma body) {
		return programaService.saveHelpPrograma(body.id, body.help);
	}
	
	@RequestMapping(value = "descricao/{descricao}", method = RequestMethod.GET)
	public Programa findProgramaByDesc(@PathVariable("descricao") String descricao) {
		return programaRepository.findProgramaByDescricao(descricao);
	}

	@PostMapping("/programas-area-usuario")
	public List<ProgramaConsulta> findProgramasByListaModulosAndUsuarios(@RequestBody BodyPrograma body) {
		return programaService.findProgramasByListaModulosAndUsuarios(body.listaModulos, body.listaUsuarios);
	}
}