package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendasItem;
import br.com.live.model.ConsultaPrevisaoVendasItemTam;

@Repository
public class PrevisaoVendasCustom {

	private final JdbcTemplate jdbcTemplate;

	public PrevisaoVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long findNextIdPrevisaoVendas() {

		Integer idPrevisaoVendas;

		String query = " select nvl(max(id),0) + 1 from orion_040 ";

		try {
			idPrevisaoVendas = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idPrevisaoVendas = 0;
		}

		return (long) idPrevisaoVendas;
	}

	public List<ConsultaPrevisaoVendas> findPrevisoesVendas() {

		List<ConsultaPrevisaoVendas> previsoes;

		String query = " select a.id, a.descricao, " + " a.colecao || '-' || b.descr_colecao descricaoColecao, "
				+ "  (select c.col_tabela_preco || '.' || c.mes_tabela_preco || '.' || c.seq_tabela_preco || '-' || c.descricao from pedi_090 c "
				+ "    where c.col_tabela_preco = a.col_tab_preco_sell_in "
				+ "      and c.mes_tabela_preco = a.mes_tab_preco_sell_in "
				+ "      and c.seq_tabela_preco = a.seq_tab_preco_sell_in) descricaoTabPrecoSellIn, "
				+ "  (select d.col_tabela_preco || '.' || d.mes_tabela_preco || '.' || d.seq_tabela_preco || '-' || d.descricao from pedi_090 d "
				+ "    where d.col_tabela_preco = a.col_tab_preco_sell_out "
				+ "      and d.mes_tabela_preco = a.mes_tab_preco_sell_out "
				+ "      and d.seq_tabela_preco = a.seq_tab_preco_sell_out) descricaoTabPrecoSellOut "
				+ " from orion_040 a, basi_140 b " + " where b.colecao = a.colecao " + " order by a.id ";

		try {
			previsoes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendas.class));
		} catch (Exception e) {
			previsoes = new ArrayList<ConsultaPrevisaoVendas>();
		}

		return previsoes;
	}

	public List<ConsultaPrevisaoVendasItem> findPrevisaoVendasItensByIdPrevisaoVendaColecao(long idPrevisaoVendas,
			int colecao) {

		List<ConsultaPrevisaoVendasItem> previsoesItens;
				
		String query = " select rownum id, itens_previsao.grupo, itens_previsao.item, itens_previsao.descricao, "
		+ " itens_previsao.artigo, itens_previsao.linha, itens_previsao.embarque, "
	    + " itens_previsao.valorSellIn, itens_previsao.valorSellOut, "
	    + " itens_previsao.grupoBase, itens_previsao.itemBase, itens_previsao.descricaoBase, "
	    + " itens_previsao.percAplicar, itens_previsao.qtdeVendidaBase, itens_previsao.qtdePrevisao "   
	    + " from ( "
		+ " select produtos.grupo, produtos.item, produtos.descricao, "
        + " artigo.artigo || '-' || max(artigo.descr_artigo) artigo, "
        + " linha.linha_produto || '-' || max(linha.descricao_linha) linha, "  
        + " produtos.embarque, "
        + " to_char(nvl(previsao_item.valor_sell_in,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellIn, " 
        + " to_char(nvl(previsao_item.valor_sell_out,0), 'FM9999D90', 'nls_numeric_characters=''.,''' ) valorSellOut, " 
        + " nvl(previsao_item.grupo_base,'') grupoBase, "  
        + " nvl(previsao_item.item_base,'') itemBase, "         
        + " nvl(max(m.descr_referencia),'') || ' - ' || nvl(max(n.descricao_15),'') descricaoBase, "         
        + " nvl(previsao_item.percentual_aplicar,0) percAplicar, "
        + " nvl(previsao_item.qtde_vendida_base,0) qtdeVendidaBase, " 
        + " nvl(previsao_item.qtde_previsao,0) qtdePrevisao "
        + " from (select a.referencia grupo, b.item_estrutura item, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, " 
        + " a.linha_produto linha, a.artigo, max(b.codigo_cliente) embarque, 1 "
        + " from basi_030 a, basi_010 b  "
        + " where a.nivel_estrutura = '1' " 
        + " and a.colecao = " + colecao
        + " and b.nivel_estrutura = a.nivel_estrutura "  
        + " and b.grupo_estrutura = a.referencia "
        + " and b.item_ativo = 0 "
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
        + " and b.item_ativo = 0 "
        + " and exists (select 1 from basi_632 c " 
        + " where c.cd_agrupador = " + colecao
        + " and c.grupo_ref = b.grupo_estrutura " 
        + " and c.item_ref = b.item_estrutura) "
        + " group by a.referencia, a.linha_produto, a.artigo, b.item_estrutura) produtos, orion_041 previsao_item, basi_290 artigo, basi_120 linha, basi_030 m, basi_010 n "
        + " where previsao_item.id_previsao_vendas (+) = " + idPrevisaoVendas 
        + " and previsao_item.grupo (+) = produtos.grupo " 
        + " and previsao_item.item (+) = produtos.item "
        + " and artigo.artigo = produtos.artigo " 
        + " and linha.linha_produto = produtos.linha "          
        + " and m.nivel_estrutura (+) = '1' "
        + " and m.referencia (+) = previsao_item.grupo_base "
        + " and n.nivel_estrutura (+) = '1' "
        + " and n.grupo_estrutura (+) = previsao_item.grupo_base "
        + " and n.item_estrutura (+) = previsao_item.item_base "        
        + " group by produtos.grupo, produtos.item, produtos.descricao, artigo.artigo, linha.linha_produto, "   
        + " produtos.embarque, previsao_item.valor_sell_in, previsao_item.valor_sell_out, "
        + " previsao_item.grupo_base, previsao_item.item_base, previsao_item.percentual_aplicar, " 
        + " previsao_item.qtde_vendida_base, previsao_item.qtde_previsao "
        + " order by produtos.grupo, produtos.item "
        + " ) itens_previsao ";

		try {
			previsoesItens = jdbcTemplate.query(query,
					BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendasItem.class));
		} catch (Exception e) {
			previsoesItens = new ArrayList<ConsultaPrevisaoVendasItem>();
		}

		return previsoesItens;
	}

	public List<ConsultaPrevisaoVendasItemTam> findPrevisaoVendasItemTamByIdPrevisaoVendasGrupoItem(
			long idPrevisaoVendas, String grupo, String item) {

		List<ConsultaPrevisaoVendasItemTam> tamanhos;

		String query = " select orion041.id_previsao_vendas idPrevisaoVendas, orion041.id_item_previsao_vendas idItemPrevisaoVendas, nvl(f.id,'') id, orion041.sub, orion041.ordem, nvl(f.qtde_previsao,0) qtdePrevisao "
				+ " from (select o.id_previsao_vendas, o.id id_item_previsao_vendas, b.tamanho_ref sub, b.ordem_tamanho ordem "
				+ " from orion_041 o, basi_020 a, basi_220 b " + " where o.id_previsao_vendas = '" + idPrevisaoVendas
				+ "'" + " and o.grupo = '" + grupo + "'" + " and o.item = '" + item + "'"
				+ " and a.basi030_nivel030 = '1' " + " and a.basi030_referenc = o.grupo "
				+ " and b.tamanho_ref = a.tamanho_ref " + " order by o.id, b.ordem_tamanho) orion041, orion_042 f "
				+ " where f.id_previsao_vendas (+)= orion041.id_previsao_vendas "
				+ " and f.id_item_previsao_vendas (+)= orion041.id_item_previsao_vendas "
				+ " and f.sub (+)= orion041.sub ";

		try {
			tamanhos = jdbcTemplate.query(query,
					BeanPropertyRowMapper.newInstance(ConsultaPrevisaoVendasItemTam.class));
		} catch (Exception e) {
			tamanhos = new ArrayList<ConsultaPrevisaoVendasItemTam>();
		}

		return tamanhos;
	}

	public int findQtdeVendidaByItem(String grupo, String item) {

		int qtdeVendida = 0;

		String query = " select sum(b.qtde_faturada) from pedi_100 a, pedi_110 b " + " where a.situacao_venda   = 10 "
				+ " and a.tecido_peca      = '1' " + " and b.pedido_venda     = a.pedido_venda "
				+ " and b.cod_cancelamento = 0 " + " and b.cd_it_pe_nivel99 = '1' " + " and b.cd_it_pe_grupo   = '"
				+ grupo + "'" + " and b.cd_it_pe_item = '" + item + "'";

		try {
			qtdeVendida = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeVendida = 0;
		}

		return qtdeVendida;
	}

	public int findQtdePrevisaoByIdPrevisaoVendasGrupoItem(long idPrevisaoVendas, String grupo, String item) {

		int qtdePrevisao = 0;
		String query = "";

		query = " select sum(o.qtde_previsao) from orion_042 o "
		+ " where o.id_previsao_vendas = " + idPrevisaoVendas
		+ " and o.grupo = '" + grupo + "'" 
		+ " and o.item = '" + item + "'";		

		try {
			qtdePrevisao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdePrevisao = 0;
		}

		if (qtdePrevisao <= 0) {
			query = " select o.qtde_previsao from orion_041 o " 
		    + " where o.id_previsao_vendas = " + idPrevisaoVendas
			+ " and o.grupo = '" + grupo + "'" 
		    + " and o.item = '" + item + "'";

			try {
				qtdePrevisao = jdbcTemplate.queryForObject(query, Integer.class);
			} catch (Exception e) {
				qtdePrevisao = 0;
			}
		}
		
		return qtdePrevisao;
	}

}