package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.ConsultaPrevisaoVendas;

@Repository
public class PrevisaoVendasCustom {

	private final JdbcTemplate jdbcTemplate;

	public PrevisaoVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ConsultaPrevisaoVendas> findPrevisaoByColecao(int colecao) {
		
		String query = " select rownum id, nvl(previsao.colecao,0), produtos.grupo, produtos.item, produtos.descricao, " 
			       + " nvl(previsao.col_tab_preco_sell_in,0) colTabPrecoSellIn, "
			       + " nvl(previsao.mes_tab_preco_sell_in,0) mesTabPrecoSellIn, "
			       + " nvl(previsao.seq_tab_preco_sell_in,0) seqTabPrecoSellIn, "
			       + " nvl(previsao.valor_sell_in,0) valorSellIn, "
			       + " nvl(previsao.col_tab_preco_sell_out,0) colTabPrecoSellOut, " 
			       + " nvl(previsao.mes_tab_preco_sell_out,0) mesTabPrecoSellOut, "
			       + " nvl(previsao.seq_tab_preco_sell_out,0) seqTabPrecoSellOut, "
			       + " nvl(previsao.valor_sell_out,0) valorSellOut, "
			       + " nvl(previsao.qtde_previsao,0) qtdePrevisaoVendas " 
			+ " from (select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, 1 from basi_030 a, basi_010 b "
			     + " where a.nivel_estrutura = '1' "
			        + " and a.colecao = " + colecao
			        + " and b.nivel_estrutura = a.nivel_estrutura "
			        + " and b.grupo_estrutura = a.referencia  "
			      + " group by a.referencia, b.item_estrutura "
			      + " union "
			      + " select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, 2 from basi_030 a, basi_010 b "
			      + " where a.nivel_estrutura = '1' "
			        + " and a.colecao in (select c.colecao from basi_140 c "
			                           + " where c.descricao_espanhol like '%COLECAO PERMANENTE%') " 
			        + " and b.nivel_estrutura = a.nivel_estrutura "
			        + " and b.grupo_estrutura = a.referencia "
			        + " and exists (select 1 from basi_632 c "
			                     + " where c.cd_agrupador in " + colecao 
			                       + " and c.grupo_ref = b.grupo_estrutura "
			                       + " and c.item_ref = b.item_estrutura) "                
			      + " group by a.referencia, b.item_estrutura) produtos, orion_040 previsao "
			+ " where previsao.colecao (+) = " + colecao
			  + " and previsao.grupo  (+) = produtos.grupo "
			  + " and previsao.item (+) = produtos.item ";


		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendas.class));				
	}	
	
}
