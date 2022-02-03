package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

		String query = " select count(*) totalPecas, a.nivel || '.' || a.grupo || '.' || a.subgrupo || '.' || a.item referencia from pcpc_330 a "
				+ " where a.deposito = " + codDeposito
				+ " and a.estoque_tag = 1 "
				+ " group by a.endereco, a.nivel, a.grupo, a.subgrupo, a.item ";
		
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

}
