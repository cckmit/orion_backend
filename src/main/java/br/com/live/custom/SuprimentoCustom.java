package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.CentroCusto;
import br.com.live.model.Deposito;

@Repository
public class SuprimentoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public EmpresaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CentroCusto> findCentroCusto() {
				
		String query = " select b.centro_custo codigo, b.descricao from basi_185 b "
		+ " where b.descricao not like '%(IN)%' "
		+ " and b.centro_custo > 0 "
		+ " order by b.centro_custo ";
						
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CentroCusto.class));		
	}
	
	/*
	public List<> findDivisaoProducao() {
		
		String query = " select b.divisao_producao codigo, b.descricao from basi_180 b "
		+ " where b.divisao_producao > 0 "
		+ " order by b.divisao_producao ";
				
		return 
	}*/
	
	
	
}
