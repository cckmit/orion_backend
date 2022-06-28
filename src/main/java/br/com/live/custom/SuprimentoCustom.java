package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.CentroCusto;
import br.com.live.model.ConsultaPreRequisicaoAlmoxItem;
import br.com.live.model.DivisaoProducao;

@Repository
public class SuprimentoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public SuprimentoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CentroCusto> findCentroCusto() {
				
		String query = " select b.centro_custo codigo, b.descricao from basi_185 b "
		+ " where b.descricao not like '%(IN)%' "
		+ " and b.centro_custo > 0 "
		+ " order by b.centro_custo ";
						
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CentroCusto.class));		
	}
		
	public List<DivisaoProducao> findDivisaoProducao() {
		
		String query = " select b.divisao_producao codigo, b.descricao from basi_180 b "
		+ " where b.divisao_producao > 0 "
		+ " order by b.divisao_producao ";
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DivisaoProducao.class));
	}
	
	public List<ConsultaPreRequisicaoAlmoxItem> findItensPreRequisicaoByIdRequisicao(long idRequisicao) {
		
		String query = " select a.id, a.sequencia, a.nivel, a.grupo, a.sub, a.item, b.narrativa, a.cod_deposito deposito, c.descricao descDeposito, a.quantidade " 
		+ " from orion_sup_002 a, basi_010 b, basi_205 c "
		+ " where b.nivel_estrutura = a.nivel "
		+ " and b.grupo_estrutura = a.grupo "
		+ " and b.subgru_estrutura = a.sub "
		+ " and b.item_estrutura = a.item "
		+ " and c.codigo_deposito = a.cod_deposito "
		+ " and a.id_pre_requisicao = " + idRequisicao
		+ " order by a.sequencia ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPreRequisicaoAlmoxItem.class));
	}
	
}
