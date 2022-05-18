package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.repository.UsuarioRepository;
import br.com.live.repository.UsuarioProgramaRepository;
import br.com.live.custom.UsuarioCustom;
import br.com.live.entity.Usuario;
import br.com.live.entity.UsuarioPrograma;
import br.com.live.model.DadosUsuario;
import br.com.live.util.Criptografia;

@Service
@Transactional
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioProgramaRepository usuarioProgramaRepository;
	private final UsuarioCustom usuarioCustom;

	public UsuarioService(UsuarioRepository usuarioRepository, UsuarioCustom usuarioCustom, UsuarioProgramaRepository usuarioProgramaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioCustom = usuarioCustom;
		this.usuarioProgramaRepository = usuarioProgramaRepository;
	}

	public Usuario findUsuarios(long idUsuario) {
		return usuarioRepository.findByIdUsuario(idUsuario);
	}
	public Usuario saveUsuario(long idUsuario, String nome, String usuario, String senha, List <Long> listaIdsProgramas, int situacao, int liberaInspecaoQualidade, String email, int usuarioRepositor, String codUsuarioSystextil, int empresaSystextil) {
		
		Usuario dadosUsuario = null;
		
		// EDIÇÃO
		if (idUsuario > 0) {
			dadosUsuario = usuarioRepository.findByIdUsuario(idUsuario);

			dadosUsuario.nome = nome;
			dadosUsuario.usuario = usuario.toLowerCase();
			dadosUsuario.senha = Criptografia.criptografar(senha);
			dadosUsuario.situacao = situacao;
			dadosUsuario.liberaInspecaoQualidade = liberaInspecaoQualidade;
			dadosUsuario.email = email;
			dadosUsuario.usuarioRepositor = usuarioRepositor;
			dadosUsuario.usuarioSystextil = codUsuarioSystextil;
			dadosUsuario.empresaSystextil = empresaSystextil;
			
		// INSERÇÃO
		} else {
			idUsuario = usuarioCustom.findNextIdUsuario();
			
			dadosUsuario = new Usuario(idUsuario, nome, usuario.toLowerCase(), Criptografia.criptografar(senha), situacao, liberaInspecaoQualidade, email, usuarioRepositor, codUsuarioSystextil, empresaSystextil);
		}
		
		usuarioRepository.save(dadosUsuario);
		
		saveProgramas(dadosUsuario.id, listaIdsProgramas);
		
		return dadosUsuario;
	}
	
	public void saveProgramas(long idUsuario, List <Long> listaIdsProgramas) {
	
		usuarioProgramaRepository.deleteByIdUsuario(idUsuario);
		
		for (long idPrograma : listaIdsProgramas) {
			
			UsuarioPrograma usuarioPrograma = new UsuarioPrograma (idUsuario, idPrograma);
			usuarioProgramaRepository.save(usuarioPrograma);
		}
	}

	public List <Long> findIdsProgramasByUsuario(long idUsuario) {
		
		List<UsuarioPrograma> programas = usuarioProgramaRepository.findByIdUsuario(idUsuario);
		
		List <Long> listaIdsProgramas = new ArrayList<Long>();
		
		for (UsuarioPrograma programa : programas) {
			listaIdsProgramas.add(programa.idPrograma);
		}
		return listaIdsProgramas;
	}
	
	public DadosUsuario findDadosUsuario(long idUsuario) {
		return usuarioCustom.findDadosUsuario(idUsuario);
	}
	
	public void deleteByIdUsuario(long idUsuario) {
		usuarioProgramaRepository.deleteByIdUsuario(idUsuario);
		usuarioRepository.deleteById(idUsuario);
	}
	
	public String findPathPrograma(long idUsuario, String descricao) {
		return usuarioCustom.findPathPrograma(idUsuario, descricao);
	}
	
	public Usuario findByIdUsuario(long idUsuario) {		
		Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);		
		return new Usuario(usuario.id,usuario.nome,usuario.usuario,Criptografia.descriptografar(usuario.senha),usuario.situacao, usuario.liberaInspecaoQualidade, usuario.email, usuario.usuarioRepositor, usuario.usuarioSystextil, usuario.empresaSystextil);
	}
	
	public Usuario findByUsuarioSenha(String usuario, String senha) {
		
		Usuario dadosUsuario = usuarioRepository.findByUsuarioSenha(usuario, Criptografia.criptografar(senha));
		
		return dadosUsuario;
	}
	
	public boolean redefinirSenha(String usuario, String senhaAtual, String novaSenha) {
		
		boolean redefiniuSenha = false;
		
		Usuario dadosUsuario = usuarioRepository.findByUsuarioSenha(usuario, Criptografia.criptografar(senhaAtual));
		
		if (dadosUsuario != null) {
			
			dadosUsuario.senha = Criptografia.criptografar(novaSenha);
			
			usuarioRepository.save(dadosUsuario);
	
			redefiniuSenha = true;
		}
		
		return redefiniuSenha;
	}
	
	public int findCodigoUsuarioSystextil(long idUsuarioOrion) {	
		Usuario usuarioOrion = findUsuarios(idUsuarioOrion);		
		return usuarioCustom.findCodigoUsuarioSystextil(usuarioOrion.usuarioSystextil);
	}	
}