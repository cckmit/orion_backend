package br.com.live.sistema.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.sistema.custom.UsuarioBiCustom;
import br.com.live.sistema.entity.ProgramaBi;
import br.com.live.sistema.entity.TiposEmailBi;
import br.com.live.sistema.entity.UsuarioBi;
import br.com.live.sistema.entity.UsuarioProgramaBi;
import br.com.live.sistema.entity.UsuarioTipoEmailBi;
import br.com.live.sistema.repository.ProgramaBiRepository;
import br.com.live.sistema.repository.TiposEmailBiRepository;
import br.com.live.sistema.repository.UsuarioBiRepository;
import br.com.live.sistema.repository.UsuarioProgramaBiRepository;
import br.com.live.sistema.repository.UsuarioTipoEmailBiRepository;

@Service
@Transactional
public class UsuarioBiService {

	private final UsuarioBiRepository usuarioBiRepository;
	private final UsuarioProgramaBiRepository usuarioProgramaBiRepository;
	private final UsuarioBiCustom usuarioBiCustom;
	private final ProgramaBiRepository programaBiRepository;
	private final UsuarioTipoEmailBiRepository usuarioTipoEmailBiRepository;
	private final TiposEmailBiRepository tiposEmailBiRepository;	

	public UsuarioBiService(UsuarioBiRepository usuarioBiRepository,
			UsuarioProgramaBiRepository usuarioProgramaBiRepository, UsuarioBiCustom usuarioBiCustom,
			ProgramaBiRepository programaBiRepository, UsuarioTipoEmailBiRepository usuarioTipoEmailBiRepository,
			TiposEmailBiRepository tiposEmailBiRepository) {
		this.usuarioBiRepository = usuarioBiRepository;
		this.usuarioProgramaBiRepository = usuarioProgramaBiRepository;
		this.usuarioBiCustom = usuarioBiCustom;
		this.programaBiRepository = programaBiRepository;
		this.usuarioTipoEmailBiRepository = usuarioTipoEmailBiRepository;
		this.tiposEmailBiRepository = tiposEmailBiRepository;
	}

	public UsuarioBi saveUsuario(long codUsuario, String nome, String usuario, String senha, int situacao,
			int administrador, String email) {

		UsuarioBi dadosUsuario = null;

		// EDIÇÃO
		if (codUsuario > 0) {
			dadosUsuario = usuarioBiRepository.findByCodUsuario(codUsuario);

			dadosUsuario.nome = nome;
			dadosUsuario.usuario = usuario.toLowerCase();
			dadosUsuario.senha = senha;
			dadosUsuario.situacao = situacao;
			dadosUsuario.administrador = administrador;
			dadosUsuario.email = email;

			// INSERÇÃO
		} else {
			codUsuario = usuarioBiCustom.findNextIdUsuario();

			dadosUsuario = new UsuarioBi(codUsuario, nome, usuario, senha, email, situacao, administrador);
		}

		usuarioBiRepository.save(dadosUsuario);

		return dadosUsuario;
	}

	public void saveProgramas(long codUsuario, List<String> listaIdsProgramas) {
		List<UsuarioProgramaBi> programasAtuais = usuarioProgramaBiRepository.findByCodUsuario(codUsuario);
		for (UsuarioProgramaBi dadosPrograma : programasAtuais) {
			if (!listaIdsProgramas.contains(dadosPrograma.idPrograma)) {				
				usuarioTipoEmailBiRepository.deleteByCodUsuarioAndIdPrograma(codUsuario, dadosPrograma.idPrograma);
				usuarioProgramaBiRepository.deleteByCodUsuarioAndIdPrograma(codUsuario, dadosPrograma.idPrograma);
			}
		}
		adicionarProgramas(codUsuario, listaIdsProgramas);
	}

	public void saveProgramaToUsuarios(String idPrograma, List<Long> listaIdsUsuarios) {						
		List<UsuarioProgramaBi> programasAtuais = usuarioProgramaBiRepository.findByIdPrograma(idPrograma);
		for (UsuarioProgramaBi dadosPrograma : programasAtuais) {
			if (!listaIdsUsuarios.contains(dadosPrograma.codUsuario)) {				
				usuarioTipoEmailBiRepository.deleteByCodUsuarioAndIdPrograma(dadosPrograma.codUsuario, idPrograma);
				usuarioProgramaBiRepository.deleteByCodUsuarioAndIdPrograma(dadosPrograma.codUsuario, idPrograma);
			}			
		}											
		for (Long codUsuario : listaIdsUsuarios) {
			adicionarPrograma(codUsuario, idPrograma);
		}
	}
	
	public void adicionarProgramas(long codUsuario, List<String> listaIdsProgramas) {
		for (String idPrograma : listaIdsProgramas) {
			adicionarPrograma(codUsuario, idPrograma);
		}
	}	
	
	private void adicionarPrograma(long codUsuario, String idPrograma) {
		UsuarioProgramaBi usuarioProgramaBi = usuarioProgramaBiRepository.findByCodUsuarioAndIdPrograma(codUsuario, idPrograma);
		if (usuarioProgramaBi == null) {			
			usuarioProgramaBi = new UsuarioProgramaBi(codUsuario, idPrograma);
			usuarioProgramaBiRepository.save(usuarioProgramaBi);
		}
	}
	
	public void sobreporProgramas(long codUsuario, List<String> listaIdsProgramas) {
		
		usuarioTipoEmailBiRepository.deleteByCodUsuario(codUsuario);
		usuarioProgramaBiRepository.deleteByCodUsuario(codUsuario);
		
		for (String idPrograma : listaIdsProgramas) {
			UsuarioProgramaBi usuarioProgramaBi = usuarioProgramaBiRepository.findByCodUsuarioAndIdPrograma(codUsuario, idPrograma);
			if (usuarioProgramaBi == null) {			
				usuarioProgramaBi = new UsuarioProgramaBi(codUsuario, idPrograma);
				usuarioProgramaBiRepository.save(usuarioProgramaBi);
			}
		}
	}	
	
	public void insereTiposEmailSelecionado(long codUsuario, String idPrograma, String idTipoEmail) {

		if (codUsuario > 0) {
			usuarioBiCustom.deleteTiposEmailProgramaUsuarioId(codUsuario, idPrograma, idPrograma);
			UsuarioTipoEmailBi usuarioTipoEmailBi = new UsuarioTipoEmailBi(codUsuario, idTipoEmail, idPrograma);
			usuarioTipoEmailBiRepository.save(usuarioTipoEmailBi);
		}
	}

	public void insereTodosTiposEmailSelecionado(long codUsuario, String idPrograma) {

		if (codUsuario > 0) {
			List<TiposEmailBi> tiposEmail = tiposEmailBiRepository.findByIdPrograma(idPrograma);

			for (TiposEmailBi dadosTipoEmail : tiposEmail) {

				UsuarioTipoEmailBi usuarioTipoEmailBi = new UsuarioTipoEmailBi(codUsuario, dadosTipoEmail.id,
						idPrograma);
				usuarioTipoEmailBiRepository.save(usuarioTipoEmailBi);
			}
		}
	}

	public void excluiTiposEmailSelecionado(long codUsuario, String idPrograma, String idTipoEmail) {

		usuarioBiCustom.deleteTiposEmailProgramaUsuarioId(codUsuario, idPrograma, idTipoEmail);
	}

	public void excluiTodosTiposEmailSelecionado(long codUsuario, String idPrograma) {

		usuarioBiCustom.deleteTiposEmailProgramaUsuario(codUsuario, idPrograma);
	}

	public void deleteByCodUsuario(long codUsuario) {
		usuarioTipoEmailBiRepository.deleteByCodUsuario(codUsuario);
		usuarioProgramaBiRepository.deleteByCodUsuario(codUsuario);
		usuarioBiRepository.deleteBycodUsuario(codUsuario);
	}

	public List<String> findIdsProgramasByUsuario(long codUsuario) {

		List<UsuarioProgramaBi> programas = usuarioProgramaBiRepository.findByCodUsuario(codUsuario);

		List<String> listaIdsProgramas = new ArrayList<String>();

		for (UsuarioProgramaBi programa : programas) {
			listaIdsProgramas.add(programa.idPrograma);
		}
		return listaIdsProgramas;
	}

	public List<Long> findIdsUsuariosByPrograma(String idPrograma) {

		List<UsuarioProgramaBi> programas = usuarioProgramaBiRepository.findByIdPrograma(idPrograma);

		List<Long> listaIdsUsuarios = new ArrayList<Long>();

		for (UsuarioProgramaBi programa : programas) {
			listaIdsUsuarios.add(programa.codUsuario);
		}
		return listaIdsUsuarios;
	}
	
	public List<ProgramaBi> findProgramasPorUsuario(long codUsuario) {

		ProgramaBi dadosPrograma = null;

		List<UsuarioProgramaBi> programas = usuarioProgramaBiRepository.findByCodUsuario(codUsuario);

		List<ProgramaBi> listaIdsProgramas = new ArrayList<ProgramaBi>();

		for (UsuarioProgramaBi programa : programas) {

			dadosPrograma = programaBiRepository.findByIdPrograma(programa.idPrograma);

			listaIdsProgramas.add(dadosPrograma);
		}
		return listaIdsProgramas;
	}

	public List<String> findTiposEmailByUsuario(long codUsuario, String idPrograma) {

		List<UsuarioTipoEmailBi> tiposEmails = usuarioTipoEmailBiRepository.findByIdUsuarioIdPrograma(codUsuario,
				idPrograma);

		List<String> listaIdsTiposEmail = new ArrayList<String>();

		for (UsuarioTipoEmailBi tipoEmail : tiposEmails) {

			listaIdsTiposEmail.add(tipoEmail.idTipoEmail);
		}
		return listaIdsTiposEmail;
	}
	
	public boolean existsUsuario(long idUsuario, String usuario) {
		return usuarioBiCustom.existsUsuario(idUsuario, usuario);
	}
}
