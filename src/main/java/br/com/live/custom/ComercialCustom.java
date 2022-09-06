package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaTitulosBloqForn;

@Repository
public class ComercialCustom {
	
	private JdbcTemplate jdbcTemplate;
	
	public ComercialCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void gravaEnvioProdEcommerce(String produto) {
		String query = "insert into ecom_005 (sku, disponivel, preco, controle, data) values (?, 0, 0, 0, sysdate)";
		
		jdbcTemplate.update(query, produto);
	}
	
	public List<ConsultaTitulosBloqForn> findAllTitulosBloqForn() {
		String query = " select a.cnpj_fornecedor_9 || '.' || a.cnpj_fornecedor_4 || '.' || a.cnpj_fornecedor_2 id, a.cnpj_fornecedor_9 || '/' || lpad(a.cnpj_fornecedor_4,4,0) || '-' || lpad(a.cnpj_fornecedor_2,2,0) || ' - ' || b.nome_fornecedor fornecedor, a.data_bloqueio dataBloqueio, a.data_desbloqueio dataDesbloqueio, "
				+ " a.motivo from orion_com_250 a, supr_010 b "
				+ " where a.cnpj_fornecedor_9 = b.fornecedor9 "
				+ " and a.cnpj_fornecedor_4 = b.fornecedor4 "
				+ " and a.cnpj_fornecedor_2 = b.fornecedor2 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosBloqForn.class));
	}

}
