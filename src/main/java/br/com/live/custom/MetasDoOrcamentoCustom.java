package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaMetasOrcamento;

@Repository
public class MetasDoOrcamentoCustom {
	private JdbcTemplate jdbcTemplate;

	public MetasDoOrcamentoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConsultaMetasOrcamento> findMetasOrcamentoGrid(int ano, int tipoMeta) {
		

		String query = " select a.id, a.tipo_meta tipoMeta, a.descricao, a.ano, a.valor_mes_1 valorMes1, a.valor_mes_2 valorMes2, a.valor_mes_3 valorMes3, " +
				" a.valor_mes_4 valorMes4, a.valor_mes_5 valorMes5, a.valor_mes_6 valorMes6, a.valor_mes_7 valorMes7, a.valor_mes_8 valorMes8, " +
				" a.valor_mes_9 valorMes9, a.valor_mes_10 valorMes10, a.valor_mes_11 valorMes11, a.valor_mes_12 valorMes12 from orion_150 a " +
				" where a.ano = " + ano +
				" and a.tipo_meta = " + tipoMeta +
				" order by a.descricao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMetasOrcamento.class));
	}
}
