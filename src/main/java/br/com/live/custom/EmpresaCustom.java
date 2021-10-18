package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.Empresa;

@Repository
public class EmpresaCustom {

	private JdbcTemplate jdbcTemplate;

	public EmpresaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Empresa> findEmpresas() {
		
		String query = " select a.codigo_empresa codigo, a.nome_empresa nome from fatu_500 a where a.codigo_empresa in (1,500,600)";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Empresa.class));
	}
}