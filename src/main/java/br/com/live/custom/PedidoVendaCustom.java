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
	
	public int findSugestaoLocked(int codigoEmpresa) {

		Integer sugestao;

		String query = " select k.nr_sugestao from fatu_503 k "
				+ " where k.sugestao_libera = 1 "
				+ " and k.codigo_empresa = " + codigoEmpresa;

		try {
			sugestao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sugestao = 0;
		}
		return sugestao;
	}
	
	public void liberarSugestao(int numeroSugestao) {
		String query = " update fatu_503 "
				+ "set sugestao_libera = 0"
				+ "where nr_sugestao = " + numeroSugestao;
		jdbcTemplate.update(query);
	}
}
