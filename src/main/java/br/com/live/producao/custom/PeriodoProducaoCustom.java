package br.com.live.producao.custom;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.model.PeriodoProducao;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class PeriodoProducaoCustom {

	public static final int EMPRESA_DEMANDA = 1;
	public static final int EMPRESA_PRODUCAO = 500;		
	private JdbcTemplate jdbcTemplate;
	
	public PeriodoProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void savePeriodoProducao(int empresa, int area, int periodo, Date dataIniProd, Date dataFimProd, Date dataIniFat, Date dataFimFat, Date dataLimiteProg, int quinzena) {
			
		String query = " insert into pcpc_010 (codigo_empresa, area_periodo, periodo_producao, " 
		+ " data_ini_periodo, data_fim_periodo, data_ini_fatu, data_fim_fatu, " 
		+ " data_limite_programacao, cod_quinzena) values (?,?,?,?,?,?,?,?,?) " ;

		jdbcTemplate.update(query, empresa, area, periodo, dataIniProd, dataFimProd, dataIniFat, dataFimFat, dataLimiteProg, quinzena);
	}
	
	public List<PeriodoProducao> findPeriodosProducao() {
		
		String query = " select p.periodo_producao periodo, to_char(p.data_ini_periodo, 'dd/mm/yyyy') dataIniPeriodo, "
		 + " to_char(p.data_fim_periodo, 'dd/mm/yyyy') dataFimPeriodo, to_char(p.data_ini_fatu, 'dd/mm/yyyy') dataIniFaturamento, to_char(p.data_fim_fatu, 'dd/mm/yyyy') dataFimFaturamento "
		 + " from pcpc_010 p "  
		 + " where p.area_periodo = 1 " 
		 + " and p.codigo_empresa = ? "
		 + " and p.data_ini_periodo > SYSDATE - 360 ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PeriodoProducao.class), EMPRESA_PRODUCAO);
	}

	public List<ConteudoChaveNumerica> findPeriodosProducaoListContChaveNum() {
		
		String query = " select p.periodo_producao value, p.periodo_producao || ' - ' || to_char(p.data_ini_periodo, 'dd/mm/yyyy') || ' até ' || to_char(p.data_fim_periodo, 'dd/mm/yyyy') label " 
				+ " from pcpc_010 p " 
				+ " where p.area_periodo = 1 " 
				+ " and p.codigo_empresa = ? "
				+ " and p.data_ini_periodo > SYSDATE - 360 " 
				+ " order by p.periodo_producao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class), EMPRESA_PRODUCAO);
	}

	
	public List<PeriodoProducao> findPeriodosDemanda() {
		
		String query = " select p.periodo_producao periodo, to_char(p.data_ini_periodo, 'dd/mm/yyyy') dataIniPeriodo, "
				 + " to_char(p.data_fim_periodo, 'dd/mm/yyyy') dataFimPeriodo, to_char(p.data_ini_fatu, 'dd/mm/yyyy') dataIniFaturamento, to_char(p.data_fim_fatu, 'dd/mm/yyyy') dataFimFaturamento "
				 + " from pcpc_010 p "  
				 + " where p.area_periodo = 1 " 
				 + " and p.codigo_empresa = ? "
				 + " and p.data_ini_periodo > SYSDATE - 360 ";
					
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PeriodoProducao.class), EMPRESA_DEMANDA);
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
