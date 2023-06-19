package br.com.live.controller;

import java.util.List;

import br.com.live.entity.Programa;
import br.com.live.util.ConteudoChaveAlfaNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.live.body.BodyUsuario;
import br.com.live.entity.Usuario;
import br.com.live.model.DadosUsuario;
import br.com.live.repository.UsuarioRepository;
import br.com.live.service.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
          this.usuarioRepository = usuarioRepository;
          this.usuarioService = usuarioService;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Usuario> findAll() {
          return usuarioRepository.findAll(); 
    }
    
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
	public Usuario findByIdUsuario(@PathVariable("idUsuario") long idUsuario) {
		return usuarioService.findByIdUsuario(idUsuario);
	}
	
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.DELETE)
	public List<Usuario> deleteById(@PathVariable("idUsuario") long idUsuario) {
		usuarioService.deleteByIdUsuario(idUsuario);
		return findAll();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Usuario saveUsuario(@RequestBody BodyUsuario body) {
		return usuarioService.saveUsuario(body.id, body.nome, body.usuario, body.senha, body.listaIdsProgramas, body.situacao, body.liberaInspecaoQualidade, body.email, body.usuarioRepositor, body.usuarioSystextil, body.empresaSystextil);
	}
	
	@RequestMapping(value = "programas-usuario/{idUsuario}", method = RequestMethod.GET)
	public List<Long> findByIdUsuarioPrograma(@PathVariable("idUsuario") long idUsuario) {
		return usuarioService.findIdsProgramasByUsuario(idUsuario);
	}
	
	@RequestMapping(value = "/{usuario}/{senha}", method = RequestMethod.GET)
	public Usuario findByUsuarioSenha(@PathVariable("usuario") String usuario, @PathVariable("senha") String senha) {
		return usuarioService.findByUsuarioSenha(usuario, senha);
	}
	
	@RequestMapping(value = "programas-navbar/{idUsuario}", method = RequestMethod.GET)
	public DadosUsuario findDadosUsuario(@PathVariable("idUsuario") long idUsuario) {
		return usuarioService.findDadosUsuario(idUsuario);
	}
	
	@RequestMapping(value = "programa-like/{idUsuario}/{descricao}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findPathPrograma(@PathVariable("idUsuario") long idUsuario, @PathVariable("descricao") String descricao) {
		return usuarioService.findPathPrograma(idUsuario, descricao);
	}
	
	@RequestMapping(value = "usuario-redefinir/{usuario}/{senhaAtual}/{novaSenha}", method = RequestMethod.GET)
	public boolean redefinirSenhaUsuario(@PathVariable("usuario") String usuario, @PathVariable("senhaAtual") String senhaAtual, @PathVariable("novaSenha") String novaSenha) {
		return usuarioService.redefinirSenha(usuario, senhaAtual, novaSenha);
	}

	@GetMapping("find-all-modulos")
	public List<ConteudoChaveAlfaNum> findAllModulos(){
		return usuarioService.findAllModulos();
	}

	@PostMapping("/adicionar-programas-usuario")
	public List<Programa> adicionarProgramasUsuario(@RequestBody BodyUsuario body) {
		usuarioService.adicionarProgramas(body.id, body.listaIdsProgramas);
		return usuarioService.findProgramasPorUsuario(body.id);
	}

	@PostMapping("/sobrepor-programas-usuario")
	public List<Programa> sobreporProgramasUsuario(@RequestBody BodyUsuario body) {
		usuarioService.sobreporProgramas(body.id, body.listaIdsProgramas);
		return usuarioService.findProgramasPorUsuario(body.id);
	}
}
