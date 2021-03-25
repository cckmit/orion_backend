package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ArtigoCapacidadeProducao;
import br.com.live.model.EstagioCapacidadeProducao;

@Repository
public class CapacidadeProducaoCustom {

	private JdbcTemplate jdbcTemplate;

	public CapacidadeProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<EstagioCapacidadeProducao> findEstagios() {

		String query = "select m.codigo_estagio estagio, m.descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos "
				+ " from mqop_005 m, orion_035 o " + " where m.area_producao = 1 " + " and m.codigo_estagio <> 0 "
				+ " and o.estagio (+) = m.codigo_estagio " + " order by m.codigo_estagio ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioCapacidadeProducao.class));
	}

	public List<ArtigoCapacidadeProducao> findArtigosByEstagio(int estagio) {

		String query = "select b.artigo, b.descr_artigo descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos "
				+ " from basi_290 b, orion_036 o " + " where b.nivel_estrutura = '1' " + " and b.artigo <> 0 "
				+ " and o.estagio (+) = " + estagio + " and o.artigo (+) = b.artigo " + " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCapacidadeProducao.class));
	}

	public List<EstagioCapacidadeProducao> findEstagiosCapacidadeConfigurada() {

		String query = "select m.codigo_estagio estagio, m.descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos "
				+ " from mqop_005 m, orion_035 o " + " where m.area_producao = 1 " + " and m.codigo_estagio <> 0 "
				+ " and o.estagio (+) = m.codigo_estagio and (o.qtde_pecas > 0 or o.qtde_minutos > 0) "
				+ " order by m.codigo_estagio ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioCapacidadeProducao.class));
	}
	
	public List<ArtigoCapacidadeProducao> findArtigosCapacidadeConfigurada() {

		String query = "select b.artigo, b.descr_artigo descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos "
				+ " from basi_290 b, orion_036 o " + " where b.nivel_estrutura = '1' " + " and b.artigo <> 0 "
				+ " and o.artigo (+) = b.artigo  and (o.qtde_pecas > 0 or o.qtde_minutos > 0) " 
				+ " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCapacidadeProducao.class));
	}

}
