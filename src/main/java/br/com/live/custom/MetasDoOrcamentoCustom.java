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

		String query = " select nvl(l.id,'') id, nvl(l.tipo_meta," + tipoMeta + ") tipoMeta, j.live_agrup_tipo_cliente descricao, nvl(l.valor_mes_1,0) valorMes1, nvl(l.valor_mes_2,0) valorMes2, nvl(l.valor_mes_3,0) valorMes3, nvl(l.valor_mes_4,0) valorMes4, "
				+ " nvl(l.valor_mes_5,0) valorMes5, nvl(l.valor_mes_6,0) valorMes6, nvl(l.valor_mes_7,0) valorMes7, nvl(l.valor_mes_8,0) valorMes8, nvl(l.valor_mes_9,0) valorMes9, nvl(l.valor_mes_10,0) valorMes10, "
				+ " nvl(l.valor_mes_11,0) valorMes11, nvl(l.valor_mes_12,0) valorMes12  from pedi_085 j, orion_150 l "
				+ " where j.live_agrup_tipo_cliente <> ' ' "
				+ " and l.descricao (+) = j.live_agrup_tipo_cliente "
				+ " and l.ano (+) = " + ano
				+ " and l.tipo_meta (+) = " + tipoMeta
				+ " group by j.live_agrup_tipo_cliente, l.id, l.tipo_meta, l.valor_mes_1, l.valor_mes_2, l.valor_mes_3, l.valor_mes_4, l.valor_mes_5, l.valor_mes_6, l.valor_mes_7, l.valor_mes_8, l.valor_mes_9, "
				+ " l.valor_mes_10, l.valor_mes_11, l.valor_mes_12 "
				+ " order by j.live_agrup_tipo_cliente";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMetasOrcamento.class));
	}
}
