package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.TabelaPreco;
import br.com.live.util.ConteudoChaveAlfaNum;

@Repository
public class TabelaPrecoCustom {

	private JdbcTemplate jdbcTemplate;

	public TabelaPrecoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<TabelaPreco> findAll() {

		String query = " select tabelas.id, tabelas.colecao, tabelas.mes, tabelas.sequencia, tabelas.descricao "
				+ " from (select p.col_tabela_preco || '.' || p.mes_tabela_preco || '.' || p.seq_tabela_preco id, "
				+ " p.col_tabela_preco colecao, p.mes_tabela_preco mes, p.seq_tabela_preco sequencia, p.descricao " 
				+ " from pedi_090 p "
				+ " union select '0.0.0' id, 0 colecao, 0 mes, 0 sequencia, 'SEM TABELA DE PREÇO' descricao from dual) tabelas "
				+ " order by tabelas.colecao, tabelas.mes, tabelas.sequencia ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TabelaPreco.class));
	}
	
	public List<TabelaPreco> findAllTabelas() {

		String query = " select p.col_tabela_preco || '.' || p.mes_tabela_preco || '.' || p.seq_tabela_preco id, "
				+ " p.col_tabela_preco colecao, p.mes_tabela_preco mes, p.seq_tabela_preco sequencia, p.descricao "
				+ " from pedi_090 p ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TabelaPreco.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllTabelasAsync(String leitor) {
		String query = " select a.col_tabela_preco || '.' || a.mes_tabela_preco || '.' || a.seq_tabela_preco value, "
				+ " a.col_tabela_preco || '.' || a.mes_tabela_preco || '.' || a.seq_tabela_preco || "
				+ " ' - ' || a.descricao label from pedi_090 a "
				+ " where a.col_tabela_preco || '.' || a.mes_tabela_preco || '.' || a.seq_tabela_preco || a.descricao like '%" + leitor + "%'";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public double findPrecoProduto(int colecao, int mes, int sequencia, String grupo, String item) {
		
		String query = ""; 
		Double valor = 0.000;
		
		query = "select pedi_095.val_tabela_preco "	        
	        + " from pedi_095 " 
	        + " where pedi_095.tab_col_tab        = " + colecao
	          + " and pedi_095.tab_mes_tab        = " + mes
	          + " and pedi_095.tab_seq_tab        = " + sequencia
	          + " and pedi_095.nivel_estrutura    = '1'" 
	          + " and pedi_095.grupo_estrutura    = '" + grupo + "'"          
	          + " and pedi_095.item_estrutura     = '" + item + "'"
	          + " and pedi_095.nivel_preco        = 4 "
	          + " and rownum = 1 ";
		
		try {
			valor = jdbcTemplate.queryForObject(query, Double.class);
		} catch (Exception e) {
			valor = 0.000;
		}
		
		if (valor <= 0.000) {
			query = "select pedi_095.val_tabela_preco "			        
			        + " from pedi_095 " 
			        + " where pedi_095.tab_col_tab        = " + colecao
			          + " and pedi_095.tab_mes_tab        = " + mes
			          + " and pedi_095.tab_seq_tab        = " + sequencia
			          + " and pedi_095.nivel_estrutura    = '1'" 
			          + " and pedi_095.grupo_estrutura    = '" + grupo + "'"          			          
			          + " and pedi_095.nivel_preco        = 1 "
			          + " and rownum = 1 ";

			try {
				valor = jdbcTemplate.queryForObject(query, Double.class);
			} catch (Exception e) {								
				valor = 0.000;
			}
		}
		
		return valor;
	}
	
	public int validarReferenciaCorCadastrada(String referencia, String cor) {
		int existeRef = 0;
		
		String query = " select nvl(max(1), 0) from basi_010 a "
				+ " where a.nivel_estrutura = '1' "
				+ " and a.grupo_estrutura = '" + referencia + "' ";
				
		if (!cor.equalsIgnoreCase("Sem Cor")) {
			query = query + " and a.item_estrutura = '" + cor + "' ";
		}
		
		try {
			existeRef = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {								
			existeRef = 0;
		}
		
		return existeRef;
	}
	
	public void insereTabelaPreco(int coluna, int mes, int sequencia, int nivelPreco, String nivel, String grupo, String subGrupo, String item, int serie, float valor) {
		 try {
	          String query = " insert into pedi_095(tab_col_tab, tab_mes_tab, tab_seq_tab, nivel_preco, nivel_estrutura, grupo_estrutura, "
	            + " subgru_estrutura, item_estrutura, serie_cor, val_tabela_preco, desconto_maximo, largura, data_form_preco) " 
	            + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, sysdate) ";
	          
	          jdbcTemplate.update(query, coluna, mes, sequencia, nivelPreco, nivel, grupo, subGrupo, item, serie, valor);
	        } catch (Exception e) {
	        	String queryException = " update pedi_095 "
	            	+ " set val_tabela_preco 	= ?, "
	                + " data_form_preco 		= sysdate "
	                	+ " where tab_col_tab 		= ?"
	                	+ " and tab_mes_tab 		= ? "
	                	+ " and tab_seq_tab 		= ? "
	                	+ " and nivel_preco 		= ? "
	                	+ " and nivel_estrutura 	= ? "
	                	+ " and grupo_estrutura 	= ? "
	                	+ " and subgru_estrutura 	= ? "
	                	+ " and item_estrutura 		= ? "
	                	+ " and serie_cor 			= ? ";
	        	jdbcTemplate.update(queryException, valor, coluna, mes, sequencia, nivelPreco, nivel, grupo, subGrupo, item, serie);
	        }
	}
}