package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.body.BodyFiltroProduto;
import br.com.live.entity.ProdutoReferencia;
import br.com.live.entity.ProdutoReferenciaCor;
import br.com.live.model.Alternativa;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.ArtigoCotas;
import br.com.live.model.ArtigoProduto;
import br.com.live.model.Colecao;
import br.com.live.model.ConsultaDadosCompEstrutura;
import br.com.live.model.ConsultaDadosEstrutura;
import br.com.live.model.ConsultaDadosFilete;
import br.com.live.model.ConsultaDadosRoteiro;
import br.com.live.model.ConsultaItemSugestaoCancelProducao;
import br.com.live.model.ConsultaTag;
import br.com.live.model.CorProduto;
import br.com.live.model.Embarque;
import br.com.live.model.LinhaProduto;
import br.com.live.model.MarcacaoRisco;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.model.Produto;
import br.com.live.model.PublicoAlvo;
import br.com.live.model.Roteiro;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class ProdutoCustom {

	private final EntityManager manager;
	private final JdbcTemplate jdbcTemplate;

	public ProdutoCustom(EntityManager manager, JdbcTemplate jdbcTemplate) {
		this.manager = manager;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProdutoReferencia> findProdutosByParameters(BodyFiltroProduto filtro) {

		String query = "select new br.com.live.entity.ProdutoReferencia (p.id, p.grupo, p.descricao, p.colecao, p.permanente) from ProdutoReferencia p ";
		String condicao = "where ";

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " colecao in " + filtro.getColecoes();
			condicao = " and ";
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + "(p.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ filtro.getSubColecoes();
			query += " and s.grupo = p.grupo ) or p.permanente = 0)";
			condicao = " and ";
		}

		query += " order by p.grupo ";

		System.out.println("Produtos - query: " + query);

		var q = manager.createQuery(query, ProdutoReferencia.class);

		return q.getResultList();
	}

	public List<CorProduto> findCoresByParameters(BodyFiltroProduto filtro) {

		List<CorProduto> cores;

		String query = "select max(rownum) id, c.item_estrutura item, c.descricao_15 descricao "
				+ " from basi_030 a, basi_010 c "
				+ " where a.nivel_estrutura = c.nivel_estrutura "
				+ " and a.referencia = c.grupo_estrutura "
				+ " and c.nivel_estrutura = '1' "
				+ " and c.item_ativo = 1 "
				+ " and c.descricao_15 <> '.' ";

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += " and a.colecao in " + filtro.getColecoes();
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += " and exists (select * from basi_632 f "
					+ " where f.cd_agrupador in " + filtro.getSubColecoes()
					+ "   and f.item_ref = c.item_estrutura) ";
		}

		query += " group by c.item_estrutura, c.descricao_15 "
				+ " order by c.item_estrutura, c.descricao_15 ";

		System.out.println("CorProdutos - query: " + query);

		try {
			cores = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CorProduto.class));
		} catch (Exception e) {
			cores = new ArrayList<CorProduto>();
		}

		return cores;
	}

	public List<CorProduto> findCoresByNivelGrupoSub(String nivel, String grupo, String sub) {

		List<CorProduto> cores;

		String query = " select rownum id, a.item_estrutura item, a.descricao_15 descricao "
				+ " from basi_010 a "
				+ " where a.nivel_estrutura = '" + nivel + "' "
				+ " and a.grupo_estrutura = '" + grupo + "' "
				+ " and a.subgru_estrutura = '" + sub + "'"
				+ " and a.item_ativo = 0 ";

		try {
			cores = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CorProduto.class));
		} catch (Exception e) {
			cores = new ArrayList<CorProduto>();
		}

		return cores;
	}

	public List<Embarque> findAllEmbarquesBasi() {

		String query = "select a.grupo_embarque as id, a.data_entrega as descricao from basi_590 a "
				+ " group by a.grupo_embarque, a.data_entrega ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Embarque.class));
	}

	public List<ProdutoReferenciaCor> findItensByParameters(BodyFiltroProduto filtro) {

		String query = "select new br.com.live.entity.ProdutoReferenciaCor (p.id, p.grupo, p.item, p.descricao, p.colecao, p.permanente, p.embarque, p.alternativaPadrao, p.roteiroPadrao, p.riscoPadrao) from ProdutoReferenciaCor p ";
		String condicao = "where ";

		if (!filtro.getReferencias().equalsIgnoreCase("")) {
			query += condicao + " p.grupo in " + filtro.getReferencias();
			condicao = " and ";
		}

		if (!filtro.getCores().equalsIgnoreCase("")) {
			query += condicao + " p.item in " + filtro.getCores();
			condicao = " and ";
		}

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " p.colecao in " + filtro.getColecoes();
			condicao = " and ";
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + " (p.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ filtro.getSubColecoes();
			query += " and s.grupo = p.grupo ";
			query += " and s.item = p.item ) or p.permanente = 0) ";
		}

		query += " order by p.grupo, p.item ";

		System.out.println("ProdutoReferenciaCor - query: " + query);

		var q = manager.createQuery(query, ProdutoReferenciaCor.class);

		return q.getResultList();
	}

	public List<ConsultaItemSugestaoCancelProducao> findItensSugestaoCancelProducaoByParameters(
			BodyFiltroProduto filtro) {

		String comando = " where ";

		String query = " select itens.grupo_estrutura grupo, itens.item_estrutura item, itens.descricao, "
				+ " itens.colecao, itens.descr_colecao descColecao, itens.sug_cancel_prod sugCancelProducao "
				+ " from ( "
				+ " select b.grupo_estrutura, b.item_estrutura, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, "
				+ "        a.colecao, c.descr_colecao, "
				+ "        nvl((select 'S' from orion_030 o "
				+ "             where o.nivel = '1' "
				+ "               and o.grupo = b.grupo_estrutura "
				+ "               and o.item = b.item_estrutura "
				+ "               and o.colecao = a.colecao),'N') sug_cancel_prod "
				+ " from basi_030 a, basi_010 b, basi_140 c "
				+ " where a.nivel_estrutura = '1' "
				+ "   and b.nivel_estrutura = a.nivel_estrutura "
				+ "   and b.grupo_estrutura = a.referencia "
				+ "   and c.colecao = a.colecao "
				+ "   and c.descricao_espanhol not like '%COLECAO PERMANENTE%' ";

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += " and a.colecao in " + filtro.getColecoes();
		}

		query += " group by b.grupo_estrutura, b.item_estrutura, a.colecao, c.descr_colecao "
				+ " union "
				+ " select b.grupo_estrutura, b.item_estrutura, max(a.descr_referencia) || ' - ' || max(b.descricao_15) descricao, "
				+ "        d.cd_agrupador colecao, e.ds_agrupador descr_colecao, "
				+ "        nvl((select 'S' from orion_030 o "
				+ "              where o.nivel = '1' "
				+ "                and o.grupo = b.grupo_estrutura "
				+ "                and o.item = b.item_estrutura "
				+ "                and o.colecao = d.cd_agrupador),'N') sug_cancel_prod "
				+ "  from basi_030 a, basi_010 b, basi_140 c, basi_632 d, basi_630 e "
				+ " where a.nivel_estrutura = '1' "
				+ "   and b.nivel_estrutura = a.nivel_estrutura "
				+ "   and b.grupo_estrutura = a.referencia "
				+ "   and c.colecao = a.colecao "
				+ "   and c.descricao_espanhol like '%COLECAO PERMANENTE%' "
				+ "   and d.grupo_ref = a.referencia "
				+ "   and d.item_ref = b.item_estrutura "
				+ "   and e.cd_agrupador = d.cd_agrupador ";

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += " and a.colecao in " + filtro.getColecoes();
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += " and d.cd_agrupador in " + filtro.getSubColecoes();
		}

		query += " group by b.grupo_estrutura, b.item_estrutura, d.cd_agrupador , e.ds_agrupador ) itens ";

		if (!filtro.getReferencias().equalsIgnoreCase("")) {
			query += comando + " itens.grupo_estrutura in " + filtro.getReferencias();
			comando = " and ";
		}

		if (!filtro.getCores().equalsIgnoreCase("")) {
			query += comando + " itens.item_estrutura in " + filtro.getCores();
		}
		query += " order by itens.grupo_estrutura, itens.item_estrutura, itens.colecao, itens.descr_colecao, itens.sug_cancel_prod ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaItemSugestaoCancelProducao.class));
	}

	public ProdutoReferenciaCor findItemByCodigo(String grupo, String item) {
		String query = "select new br.com.live.entity.ProdutoReferenciaCor (p.id, p.grupo, p.item, p.descricao, p.colecao, p.permanente, p.embarque, p.alternativaPadrao, p.roteiroPadrao, p.riscoPadrao) from ProdutoReferenciaCor p ";
		query += " where p.grupo = '" + grupo + "'";
		query += " and p.item = '" + item + "'";

		var q = manager.createQuery(query, ProdutoReferenciaCor.class);

		return q.getSingleResult();
	}

	public int findRiscoPadraoByCodigo(String grupo) {

		String query = "select b.risco_padrao from basi_030 b where b.nivel_estrutura = '1' " + " and b.referencia = '"
				+ grupo + "'";

		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	public List<Alternativa> findAlternativasByCodigo(String grupo, String item) {
		/*
		 * String query =
		 * "select max(rownum) id, b.alternativa_item alternativa, nvl(c.descricao, ' ') descricao from basi_050 b, basi_070 c"
		 * + " where b.nivel_item       = '1' " + " and c.nivel (+) = b.nivel_item "
		 * + " and c.grupo (+) = b.grupo_item " +
		 * " and c.alternativa (+) = b.alternativa_item "
		 * + " and b.grupo_item = '" + grupo + "'" + " and (b.item_item = '" + item
		 * + "' or b.item_item = '000000') "
		 * +
		 * " group by b.nivel_item, b.grupo_item, b.item_item, b.alternativa_item, c.descricao "
		 * + " order by b.alternativa_item, c.descricao ";
		 */

		String query = " select a.alternativa_produto alternativa, nvl(b.descricao,'') descricao from basi_013 a, basi_070 b "
				+ " where a.nivel_item = '1' "
				+ "  and a.grupo_item = '" + grupo + "'"
				+ "  and b.nivel (+) = a.nivel_item "
				+ "  and b.grupo (+) = a.grupo_item "
				+ "  and b.alternativa (+) = a.alternativa_produto "
				+ " group by a.alternativa_produto, b.descricao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Alternativa.class));
	}

	public List<Roteiro> findRoteirosByCodigo(String grupo, String item, int alternativa) {

		String query = "select m.numero_roteiro codigo from mqop_050 m" + " where m.nivel_estrutura  = '1' "
				+ " and m.grupo_estrutura  = '" + grupo + "'" + " and m.numero_alternati = " + alternativa
				+ " group by m.numero_roteiro" + " order by m.numero_roteiro";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Roteiro.class));
	}

	public AlternativaRoteiroPadrao findAlternativaRoteiroPadraoByCodigo(String grupo, String item) {

		String query = "select b.grupo_estrutura grupo, b.item_estrutura item, max(b.alternativa_custos) alternativa, max(b.roteiro_custos) roteiro from basi_010 b"
				+ " where b.nivel_estrutura = '1' " + " and b.grupo_estrutura = '" + grupo + "'"
				+ " and b.item_estrutura = '" + item + "'" + " group by b.grupo_estrutura, b.item_estrutura ";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(AlternativaRoteiroPadrao.class));
	}

	public List<ArtigoCotas> findAllArtigosCotas() {

		String query = "select b.artigo_cotas id, b.descr_artigo descricao from basi_295 b "
				+ " where b.nivel_estrutura = '1' " + " and b.descr_artigo <> '.' " + " order by b.artigo_cotas ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCotas.class));
	}

	public List<ArtigoProduto> findAllArtigosProduto() {

		String query = "select b.artigo id, b.descr_artigo descricao from basi_290 b"
				+ " where b.nivel_estrutura = '1' " + "   and b.descr_artigo <> '.' " + " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoProduto.class));
	}

	public List<Colecao> findAllColecoes() {

		String query = " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.descricao_espanhol not like '%COLECAO PERMANENTE%' "
				+ " and b.descricao_espanhol not like '%COLECAO ANTIGA%' " + " and b.colecao > 0 "
				+ " order by colecao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<Colecao> findAllColecoesWithPermanentes() {
		String query = " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.descricao_espanhol not like '%COLECAO ANTIGA%' "
				+ " order by b.colecao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}
	
	public List<Colecao> findColecoesWithSemColecao() {
		String query = " select colecao.id, colecao.descricao from "
				+ " ( "
				+ " select 0 id, 'SEM COLEÇÃO' descricao from dual "
				+ " union all "
				+ " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.descricao_espanhol not like '%COLECAO ANTIGA%') colecao "
				+ " order by colecao.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<Colecao> findAllColecoesPermanentes() {

		String query = " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.descricao_espanhol like '%COLECAO PERMANENTE%' " + " and b.colecao > 0 "
				+ " order by colecao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<Colecao> findAllSubColecoes() {

		String query = " select b.cd_agrupador id, b.ds_agrupador descricao from basi_630 b order by b.cd_agrupador ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<Colecao> findColecoesByCodigos(String colecoes) {

		String query = " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.colecao in (" + colecoes + ")";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<LinhaProduto> findAllLinhasProdutos() {

		String query = " select b.linha_produto id, b.descricao_linha descricao from basi_120 b "
				+ " where b.nivel_estrutura = '1' " + " order by b.linha_produto ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LinhaProduto.class));
	}

	public List<PublicoAlvo> findAllPublicosAlvos() {

		String query = " select h.codigo id, h.descricao from hdoc_001 h " + " where h.tipo = 9 "
				+ " order by h.codigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PublicoAlvo.class));
	}

	public int findSequenciaPrincipalRisco(String grupo, String item, int alternativa) {

		String query = "select nvl(basi_050.sequencia,0) "
				+ " from basi_050 "
				+ " where basi_050.nivel_item = '1' "
				+ " and basi_050.grupo_item       = '" + grupo + "'"
				+ " and (basi_050.item_item       = '" + item + "' or basi_050.item_item = '000000') "
				+ " and basi_050.alternativa_item = " + alternativa
				+ " and basi_050.tecido_principal = 1 "
				+ " and rownum = 1 "
				+ " order by basi_050.consumo desc ";

		Integer sequencia = 0;

		try {
			sequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sequencia = 0;
		}

		return (int) sequencia;
	}

	public int findMenorSeqRisco(String grupo, int alternativa) {

		String query = " select min(pcpc_200.ordem_estrutura) from pcpc_200 "
				+ " where pcpc_200.codigo_risco = 1 "
				+ " and pcpc_200.grupo_riscado = '00000' "
				+ " and pcpc_200.alternativa_item = " + alternativa
				+ " and pcpc_200.grupo_estrutura = '" + grupo + "'";

		Integer sequencia = 0;

		try {
			sequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sequencia = 0;
		}

		return (int) sequencia;
	}

	public int findQtdeMarcacoesTamanho(String grupo, String sub, int risco, int seqRisco, int alternativa) {

		int qtdeMarcacoes;

		String query = "select sum(pcpc_200.qtde_marcacoes) "
				+ " from pcpc_200 "
				+ " where pcpc_200.codigo_risco = " + risco
				+ " and pcpc_200.grupo_estrutura = '" + grupo + "' "
				+ " and pcpc_200.ordem_estrutura = " + seqRisco
				+ " and pcpc_200.alternativa_item = " + alternativa
				+ " and pcpc_200.tamanho = " + sub;

		try {
			qtdeMarcacoes = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeMarcacoes = 0;
		}

		return qtdeMarcacoes;
	}

	public List<MarcacaoRisco> findMarcacoesRisco(String grupo, int risco, int seqRisco, int alternativa) {

		String query = "select pcpc_200.grupo_estrutura grupo, pcpc_200.tamanho sub, pcpc_200.qtde_marc_proj quantidade "
				+ " from pcpc_200 "
				+ " where pcpc_200.codigo_risco = " + risco
				+ " and pcpc_200.grupo_estrutura = '" + grupo + "' "
				+ " and pcpc_200.ordem_estrutura  = " + seqRisco
				+ " and pcpc_200.alternativa_item = " + alternativa;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MarcacaoRisco.class));
	}

	public int findOrdemTamanho(String tamanho) {

		Integer ordemTamanho;

		String query = " select a.ordem_tamanho from basi_220 a where a.tamanho_ref = '" + tamanho + "'";

		try {
			ordemTamanho = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			ordemTamanho = 0;
		}

		return ordemTamanho;
	}

	public int findLoteFabricacao(String grupo, String sub) {

		int loteFabricao;

		String query = " select basi_020.lote_fabr_pecas from basi_020 "
				+ " where basi_020.basi030_nivel030 = '1' "
				+ " and basi_020.basi030_referenc = '" + grupo + "'"
				+ " and basi_020.tamanho_ref      = '" + sub + "'";

		try {
			loteFabricao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			loteFabricao = 0;
		}

		if (loteFabricao == 0)
			loteFabricao = 99999999;

		return loteFabricao;
	}

	public boolean existsItem(String grupo, String item) {

		int encontrou = 0;

		String query = " select 1 from basi_010 a "
				+ " where a.nivel_estrutura = '1' "
				+ " and a.grupo_estrutura = '" + grupo + "'"
				+ " and a.item_estrutura = '" + item + "'"
				+ " and rownum = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}
	
	public boolean isProdutoComprado(String grupo) {

		int encontrou = 0;

		String query = " select 1 from basi_030 "
				+ " where basi_030.nivel_estrutura = '1' "
				+ " and basi_030.referencia      = '" + grupo + "' "
				+ " and basi_030.comprado_fabric = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}

	public boolean existsEstrutura(String grupo, String sub, String item, int alternativa) {

		int encontrou = 0;

		String query = " select 1 from basi_050 "
				+ " where  basi_050.nivel_item = '1' "
				+ " and  basi_050.grupo_item = '" + grupo + "'"
				+ " and (basi_050.sub_item = '" + sub + "' or basi_050.sub_item  = '000') "
				+ " and (basi_050.item_item = '" + item + "' or basi_050.item_item = '000000') "
				+ " and  basi_050.alternativa_item = " + alternativa
				+ " and rownum = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}

	public boolean existsRoteiro(String nivel, String grupo, String sub, String item, int alternativa, int roteiro) {
		int encontrou = 0;

		String query = " select 1 from mqop_050 "
				+ " where mqop_050.nivel_estrutura = '" + nivel + "' "
				+ " and mqop_050.grupo_estrutura   = '" + grupo + "'"
				+ " and (mqop_050.subgru_estrutura = '" + sub + "' or mqop_050.subgru_estrutura = '000') "
				+ " and (mqop_050.item_estrutura   = '" + item + "' or mqop_050.item_estrutura   = '000000') "
				+ " and mqop_050.numero_alternati  = " + alternativa
				+ " and mqop_050.numero_roteiro    = " + roteiro
				+ " and rownum = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}

	public boolean existsRoteiroAltRotMaiorZero(String nivel, String grupo, String sub, String item, int alternativa,
			int roteiro) {
		if ((alternativa == 0) || (roteiro == 0))
			return true;
		return existsRoteiro(nivel, grupo, sub, item, alternativa, roteiro);
	}

	public boolean roteiroSequenciado(String grupo, String sub, String item, int alternativa, int roteiro) {
		int encontrou = 0;

		String query = " select 1 from mqop_050 "
				+ " where mqop_050.nivel_estrutura = '1' "
				+ " and mqop_050.grupo_estrutura   = '" + grupo + "'"
				+ " and (mqop_050.subgru_estrutura = '" + sub + "' or mqop_050.subgru_estrutura = '000') "
				+ " and (mqop_050.item_estrutura   = '" + item + "' or mqop_050.item_estrutura   = '000000') "
				+ " and mqop_050.numero_alternati  = " + alternativa
				+ " and mqop_050.numero_roteiro    = " + roteiro
				+ " and mqop_050.sequencia_estagio = 0 "
				+ " and rownum = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return !(encontrou == 1);
	}

	private String[] getCodProdutoCadRoteiro(String grupo, String sub, String item, int alternativa, int roteiro) {

		String[] codProdutoRoteiro = new String[3];

		String grupoRoteiro = grupo;
		String subRoteiro = sub;
		String itemRoteiro = item;

		String query = " select 1 from mqop_050 "
				+ " where mqop_050.nivel_estrutura = '1' "
				+ " and mqop_050.grupo_estrutura  = '" + grupoRoteiro + "'"
				+ " and mqop_050.subgru_estrutura = '" + subRoteiro + "'"
				+ " and mqop_050.item_estrutura   = '" + itemRoteiro + "'"
				+ " and mqop_050.numero_alternati = " + alternativa
				+ " and mqop_050.numero_roteiro   = " + roteiro
				+ " and rownum = 1 ";

		try {
			jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {

			itemRoteiro = "000000";

			query = " select 1 from mqop_050 "
					+ " where mqop_050.nivel_estrutura = '1' "
					+ " and mqop_050.grupo_estrutura  = '" + grupoRoteiro + "'"
					+ " and mqop_050.subgru_estrutura = '" + subRoteiro + "'"
					+ " and mqop_050.item_estrutura   = '" + itemRoteiro + "'"
					+ " and mqop_050.numero_alternati = " + alternativa
					+ " and mqop_050.numero_roteiro   = " + roteiro
					+ " and rownum = 1 ";

			try {
				jdbcTemplate.queryForObject(query, Integer.class);
			} catch (Exception f) {

				subRoteiro = "000";
				itemRoteiro = item;

				query = " select 1 from mqop_050 "
						+ " where mqop_050.nivel_estrutura = '1' "
						+ " and mqop_050.grupo_estrutura  = '" + grupoRoteiro + "'"
						+ " and mqop_050.subgru_estrutura = '" + subRoteiro + "'"
						+ " and mqop_050.item_estrutura   = '" + itemRoteiro + "'"
						+ " and mqop_050.numero_alternati = " + alternativa
						+ " and mqop_050.numero_roteiro   = " + roteiro
						+ " and rownum = 1 ";

				try {
					jdbcTemplate.queryForObject(query, Integer.class);
				} catch (Exception g) {
					itemRoteiro = "000000";
				}
			}
		}

		codProdutoRoteiro[0] = grupoRoteiro;
		codProdutoRoteiro[1] = subRoteiro;
		codProdutoRoteiro[2] = itemRoteiro;

		return codProdutoRoteiro;
	}

	public List<ConsultaDadosRoteiro> findDadosRoteiro(String grupo, String sub, String item, int alternativa,
			int roteiro) {

		String[] strCodProdCadRoteiro = getCodProdutoCadRoteiro(grupo, sub, item, alternativa, roteiro);
		String grupoRot = strCodProdCadRoteiro[0];
		String subRot = strCodProdCadRoteiro[1];
		String itemRot = strCodProdCadRoteiro[2];

		String query = " select mqop_050.codigo_estagio estagio, mqop_050.codigo_operacao operacao, "
				+ " mqop_050.centro_custo centroCusto, mqop_050.minutos, "
				+ " mqop_050.codigo_familia familia, mqop_050.seq_operacao seqOperacao, "
				+ " mqop_050.sequencia_estagio seqEstagio, mqop_050.estagio_depende estagioDepende, "
				+ " mqop_040.pede_produto pedeProduto, nvl((select 1 from mqop_045 "
				+ " where mqop_045.codigo_operacao = mqop_050.codigo_operacao),0) tipoOperCmc "
				+ " from mqop_040, mqop_050 "
				+ " where mqop_040.codigo_operacao  = mqop_050.codigo_operacao "
				+ " and mqop_050.nivel_estrutura = '1' "
				+ " and mqop_050.grupo_estrutura = '" + grupoRot + "'"
				+ " and mqop_050.subgru_estrutura = '" + subRot + "'"
				+ " and mqop_050.item_estrutura = '" + itemRot + "'"
				+ " and mqop_050.numero_alternati = " + alternativa
				+ " and mqop_050.numero_roteiro = " + roteiro
				+ " order by mqop_050.seq_operacao ASC, "
				+ " mqop_050.sequencia_estagio ASC, "
				+ " mqop_050.estagio_depende ASC, "
				+ " mqop_050.codigo_estagio ASC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDadosRoteiro.class));
	}

	public double findTempoCostura(String grupo, String sub, String item, int alternativa, int roteiro) {

		String query = " select nvl(sum(m.minutos_homem),0) minutos from mqop_050 m "
				+ " where m.nivel_estrutura = '1' "
				+ " and m.grupo_estrutura = '" + grupo + "'"
				+ " and (m.subgru_estrutura = '" + sub + "' or m.subgru_estrutura = '000') "
				+ " and (m.item_estrutura = '" + item + "' or m.item_estrutura = '000000') "
				+ " and m.numero_alternati = " + alternativa
				+ " and m.numero_roteiro = " + roteiro
				+ " and m.codigo_estagio = 20 "; // Estágio de Costura

		return jdbcTemplate.queryForObject(query, Double.class);
	}

	public List<ConsultaDadosEstrutura> findDadosEstrutura(String grupo, String sub, String item, int alternativa) {

		String query = " select basi_050.sequencia, basi_050.qtde_camadas qtdeCamadas, "
				+ " basi_050.sub_item subItem, basi_050.item_item itemItem, "
				+ " basi_050.nivel_comp nivelComp, basi_050.grupo_comp grupoComp, "
				+ " basi_050.sub_comp subComp, basi_050.item_comp itemComp, "
				+ " basi_050.consumo, basi_050.alternativa_comp alternativaComp, "
				+ " basi_050.percent_perdas percPerdas "
				+ " from basi_050 "
				+ " where  basi_050.nivel_item = '1' "
				+ " and basi_050.grupo_item = '" + grupo + "'"
				+ " and (basi_050.sub_item = '" + sub + "' or basi_050.sub_item = '000') "
				+ " and (basi_050.item_item = '" + item + "' or basi_050.item_item = '000000') "
				+ " and basi_050.alternativa_item = " + alternativa
				+ " and basi_050.nivel_comp       = '2' "
				+ " order by basi_050.sequencia ASC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDadosEstrutura.class));
	}

	public ConsultaDadosCompEstrutura findDadosComponenteEstrutura(String grupo, String sub, String item, int sequencia,
			int alternativa) {

		ConsultaDadosCompEstrutura dadosCompEstrutura = null;

		String query = " select basi_040.sub_comp sub, basi_040.consumo, basi_040.item_comp item, basi_040.alternativa_comp alternativa"
				+ " from basi_040 "
				+ " where basi_040.nivel_item = '1' "
				+ " and basi_040.grupo_item = '" + grupo + "'"
				+ " and basi_040.sub_item = '" + sub + "'"
				+ " and basi_040.item_item = '" + item + "'"
				+ " and basi_040.sequencia = " + sequencia
				+ " and basi_040.alternativa_item = " + alternativa
				+ " and rownum = 1 ";

		try {
			dadosCompEstrutura = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(ConsultaDadosCompEstrutura.class));
		} catch (Exception e) {
			dadosCompEstrutura = new ConsultaDadosCompEstrutura();
		}

		return dadosCompEstrutura;
	}

	public ConsultaDadosFilete findDadosFileteEstrutura(String grupo, String sub, String item, int sequencia,
			int alternativa) {

		ConsultaDadosFilete dadosFilete = null;

		String query = " select basi_060.tipo_corte_peca tipoCorte, basi_060.comprimento_debrum comprimentoFilete, basi_060.largura_debrum larguraFilete"
				+ " from basi_060 "
				+ " where  basi_060.grupo_estrutura = '" + grupo + "'"
				+ " and (basi_060.subgru_estrutura = '" + sub + "' or basi_060.subgru_estrutura = '000') "
				+ " and (basi_060.item_estrutura = '" + item + "' or basi_060.item_estrutura   = '000000') "
				+ " and  basi_060.alternativa_item = " + alternativa
				+ " and  basi_060.sequencia = " + sequencia
				+ " and rownum = 1 ";

		try {
			dadosFilete = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(ConsultaDadosFilete.class));
		} catch (Exception e) {
			dadosFilete = new ConsultaDadosFilete();
		}

		return dadosFilete;
	}

	public ConsultaDadosFilete findDadosFileteRisco(String grupo, int risco, int sequencia, int alternativa) {

		ConsultaDadosFilete dadosFilete = null;

		String query = " select pcpc_200.largura larguraRisco"
				+ " from pcpc_200 "
				+ " where pcpc_200.codigo_risco = " + risco
				+ " and pcpc_200.grupo_estrutura = '" + grupo + "'"
				+ " and pcpc_200.alternativa_item = " + alternativa
				+ " and pcpc_200.ordem_estrutura = " + sequencia
				+ " and pcpc_200.grupo_riscado = '00000' "
				+ " and rownum = 1 ";

		try {
			dadosFilete = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(ConsultaDadosFilete.class));
		} catch (Exception e) {
			dadosFilete = new ConsultaDadosFilete();
		}

		return dadosFilete;
	}

	public ConsultaDadosFilete findDadosFileteTecidos(String grupo, String sub) {

		ConsultaDadosFilete dadosFilete = null;

		String query = " select basi_020.largura_1 larguraTecido, basi_020.tubular_aberto tubularAberto"
				+ " from basi_020 "
				+ " where basi_020.basi030_nivel030 = '1' "
				+ " and basi_020.basi030_referenc = '" + grupo + "'"
				+ " and basi_020.tamanho_ref      = '" + sub + "'";

		try {
			dadosFilete = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(ConsultaDadosFilete.class));
		} catch (Exception e) {
			dadosFilete = new ConsultaDadosFilete();
		}

		return dadosFilete;
	}

	public String findObservacaoFichaTecnica(String grupo) {

		String observacao;

		String query = " select a.descricao "
				+ " from basi_095 a "
				+ " where a.nivel_estrutura = '1' "
				+ " and a.grupo_estrutura = '" + grupo + "' "
				+ " and a.tipo_comentario = 1 "
				+ " and a.data_historico = (select max(b.data_historico) "
				+ " from basi_095 b "
				+ " where b.nivel_estrutura = a.nivel_estrutura "
				+ " and b.grupo_estrutura = a.grupo_estrutura "
				+ " and b.tipo_comentario = 1) ";

		try {
			observacao = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			observacao = "";
		}

		return observacao;
	}

	public Produto findProduto(String nivel, String grupo, String sub, String item) {

		Produto produto;

		String query = " select a.nivel_estrutura nivel, a.grupo_estrutura grupo, a.subgru_estrutura sub, a.item_estrutura item, a.narrativa, b.unidade_medida unidade, nvl(c.ordem_tamanho,0) seqTamanho"
				+ " from basi_010 a, basi_030 b, basi_220 c "
				+ " where a.nivel_estrutura = '" + nivel + "' "
				+ " and a.grupo_estrutura = '" + grupo + "' "
				+ " and a.subgru_estrutura = '" + sub + "' "
				+ " and a.item_estrutura = '" + item + "' "
				+ " and b.nivel_estrutura = a.nivel_estrutura "
				+ " and b.referencia = a.grupo_estrutura "
				+ " and c.tamanho_ref (+) = a.subgru_estrutura ";

		try {
			produto = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produto = new Produto();
			;
		}

		return produto;
	}

	public List<Produto> findProdutos(String nivel, String grupo, String sub, String item) {

		List<Produto> produtos;
		
		if (sub == null) sub = "";
		if (item == null) item = "";
		
		String query = " select a.nivel_estrutura nivel, a.grupo_estrutura grupo, a.subgru_estrutura sub, a.item_estrutura item, a.narrativa, b.unidade_medida unidade, nvl(c.ordem_tamanho,0) seqTamanho"
				+ " from basi_010 a, basi_030 b, basi_220 c "
				+ " where a.nivel_estrutura = '" + nivel + "' "
				+ " and a.grupo_estrutura = '" + grupo + "' ";				
				if (!sub.equalsIgnoreCase("")) query += " and a.subgru_estrutura = '" + sub + "' "; 
				if (!item.equalsIgnoreCase("")) query += " and a.item_estrutura = '" + item + "' ";				
				query += " and b.nivel_estrutura = a.nivel_estrutura "
				+ " and b.referencia = a.grupo_estrutura "
				+ " and c.tamanho_ref (+) = a.subgru_estrutura "
				+ " order by a.nivel_estrutura , a.grupo_estrutura , c.ordem_tamanho";

		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto>();
		}

		return produtos;
	}
	
	public List<Produto> findProdutosComRoteiroByNiveis(String niveis) {

		List<Produto> produtos;

		String query = " select b.basi030_nivel030 nivel, b.basi030_referenc grupo, b.tamanho_ref sub, a.descr_referencia || ' ' || b.descr_tam_refer narrativa "
				+ " from basi_030 a, basi_020 b "
				+ " where a.nivel_estrutura in ('" + niveis + "') "
				+ " and a.nivel_estrutura = b.basi030_nivel030 "
				+ " and a.referencia = b.basi030_referenc "
				+ " and exists (select 1 from mqop_050 m "
				+ " where m.nivel_estrutura = a.nivel_estrutura "
				+ " and m.grupo_estrutura = a.referencia "
				+ " and (m.subgru_estrutura = b.tamanho_ref or m.subgru_estrutura = '000')) "
				+ " and exists (select 1 from basi_010 c "
				+ " where c.nivel_estrutura = b.basi030_nivel030 "
				+ " and c.grupo_estrutura = b.basi030_referenc "
				+ " and c.subgru_estrutura = b.tamanho_ref "
				+ " and c.item_ativo = 0) ";

		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto>();
		}

		return produtos;
	}

	public List<NecessidadeTecidos> calcularNecessidadeTecido(String grupo, String sub, String item, int alternativa,
			int quantidade) {

		List<NecessidadeTecidos> tecidos = new ArrayList<NecessidadeTecidos>();

		String subTecido = "";
		String itemTecido = "";
		double consumoTecido = 0.0;
		double qtdeKgProg = 0.0;
		double metrosTecido = 0.0;
		double larguraRisco = 0.0;
		double larguraFilete = 0.0;
		double metrosOrdem = 0.0;
		double qtdeTotMetrosTecido = 0.0;
		double tirasLargura = 0.0;
		double qtdePerdas = 0.0;

		int riscoPadrao = findRiscoPadraoByCodigo(grupo);

		ConsultaDadosCompEstrutura dadosComponente;
		ConsultaDadosFilete dadosFileteEstrutura;
		ConsultaDadosFilete dadosFileteRisco;
		ConsultaDadosFilete dadosFileteTecido;

		List<ConsultaDadosEstrutura> listaDadosEstrutura = findDadosEstrutura(grupo, sub, item, alternativa);

		for (ConsultaDadosEstrutura dadosEstrutura : listaDadosEstrutura) {
			subTecido = dadosEstrutura.subComp;
			itemTecido = dadosEstrutura.itemComp;
			consumoTecido = dadosEstrutura.consumo;

			if ((subTecido.equalsIgnoreCase("000")) || (consumoTecido == 0.000000)) {
				dadosComponente = findDadosComponenteEstrutura(grupo, sub, dadosEstrutura.itemItem,
						dadosEstrutura.sequencia, alternativa);
				subTecido = dadosComponente.sub;
				consumoTecido = dadosComponente.consumo;
			}

			if (itemTecido.equalsIgnoreCase("000000")) {
				dadosComponente = findDadosComponenteEstrutura(grupo, dadosEstrutura.subItem, item,
						dadosEstrutura.sequencia, alternativa);
				itemTecido = dadosComponente.item;
			}

			qtdeKgProg = (consumoTecido * (float) quantidade);
			metrosTecido = 0.0;
			qtdeTotMetrosTecido = 0.0;

			dadosFileteEstrutura = findDadosFileteEstrutura(grupo, sub, item, dadosEstrutura.sequencia, alternativa);

			if (dadosFileteEstrutura.tipoCorte == 2) {
				dadosFileteRisco = findDadosFileteRisco(grupo, riscoPadrao, dadosEstrutura.sequencia, alternativa);

				larguraFilete = dadosFileteEstrutura.larguraFilete;
				larguraRisco = dadosFileteRisco.larguraRisco;

				if (larguraRisco == 0.000) {
					dadosFileteTecido = findDadosFileteTecidos(dadosEstrutura.grupoComp, subTecido);

					if (dadosFileteTecido.tubularAberto == 2)
						larguraRisco = dadosFileteTecido.larguraTecido * 2;
				}

				if (larguraRisco == 0.000)
					larguraRisco = 1.000;
				if (larguraFilete == 0.000)
					larguraFilete = 1.000;

				metrosOrdem = ((double) quantidade * dadosFileteEstrutura.comprimentoFilete);

				tirasLargura = larguraRisco / larguraFilete;

				if (tirasLargura == 0.000)
					tirasLargura = 1.000;

				metrosTecido = metrosOrdem / tirasLargura;

				if (dadosEstrutura.percPerdas > 0.000) {
					qtdePerdas = (dadosEstrutura.percPerdas * metrosTecido) / 100;
					metrosTecido += qtdePerdas;
				}

				qtdeTotMetrosTecido += metrosTecido;
			}

			tecidos.add(new NecessidadeTecidos(dadosEstrutura.sequencia, dadosEstrutura.nivelComp,
					dadosEstrutura.grupoComp, subTecido, itemTecido, qtdeKgProg, qtdeTotMetrosTecido, consumoTecido));
		}
		return tecidos;
	}

	public List<Produto> findTecidosSubstitutos(String nivel, String grupo, String sub, String item) {

		List<Produto> substitutos;

		String query = " select substitutos.nivel, substitutos.grupo_subst grupo, substitutos.sub_subst sub, substitutos.item "
				+ " from (select a.basi030_nivel030 nivel, "
				+ " a.basi030_referenc grupo, "
				+ " a.tamanho_ref sub, "
				+ " a.grupo_agrupador grupo_subst, "
				+ " a.sub_agrupador sub_subst, "
				+ " b.item_estrutura item "
				+ " from basi_020 a, basi_010 b "
				+ " where a.basi030_nivel030 in ('2','4') "
				+ " and a.basi030_referenc <> a.grupo_agrupador "
				+ " and b.nivel_estrutura = a.basi030_nivel030 "
				+ " and b.grupo_estrutura = a.basi030_referenc "
				+ " and b.subgru_estrutura = a.tamanho_ref "
				+ " union "
				+ " select a.basi030_nivel030 nivel, "
				+ " a.grupo_agrupador grupo, "
				+ " a.sub_agrupador sub, "
				+ " a.basi030_referenc grupo_subst, "
				+ " a.tamanho_ref sub_subst, "
				+ " b.item_estrutura item "
				+ " from basi_020 a, basi_010 b "
				+ " where a.basi030_nivel030 in ('2','4') "
				+ " and a.basi030_referenc <> a.grupo_agrupador "
				+ " and b.nivel_estrutura = a.basi030_nivel030 "
				+ " and b.grupo_estrutura = a.grupo_agrupador "
				+ " and b.subgru_estrutura = a.sub_agrupador "
				+ " ) substitutos "
				+ " where substitutos.nivel = '" + nivel + "'"
				+ " and substitutos.grupo = '" + grupo + "'"
				+ " and substitutos.sub = '" + sub + "'"
				+ " and substitutos.item = '" + item + "'"
				+ " and (substitutos.grupo_subst <> '00000' and substitutos.sub_subst <> '000') "
				+ " and exists (select 1 from basi_010 b "
				+ " where b.nivel_estrutura = substitutos.nivel "
				+ " and b.grupo_estrutura = substitutos.grupo_subst "
				+ " and b.subgru_estrutura = substitutos.sub_subst "
				+ " and b.item_estrutura = substitutos.item) ";

		// System.out.println(query);

		try {
			substitutos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			substitutos = new ArrayList<Produto>();
		}

		return substitutos;
	}

	public List<Produto> findProdutosByColecaoAndArtigo(String colecoes, String artigos, int totalRegistros) {

		List<Produto> produtos;

		String query = " select b.grupo_estrutura || '.' || b.item_estrutura || '.' || b.subgru_estrutura id, b.grupo_estrutura grupo, b.subgru_estrutura sub, b.item_estrutura item, 1 quantCesto from basi_010 b, basi_030 c, basi_290 d, basi_220 e "
				+ " where c.nivel_estrutura = b.nivel_estrutura "
				+ " and c.referencia = b.grupo_estrutura "
				+ " and d.nivel_estrutura = b.nivel_estrutura "
				+ " and d.artigo = c.artigo "
				+ " and e.tamanho_ref = b.subgru_estrutura "
				+ " and not exists (select 1 from estq_110 f "
				+ "                where f.nivel = b.nivel_estrutura "
				+ "                and f.grupo = b.grupo_estrutura "
				+ "                and f.subgrupo = b.subgru_estrutura "
				+ "                and f.item = b.item_estrutura) "
				+ " and rownum < " + totalRegistros;

		if (!artigos.equals("")) {
			query = query + " and d.artigo in (" + artigos + ") ";
		}

		if (!colecoes.equals("")) {
			query = query + " and c.colecao in (" + colecoes + ") ";
		}

		query = query + " order by b.grupo_estrutura, b.item_estrutura, e.ordem_tamanho ";

		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto>();
		}

		return produtos;
	}

	public boolean existeProduto(String nivel, String grupo, String sub, String item) {
		int encontrou = 0;

		String query = " select 1 from basi_010 a "
				+ " where a.nivel_estrutura = '" + nivel + "' "
				+ " and a.grupo_estrutura = '" + grupo + "' "
				+ " and a.subgru_estrutura = '" + sub + "' "
				+ " and a.item_estrutura = '" + item + "' ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}

	public List<ConteudoChaveNumerica> findCategorias() {
		List<ConteudoChaveNumerica> dadosCategoria = null;

		String query = " select b.seq_escolha value, b.seq_escolha || ' - ' || b.conteudo_escolha label from basi_542 b "
				+ " where b.familia_atributo = '000001' "
				+ " and b.codigo_atributo = 5 "
				+ " and b.seq_escolha > 0 ";

		try {
			dadosCategoria = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
		} catch (Exception e) {
			dadosCategoria = new ArrayList<ConteudoChaveNumerica>();
		}

		return dadosCategoria;
	}

	public ConsultaTag findDadosTagByTagAndDeposito(int periodo, int ordemProducao, int pacote, int sequencia) {

		ConsultaTag dadosTag = null;

		String query = " select b.nivel, b.grupo, b.subgrupo, b.item, b.estoque_tag situacao, b.endereco, c.deposito, c.qtde_empenhada quantEmpenhada, c.qtde_sugerida quantSugerida,c.qtde_estoque_atu quantEstoque, c.qtde_estoque_atu - c.qtde_empenhada saldo from pcpc_330 b, estq_040 c "
				+ " where b.periodo_producao = " + periodo
				+ " and b.ordem_producao = " + ordemProducao
				+ " and b.ordem_confeccao = " + pacote
				+ " and b.sequencia = " + sequencia
				+ " and c.cditem_nivel99 = b.nivel "
				+ " and c.cditem_grupo = b.grupo "
				+ " and c.cditem_subgrupo = b.subgrupo "
				+ " and c.cditem_item = b.item "
				+ " and c.deposito = b.deposito ";
		try {
			dadosTag = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
		} catch (Exception e) {
			dadosTag = new ConsultaTag();
		}

		return dadosTag;
	}

	public ConsultaTag findDadosTagByReferencia(int deposito, String nivel, String grupo, String subGrupo, String item) {
		String query = " select c.cditem_nivel99 nivel, c.cditem_grupo grupo, c.cditem_subgrupo subGrupo, c.cditem_item item, c.deposito, c.qtde_empenhada quantEmpenhada, c.qtde_sugerida quantSugerida,c.qtde_estoque_atu quantEstoque, c.qtde_estoque_atu - c.qtde_empenhada saldo from estq_040 c "
				+ " where c.cditem_nivel99 = '" + nivel + "' "
				+ " and c.cditem_grupo = '" + grupo + "' "
				+ " and c.cditem_subgrupo = '" + subGrupo + "' "
				+ " and c.cditem_item = '" + item + "' "
				+ " and c.deposito = " + deposito;
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
	}
	
	public List<ConteudoChaveAlfaNum> findTamanhosByGrupo(String nivel, String grupo){
		String query = " select a.subgru_estrutura value, a.subgru_estrutura label from basi_010 a "
				+ " where a.nivel_estrutura = '" + nivel + "'"
				+ " and a.grupo_estrutura = '" + grupo + "' "
				+ " group by a.subgru_estrutura "
				+ " order by a.subgru_estrutura desc ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findCoresByTamanho(String nivel, String grupo, String subGrupo){
		String query = " select a.item_estrutura value, a.item_estrutura label from basi_010 a "
				+ " where a.nivel_estrutura = '" + nivel + "' "
				+ " and a.grupo_estrutura = '" + grupo + "' " 
				+ " and a.subgru_estrutura = '" + subGrupo + "' "
				+ " group by a.item_estrutura "
				+ " order by a.item_estrutura desc ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findProdutosByLeitorProduto(String leitor) {
		List<ConteudoChaveAlfaNum> produtos;
				
		String query = " select a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura  value, "
				+ " a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura || ' - ' || a.narrativa label "
		+ " from basi_010 a " 
		+ " where a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura  || ' - ' || a.narrativa like '%" + leitor + "%' "
		+ " and a.item_ativo = 0 "
		+ " and rownum <= 500 ";
		
		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		} catch (Exception e) {
			produtos = new ArrayList<ConteudoChaveAlfaNum>();
		}
		
		return produtos;
	}
	
	public List<ConteudoChaveAlfaNum> findGruposByLeitorProduto(String leitor) {
		List<ConteudoChaveAlfaNum> grupos;
				
		String query = " select a.referencia value, a.referencia || ' - ' || a.descr_referencia label from basi_030 a "
				+ " where a.referencia || a.descr_referencia like '%" + leitor + "%' "
				+ " and rownum <= 500 ";
		
		try {
			grupos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		} catch (Exception e) {
			grupos = new ArrayList<ConteudoChaveAlfaNum>();
		}
		return grupos;
	}
	
	public void atualizaComplemento(String nivel, String grupo, String subGrupo, String item, int complemento) {
		String query = " update basi_010 "
				+ " set complemento = ? "
				+ " where basi_010.nivel_estrutura = ? "
				+ " and basi_010.grupo_estrutura = ? "
				+ " and basi_010.subgru_estrutura = ? "
				+ " and basi_010.item_estrutura = ? ";
		
		jdbcTemplate.update(query, Integer.toString(complemento), nivel, grupo, subGrupo, item);
	}
	
	public void atualizaComplementoByColecao(int colecao, int complemento) {
		String query = " update basi_010 y "
				+ " set complemento = ? "
				+ " where exists ( select 1 from (select v.nivel, v.grupo, v.sub, v.item, v.colecao from orion_vi_item_colecao v) itens "
				+ "                      where itens.colecao = ? "
				+ "                      and itens.nivel = '1' "
				+ "                      and itens.grupo = y.grupo_estrutura "
				+ "                      and itens.sub = y.subgru_estrutura "
				+ "                      and itens.item = y.item_estrutura "
				+ "                      )";
		jdbcTemplate.update(query, Integer.toString(complemento), colecao);
	}
	
	public int findTotalProdutosInColecao(int colecao) {
		int totalProdutos = 0;
		
		String query = " select count(*) from basi_010 y "
				+ " where exists ( select 1 from (select v.nivel, v.grupo, v.sub, v.item, v.colecao from orion_vi_item_colecao v) itens "
				+ "                      where itens.colecao = " + colecao
				+ "                      and itens.nivel = '1' "
				+ "                      and itens.grupo = y.grupo_estrutura "
				+ "                      and itens.sub = y.subgru_estrutura "
				+ "                      and itens.item = y.item_estrutura "
				+ "                      ) ";
		try {
			totalProdutos = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			totalProdutos = 0;
		}
		return totalProdutos;
	}
	
	public String findNarrativaProduto(String nivel, String grupo, String sub, String item) {
		String narrativa = "";
		
		String query = " select a.narrativa from basi_010 a "
				+ " where a.nivel_estrutura = '" + nivel + "' "
				+ " and a.grupo_estrutura = '" + grupo + "' "
				+ " and a.subgru_estrutura = '" + sub + "' "
				+ " and a.item_estrutura = '" + item + "' ";
		try {
			narrativa = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			narrativa = "";
		}
		return narrativa;
	}

	public List<String> obterListaCaracteristicasPorProdutoCompleto(String nivel, String grupo, String subGrupo, String item) {
		List<String> caracteristicasProd = null;

		String queryProdutoCompleto = " select b.desc_carac caracteristica from basi_770 a, basi_760 b  "
				+ " where a.nivel = '" + nivel + "' "
				+ " and a.grupo = '" + grupo + "' "
				+ " and a.sub_grupo = '" + subGrupo + "' "
				+ " and a.item = '" + item + "' "
				+ " and b.id_carac = a.id_carac ";
		try {
			caracteristicasProd = jdbcTemplate.queryForList(queryProdutoCompleto, String.class);
		} catch (Exception e) {
			caracteristicasProd = new ArrayList<>();
		}
		return caracteristicasProd;
	}

	public List<String> obterListaCaracteristicasPorCorSemTamanho(String nivel, String grupo, String item) {
		List<String> caracteristicasProd = null;

		String queryProdutoCompleto = " select b.desc_carac caracteristica from basi_770 a, basi_760 b  "
				+ " where a.nivel = '" + nivel + "' "
				+ " and a.grupo = '" + grupo + "' "
				+ " and a.sub_grupo = '000' "
				+ " and a.item = '" + item + "' "
				+ " and b.id_carac = a.id_carac ";
		try {
			caracteristicasProd = jdbcTemplate.queryForList(queryProdutoCompleto, String.class);
		} catch (Exception e) {
			caracteristicasProd = new ArrayList<>();
		}
		return caracteristicasProd;
	}

	public List<String> obterListaCaracteristicasPorTamanho(String nivel, String grupo, String subGrupo) {
		List<String> caracteristicasProd = null;

		String queryProdutoCompleto = " select b.desc_carac caracteristica from basi_770 a, basi_760 b  "
				+ " where a.nivel = '" + nivel + "' "
				+ " and a.grupo = '" + grupo + "' "
				+ " and a.sub_grupo = '" + subGrupo + "' "
				+ " and a.item = '000000' "
				+ " and b.id_carac = a.id_carac ";
		try {
			caracteristicasProd = jdbcTemplate.queryForList(queryProdutoCompleto, String.class);
		} catch (Exception e) {
			caracteristicasProd = new ArrayList<>();
		}
		return caracteristicasProd;
	}

	public List<String> obterListaCaracteristicasPorReferencia(String nivel, String grupo) {
		List<String> caracteristicasProd = null;

		String queryProdutoCompleto = " select b.desc_carac caracteristica from basi_770 a, basi_760 b  "
				+ " where a.nivel = '" + nivel + "' "
				+ " and a.grupo = '" + grupo + "' "
				+ " and a.sub_grupo = '000' "
				+ " and a.item = '000000' "
				+ " and b.id_carac = a.id_carac ";

		try {
			caracteristicasProd = jdbcTemplate.queryForList(queryProdutoCompleto, String.class);
		} catch (Exception e) {
			caracteristicasProd = new ArrayList<>();
		}
		return caracteristicasProd;
	}

}
