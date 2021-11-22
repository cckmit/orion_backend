package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaTipoPonto;
import br.com.live.model.ConsultaTipoPontoFio;
import br.com.live.model.ConsultaTiposFio;
import br.com.live.model.Maquinas;
import br.com.live.model.OptionProduto;

@Repository
public class EngenhariaCustom {

	private JdbcTemplate jdbcTemplate;

	public EngenhariaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int findNewIdMarcas() {

		int proxCod = 0;

		String query = " select nvl(max(id),0) +1 from orion_080 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findNewIdTipos() {

		int proxCod = 0;

		String query = " select nvl(max(id),0) +1 from orion_081 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findIdNewTipoPonto() {

		int proxCod = 0;

		String query = " select nvl(max(id),0) +1 from orion_082 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findIdNewSequenciaPontoFio(int idTipoPonto) {

		int proxCod = 0;

		String query = " select nvl(max(sequencia),0) +1 from orion_083 a" + " where a.id_tipo_ponto = " + idTipoPonto;
		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public List<ConsultaTiposFio> findAllTiposFio() {
		String query = " select a.id, a.descricao, a.titulo, a.centim_cone centimetrosCone, b.id || ' - ' || b.descricao marca from orion_081 a, orion_080 b "
				+ " where a.marca = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTiposFio.class));
	}

	public List<Maquinas> findAllMaquinas() {
		String query = " select a.grupo_maquina || '.' || b.subgrupo_maquina codigo, a.nome_grupo_maq || ' ' || b.nome_sbgrupo_maq descricao from mqop_010 a, mqop_020 b "
				+ " where a.grupo_maquina = b.grupo_maquina " + " and a.grupo_maquina not like '%.%' "
				+ " order by a.grupo_maquina, b.subgrupo_maquina ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Maquinas.class));
	}

	public List<OptionProduto> findAllFios() {
		String query = " select n.basi030_nivel030 || '.' || n.basi030_referenc || '.' || n.tamanho_ref codigo, b.descr_referencia || ' ' || n.descr_tam_refer descricao from basi_020 n, basi_030 b "
				+ " where n.basi030_nivel030 = '9' " + " and b.nivel_estrutura = n.basi030_nivel030 "
				+ " and b.referencia = n.basi030_referenc " + " and b.conta_estoque = 63 ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OptionProduto.class));
	}

	public List<ConsultaTipoPontoFio> findTipoPontoFio(int idTipoPonto) {
		String query = " select a.id, a.sequencia, a.id_tipo_ponto idTipoPonto, a.tipo_fio tipoFio, a.consumo_fio consumoFio, b.descricao descFio from orion_083 a, orion_081 b "
				+ " where a.id_tipo_ponto = " + idTipoPonto + " and a.tipo_fio = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTipoPontoFio.class));
	}

	public ConsultaTipoPonto findTipoPonto(int idTipoPonto) {
		String query = " select l.id, l.descricao, l.grupo_maquina || '.' || l.sub_grupo_maquina maquina from orion_082 l "
				+ " where l.id = " + idTipoPonto;

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTipoPonto.class));
	}

}
