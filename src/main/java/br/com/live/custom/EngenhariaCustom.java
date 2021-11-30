package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaConsumoMetragem;
import br.com.live.model.ConsultaTabelaConsumo;
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
		String query = " select a.id, a.descricao, a.titulo, a.centim_cone centimetrosCone from orion_081 a ";

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
		String query = " select a.id, a.sequencia, a.descricao, a.id_tipo_ponto idTipoPonto, a.tipo_fio_1 tipoFio1, a.tipo_fio_2 tipoFio2, a.tipo_fio_3 tipoFio3, a.consumo_fio consumoFio, b.descricao descFio1, c.descricao descFio2, d.descricao descFio3 from orion_083 a, orion_081 b, orion_081 c, orion_081 d "
		+ " where a.id_tipo_ponto = " + idTipoPonto
		+ " and a.tipo_fio_1 = b.id "
		+ " and a.tipo_fio_2 = c.id " 
		+ " and a.tipo_fio_3 = d.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTipoPontoFio.class));
	}

	public ConsultaTipoPonto findTipoPonto(int idTipoPonto) {
		String query = " select l.id, l.descricao, l.grupo_maquina || '.' || l.sub_grupo_maquina maquina from orion_082 l "
				+ " where l.id = " + idTipoPonto;

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTipoPonto.class));
	}

	public List<ConsultaTabelaConsumo> findConsumoByReferencia(String referencia) {
		String query = " select a.id, a.id_tipo_ponto tipoPonto, a.comprimento_costura comprimentoCostura, b.descricao from orion_084 a, orion_082 b "
				+ " where a.referencia = '" + referencia + "'" + " and a.id_tipo_ponto = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTabelaConsumo.class));
	}

	public OptionProduto findReferenciaById(String referencia) {
		String query = " select v.referencia codigo, v.descr_referencia descricao from basi_030 v "
		+ " where v.nivel_estrutura = '1' "
		+ " and v.referencia = '" + referencia + "'";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OptionProduto.class));
	}

	public List<ConsultaTabelaConsumo> findAllReferenciasSalvas() {
		String query = " select o.referencia id, d.descr_referencia descricao from orion_084 o, basi_030 d "
		+ " where o.referencia = d.referencia group by o.referencia, d.descr_referencia";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTabelaConsumo.class));
	}

	public void deleteConsumo(String referencia, int tipoPonto) {
		String queryDelete = " delete from  orion_085 u where u.referencia = '" + referencia + "'"
		+ " and u.id_tipo_ponto = " + tipoPonto;

		jdbcTemplate.update(queryDelete);
	}

	public void deleteConsumoByIdReferencia(String idReferencia) {
		String queryDelete = " delete from orion_085 u where u.id_referencia = '" + idReferencia + "'";

		jdbcTemplate.update(queryDelete);
	}

	public List<ConsultaConsumoMetragem> ConsultaConsumoMetragem(String idConsumo) {
		String query = " select n.id, n.pacote, n.metragem_costura_cm consumoFio, n.metragem_total metragemTotal, n.metragem_um metragemUm, m.grupo_maquina || '.' || m.sub_grupo_maquina maquina, p.descricao descTipoFio, p.id tipoFio from orion_085 n, orion_082 m, orion_083 o, orion_081 p "
		+ " where n.id_referencia = '" + idConsumo + "'"
		+ " and m.id = n.id_tipo_ponto "
		+ " and o.id_tipo_ponto = m.id "
		+ " and o.tipo_fio = n.sequencia "
		+ " and p.id = o.tipo_fio ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaConsumoMetragem.class));
	}

	public List<ConsultaConsumoMetragem> ConsultaResumoPorReferencia(String referencia) {
		String query = " select p.descricao descTipoFio, SUM(b.metragem_total) metragemTotal, SUM(b.metragem_um) metragemUm from orion_085 b, orion_082 m, orion_083 o, orion_081 p "
		+ " where m.id = b.id_tipo_ponto "
		+ " and o.id_tipo_ponto = m.id "
		+ " and o.tipo_fio = b.sequencia "
		+ " and p.id = o.tipo_fio "
		+ " and b.referencia = '" + referencia + "'"
		+ " group by p.descricao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaConsumoMetragem.class));
	}
}
