package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.TabelaPreco;

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
	
}