package br.com.live.custom;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.Calendario;
import br.com.live.model.CalendarioSemana;
import br.com.live.util.FormataData;

@Repository
public class CalendarioCustom {

	private final JdbcTemplate jdbcTemplate;

	public CalendarioCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	

	public List<Calendario> getCalendarioByMes(int mes, int ano) {		
		String query = " select a.data_calendario data, a.dia_util diaUtil, a.dia_semana diaSemana, a.numero_semana numeroSemana " 
		+ " from basi_260 a " 
		+ " where a.data_calendario between ? and ? ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Calendario.class), FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));
	}
	
	public List<CalendarioSemana> getSemanasByMes(int mes, int ano) {
		String query = " select semanas.numero_semana numeroSemana, semanas.inicio dataInicio, semanas.fim dataFim, semanas.qtde_dias qtdeDias, semanas.mes, semanas.ano, "
		+ " (select count(*) from basi_260 z where z.data_calendario between semanas.inicio and semanas.fim and z.dia_util = 0) qtdeDiasUteis "
		+ " from ( "
		+ " select a.numero_semana, min(a.data_calendario) inicio, max(a.data_calendario) fim, count(*) qtde_dias, EXTRACT(MONTH FROM min(a.data_calendario)) mes, EXTRACT(YEAR FROM min(a.data_calendario)) ano "
		+ " from basi_260 a " 
		+ " where a.data_calendario between ? and ? " 
		+ " group by a.numero_semana "
		+ " order by a.numero_semana "
		+ " ) semanas "; 
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CalendarioSemana.class), FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));	
	}
	
	public int findDiasUteis(int mes, int ano) {
		String query = " select count(*) from basi_260 a where a.data_calendario between ? and ? and a.dia_util = 0 ";
		return jdbcTemplate.queryForObject(query, Integer.class, FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));
	}
}