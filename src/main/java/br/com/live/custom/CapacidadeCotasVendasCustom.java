package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.com.live.model.CapacidadeCotasVendas;
import br.com.live.util.ConvertePeriodo;

@Repository
public class CapacidadeCotasVendasCustom {

	private JdbcTemplate jdbcTemplate;

	public CapacidadeCotasVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CapacidadeCotasVendas> findItensByFiltros(int periodoAtualInicial, int periodoAtualFinal, int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes, String depositos){

		String query = " select ordenacao.referencia, ordenacao.tamanho, ordenacao.cor, ordenacao.descricao, nvl(categorias.des_categoria,' ') categoria, "
		+ " nvl(depositos.quantidade,0) qtdeEstoque, "
		+ " nvl(demandas_atual.quantidade,0) qtdeDemandaAtual, "
		+ " nvl(demandas_analise.quantidade,0) qtdeDemanda, "   		
		+ " nvl(processos.quantidade,0) qtdeProcesso "
		+ " from ( "
		+ " select capac_cotas.referencia, capac_cotas.tamanho, capac_cotas.cor, capac_cotas.descricao "    		
		+ " from ( "
		+ " select basi_030.referencia, basi_010.subgru_estrutura tamanho, basi_010.item_estrutura cor, basi_010.narrativa descricao "    		
		+ " from basi_030, basi_010 "
		+ " where basi_030.nivel_estrutura = '1' "   
		+ " and basi_030.colecao in (" + colecoes + ")"		                  
		+ " and basi_010.nivel_estrutura = basi_030.nivel_estrutura "  
		+ " and basi_010.grupo_estrutura = basi_030.referencia "
		+ " and basi_010.item_ativo = 0 "
		+ " union all " 
		+ " select a.referencia, b.subgru_estrutura tamanho, b.item_estrutura cor, b.narrativa descricao "        
		+ " from basi_030 a, basi_010 b "  
		+ " where a.colecao in (select basi_140.colecao from basi_140 "   
		+ " where basi_140.descricao_espanhol like '%PERMANENTE%') "  
		+ " and exists (select 1 from basi_632 c " 
		+ " where c.cd_agrupador in (" + colecoes + ")"
		+ " and c.grupo_ref = a.referencia "  
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
		+ " and a.codigo_atributo = 5) categorias, "
		+ " /* DEPOSITOS */ "
		+ " (select a.cditem_grupo grupo, a.cditem_subgrupo tamanho, a.cditem_item cor, nvl(sum(a.qtde_estoque_atu),0) quantidade " 
		+ " from estq_040 a " 
		+ " where a.cditem_nivel99  = '1' "
		+ " and a.deposito in (" + depositos + ")"
		+ " group by a.cditem_grupo, a.cditem_subgrupo, a.cditem_item) depositos, "     			               
		+ " /* DEMANDAS ATUAL */ "
		+ " (select vendas.nivel, vendas.grupo, vendas.sub, vendas.item, nvl(sum(vendas.quantidade),0) quantidade "
		+ " from "
		+ "  (select a.cd_it_pe_nivel99 nivel, a.cd_it_pe_grupo grupo, a.cd_it_pe_subgrupo sub, a.cd_it_pe_item item, sum(a.qtde_pedida - a.qtde_faturada) quantidade " 
		+ " from pedi_100 p, pedi_110 a " 
		+ " where p.situacao_venda <> 10 " 
		+ " and p.cod_cancelamento = 0 " 
		+ " and p.tecido_peca = '1' " 
		+ " and a.pedido_venda = p.pedido_venda " 
		+ " and a.cod_cancelamento = 0 " 
		+ " and (a.qtde_pedida - a.qtde_faturada) > 0 "    
		+ " and p.num_periodo_prod between " + periodoAtualInicial + " and " + periodoAtualFinal    
		+ " group by a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item "    
		+ " UNION " 
		+ " select a.item_nivel99 nivel, a.item_grupo grupo, a.item_sub sub, a.item_item item, sum(a.qtde_pedida) quantidade " 
		+ " from inte_100 i, inte_110 a, pcpc_010 c " 
		+ " where i.tecido_peca = '1' " 
		+ " and i.tipo_registro = 1 " 
		+ " and a.pedido_venda = i.pedido_venda "
		+ " and c.periodo_producao between " + periodoAtualInicial + " and " + periodoAtualFinal      
		+ " group by a.item_nivel99, a.item_grupo, a.item_sub, a.item_item "  
		+ " ) vendas " 
		+ " group by vendas.nivel, vendas.grupo, vendas.sub, vendas.item "
		+ " ) demandas_atual, "			     
		+ " /* DEMANDAS ANALISE */ "
		+ " (select vendas.nivel, vendas.grupo, vendas.sub, vendas.item, nvl(sum(vendas.quantidade),0) quantidade "
		+ " from "
		+ " (select a.cd_it_pe_nivel99 nivel, a.cd_it_pe_grupo grupo, a.cd_it_pe_subgrupo sub, a.cd_it_pe_item item, sum(a.qtde_pedida - a.qtde_faturada) quantidade " 
		+ " from pedi_100 p, pedi_110 a "  
		+ " where p.situacao_venda <> 10 " 
		+ " and p.cod_cancelamento = 0 " 
		+ " and p.tecido_peca = '1' " 
		+ " and a.pedido_venda = p.pedido_venda " 
		+ " and a.cod_cancelamento = 0 " 
		+ "  and (a.qtde_pedida - a.qtde_faturada) > 0 "    
		+ " and p.num_periodo_prod between " + periodoAnaliseInicial + " and " + periodoAnaliseFinal 
		+ " and not p.num_periodo_prod between " + periodoAtualInicial + " and " + periodoAtualFinal 
		+ " group by a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item "
		+ " UNION " 
		+ " select a.item_nivel99 nivel, a.item_grupo grupo, a.item_sub sub, a.item_item item, sum(a.qtde_pedida) quantidade " 
		+ " from inte_100 i, inte_110 a, pcpc_010 c " 
		+ " where i.tecido_peca = '1' " 
		+ " and i.tipo_registro = 1 " 
		+ " and a.pedido_venda = i.pedido_venda "
		+ " and c.periodo_producao between " + periodoAnaliseInicial + " and " + periodoAnaliseFinal  
		+ " and not c.periodo_producao between " + periodoAtualInicial + " and " + periodoAtualFinal  
		+ " group by a.item_nivel99, a.item_grupo, a.item_sub, a.item_item "   
		+ " ) vendas " 
		+ " group by vendas.nivel, vendas.grupo, vendas.sub, vendas.item "
		+ " ) demandas_analise, "			     
		+ " (select a.proconf_nivel99 nivel, a.proconf_grupo grupo, a.proconf_subgrupo sub, a.proconf_item item, nvl(sum(a.qtde_a_produzir_pacote),0) quantidade "  
		+ "	from pcpc_040 a, pcpc_020 b "
		+ " where b.ordem_producao = a.ordem_producao " 
		+ " and b.ultimo_estagio = a.codigo_estagio "
		+ " and b.cod_cancelamento = 0 "
		+ " and b.periodo_producao between " + ConvertePeriodo.parse(periodoAnaliseInicial, 500) + " and " + ConvertePeriodo.parse(periodoAnaliseFinal, 500)
		+ " group by a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item) processos "			   
		+ " where categorias.cod_refere (+) = ordenacao.referencia " 
		+ " and depositos.grupo (+) = ordenacao.referencia "
		+ " and depositos.tamanho (+) = ordenacao.tamanho "
		+ " and depositos.cor (+) = ordenacao.cor "
		+ " and demandas_atual.nivel (+) = '1' " 
		+ " and demandas_atual.grupo (+) = ordenacao.referencia "
		+ " and demandas_atual.sub (+) = ordenacao.tamanho "
		+ " and demandas_atual.item (+) = ordenacao.cor "
		+ " and demandas_analise.nivel (+) = '1' "
		+ " and demandas_analise.grupo (+) = ordenacao.referencia "
		+ " and demandas_analise.sub (+) = ordenacao.tamanho "
		+ " and demandas_analise.item (+) = ordenacao.cor "			  
		+ " and processos.nivel (+) = '1' "
		+ " and processos.grupo (+) = ordenacao.referencia "
		+ " and processos.sub (+) = ordenacao.tamanho "
		+ " and processos.item (+) = ordenacao.cor ";				
					
		//System.out.println(query);
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadeCotasVendas.class));
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
	                                        + " where c.cd_agrupador in (" + colecoes + ")"
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
