package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.Programa;
import br.com.live.repository.ProgramaRepository;

@RestController
@CrossOrigin
@RequestMapping("/programas")
public class ProgramaController {

    private ProgramaRepository programaRepository;

    @Autowired
    public ProgramaController(ProgramaRepository programaRepository) {
          this.programaRepository = programaRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Programa> findAll() {
          return programaRepository.findAll();
    }
    
	@RequestMapping(value = "/{idPrograma}", method = RequestMethod.GET)
	public Programa findByIdPrograma(@PathVariable("idPrograma") long idPrograma) {
		return programaRepository.findByIdPrograma(idPrograma);
	}
	
}