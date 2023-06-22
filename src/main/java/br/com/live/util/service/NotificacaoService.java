package br.com.live.util.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.sistema.entity.Usuario;
import br.com.live.sistema.repository.UsuarioRepository;
import br.com.live.util.entity.Notificacao;
import br.com.live.util.repository.NotificacaoRepository;

@Service
@Transactional
public class NotificacaoService {
	
	private final UsuarioRepository usuarioRepository;
	private final NotificacaoRepository notificacaoRepository;

	public NotificacaoService(UsuarioRepository usuarioRepository, NotificacaoRepository notificacaoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.notificacaoRepository = notificacaoRepository;
	}
	
	public List<String> findEmailByTipoNotificacao(int tipoNotificacao) {
		List<String> emails = new ArrayList<String>();
		List<Notificacao> usuariosNot = notificacaoRepository.findByTipoNotificacao(tipoNotificacao);
		
		for (Notificacao dadosNot : usuariosNot) {
			Usuario dadosUsuario = usuarioRepository.findByIdUsuario(dadosNot.idUsuario);		
			emails.add(dadosUsuario.email);
		}
		return emails;
	}
}
