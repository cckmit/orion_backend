package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.Produto;

@Repository
public class SugestaoReservaTecidoCustom {
	
	private final JdbcTemplate jdbcTemplate;

	public SugestaoReservaTecidoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
		
	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		
		List<Produto> produtos;
				
		String query = " select p.tecordco_nivel99 nivel, p.tecordco_grupo grupo, p.tecordco_subgrupo sub, p.tecordco_item item, m.narrativa "
		+ " from pcpc_032 p, basi_010 m "
		+ " where p.pcpc0302_orprocor in (select a.ordem_producao from pcpc_020 a, pcpc_040 b "
		+ " where a.cod_cancelamento = 0 "
		+ " and b.ordem_producao = a.ordem_producao "
        + " and b.codigo_estagio = 1 " // PROGRAMACAO
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and m.nivel_estrutura = p.tecordco_nivel99 "
		+ " and m.grupo_estrutura = p.tecordco_grupo "
		+ " and m.subgru_estrutura = p.tecordco_subgrupo "
		+ " and m.item_estrutura = p.tecordco_item "
		+ " group by p.tecordco_nivel99, p.tecordco_grupo, p.tecordco_subgrupo, p.tecordco_item, m.narrativa "
		+ " order by p.tecordco_nivel99, p.tecordco_grupo, p.tecordco_subgrupo, p.tecordco_item, m.narrativa ";
		
		try {
			produtos= jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto> ();
		}
		
		return produtos;
	}

	public List<Produto> findReferenciasEmOrdensParaLiberacao() {
		
		List<Produto> produtos;
	
		String query = " select a.referencia_peca grupo, c.descr_referencia narrativa " 
		+ " from pcpc_020 a, pcpc_040 b, basi_030 c "
		+ " where a.cod_cancelamento = 0 "
	    + " and b.ordem_producao = a.ordem_producao "
	    + " and b.codigo_estagio = 1 " // PROGRAMACAO
	    + " and b.qtde_disponivel_baixa > 0 "
	    + " and c.nivel_estrutura = '1' "
	    + " and c.referencia = a.referencia_peca "
		+ " group by a.referencia_peca, c.descr_referencia "
		+ " order by a.referencia_peca, c.descr_referencia ";
		
		try {
			produtos= jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto> ();
		}
		
		return produtos;
	}	
}