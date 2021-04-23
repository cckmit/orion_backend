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
	
	public List<ConsultaPrevisaoVendasItens> findPrevisoesVendasItensByIdPrevisaoVenda(long idPrevisaoVendas) {
		
		/*
		select rownum id, nvl(previsao.colecao,0), produtos.grupo, produtos.item, produtos.descricao, 
	       artigo.artigo || '-' || artigo.descr_artigo artigo, 
	       linha.linha_produto || '-' || linha.descricao_linha linha, 
	       produtos.embarque, 
	       to_char(nvl(previsao_item.valor_sell_in,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellIn, 
	       to_char(nvl(previsao_item.valor_sell_out,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellOut, 
	       
	       nvl(previsao_item.grupo_base,''), 
	       nvl(previsao_item.item_base,''), ->  Ver como vai ficar a apresentação desses campos na tela, avaliar a possibilidade de carregar um combo 
	       nvl(previsao_item.percentual_aplicar,0),
	       nvl(previsao_item.qtde_vendida_base,0),

	       nvl(previsao_item.qtde_previsao,0) qtdePrevisaoVendas 
	       from (select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, 
	                    a.linha_produto linha, a.artigo, max(b.codigo_cliente) embarque, 1 
	             from basi_030 a, basi_010 b 
	             where a.nivel_estrutura = '1' 
	               and a.colecao = 150
	               and b.nivel_estrutura = a.nivel_estrutura 
	               and b.grupo_estrutura = a.referencia 
	             group by a.referencia, a.linha_produto, a.artigo, b.item_estrutura 
	             union 
	             select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, 
	                    a.linha_produto linha, a.artigo, max(b.codigo_cliente) embarque, 2 
	              from basi_030 a, basi_010 b 
	              where a.nivel_estrutura = '1' 
	                and a.colecao in (select c.colecao from basi_140 c 
	                                   where c.descricao_espanhol like '%COLECAO PERMANENTE%') 
	                and b.nivel_estrutura = a.nivel_estrutura 
	                and b.grupo_estrutura = a.referencia 
	                and exists (select 1 from basi_632 c 
	                             where c.cd_agrupador = 150 
	                               and c.grupo_ref = b.grupo_estrutura 
	                               and c.item_ref = b.item_estrutura)                 
	              group by a.referencia, a.linha_produto, a.artigo, b.item_estrutura) produtos, 
	                                                                                  orion_040 previsao,
	                                                                                  orion_041 previsao_item, 
	                                                                                  basi_290 artigo, 
	                                                                                  basi_120 linha 
	where previsao.colecao (+) = 150
	  and previsao_item.id_previsao_vendas = previsao.id
	  and previsao_item.grupo (+) = produtos.grupo 
	  and previsao_item.item (+) = produtos.item 
	  and artigo.artigo = produtos.artigo 
	  and linha.linha_produto = produtos.linha 

	  */

	/*  public String id;
	  public String grupo;
	  public String item;
	  public String descricao;    
	  public String artigo; 
	    public String linha;  
	    public String embarque;
	  public String valorSellIn;    
	  public String valorSellOut;                  
	  public String grupoBase;
	  public String itemBase;
	  public double qtdeVendidaBase;
	  public int percentalAplicar;  
	  public int qtdePrevisao;
	*/




		
		
		return null;
	}
	
	// TODO - VERIFICAR OS MÉTODOS QUE DEVERAO SER ELIMINADOS
	
	public List<ConsultaPrevisaoVendasItens> findPrevisaoByColecao(int colecao) {
				
		String query = " select rownum id, nvl(previsao.colecao,0), produtos.grupo, produtos.item, produtos.descricao, "
        			+ " artigo.artigo || '-' || artigo.descr_artigo artigo, "
			        + " linha.linha_produto || '-' || linha.descricao_linha linha, " 
			        + " produtos.embarque, "
			        + " nvl(previsao.col_tab_preco_sell_in,0) colTabPrecoSellIn, " 
			        + " nvl(previsao.mes_tab_preco_sell_in,0) mesTabPrecoSellIn, "
			        + " nvl(previsao.seq_tab_preco_sell_in,0) seqTabPrecoSellIn, "
					+ " to_char(nvl(previsao.valor_sell_in,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellIn, "
			        + " nvl(previsao.col_tab_preco_sell_out,0) colTabPrecoSellOut, "  
			        + " nvl(previsao.mes_tab_preco_sell_out,0) mesTabPrecoSellOut, "
			        + " nvl(previsao.seq_tab_preco_sell_out,0) seqTabPrecoSellOut, "
					+ " to_char(nvl(previsao.valor_sell_out,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellOut, "			        
			        + " nvl(previsao.qtde_previsao,0) qtdePrevisaoVendas "  
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
			         + " group by a.referencia, a.linha_produto, a.artigo, b.item_estrutura) produtos, orion_040 previsao, basi_290 artigo, basi_120 linha "
				 + " where previsao.colecao (+) = " + colecao
				   + " and previsao.grupo (+) = produtos.grupo " 
				   + " and previsao.item (+) = produtos.item " 
				   + " and artigo.artigo = produtos.artigo "
				   + " and linha.linha_produto = produtos.linha ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendasItens.class));				
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
	
	public int findPrevisaoVendaByProduto(String colecoes, String grupo, String item) {
		
		int qtdePrevisao = 0;
		
		String query = " select o.qtde_previsao from orion_040 o " 
				+ " where o.colecao in (" + colecoes + ")"
		  + " and o.grupo = '" + grupo + "' "
		  + " and o.item = '" + item + "' "
		  + " and rownum = 1 ";

		try {
			qtdePrevisao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdePrevisao = 0;
		}
		
		return qtdePrevisao;		
	}
		
}