package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.DivergenciasPoliticaVendas;
import br.com.live.model.RegrasPoliticaVendas;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class PoliticaVendasCustom {
	
	private JdbcTemplate jdbcTemplate;

	public PoliticaVendasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveAlfaNum> findFormaPagamentos() {
		String query = " select a.forma_pgto value, a.forma_pgto || ' - ' || a.descricao label from loja_010 a ORDER BY a.forma_pgto ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findPortador() {
		String query = " select a.cod_portador value, a.cod_portador || ' - ' || a.nome_banco label from pedi_050 a ORDER BY a.cod_portador ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findCondPagamento() {
		String query = " SELECT a.cond_pgt_cliente value, a.cond_pgt_cliente || ' - ' || a.descr_pg_cliente label FROM pedi_070 a ORDER BY a.cond_pgt_cliente ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveNumerica> findCodFuncionario() {
		String query = " SELECT a.cod_funcionario value, LPAD(a.cod_funcionario, 6, 0) || ' - ' || a.nome label "
				+ "FROM efic_050 a "
				+ "WHERE a.cod_funcionario BETWEEN 40000 AND 41000 "
				+ "AND a.cod_empresa = 1 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findNaturezaOperacao() {
		String query = " select a.natur_operacao value, a.natur_operacao || ' - ' || MIN(a.descr_nat_oper) label from pedi_080 a "
				+ " group by a.natur_operacao "
				+ " order by a.natur_operacao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findCnpj(String cnpj) {
		String query = " SELECT LPAD(a.cgc_9, 9, 0) || LPAD(a.cgc_4, 4, 0) || LPAD(a.cgc_2, 2, 0) value, "
				+ "       DECODE(a.cgc_4, 0, "
				+ "       'CPF: ' || SUBSTR(LPAD(a.cgc_9, 9, 0), 0, 3) || '.' || SUBSTR(LPAD(a.cgc_9, 9, 0), 4, 3) || '.' || SUBSTR(LPAD(a.cgc_9, 9, 0), 7, 3) || '-' || LPAD(a.cgc_2, 2, 0) || ' - ' || a.nome_cliente, "
				+ "       'CNPJ: ' || LPAD(a.cgc_9, 8, 0) || '/' || LPAD(a.cgc_4, 4, 0) || '-' || LPAD(a.cgc_2, 2, 0) || ' - ' || a.nome_cliente) label "
				+ "       FROM pedi_010 a, pedi_085 b "
				+ "       WHERE b.tipo_cliente = a.tipo_cliente "
				+ "       AND a.tipo_cliente IN (2, 4, 6, 8, 9, 12, 15, 16, 17, 18, 19, 21, 22, 23, 25, 26, 27, 28, 29, 31, 32, 33, 35, 36, 37, 99) "
				+ "       AND a.situacao_cliente = 1 "
				+ "       AND LPAD(a.cgc_9, 8, 0) || LPAD(a.cgc_4, 4, 0) || LPAD(a.cgc_2, 2, 0) || a.nome_cliente LIKE '%" + cnpj + "%'";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	public List<ConteudoChaveAlfaNum> findDeposito() {
		String query = " SELECT a.codigo_deposito value, LPAD(a.codigo_deposito, 3, 0) || ' - ' || a.descricao label FROM basi_205 a WHERE a.descricao NOT LIKE '%(IN)%' ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllTipoDeCliente() {
		
		String query = " SELECT a.tipo_cliente value, a.tipo_cliente || ' - ' || a.descr_tipo_clien label FROM pedi_085 a ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<RegrasPoliticaVendas> findAllRegrasByTipo(int tipo) {
		String query = " SELECT a.id, "
				+ "     (SELECT LPAD(b.forma_pgto, 2, 0) || ' - ' || b.descricao FROM loja_010 b WHERE b.forma_pgto = a.forma_pagamento) formapagamento, "
				+ "     (SELECT LPAD(c.cod_portador, 3, 0) || ' - ' || c.nome_banco FROM pedi_050 c WHERE c.cod_portador = a.portador) portador, "
				+ "     (SELECT DECODE(e.cgc_4, 0, "
				+ "     'CPF: ' || SUBSTR(LPAD(e.cgc_9, 9, 0), 0, 3) || '.' || SUBSTR(LPAD(e.cgc_9, 9, 0), 4, 3) || '.' || SUBSTR(LPAD(e.cgc_9, 9, 0), 7, 3) || '-' || LPAD(e.cgc_2, 2, 0) || ' - ' || e.nome_cliente, "
				+ "		'CNPJ: ' || LPAD(e.cgc_9, 8, 0) || '/' || LPAD(e.cgc_4, 4, 0) || '-' || LPAD(e.cgc_2, 2, 0) || ' - ' || e.nome_cliente) "
				+ "     FROM pedi_010 e WHERE LPAD(e.cgc_9, 9, 0) = LPAD(a.cnpj9, 9, 0) AND e.cgc_4 = a.cnpj4 AND e.cgc_2 = a.cnpj2) cnpj, "
				+ "     (SELECT d.cod_funcionario || ' - ' || MIN(d.nome) FROM efic_050 d WHERE d.cod_funcionario = a.cod_funcionario GROUP BY d.cod_funcionario) codfuncionario, "
				+ "     a.desc_capa desccapa, "
				+ "     DECODE(a.tipo,4, DECODE(a.tipo_pedido, 0, '0 - PROGRAMADO', '1 - PRONTA ENTREGA') , '') tipopedido, "
				+ "     (SELECT LPAD(f.codigo_deposito, 3, 0) || ' - ' || f.descricao FROM basi_205 f WHERE f.codigo_deposito = a.deposito_itens AND f.descricao NOT LIKE ('%(IN)%')) depositoitens, "
				+ "     a.desc_max_cliente descmaxcliente, "
				+ "     a.comissao comissao, "
				+ "     (SELECT j.col_tabela_preco || '.' || j.mes_tabela_preco || '.' || j.seq_tabela_preco || ' - ' || j.descricao "
				+ "         FROM pedi_090 j WHERE j.col_tabela_preco = a.tab_col AND j.mes_tabela_preco = a.tab_mes AND j.seq_tabela_preco = a.tab_seq) tabPreco, "
				+ "     (SELECT LPAD(g.cond_pgt_cliente, 3, 0) || ' - ' || g.descr_pg_cliente FROM pedi_070 g WHERE g.cond_pgt_cliente = a.cond_pgto) condpagamento, "
				+ "     (SELECT i.tipo_cliente || ' - ' || i.descr_tipo_clien FROM pedi_085 i WHERE a.tipo_cliente = i.tipo_cliente) tipoCliente, "
				+ "     (SELECT h.natur_operacao || ' - ' || MIN(h.descr_nat_oper) FROM pedi_080 h WHERE h.natur_operacao = a.natureza_operacao GROUP BY h.natur_operacao) naturezaOperacao, "
				+ "     a.desconto desconto "
				+ "     FROM orion_com_260 a WHERE a.tipo = ? ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RegrasPoliticaVendas.class),tipo);
	}
	
	public List<DivergenciasPoliticaVendas> findDivergencias() {
		String query = " SELECT REGRAS.PEDIDO CODPEDIDO,  "
				+ "        TO_CHAR(SYSDATE, 'DD/MM/YYYY') DATA_AUDITORIA, "
				+ "        REGRAS.CANAL, "
				+ "        TO_CHAR(REGRAS.DATA_EMBARQUE, 'DD/MM/YYYY') DATA_EMBARQUE, "
				+ "        TO_CHAR(REGRAS.DATA_EMISSAO, 'DD/MM/YYYY') DATA_EMISSAO, "
				+ "        REGRAS.DIVERGENCIA "
				+ "        FROM ( "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: FORMA DE PAGTO INCORRETA!' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "        AND a.situacao_venda  <> 10 "
				+ "        AND b.forma_pagamento = a.cod_forma_pagto "
				+ "        AND b.tipo = 1 "
				+ "        AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "        AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "        AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "        AND f.tipo_cliente = e.tipo_cliente "
				+ "        AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                        WHERE c.tipo = 1 "
				+ "                        AND c.portador = a.cod_banco) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: CNPJ X Codigo Funcionário!' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "        AND a.situacao_venda <> 10 "
				+ "        AND b.cnpj9 = a.cli_ped_cgc_cli9 "
				+ "        AND b.cnpj4 = a.cli_ped_cgc_cli4 "
				+ "        AND b.cnpj2 = a.cli_ped_cgc_cli2 "
				+ "        AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "        AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "        AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "        AND b.tipo = 2 "
				+ "        AND f.tipo_cliente = e.tipo_cliente "
				+ "        AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 2 "
				+ "                         AND c.cod_funcionario = a.cod_funcionario) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: CNPJ X Desconto Capa' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "        AND a.situacao_venda <> 10 "
				+ "        AND b.cnpj9 = a.cli_ped_cgc_cli9 "
				+ "        AND b.cnpj4 = a.cli_ped_cgc_cli4 "
				+ "        AND b.cnpj2 = a.cli_ped_cgc_cli2 "
				+ "        AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "        AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "        AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "        AND b.tipo = 3 "
				+ "        AND f.tipo_cliente = e.tipo_cliente "
				+ "        AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 3 "
				+ "                         AND c.cnpj9 = a.cli_ped_cgc_cli9 "
				+ "                  		AND c.cnpj4 = a.cli_ped_cgc_cli4 "
				+ "                  		AND c.cnpj2 = a.cli_ped_cgc_cli2 "
				+ "                  		AND c.desc_capa = ROUND(live_fn_calc_perc_desconto(a.desconto_item1, a.desconto_item2, a.desconto_item3), 2)) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: Tipo de Pedido X Deposito Itens' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, pedi_110 b, orion_com_260 c "
				+ "        WHERE b.pedido_venda = a.pedido_venda "
				+ "        AND a.cod_cancelamento = 0 "
				+ "        AND a.situacao_venda <> 10 "
				+ "        AND c.tipo_pedido = a.tipo_pedido "
				+ "        AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "        AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "        AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "        AND f.tipo_cliente = e.tipo_cliente "
				+ "        AND c.tipo = 4 "
				+ "        AND NOT EXISTS (SELECT 1 FROM orion_com_260 d "
				+ "                         WHERE d.tipo = 4 "
				+ "                         AND d.deposito_itens = b.codigo_deposito) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, c.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: Desconto Máx. X Comissão X Tipo Cliente' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_010 b, pedi_085 c "
				+ "        WHERE b.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "        AND b.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "        AND b.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "        AND c.tipo_cliente = b.tipo_cliente "
				+ "        AND EXISTS (SELECT 1 FROM orion_com_260 e WHERE e.tipo = 5 "
				+ "                                                AND e.comissao = a.perc_comis_venda "
				+ "                                                AND e.tipo_cliente = b.tipo_cliente "
				+ "                                                AND e.desc_max_cliente < live_fn_calc_perc_desconto(a.desconto_item1, a.desconto_item2, a.desconto_item3))"
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: Portador x Forma de Pagamento x Condição de Pagamento' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "         AND a.situacao_venda  <> 10 "
				+ "         AND b.forma_pagamento = a.cod_forma_pagto "
				+ "         AND b.tipo = 6 "
				+ "         AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "         AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "         AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.tipo_cliente = e.tipo_cliente  "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 6 "
				+ "                         AND c.portador = a.cod_banco) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 6 "
				+ "                         AND c.cond_pgto = a.cond_pgto_venda) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: Franchising x Natureza Operação x Desconto' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "         AND a.situacao_venda  <> 10 "
				+ "         AND b.cnpj9 = a.cli_ped_cgc_cli9 "
				+ "         AND b.cnpj4 = a.cli_ped_cgc_cli4 "
				+ "         AND b.cnpj2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "         AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "         AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.tipo_cliente = e.tipo_cliente  "
				+ "         AND b.tipo = 7 "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 7 "
				+ "                         AND c.natureza_operacao = a.natop_pv_nat_oper) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 7 "
				+ "                         AND c.desconto = a.desconto_item1 + a.desconto_item2 + a.desconto_item3) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: Cond De Pagamento x Desconto x Natureza x Forma de Pagamento' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "         AND a.situacao_venda  <> 10 "
				+ "         AND b.cond_pgto = a.cond_pgto_venda "
				+ "         AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "         AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "         AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.tipo_cliente = e.tipo_cliente "
				+ "         AND b.tipo = 8 "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 8 "
				+ "                         AND c.desconto = a.desconto_item1 + a.desconto_item2 + a.desconto_item3) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 8 "
				+ "                         AND c.natureza_operacao = a.natop_pv_nat_oper) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 8 "
				+ "                         AND c.forma_pagamento = a.cod_forma_pagto) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: CNPJ X Codigo Funcionario X Natureza Operação X Portador X Condição Pagamento' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f, orion_com_260 b "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "         AND a.situacao_venda  <> 10 "
				+ "         AND b.cnpj9 = a.cli_ped_cgc_cli9 "
				+ "         AND b.cnpj4 = a.cli_ped_cgc_cli4 "
				+ "         AND b.cnpj2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "         AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "         AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.tipo_cliente = e.tipo_cliente "
				+ "         AND b.tipo = 9 "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 9 "
				+ "                         AND c.cod_funcionario = a.cod_funcionario) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 9 "
				+ "                         AND c.natureza_operacao = a.natop_pv_nat_oper) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 9 "
				+ "                         AND c.portador = a.cod_banco) "
				+ "         AND NOT EXISTS (SELECT 1 FROM orion_com_260 c "
				+ "                         WHERE c.tipo = 9 "
				+ "                         AND c.cond_pgto = a.cond_pgto_venda) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM DEPÓSITOS DIFERENTE NOS ITENS' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f "
				+ "        WHERE a.situacao_venda <> 10 "
				+ "         AND a.cod_cancelamento = 0 "
				+ "         AND a.tipo_pedido = 0 "
				+ "         AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "         AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "         AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.tipo_cliente = e.tipo_cliente "
				+ "         AND EXISTS (SELECT 1 FROM pedi_110 b "
				+ "                      WHERE b.pedido_venda = a.pedido_venda "
				+ "                        AND EXISTS (SELECT 1 FROM pedi_110 c "
				+ "                                     WHERE c.pedido_venda = b.pedido_venda "
				+ "                                       AND c.codigo_deposito <> b.codigo_deposito)) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, e.descr_tipo_clien CANAL, a.data_entr_venda DATA_EMBARQUE, a.data_emis_venda DATA_EMISSAO, 'PEDIDO: ' || a.pedido_venda || ' COM LOTE DE ITENS DIFERENTE DE 0' DIVERGENCIA "
				+ "        FROM pedi_100 a, pedi_085 e, pedi_010 f "
				+ "        WHERE a.situacao_venda <> 10 "
				+ "         AND a.cod_cancelamento = 0 "
				+ "         AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "         AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "         AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "         AND f.tipo_cliente = e.tipo_cliente "
				+ "         AND EXISTS (SELECT 1 FROM pedi_110 b "
				+ "                      WHERE b.pedido_venda = a.pedido_venda "
				+ "                        AND EXISTS (SELECT 1 FROM pedi_110 c "
				+ "                                     WHERE c.pedido_venda = b.pedido_venda "
				+ "                                     AND c.lote_empenhado <> 0)) "
				+ "        UNION "
				+ "        SELECT a.pedido_venda PEDIDO, "
				+ "          c.descr_tipo_clien CANAL, "
				+ "          a.data_entr_venda DATA_EMBARQUE, "
				+ "          a.data_emis_venda DATA_EMISSAO, "
				+ "          'PEDIDO: ' || a.pedido_venda || ' COM DIVERGÊNCIA: Tabela de Preço Isento X Natureza de Operação' DIVERGENCIA "
				+ "         FROM pedi_100 a, pedi_010 b, pedi_085 c, pedi_110 d "
				+ "      	WHERE b.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "      	AND b.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "      	AND b.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "      	AND c.tipo_cliente = b.tipo_cliente "
				+ "      	AND d.pedido_venda = a.pedido_venda "
				+ "      	AND EXISTS (SELECT 1 FROM orion_com_260 e WHERE e.tipo = 10 "
				+ "                                                AND e.tab_col = a.colecao_tabela "
				+ "                                                AND e.tab_mes = a.mes_tabela "
				+ "                                                AND e.tab_seq = a.sequencia_tabela) "
				+ "      	AND NOT EXISTS (SELECT 1 FROM orion_com_260 e WHERE e.tipo = 10 "
				+ "                                                AND e.tab_col = a.colecao_tabela "
				+ "                                                AND e.tab_mes = a.mes_tabela "
				+ "                                                AND e.tab_seq = a.sequencia_tabela "
				+ "                                                AND e.natureza_operacao = d.cod_nat_op) "
				+ "        UNION "
				+ "        SELECT pedidos.pedido_venda PEDIDO, "
				+ "               pedidos.canal, "
				+ "              pedidos.data_entr_venda DATA_EMBARQUE, "
				+ "              pedidos.data_emis_venda DATA_EMISSAO, "
				+ "              'PEDIDO: ' || pedidos.pedido_venda || ' COM VALOR LÍQUIDO DOS ITENS DIFERENTES DA CAPA' DIVERGENCIA "
				+ "        FROM ( "
				+ "        SELECT a.pedido_venda, min(a.desconto1) desconto1, min(a.desconto2) desconto2, min(a.desconto3) desconto3, "
				+ "               e.descr_tipo_clien canal, "
				+ "              MAX(a.data_emis_venda) data_emis_venda , "
				+ "              MAX(a.data_entr_venda) data_entr_venda, "
				+ "              MAX(a.qtde_total_pedi) qtde_total_pedi, "
				+ "              MAX(a.valor_total_pedi) valor_total_pedi, "
				+ "              SUM(b.qtde_pedida) qtde_total_itens, "
				+ "              ROUND(SUM(b.qtde_pedida * b.valor_unitario),2) valor_total_itens, "
				+ "              MAX(a.valor_saldo_pedi) valor_saldo_pedi, "
				+ "              MAX(a.qtde_saldo_pedi) qtde_saldo_pedi, "
				+ "              (SELECT ROUND(SUM(((z.qtde_pedida-z.qtde_faturada)* z.valor_unitario) - ( ((z.qtde_pedida-z.qtde_faturada)* z.valor_unitario) * (z.percentual_desc / 100) )),2) "
				+ "               FROM pedi_110 z "
				+ "               WHERE z.pedido_venda = a.pedido_venda "
				+ "                 AND z.cod_cancelamento = 0 "
				+ "              ) valor_saldo_itens "
				+ "        FROM pedi_100 a, pedi_110 b, pedi_085 e, pedi_010 f "
				+ "        WHERE a.cod_cancelamento = 0 "
				+ "        AND a.situacao_venda <> 10 "
				+ "        AND b.pedido_venda = a.pedido_venda "
				+ "        AND f.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "        AND f.cgc_4 = a.cli_ped_cgc_cli4 "
				+ "        AND f.cgc_2 = a.cli_ped_cgc_cli2 "
				+ "        AND f.tipo_cliente = e.tipo_cliente  "
				+ "        GROUP BY a.pedido_venda, e.descr_tipo_clien "
				+ "        ) pedidos "
				+ "        WHERE ((not (pedidos.qtde_total_pedi - pedidos.qtde_total_itens) between -5 and 5) "
				+ "       OR (not (pedidos.valor_total_pedi - pedidos.valor_total_itens) between -5 and 5 ) "
				+ "       OR (not (TRUNC(pedidos.valor_saldo_pedi) - TRUNC(pedidos.valor_saldo_itens - (pedidos.valor_saldo_itens * "
				+ "       (live_fn_calc_perc_desconto(pedidos.desconto1, pedidos.desconto2, pedidos.desconto3) / 100)))) between -5 and 5 ))) REGRAS "
				+ "        ORDER BY REGRAS.PEDIDO";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DivergenciasPoliticaVendas.class));
	}
}
