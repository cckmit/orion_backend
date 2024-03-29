package br.com.live.sistema.service;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.sistema.custom.ProgramaCustom;
import br.com.live.sistema.entity.Programa;
import br.com.live.sistema.model.ProgramaConsulta;
import br.com.live.sistema.repository.ProgramaRepository;

import java.util.List;

@Service
@Transactional
public class ProgramaService {

	private final ProgramaRepository programaRepository;
	private final ProgramaCustom programaCustom;

	public ProgramaService(ProgramaRepository programaRepository, ProgramaCustom programaCustom) {
			this.programaRepository = programaRepository;
			this.programaCustom = programaCustom;
		}

	public Programa saveHelpPrograma(long idPrograma, String help) {
		
		Programa dadosPrograma = programaRepository.findByIdPrograma(idPrograma);
		
		dadosPrograma.help = help;
		
		programaRepository.save(dadosPrograma);
		
		return dadosPrograma;
	}
	public List<ProgramaConsulta> findProgramasByListaModulosAndUsuarios(List<ConteudoChaveAlfaNum> listaModulos, List<ConteudoChaveNumerica> listaUsuarios) {
		return programaCustom.findProgramasByListaModulosAndUsuarios(ConteudoChaveAlfaNum.parseValueToString(listaModulos), ConteudoChaveNumerica.parseValueToString(listaUsuarios));
	}

}
