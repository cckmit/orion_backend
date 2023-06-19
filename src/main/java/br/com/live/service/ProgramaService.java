package br.com.live.service;

import br.com.live.custom.ProgramaCustom;
import br.com.live.model.ProgramaConsulta;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.entity.Programa;
import br.com.live.repository.ProgramaRepository;

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
