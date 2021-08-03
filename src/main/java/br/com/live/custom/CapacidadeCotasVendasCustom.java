package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.model.ProdutosCapacidadeProd;

@Repository
public class CapacidadeCotasVendasCustom {

	private JdbcTemplate jdbcTemplate;

	public CapacidadeCotasVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {
		
		String query = " select a.periodo, b.data_ini_periodo dataInicial, b.data_fim_periodo dataFinal, a.id, a.linha, c.descricao_linha descLinha, a.categoria, d.conteudo_escolha descCategoria, nvl(sum(e.qtde_minutos),0) minutos, nvl(sum(e.qtde_pecas),0) pecas from orion_045 a, pcpc_010 b, basi_120 c, basi_542 d, orion_046 e "
				+ " where b.periodo_producao = a.periodo "
				+ " and b.area_periodo = 1 "
				+ " and c.linha_produto = a.linha "
				+ " and d.familia_atributo = '000001' "
				+ "	and d.codigo_atributo = 5 "
				+ " and d.seq_escolha = a.categoria "
				+ " and e.id_capacidade_cotas (+) = a.id "
				+ " group by a.periodo, b.data_ini_periodo, b.data_fim_periodo, a.linha, c.descricao_linha, a.categoria, d.conteudo_escolha, a.id "
				+ " order by a.periodo, a.linha, a.categoria ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadesCotasVendas.class));
	}
	
	public List<Categoria> findCategoriasProd() {
		
		String query = " select b.seq_escolha codigo, b.conteudo_escolha descricao from basi_542 b"
				+ " where b.familia_atributo = '000001' "
				+ " and b.codigo_atributo = 5 " // --ESTE É A CATEGORIA 4 
				+ " and b.seq_escolha > 0 ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Categoria.class));
	}
	
	public List<ProdutosCapacidadeProd> findProdutosByCategoriaLinha(int categoria, int linha, int periodo, boolean listarComQtde){
		
		String query = " select ordenacao.modelo, ordenacao.descricao, ordenacao.minutos, ordenacao.pecas "
				+ " from "
				+ " ( "
				+ " select capac_cotas.modelo, capac_cotas.descricao, sum(capac_cotas.minutos) minutos, sum(capac_cotas.pecas) pecas "
				+ " from "
				+ " ( "
				+ " select orion_046.modelo, nvl(basi_030.descr_referencia,'DEMAIS MODELOS') descricao, orion_046.qtde_minutos minutos, orion_046.qtde_pecas pecas "
				+ " from orion_045, orion_046, basi_030 "
				+ " where orion_045.periodo = " + periodo
				+ " and orion_045.linha = " + linha
				+ " and orion_045.categoria = " + categoria
				+ " and orion_046.id_capacidade_cotas = orion_045.id "
				+ " and basi_030.nivel_estrutura (+) = '1' "
				+ " and basi_030.referencia (+) = orion_046.modelo "
				+ " union all "
				+ " select '00000' modelo, 'DEMAIS MODELOS' descricao, 0 minutos, 0 pecas from dual "
				+ " union all "
				+ " select basi_030.referencia modelo, basi_030.descr_referencia descricao, 0 minutos, 0 pecas "
				+ " from basi_030, (select SUBSTR(a.chave_acesso,2,5) AS COD_REFERE, "
				+ " b.descr_atributo           AS DES_ATRIBUTO, "
				+ " a.codigo_atributo          AS COD_CATEGORIA, "
				+ " a.conteudo_atributo        AS DES_CATEGORIA, "
				+ " (select f.seq_escolha from basi_542 f "
				+ " where f.familia_atributo = a.familia_atributo "
				+ " and f.codigo_atributo  = a.codigo_atributo "
				+ " and f.conteudo_escolha = a.conteudo_atributo) COD_OPCAO_CATEGORIA "
				+ " from basi_544 a, "
				+ " basi_541 b "
				+ " where b.codigo_atributo = a.codigo_atributo "
				+ " and a.familia_atributo = '000001' "
				+ " and a.codigo_grupo_atrib = 1 "
				+ " and a.codigo_subgrupo_atrib = 1 "
				+ " and a.codigo_atributo = 5) categorias"
				+ " where basi_030.nivel_estrutura = '1' "
				+ " and basi_030.linha_produto in ( " + linha + ") "
				+ " and categorias.cod_refere = basi_030.referencia "
				+ " and categorias.cod_opcao_categoria in ( " + categoria + " ) "
				+ " ) capac_cotas "
				+ " group by capac_cotas.modelo, capac_cotas.descricao "
				+ " order by capac_cotas.modelo, capac_cotas.descricao "
				+ " ) ordenacao ";
					
		        if (listarComQtde) {
		        	query += " where (ordenacao.minutos > 0 or ordenacao.pecas > 0) ";
		        }
		
		System.out.println("SQL: " + query);
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProdutosCapacidadeProd.class));
	}
	
	public List<ProdutosCapacidadeProd> findModelosByidCapacidadeCotas(String idCapacidadeCotas){
		
		String query = " select a.modelo modelo, b.descr_referencia descricao, a.qtde_minutos minutos, a.qtde_pecas pecas from orion_046 a, basi_030 b "
				+ "	where a.id_capacidade_cotas = '" + idCapacidadeCotas + "'"
				+ "	and b.referencia = a.modelo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProdutosCapacidadeProd.class));
	}

}
