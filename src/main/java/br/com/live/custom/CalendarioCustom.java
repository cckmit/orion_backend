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
		String query = " select a.numero_semana numeroSemana, min(a.data_calendario) dataInicio, max(a.data_calendario) dataFim, count(*) qtdeDias, EXTRACT(MONTH FROM min(a.data_calendario)) mes, EXTRACT(YEAR FROM min(a.data_calendario)) ano "
		+ " from basi_260 a " 
		+ " where a.data_calendario between ? and ? " 
		+ " group by a.numero_semana "
		+ " order by a.numero_semana ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CalendarioSemana.class), FormataData.getStartingDay(mes, ano), FormataData.getFinalDay(mes, ano));	
	}
	
}