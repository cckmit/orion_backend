package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.Usuario;
import br.com.live.model.DadosUsuario;
import br.com.live.repository.UsuarioRepository;
import br.com.live.service.UsuarioService;
import br.com.live.util.BodyUsuario;

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
		return usuarioService.saveUsuario(body.id, body.nome, body.usuario, body.senha, body.listaIdsProgramas, body.situacao);
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
	public String findPathPrograma(@PathVariable("idUsuario") long idUsuario, @PathVariable("descricao") String descricao) {
		return usuarioService.findPathPrograma(idUsuario, descricao);
	}
	
	@RequestMapping(value = "usuario-redefinir/{usuario}/{senhaAtual}/{novaSenha}", method = RequestMethod.GET)
	public boolean redefinirSenhaUsuario(@PathVariable("usuario") String usuario, @PathVariable("senhaAtual") String senhaAtual, @PathVariable("novaSenha") String novaSenha) {
		return usuarioService.redefinirSenha(usuario, senhaAtual, novaSenha);
	}
	
}
