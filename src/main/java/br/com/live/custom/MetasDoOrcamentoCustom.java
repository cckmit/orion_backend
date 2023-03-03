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
		

		String query = " SELECT a.id, a.tipo_meta tipoMeta, a.descricao, a.modalidade, a.ano, a.valor_mes_1 valorMes1, a.valor_mes_2 valorMes2, a.valor_mes_3 valorMes3, " + 
				" a.valor_mes_4 valorMes4, a.valor_mes_5 valorMes5, a.valor_mes_6 valorMes6, a.valor_mes_7 valorMes7, a.valor_mes_8 valorMes8, " + 
				" a.valor_mes_9 valorMes9, a.valor_mes_10 valorMes10, a.valor_mes_11 valorMes11, a.valor_mes_12 valorMes12," +
				" a.valor_mes_1 + a.valor_mes_2 + a.valor_mes_3 + a.valor_mes_4 + a.valor_mes_5 + a.valor_mes_6 + a.valor_mes_7 + a.valor_mes_8 + a.valor_mes_9 + a.valor_mes_10 + a.valor_mes_11 + a.valor_mes_12 totalCanal" +
				" FROM orion_150 a " +
				" WHERE a.ano = " + ano +
				" AND a.tipo_meta = " + tipoMeta +
				" ORDER BY a.descricao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMetasOrcamento.class));
	}
	
	public String findTotalGeralPorTipoMeta(int tipoMeta, int ano) {
		
		String total = "";
		
		String query = " SELECT SUM(a.valor_mes_1 + a.valor_mes_2 + a.valor_mes_3 + a.valor_mes_4 + a.valor_mes_5 + a.valor_mes_6 + "
				+ "       a.valor_mes_7 + a.valor_mes_8 + a.valor_mes_9 + a.valor_mes_10 + a.valor_mes_11 + a.valor_mes_12) totalGeral FROM orion_150 a "
				+ "    WHERE a.ano = '" + ano + "'"
				+ "    AND a.tipo_meta = '" + tipoMeta + "'";
		
		try {
			total = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			total = "";
		}

		return total;
	}
}
