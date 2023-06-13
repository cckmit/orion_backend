package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.entity.Indicadores;
import br.com.live.entity.OrionReportsUsuarioProgramas;
import br.com.live.entity.OrionReportsUsuarios;
import br.com.live.entity.UsuarioProgramaBi;
import br.com.live.repository.OrionReportsUsuariosProgramasRepository;
import br.com.live.repository.OrionReportsUsuariosRepository;

@Service
@Transactional
public class OrionReportsUsuariosService {
	
	private final OrionReportsUsuariosRepository orionReportsUsuariosRepository;
	private final OrionReportsUsuariosProgramasRepository orionReportsUsuariosProgramasRepository;
	
	public OrionReportsUsuariosService(OrionReportsUsuariosRepository orionReportsUsuariosRepository, OrionReportsUsuariosProgramasRepository orionReportsUsuariosProgramasRepository) {
		this.orionReportsUsuariosRepository = orionReportsUsuariosRepository;
		this.orionReportsUsuariosProgramasRepository = orionReportsUsuariosProgramasRepository;
		
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
	
	public List<String> findIdsProgramasByUsuario(int idUsuario) {

		List<OrionReportsUsuarioProgramas> programas = orionReportsUsuariosProgramasRepository.findByCodUsuario(idUsuario);

		List<String> listaIdsProgramas = new ArrayList<String>();

		for (OrionReportsUsuarioProgramas programa : programas) {
			listaIdsProgramas.add(programa.idPrograma);
		}
		return listaIdsProgramas;
	}
	
	public OrionReportsUsuarios findUsuariolById(int id){
		return orionReportsUsuariosRepository.findById(id);
	}
	
	public List<OrionReportsUsuarios> deleteUsuario(int id){
		orionReportsUsuariosRepository.deleteById(id);
		return orionReportsUsuariosRepository.findAll();
	}
	
	public void saveProgramas(int idUsuario, List<String> listaIdsProgramas) {
		
		List<OrionReportsUsuarioProgramas> programasAtuais = orionReportsUsuariosProgramasRepository.findByCodUsuario(idUsuario);
		
		for (OrionReportsUsuarioProgramas dadosPrograma : programasAtuais) {
			if (!listaIdsProgramas.contains(dadosPrograma.idPrograma)) {				
				orionReportsUsuariosProgramasRepository.deleteByIdUsuarioAndIdPrograma(idUsuario, dadosPrograma.idPrograma);
			}
		}
		adicionarProgramas(idUsuario, listaIdsProgramas);
	}
	
	public void adicionarProgramas(int codUsuario, List<String> listaIdsProgramas) {
		for (String idPrograma : listaIdsProgramas) {
			adicionarPrograma(codUsuario, idPrograma);
		}
	}
	
	private void adicionarPrograma(int idUsuario, String idPrograma) {
		
		OrionReportsUsuarioProgramas usuarioProgramaBi = orionReportsUsuariosProgramasRepository.findByIdUsuarioAndIdPrograma(idUsuario, idPrograma);
		
		if (usuarioProgramaBi == null) {			
			usuarioProgramaBi = new OrionReportsUsuarioProgramas(idUsuario + "-" + idPrograma, idUsuario, idPrograma);
			orionReportsUsuariosProgramasRepository.save(usuarioProgramaBi);
		}
	}

}
