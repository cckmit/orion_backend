package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.OrionReportsProgramasCustom;
import br.com.live.entity.OrionReportsProgramas;
import br.com.live.model.ConsultaProgramasOrionReport;
import br.com.live.repository.OrionReportsProgramasRepository;
import br.com.live.util.ConteudoChaveAlfaNum;

@Service
@Transactional
public class OrionReportsProgramasService {
	
	private final OrionReportsProgramasCustom orionReportsProgramasCustom;
	private final OrionReportsProgramasRepository orionReportsProgramasRepository;
	
	
	public OrionReportsProgramasService(OrionReportsProgramasCustom orionReportsProgramasCustom, OrionReportsProgramasRepository orionReportsProgramasRepository) {
		this.orionReportsProgramasCustom = orionReportsProgramasCustom;
		this.orionReportsProgramasRepository = orionReportsProgramasRepository;
				
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
	

}
