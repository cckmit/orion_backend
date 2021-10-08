package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.repository.UsuarioBiRepository;
import br.com.live.service.UsuarioBiService;
import br.com.live.body.BodyUsuarioBi;
import br.com.live.custom.UsuarioBiCustom;
import br.com.live.entity.ProgramaBi;
import br.com.live.entity.UsuarioBi;
import br.com.live.model.FiltroProgramaBi;

@RestController
@CrossOrigin
@RequestMapping("/usuarios-bi")
public class UsuarioBiController {
    private UsuarioBiRepository usuarioBiRepository;
    private UsuarioBiService usuarioBiService;
    private UsuarioBiCustom usuarioBiCustom;

    @Autowired
    public UsuarioBiController(UsuarioBiRepository usuarioBiRepository, UsuarioBiService usuarioBiService, UsuarioBiCustom usuarioBiCustom) {
          this.usuarioBiRepository = usuarioBiRepository;
          this.usuarioBiService = usuarioBiService;
          this.usuarioBiCustom = usuarioBiCustom;
    }
    
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.DELETE)
	public List<UsuarioBi> deleteById(@PathVariable("idUsuario") long idUsuario) {
		usuarioBiService.deleteByCodUsuario(idUsuario);
		return findAll();
	}
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<UsuarioBi> findAll() {
          return usuarioBiRepository.findAll(); 
    }
    
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
	public UsuarioBi findByIdUsuario(@PathVariable("idUsuario") long idUsuario) {
		return usuarioBiRepository.findByCodUsuario(idUsuario);
	}
	
	@RequestMapping(value = "/programas-usuario/{idUsuario}", method = RequestMethod.GET)
	public List<String> findByCodUsuario(@PathVariable("idUsuario") long idUsuario) {
		return usuarioBiService.findIdsProgramasByUsuario(idUsuario);
	}
	
	@RequestMapping(value = "/programas-por-usuario/{idUsuario}", method = RequestMethod.GET)
	public List<ProgramaBi> findProgramasByUsuario(@PathVariable("idUsuario") long idUsuario) {
		return usuarioBiService.findProgramasPorUsuario(idUsuario);
	}
	
	@RequestMapping(value = "/tiposEmail-por-usuario/{idUsuario}/{idPrograma}", method = RequestMethod.GET)
	public List<String> findTiposEmailByUsuario(@PathVariable("idUsuario") long idUsuario, @PathVariable("idPrograma") String idPrograma) {
		return usuarioBiService.findTiposEmailByUsuario(idUsuario, idPrograma);
	}
	
	@RequestMapping(value = "/programas-usuario/filtrar/{chavePesquisa}", method = RequestMethod.GET)
	public List<FiltroProgramaBi> findProgramasByUsuario(@PathVariable("chavePesquisa") String chavePesquisa) {
		return usuarioBiCustom.filtrarProgramas(chavePesquisa);
	}
	
	@RequestMapping(value = "/programas-usuario", method = RequestMethod.POST)
	public List<ProgramaBi> saveProgramasUsuario(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.saveProgramas(body.id, body.listaIdsProgramas);
		return usuarioBiService.findProgramasPorUsuario(body.id);
	}

	@RequestMapping(value = "/adicionar-programas-usuario", method = RequestMethod.POST)
	public List<ProgramaBi> adicionarProgramasUsuario(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.adicionarProgramas(body.id, body.listaIdsProgramas);
		return usuarioBiService.findProgramasPorUsuario(body.id);
	}

	@RequestMapping(value = "/sobrepor-programas-usuario", method = RequestMethod.POST)
	public List<ProgramaBi> sobreporProgramasUsuario(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.sobreporProgramas(body.id, body.listaIdsProgramas);
		return usuarioBiService.findProgramasPorUsuario(body.id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public UsuarioBi saveUsuario(@RequestBody BodyUsuarioBi body) {
		return usuarioBiService.saveUsuario(body.id, body.nome, body.usuario, body.senha, body.situacao, body.administrador, body.email);
	}
	
	@RequestMapping(value = "/insere-tipo-email", method = RequestMethod.POST)
	public void insereTipoEmail(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.insereTiposEmailSelecionado(body.id, body.idPrograma, body.idTipoEmail);
	}
	
	@RequestMapping(value = "/excluir-tipo-email", method = RequestMethod.POST)
	public void excluiTipoEmail(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.excluiTiposEmailSelecionado(body.id, body.idPrograma, body.idTipoEmail);
	}
	
	@RequestMapping(value = "/insere-all-tipo-email", method = RequestMethod.POST)
	public void insereAllTipoEmail(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.insereTodosTiposEmailSelecionado(body.id, body.idPrograma);
	}
	
	@RequestMapping(value = "/excluir-all-tipo-email", method = RequestMethod.POST)
	public void excluiAllTipoEmail(@RequestBody BodyUsuarioBi body) {
		usuarioBiService.excluiTodosTiposEmailSelecionado(body.id, body.idPrograma);
	}
	
	@RequestMapping(value = "/validar-usuario/{idUsuario}/{usuario}", method = RequestMethod.GET)
	public boolean validarUsuario(@PathVariable("idUsuario") long idUsuario, @PathVariable("usuario") String usuario) {
		return usuarioBiService.existsUsuario(idUsuario, usuario);
	}

}
