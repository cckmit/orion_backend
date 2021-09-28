package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.model.CapacidadeCotasVendasDadosItem;

@Repository
public class CapacidadeCotasVendasCustom {

	private JdbcTemplate jdbcTemplate;

	public CapacidadeCotasVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public long findNextIdCapacidadeCotas() {
		String query = " select nvl(max(a.id),0) + 1 from orion_045 a ";		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {

		String query = " select a.id, a.descricao, " 	    
        + " a.periodo_atual_inicio periodoAtualInicio, a.periodo_atual_final periodoAtualFinal, "
        + " a.periodo_analise_inicio periodoAnaliseInicio, a.periodo_analise_final periodoAnaliseFinal, "
	    + " a.colecoes, "
	    + " a.depositos, "
	    + " a.minutos_periodo "
	    + " from orion_045 a ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadesCotasVendas.class));
	}
	
	public List<Categoria> findCategoriasProd() {
		
		String query = " select b.seq_escolha codigo, b.conteudo_escolha descricao from basi_542 b"
				+ " where b.familia_atributo = '000001' "
				+ " and b.codigo_atributo = 5 " // --ESTE É A CATEGORIA 4 
				+ " and b.seq_escolha > 0 ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Categoria.class));
	}
		
	// OLD -> int colecao, int linha, int periodo, boolean listarTempUnit
	
	public List<CapacidadeCotasVendasDadosItem> findProdutosByCategoriaLinha(long idCapacidadeCotas, String colecoes){
		String query = " select ordenacao.referencia, ordenacao.tamanho, ordenacao.cor, ordenacao.descricao, nvl(categorias.des_categoria,' ') categoria, " 
		+ " ordenacao.bloqueio_venda bloqueioVenda "
		+ " from ( "
		+ " select capac_cotas.referencia, capac_cotas.tamanho, capac_cotas.cor, capac_cotas.descricao, "   
		+ " sum(capac_cotas.bloqueio_venda) bloqueio_venda "
		+ " from ( "
		+ " select orion_046.referencia, orion_046.tamanho, orion_046.cor, basi_010.narrativa descricao, "
		+ " orion_046.bloqueio_venda "
		+ " from orion_045, orion_046, basi_010, basi_030 "  
		+ " where orion_045.id = " + idCapacidadeCotas
		+ " and orion_046.id_capacidade_cotas = orion_045.id "  
		+ " and basi_010.nivel_estrutura (+) = '1' "
		+ " and basi_010.grupo_estrutura (+) = orion_046.referencia " 
		+ " and basi_010.subgru_estrutura (+) = orion_046.tamanho "
		+ " and basi_010.item_estrutura (+) = orion_046.cor "
		+ " union all " 
		+ " select basi_030.referencia, basi_010.subgru_estrutura tamanho, basi_010.item_estrutura cor, basi_010.narrativa descricao, 0 bloqueio_venda "   		
		+ " from basi_030, basi_010 "
		+ " where basi_030.nivel_estrutura = '1' "  
		+ " and basi_030.colecao in (" + colecoes + ")"		                  
		+ " and basi_010.nivel_estrutura = basi_030.nivel_estrutura " 
		+ " and basi_010.grupo_estrutura = basi_030.referencia "
		+ " and basi_010.item_ativo = 0 "
		+ " union all "
		+ " select a.referencia, b.subgru_estrutura tamanho, b.item_estrutura cor, b.narrativa descricao, 0 bloqueio_venda "   		
		+ " from basi_030 a, basi_010 b " 
		+ " where a.colecao in (select 1 from basi_140 "  
		+ " where basi_140.descricao_espanhol like '%PERMANENTE%') " 
		+ " and exists (select 1 from basi_632 c "
		+ " where c.cd_agrupador in (" + colecoes + ")"
	    + "	and c.grupo_ref = a.referencia " 
		+ " and c.subgrupo_ref = b.subgru_estrutura "
		+ " and c.item_ref = b.item_estrutura ) "
		+ " and b.nivel_estrutura = a.nivel_estrutura " 
		+ " and b.grupo_estrutura = a.referencia "
		+ " and b.item_ativo = 0 "                           
		+ " ) capac_cotas " 
		+ " group by capac_cotas.referencia, capac_cotas.tamanho, capac_cotas.cor, capac_cotas.descricao "       
		+ " order by capac_cotas.referencia, capac_cotas.tamanho, capac_cotas.cor, capac_cotas.descricao "
		+ " ) ordenacao, " 
		+ " (select SUBSTR(a.chave_acesso,2,5) AS COD_REFERE, "  
		+ " b.descr_atributo           AS DES_ATRIBUTO, "  
		+ " a.codigo_atributo          AS COD_CATEGORIA, " 
		+ " a.conteudo_atributo        AS DES_CATEGORIA, " 
		+ " (select f.seq_escolha from basi_542 f "
		+ " where f.familia_atributo = a.familia_atributo "  
		+ " and f.codigo_atributo  = a.codigo_atributo "
		+ " and f.conteudo_escolha = a.conteudo_atributo) COD_OPCAO_CATEGORIA "  
		+ " from basi_544 a, basi_541 b "
		+ " where b.codigo_atributo = a.codigo_atributo "  
		+ " and a.familia_atributo = '000001' "
		+ " and a.codigo_grupo_atrib = 1 "
		+ " and a.codigo_subgrupo_atrib = 1 "  
		+ " and a.codigo_atributo = 5) categorias " 
		+ " where categorias.cod_refere (+) = ordenacao.referencia "     
		+ " and exists (select 1 from basi_010 " 
		+ " where basi_010.nivel_estrutura = '1' " 
		+ " and basi_010.grupo_estrutura = categorias.cod_refere " 
		+ " and basi_010.item_ativo = 0) ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadeCotasVendasDadosItem.class));
	}
	
	public float findTempoUnitarioByReferenciaColecao(String referencia, String colecoes) {
		
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
								  + " and (a.colecao in (" + colecoes + ")"   
								  + " or exists (select 1 "
	                                        + " from basi_632 c "
	                                        + " where c.cd_agrupador = (" + colecoes + ")"
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
