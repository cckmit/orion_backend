package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasProducao;
import br.com.live.model.ConsultaObservacaoOrdemPacote;
import br.com.live.model.ConsultaTiposFio;
import br.com.live.model.DiasUteis;
import br.com.live.model.EstagioProducao;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Repository
public class ConfeccaoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ConfeccaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int findNextIdOrdem() {
		int nextId = 0;
		
		String query = " select nvl((max(b.id)),0)+1 from orion_cfc_221 b ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}
	
	public List<ConteudoChaveNumerica> findPacotesOrdem(int ordemProducao) {
		String query = " select 0 value, '0 - TODOS OS PACOTES' label from dual "
				+ " union all "
				+ " select a.ordem_confeccao value, to_char(a.ordem_confeccao) label "
				+ " from pcpc_040 a, pcpc_020 b "
				+ " where b.ordem_producao = a.ordem_producao "
				+ " and a.ordem_producao = " + ordemProducao
				+ " group by a.ordem_confeccao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConsultaObservacaoOrdemPacote> findAllObsWithQuantidade() {
		String query = " select c.id, c.estagio || ' - ' || g.descricao estagio, c.ordem_producao ordemProducao, c.ordem_confeccao ordemConfeccao, sum(f.qtde_pecas_prog) quantidade, c.tipo_observacao || ' - ' || d.descricao tipoObservacao, "
				+ " c.observacao_adicional observacaoAdicional from orion_cfc_220 c, orion_cfc_221 d, pcpc_020 e, pcpc_040 f, mqop_005 g "
				+ " where c.tipo_observacao = d.id "
				+ " and e.ordem_producao = c.ordem_producao "
				+ " and f.ordem_producao = c.ordem_producao "
				+ " and (f.ordem_confeccao = c.ordem_confeccao OR c.ordem_confeccao = 0) "
				+ " and f.codigo_estagio = 35 "
				+ " and f.qtde_a_produzir_pacote > 0 "
				+ " and g.codigo_estagio = c.estagio "
				+ " group by c.id, c.estagio, c.ordem_producao, c.ordem_confeccao, c.tipo_observacao, d.descricao, c.observacao_adicional, g.descricao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaObservacaoOrdemPacote.class));
	}
	
	public List<EstagioProducao> findAllEstagio() {
		String query = " SELECT a.codigo_estagio estagio, a.descricao FROM mqop_005 a GROUP BY a.codigo_estagio, a.descricao "
				+ " ORDER BY a.codigo_estagio ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
	}
	
	public int findDiasUteis(String dataMeta) {
		String query = " select COUNT(a.dia_util) from basi_260 a where a.data_calendario between (trunc(to_date(?, 'dd-MM-YYYY'),'MM')) "
				+ " AND (trunc(LAST_DAY(to_date(?, 'dd-MM-YYYY')))) "
				+ " AND a.dia_util = 0 ";
		return jdbcTemplate.queryForObject(query, Integer.class, dataMeta, dataMeta);
	}
	
	public List<MetasProducao> findMetasProducao(String idMeta) {
		String query = " SELECT a.nr_semana numSemana, a.dias_uteis diasUteis, a.meta_real metaReal, a.meta_real_turno metaRealTurno, a.meta_ajustada metaAjustada, a.meta_ajustada_turno metaAjustadaTurno "
				+ " FROM orion_cfc_240 a WHERE a.id_mes = " + idMeta;
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MetasProducao.class));
		
	}
}
