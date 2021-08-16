package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.ProgramaBi;
import br.com.live.entity.TiposEmailBi;
import br.com.live.repository.ProgramaBiRepository;
import br.com.live.repository.TiposEmailBiRepository;
import br.com.live.service.ProgramaBiService;
import br.com.live.util.BodyProgramaBi;

@RestController
@CrossOrigin
@RequestMapping("/programas-bi")
public class ProgramaBiController {

    private final ProgramaBiRepository programaBiRepository;
    private final ProgramaBiService programaBiService;
    private final TiposEmailBiRepository tiposEmailBiRepository;

    @Autowired
    public ProgramaBiController(ProgramaBiRepository programaBiRepository, ProgramaBiService programaBiService, TiposEmailBiRepository tiposEmailBiRepository) {
          this.programaBiRepository = programaBiRepository;
          this.programaBiService = programaBiService;
          this.tiposEmailBiRepository = tiposEmailBiRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ProgramaBi> findAll() {
          return programaBiRepository.findAll();
    }
    
	@RequestMapping(value = "/{idPrograma}", method = RequestMethod.GET)
	public ProgramaBi findByIdPrograma(@PathVariable("idPrograma") String idPrograma) {
		return programaBiRepository.findByIdPrograma(idPrograma);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public List<ProgramaBi> savePrograma(@RequestBody BodyProgramaBi body) {
		programaBiService.saveProgramaBi(body.id, body.areaModulo, body.atividade, body.descricao, body.ferramenta, body.frequencia, body.planilha, body.extrator, body.help, body.tiposEmail);
		return programaBiRepository.findAll();
	}
	
	@RequestMapping(value = "/{idPrograma}", method = RequestMethod.DELETE)
	public List<ProgramaBi> deleteById(@PathVariable("idPrograma") String idPrograma) {
		programaBiService.deleteByIdPrograma(idPrograma);
		return findAll();
	}
	
	@RequestMapping(value = "tipos-email/{idPrograma}", method = RequestMethod.GET)
	public List<TiposEmailBi> findTipoEmailByIdPrograma(@PathVariable("idPrograma") String idPrograma) {
		return tiposEmailBiRepository.findByIdPrograma(idPrograma);
	}
	
}