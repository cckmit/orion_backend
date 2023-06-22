package br.com.live.comercial.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.model.ResumoOcupacaoCarteiraPorPedido;
import br.com.live.producao.custom.OrdemProducaoCustom;
import br.com.live.util.FormataData;

@Repository
public class OcupacaoCarteiraCustom {

	public final static String OCUPACAO_EM_VALORES = "VALORES";
	public final static String OCUPACAO_EM_PECAS = "PECAS";
	public final static String OCUPACAO_EM_MINUTOS = "MINUTOS";
	public final static String NATUREZAS_FRANCHISING = "421,422,423,424";	
	public final static String MODALIDADE_ATACADO = "ATACADO";
	public final static String MODALIDADE_VAREJO = "VAREJO";
	public final static String META_ORCADA = "ORCADO";
	public final static String META_REALINHADA = "REALINHADO";
	public final static int CLASSIFICACAO_DISPONIBILIDADE = 4;
	
	private JdbcTemplate jdbcTemplate;

	public OcupacaoCarteiraCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ResumoOcupacaoCarteiraPorPedido> consultarCarteiraPorValor(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta) {
		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		List<ResumoOcupacaoCarteiraPorPedido> listResumoOcupacaoCarteiraPorCanalVenda;
		
		String query = "select canais_valores.canal, canais_valores.pedido_venda pedidoVenda, sum(canais_valores.valor_liquido - ((canais_valores.valor_liquido * canais_valores.desconto_capa) / 100)) valorReal "
		+ " from ( "
		+ " select c.live_agrup_tipo_cliente canal, a.pedido_venda, LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, sum(pedi110.valor - ((pedi110.valor * pedi110.desconto) / 100))) valor_liquido, "
		+ "        live_fn_calc_perc_desconto(a.desconto1, a.desconto2, a.desconto3) desconto_capa "
		+ " from pedi_100 a, pedi_010 b, pedi_085 c, "
		+ " (select pedi_110.pedido_venda, ((pedi_110.qtde_pedida * pedi_110.valor_unitario)) valor, pedi_110.percentual_desc desconto, 'NORMAL' tipo " 
		+ " from pedi_110, pedi_080 "
		+ " where pedi_110.cod_cancelamento = 0 "
		+ " and pedi_110.cod_nat_op not in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ")"  
		+ " and pedi_080.natur_operacao = pedi_110.cod_nat_op "
		+ " and pedi_080.estado_natoper = pedi_110.est_nat_op "
		+ " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1) "
		+ " union all "
		+ " select pedi_110.pedido_venda, ((pedi_110.qtde_pedida * pedi_110.valor_unitario) * 2) valor, pedi_110.percentual_desc desconto, 'FRANCHISING' tipo "
		+ " from pedi_110, pedi_080 "   
		+ " where pedi_110.cod_cancelamento = 0 "
		+ " and pedi_110.cod_nat_op in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ")"
	    + " and pedi_080.natur_operacao = pedi_110.cod_nat_op "
	    + " and pedi_080.estado_natoper = pedi_110.est_nat_op "
	    + " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1)) pedi110 "
	    + " where a.data_entr_venda between ? and ? "
	    + " and a.cod_cancelamento = 0 ";
		
		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 

		query += " and a.tipo_pedido in (" + tipoPedido + ")"
		+ " and b.cgc_9 = a.cli_ped_cgc_cli9 "
		+ " and b.cgc_4 = a.cli_ped_cgc_cli4 "
		+ " and b.cgc_2 = a.cli_ped_cgc_cli2 "
		+ " and c.tipo_cliente = b.tipo_cliente "
		+ " and pedi110.pedido_venda = a.pedido_venda "  
		+ " and upper(c.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + OcupacaoCarteiraCustom.MODALIDADE_ATACADO + "') " // CONSIDERAR APENAS VALORES DE ATACADO 
		+ " group by c.live_agrup_tipo_cliente, a.pedido_venda, a.desconto1, a.desconto2, a.desconto3, a.codigo_moeda "
		+ " ) canais_valores "
		+ " group by canais_valores.canal, canais_valores.pedido_venda ";

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorPedido.class), dataInicio, dataFim);				
		} catch (Exception e) {				
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorPedido>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;		
	}
	
	public List<ResumoOcupacaoCarteiraPorPedido> consultarCarteiraConfirmarPorValor(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta) {
		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		List<ResumoOcupacaoCarteiraPorPedido> listResumoOcupacaoCarteiraPorCanalVenda;
		
		String query = " select canais_valores_integ.canal, canais_valores_integ.pedido_venda pedidoVenda, sum(canais_valores_integ.valor_liquido - ((canais_valores_integ.valor_liquido * canais_valores_integ.desconto_capa) / 100)) valorConfirmar "
		+ " from ( "
		+ " select c.live_agrup_tipo_cliente canal, a.pedido_venda, LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, sum(inte110.valor - ((inte110.valor * inte110.desconto) / 100))) valor_liquido, "
		+ " (live_fn_calc_perc_desconto(a.desconto1, a.desconto2, a.desconto3)/100) desconto_capa "
		+ " from inte_100 a, pedi_010 b, pedi_085 c, "
		+ " (select inte_110.pedido_venda, (((inte_110.qtde_pedida / 100) * (inte_110.valor_unitario / 100))) valor, (inte_110.percentual_desc / 100) desconto, 'NORMAL' tipo " 
		+ " from inte_110, pedi_080 "
		+ " where inte_110.cod_nat_op not in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ") " 
	    + " and pedi_080.natur_operacao = inte_110.cod_nat_op "
	    + " and pedi_080.estado_natoper = inte_110.est_nat_op "
	    + " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1) "
	    + " union all "
	    + " select inte_110.pedido_venda, (((inte_110.qtde_pedida / 100) * (inte_110.valor_unitario / 100)) * 2) valor, (inte_110.percentual_desc / 100) desconto, 'FRANCHISING' tipo " 
	    + " from inte_110, pedi_080 "
	    + " where inte_110.cod_nat_op in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ")" 
		+ " and pedi_080.natur_operacao = inte_110.cod_nat_op "
		+ " and pedi_080.estado_natoper = inte_110.est_nat_op "
		+ " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1)) inte110 "		    
		+ " where a.data_entrega between ? and ? ";

		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 
		
		query += " and a.tipo_pedido in (" + tipoPedido + ")" 
		+ " and b.cgc_9 = a.cliente9 " 
		+ " and b.cgc_4 = a.cliente4 " 
		+ " and b.cgc_2 = a.cliente2 " 
		+ " and c.tipo_cliente = b.tipo_cliente " 
		+ " and inte110.pedido_venda = a.pedido_venda "
		+ " and upper(c.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + OcupacaoCarteiraCustom.MODALIDADE_ATACADO + "') " // CONSIDERAR APENAS VALORES DE ATACADO
		+ " group by c.live_agrup_tipo_cliente, a.pedido_venda, a.desconto1, a.desconto2, a.desconto3, a.codigo_moeda "
		+ " ) canais_valores_integ group by canais_valores_integ.canal, canais_valores_integ.pedido_venda ";

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorPedido.class), dataInicio, dataFim);	
		} catch (Exception e) {
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorPedido>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;						
	}	
	
	public List<ResumoOcupacaoCarteiraPorPedido> consultarCarteiraPorQuantidade(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta, String tipoModalidade) {		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		List<ResumoOcupacaoCarteiraPorPedido> listResumoOcupacaoCarteiraPorCanalVenda;
				
		String query = " select d.live_agrup_tipo_cliente canal, a.pedido_venda pedidoVenda, sum(b.qtde_pedida) valorReal "
		+ " from pedi_100 a, pedi_110 b, pedi_010 c, pedi_085 d "
		+ " where a.data_entr_venda between ? and ? "
		+ " and a.cod_cancelamento = 0 "
		+ " and b.pedido_venda = a.pedido_venda "
		+ " and b.cod_cancelamento = 0 ";
		
		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 

		query += "and a.tipo_pedido in (" + tipoPedido + ")"
		+ " and c.cgc_9 = a.cli_ped_cgc_cli9 "
		+ " and c.cgc_4 = a.cli_ped_cgc_cli4 "
		+ " and c.cgc_2 = a.cli_ped_cgc_cli2 "
		+ " and d.tipo_cliente = c.tipo_cliente "
		+ " and upper(d.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + tipoModalidade + "') "  		
		+ " group by d.live_agrup_tipo_cliente, a.pedido_venda ";

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorPedido.class), dataInicio, dataFim);				
		} catch (Exception e) {			
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorPedido>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;		
	}

	public List<ResumoOcupacaoCarteiraPorPedido> consultarCarteiraConfirmarPorQuantidade(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta, String tipoModalidade) {
		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
				
		List<ResumoOcupacaoCarteiraPorPedido> listResumoOcupacaoCarteiraPorCanalVenda;
		
		String query = " select d.live_agrup_tipo_cliente canal, a.pedido_venda pedidoVenda, sum(b.qtde_pedida) valorConfirmar "
		+ " from inte_100 a, inte_110 b, pedi_010 c, pedi_085 d "
		+ " where a.data_entrega between ? and ? "
		+ " and b.pedido_venda = a.pedido_venda ";
		
		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 

		query += "and a.tipo_pedido in (" + tipoPedido + ")"
		+ " and c.cgc_9 = a.cliente9 "
		+ " and c.cgc_4 = a.cliente4 "
		+ " and c.cgc_2 = a.cliente2 "
		+ " and d.tipo_cliente = c.tipo_cliente "
		+ " and upper(d.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + tipoModalidade + "') "  		
		+ " group by d.live_agrup_tipo_cliente, a.pedido_venda ";

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorPedido.class), dataInicio, dataFim);				
		} catch (Exception e) {			
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorPedido>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;		
	}

	public List<ResumoOcupacaoCarteiraPorPedido> consultarCarteiraPorMinutos(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta, String tipoModalidade) {		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		List<ResumoOcupacaoCarteiraPorPedido> listResumoOcupacaoCarteiraPorCanalVenda;
				
		String query = " select d.live_agrup_tipo_cliente canal, a.pedido_venda pedidoVenda, sum(b.qtde_tempo_producao) valorReal " 
		+ " from pedi_100 a, pedi_010 c, pedi_085 d, " 
		+ " (select z.pedido_venda, z.seq_item_pedido, z.cd_it_pe_grupo, z.cd_it_pe_subgrupo, z.cd_it_pe_item, z.qtde_pedida, nvl(max(w.tempo),0) tempo, (z.qtde_pedida * nvl(max(w.tempo),0)) qtde_tempo_producao "  
		+ " from pedi_110 z, orion_vi_itens_x_tempo_estagio w "
		+ " where z.cod_cancelamento = 0 "
		+ " and w.estagio (+) = " + OrdemProducaoCustom.ESTAGIO_COSTURA
		+ " and w.nivel   (+) = z.cd_it_pe_nivel99 "
		+ " and w.grupo   (+) = z.cd_it_pe_grupo "
		+ " group by z.pedido_venda, z.seq_item_pedido, z.cd_it_pe_grupo, z.cd_it_pe_subgrupo, z.cd_it_pe_item, z.qtde_pedida) b "
		+ " where a.data_entr_venda between ? and ? " 
		+ " and a.cod_cancelamento = 0 " 
		+ " and b.pedido_venda = a.pedido_venda "; 

		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 

		query += " and a.tipo_pedido in (" + tipoPedido + ")"
		+ " and c.cgc_9 = a.cli_ped_cgc_cli9 "
		+ " and c.cgc_4 = a.cli_ped_cgc_cli4 "
		+ " and c.cgc_2 = a.cli_ped_cgc_cli2 "
		+ " and d.tipo_cliente = c.tipo_cliente "
		+ " and upper(d.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + tipoModalidade + "') "  		
		+ " group by d.live_agrup_tipo_cliente, a.pedido_venda ";                                               

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorPedido.class), dataInicio, dataFim);				
		} catch (Exception e) {			
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorPedido>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;				
	}
	
	public List<ResumoOcupacaoCarteiraPorPedido> consultarCarteiraConfirmarPorMinutos(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta, String tipoModalidade ) {		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
				
		List<ResumoOcupacaoCarteiraPorPedido> listResumoOcupacaoCarteiraPorCanalVenda;
				
		String query = "select d.live_agrup_tipo_cliente canal, a.pedido_venda pedidoVenda, sum(b.qtde_tempo_producao) valorConfirmar "
		+ " from inte_100 a, pedi_010 c, pedi_085 d, "		  
		+ " (select z.pedido_venda, z.seq_item_pedido, z.item_grupo, z.item_sub, z.item_item, z.qtde_pedida, nvl(max(w.tempo),0) tempo, (z.qtde_pedida * nvl(max(w.tempo),0)) qtde_tempo_producao "  
		+ " from inte_110 z, orion_vi_itens_x_tempo_estagio w "
		+ " where w.estagio (+) = " + OrdemProducaoCustom.ESTAGIO_COSTURA
		+ " and w.nivel   (+) = z.item_nivel99 "
		+ " and w.grupo   (+) = z.item_grupo "
		+ " group by z.pedido_venda, z.seq_item_pedido, z.item_grupo, z.item_sub, z.item_item, z.qtde_pedida) b "
		+ " where a.data_entrega between ? and ? " 
		+ " and b.pedido_venda = a.pedido_venda "; 
		    
		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 

		query += " and a.tipo_pedido in (" + tipoPedido + ")"
		+ " and c.cgc_9 = a.cliente9 "
		+ " and c.cgc_4 = a.cliente4 "
		+ " and c.cgc_2 = a.cliente2 "
		+ " and d.tipo_cliente = c.tipo_cliente "
		+ " and upper(d.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + tipoModalidade + "') "  		
		+ " group by d.live_agrup_tipo_cliente, a.pedido_venda ";                                               

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorPedido.class), dataInicio, dataFim);				
		} catch (Exception e) {			
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorPedido>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;		
	}
}
