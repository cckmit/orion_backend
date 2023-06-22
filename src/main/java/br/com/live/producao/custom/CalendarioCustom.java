package br.com.live.producao.custom;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.MetasProducao;
import br.com.live.producao.model.Calendario;
import br.com.live.producao.model.CalendarioSemana;
import br.com.live.util.FormataData;

@Repository
public class CalendarioCustom {

	private final JdbcTemplate jdbcTemplate;
	private static final String COLUNAS_CALENDARIO = "data_calendario data, dia_util diaUtil, dia_semana diaSemana, numero_semana numeroSemana";
	
	public CalendarioCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	

	public List<Calendario> getCalendarioByMes(int mes, int ano) {		
		String query = " select " + COLUNAS_CALENDARIO 
		+ " from basi_260 " 
		+ " where data_calendario between ? and ? ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Calendario.class), FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));
	}
	
	public Calendario getProximoDiaUtil(Date data) {
		String query = " select " + COLUNAS_CALENDARIO 
		+ " from basi_260 " 
		+ " where data_calendario > ? "
		+ " and dia_util = 0 "
		+ " and rownum = 1 "
		+ " order by data_calendario " ;		 

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Calendario.class), data);
	}
	
	public List<CalendarioSemana> getSemanasByMes(int mes, int ano) {
		String query = " select semana.numeroSemana, semana.dataInicio, semana.dataFim, semana.qtdeDias, semana.mes, semana.ano, "
				+ " (select count(*) from basi_260 v where v.data_calendario between semana.dataInicio and semana.dataFim "
				+ "        and v.dia_util = 0) qtdeDiasUteis "
				+ " from "
				+ " ("
				+ " select a.numero_semana numeroSemana, min(a.data_calendario) dataInicio, max(a.data_calendario) dataFim, count(*) qtdeDias, "
				+ " EXTRACT(MONTH FROM min(a.data_calendario)) mes, EXTRACT(YEAR FROM min(a.data_calendario)) ano "
				+ "    from basi_260 a "
				+ "    where a.data_calendario between ? and ? "
				+ "    group by a.numero_semana "
				+ "    order by a.numero_semana"
				+ "    ) semana ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CalendarioSemana.class), FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));	
	}
	
	public int findDiasUteis(int mes, int ano) {
		String query = " select COUNT(a.dia_util) from basi_260 a where a.data_calendario between ? and ? "
				+ " AND a.dia_util = 0 ";
		return jdbcTemplate.queryForObject(query, Integer.class, FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));
	}
	
	public List<MetasProducao> findMetasProducao(String idMeta) {
		String query = " SELECT a.nr_semana numSemana, a.dias_uteis diasUteis, a.meta_real metaReal, a.meta_real_turno metaRealTurno, a.meta_ajustada metaAjustada, a.meta_ajustada_turno metaAjustadaTurno "
				+ " FROM orion_cfc_240 a WHERE a.id_mes = " + idMeta;
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MetasProducao.class));
		
	}
}