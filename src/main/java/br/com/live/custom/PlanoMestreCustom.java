package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.ProgramacaoPlanoMestre;

@Repository
public class PlanoMestreCustom {

	private JdbcTemplate jdbcTemplate;

	public PlanoMestreCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProgramacaoPlanoMestre> findProgramacaoIdByPlanoMestre(long idPlanoMestre) {

		String query = "select a.grupo, a.sub, a.item, a.qtde_programada, c.alternativa, c.roteiro, c.periodo from orion_015 a, orion_016 b, orion_017 c "
				+ " where a.num_plano_mestre = " + idPlanoMestre + " and b.num_plano_mestre = a.num_plano_mestre "
				+ " and b.grupo = a.grupo " + " and b.item = a.item " + " and c.num_plano_mestre = a.num_plano_mestre "
				+ " and c.num_item_plano_mestre = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProgramacaoPlanoMestre.class));
	}

	public List<ConsultaPreOrdemProducao> findPreOrdensByIdPlanoMestre(long idPlanoMestre) {

		String query = "select a.id, a.referencia || ' - ' || b.descr_referencia referencia, a.periodo, "
				+ " a.alternativa || ' - ' || max(c.descricao) alternativa, a.roteiro, a.quantidade, "
				+ " a.deposito || ' - ' || max(d.descricao) deposito, a.observacao "
				+ " from orion_020 a, basi_030 b, basi_070 c, basi_205 d "
				+ " where a.num_plano_mestre = " + idPlanoMestre
				+ " and b.nivel_estrutura = '1' "
				+ " and b.referencia = a.referencia "
				+ " and c.nivel (+) = '1' "
				+ " and c.grupo (+) = a.referencia "
				+ " and c.alternativa (+) = a.alternativa "
				+ " and d.codigo_deposito = a.deposito "
				+ " group by a.id, a.referencia, b.descr_referencia, a.periodo, a.alternativa, a.roteiro, a.deposito, a.observacao, a.quantidade "; 
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPreOrdemProducao.class));
	}

	public int findMaxIdPreOrdem() {

		Integer id;

		String query = " select nvl(max(id),0) from orion_020 ";

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}

		return (int) id;
	}

	public int findMaxIdPreOrdemItem() {

		Integer id;

		String query = " select nvl(max(id),0) from orion_021 ";

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}

		return (int) id;
	}

}
