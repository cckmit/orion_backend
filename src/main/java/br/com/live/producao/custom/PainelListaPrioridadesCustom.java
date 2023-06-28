package br.com.live.producao.custom;

import org.springframework.jdbc.core.JdbcTemplate;

public class PainelListaPrioridadesCustom {

	private JdbcTemplate jdbcTemplate;

	public PainelListaPrioridadesCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	
}
