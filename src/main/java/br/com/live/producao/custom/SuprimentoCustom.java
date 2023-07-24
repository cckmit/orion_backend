package br.com.live.producao.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.model.CentroCusto;
import br.com.live.producao.model.ConsultaPreRequisicaoAlmoxItem;
import br.com.live.producao.model.DivisaoProducao;
import br.com.live.producao.model.RequisicaoAlmoxarifado;
import br.com.live.producao.model.RequisicaoAlmoxarifadoItem;
import br.com.live.util.ConteudoChaveAlfaNum;

@Repository
public class SuprimentoCustom {
	
	public static final int PRE_REQUISICAO = 1;
	public static final int REQUISICAO = 2;		
	private JdbcTemplate jdbcTemplate;

	public SuprimentoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	// Regra temporária para o comercial
	public int getDepositoPadraoReqComercial(int empresa) {
	    if (empresa == 1) return 190;
	    else if (empresa == 100) return 491;
	    else if (empresa == 500) return 599;        
	    return 0;
	}
	
	public List<CentroCusto> findCentroCusto() {
				
		String query = " select " + CentroCusto.CAMPOS_BASE_DADOS + " from basi_185 "
		+ " where descricao not like '%(IN)%' "
		+ " and centro_custo > 0 "
		+ " and situacao = 0 "
		+ " order by centro_custo ";
						
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CentroCusto.class));		
	}
		
	public CentroCusto findCentroCustoByCodigo(int centroCusto) {
		
		String query = " select " + CentroCusto.CAMPOS_BASE_DADOS + " from basi_185 "
		+ " where centro_custo = ? " ;
						
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(CentroCusto.class), centroCusto);		
	}
		
	public List<DivisaoProducao> findDivisaoProducao() {
		
		String query = " select " + DivisaoProducao.CAMPOS_BASE_DADOS + " from basi_180 "
		+ " where divisao_producao > 0 "
		+ " order by divisao_producao ";
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DivisaoProducao.class));
	}
	
	public int findTransacaoAlmoxarifado(int empresa) {
	
		String query = " select fatu_500.tran_est_almoxar from fatu_500 where fatu_500.codigo_empresa = ?";
		return jdbcTemplate.queryForObject(query, Integer.class, empresa);				
	}
		
	public boolean isTemPermissaoRequisitarParaCentroCusto(int centroCusto, String usuarioSystextil) {
		
		int encontrou = 0;
		
		String query = " select 1 from supr_500 "
		+ " where (supr_500.centro_custo = ? or supr_500.centro_custo = 0) "
		+ " and supr_500.usuario = ? "
		+ " and supr_500.requisitar = 1 ";

		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class, centroCusto, usuarioSystextil);
		} catch (Exception e) {
			encontrou = 0;
		}
			
		return (encontrou == 1);	
	}
	
	public List<ConteudoChaveAlfaNum> findRequisicoesAlmoxByLeitorRequisicao(Integer leitorRequisicao) {

		List<ConteudoChaveAlfaNum> requisicoes = new ArrayList<>();
		
		String query = " select requisicoes.num_requisicao "
		+ " from (select a.num_requisicao from supr_510 a "
		+ " order by a.num_requisicao desc) requisicoes "
		+ " where requisicoes.num_requisicao like '%" + leitorRequisicao + "%'" 
		+ " and rownum <= 500" ;
		      
		List<Integer> listaRequisicoes = jdbcTemplate.queryForList(query, Integer.class);
		
		for (Integer requisicao : listaRequisicoes) {
			ConteudoChaveAlfaNum conteudo = new ConteudoChaveAlfaNum();
			conteudo.value = requisicao.toString();
			conteudo.label = requisicao.toString();	
			requisicoes.add(conteudo);
		}
		
		return requisicoes;
	}
	
	public List<ConsultaPreRequisicaoAlmoxItem> findItensPreRequisicaoByIdRequisicao(long idPreRequisicao) {
		
		String query = " select a.id, a.sequencia, a.nivel, a.grupo, a.sub, a.item, b.narrativa, a.cod_deposito deposito, d.descricao descDeposito, a.quantidade, c.unidade_medida unidade " 
		+ " from orion_sup_002 a, basi_010 b, basi_030 c, basi_205 d "
		+ " where b.nivel_estrutura = a.nivel "
		+ " and b.grupo_estrutura = a.grupo "
		+ " and b.subgru_estrutura = a.sub "
		+ " and b.item_estrutura = a.item "		
		+ " and c.nivel_estrutura = a.nivel "
		+ " and c.referencia = a.grupo "				
		+ " and d.codigo_deposito = a.cod_deposito "
		+ " and a.id_pre_requisicao = " + idPreRequisicao
		+ " order by a.sequencia ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPreRequisicaoAlmoxItem.class));
	}

	public List<RequisicaoAlmoxarifadoItem> findItensRequisicaoByIdRequisicao(long idRequisicao) {
		
		String query = " select a.num_requisicao || '-' || a.sequencia  id, a.sequencia, a.nivel, a.grupo, a.subgrupo sub, a.item, b.narrativa, a.deposito deposito, d.descricao descDeposito, a.qtde_requisitada quantidade, c.unidade_medida unidade "
		+ " from supr_520 a, basi_010 b, basi_030 c, basi_205 d "
		+ " where b.nivel_estrutura = a.nivel "
		+ " and b.grupo_estrutura = a.grupo "
		+ " and b.subgru_estrutura = a.subgrupo "
		+ " and b.item_estrutura = a.item "
		+ " and c.nivel_estrutura = a.nivel "
		+ " and c.referencia = a.grupo "				
		+ " and d.codigo_deposito = a.deposito "
		+ " and a.num_requisicao = " + idRequisicao 
		+ " order by a.sequencia ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RequisicaoAlmoxarifadoItem.class));
	}
	
	public RequisicaoAlmoxarifado findDadosRequisicaoByOrigem(int origem, long id) {				
		String query = "";
		RequisicaoAlmoxarifado requisicao = null;
		List<RequisicaoAlmoxarifadoItem> itens = new ArrayList<>();
		List<?> listaObj = null;		
		
		if (origem == PRE_REQUISICAO) {			
			query = " select a.cod_empresa empresa, b.nome_empresa descEmpresa, a.divisao_producao divisaoProducao, c.descricao descDivisaoProducao, a.centro_custo centroCusto, d.descricao descCentroCusto, '' observacao "
			+ " from orion_sup_001 a, fatu_500 b, basi_180 c, basi_185 d "
			+ " where b.codigo_empresa = a.cod_empresa "
			+ " and c.divisao_producao = a.divisao_producao "
			+ " and d.centro_custo = a.centro_custo "
			+ " and a.id = " + id ;		
			
			listaObj = findItensPreRequisicaoByIdRequisicao(id);			
		} else {			
			query = " select a.codigo_empresa empresa, b.nome_empresa descEmpresa, a.divisao_producao divisaoProducao, c.descricao descDivisaoProducao, a.centro_custo centroCusto, d.descricao descCentroCusto, a.observacao "
			+ " from supr_510 a, fatu_500 b, basi_180 c, basi_185 d "
			+ " where b.codigo_empresa = a.codigo_empresa "
			+ " and c.divisao_producao = a.divisao_producao "
			+ " and d.centro_custo = a.centro_custo "
			+ " and a.num_requisicao = " + id;	
			
			listaObj = findItensRequisicaoByIdRequisicao(id);
		}
	
		for (Object obj : listaObj) {
			RequisicaoAlmoxarifadoItem item = (RequisicaoAlmoxarifadoItem) obj;
			itens.add(item);			
		}
			
		requisicao = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(RequisicaoAlmoxarifado.class));			
		requisicao.setListaItens(itens);		
		
		return requisicao;
	}		
	
	public int findNextNumeroRequisicao() {		
		String query = "select seq_supr_510.nextval from dual";
		return jdbcTemplate.queryForObject(query, Integer.class);		
	}
	
	public int findNextSequenciaItemRequisicao(int numeroRequisicao) {		
		String query = "select nvl(max(a.sequencia),0) + 1 from supr_520 a where a.num_requisicao = ?";
		return jdbcTemplate.queryForObject(query, Integer.class, numeroRequisicao);		
	}
		
	public void gravarCapaRequisicao(int numeroRequisicao, int centroCusto, String observacao, String requisitante, int empresa, int divisaoProducao) {
				
		String query = "insert into supr_510 (NUM_REQUISICAO, DATA_EMISSAO, CENTRO_CUSTO, OBSERVACAO, REQUISITANTE, CODIGO_EMPRESA, DIVISAO_PRODUCAO, ORDEM_PRODUCAO) "
		+ " values (?, to_char(sysdate), ?, ?, ?, ?, ?, 999999999) ";
		
		jdbcTemplate.update(query, numeroRequisicao, centroCusto, observacao, requisitante, empresa, divisaoProducao);
	}
		
	public void gravarItemRequisicao(int numeroRequisicao, int sequencia, String nivel, String grupo, String sub, String item, double qtdeRequisitada, int codTransacao, int deposito, int centroCustoDestino, String narrativaProduto) {
	
		String query = " insert into supr_520 (NUM_REQUISICAO, SEQUENCIA, NIVEL, GRUPO, SUBGRUPO, ITEM, QTDE_REQUISITADA, COD_TRANSACAO, DEPOSITO, CCUSTO_DESTINO, NARRATIVA_PROD, SITUACAO, DATA_REQUIS, HORA_REQUIS) "
		+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, "
		+ "	to_date(sysdate), " 
		+ "	to_date('16/11/1989 ' || to_CHAR(sysdate,'hh24:mi'),'dd/mm/yyyy hh24:mi')) ";
		
		jdbcTemplate.update(query, numeroRequisicao, sequencia, nivel, grupo, sub, item, qtdeRequisitada, codTransacao, deposito, centroCustoDestino, narrativaProduto);
	}
	
	public Map<String, Object> findDadosPedidoCompraMaisProximoByItem(String nivel, String grupo, String sub, String item, boolean isMostruario, int periodoMostruario) {		
		Map<String, Object> registro;				

		String operacaoPeriodo = "<>";
		if (isMostruario) operacaoPeriodo = "="; 
		
		String query = "select * from supr_100 " 
		+ " where item_100_nivel99 = ? "
		+ " and item_100_grupo = ? "
		+ " and item_100_subgrupo = ? "
		+ " and item_100_item = ? "
		+ " and qtde_saldo_item > 0 "
		+ " and cod_cancelamento = 0 "
		+ " and situacao_item < 3 "
		+ " and periodo_compras " + operacaoPeriodo + " " + periodoMostruario
		+ " and rownum = 1 "
		+ " order by data_prev_entr "; 

		try {
			registro = jdbcTemplate.queryForMap(query, nivel, grupo, sub, item);	
		} catch (Exception e) {			
			registro = new HashMap<String, Object>();
		}
	    
	    return registro;
	}
	
	public List<Integer> findPedidosCompraParaGeracaoReqAlmoxarifado(Date dataEmissaoInicial, int codLocalEntrega) {
		List<Integer> listaPedidosCompras;
		
		String query = " select a.pedido_compra, min(a.dt_emis_ped_comp) from supr_090 a, supr_100 b, basi_185 c "
		+ " where a.dt_emis_ped_comp >= ? "
		+ " and a.cod_end_entrega   = ? "
		+ " and b.num_ped_compra = a.pedido_compra "
		+ " and c.centro_custo = b.centro_custo "
		+ " and c.local_entrega = ? "		
		+ " and not exists (select 1 from orion_sup_005 o where o.pedido_compra = a.pedido_compra) "		
		+ " group by a.pedido_compra ";  
		
		try {
			listaPedidosCompras = jdbcTemplate.queryForList(query, Integer.class, dataEmissaoInicial, codLocalEntrega, codLocalEntrega);	
		} catch (Exception e) {
			listaPedidosCompras = new ArrayList<Integer>();
		}
		return listaPedidosCompras;		
	}	
	
	public void gravarRequisicaoAlmoxGeradoParaPedidoCompra(int pedidoCompra, int numRequisicaoAlmox) {		
		String query = " insert into orion_sup_005 (pedido_compra, num_requisicao, email_enviado) values (?, ?, 0) ";				
		jdbcTemplate.update(query, pedidoCompra, numRequisicaoAlmox); 
	}
	
	public void atualizarEmailEnviadoNaRequisicaoAlmoxGeradoParaPedidoCompra(int pedidoCompra, int numRequisicaoAlmox, int emailEviado) {
		String query = " update orion_sup_005 set email_enviado = ? where pedido_compra = ? and num_requisicao = ? ";
		jdbcTemplate.update(query, emailEviado, pedidoCompra, numRequisicaoAlmox);
	}
}
