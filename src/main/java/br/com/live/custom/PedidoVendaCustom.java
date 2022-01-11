package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.PedidoVenda;

@Repository
public class PedidoVendaCustom {

	private JdbcTemplate jdbcTemplate;

	public PedidoVendaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PedidoVenda> findOrigensPedido() {
		String query = " select a.origem id, a.descr_origem descricao, nvl(b.perc_comissao, 0) percComissao from pedi_115 a, orion_095 b "
				+ " where b.origem (+) = a.origem "
				+ " order by a.origem ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PedidoVenda.class));
	}
	
	public void deleteAllOrigens() {
		String query = " delete from orion_095 ";
		
		jdbcTemplate.update(query);
	}
}
