package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ProgramacaoPlanoMestre;

@Repository
public class PlanoMestreCustom {
	
	private JdbcTemplate jdbcTemplate;

	public PlanoMestreCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProgramacaoPlanoMestre> findProgramacaoByPlanoMestre(long idPlanoMestre) {
				
		String query = "select a.grupo, a.sub, a.item, a.qtde_programada, c.alternativa, c.roteiro, c.periodo from orion_015 a, orion_016 b, orion_017 c "
		+ " where a.num_plano_mestre = " + idPlanoMestre
		  + " and b.num_plano_mestre = a.num_plano_mestre "
		  + " and b.grupo = a.grupo "
		  + " and b.item = a.item "
		  + " and c.num_plano_mestre = a.num_plano_mestre "
		  + " and c.num_item_plano_mestre = b.id ";		
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProgramacaoPlanoMestre.class));
	}
	
}
