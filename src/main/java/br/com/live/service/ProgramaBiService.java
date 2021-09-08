package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProgramaBiCustom;
import br.com.live.entity.ProgramaBi;
import br.com.live.entity.TiposEmailBi;
import br.com.live.model.SalvarTipoEmailBi;
import br.com.live.repository.ProgramaBiRepository;
import br.com.live.repository.TiposEmailBiRepository;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Service
@Transactional
public class ProgramaBiService {

	private final ProgramaBiRepository programaBiRepository;
	private final ProgramaBiCustom programaBiCustom;
	private final TiposEmailBiRepository tiposEmailBiRepository;

	public ProgramaBiService(ProgramaBiRepository programaBiRepository, ProgramaBiCustom programaBiCustom,
			TiposEmailBiRepository tiposEmailBiRepository) {
		this.programaBiRepository = programaBiRepository;
		this.programaBiCustom = programaBiCustom;
		this.tiposEmailBiRepository = tiposEmailBiRepository;
	}

	public List<ProgramaBi> findProgramasByListaAreasModulosAndUsuarios(List<ConteudoChaveAlfaNum> listaAreasModulos, List<ConteudoChaveNumerica> listaUsuarios) {
		return programaBiCustom.findProgramasByListaAreasModulosAndUsuarios(ConteudoChaveAlfaNum.parseValueToString(listaAreasModulos), ConteudoChaveNumerica.parseValueToString(listaUsuarios));
	}
	
	public ProgramaBi saveProgramaBi(String idProgramaBi, String areaModulo, int atividade, String descricao,
			String ferramenta, String frequencia, String planilha, String extrator, String help,
			List<SalvarTipoEmailBi> tiposEmail) {

		ProgramaBi dadosPrograma = null;

		// EDIÇÃO
		if (idProgramaBi != "") {
			dadosPrograma = programaBiRepository.findByIdPrograma(idProgramaBi);

			dadosPrograma.descricao = descricao;
			dadosPrograma.extrator = extrator;
			dadosPrograma.ferramenta = ferramenta;
			dadosPrograma.frequencia = frequencia;
			dadosPrograma.planilha = planilha;
			dadosPrograma.help = help;

			// INSERÇÃO
		} else {
			atividade = programaBiCustom.findNextAtividade(areaModulo);

			dadosPrograma = new ProgramaBi(areaModulo, atividade, descricao, ferramenta, frequencia, planilha, extrator,
					help);
		}

		programaBiRepository.save(dadosPrograma);

		saveTiposEmail(dadosPrograma.id, tiposEmail);

		return dadosPrograma;
	}

	public void saveTiposEmail(String idProgramaBi, List<SalvarTipoEmailBi> listaIdsProgramas) {
		
		for (SalvarTipoEmailBi dadosTipoEmail : listaIdsProgramas) {
			TiposEmailBi tipoEmailBI = tiposEmailBiRepository.findByIdTipoEmail(dadosTipoEmail.getId());
			
			boolean permiteRelacionarUsuariuo = false;
			
			if (dadosTipoEmail.getPermRelacUsuarios().equals("Sim")) {
				permiteRelacionarUsuariuo = true;
			}
			
			if (tipoEmailBI != null) {				
				tipoEmailBI.descricao = dadosTipoEmail.getDescricao();
				tipoEmailBI.permRelacUsuarios = permiteRelacionarUsuariuo; 
				tiposEmailBiRepository.save(tipoEmailBI);
			} else {
				tipoEmailBI = new TiposEmailBi(idProgramaBi, dadosTipoEmail.getCodTipoEmail(), dadosTipoEmail.getDescricao(), permiteRelacionarUsuariuo);
				tiposEmailBiRepository.save(tipoEmailBI);
			}	
		}		
		
		List<TiposEmailBi> listaTodosTiposEmail = tiposEmailBiRepository.findByIdPrograma(idProgramaBi);

		for (TiposEmailBi dadosTipoEmailBanco : listaTodosTiposEmail) {
			boolean excluir = true;			
			for (SalvarTipoEmailBi dadosTipoEmail : listaIdsProgramas) {
				
				String idMontado = dadosTipoEmailBanco.idPrograma + dadosTipoEmail.getCodTipoEmail();
				
				if (dadosTipoEmailBanco.id.equals(idMontado)) {
					excluir = false;
				}
			}
			if (excluir) tiposEmailBiRepository.deleteById(dadosTipoEmailBanco.id);			
		}
	}

	public void deleteByIdPrograma(String idPrograma) {
		tiposEmailBiRepository.deleteByIdPrograma(idPrograma);
		programaBiRepository.deleteById(idPrograma);
	}

}
