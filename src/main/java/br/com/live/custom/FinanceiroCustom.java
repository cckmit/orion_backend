package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class FinanceiroCustom {
	
	private JdbcTemplate jdbcTemplate;

	public FinanceiroCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveNumerica> findAllRepresentantes(int cod) {
		
		String query = " SELECT a.cod_rep_cliente value, "
				+ "    a.cod_rep_cliente || ' - ' || a.nome_rep_cliente label "
				+ "    FROM pedi_020 a WHERE a.tipo_repr = 3 "
				+ "    AND cod_rep_cliente LIKE '%" + cod + "%'";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
		
	}

}
