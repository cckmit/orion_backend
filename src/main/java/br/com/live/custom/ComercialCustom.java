package br.com.live.custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ComercialCustom {
	
	private JdbcTemplate jdbcTemplate;
	
	public ComercialCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void gravaEnvioProdEcommerce(String produto) {
		String query = "insert into ecom_005 (sku, disponivel, preco, controle, data) values (?, 0, 0, 3, sysdate)";
		
		jdbcTemplate.update(query, produto);
	}

}
