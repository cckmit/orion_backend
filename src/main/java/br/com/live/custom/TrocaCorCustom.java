package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.TrocaCor;

@Repository
public class TrocaCorCustom {
	private final JdbcTemplate jdbcTemplate;

	public TrocaCorCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean validarOrdemProduzida(int ordemProducao) {

		int encontrou = 0;

		String query = " select 1 from pcpb_020 a " + " where a.ordem_producao = " + ordemProducao
				+ " and a.qtde_quilos_prod > 0 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return !(encontrou == 1);
	}

	public TrocaCor findGrupoOrdemProducao(int ordemProducao) {
		String query = " select b.pano_sbg_grupo grupo, b.pano_sbg_nivel99 nivel, b.pano_sbg_subgrupo subGrupo, b.pano_sbg_item item, c.narrativa from pcpb_020 b, basi_010 c "
				+ " where b.ordem_producao = " + ordemProducao + " and c.nivel_estrutura = b.pano_sbg_nivel99 "
				+ " and c.grupo_estrutura = b.pano_sbg_grupo " + " and c.subgru_estrutura = b.pano_sbg_subgrupo "
				+ " and c.item_estrutura = b.pano_sbg_item ";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(TrocaCor.class));
	}

	public List<TrocaCor> findTamanhosByReferencia(String nivel, String grupo) {
		List<TrocaCor> listaTamanhos = new ArrayList<TrocaCor>();

		String query = " select c.tamanho_ref subGrupo from basi_020 c " + " where c.basi030_nivel030 = '" + nivel
				+ "' " + " and c.basi030_referenc = '" + grupo + "' ";

		try {
			listaTamanhos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TrocaCor.class));
		} catch (Exception e) {
			listaTamanhos = null;
		}

		return listaTamanhos;
	}

	public List<TrocaCor> findCoresByTamanho(String nivel, String grupo, String subGrupo) {
		List<TrocaCor> listaCores = new ArrayList<TrocaCor>();

		String query = " select d.item_estrutura item, d.descricao_15 descCor from basi_010 d "
				+ " where d.nivel_estrutura = '" + nivel + "' " + " and d.grupo_estrutura = '" + grupo + "' "
				+ " and d.subgru_estrutura = '" + subGrupo + "' ";

		try {
			listaCores = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TrocaCor.class));
		} catch (Exception e) {
			listaCores = null;
		}

		return listaCores;
	}

	public void updateCorRolosPreparados(int ordemProducao, String sub, String item) {

		String setCampos = "";

		if (sub != null) {
			setCampos = " pcpt_025.pano_fin_subgrupo = '" + sub + "'";
		}

		if (item != null) {
			if (!setCampos.isEmpty()) {
				setCampos += ", ";
			}
			setCampos = setCampos + " pcpt_025.pano_fin_item = '" + item + "'";
		}

		String query = " update pcpt_025 set " + setCampos + " where pcpt_025.area_ordem = 2 "
				+ " and pcpt_025.ordem_producao = " + ordemProducao + " and exists (select 1 from pcpb_020 pp "
				+ " where pp.ordem_producao = pcpt_025.ordem_producao "
				+ " and pp.pano_sbg_nivel99 = pcpt_025.pano_fin_nivel99 "
				+ " and pp.pano_sbg_grupo = pcpt_025.pano_fin_grupo "
				+ " and pp.pano_sbg_subgrupo = pcpt_025.pano_fin_subgrupo "
				+ " and pp.pano_sbg_item = pcpt_025.pano_fin_item " + " and exists (select 1 from basi_010 b "
				+ " where b.nivel_estrutura = pp.pano_sbg_nivel99 " + " and b.grupo_estrutura = pp.pano_sbg_grupo ";

		if (sub != null) {
			query = query + " and b.subgru_estrutura = '" + sub + "' ";
		} else {
			query = query + " and b.subgru_estrutura = pp.pano_sbg_subgrupo ";
		}

		if (item != null) {
			query = query + " and b.item_estrutura = '" + item + "' ";
		} else {
			query = query + " and b.item_estrutura = pp.pano_sbg_item";
		}

		query = query + " )) ";

		jdbcTemplate.update(query);
	}

	public void updateCorPlanejProduto(int ordemProducao, String sub, String item) {

		String setCampos = "";

		if (sub != null) {
			setCampos = " tmrp_041.subgru_estrutura = '" + sub + "'";
		}

		if (item != null) {
			if (!setCampos.isEmpty()) {
				setCampos += ", ";
			}
			setCampos = setCampos + " tmrp_041.item_estrutura = '" + item + "'";
		}

		String query = " update tmrp_041 " + " set " + setCampos + " where tmrp_041.area_producao   = 2 "
				+ " and tmrp_041.nr_pedido_ordem = " + ordemProducao + " and exists (select 1 from pcpb_020 pp "
				+ " where pp.ordem_producao    = tmrp_041.nr_pedido_ordem "
				+ " and pp.pano_sbg_nivel99  = tmrp_041.nivel_estrutura "
				+ " and pp.pano_sbg_grupo    = tmrp_041.grupo_estrutura "
				+ " and pp.pano_sbg_subgrupo = tmrp_041.subgru_estrutura "
				+ " and pp.pano_sbg_item     = tmrp_041.item_estrutura "
				+ " and exists               (select 1 from basi_010 b "
				+ " where b.nivel_estrutura  = pp.pano_sbg_nivel99 " + " and b.grupo_estrutura  = pp.pano_sbg_grupo ";

		if (sub != null) {
			query = query + " and b.subgru_estrutura  = '" + sub + "' ";
		} else {
			query = query + " and b.subgru_estrutura = pp.pano_sbg_subgrupo ";
		}

		if (item != null) {
			query = query + " and b.item_estrutura   = '" + item + "' ";
		} else {
			query = query + " and b.item_estrutura = pp.pano_sbg_item";
		}

		query = query + " )) ";

		jdbcTemplate.update(query);
	}

	public void updateCorDestinoOrdemBenef(int ordemProducao, String sub, String item) {

		String setCampos = "";

		if (sub != null) {
			setCampos = " pcpb_030.pano_subgrupo = '" + sub + "'";
		}

		if (item != null) {
			if (!setCampos.isEmpty()) {
				setCampos += ", ";
			}
			setCampos = setCampos + " pcpb_030.pano_item = '" + item + "'";
		}

		String query = " update pcpb_030 set " + setCampos + " where pcpb_030.ordem_producao = " + ordemProducao
				+ " and exists (select 1 from pcpb_020 pp " + " where pp.ordem_producao    = pcpb_030.ordem_producao "
				+ " and pp.sequencia         = pcpb_030.sequencia "
				+ " and pp.pano_sbg_nivel99  = pcpb_030.pano_nivel99 "
				+ " and pp.pano_sbg_grupo    = pcpb_030.pano_grupo "
				+ " and pp.pano_sbg_subgrupo = pcpb_030.pano_subgrupo "
				+ " and pp.pano_sbg_item     = pcpb_030.pano_item "
				+ " and exists               (select 1 from basi_010 b "
				+ " where b.nivel_estrutura  = pp.pano_sbg_nivel99 " + " and b.grupo_estrutura  = pp.pano_sbg_grupo ";

		if (sub != null) {
			query = query + " and b.subgru_estrutura  = '" + sub + "' ";
		} else {
			query = query + " and b.subgru_estrutura = pp.pano_sbg_subgrupo ";
		}

		if (item != null) {
			query = query + " and b.item_estrutura   = '" + item + "' ";
		} else {
			query = query + " and b.item_estrutura = pp.pano_sbg_item";
		}

		query = query + " )) ";

		jdbcTemplate.update(query);
	}

	public void updateCorTecidoOrdemBenef(int ordemProducao, String sub, String item) {

		String setCampos = "";

		if (sub != null) {
			setCampos = " pcpb_020.pano_sbg_subgrupo = '" + sub + "'";
		}

		if (item != null) {
			if (!setCampos.isEmpty()) {
				setCampos += ", ";
			}
			setCampos = setCampos + " pcpb_020.pano_sbg_item = '" + item + "'";
		}

		String query = " update pcpb_020 set " + setCampos + " where pcpb_020.ordem_producao = " + ordemProducao
				+ " and exists (select 1 from pcpb_020 pp " + " where pp.ordem_producao    = pcpb_020.ordem_producao "
				+ " and pp.sequencia         = pcpb_020.sequencia "
				+ " and pp.pano_sbg_nivel99  = pcpb_020.pano_sbg_nivel99 "
				+ " and pp.pano_sbg_grupo    = pcpb_020.pano_sbg_grupo "
				+ " and pp.pano_sbg_subgrupo = pcpb_020.pano_sbg_subgrupo "
				+ " and pp.pano_sbg_item     = pcpb_020.pano_sbg_item "
				+ " and exists               (select 1 from basi_010 b "
				+ " where b.nivel_estrutura  = pp.pano_sbg_nivel99 " + " and b.grupo_estrutura  = pp.pano_sbg_grupo ";

		if (sub != null) {
			query = query + " and b.subgru_estrutura  = '" + sub + "' ";
		} else {
			query = query + " and b.subgru_estrutura = pp.pano_sbg_subgrupo ";
		}

		if (item != null) {
			query = query + " and b.item_estrutura   = '" + item + "' ";
		} else {
			query = query + " and b.item_estrutura = pp.pano_sbg_item";
		}

		query = query + " )) ";

		jdbcTemplate.update(query);
	}
}
