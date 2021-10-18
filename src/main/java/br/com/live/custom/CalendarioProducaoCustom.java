package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.EstagiosConfigCalend;
import br.com.live.model.EstagiosConfigurados;

@Repository
public class CalendarioProducaoCustom {

	private final JdbcTemplate jdbcTemplate;

	public CalendarioProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<EstagiosConfigurados> findEstagiosConfigurados() {

		List<EstagiosConfigurados> estagiosConfig;

		String query = " select a.sequencia, a.estagio, a.estagio ||' - '|| b.descricao descEstagio, a.lead from orion_060 a, mqop_005 b "
				+ " where a.estagio = b.codigo_estagio ";

		try {
			estagiosConfig = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagiosConfigurados.class));
		} catch (Exception e) {
			estagiosConfig = new ArrayList<EstagiosConfigurados>();
		}
		return estagiosConfig;
	}

	public List<EstagiosConfigurados> findEstagiosParametro(int anoCalendario) {

		List<EstagiosConfigurados> estagiosParam;

		String query = " select a.sequencia, a.estagio, a.estagio ||' - '|| b.descricao descEstagio, a.lead, a.data_inicio dataInicio, a.data_fim dataFim from orion_062 a, mqop_005 b "
				+ " where a.estagio = b.codigo_estagio " + " and a.ano_calendario = " + anoCalendario;

		try {
			estagiosParam = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagiosConfigurados.class));
		} catch (Exception e) {
			estagiosParam = new ArrayList<EstagiosConfigurados>();
		}
		return estagiosParam;
	}

	public EstagiosConfigurados findSequenciaEstagiosParametro(int anoCalendario, int sequencia) {

		EstagiosConfigurados dadosEstagio;

		String query = " select a.sequencia, a.estagio, a.estagio ||' - '|| b.descricao descEstagio, a.lead, a.data_inicio dataInicio, a.data_fim dataFim from orion_062 a, mqop_005 b "
				+ " where a.estagio = b.codigo_estagio " + " and a.ano_calendario = " + anoCalendario
				+ " and a.sequencia = " + sequencia;

		try {
			dadosEstagio = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(EstagiosConfigurados.class));
		} catch (Exception e) {
			dadosEstagio = new EstagiosConfigurados();
		}

		return dadosEstagio;
	}

	public EstagiosConfigurados findSequenciaEstagiosConfigurados(int sequencia) {

		EstagiosConfigurados dadosEstagio;

		String query = " select a.sequencia, a.estagio, a.estagio ||' - '|| b.descricao descEstagio, a.lead from orion_060 a, mqop_005 b "
				+ " where a.estagio = b.codigo_estagio " + " and a.sequencia = " + sequencia;

		try {
			dadosEstagio = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(EstagiosConfigurados.class));
		} catch (Exception e) {
			dadosEstagio = new EstagiosConfigurados();
		}

		return dadosEstagio;
	}

	public boolean existsAnoCalendario(int anoCalendario) {

		int encontrou;

		String query = " select max(1) from orion_062 a " + " where a.ano_calendario = " + anoCalendario;

		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}

	public String getDescricaoEstagio(int estagio) {

		String descEstagio = "";

		String query = " select a.codigo_estagio || ' - ' || a.descricao from mqop_005 a "
				+ " where a.codigo_estagio = " + estagio;

		try {
			descEstagio = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			descEstagio = "";
		}

		return descEstagio;
	}

	public List<EstagiosConfigCalend> findEstagiosByAnoCalendario(int anoCalendario) {

		List<EstagiosConfigCalend> estagiosParam;

		String query = " select a.estagio, a.lead, a.data_inicio dataInicioDate, a.data_fim dataFimDate from orion_062 a "
				+ " where a.ano_calendario = " + anoCalendario;

		try {
			estagiosParam = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagiosConfigCalend.class));
		} catch (Exception e) {
			estagiosParam = new ArrayList<EstagiosConfigCalend>();
		}
		return estagiosParam;
	}

	public boolean todasDatasEstagiosInformadas(int anoCalendario) {

		String query = " select nvl(min(1),0) encontrou from orion_062 o " + " where o.ano_calendario = "
				+ anoCalendario + " and (o.data_inicio is null or o.data_fim is null) ";

		int encontrou = jdbcTemplate.queryForObject(query, Integer.class);

		return !(encontrou == 1);
	}

	public boolean verificaPeriodoExistente(int periodo) {

		String query = " select nvl(min(1),0) from pcpc_010 "
				+ " where pcpc_010.periodo_producao = " + periodo;

		int encontrou = jdbcTemplate.queryForObject(query, Integer.class);

		return (encontrou == 1);
	}
}