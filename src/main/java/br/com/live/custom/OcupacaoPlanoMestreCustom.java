package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.OcupacaoPlanoPorArtigo;
import br.com.live.model.OcupacaoPlanoPorEstagio;

@Repository
public class OcupacaoPlanoMestreCustom {

	private JdbcTemplate jdbcTemplate;

	public OcupacaoPlanoMestreCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public OcupacaoPlanoPorEstagio findOcupacaoByEstagio(long idPlanoMestre, int estagio) {

		String query = "select ocupacao_plano.estagio, "
				+ " max(ocupacao_plano.qtde_capac_pecas) capacidadePecas, sum(ocupacao_plano.qtde_pecas) qtdePecas, "
				+ " DECODE (max(ocupacao_plano.qtde_capac_pecas),0,0,((sum(ocupacao_plano.qtde_pecas) / max(ocupacao_plano.qtde_capac_pecas)) * 100)) percOcupacaoPecas, "
				+ " (max(ocupacao_plano.qtde_capac_pecas) - sum(ocupacao_plano.qtde_pecas)) sobraFaltaPecas, "
				+ " max(ocupacao_plano.qtde_capac_minutos) capacidadeMinutos, sum(ocupacao_plano.qtde_minutos) qtdeMinutos, "
				+ " DECODE (max(ocupacao_plano.qtde_capac_minutos),0,0,((sum(ocupacao_plano.qtde_minutos) / max(ocupacao_plano.qtde_capac_minutos)) * 100)) percOcupacaoMinutos, "
				+ " (max(ocupacao_plano.qtde_capac_minutos) - sum(ocupacao_plano.qtde_minutos)) sobraFaltaMinutos "
				+ " from (select a.estagio, c.grupo, c.item, c.qtde_programada qtde_pecas, (c.qtde_programada * sum(d.minutos_homem)) qtde_minutos, "
				+ " a.qtde_pecas qtde_capac_pecas, a.qtde_minutos qtde_capac_minutos "
				+ " from orion_035 a, orion_017 b, orion_016 c, mqop_050 d  where a.estagio = " + estagio
				+ " and b.num_plano_mestre = " + idPlanoMestre + " and c.id = b.num_item_plano_mestre "
				+ " and c.qtde_programada > 0  and d.nivel_estrutura = '1'  and d.grupo_estrutura = c.grupo "
				+ " and (d.item_estrutura = c.item or d.item_estrutura = '000000') "
				+ " and d.numero_alternati = b.alternativa  and d.numero_roteiro = b.roteiro "
				+ " and d.codigo_estagio = a.estagio "
				+ " group by a.estagio, a.qtde_pecas, a.qtde_minutos, c.grupo, c.item, c.qtde_programada) ocupacao_plano "
				+ " group by ocupacao_plano.estagio ";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OcupacaoPlanoPorEstagio.class));
	}

	public List<OcupacaoPlanoPorArtigo> findOcupacaoArtigosByEstagio(long idPlanoMestre, int estagio) {

		String query = "select ocupacao_plano.estagio, ocupacao_plano.artigo, max(l.descr_artigo) descricao, "
				+ " max(ocupacao_plano.qtde_capac_pecas) capacidadePecas, sum(ocupacao_plano.qtde_pecas) qtdePecas, "
				+ " decode(max(ocupacao_plano.qtde_capac_pecas),0,0,((sum(ocupacao_plano.qtde_pecas) / max(ocupacao_plano.qtde_capac_pecas)) * 100)) percOcupacaoPecas, "
				+ " (max(ocupacao_plano.qtde_capac_pecas) - sum(ocupacao_plano.qtde_pecas)) sobraFaltaPecas, "
				+ " max(ocupacao_plano.qtde_capac_minutos) capacidadeMinutos, sum(ocupacao_plano.qtde_minutos) qtdeMinutos, "
				+ " decode(max(ocupacao_plano.qtde_capac_minutos),0,0,((sum(ocupacao_plano.qtde_minutos) / max(ocupacao_plano.qtde_capac_minutos)) * 100)) percOcupacaoMinutos, "
				+ " (max(ocupacao_plano.qtde_capac_minutos) - sum(ocupacao_plano.qtde_minutos)) sobraFaltaMinutos "
				+ " from (select a.estagio, a.artigo, c.grupo, c.item, c.qtde_programada qtde_pecas, (c.qtde_programada * sum(d.minutos_homem)) qtde_minutos, "
				+ " a.qtde_pecas qtde_capac_pecas, a.qtde_minutos qtde_capac_minutos "
				+ " from orion_036 a, orion_017 b, orion_016 c, basi_030 f, mqop_050 d " + " where a.estagio = "
				+ estagio + " and (a.qtde_pecas > 0 or a.qtde_minutos > 0) " + " and b.num_plano_mestre = "
				+ idPlanoMestre + " and b.num_item_plano_mestre = c.id " + " and c.qtde_programada > 0 "
				+ " and f.nivel_estrutura = '1' " + " and f.referencia = c.grupo " + " and f.artigo = a.artigo "
				+ " and d.nivel_estrutura = '1' " + " and d.grupo_estrutura = c.grupo "
				+ " and (d.item_estrutura = c.item or d.item_estrutura = '000000') "
				+ " and d.numero_alternati = b.alternativa " + " and d.numero_roteiro = b.roteiro "
				+ " and d.codigo_estagio = a.estagio "
				+ " group by a.estagio, a.artigo, a.qtde_pecas, a.qtde_minutos, c.grupo, c.item, c.qtde_programada) ocupacao_plano, basi_290 l "
				+ " where l.artigo = ocupacao_plano.artigo "
				+ " group by ocupacao_plano.estagio, ocupacao_plano.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OcupacaoPlanoPorArtigo.class));
	}

}
