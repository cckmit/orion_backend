package br.com.live.custom;

import java.util.List;

import br.com.live.model.ConsultaRestricoesRolo;
import br.com.live.model.ConsultaTipoPonto;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasProducao;
import br.com.live.entity.MetasProducaoSemana;
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

	public long findNextIdRestricao() {
		long nextId = 0;

		String query = " select nvl(max(a.id), 0) + 1 from orion_cfc_270 a ";
		try {
			nextId = jdbcTemplate.queryForObject(query, Long.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public long findNextIdRestricaoRolo() {
		long nextId = 0;

		String query = " select nvl(max(a.id), 0) + 1 from orion_cfc_271 a ";
		try {
			nextId = jdbcTemplate.queryForObject(query, Long.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public int validarRestricaoRolo(int codigoRolo, int restricao) {
		int existeDados = 0;

		String query = " select 1 from orion_cfc_271 b "
				+ " where b.codigo_rolo = " + codigoRolo
				+ " and b.restricao = " + restricao;
		try {
			existeDados = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existeDados = 0;
		}
		return existeDados;
	}

	public List<ConteudoChaveNumerica> findOptionsRestricao() {
		String query = " select a.id value, a.id || ' - ' || a.descricao label from orion_cfc_270 a "
				+ " order by a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<Integer> findRolosByOrdem(int ordermProducao) {
		String query = " select b.codigo_rolo from pcpt_020 b " +
				" where b.ordem_producao = " + ordermProducao;
		return jdbcTemplate.queryForList(query, Integer.class);
	}

	public List<ConteudoChaveNumerica> findOptionLeitorOrdensBeneficiamento(String leitor) {
		String query = " select e.ordem_producao value, e.ordem_producao label from pcpt_020 e, pcpb_010 m " +
				" where e.ordem_producao is not null " +
				" and m.ordem_producao = e.ordem_producao " +
				" and m.situacao_ordem = 1 " +
				" and e.ordem_producao like '%" + leitor + "%' " +
				" group by e.ordem_producao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConteudoChaveNumerica> findOptionLeitorRolos(String leitor) {
		String query = " select a.codigo_rolo value, a.codigo_rolo label from pcpt_020 a " +
				"where a.codigo_rolo like '%" + leitor + "%' ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConsultaRestricoesRolo> findAllRestricoesPorRolo() {
		String query = " select a.id, b.ordem_producao ordemProd, b.codigo_rolo rolo, b.codigo_deposito deposito, c.id || ' - ' || c.descricao restricao " +
				" from orion_cfc_271 a, pcpt_020 b, orion_cfc_270 c " +
				" where a.codigo_rolo = b.codigo_rolo " +
				" and c.id = a.restricao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRestricoesRolo.class));
	}
	
	public List<EstagioProducao> findAllEstagio() {
		String query = " SELECT a.codigo_estagio estagio, a.descricao FROM mqop_005 a GROUP BY a.codigo_estagio, a.descricao "
				+ " ORDER BY a.codigo_estagio ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
	}
	
	public List<EstagioProducao> findAllEstagioMetas() {
		String query = " SELECT ESTAGIOS.estagio, ESTAGIOS.descricao FROM "
				+ " ( "
				+ " SELECT a.codigo_estagio estagio, a.descricao FROM mqop_005 a "
				+ " UNION "
				+ " SELECT 101 estagio, 'COLETA PARA FATURAMENTO' descricao FROM dual) ESTAGIOS "
				+ " GROUP BY ESTAGIOS.estagio, ESTAGIOS.descricao "
				+ " ORDER BY ESTAGIOS.estagio ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
	}
}
