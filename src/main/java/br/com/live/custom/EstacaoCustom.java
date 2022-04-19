package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaColecoesAgrupador;
import br.com.live.model.ConsultaEstacaoAgrupadores;
import br.com.live.model.ConsultaEstacaoTabelaPreco;
import br.com.live.model.ConsultaMetasCategoria;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class EstacaoCustom {

	private JdbcTemplate jdbcTemplate;

	public EstacaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ConteudoChaveNumerica> findRepresentantes() {

		String query = " select a.cod_rep_cliente value, a.cod_rep_cliente || ' - ' || a.nome_rep_cliente label from pedi_020 a ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public String findDescRepresentante(int codRepresentante) {

		String descRepresentante;

		String query = " select a.nome_rep_cliente from pedi_020 a where a.cod_rep_cliente = " + codRepresentante;

		try {
			descRepresentante = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			descRepresentante = "";
		}
		return descRepresentante;
	}

	public List<ConsultaEstacaoTabelaPreco> findTabelasPreco(long codEstacao) {

		String query = " select a.id, a.col_tab colTab, a.mes_tab mesTab, a.seq_tab seqTab, b.descricao descTabela from orion_073 a, pedi_090 b "
				+ " where a.cod_estacao = " + codEstacao + " and b.col_tabela_preco = a.col_tab"
				+ " and b.mes_tabela_preco = a.mes_tab" + " and b.seq_tabela_preco = a.seq_tab ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaEstacaoTabelaPreco.class));
	}

	public int findNewCodEstacao() {

		int proxCod = 0;

		String query = " select nvl(max(cod_estacao),0) +1 from orion_070 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findNewCodAgrupador() {

		int proxCod = 0;

		String query = " select nvl(max(cod_agrupador),0) +1 from orion_074 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public List<ConsultaColecoesAgrupador> findColecoesGrid(int codAgrupador) {

		String query = " select a.id, a.cod_agrupador codAgrupador, a.colecao, a.sub_colecao subColecao, b.descr_colecao descColecao, c.ds_agrupador descSubColecao from orion_076 a, basi_140 b, basi_630 c "
				+ " where a.cod_agrupador = " + codAgrupador + " and a.colecao = b.colecao "
				+ " and a.sub_colecao = c.cd_agrupador (+)";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaColecoesAgrupador.class));
	}

	public List<ConsultaEstacaoAgrupadores> findAgrupadoresGrid(long codEstacao) {

		String query = " select a.id, a.cod_agrupador codAgrupador, a.cod_estacao codEstacao, b.descricao descAgrupador from orion_075 a, orion_074 b"
				+ " where a.cod_estacao = " + codEstacao + " and a.cod_agrupador = b.cod_agrupador ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaEstacaoAgrupadores.class));
	}

	public String findDescCatalogo(int codCatalogo) {

		String descCatalogo = "";

		String query = " select a.des_catalogo from pedi_063 a " + " where a.cod_catalogo = " + codCatalogo;
		try {
			descCatalogo = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			descCatalogo = "";
		}

		return descCatalogo;
	}

	public void deleteByCodEstacaoAndTipoMeta(long codEstacao, int tipoMeta) {
		
		String query = " delete from orion_072 "
				+ " where orion_072.cod_estacao = " + codEstacao
				+ " and orion_072.tipo_meta = " + tipoMeta;
		
		jdbcTemplate.update(query);
	}
	
	public List<ConsultaMetasCategoria> findMetasCategoriaGrid(long codEstacao, int tipoMeta) {
		
		List<ConsultaMetasCategoria> dadosCategoria = null;
		
		String query = " select a.id id, a.cod_estacao codEstacao, a.cod_representante codRepresentante, b.nome_rep_cliente descRepresentante, a.tipo_meta tipoMeta, a.valor_categoria_1 valorCategoria1, "
				+ " a.valor_categoria_2 valorCategoria2, a.valor_categoria_3 valorCategoria3, a.valor_categoria_4 valorCategoria4, a.valor_categoria_5 valorCategoria5, "
				+ " a.valor_categoria_6 valorCategoria6, a.valor_categoria_7 valorCategoria7, a.valor_categoria_8 valorCategoria8, a.valor_categoria_9 valorCategoria9, "
				+ " a.valor_categoria_10 valorCategoria10 from orion_140 a, pedi_020 b "
				+ " where b.cod_rep_cliente = a.cod_representante "
				+ " and a.tipo_meta = " + tipoMeta
				+ " and a.cod_estacao = " + codEstacao;
		
		try {
			dadosCategoria = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMetasCategoria.class));
		} catch (Exception e) {
			dadosCategoria = new ArrayList<ConsultaMetasCategoria>();
		}
		return dadosCategoria;
	}
}