package br.com.live.custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodoProducaoCustom {

	private JdbcTemplate jdbcTemplate;

	public PeriodoProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean periodoExiste(int periodo) {
		
		Integer verifica = 0;
		
		String query = " select 1 from pcpc_010 p " 
		+ " where p.area_periodo = 1 "
		+ "   and p.periodo_producao = " + periodo ;
		
		try {
			verifica = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			verifica = 0;
		}

		System.out.println("periodo existe: " + periodo);
		System.out.println(verifica);
		
		return (verifica == 1);
	}
	
}
