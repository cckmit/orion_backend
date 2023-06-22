package br.com.live.sistema.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.OrionReportsProgramas;
import br.com.live.sistema.model.ConsultaProgramasOrionReport;
import br.com.live.util.ConteudoChaveAlfaNum;

@Repository
public class OrionReportsProgramasCustom {
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrionReportsProgramasCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<ConsultaProgramasOrionReport> findAllProgramas() {
    	
        String query = " SELECT a.id idPrograma, "
        		+ "       b.descricao area, "
        		+ "       c.descricao modulo, "
        		+ "       a.nr_programa nrPrograma, "
        		+ "       a.descricao descricaoPrograma, "
        		+ "       a.powerbi powerbi, "
        		+ "       a.link_powerbi linkPowerbi, "
        		+ "       a.situacao situacao "
        		+ "		FROM orion_bi_110 a, orion_bi_100 b, orion_bi_105 c "
        		+ "		WHERE b.id = a.id_area "
        		+ "		AND c.id = a.id_modulo ";
        
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaProgramasOrionReport.class));
    }
    
    public String findMaxNrProgramByAreaModulo(String area, String modulo) {
    	
    	String numPrograma = "";
    	
    	String query = " SELECT LPAD(NVL(MAX(a.nr_programa), 0) + 1, 3, 0) FROM orion_bi_110 a "
    			+ "     WHERE a.id_area = '" + area + "' AND a.id_modulo = '" + modulo + "' ";
    	
    	try {
    		numPrograma = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			numPrograma = "";
		}
		return numPrograma;
    }
    
    public String findAreaById(String id) {
    	
    	String idArea = " ";
    	
    	String query = " SELECT d.id_area FROM orion_bi_110 d WHERE d.id = '" + id + "' ";
    	
    	try {
    		idArea = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			idArea = "";
		}
		return idArea ;
    }
    
    public List<ConteudoChaveAlfaNum> findAllAreas(){
    		
    		String query = " SELECT b.id value, b.id || ' - ' || b.descricao label FROM orion_bi_100 b ";
    		
    	return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));				
    }
    
    public List<ConteudoChaveAlfaNum> findAllModulos(){
		
		String query = " SELECT c.id value, SUBSTR(c.id, 2, 1) || ' - ' || c.descricao label FROM orion_bi_105 c ";
	
	return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));				
    }
    
    public List<ConteudoChaveAlfaNum> findAllModulosPorArea(String area){
		
		String query = " SELECT c.id value, SUBSTR(c.id, 2, 1) || ' - ' || c.descricao label FROM orion_bi_105 c WHERE c.id_area = '" + area + "' ";
	
	return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));				
    }

}
