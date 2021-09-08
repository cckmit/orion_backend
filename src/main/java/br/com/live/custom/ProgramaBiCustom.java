package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ProgramaBi;

@Repository
public class ProgramaBiCustom {
	
	private final JdbcTemplate jdbcTemplate;

	public ProgramaBiCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int findNextAtividade(String areaModulo) {

		Integer nextAtividade;

		String query = " select nvl(max(atividade),0) + 1 from orion_bi_001 a "
					+  " where a.area_modulo = '" + areaModulo + "'";
		try {
			nextAtividade = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextAtividade = 0;
		}
		return nextAtividade;
	}
	
	public List<ProgramaBi> findProgramasByListaAreasModulosAndUsuarios(String listaAreasModulos, String listaUsuarios) {
		
		List<ProgramaBi> programas;
		
		String query = "";
		String queryByAreas = "";
		String queryByUsuarios = "";
		
		if (listaAreasModulos.length() > 0) {
			queryByAreas = " select a.id, a.descricao, a.area_modulo from orion_bi_001 a "
			+ " where a.area_modulo in (" + listaAreasModulos + " ) ";
		}

		if (listaUsuarios.length() > 0) {
			queryByUsuarios = " select b.id, b.descricao, b.area_modulo from orion_bi_004 a, orion_bi_001 b " 
			+ "	where a.cod_usuario in (" + listaUsuarios + ") "
			+ " and b.id = a.id_programa "
			+ " group by b.id, b.descricao, b.area_modulo ";
		}
		
		query += queryByAreas;
		
		if (queryByUsuarios.length() > 0) {
			if (query.length() > 0) query += " union ";			
			query += queryByUsuarios;
		}
		
		try {
			programas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProgramaBi.class));
		} catch (Exception e) {
			programas = new ArrayList<ProgramaBi> ();
		}
		
		return programas;
	}

}
