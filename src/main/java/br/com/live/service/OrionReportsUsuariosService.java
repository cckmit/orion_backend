package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.entity.Indicadores;
import br.com.live.entity.OrionReportsUsuarios;
import br.com.live.repository.OrionReportsUsuariosRepository;

@Service
@Transactional
public class OrionReportsUsuariosService {
	
	private final OrionReportsUsuariosRepository orionReportsUsuariosRepository;
	
	public OrionReportsUsuariosService(OrionReportsUsuariosRepository orionReportsUsuariosRepository) {
		this.orionReportsUsuariosRepository = orionReportsUsuariosRepository;
		
	}
	
	public List<OrionReportsUsuarios> salvarUsuario(int id, String nome, String nomeUsuario, String email, String senha, int situacao, int administrador) {
		
		OrionReportsUsuarios dadosUsuario = orionReportsUsuariosRepository.findById(id);
		
		if (dadosUsuario == null) {
			dadosUsuario = new OrionReportsUsuarios(orionReportsUsuariosRepository.findNextID(), nome, nomeUsuario, senha, email, situacao, administrador);
		} else {
			dadosUsuario.nome = nome;
			dadosUsuario.usuario = nomeUsuario;
			dadosUsuario.senha = senha;
			dadosUsuario.email = email;
			dadosUsuario.situacao = situacao;
			dadosUsuario.administrador = administrador;
			
		}
		orionReportsUsuariosRepository.save(dadosUsuario);
		return orionReportsUsuariosRepository.findAll();
	}
	
	public OrionReportsUsuarios findUsuariolById(int id){
		return orionReportsUsuariosRepository.findById(id);
	}

}
