package br.com.live.sistema.service;

import java.util.List;

import br.com.live.sistema.entity.RelacEmailsProgramasOrionReports;
import br.com.live.sistema.repository.RelacEmailsProgramasOrionReportsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.sistema.custom.OrionReportsProgramasCustom;
import br.com.live.sistema.entity.OrionReportsProgramas;
import br.com.live.sistema.model.ConsultaProgramasOrionReport;
import br.com.live.sistema.repository.OrionReportsProgramasRepository;
import br.com.live.util.ConteudoChaveAlfaNum;

@Service
@Transactional
public class OrionReportsProgramasService {
	
	private final OrionReportsProgramasCustom orionReportsProgramasCustom;
	private final OrionReportsProgramasRepository orionReportsProgramasRepository;
	private final RelacEmailsProgramasOrionReportsRepository relacEmailsProgramasOrionReportsRepository;
	
	
	public OrionReportsProgramasService(OrionReportsProgramasCustom orionReportsProgramasCustom, OrionReportsProgramasRepository orionReportsProgramasRepository,
										RelacEmailsProgramasOrionReportsRepository relacEmailsProgramasOrionReportsRepository) {
		this.orionReportsProgramasCustom = orionReportsProgramasCustom;
		this.orionReportsProgramasRepository = orionReportsProgramasRepository;
		this.relacEmailsProgramasOrionReportsRepository = relacEmailsProgramasOrionReportsRepository;
				
	}
	
	public List<ConsultaProgramasOrionReport> findAllProgramas(){
		return orionReportsProgramasCustom.findAllProgramas();	
	}
	
	public List<ConteudoChaveAlfaNum> findAllAreas(){
		return orionReportsProgramasCustom.findAllAreas();
	}
	
	public List<ConteudoChaveAlfaNum> findAllModulos(){
		return orionReportsProgramasCustom.findAllModulos();
	}
	
	public List<ConteudoChaveAlfaNum> findAllModulosPorArea(String area){
		return orionReportsProgramasCustom.findAllModulosPorArea(area);	
	}
	
	public String findAreaById(String id){
		return orionReportsProgramasCustom.findAreaById(id);
	}
	
	public OrionReportsProgramas findProgramaById(String id){
		return orionReportsProgramasRepository.findByIdProgr(id);
	}
	
	public List<ConsultaProgramasOrionReport> salvarPrograma(String idPrograma, String area, String modulo, String descricao, int situacao, int powerbi, String linkPowerbi){
		
		OrionReportsProgramas dadosPrograma = orionReportsProgramasRepository.findByIdProgr(idPrograma);
		
		String numPrograma = orionReportsProgramasCustom.findMaxNrProgramByAreaModulo(area, modulo);
		
		String idProg = modulo + numPrograma;
		
		if (dadosPrograma == null) {
			dadosPrograma = new OrionReportsProgramas(idProg, area, modulo, Integer.parseInt(numPrograma), descricao, powerbi, linkPowerbi, situacao);
		} else {
			dadosPrograma.idArea = area;
			dadosPrograma.idModulo = modulo;
			dadosPrograma.descricao = descricao;
			dadosPrograma.powerbi = powerbi;
			dadosPrograma.linkPowerbi = linkPowerbi;
			dadosPrograma.situacao = situacao;
		}
		orionReportsProgramasRepository.save(dadosPrograma);
		return orionReportsProgramasCustom.findAllProgramas();		
	}
	
	public List<ConsultaProgramasOrionReport> deletePrograma(String id){
		orionReportsProgramasRepository.deleteById(id);
		return orionReportsProgramasCustom.findAllProgramas();
	}
	
	public List<RelacEmailsProgramasOrionReports> salvarEmailsPrograma(String email, String programa) {
		System.out.println("EMAIL: " + email);
		System.out.println("programa: " + programa);

		RelacEmailsProgramasOrionReports dadosEmail = new RelacEmailsProgramasOrionReports(programa, email.toLowerCase());
		relacEmailsProgramasOrionReportsRepository.saveAndFlush(dadosEmail);
		return relacEmailsProgramasOrionReportsRepository.findEmailsByPrograma(programa);
	}
}
