package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.com.live.model.CapacidadeCotasVendas;
import br.com.live.model.CapacidadeCotasVendasTipoCliente;
import br.com.live.util.ConvertePeriodo;

@Repository
public class CapacidadeCotasVendasCustom {

	private JdbcTemplate jdbcTemplate;

	public CapacidadeCotasVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CapacidadeCotasVendasTipoCliente> findDadosPorTipoCliente(int periodoAnaliseInicial, int periodoAnaliseFinal, int periodoProgInicial, int periodoProgFinal, String colecoes) {
		String query = "select tipo.tipo_cliente tipoCliente, pedi_085.descr_tipo_clien descricaoTipo,"
			   + " sum(tipo.quantidade) qtdePecas, "
		       + " round(sum(tipo.valor_bruto),2) valorBruto, round(sum(tipo.valor_liq_itens),2) valorLiqItens, round(sum(tipo.valor_liq_total),2) valorLiqTotal, "
		       + " round(sum(tipo.tempo),2) tempo "
			+ " from ( "
			+ " select pedi.tipo_cliente, pedi.pedido_venda, sum(pedi.quantidade) quantidade, "
			       + " sum(pedi.valor_bruto) valor_bruto, sum(pedi.valor_liquido) valor_liq_itens, "
			      + " (sum(pedi.valor_liquido) - ((sum(pedi.valor_liquido) * pedi.perc_desc_capa) / 100)) valor_liq_total, "
			      + " sum(pedi.quantidade * pedi.tempo) tempo "
			+ " from ( "
			+ " select m.tipo_cliente, p.pedido_venda, LIVE_FN_CALC_PERC_DESCONTO(min(p.desconto1), min(p.desconto2), min(p.desconto3)) perc_desc_capa, "
			       + " a.cd_it_pe_nivel99 nivel, a.cd_it_pe_grupo grupo, a.cd_it_pe_subgrupo sub, a.cd_it_pe_item item, sum(a.qtde_pedida - a.qtde_faturada) quantidade, "
			       + " sum((a.qtde_pedida - a.qtde_faturada) * LIVE_FN_CONVERTE_MOEDA(p.codigo_moeda, a.valor_unitario)) valor_bruto, "
			       + " sum(((a.qtde_pedida - a.qtde_faturada) * LIVE_FN_CONVERTE_MOEDA(p.codigo_moeda, a.valor_unitario)) - (((a.qtde_pedida - a.qtde_faturada) * LIVE_FN_CONVERTE_MOEDA(p.codigo_moeda, a.valor_unitario)) * a.percentual_desc / 100)) valor_liquido, "
			       + " nvl((select max(o.tempo) from orion_vi_itens_x_tempo_estagio o "
			         + " where o.nivel = a.cd_it_pe_nivel99 "
			           + " and o.grupo = a.cd_it_pe_grupo "
			           + " and o.estagio = 20),0) tempo "
			 + " from pedi_100 p, pedi_010 m, pedi_110 a "
			 + " where p.situacao_venda <> 10 "
			 + " and p.cod_cancelamento = 0 "
			 + " and p.tecido_peca = '1' "
			 + " and m.cgc_9 = p.cli_ped_cgc_cli9 "
			 + " and m.cgc_4 = p.cli_ped_cgc_cli4 "
			 + " and m.cgc_2 = p.cli_ped_cgc_cli2 "
			 + " and a.pedido_venda = p.pedido_venda "
			 + " and a.cod_cancelamento = 0 "
			 + " and (a.qtde_pedida - a.qtde_faturada) > 0 ";
			 
		if ((periodoProgInicial > 0) && (periodoProgFinal > 0)) { 
			 query+= " and p.num_periodo_prod not between " + periodoAnaliseInicial + " and " + periodoAnaliseFinal
			       + " and p.num_periodo_prod between " + periodoProgInicial + " and " + periodoProgFinal;
		} else {
			 query+= " and p.num_periodo_prod between " + periodoAnaliseInicial + " and " + periodoAnaliseFinal;
		}
			 
		query+= " group by m.tipo_cliente, p.pedido_venda, a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item "
			+ " ) pedi "
			+ " where exists (select 1 from orion_vi_itens_x_colecoes oc "
			               + " where oc.nivel = pedi.nivel "
			                 + " and oc.referencia = pedi.grupo "
			                 + " and oc.tamanho = pedi.sub "
			                 + " and oc.cor = pedi.item	"
			                 + " and oc.colecao in (" + colecoes + ")) " 
			+ " group by pedi.tipo_cliente, pedi.pedido_venda, pedi.perc_desc_capa "
			+ " ) tipo, pedi_085 "
			+ " where pedi_085.tipo_cliente = tipo.tipo_cliente "
			+ " group by tipo.tipo_cliente, pedi_085.descr_tipo_clien "
			+ " order by tipo.tipo_cliente ";
			
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadeCotasVendasTipoCliente.class));
	}
	
	public List<CapacidadeCotasVendasTipoCliente> findDadosPorTipoCliente(int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes) {				
		return findDadosPorTipoCliente(periodoAnaliseInicial, periodoAnaliseFinal, 0, 0, colecoes);
	}	
	
	public List<CapacidadeCotasVendas> findItensByFiltros(int periodoAnaliseInicial, int periodoAnaliseFinal, int periodoProgInicial, int periodoProgFinal, String colecoes, String depositos) {		
		String query = " select ordenacao.referencia, ordenacao.tamanho, ordenacao.cor, ordenacao.descricao, nvl(categorias.des_categoria,' ') categoria, " 
		+ " nvl(depositos.quantidade,0) qtdeEstoque, "
		 + " nvl(demandas_atual.quantidade,0) qtdeDemandaAnalise, "
		 + " nvl(processos_analise.quantidade,0) qtdeProcessoAnalise, "
		 + " nvl(demandas_analise.quantidade,0) qtdeDemanda, "
		 + " nvl(processos_prog.quantidade,0) qtdeProcesso, "
		 + " (nvl(depositos.quantidade,0) + nvl(processos_prog.quantidade,0) + nvl(processos_analise.quantidade,0) - nvl(demandas_analise.quantidade,0) - nvl(demandas_atual.quantidade,0)) qtdeSaldo, "
		 + " nvl((select max(o.tempo) from orion_vi_itens_x_tempo_estagio o "
		       + " where o.nivel = '1' "
		         + " and o.grupo = ordenacao.referencia "
		         + " and o.estagio = 20),0) tempoUnitario, "
		 + " (nvl(demandas_atual.quantidade,0) * "
		 + " nvl((select max(o.tempo) from orion_vi_itens_x_tempo_estagio o "
		       + " where o.nivel = '1' "
		         + " and o.grupo = ordenacao.referencia "
		         + " and o.estagio = 20),0)) qtdeMinutosDemandaAnalise " 
		 + " from ( "
		 + " select capac_cotas.referencia, capac_cotas.tamanho, capac_cotas.cor, capac_cotas.descricao " 
		 + " from ( select o.nivel, o.referencia, o.tamanho, o.cor, o.descricao from orion_vi_itens_x_colecoes o "
		         + " where o.colecao in (" + colecoes + ") "
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
		 + " and a.deposito in (" + depositos + ") "
		 + " group by a.cditem_grupo, a.cditem_subgrupo, a.cditem_item) depositos, "                           
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
		 + " and (a.qtde_pedida - a.qtde_faturada) > 0 "     
		 + " and p.num_periodo_prod between " + periodoAnaliseInicial + " and " + periodoAnaliseFinal    
		 + " group by a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item "    
		 + " ) vendas " 
		 + " group by vendas.nivel, vendas.grupo, vendas.sub, vendas.item " 
		 + " ) demandas_atual, "           
		 + " /* DEMANDAS PROGRAMADO */ " 
		 + " (select vendas.nivel, vendas.grupo, vendas.sub, vendas.item, nvl(sum(vendas.quantidade),0) quantidade " 
		 + " from "
		 + " (select a.cd_it_pe_nivel99 nivel, a.cd_it_pe_grupo grupo, a.cd_it_pe_subgrupo sub, a.cd_it_pe_item item, sum(a.qtde_pedida - a.qtde_faturada) quantidade "  
		 + " from pedi_100 p, pedi_110 a "   
		 + " where p.situacao_venda <> 10 " 
		 + " and p.cod_cancelamento = 0 " 
		 + " and p.tecido_peca = '1' "  
		 + " and a.pedido_venda = p.pedido_venda "  
		 + " and a.cod_cancelamento = 0 " 
		 + " and (a.qtde_pedida - a.qtde_faturada) > 0 "     
		 + " and p.num_periodo_prod between " + periodoProgInicial + " and " + periodoProgFinal
		 + " and not p.num_periodo_prod between " + periodoAnaliseInicial + " and " + periodoAnaliseFinal
		 + " group by a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item "
		 + " ) vendas "
		 + " group by vendas.nivel, vendas.grupo, vendas.sub, vendas.item " 
		 + " ) demandas_analise, "		 		 
		 + " (select a.proconf_nivel99 nivel, a.proconf_grupo grupo, a.proconf_subgrupo sub, a.proconf_item item, nvl(sum(a.qtde_a_produzir_pacote),0) quantidade "   
		 + " from pcpc_040 a, pcpc_020 b "
		 + " where b.ordem_producao = a.ordem_producao " 
		 + " and b.ultimo_estagio = a.codigo_estagio "
		 + " and b.cod_cancelamento = 0 "
    	 + " and b.periodo_producao between " + ConvertePeriodo.parse(periodoProgInicial, 500) + " and " + ConvertePeriodo.parse(periodoProgFinal, 500)
    	 + " and b.periodo_producao not between " + ConvertePeriodo.parse(periodoAnaliseInicial, 500) + " and " + ConvertePeriodo.parse(periodoAnaliseFinal, 500)
		 + " group by a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item) processos_prog, "
		 + " (select a.proconf_nivel99 nivel, a.proconf_grupo grupo, a.proconf_subgrupo sub, a.proconf_item item, nvl(sum(a.qtde_a_produzir_pacote),0) quantidade "   
		 + " from pcpc_040 a, pcpc_020 b "
		 + " where b.ordem_producao = a.ordem_producao " 
		 + " and b.ultimo_estagio = a.codigo_estagio "
		 + " and b.cod_cancelamento = 0 "
    	 + " and b.periodo_producao between " + ConvertePeriodo.parse(periodoAnaliseInicial, 500) + " and " + ConvertePeriodo.parse(periodoAnaliseFinal, 500)
		 + " and b.periodo_producao not between " + ConvertePeriodo.parse(periodoProgInicial, 500) + " and " + ConvertePeriodo.parse(periodoProgFinal, 500)
		 + " group by a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item) processos_analise "    		 		 
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
		 + " and processos_prog.nivel (+) = '1' "
		 + " and processos_prog.grupo (+) = ordenacao.referencia " 
		 + " and processos_prog.sub (+) = ordenacao.tamanho "
		 + " and processos_prog.item (+) = ordenacao.cor "
		 + " and processos_analise.nivel (+) = '1' "
		 + " and processos_analise.grupo (+) = ordenacao.referencia " 
		 + " and processos_analise.sub (+) = ordenacao.tamanho "
		 + " and processos_analise.item (+) = ordenacao.cor " 		 
		 // TODO - TESTE
		 + " and ordenacao.referencia = '42571'"
		 ;        
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CapacidadeCotasVendas.class));
	}	
}
