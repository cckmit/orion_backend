package br.com.live.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.entity.Programa;
import br.com.live.repository.ProgramaRepository;

@Service
@Transactional
public class ProgramaService {

	private final ProgramaRepository programaRepository;

	public ProgramaService(ProgramaRepository programaRepository) {
			this.programaRepository = programaRepository;
		}

	public Programa saveHelpPrograma(long idPrograma, String help) {
		
		Programa dadosPrograma = programaRepository.findByIdPrograma(idPrograma);
		
		dadosPrograma.help = help;
		
		programaRepository.save(dadosPrograma);
		
		return dadosPrograma;
	}

}
