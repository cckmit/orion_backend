package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
		String query = " SELECT a.cod_funcionario value, LPAD(a.cod_funcionario, 6, 0) || ' - ' || a.nome label FROM efic_050 a WHERE a.cod_funcionario BETWEEN 40000 AND 41000 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findNaturezaOperacao() {
		String query = " select a.natur_operacao value, a.natur_operacao || ' - ' || MIN(a.descr_nat_oper) label from pedi_080 a "
				+ " group by a.natur_operacao "
				+ " order by a.natur_operacao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	public List<ConteudoChaveAlfaNum> findCnpj() {
		String query = " SELECT LPAD(a.cgc_9, 9, 0) || LPAD(a.cgc_4, 4, 0) || LPAD(a.cgc_2, 2, 0) value, LPAD(a.cgc_9, 9, 0) || '/' || LPAD(a.cgc_4, 4, 0) || '-' || LPAD(a.cgc_2, 2, 0) || ' - ' || a.nome_cliente label "
				+ " FROM pedi_010 a, pedi_085 b "
				+ " WHERE b.tipo_cliente = a.tipo_cliente "
				+ " AND a.tipo_cliente = 4 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	public List<ConteudoChaveAlfaNum> findDeposito() {
		String query = " SELECT a.codigo_deposito value, LPAD(a.codigo_deposito, 3, 0) || ' - ' || a.descricao label FROM basi_205 a WHERE a.descricao NOT LIKE '%(IN)%' ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<RegrasPoliticaVendas> findAllRegrasByTipo(int tipo) {
		String query = " SELECT a.id, "
				+ " (SELECT b.forma_pgto || ' - ' || b.descricao FROM loja_010 b WHERE b.forma_pgto = a.forma_pagamento) formapagamento, "
				+ " (SELECT c.cod_portador || ' - ' || c.nome_banco FROM pedi_050 c WHERE c.cod_portador = a.portador) portador, "
				+ " (SELECT LPAD(e.cgc_9, 9, 0) || '/' || LPAD(e.cgc_4, 4, 0) || '-' || LPAD(e.cgc_2, 2, 0) || ' - ' || e.nome_cliente "
				+ " FROM pedi_010 e WHERE e.cgc_9 = a.cnpj9 AND e.cgc_4 = a.cnpj4 AND e.cgc_2 = a.cnpj2 )cnpj, "
				+ " (SELECT d.cod_funcionario || ' - ' || MIN(d.nome) FROM efic_050 d WHERE d.cod_funcionario = a.cod_funcionario GROUP BY d.cod_funcionario) codfuncionario, "
				+ " a.desc_capa desccapa, "
				+ " DECODE(a.tipo,4, DECODE(a.tipo_pedido, 0, '0 - PROGRAMADO', '1 - PRONTA ENTREGA') , '') tipopedido, "
				+ " (SELECT f.codigo_deposito || ' - ' || f.descricao FROM basi_205 f WHERE f.codigo_deposito = a.deposito_itens AND f.descricao NOT LIKE ('%(IN)%')) depositoitens, "
				+ " a.desc_max_cliente descmaxcliente, "
				+ " a.comissao comissao, "
				+ " (SELECT g.cond_pgt_cliente || ' - ' || g.descr_pg_cliente FROM pedi_070 g WHERE g.cond_pgt_cliente = a.cond_pgto) condpagamento, "
				+ " (SELECT h.natur_operacao || ' - ' || MIN(h.descr_nat_oper) FROM pedi_080 h WHERE h.natur_operacao = a.natureza_operacao GROUP BY h.natur_operacao) naturezaOperacao, "
				+ " a.desconto desconto "
				+ " FROM orion_com_260 a WHERE a.tipo = ? ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RegrasPoliticaVendas.class),tipo);
	}
}
