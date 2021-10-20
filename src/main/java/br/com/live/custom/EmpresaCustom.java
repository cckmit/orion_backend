package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.Empregado;
import br.com.live.model.Empresa;

@Repository
public class EmpresaCustom {

	private JdbcTemplate jdbcTemplate;

	public EmpresaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Empresa> findEmpresas() {

		String query = " select a.codigo_empresa codigo, a.nome_empresa nome from fatu_500 a where a.codigo_empresa in (1,100,500,600)";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Empresa.class));
	}

	public List<Empregado> findEmpregados() {
				
		String query = " select ' ' nome, 0 codigo from dual "
		+ " union " 
		+ " select a.nome, a.cod_funcionario codigo "  
		+ " from efic_050 a "  
		+ " where a.sit_funcionario = 1 "  
		+ " and a.cod_funcionario > 1 "  
		+ " and length(a.nome) > 1 "  
		+ " group by a.nome, a.cod_funcionario " ; 
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Empregado.class)); 
	}

}