package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ArtigoCapacidadeProducao;
import br.com.live.model.CapacidadeEmMinutosMes;
import br.com.live.model.EstagioCapacidadeProducao;

@Repository
public class CapacidadeProducaoCustom {

	private JdbcTemplate jdbcTemplate;

	public CapacidadeProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<EstagioCapacidadeProducao> findEstagios(int periodo) {

		String query = "select m.codigo_estagio estagio, m.descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos " 
		  + " from mqop_005 m, orion_035 o "
		  + " where m.area_producao = 1 "
		    + " and m.codigo_estagio <> 0 " 
		    + " and o.periodo (+) = " + periodo
		    + " and o.estagio (+) = m.codigo_estagio " 
		  + " order by m.codigo_estagio " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioCapacidadeProducao.class));
	}

	public EstagioCapacidadeProducao findCapacidadeByEstagio(int periodo, int estagio) {

		String query = "select m.codigo_estagio estagio, m.descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos " 
		  + " from mqop_005 m, orion_035 o "
		  + " where m.area_producao = 1 "
		    + " and m.codigo_estagio = " + estagio 
		    + " and o.periodo (+) = " + periodo
		    + " and o.estagio (+) = m.codigo_estagio " 
		  + " order by m.codigo_estagio " ;
				
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(EstagioCapacidadeProducao.class));
	}	
	
	public List<EstagioCapacidadeProducao> findEstagiosCapacidadeConfigurada() {

		String query = "select a.estagio, b.descricao, sum(a.qtde_pecas) qtdePecas, sum(a.qtde_minutos) qtdeMinutos "
		 + " from orion_035 a, mqop_005 b "
		 + " where (a.qtde_pecas > 0 and a.qtde_minutos > 0) "
		 + " and b.codigo_estagio = a.estagio "
	 	 + " group by a.estagio, b.descricao "
		 + " order by a.estagio, b.descricao " ;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioCapacidadeProducao.class));
	}

	public List<EstagioCapacidadeProducao> findEstagiosCapacidadeConfiguradaByPeriodo(int periodoInicio, int periodoFim) {
		 
		String query = "select a.estagio, sum(a.qtde_pecas) qtdePecas, sum(a.qtde_minutos) qtdeMinutos "
		 + " from orion_035 a "
		 + " where a.periodo between " + periodoInicio + " and " + periodoFim
		 + " and a.qtde_pecas > 0 " 
		 + " and a.qtde_minutos > 0 "
		 + " group by a.estagio ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioCapacidadeProducao.class));
	}
	
	public List<ArtigoCapacidadeProducao> findArtigosCapacidadeConfiguradaByPeriodoEstagio(int periodoInicio, int periodoFim, int estagio) {
		 
		String query = "select a.artigo, sum(a.qtde_pecas) qtdePecas, sum(a.qtde_minutos) qtdeMinutos "
		 + " from orion_036 a "
		 + " where a.periodo between " + periodoInicio + " and " + periodoFim
		 + " and a.estagio = " + estagio
		 + " and a.qtde_pecas > 0 " 
		 + " and a.qtde_minutos > 0 "
		 + " group by a.artigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCapacidadeProducao.class));
	}
		
	public List<ArtigoCapacidadeProducao> findArtigosByEstagio(int periodo, int estagio) {

		String query = "select b.artigo, b.descr_artigo descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos " 
 		  + " from basi_290 b, orion_036 o "
		  + " where b.nivel_estrutura = '1' "
		    + " and b.artigo <> 0 "
		    + " and o.periodo (+) = " + periodo
		    + " and o.estagio (+) = " + estagio
		    + " and o.artigo (+) = b.artigo " 
		  + " order by b.artigo " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCapacidadeProducao.class));
	}
	
	public List<ArtigoCapacidadeProducao> findArtigosCapacidadeConfigurada() {

		String query = "select b.artigo, b.descr_artigo descricao, nvl(o.qtde_pecas,0) qtdePecas, nvl(o.qtde_minutos,0) qtdeMinutos "
				+ " from basi_290 b, orion_036 o " + " where b.nivel_estrutura = '1' " + " and b.artigo <> 0 "
				+ " and o.artigo (+) = b.artigo  and (o.qtde_pecas > 0 or o.qtde_minutos > 0) " 
				+ " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCapacidadeProducao.class));
	}

	public CapacidadeEmMinutosMes findCapacidadeEmMinutosByMesAno(int mes, int ano) {
		
		String query = "select o.mes, o.ano, sum(o.capacidade_total) qtdeMinutos from ORION_CFC_320 o "
		+ " where o.mes = ? "
		+ " and o.ano = ? "
		+ " group by o.mes, o.ano ";  		
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(CapacidadeEmMinutosMes.class), mes, ano);
	}
}
