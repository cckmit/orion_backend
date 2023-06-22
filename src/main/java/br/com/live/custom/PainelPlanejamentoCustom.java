package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaPainelPlanejamento;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class PainelPlanejamentoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public PainelPlanejamentoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveNumerica> findAllColecaoWithPermanentes() {
		
		String query = " SELECT b.colecao value, b.colecao || ' - ' || b.descr_colecao label FROM basi_140 b "
				+ "	   WHERE b.descricao_espanhol NOT LIKE '%COLECAO ANTIGA%' "
				+ "	   ORDER BY b.colecao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllSubColecoes() {

		String query = " SELECT b.cd_agrupador value, b.cd_agrupador || ' - ' || b.ds_agrupador label FROM basi_630 b ORDER BY b.cd_agrupador ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllArtigoCotas() {
		
		String query = " SELECT e.artigo_cotas value, e.artigo_cotas || ' - ' || e.descr_artigo label FROM basi_295 e ORDER BY e.artigo_cotas ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllOrdensProducao(int ordemProducao) {
		
		String query = " SELECT a.ordem_producao value, a.ordem_producao label "
				+ "	FROM pcpc_020 a "
				+ "	WHERE a.ordem_producao LIKE '%" + ordemProducao + "%'"
				+ "	AND a.cod_cancelamento = 0 ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConteudoChaveNumerica> findAllLinhaProduto() {
		
		String query = " SELECT s.linha_produto value, s.linha_produto || ' - ' || s.descricao_linha label FROM basi_120 s ORDER BY s.linha_produto ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllArtigo() {
		
		String query = " SELECT t.artigo value, t.artigo || ' - ' || t.descr_artigo label FROM basi_290 t ORDER BY t.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllContaEstoque() {
		
		String query = " SELECT d.conta_estoque value, d.conta_estoque || ' - ' || d.descr_ct_estoque label FROM basi_150 d ORDER BY d.conta_estoque ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllPublicoAlvo() {
		
		String query = " SELECT x.codigo value, x.codigo || ' - ' || x.descricao label FROM hdoc_001 x WHERE x.tipo = 9 ORDER BY x.codigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllSegmento() {
		
		String query = " SELECT c.codigo value, c.codigo || ' - ' || c.descricao label FROM hdoc_001 c WHERE c.tipo = 10 ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllFaixaEtaria() {
		
		String query = " SELECT c.codigo value, c.codigo || ' - ' || c.descricao label FROM hdoc_001 c WHERE c.tipo = 516 ORDER BY c.codigo";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllComplemento() {
		
		String query = " SELECT e.complemento value, e.complemento label FROM basi_010 e WHERE e.nivel_estrutura = 1 AND e.complemento IS NOT null AND e.complemento <> ' ' "
				+ "    GROUP BY e.complemento "
				+ "    ORDER BY e.complemento ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveNumerica> findAllDepositos() {
		
		String query = " select i.codigo_deposito value, i.codigo_deposito || ' - ' || i.descricao label from basi_205 i where i.descricao NOT LIKE '%(IN)%' ORDER BY i.codigo_deposito";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllPeriodosEmbarque() {
		
		String query = " SELECT 'GRUPO: ' || TO_CHAR(a.data_entrega, 'DD/MM') || '-BLOCO: ' || SUBSTR(a.grupo_embarque, LENGTH(a.grupo_embarque) - 1) value, "
				+ "       'GRUPO: ' || TO_CHAR(a.data_entrega, 'DD/MM') || '-BLOCO: ' || SUBSTR(a.grupo_embarque, LENGTH(a.grupo_embarque) - 1) label "
				+ "		FROM basi_590 a "
				+ "		GROUP BY a.data_entrega, a.grupo_embarque ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveNumerica> findAllPeriodosProducao() {
		
		String query = " SELECT a.periodo_producao value, a.periodo_producao || ' - Dê: ' || TO_CHAR(a.data_ini_periodo, 'DD/MM/YYYY') || ' Até: ' || TO_CHAR(a.data_fim_periodo, 'DD/MM/YYYY') label "
				+ "		FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "		AND a.codigo_empresa = 500 "
				+ "		AND a.periodo_producao NOT IN(9998, 9960, 9950, 9051, 5998) "
				+ "		GROUP BY a.periodo_producao, a.data_ini_periodo, a.data_fim_periodo  "
				+ "		ORDER BY a.periodo_producao DESC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllPeriodosCarteira() {
		
		String query = " SELECT a.periodo_producao value, a.periodo_producao || ' - Dê: ' || TO_CHAR(a.data_ini_periodo, 'DD/MM/YYYY') || ' Até: ' || TO_CHAR(a.data_fim_periodo, 'DD/MM/YYYY') label "
				+ "		FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "		AND a.codigo_empresa = 1 "
				+ "		AND a.periodo_producao NOT IN (8000, 8001, 8800, 9900, 9999) "
				+ "		AND a.periodo_producao > 2201 "
				+ "		GROUP BY a.periodo_producao, a.data_ini_periodo, a.data_fim_periodo "
				+ "		ORDER BY a.periodo_producao DESC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllPeriodoAReceber() {
		
		String query = " SELECT a.periodo_producao value, a.periodo_producao || ' - Dê: ' || TO_CHAR(a.data_ini_periodo, 'DD/MM/YYYY') || ' Até: ' || TO_CHAR(a.data_fim_periodo, 'DD/MM/YYYY') label "
				+ "      FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "      AND a.codigo_empresa IN (1, 500) "
				+ "      AND a.periodo_producao NOT IN(9998, 9960, 9950, 9051, 5998, 8000, 8001, 8800, 9900, 9999) "
				+ "      AND a.periodo_producao > 2201 "
				+ "      GROUP BY a.periodo_producao, a.data_ini_periodo, a.data_fim_periodo "
				+ "      ORDER BY a.periodo_producao DESC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllNumInterno() {
		
		String query = " SELECT y.numero_controle value, y.numero_controle label from pedi_100 y "
				+ "		WHERE y.situacao_venda  <> 10 "
				+ "     AND y.numero_controle IS NOT NULL "
				+ "		GROUP BY y.numero_controle "
				+ "		ORDER BY y.numero_controle ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllEstagios() {
		
		String query = " SELECT c.codigo_estagio value, c.codigo_estagio || ' - ' || c.descricao label "
				+ "  FROM mqop_005 c "
				+ "  ORDER BY c.codigo_estagio ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosPlanejamento(String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String listPerProducao, String listPerCarteira, String listNumInterno, int bloqueado){
		
		String query = " SELECT DADOS.PRODUTO, "
				+ "       DADOS.DESCRICAO, "
				+ "       DADOS.ESTOQUE, "
				+ "       DADOS.CARTEIRA, "
				+ "       DADOS.RESERVAS, "
				+ "       DADOS.RECEBER, "
				+ "       (DADOS.RECEBER - DADOS.RESERVAS + DADOS.ESTOQUE) SALDO "
				+ "		FROM ( "
				+ "			SELECT "
				+ "				a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura produto, "
				+ "				b.narrativa descricao, "
				+ "		(SELECT NVL(SUM(c.qtde_estoque_atu), 0) FROM estq_040 c WHERE c.cditem_nivel99 = a.nivel_estrutura "
				+ "		AND c.cditem_grupo = a.grupo_estrutura "
				+ "		AND c.cditem_subgrupo = a.subgru_estrutura "
				+ "		AND c.cditem_item = a.item_estrutura) estoque, "
				+ "		(SELECT NVL(SUM(d.qtde_pedida - d.qtde_faturada), 0) FROM pedi_110 d, pedi_100 l "
				+ "      WHERE l.pedido_venda = d.pedido_venda "
				+ "      AND d.cd_it_pe_nivel99 = a.nivel_estrutura "
				+ "      AND d.cd_it_pe_grupo = a.grupo_estrutura "
				+ "      AND d.cd_it_pe_subgrupo = a.subgru_estrutura "
				+ "      AND d.cd_it_pe_item = a.item_estrutura "
				+ "      AND d.cod_cancelamento = 0 ";
		
		if (!listNumInterno.equals("")) {
			query += " AND l.numero_controle IN (" + listNumInterno + ") ";
		}
		
		if (bloqueado == 1) {
			query += " AND l.situacao_venda <> 0 ";
		}				
				
		query +=  "   ) carteira, "
				+ "		NVL(SUM(a.qtde_reservada), 0) reservas, "
				+ "		NVL(SUM(a.qtde_areceber), 0) receber "
				+ "	  FROM tmrp_041 a, basi_010 b, basi_030 e, estq_040 f "
				+ "	  WHERE b.nivel_estrutura = a.nivel_estrutura "
				+ "   AND b.grupo_estrutura = a.grupo_estrutura "
				+ "   AND b.subgru_estrutura = a.subgru_estrutura "
				+ "   AND b.item_estrutura = a.item_estrutura "
				+ "   AND e.nivel_estrutura = a.nivel_estrutura "
				+ "   AND e.referencia = a.grupo_estrutura "
				+ "   AND f.cditem_nivel99 = a.nivel_estrutura "
				+ "	  AND f.cditem_grupo = a.grupo_estrutura "
				+ "   AND f.cditem_subgrupo = a.subgru_estrutura "
				+ "   AND f.cditem_item = a.item_estrutura "
				+ "   AND a.periodo_producao <> 0  "
				+ "   AND a.nivel_estrutura = '1' ";
		
		if (!listColecao.equals("")) {
			query += " AND e.colecao IN (" + listColecao + ")";
		}
		
		if (!listSubColecao.equals("")) {
			query += " AND EXISTS (SELECT 1 from basi_632 z "
					+ "   WHERE z.grupo_ref = a.grupo_estrutura "
					+ "   AND z.subgrupo_ref = a.subgru_estrutura "
					+ "   AND z.item_ref = a.item_estrutura "
					+ "   AND z.cd_agrupador IN (" + listSubColecao + "))";
		}
		
		if (!listLinhaProduto.equals("")) {
			query += " AND e.linha_produto IN (" + listLinhaProduto + ")";
		}
		
		if (!listArtigo.equals("")) {
			query += " AND e.artigo IN (" + listArtigo + ")";
		}
		
		if (!listArtigoCota.equals("")) {
			query += " AND e.artigo_cotas IN (" + listArtigoCota + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND e.conta_estoque IN (" + listContaEstoq + ")";
		}
		
		if (!listPublicoAlvo.equals("")) {
			query += " AND e.publico_alvo IN (" + listPublicoAlvo + ")";
		}
		
		if (!listSegmento.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 j "
					+ "					WHERE j.tipo_informacao = 10 "
				    + "                 AND j.nivel = a.nivel_estrutura "
				    + "                 AND j.grupo = a.grupo_estrutura "
				    + "                 AND j.subgrupo = a.subgru_estrutura "
				    + "                 AND j.item = a.item_estrutura "
				    + "                 AND j.codigo_informacao IN (" + listSegmento + ")";
		}
		
		if (!listFaixaEtaria.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 k "
					+ "					WHERE k.tipo_informacao = 805 "
				    + "                 AND k.nivel = a.nivel_estrutura "
				    + "                 AND k.grupo = a.grupo_estrutura "
				    + "                 AND k.subgrupo = a.subgru_estrutura "
				    + "                 AND k.item = a.item_estrutura "
				    + "                 AND k.codigo_informacao IN (" + listFaixaEtaria + ")";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND e.complemento IN (" + listComplemento + ")";
		}
		
		if (!listDeposito.equals("")) {
			query += " AND f.deposito IN (" + listDeposito + ")";
		}
		
		if (!listPerProducao.equals("")) {
			query += " AND a.periodo_producao IN (" + listPerProducao + ")";
		}
		
		if (!listPerCarteira.equals("")) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 a WHERE a.area_periodo = 1 "
					+ "       AND a.codigo_empresa = 1 "
					+ "       AND a.periodo_producao IN (" + listPerCarteira + ")"
					+ "       AND a.periodo_producao NOT IN (8000, 8001, 8800, 9900, 9999) "
					+ "       AND a.periodo_producao > 2201) ";
		}
		
		query += "   GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.narrativa) DADOS ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosDetalharEstoque(String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String listPerProducao, String listPerCarteira, String listNumInterno, int bloqueado){
		
		String query = " SELECT "
				+ "		a.cditem_nivel99 || '.' || a.cditem_grupo || '.' || a.cditem_subgrupo || '.' || a.cditem_item produto, "
				+ "		b.narrativa descricao, "
				+ "		SUM(a.qtde_estoque_atu) estoque, "
				+ "		a.deposito deposito "
				+ "	FROM estq_040 a, basi_010 b, basi_030 c "
				+ "	WHERE b.nivel_estrutura = a.cditem_nivel99 "
				+ "	AND b.grupo_estrutura = a.cditem_grupo "
				+ "	AND b.subgru_estrutura = a.cditem_subgrupo "
				+ "	AND b.item_estrutura = a.cditem_item "
				+ " AND c.nivel_estrutura = a.cditem_nivel99 "
				+ " AND c.referencia = a.cditem_grupo "
				+ "	AND a.qtde_estoque_atu <> 0  "
				+ " AND a.cditem_nivel99 = '1' ";
		
		if (!listColecao.equals("")) {
			query += " AND c.colecao IN (" + listColecao + ")";
		}
		
		if (!listSubColecao.equals("")) {
			query += " AND EXISTS (SELECT 1 from basi_632 z "
					+ "   WHERE z.grupo_ref = a.cditem_grupo "
					+ "   AND z.subgrupo_ref = a.cditem_subgrupo "
					+ "   AND z.item_ref = a.cditem_item "
					+ "   AND z.cd_agrupador IN (" + listSubColecao + "))";
		}
		
		if (!listLinhaProduto.equals("")) {
			query += " AND c.linha_produto IN (" + listLinhaProduto + ")";
		}
		
		if (!listArtigo.equals("")) {
			query += " AND c.artigo IN (" + listArtigo + ")";
		}
		
		if (!listArtigoCota.equals("")) {
			query += " AND c.artigo_cotas IN (" + listArtigoCota + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND c.conta_estoque IN (" + listContaEstoq + ")";
		}
		
		if (!listPublicoAlvo.equals("")) {
			query += " AND c.publico_alvo IN (" + listPublicoAlvo + ")";
		}
		
		if (!listSegmento.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 j "
					+ "					WHERE j.tipo_informacao = 10 "
				    + "                 AND j.nivel = b.nivel_estrutura "
				    + "                 AND j.grupo = b.grupo_estrutura "
				    + "                 AND j.subgrupo = b.subgru_estrutura "
				    + "                 AND j.item = b.item_estrutura "
				    + "                 AND j.codigo_informacao IN (" + listSegmento + ")";
		}
		
		if (!listFaixaEtaria.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 k "
					+ "					WHERE k.tipo_informacao = 805 "
				    + "                 AND k.nivel = b.nivel_estrutura "
				    + "                 AND k.grupo = b.grupo_estrutura "
				    + "                 AND k.subgrupo = b.subgru_estrutura "
				    + "                 AND k.item = b.item_estrutura "
				    + "                 AND k.codigo_informacao IN (" + listFaixaEtaria + ")";
		}
				
		if (!listComplemento.equals("")) {
			query += " AND c.complemento IN (" + listComplemento + ")";
		}		
		
		if (!listDeposito.equals("")) {
			query += " AND a.deposito IN (" + listDeposito + ")";
		}
				
		query +=  "	GROUP BY a.cditem_nivel99, a.cditem_grupo, a.cditem_subgrupo, a.cditem_item, b.narrativa, a.deposito, a.qtde_estoque_atu "
				+ "	ORDER BY a.qtde_estoque_atu DESC ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosDetalharCarteira(String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String listPerProducao, String listPerCarteira, String listNumInterno, int bloqueado){
		
		String query = " SELECT a.cd_it_pe_nivel99 || '.' || a.cd_it_pe_grupo || '.' || a.cd_it_pe_subgrupo || '.' || a.cd_it_pe_item produto, "
				+ "       b.narrativa descricao, "
				+ "       a.pedido_venda pedido, "
				+ "       d.nome_cliente cliente, "
				+ "       TO_CHAR(c.data_prev_receb, 'DD/MM/YYYY') embarquePrevisto, "
				+ "       NVL(SUM(a.qtde_pedida - a.qtde_faturada), 0) carteira "
				+ "		FROM pedi_110 a, basi_010 b, pedi_100 c, pedi_010 d "
				+ "		WHERE b.nivel_estrutura = a.cd_it_pe_nivel99 "
				+ "		AND b.grupo_estrutura = a.cd_it_pe_grupo "
				+ "		AND b.subgru_estrutura = a.cd_it_pe_subgrupo "
				+ "		AND b.item_estrutura = a.cd_it_pe_item "
				+ "		AND c.pedido_venda = a.pedido_venda "
				+ "		AND d.cgc_9 = c.cli_ped_cgc_cli9 "
				+ "		AND d.cgc_4 = c.cli_ped_cgc_cli4 "
				+ "		AND d.cgc_2 = c.cli_ped_cgc_cli2 "
				+ "		AND a.cod_cancelamento = 0 "
				+ "		AND a.cd_it_pe_nivel99 = '1' "
				+ "		AND a.qtde_pedida - a.qtde_faturada <> 0 "
				+ "		GROUP BY a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item, b.narrativa, a.pedido_venda, d.nome_cliente, c.data_prev_receb ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosDetalharOrdens(String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String listPerProducao, String listPerCarteira, String listNumInterno, int bloqueado){
		
		String query = " SELECT a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura produto, "
				+ "		c.narrativa descricao, "
				+ "		a.periodo_producao periodo, "
				+ "		a.nr_pedido_ordem ordemProd, "
				+ "		a.seq_pedido_ordem ordemConf, "
				+ "		MIN(b.qtde_pecas_prog) qtdeOrdem, "
				+ "		a.qtde_reservada reservas, "
				+ "		a.qtde_areceber receber "
				+ "		FROM tmrp_041 a, pcpc_040 b, basi_010 c WHERE b.periodo_producao = a.periodo_producao "
				+ "		AND b.ordem_confeccao = a.seq_pedido_ordem "
				+ "		AND c.nivel_estrutura = a.nivel_estrutura "
				+ "		AND c.grupo_estrutura = a.grupo_estrutura "
				+ "		AND c.subgru_estrutura = a.subgru_estrutura "
				+ "		AND c.item_estrutura = a.item_estrutura "
				+ "		AND a.nivel_estrutura = '1' "
				+ "		GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, c.narrativa, "
				+ "		a.periodo_producao, a.nr_pedido_ordem, a.seq_pedido_ordem, a.qtde_reservada, a.qtde_areceber ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
}
