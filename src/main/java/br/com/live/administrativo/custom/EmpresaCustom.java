package br.com.live.administrativo.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.model.Empregado;
import br.com.live.administrativo.model.Empresa;

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

	public List<Empresa> findAllEmpresas() {

		String query = " select lpad(nvl(a.cgc_9,''),8,0) || lpad(nvl(a.cgc_4,''),4,0) || lpad(nvl(a.cgc_2,''),2,0) cnpjEmpresa, a.nome_empresa nome from fatu_500 a ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Empresa.class));
	}
	
	public List<Empresa> findEmpresasAtivas() {
		
		String query = " select a.codigo_empresa codigo, a.nome_empresa nome, lpad(nvl(a.cgc_9,''),8,0) || lpad(nvl(a.cgc_4,''),4,0) || lpad(nvl(a.cgc_2,''),2,0) cnpjEmpresa " 
		+ " from fatu_500 a " 
		+ " where a.nome_empresa not like ('%(IN)%') order by a.codigo_empresa " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Empresa.class));			
	}
}