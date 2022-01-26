package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SugestaoReservaTecidoCustom {
	
	private final JdbcTemplate jdbcTemplate;

	public SugestaoReservaTecidoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
		
}