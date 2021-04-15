package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.PeriodoProducao;

@Repository
public class PeriodoProducaoCustom {

	private JdbcTemplate jdbcTemplate;

	public PeriodoProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PeriodoProducao> findPeriodosProducao() {
		
		String query = " select p.periodo_producao periodo, to_char(p.data_ini_periodo, 'dd/mm/yyyy') dataIniPeriodo, "
		 + " to_char(p.data_fim_periodo, 'dd/mm/yyyy') dataFimPeriodo, to_char(p.data_ini_fatu, 'dd/mm/yyyy') dataIniFaturamento, to_char(p.data_fim_fatu, 'dd/mm/yyyy') dataFimFaturamento "
		 + " from pcpc_010 p "  
		 + " where p.area_periodo = 1 " 
		 + " and p.codigo_empresa = 500 "
		 + " and p.data_ini_periodo > '01-JAN-2019' ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PeriodoProducao.class));
	}
	
	public List<PeriodoProducao> findPeriodosDemanda() {
		
		String query = " select p.periodo_producao periodo, to_char(p.data_ini_periodo, 'dd/mm/yyyy') dataIniPeriodo, "
				 + " to_char(p.data_fim_periodo, 'dd/mm/yyyy') dataFimPeriodo, to_char(p.data_ini_fatu, 'dd/mm/yyyy') dataIniFaturamento, to_char(p.data_fim_fatu, 'dd/mm/yyyy') dataFimFaturamento "
				 + " from pcpc_010 p "  
				 + " where p.area_periodo = 1 " 
				 + " and p.codigo_empresa = 1 "
				 + " and p.data_ini_periodo > '01-JAN-2019' ";
					
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PeriodoProducao.class));
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

		return (verifica == 1);
	}
}
