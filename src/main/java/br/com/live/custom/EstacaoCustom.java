package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class EstacaoCustom {

	private JdbcTemplate jdbcTemplate;

	public EstacaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ConteudoChaveNumerica> findRepresentantes() {
				
		String query = " select a.cod_rep_cliente value, a.cod_rep_cliente || ' - ' || a.nome_rep_cliente label from pedi_020 a " ; 
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class)); 
	}
	
	public String findDescRepresentante(int codRepresentante) {
		
		String descRepresentante;
		
		String query = " select a.nome_rep_cliente from pedi_020 a where a.cod_rep_cliente = " + codRepresentante; 
		
		try {
			descRepresentante = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			descRepresentante = "";
		}
		return descRepresentante;
	}

}