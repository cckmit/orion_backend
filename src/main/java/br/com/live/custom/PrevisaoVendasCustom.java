package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendasItens;

@Repository
public class PrevisaoVendasCustom {

	private final JdbcTemplate jdbcTemplate;

	public PrevisaoVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ConsultaPrevisaoVendas> findPrevisoesVendas() {
		
		List<ConsultaPrevisaoVendas> previsoes;
		
		String query = " select a.id, a.descricao, "
			      + " a.colecao || '-' || b.descr_colecao descricaoColecao, "
			      + "  (select c.col_tabela_preco || '.' || c.mes_tabela_preco || '.' || c.seq_tabela_preco || '-' || c.descricao from pedi_090 c "
			      + "    where c.col_tabela_preco = a.col_tab_preco_sell_in "
			      + "      and c.mes_tabela_preco = a.mes_tab_preco_sell_in "
			      + "      and c.seq_tabela_preco = a.seq_tab_preco_sell_in) descricaoTabPrecoSellIn, "
			      + "  (select d.col_tabela_preco || '.' || d.mes_tabela_preco || '.' || d.seq_tabela_preco || '-' || d.descricao from pedi_090 d "
			      + "    where d.col_tabela_preco = a.col_tab_preco_sell_out "
			      + "      and d.mes_tabela_preco = a.mes_tab_preco_sell_out "
			      + "      and d.seq_tabela_preco = a.seq_tab_preco_sell_out) descricaoTabPrecoSellOut "           
			+ " from orion_040 a, basi_140 b "
			+ " where b.colecao = a.colecao "
			+ " order by a.id desc ";
		
		try {
			previsoes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendas.class));
		} catch (Exception e) {
			previsoes = new ArrayList <ConsultaPrevisaoVendas> () ;
		}		
		
		return previsoes;
	}	
	
	public List<ConsultaPrevisaoVendasItens> findPrevisoesVendasItensByIdPrevisaoVendaColecao(long idPrevisaoVendas, int colecao) {
		
		List<ConsultaPrevisaoVendasItens> previsoesItens;
		
		String query = " select rownum id, produtos.grupo, produtos.item, produtos.descricao, "
	     + " artigo.artigo || '-' || artigo.descr_artigo artigo, "
	     + " linha.linha_produto || '-' || linha.descricao_linha linha, " 
	     + " produtos.embarque, "
	     + " to_char(nvl(previsao_item.valor_sell_in,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellIn, " 
	     + " to_char(nvl(previsao_item.valor_sell_out,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellOut, "         
	     + " nvl(previsao_item.grupo_base,'') grupoBase, " 
      	 + " nvl(previsao_item.item_base,'') itemBase, " 
      	 + " nvl(previsao_item.percentual_aplicar,0) percentualAplicar, " 
      	 + " nvl(previsao_item.qtde_vendida_base,0) qtdeVendidaBase, "
      	 + " nvl(previsao_item.qtde_previsao,0) qtdePrevisao "
      	 + " from (select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, " 
	     + " a.linha_produto linha, a.artigo, max(b.codigo_cliente) embarque, 1 " 
	     + " from basi_030 a, basi_010 b "
	     + " where a.nivel_estrutura = '1' " 
	     + " and a.colecao = " + colecao
	     + " and b.nivel_estrutura = a.nivel_estrutura " 
	     + " and b.grupo_estrutura = a.referencia " 
	     + " group by a.referencia, a.linha_produto, a.artigo, b.item_estrutura " 
	     + " union " 
	     + " select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, " 
	     + " a.linha_produto linha, a.artigo, max(b.codigo_cliente) embarque, 2 "
	     + " from basi_030 a, basi_010 b "
	     + " where a.nivel_estrutura = '1' " 
	     + " and a.colecao in (select c.colecao from basi_140 c " 
	     + " where c.descricao_espanhol like '%COLECAO PERMANENTE%') " 
	     + " and b.nivel_estrutura = a.nivel_estrutura " 
	     + " and b.grupo_estrutura = a.referencia "
	     + " and exists (select 1 from basi_632 c "
	     + " where c.cd_agrupador = " + colecao
	     + " and c.grupo_ref = b.grupo_estrutura " 
	     + " and c.item_ref = b.item_estrutura) "                
	     + " group by a.referencia, a.linha_produto, a.artigo, b.item_estrutura) produtos, orion_041 previsao_item, basi_290 artigo, basi_120 linha "
		 + " where previsao_item.id_previsao_vendas (+) = " + idPrevisaoVendas
		 + " and previsao_item.grupo (+) = produtos.grupo "
		 + " and previsao_item.item (+) = produtos.item "
		 + " and artigo.artigo = produtos.artigo "
		 + " and linha.linha_produto = produtos.linha ";

		try {
			previsoesItens = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendasItens.class));
		} catch (Exception e) {
			previsoesItens = new ArrayList <ConsultaPrevisaoVendasItens> () ;
		}		
		
		return previsoesItens;
	}
	
	public int findQtdeVendidaByItem(String grupo, String item) {
		
		int qtdeVendida = 0;
		
		String query = " select sum(b.qtde_faturada) from pedi_100 a, pedi_110 b "
		+ " where a.situacao_venda   = 10 "
		  + " and a.tecido_peca      = '1' "
		  + " and b.pedido_venda     = a.pedido_venda "
		  + " and b.cod_cancelamento = 0 "
		  + " and b.cd_it_pe_nivel99 = '1' " 
		  + " and b.cd_it_pe_grupo   = '" + grupo + "'"		  
		  + " and b.cd_it_pe_item = '" + item + "'";
		
		try {
			qtdeVendida = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeVendida = 0;
		}
		
		return qtdeVendida;
	}	
	
	
	public String findIdTabelaSellIn(int colecao) {
		
		String id = "";
		
		String query = " select o.col_tab_preco_sell_in || '.' || o.mes_tab_preco_sell_in || '.' || o.seq_tab_preco_sell_in id from orion_040 o " 
					 + " where o.colecao = " + colecao
					 + " and rownum = 1 ";
		
		try {
			id = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			id = "";
		}
		
		return id;
	}
	
	public String findIdTabelaSellOut(int colecao) {
		
		String id = "";
		
		String query = " select o.col_tab_preco_sell_out || '.' || o.mes_tab_preco_sell_out || '.' || o.seq_tab_preco_sell_out id from orion_040 o " 
					 + " where o.colecao = " + colecao
					 + " and rownum = 1 ";
		
		try {
			id = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			id = "";
		}
		
		return id;
	}
	
	public int findPrevisaoVendaByProduto(long idPrevisaoVendas, String grupo, String item) {
		
		int qtdePrevisao = 0;
		
		String query = " select sum(o.qtde_previsao) from orion_041 o " 
 		  + " where o.id_previsao_vendas = " + idPrevisaoVendas
		  + " and o.grupo = '" + grupo + "' "
		  + " and o.item = '" + item + "' ";		  

		try {
			qtdePrevisao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdePrevisao = 0;
		}
		
		return qtdePrevisao;		
	}
		
}