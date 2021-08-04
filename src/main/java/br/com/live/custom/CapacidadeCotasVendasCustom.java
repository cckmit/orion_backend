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
		
		String query = " select a.periodo, b.data_ini_periodo dataInicial, b.data_fim_periodo dataFinal, a.id, a.linha, c.descricao_linha descLinha, a.colecao, d.descr_colecao descColecao, nvl(sum(e.qtde_minutos),0) minutos, nvl(sum(e.qtde_pecas),0) pecas from orion_045 a, pcpc_010 b, basi_120 c, basi_140 d, orion_046 e "
				+ " where b.periodo_producao = a.periodo "
				+ " and b.area_periodo = 1 "
				+ " and c.linha_produto = a.linha "
				+ " and d.colecao = a.colecao"
				+ " and e.id_capacidade_cotas (+) = a.id "
				+ " group by a.periodo, b.data_ini_periodo, b.data_fim_periodo, a.linha, c.descricao_linha, a.colecao, d.descr_colecao, a.id "
				+ " order by a.periodo, a.linha, a.colecao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadesCotasVendas.class));
	}
	
	public List<Categoria> findCategoriasProd() {
		
		String query = " select b.seq_escolha codigo, b.conteudo_escolha descricao from basi_542 b"
				+ " where b.familia_atributo = '000001' "
				+ " and b.codigo_atributo = 5 " // --ESTE É A CATEGORIA 4 
				+ " and b.seq_escolha > 0 ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Categoria.class));
	}
	
	public List<ProdutosCapacidadeProd> findProdutosByCategoriaLinha(int colecao, int linha, int periodo, boolean listarComQtde){
		
		String query = " select ordenacao.modelo, ordenacao.descricao, categorias.des_categoria categoria, ordenacao.tempo_unit tempoUnitario, ordenacao.minutos, ordenacao.pecas " 
		  + " from (select capac_cotas.modelo, capac_cotas.descricao, sum(capac_cotas.tempo_unit) tempo_unit, sum(capac_cotas.minutos) minutos, sum(capac_cotas.pecas) pecas " 
		  + " from (select orion_046.modelo, basi_030.descr_referencia descricao, orion_046.tempo_unitario tempo_unit, orion_046.qtde_minutos minutos, orion_046.qtde_pecas pecas " 
		  + " from orion_045, orion_046, basi_030 " 
		  + " where orion_045.periodo = " + periodo
		  + " and orion_045.colecao = " + colecao
		  + " and orion_045.linha = " + linha                                     
		  + " and orion_046.id_capacidade_cotas = orion_045.id " 
		  + " and basi_030.nivel_estrutura (+) = '1' "
		  + " and basi_030.referencia (+) = orion_046.modelo " 		                   
		  + " union all "
		  + " select basi_030.referencia modelo, basi_030.descr_referencia descricao, 0 tempo_unit, 0 minutos, 0 pecas " 
		  + " from basi_030 "
		  + " where basi_030.nivel_estrutura = '1' " 
		  + " and basi_030.colecao = " + colecao
		  + " and basi_030.linha_produto = " + linha		                    
		  + " union all "	                  
		  + " select a.referencia modelo , a.descr_referencia descricao, 0 tempo_unit, 0 minutos, 0 pecas "
		  + " from basi_030 a "
		  + " where a.linha_produto = " + linha
		  + " and exists (select 1 from basi_631 b "
		  + " where b.cd_agrupador = " + colecao
		  + " and b.grupo_ref = a.referencia)) capac_cotas " 
		  + " group by capac_cotas.modelo, capac_cotas.descricao "		  
		  + " order by capac_cotas.modelo, capac_cotas.descricao "
		  + " ) ordenacao, " 
		  + " (select SUBSTR(a.chave_acesso,2,5) AS COD_REFERE, " 
		  + " b.descr_atributo           AS DES_ATRIBUTO,  " 
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
		  + " and a.codigo_atributo = 5) categorias "
		  + " where categorias.cod_refere = ordenacao.modelo ";

        if (listarComQtde) {
        	query += " and (ordenacao.minutos > 0 or ordenacao.pecas > 0) ";
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

	public float findTempoUnitarioByReferenciaColecao(String referencia, int colecao) {
		
		float minutosUnitario;
		
		String query = " select nvl(sum(m.minutos_homem),0) minutos "
		+ " from mqop_050 m "
		+ " where m.nivel_estrutura  = '1' "
		  + " and m.grupo_estrutura  = '" + referencia + "' " 
		  + " and m.codigo_estagio   = 20 " // ESTAGIO DE COSTURA
		  + " and exists (select 1 "
		                 + " from (select a.referencia modelo, min(b.numero_alternati) alternativa, min(b.numero_roteiro) roteiro "
		                         + " from basi_030 a, basi_010 b "
		                        + " where a.nivel_estrutura = '1' "
		                          + " and a.referencia = '" + referencia + "' " 
		                          + " and b.nivel_estrutura = a.nivel_estrutura "
		                          + " and b.grupo_estrutura = a.referencia "
		                          + " and b.item_ativo = 0 "
		                          + " and b.numero_alternati > 0 "
		                          + " and b.numero_roteiro   > 0 "		                         		                          
								  + " and (a.colecao = " + colecao   
								  + " or exists (select 1 "
	                                        + " from basi_632 c "
	                                       + " where c.cd_agrupador = " + colecao
	                                         + " and c.grupo_ref = b.grupo_estrutura "
	                                         + " and c.item_ref  = b.item_estrutura)) "
		                        + " group by a.referencia) roteiro "
		                + " where roteiro.modelo = m.grupo_estrutura "
		                  + " and roteiro.alternativa = m.numero_alternati " 
		                  + " and roteiro.roteiro = m.numero_roteiro) " ;        

		
		try {
			minutosUnitario = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			minutosUnitario = 0;
		}
		
		return minutosUnitario; 
	}		
}
