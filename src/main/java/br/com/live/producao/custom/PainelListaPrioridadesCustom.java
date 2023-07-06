package br.com.live.producao.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.model.PainelListaPrioridadesCarteiraPedidos;
import br.com.live.producao.model.PainelListaPrioridadesOrdensEmProducao;

@Repository
public class PainelListaPrioridadesCustom {

	private JdbcTemplate jdbcTemplate;

	public PainelListaPrioridadesCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PainelListaPrioridadesCarteiraPedidos> findCarteiraPedidos(Date dtInicio, Date dtFim, String listaPedidos, String listaPedidosOrdens, String listaSituacoes, String listaNumerosControle, int periodoInicial, int periodoFinal, String listaDepositosEstoques, String listaDepositosPedidos, String listaEstagios, String listaColecoes, String listaLinhasProduto, String listaArtigos, String listaReferencias, String listaTamanhos, String listaCores, String listaSegmentos, String listaFamilias, String listaFaccoes, String listaNaturezas) {
		List<PainelListaPrioridadesCarteiraPedidos> resultado;
		
		if (listaPedidos.isEmpty()) listaPedidos = listaPedidosOrdens;
		else {
			if (!listaPedidosOrdens.isEmpty()) listaPedidos +=  "," + listaPedidosOrdens;
		}

		String query = " select rownum id, analise.pedido_venda pedidoVenda, analise.data_entr_venda dataEmbarque, analise.grupo referencia, analise.descricao descReferencia, analise.sub tamanho, analise.item cor, analise.desc_cor descCor, " 
	    + " analise.carteira qtdeCarteira, analise.estoque qtdeEstoque, analise.em_producao qtdeEmProducao, analise.sugerido qtdeSugerida, analise.coletado qtdeColetada" 
	    + " from ( "
	    + " select a.pedido_venda, a.data_entr_venda, b.cd_it_pe_grupo grupo, b.cd_it_pe_subgrupo sub, b.cd_it_pe_item item, f.descr_referencia descricao, w.descricao_15 desc_cor, sum(b.qtde_pedida - b.qtde_faturada) carteira, sum(b.qtde_sugerida) sugerido, sum(b.qtde_afaturar) coletado, "
	    + " nvl((select sum(c.qtde_estoque_atu) " 
	    + " from estq_040 c "
	    + " where c.cditem_nivel99 = '1' "
	    + " and c.cditem_grupo = b.cd_it_pe_grupo "
	    + " and c.cditem_subgrupo = b.cd_it_pe_subgrupo "
	    + " and c.cditem_item = b.cd_it_pe_item ";

		if (!listaDepositosEstoques.isEmpty())
			query += " and c.deposito in (" + listaDepositosEstoques + ")";
		
		query += " ),0) estoque, "	       
		+ " nvl((select sum(t.qtde_areceber) from tmrp_041 t "
		+ " where t.nivel_estrutura = '1' "
		+ " and t.grupo_estrutura = b.cd_it_pe_grupo "
		+ " and t.subgru_estrutura = b.cd_it_pe_subgrupo "
		+ " and t.item_estrutura = b.cd_it_pe_item "
		+ " and t.area_producao = 1 "	               	               
		+ " and t.periodo_producao between " + periodoInicial + " and " + periodoFinal                
		+ " ),0) em_producao "	              
		+ " from pedi_100 a, pedi_110 b, basi_030 f, basi_010 w "		
		+ " where a.situacao_venda <> 10 "
		+ " and a.cod_cancelamento = 0 "
		+ " and a.data_entr_venda between ? and ? ";
		
		if (!listaPedidos.isEmpty())
			query += " and a.pedido_venda in (" + listaPedidos + ")";

		if (!listaSituacoes.isEmpty())
			query += " and a.situacao_venda in (" + listaSituacoes + ") "; 

		if (!listaNumerosControle.isEmpty())
			query += " and a.numero_controle in (" + listaNumerosControle + ")";
		
		query += " and b.pedido_venda = a.pedido_venda "
		+ " and b.cod_cancelamento = 0 ";
		
		if (!listaDepositosPedidos.isEmpty())
			query += " and b.codigo_deposito in (" + listaDepositosPedidos + ")";
		
		query += " and (b.qtde_pedida - b.qtde_faturada) > 0 "
		+ " and f.nivel_estrutura = b.cd_it_pe_nivel99 "
		+ " and f.referencia = b.cd_it_pe_grupo "
		+ " and w.nivel_estrutura = b.cd_it_pe_nivel99 "
		+ " and w.grupo_estrutura = b.cd_it_pe_grupo "
		+ " and w.subgru_estrutura = b.cd_it_pe_subgrupo "
		+ " and w.item_estrutura = b.cd_it_pe_item " ;
	  			  
		if (!listaColecoes.isEmpty())
		    query += " and exists (select 1 " 
			+ " from ORION_VI_ITENS_X_COLECOES v "
			+ " where v.colecao in (" + listaColecoes + ") "
	        + " and v.nivel = '1' " 
	        + " and v.referencia = b.cd_it_pe_grupo "
	        + " and v.tamanho = b.cd_it_pe_subgrupo "
	        + " and v.cor = b.cd_it_pe_item) ";
	  
		if (!listaLinhasProduto.isEmpty())
			query += " and f.linha_produto in (" + listaLinhasProduto + ") "; 
	  
		if (!listaArtigos.isEmpty())
			query += " and f.artigo in (" + listaArtigos + ") "; 
			
		if (!listaReferencias.isEmpty())
			query += " and f.referencia in (" + listaReferencias + ") "; 

		if (!listaTamanhos.isEmpty())
			query += " and b.cd_it_pe_subgrupo in (" + listaTamanhos + ") ";

		if (!listaCores.isEmpty())
			query += " and b.cd_it_pe_item in (" + listaCores + ") ";  
		
		if (!listaSegmentos.isEmpty())
			query += " and exists (select 1 from basi_400 m "
            + " where m.nivel = b.cd_it_pe_nivel99 "
            + " and m.grupo = b.cd_it_pe_grupo "
	        + " and m.tipo_informacao = 10 "
	        + " and m.codigo_informacao in (" + listaSegmentos + "))";

	    if (!listaEstagios.isEmpty())
	        query += " and exists (select 1 from pcpc_040 n "
            + " where n.proconf_nivel99 = '1' "
            + " and n.proconf_grupo = b.cd_it_pe_grupo "
	        + " and n.proconf_subgrupo = b.cd_it_pe_subgrupo "
	        + " and n.proconf_item = b.cd_it_pe_item "
	        + " and n.codigo_estagio in (" + listaEstagios + ") "
	        + " and n.qtde_em_producao_pacote > 0)";
				
	    if (!listaFamilias.isEmpty())
	        query += " and exists (select 1 from pcpc_040 n "
            + " where n.proconf_nivel99 = '1' "
            + " and n.proconf_grupo = b.cd_it_pe_grupo "
	        + " and n.proconf_subgrupo = b.cd_it_pe_subgrupo "
	        + " and n.proconf_item = b.cd_it_pe_item "
	        + " and n.codigo_familia in (" + listaFamilias + ") "
	        + " and n.qtde_em_producao_pacote > 0)";
	    
	    if (!listaFaccoes.isEmpty())
	    	query += " and exists (select 1 from pcpc_040 n, obrf_080 k, supr_010 s "
			+ " where n.numero_ordem > 0 " 
			+ " and n.proconf_nivel99 = '1' "
			+ " and n.proconf_grupo = b.cd_it_pe_grupo "
			+ " and n.proconf_subgrupo = b.cd_it_pe_subgrupo "
			+ " and n.proconf_item = b.cd_it_pe_item "
			+ " and k.numero_ordem = n.numero_ordem "
			+ " and s.fornecedor9 = k.cgcterc_forne9 "
			+ " and s.fornecedor4 = k.cgcterc_forne4 "
			+ " and s.fornecedor2 = k.cgcterc_forne2 "
			+ " and n.qtde_em_producao_pacote > 0 "
			+ " and lpad(s.fornecedor9,8,0) || lpad(s.fornecedor4,4,0) || lpad(s.fornecedor2,2,0) in (" + listaFaccoes + "))";  
	  
	    if (!listaNaturezas.isEmpty())	  
	    	query += " and b.cod_nat_op in (select y.natur_operacao from pedi_080 y " 
            + " where y.cod_natureza || y.divisao_natur in ('5.101')) ";  

	    if (!listaPedidosOrdens.isEmpty())
	    	query += " and exists (select 1 from pcpc_020 pa, pcpc_040 pb "
			+ " where pa.pedido_venda in (" + listaPedidosOrdens + ")"
            + " and pb.ordem_producao = pa.ordem_producao "
            + " and pb.qtde_em_producao_pacote > 0 "
            + " and pb.proconf_nivel99 = '1' "
            + " and pb.proconf_grupo = b.cd_it_pe_grupo "
            + " and pb.proconf_subgrupo = b.cd_it_pe_subgrupo "
            + " and pb.proconf_item = b.cd_it_pe_item) ";
	  
	    query += " group by a.pedido_venda, a.data_entr_venda, b.cd_it_pe_grupo, b.cd_it_pe_subgrupo, b.cd_it_pe_item, f.descr_referencia, w.descricao_15 "
	    + " order by a.pedido_venda, b.cd_it_pe_grupo, b.cd_it_pe_subgrupo, b.cd_it_pe_item, f.descr_referencia, w.descricao_15 "
	    + " ) analise ";
	    
	    try {
	    	resultado = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PainelListaPrioridadesCarteiraPedidos.class), dtInicio, dtFim);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultado = new ArrayList<PainelListaPrioridadesCarteiraPedidos>();
		}
	    
	    return resultado;
	}	
	
	public List<PainelListaPrioridadesOrdensEmProducao> findOrdensEmProducao(String grupo, String sub, String item, int periodoInicial, int periodoFinal, String listaPedidosOrdens, String listaFamilias, String listaFaccoes, String listaEstagios) {
		List<PainelListaPrioridadesOrdensEmProducao> resultado;		
		
		String query = " select rownum id, a.proconf_grupo referencia, " 
        + " c.descr_referencia descReferencia, "
        + " a.proconf_subgrupo tamanho, "
        + " a.proconf_item cor, "
        + " e.descricao_15 descCor, "
        + " a.ordem_producao ordemProducao, "
        + " a.ordem_confeccao ordemConfeccao, "
        + " b.periodo_producao periodo, "
        + " d.sequencia_calculo_fila seqFilaEstagio, "
        + " a.sequencia_estagio seqEstagio, "        
        + " a.seq_operacao seqOperacao, "
        + " a.codigo_estagio codEstagio, "        
        + " d.descricao descEstagio, "
        + " b.pedido_venda pedidoVenda, "
        + " a.qtde_em_producao_pacote qtdeEmProducaoPacote, "
        + " 0 qtdeNecessidade, "               
		+ " decode(a.qtde_conserto,0,0,1) emConserto, "        
        + " a.codigo_familia codFamilia, "
        + " nvl((select sum(pc.qtde_em_producao_pacote) " 
        + " from pcpc_040 pc "
        + " where pc.periodo_producao = a.periodo_producao "
        + " and pc.ordem_producao = a.ordem_producao "
        + " and pc.codigo_estagio = a.codigo_estagio),0) qtdeEmProducaoOrdem, "       
        + " nvl((select s.nome_fornecedor from obrf_080 k, supr_010 s "
        + " where k.numero_ordem = a.numero_ordem "
        + " and s.fornecedor9 = k.cgcterc_forne9 "
        + " and s.fornecedor4 = k.cgcterc_forne4 "
        + " and s.fornecedor2 = k.cgcterc_forne2),'') descFaccao, "         
		+ " (select nvl(max(f.data_producao),trunc(sysdate)) "
		+ " from pcpc_045 f "
		+ " where f.pcpc040_perconf = a.periodo_producao "
		+ " and f.pcpc040_ordconf = a.ordem_confeccao "
		+ " and f.pcpc040_estconf =  (select max(g.codigo_estagio) "
		+ " from pcpc_040 g "
		+ " where g.periodo_producao = a.periodo_producao "
		+ " and g.ordem_confeccao = a.ordem_confeccao "
		+ " and g.seq_operacao = (select max(h.seq_operacao) "
		+ " from pcpc_040 h "
		+ " where h.periodo_producao = a.periodo_producao "
		+ " and h.ordem_confeccao = a.ordem_confeccao "
		+ " and h.seq_operacao < a.seq_operacao))) dataEntradaEstagio "         
        + " from pcpc_040 a, pcpc_020 b, basi_030 c, mqop_005 d, basi_010 e "
        + " where a.proconf_nivel99 = '1' "
        + " and a.proconf_grupo = '" + grupo + "' "
        + " and a.proconf_subgrupo in ('" + sub + "') "
        + " and a.proconf_item = '" + item + "' ";
        
        if (!listaEstagios.isEmpty())
        	query += " and a.codigo_estagio in (" + listaEstagios + ") ";
        	        
	    query += " and a.qtde_em_producao_pacote > 0 "
	    + " and b.ordem_producao = a.ordem_producao "
	    + " and b.cod_cancelamento = 0 "
	    + " and c.nivel_estrutura = '1' "
	    + " and c.referencia = a.proconf_grupo "
	    + " and d.codigo_estagio = a.codigo_estagio "	    
		+ " and e.nivel_estrutura = '1' "
		+ " and e.grupo_estrutura = a.proconf_grupo " 
	    + " and e.subgru_estrutura = a.proconf_subgrupo "
	    + " and e.item_estrutura = a.proconf_item "
	    + " and b.periodo_producao between " + periodoInicial + " and " + periodoFinal;  
	  
	    if (!listaPedidosOrdens.isEmpty())
	    	query += " and b.pedido_venda in (" + listaPedidosOrdens + ")";
	      
	    if (!listaFamilias.isEmpty())
	    	query += " and a.codigo_familia in (" + listaFamilias + ")";
	  
	    if (!listaFaccoes.isEmpty())
	    	query += " and exists (select 1 from obrf_080 k, supr_010 s "
            + " where k.numero_ordem = a.numero_ordem "
            + " and s.fornecedor9 = k.cgcterc_forne9 "
            + " and s.fornecedor4 = k.cgcterc_forne4 "
            + " and s.fornecedor2 = k.cgcterc_forne2 "
            + " and lpad(s.fornecedor9,8,0) || lpad(s.fornecedor4,4,0) || lpad(s.fornecedor2,2,0) in (" + listaFaccoes + "))";
	    
	    query += " order by a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, a.sequencia_estagio desc, a.seq_operacao, a.codigo_estagio, b.periodo_producao, a.ordem_producao, a.ordem_confeccao ";
	
	    try {
	    	resultado = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PainelListaPrioridadesOrdensEmProducao.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultado = new ArrayList<PainelListaPrioridadesOrdensEmProducao>();
		}
	    return resultado;
	}	
}