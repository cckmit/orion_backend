package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ArtigoProduto;

@Repository
public class ArtigoProdutoCustom {

	private JdbcTemplate jdbcTemplate;

	public ArtigoProdutoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ArtigoProduto> findAll() {

		String query = "select b.artigo id, b.descr_artigo descricao from basi_290 b"
				+ " where b.nivel_estrutura = '1' " + "   and b.descr_artigo <> '.' " + " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoProduto.class));
	}

}
