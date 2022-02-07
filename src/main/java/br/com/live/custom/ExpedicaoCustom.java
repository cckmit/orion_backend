package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.DadosModalEndereco;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;

@Repository
public class ExpedicaoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ExpedicaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<EnderecoCount> findReferenciaEnd(int codDeposito) {
		List<EnderecoCount> dadosEnd;

		String query = " select count(*) totalPecas, a.grupo || '.' || a.subgrupo || '.' || a.item referencia, a.endereco from pcpc_330 a "
				+ " where a.deposito = " + codDeposito
				+ " and a.estoque_tag = 1 "
				+ " group by a.endereco, a.grupo, a.subgrupo, a.item, a.endereco ";
		
		try {
			dadosEnd = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EnderecoCount.class));
		} catch (Exception e) {
			dadosEnd = new ArrayList<EnderecoCount>();
		}
		
		return dadosEnd;
	}
	
	public Embarque findEmbarque(int periodo, int ordem, int pacote) {
		Embarque dadosEmbarque;
		
		String query = " select a.proconf_grupo grupo, a.proconf_subgrupo subgrupo, a.proconf_item item, b.narrativa descricao, SUBSTR(min(c.grupo_embarque),LENGTH(min(c.grupo_embarque))-1,2) grupoEmbarque, min(c.data_entrega) dataEmbarque, 1 existeEmbarque from pcpc_040 a, basi_010 b, basi_590 c "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordem
				+ " and a.ordem_confeccao = " + pacote
				+ " and b.nivel_estrutura = '1' "
				+ " and b.grupo_estrutura = a.proconf_grupo "
				+ " and b.subgru_estrutura = a.proconf_subgrupo "
				+ " and b.item_estrutura = a.proconf_item "
				+ " and c.nivel = '1' "
				+ " and c.grupo = a.proconf_grupo "
				+ " and c.subgrupo = a.proconf_subgrupo "
				+ " and c.item = a.proconf_item "
				+ " group by a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, b.narrativa, c.grupo_embarque, c.data_entrega ";
		
		try {
			dadosEmbarque = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Embarque.class));
		} catch (Exception e) {
			dadosEmbarque = new Embarque();
		}
		return dadosEmbarque;
	}
	
	public DadosModalEndereco findDadosModalEndereco(int deposito, String endereco) {
		DadosModalEndereco dadosModal;
		
		String query = " select a.grupo, a.subgrupo, a.item, a.endereco, b.colecao || ' - ' || c.descr_colecao colecao, d.qtde_estoque_atu saldo, nvl(e.grupo_embarque, 0) embarque from pcpc_330 a, basi_030 b, basi_140 c, estq_040 d, basi_590 e "
				+ " where a.deposito = " + deposito
				+ " and a.estoque_tag = 1 "
				+ " and a.endereco = '" + endereco + "'"
				+ " and b.nivel_estrutura = '1' "
				+ " and b.referencia = a.grupo "
				+ " and c.colecao = b.colecao "
				+ " and d.cditem_nivel99 = '1' "
				+ " and d.cditem_grupo = a.grupo "
				+ " and d.cditem_subgrupo = a.subgrupo "
				+ " and d.cditem_item = a.item "
				+ " and d.deposito = " + deposito
				+ " and e.nivel (+) = '1' "
				+ " and e.grupo (+) = a.grupo "
				+ " and e.subgrupo (+) = a.subgrupo "
				+ " and e.item (+) = a.item "
				+ " group by a.endereco, a.grupo, a.subgrupo, a.item, b.colecao, c.descr_colecao, d.qtde_estoque_atu, e.grupo_embarque ";
		
		try {
			dadosModal = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(DadosModalEndereco.class));
		} catch (Exception e) {
			dadosModal = new DadosModalEndereco();
		}
		return dadosModal;
	}
}
