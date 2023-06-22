package br.com.live.administrativo.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.model.Bens;
import br.com.live.administrativo.model.ConsultaMovimentacoes;

@Repository
public class BensCustom {

	private JdbcTemplate jdbcTemplate;

	public BensCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int findNextIdTipoMovimentacao() {

		Integer idNextTipoMov;

		String query = " select nvl(max(id),0) + 1 from orion_096 ";

		try {
			idNextTipoMov = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idNextTipoMov = 0;
		}
		return idNextTipoMov;
	}

	public int findNextSequenciaMovimentacao() {

		Integer nextSequencia;

		String query = " select nvl(max(sequencia),0) + 1 from orion_100 ";

		try {
			nextSequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextSequencia = 0;
		}
		return nextSequencia;
	}

	public List<Bens> findAllBens() {

		List<Bens> bens;

		String query = " select a.id, a.code, a.sequence, a.descricao from patr_020 a ";

		try {
			bens = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Bens.class));
		} catch (Exception e) {
			bens = new ArrayList<Bens>();
		}

		return bens;
	}

	public List<ConsultaMovimentacoes> findAllMovimentacoes() {

		String query = " select a.sequencia, a.id_bem idbem, a.tipo_movimentacao || ' - ' || b.descricao tipoMovimentacao, a.cnpj_origem cnpjOrigem, a.cnpj_destino cnpjDestino, a.data_envio dataEnvio, a.nota_fiscal notaFiscal from orion_100 a, orion_096 b "
				+ " where b.id = a.tipo_movimentacao " + " order by a.sequencia ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMovimentacoes.class));
	}
	
	public List<ConsultaMovimentacoes> findMovimentacoesByBem(int idBem) {
		String query = " select a.sequencia, a.id_bem idbem, b.descricao tipoMovimentacao, a.cnpj_origem cnpjOrigem, a.cnpj_destino cnpjDestino, a.data_envio dataEnvio, a.nota_fiscal notaFiscal, a.observacao, a.usuario || ' - ' || c.nome usuario from orion_100 a, orion_096 b, orion_001 c "
				+ " where b.id = a.tipo_movimentacao "
				+ " and a.usuario = c.id "
				+ " and a.id_bem = " + idBem
				+ " order by sequencia ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMovimentacoes.class));
	}
	
}
