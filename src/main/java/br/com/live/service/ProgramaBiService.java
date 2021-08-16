package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProgramaBiCustom;
import br.com.live.entity.ProgramaBi;
import br.com.live.entity.TiposEmailBi;
import br.com.live.repository.ProgramaBiRepository;
import br.com.live.repository.TiposEmailBiRepository;

@Service
@Transactional
public class ProgramaBiService {
	
	private final ProgramaBiRepository programaBiRepository;
	private final ProgramaBiCustom programaBiCustom;
	private final TiposEmailBiRepository tiposEmailBiRepository;

	public ProgramaBiService(ProgramaBiRepository programaBiRepository, ProgramaBiCustom programaBiCustom, TiposEmailBiRepository tiposEmailBiRepository) {
		this.programaBiRepository = programaBiRepository;
		this.programaBiCustom = programaBiCustom;
		this.tiposEmailBiRepository = tiposEmailBiRepository;
	}

	public ProgramaBi saveProgramaBi(String idProgramaBi, String areaModulo, int atividade, String descricao, String ferramenta, String frequencia, String planilha, String extrator, String help, List<TiposEmailBi> tiposEmail) {
		
		ProgramaBi dadosPrograma = null;
		
		// EDIÇÃO
		if (idProgramaBi != "") {
			dadosPrograma = programaBiRepository.findByIdPrograma(idProgramaBi);

			dadosPrograma.descricao = descricao;
			dadosPrograma.extrator = extrator;
			dadosPrograma.ferramenta = ferramenta;
			dadosPrograma.frequencia = frequencia;
			dadosPrograma.planilha = planilha;
			dadosPrograma.help= help;
			
		// INSERÇÃO
		} else {
			atividade = programaBiCustom.findNextAtividade();
			
			dadosPrograma = new ProgramaBi(areaModulo, atividade, descricao, ferramenta, frequencia, planilha, extrator, help);
		}
		
		programaBiRepository.save(dadosPrograma);
		
		saveTiposEmail(dadosPrograma.id, tiposEmail);
		
		return dadosPrograma;
	}
	
	public void saveTiposEmail(String idProgramaBi, List<TiposEmailBi> listaIdsProgramas) {
		
		tiposEmailBiRepository.deleteByIdPrograma(idProgramaBi);
		
		for (TiposEmailBi dadosTipoEmail: listaIdsProgramas) {
			
			TiposEmailBi tipoEmail = new TiposEmailBi (idProgramaBi, dadosTipoEmail.codTipoEmail, dadosTipoEmail.descricao);
			tiposEmailBiRepository.save(tipoEmail);
		}
		
	}
	
	public void deleteByIdPrograma(String idPrograma) {
		tiposEmailBiRepository.deleteByIdPrograma(idPrograma);
		programaBiRepository.deleteById(idPrograma);
	}

}
