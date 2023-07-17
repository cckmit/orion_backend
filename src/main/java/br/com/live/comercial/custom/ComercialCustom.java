package br.com.live.comercial.custom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.live.comercial.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.FaturamentoLiveClothing;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class ComercialCustom {
	
	private JdbcTemplate jdbcTemplate;
	
	public ComercialCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void atualizarCanalDoTipoDeCliente(int codTipoCliente, String descCanalDistribuicao) {
		String query = "update pedi_085 set live_agrup_tipo_cliente = ? where tipo_cliente = ?";
		jdbcTemplate.update(query, descCanalDistribuicao, codTipoCliente); 
	}
	
	public void gravaEnvioProdEcommerce(String produto) {
		String query = "insert into ecom_005 (sku, disponivel, preco, controle, data) values (?, 0, 0, 3, sysdate)";		
		jdbcTemplate.update(query, produto);		
		query = " update ecom_005 set controle = 0 where ecom_005.controle = 3 ";		
		jdbcTemplate.update(query);				
	}
	
	public List<ConsultaTitulosBloqForn> findAllTitulosBloqForn() {
		String query = " select a.cnpj_fornecedor_9 || '.' || a.cnpj_fornecedor_4 || '.' || a.cnpj_fornecedor_2 id, a.cnpj_fornecedor_9 || '/' || lpad(a.cnpj_fornecedor_4,4,0) || '-' || lpad(a.cnpj_fornecedor_2,2,0) || ' - ' || b.nome_fornecedor fornecedor, a.data_bloqueio dataBloqueio, a.data_desbloqueio dataDesbloqueio, "
				+ " a.motivo from orion_com_250 a, supr_010 b "
				+ " where a.cnpj_fornecedor_9 = b.fornecedor9 "
				+ " and a.cnpj_fornecedor_4 = b.fornecedor4 "
				+ " and a.cnpj_fornecedor_2 = b.fornecedor2 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosBloqForn.class));
	}
	
	public List<ConteudoChaveNumerica> findTipoCliente() {
		String query = "select a.tipo_cliente value, a.tipo_cliente || ' - ' || a.descr_tipo_clien label from PEDI_085 a "
				+ "     WHERE NOT EXISTS (SELECT 1 FROM orion_com_210 b "
				+ "		                    WHERE b.tipo_cliente = a.tipo_cliente) "
				+ "     ORDER BY a.tipo_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findTipoClienteLive() {
		String query = " SELECT a.live_agrup_tipo_cliente AS value, a.live_agrup_tipo_cliente AS label "
				+ "    FROM PEDI_085 a "
				+ "    WHERE a.live_agrup_tipo_cliente IS NOT NULL "
				+ "    GROUP BY a.live_agrup_tipo_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveNumerica> findAllCatalogo(){
		String query = "SELECT a.cod_catalogo value, a.cod_catalogo || ' - ' || a.des_catalogo label FROM pedi_063 a "
				+ " ORDER BY a.cod_catalogo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllRelacionam(){
		String query = " SELECT a.id value,  c.des_catalogo || ' - ' || b.descr_tipo_clien label FROM orion_com_280 a, pedi_085 b, pedi_063 c "
				+ "  WHERE c.cod_catalogo = a.catalogo "
				+ "  AND b.tipo_cliente = a.tipo_cliente "
				+ "  ORDER BY a.id ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConsultaTpClienteXTabPreco> findByIdTpCliTabPrecoItem(String idCapa){
		String query = " SELECT a.id_item idItem, "
				+ "		  a.id_capa idCapa, "
				+ "       LPAD(a.col_tab_entr, 2, 0) || '.' || LPAD(a.mes_tab_entr, 2, 0) || '.' || LPAD(a.seq_tab_entr, 2, 0) || ' - ' || b.descricao tabela, "
				+ "       a.periodo_ini periodoInicial, "
				+ "       a.periodo_fim periodoFinal"
				+ "       FROM orion_com_281 a, pedi_090 b "
				+ "		  WHERE b.col_tabela_preco = a.col_tab_entr "
				+ "       AND b.mes_tabela_preco = a.mes_tab_entr "
				+ "       AND b.seq_tabela_preco = a.seq_tab_entr "
				+ "       AND a.id_capa = ? " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTpClienteXTabPreco.class), idCapa);
	}
	
	public ConsultaTpClienteXTabPreco findAllGridItem(String idCapa, long id){
		String query = " SELECT a.col_tab_entr || '.' || a.mes_tab_entr || '.' || a.seq_tab_entr tabela, "
				+ "       a.periodo_ini periodoInicial, "
				+ "       a.periodo_fim periodoFinal"
				+ "       FROM orion_com_281 a "
				+ "		  WHERE a.id_capa = ? "
				+ "       AND a.id_item = ? " ;
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTpClienteXTabPreco.class), idCapa, id);
	}
	
	public List<ConsultaTipoClientePorCanal> findTipoClienteByCanal(int id) {
		
		String query = " SELECT a.id id, a.tipo_cliente codigo, b.descr_tipo_clien descricao FROM orion_com_210 a, pedi_085 b"
				+ "   WHERE b.tipo_cliente = a.tipo_cliente"
				+ "   AND a.id_canal = " + id + " ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTipoClientePorCanal.class));
	}
	
	public List<ConsultaTipoClientePorCanal> findTipoClienteSemCanal() {
		
		String query = " SELECT a.tipo_cliente tipoCliente, a.descr_tipo_clien descTipoCliente FROM pedi_085 a "
				+ "		WHERE NOT EXISTS (SELECT 1 FROM orion_com_210 b "
				+ "                   WHERE b.tipo_cliente = a.tipo_cliente) ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTipoClientePorCanal.class));
	}
	
	public List<FaturamentoLiveClothing> findAllFatLiveClothing(){
		
		String query = "SELECT a.id id, "
				+ "		  a.loja loja, "
				+ "       a.data data, "
				+ "       a.quantidade quantidade, "
				+ "       a.tickets tickets, "
				+ "       a.conversao conversao, "
				+ "       a.valor_dolar valorDolar, "
				+ "       a.valor_real valorReal "
				+ "       FROM orion_com_300 a "
				+ "       ORDER BY a.data DESC ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(FaturamentoLiveClothing.class));
	}
	
	public long findNextIdImpDescClientes() {
		long nextId = 0;

		String query = " select nvl((max(b.id)),0)+1 from orion_com_290 b ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Long.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public List<ConsultaPedidosPorCliente> findPedidosPorCliente(int cnpj9, int cnpj4, int cnpj2) {
		String query = " select a.pedido_venda pedido, a.cli_ped_cgc_cli9 cnpj9, a.cli_ped_cgc_cli4 cnpj4, a.cli_ped_cgc_cli2 cnpj2, a.data_entr_venda dataEmbarque, " +
				" a.valor_saldo_pedi valorSaldo, a.natop_pv_nat_oper natureza from pedi_100 a " +
				" where a.situacao_venda <> 10 " +
				" and a.cod_cancelamento = 0" +
				" and a.valor_saldo_pedi > 2500 " +
				" and a.cli_ped_cgc_cli9 = " + cnpj9 +
				" and a.cli_ped_cgc_cli4 = " + cnpj4 +
				" and a.cli_ped_cgc_cli2 = " + cnpj2 +
				" and a.data_entr_venda <> trunc(sysdate) " +
				" and a.natop_pv_nat_oper in (421,422,0,104,325,175) " +
				" and not exists (select 1 from orion_com_291 b " +
				"                    where b.pedido = a.pedido_venda) " +
				" order by a.data_entr_venda asc, a.valor_saldo_pedi desc ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPedidosPorCliente.class));
	}

	public List<ClientesImportados> findClientesImportados() {
		String query = " select dadosCliente.cnpj9, dadosCliente.cnpj4, dadosCliente.cnpj2, dadosCliente.valor  from (" +
				" select c.cnpj_9 cnpj9, c.cnpj_4 cnpj4, c.cnpj_2 cnpj2, sum(c.valor) valor from orion_com_290 c " +
				" group by c.cnpj_9, c.cnpj_4, c.cnpj_2) dadosCliente " +
				" where dadosCliente.valor > 0 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ClientesImportados.class));
	}

	public void atualizarDescontoEspecialPedido(float desconto, String observacao, int pedido, String observacaoImportacao) {
		String observacaoAtual = findObservacaoPedi(pedido);

		LocalDate dataAtual = LocalDate.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataAtualFormatada = dataAtual.format(dateFormat);

		String query = " UPDATE pedi_100 " +
				" SET OBSERVACAO = '" + observacaoAtual + "' || chr(13) || '" + observacao + "' || chr(13) || '" + dataAtualFormatada + "' || chr(13) || '" + observacaoImportacao + "', "  +
				" DESCONTO_ESPECIAL = ? " +
				" WHERE pedi_100.PEDIDO_VENDA = ? ";
		jdbcTemplate.update(query, desconto, pedido);
	}

	public String findObservacaoPedi(int pedido) {
		String observacao = "";

		String query = " select pedi_100.observacao from pedi_100 " +
				" where pedi_100.pedido_venda = " + pedido;
		try {
			observacao = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			observacao = "";
		}
		return observacao;
	}

	public int validarImportacaoDescontos(int cnpj9,int cnpj4, int cnpj2, String dataPlanilha, float valorPlanilha) {
		int validaImp = 0;

		String query = " SELECT 1 FROM orion_com_290 m " +
				" WHERE m.CNPJ_9 = " + cnpj9 +
				" AND m.CNPJ_4 = " + cnpj4 +
				" AND m.CNPJ_2 = " + cnpj2 +
				" AND m.DATA_INSERCAO = TO_DATE ('" + dataPlanilha + "', 'DD/MM/YYYY') " +
				" AND m.VALOR = " + valorPlanilha;
		try {
			validaImp = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			validaImp = 0;
		}

		return validaImp;
	}

	public List<DescontoClientesImportados> buscarHistoricoImportacoes() {
		String query = " select a.id, lpad(a.cnpj_9,8, '0') || lpad(a.cnpj_4,4,'0') || lpad(a.cnpj_2,2,'0') cnpjCliente, " +
				" a.data_insercao dataInsercao, a.valor valor, a.usuario from orion_com_290 a ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DescontoClientesImportados.class));
	}

	public List<ConsultaPedidosPorCliente> buscarHistoricoDescontos() {
		String query = " select b.pedido, c.cod_ped_cliente pedidoCliente, lpad(b.cnpj_9,8, '0') || lpad(b.cnpj_4,4,'0') || lpad(b.cnpj_2,2,'0') cnpjCliente, " +
				" b.valor_desconto valorSaldo, b.observacao, b.usuario, b.data_desconto dataDesconto from orion_com_291 b, pedi_100 c" +
				" where c.pedido_venda = b.pedido ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPedidosPorCliente.class));
	}

	public List<DescontoClientesImportados> buscarSaldosClientes() {
		String query = " select g.cnpj_9 || g.cnpj_4 || g.cnpj_2 id, lpad(g.cnpj_9,8, '0') || lpad(g.cnpj_4,4,'0') || lpad(g.cnpj_2,2,'0') || ' - ' || b.nome_cliente cnpjCliente, g.valor_desconto valor from orion_com_292 g, pedi_010 b" +
				" where b.cgc_9 = g.cnpj_9 " +
				" and b.cgc_4 = g.cnpj_4 " +
				" and b.cgc_2 = g.cnpj_2 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DescontoClientesImportados.class));
	}
	
	public int findNaturezaPedido(int pedido) {
		int natureza = 0;
		
		String query = " select a.natop_pv_nat_oper from pedi_100 a"
				+ " where a.pedido_venda = " + pedido;
		
		try {
			natureza = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			natureza = 0;
		}
		return natureza;
	}

	public ConsultaEmailClienteCashback findEmailPedidoCliente(int pedido) {
		String query = " SELECT a.COD_PED_CLIENTE pedidoCliente, b.E_MAIL emailCliente  FROM pedi_100 a, pedi_010 b " +
				" WHERE a.pedido_venda = " + pedido +
				" AND b.CGC_9 = a.CLI_PED_CGC_CLI9 " +
				" AND b.CGC_4 = a.CLI_PED_CGC_CLI4 " +
				" AND b.CGC_2 = a.CLI_PED_CGC_CLI2 ";
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaEmailClienteCashback.class));
	}
	
	public List<ConsultaRelacionamRepAntigoNovo> findAllRelacionamentoRepAntNovo() {
		
		String query = " SELECT a.id id, a.repres_antigo || ' - ' || b.nome_rep_cliente represAntigo, "
				+ "       a.repres_novo || ' - ' || c.nome_rep_cliente represNovo "
				+ "   FROM orion_com_220 a, pedi_020 b, pedi_020 c "
				+ "   WHERE b.cod_rep_cliente = a.repres_antigo "
				+ "   AND c.cod_rep_cliente = a.repres_novo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRelacionamRepAntigoNovo.class));
	}
}
