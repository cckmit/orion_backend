package br.com.live.producao.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.model.ConsultaPainelPlanejamento;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class PainelPlanejamentoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public PainelPlanejamentoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveAlfaNum> findAllReferencia(String referencia) {
		
		String query = " SELECT a.referencia value, a.referencia || ' - ' || a.descr_referencia label "
				+ "	FROM basi_030 a WHERE a.referencia LIKE '%" + referencia + "%' ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllTamanho() {
		
		String query = " SELECT a.tamanho_ref value, a.tamanho_ref || ' - ' || a.descr_tamanho label FROM basi_220 a ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public List<ConteudoChaveAlfaNum> findAllCor(String cor) {
	
	String query = " SELECT a.cor_sortimento value, a.cor_sortimento || ' - ' || a.descricao label FROM basi_100 a WHERE a.cor_sortimento LIKE '%" + cor +"%' ";

	return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
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
		
		String query = " SELECT i.codigo_deposito value, i.codigo_deposito || ' - ' || i.descricao label "
				+ "   FROM basi_205 i WHERE i.descricao NOT LIKE '%(IN)%' "
				+ "   AND i.codigo_deposito <> 200 "
				+ "   ORDER BY i.codigo_deposito";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllPeriodosEmbarque() {
		
		String query = " SELECT a.grupo_embarque value, "
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
	
	public int findPeriodoInicialAno() {
		
		int periodoInicial = 0;
		
		String query = " SELECT MIN(a.periodo_producao) FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "      AND a.codigo_empresa = 500 "
				+ "		 AND a.periodo_producao NOT IN(9998, 9960, 9950, 9051, 5998) "
				+ "      AND TO_CHAR(a.data_ini_periodo, 'YYYY') = TO_CHAR(sysdate, 'YYYY') ";
		
		try {
			periodoInicial = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			periodoInicial = 0;
		}
		return periodoInicial;
	}
	
	public int findPeriodoAtual() {
		
		int periodoAtual = 0;
		
		String query = " SELECT MAX(a.periodo_producao) FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "      AND a.codigo_empresa = 500 "
				+ "		 AND a.periodo_producao NOT IN(9998, 9960, 9950, 9051, 5998) "
				+ "      AND TO_CHAR(a.data_ini_periodo, 'YYYY') = TO_CHAR(sysdate, 'YYYY') ";
		
		try {
			periodoAtual = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			periodoAtual = 0;
		}
		return periodoAtual;
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
	
	public int findCarteiraInicialAno() {
		
		int carteiraInicial = 0;
		
		String query = " SELECT MIN(a.periodo_producao) FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "      AND a.codigo_empresa = 1 "
				+ "		 AND a.periodo_producao NOT IN (8000, 8001, 8800, 9900, 9999) "
				+ "		 AND a.periodo_producao > 2201 "
				+ "      AND TO_CHAR(a.data_ini_periodo, 'YYYY') = TO_CHAR(sysdate, 'YYYY') ";
		
		try {
			carteiraInicial = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			carteiraInicial = 0;
		}
		return carteiraInicial;
	}
	
	public int findCarteiraAtual() {
		
		int carteiraAtual = 0;
		
		String query = " SELECT MAX(a.periodo_producao) FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "      AND a.codigo_empresa = 1 "
				+ "		 AND a.periodo_producao NOT IN (8000, 8001, 8800, 9900, 9999) "
				+ "		 AND a.periodo_producao > 2201 "
				+ "      AND TO_CHAR(a.data_ini_periodo, 'YYYY') = TO_CHAR(sysdate, 'YYYY') ";
		
		try {
			carteiraAtual = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			carteiraAtual = 0;
		}
		return carteiraAtual;
	}

	public List<ConteudoChaveNumerica> findAllPeriodoAReceber(int periodo) {
		
		String query = " SELECT a.periodo_producao value, a.periodo_producao || ' - Dê: ' || TO_CHAR(a.data_ini_periodo, 'DD/MM/YYYY') || ' Até: ' || TO_CHAR(a.data_fim_periodo, 'DD/MM/YYYY') label "
				+ "       FROM pcpc_010 a WHERE a.area_periodo IN (2, 9)"
				+ "       AND a.codigo_empresa IN (1, 600) "
				+ "       AND a.periodo_producao > 2201 "
				+ "       AND a.periodo_producao LIKE '%" + periodo + "%' "
				+ "       GROUP BY a.periodo_producao, a.data_ini_periodo, a.data_fim_periodo "
				+ "       ORDER BY a.periodo_producao DESC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllPeriodoReserva(int periodo) {
		
		String query = " SELECT a.periodo_producao value, a.periodo_producao || ' - Dê: ' || TO_CHAR(a.data_ini_periodo, 'DD/MM/YYYY') || ' Até: ' || TO_CHAR(a.data_fim_periodo, 'DD/MM/YYYY') label "
				+ "		FROM pcpc_010 a WHERE a.area_periodo = 1 "
				+ "	    AND a.codigo_empresa = 500 "
				+ "     AND a.periodo_producao NOT IN(9998, 9960, 9950, 9051) "
				+ "     AND a.periodo_producao > 5100 "
				+ "		AND a.periodo_producao LIKE '%" + periodo + "%' "
				+ "		GROUP BY a.periodo_producao, a.data_ini_periodo, a.data_fim_periodo "
				+ "		ORDER BY a.periodo_producao DESC ";

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
	
	public List<ConsultaPainelPlanejamento> findAcabadosPlanejamento(String listReferencia, String listTamanho, String listCor, String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String periodoProdInicio, String periodoProdFim, String periodoCartInicio, String periodoCartFim, String listNumInterno, int bloqueado){
		
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
				+ "	  FROM tmrp_041 a, basi_010 b, basi_030 e, estq_040 f, basi_205 g "
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
				+ "   AND g.codigo_deposito = f.deposito "
				+ "   AND a.periodo_producao <> 0  "
				+ "   AND a.nivel_estrutura = '1' "
				+ "   AND g.descricao NOT LIKE '%(IN)%' "
				+ "   AND a.periodo_producao BETWEEN " + periodoProdInicio + " AND " + periodoProdFim + " "
				+ "   AND f.deposito IN (" + listDeposito + ") ";
		
		if (!listReferencia.equals("")) {
			query += " AND a.grupo_estrutura IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND a.subgru_estrutura IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND a.item_estrutura IN (" + listCor + ")";
		}
		
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
			query += " AND EXISTS (SELECT 1 FROM basi_400 w "
					+ "   WHERE w.nivel = a.nivel_estrutura "
					+ "   AND w.grupo = a.grupo_estrutura "
					+ "   AND w.tipo_informacao = 9 "
					+ "   AND w.codigo_informacao IN (" + listPublicoAlvo + ")) ";
		}
		
		if (!listSegmento.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 j "
					+ "					WHERE j.tipo_informacao = 10 "
				    + "                 AND j.nivel = a.nivel_estrutura "
				    + "                 AND j.grupo = a.grupo_estrutura "
				    + "                 AND j.codigo_informacao IN (" + listSegmento + "))";
		}
		
		if (!listFaixaEtaria.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 k "
					+ "					WHERE k.tipo_informacao = 805 "
				    + "                 AND k.nivel = a.nivel_estrutura "
				    + "                 AND k.grupo = a.grupo_estrutura "
				    + "                 AND k.codigo_informacao IN (" + listFaixaEtaria + "))";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND b.complemento IN (" + listComplemento + ")";
		}
		
		if (!listPerEmbarque.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_590 q "
					+ "		WHERE q.nivel = a.nivel_estrutura "
					+ "     and q.grupo = a.grupo_estrutura "
					+ "     and q.subgrupo = a.subgru_estrutura "
					+ "     and q.item = a.item_estrutura "
					+ "     and q.grupo_embarque IN (" + listPerEmbarque + " )) ";
		}
	
		if ((periodoCartInicio != null) && (periodoCartFim != null)) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 a WHERE a.area_periodo = 1 "
					+ "       AND a.codigo_empresa = 1 "
					+ "       AND a.periodo_producao BETWEEN " + periodoCartInicio + " AND " + periodoCartFim + " )";
		}
		
		query += "   GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.narrativa) DADOS ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosDetalharEstoque(String listReferencia, String listTamanho, String listCor, String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String periodoProdInicio, String periodoProdFim, String periodoCartInicio, String periodoCartFim, String listNumInterno, int bloqueado){
		
		String query = " SELECT  "
				+ "     a.cditem_nivel99 || '.' || a.cditem_grupo || '.' || a.cditem_subgrupo || '.' || a.cditem_item produto, "
				+ "     b.narrativa descricao, "
				+ "     SUM(a.qtde_estoque_atu) estoque, "
				+ "     a.deposito deposito "
				+ "   FROM estq_040 a, basi_010 b, basi_030 c, basi_205 d "
				+ "   WHERE b.nivel_estrutura = a.cditem_nivel99 "
				+ "   AND b.grupo_estrutura = a.cditem_grupo "
				+ "   AND b.subgru_estrutura = a.cditem_subgrupo "
				+ "   AND b.item_estrutura = a.cditem_item "
				+ "   AND c.nivel_estrutura = a.cditem_nivel99 "
				+ "   AND c.referencia = a.cditem_grupo "
				+ "   AND d.codigo_deposito = a.deposito "
				+ "   AND a.qtde_estoque_atu <> 0 "
				+ "   AND d.descricao NOT LIKE '%(IN)%' "
				+ "   AND a.cditem_nivel99 = '1' "
				+ "   AND a.deposito IN (" + listDeposito + ") ";
		
		if (!listReferencia.equals("")) {
			query += " AND a.cditem_grupo IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND a.cditem_subgrupo IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND a.cditem_item IN (" + listCor + ")";
		}
		
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
			query += " AND EXISTS (SELECT 1 FROM basi_400 w "
					+ "   WHERE w.nivel = a.cditem_nivel99 "
					+ "   AND w.grupo = a.cditem_grupo "
					+ "   AND w.tipo_informacao = 9 "
					+ "   AND w.codigo_informacao IN (" + listPublicoAlvo + "))";
		}
		
		if (!listSegmento.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 j "
					+ "					WHERE j.tipo_informacao = 10 "
				    + "                 AND j.nivel = a.cditem_nivel99 "
				    + "                 AND j.grupo = a.cditem_grupo "
				    + "                 AND j.codigo_informacao IN (" + listSegmento + "))";
		}
		
		if (!listFaixaEtaria.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 k "
					+ "					WHERE k.tipo_informacao = 805 "
				    + "                 AND k.nivel = a.cditem_nivel99 "
				    + "                 AND k.grupo = a.cditem_grupo "
				    + "                 AND k.codigo_informacao IN (" + listFaixaEtaria + "))";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND b.complemento IN (" + listComplemento + ")";
		}

		if (!periodoProdInicio.equals("") && !periodoProdFim.equals("")) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 x WHERE x.area_periodo = 1 "
					+ "       AND x.codigo_empresa = 500 "
					+ "       AND x.periodo_producao BETWEEN " + periodoProdInicio + " AND " + periodoProdFim +" ) ";
		}
		
		if (periodoCartInicio != null && periodoCartFim != null) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 a WHERE a.area_periodo = 1 "
					+ "       AND a.codigo_empresa = 1 "
					+ "       AND a.periodo_producao BETWEEN " + periodoCartInicio + " AND " + periodoCartFim +" )";
		}
		
		if (!listNumInterno.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pedi_110 e, pedi_100 l "
					+ "      WHERE l.pedido_venda = e.pedido_venda "
					+ "      AND e.cd_it_pe_nivel99 = a.cditem_nivel99 "
					+ "      AND e.cd_it_pe_grupo = a.cditem_grupo "
					+ "      AND e.cd_it_pe_subgrupo = a.cditem_subgrupo "
					+ "      AND e.cd_it_pe_item = a.cditem_item "
					+ "      AND e.cod_cancelamento = 0 "
					+ "      AND l.numero_controle IN (" + listNumInterno + "))";
		}
		
		if (bloqueado == 1) {
			query += " AND EXISTS (SELECT 1 FROM pedi_110 f, pedi_100 l "
					+ "      WHERE l.pedido_venda = f.pedido_venda "
					+ "      AND f.cd_it_pe_nivel99 = a.cditem_nivel99 "
					+ "      AND f.cd_it_pe_grupo = a.cditem_grupo "
					+ "      AND f.cd_it_pe_subgrupo = a.cditem_subgrupo "
					+ "      AND f.cd_it_pe_item = a.cditem_item "
					+ "      AND f.cod_cancelamento = 0 "
					+ "      AND l.situacao_venda <> 0 )";
		}
				
		query +=  "	GROUP BY a.cditem_nivel99, a.cditem_grupo, a.cditem_subgrupo, a.cditem_item, b.narrativa, a.deposito, a.qtde_estoque_atu "
				+ " ORDER BY a.qtde_estoque_atu DESC ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosDetalharCarteira(String listReferencia, String listTamanho, String listCor, String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String periodoProdInicio, String periodoProdFim, String periodoCartInicio, String periodoCartFim, String listNumInterno, int bloqueado){
		
		String query = " SELECT a.cd_it_pe_nivel99 || '.' || a.cd_it_pe_grupo || '.' || a.cd_it_pe_subgrupo || '.' || a.cd_it_pe_item produto, "
				+ "       b.narrativa descricao, "
				+ "       a.pedido_venda pedido, "
				+ "       d.nome_cliente cliente, "
				+ "       TO_CHAR(c.data_prev_receb, 'DD/MM/YYYY') embarquePrevisto, "
				+ "       NVL(SUM(a.qtde_pedida - a.qtde_faturada), 0) carteira "
				+ "   FROM pedi_110 a, basi_010 b, pedi_100 c, pedi_010 d, basi_030 e, basi_205 f "
				+ "   WHERE b.nivel_estrutura = a.cd_it_pe_nivel99 "
				+ "	   AND b.grupo_estrutura = a.cd_it_pe_grupo "
				+ "    AND b.subgru_estrutura = a.cd_it_pe_subgrupo "
				+ "    AND b.item_estrutura = a.cd_it_pe_item "
				+ "    AND c.pedido_venda = a.pedido_venda "
				+ "    AND d.cgc_9 = c.cli_ped_cgc_cli9 "
				+ "    AND d.cgc_4 = c.cli_ped_cgc_cli4 "
				+ "    AND d.cgc_2 = c.cli_ped_cgc_cli2 "
				+ "    AND e.nivel_estrutura = b.nivel_estrutura "
				+ "    AND e.referencia = b.grupo_estrutura "
				+ "    AND f.codigo_deposito = a.codigo_deposito "
				+ "    AND a.cod_cancelamento = 0 "
				+ "    AND a.cd_it_pe_nivel99 = '1' "
				+ "    AND a.qtde_pedida - a.qtde_faturada <> 0 "
				+ "    AND f.descricao NOT LIKE '%(IN)%' "
				+ "    AND c.situacao_venda <> 10 "
				+ "    AND a.codigo_deposito IN (" + listDeposito + ") ";
		
		if (!listReferencia.equals("")) {
			query += " AND b.grupo_estrutura IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND b.subgru_estrutura IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND b.item_estrutura IN (" + listCor + ")";
		}
		
		if (!listColecao.equals("")) {
			query += " AND e.colecao IN (" + listColecao + ")";
		}
		
		if (!listSubColecao.equals("")) {
			query += " AND EXISTS (SELECT 1 from basi_632 z "
					+ "   WHERE z.grupo_ref = a.cd_it_pe_grupo "
					+ "   AND z.subgrupo_ref = a.cd_it_pe_subgrupo "
					+ "   AND z.item_ref = a.cd_it_pe_item "
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
			query += " AND EXISTS (SELECT 1 FROM basi_400 w "
					+ "   WHERE w.nivel = a.cd_it_pe_nivel99 "
					+ "   AND w.grupo = a.cd_it_pe_grupo "
					+ "   AND w.tipo_informacao = 9 "
					+ "   AND w.codigo_informacao IN (" + listPublicoAlvo + "))";
		}
		
		if (!listSegmento.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 j "
					+ "					WHERE j.tipo_informacao = 10 "
				    + "                 AND j.nivel = a.cd_it_pe_nivel99 "
				    + "                 AND j.grupo = a.cd_it_pe_grupo "
				    + "                 AND j.codigo_informacao IN (" + listSegmento + "))";
		}
		
		if (!listFaixaEtaria.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 k "
					+ "					WHERE k.tipo_informacao = 805 "
				    + "                 AND k.nivel = a.cd_it_pe_nivel99 "
				    + "                 AND k.grupo = a.cd_it_pe_grupo "
				    + "                 AND k.codigo_informacao IN (" + listFaixaEtaria + "))";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND b.complemento IN (" + listComplemento + ")";
		}
		
		if (periodoProdInicio != null && periodoProdFim != null) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 x WHERE x.area_periodo = 1 "
					+ "       AND x.codigo_empresa = 500 "
					+ "       AND x.periodo_producao BETWEEN " + periodoProdInicio + " AND " + periodoProdFim +" )";
		}
		
		if (periodoCartInicio != null && periodoCartFim != null) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 a WHERE a.area_periodo = 1 "
					+ "       AND a.codigo_empresa = 1 "
					+ "       AND a.periodo_producao BETWEEN " + periodoCartInicio + " AND " + periodoCartFim + " )";
		}
		
		if (!listNumInterno.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pedi_110 e, pedi_100 l "
					+ "      WHERE l.pedido_venda = e.pedido_venda "
					+ "      AND e.cd_it_pe_nivel99 = a.cd_it_pe_nivel99 "
					+ "      AND e.cd_it_pe_grupo = a.cd_it_pe_grupo "
					+ "      AND e.cd_it_pe_subgrupo = a.cd_it_pe_subgrupo "
					+ "      AND e.cd_it_pe_item = a.cd_it_pe_item "
					+ "      AND e.cod_cancelamento = 0 "
					+ "      AND l.numero_controle IN (" + listNumInterno + "))";
		}
		
		if (bloqueado == 1) {
			query += " AND EXISTS (SELECT 1 FROM pedi_110 f, pedi_100 l "
					+ "      WHERE l.pedido_venda = f.pedido_venda "
					+ "      AND f.cd_it_pe_nivel99 = a.cd_it_pe_nivel99 "
					+ "      AND f.cd_it_pe_grupo = a.cd_it_pe_grupo "
					+ "      AND f.cd_it_pe_subgrupo = a.cd_it_pe_subgrupo "
					+ "      AND f.cd_it_pe_item = a.cd_it_pe_item "
					+ "      AND f.cod_cancelamento = 0 "
					+ "      AND l.situacao_venda <> 0 )";
		}
		
		query += "  GROUP BY a.cd_it_pe_nivel99, a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item, b.narrativa, a.pedido_venda, d.nome_cliente, c.data_prev_receb ";
	
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findAcabadosDetalharOrdens(String listReferencia, String listTamanho, String listCor, String listColecao, String listSubColecao, String listLinhaProduto, String listArtigo, String listArtigoCota,
			String listContaEstoq, String listPublicoAlvo, String listSegmento, String listFaixaEtaria, String listComplemento, String listDeposito, String listPerEmbarque,
			String periodoProdInicio, String periodoProdFim, String periodoCartInicio, String periodoCartFim, String listNumInterno, int bloqueado){
		
		String query = " SELECT a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura produto, "
				+ "		c.narrativa descricao, "
				+ "		a.periodo_producao periodo, "
				+ "		a.nr_pedido_ordem ordemProd, "
				+ "		a.seq_pedido_ordem ordemConf, "
				+ "		MIN(b.qtde_pecas_prog) qtdeOrdem, "
				+ "		a.qtde_reservada reservas, "
				+ "		a.qtde_areceber receber "
				+ "	  FROM tmrp_041 a, pcpc_040 b, basi_010 c, basi_030 d, estq_040 e, basi_205 f "
				+ "   WHERE b.periodo_producao = a.periodo_producao "
				+ "		AND b.ordem_confeccao = a.seq_pedido_ordem "
				+ "		AND c.nivel_estrutura = a.nivel_estrutura "
				+ "		AND c.grupo_estrutura = a.grupo_estrutura "
				+ "		AND c.subgru_estrutura = a.subgru_estrutura "
				+ "		AND c.item_estrutura = a.item_estrutura "
				+ "     AND d.nivel_estrutura = a.nivel_estrutura "
				+ "     AND d.referencia = a.grupo_estrutura "
				+ "     AND e.cditem_nivel99 = a.nivel_estrutura "
				+ "     AND e.cditem_grupo = a.grupo_estrutura "
				+ "     AND e.cditem_subgrupo = a.subgru_estrutura "
				+ "     AND e.cditem_item = a.item_estrutura "
				+ "     AND f.codigo_deposito = e.deposito "
				+ "		AND a.nivel_estrutura = '1' "
				+ "     AND e.deposito IN (" + listDeposito + ") ";
		
		if (!listReferencia.equals("")) {
			query += " AND a.grupo_estrutura IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND a.subgru_estrutura IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND a.item_estrutura IN (" + listCor + ")";
		}
		
		if (!listColecao.equals("")) {
			query += " AND d.colecao IN (" + listColecao + ")";
		}
		
		if (!listSubColecao.equals("")) {
			query += " AND EXISTS (SELECT 1 from basi_632 z "
					+ "   WHERE z.grupo_ref = a.grupo_estrutura "
					+ "   AND z.subgrupo_ref = a.subgru_estrutura "
					+ "   AND z.item_ref = a.item_estrutura "
					+ "   AND z.cd_agrupador IN (" + listSubColecao + "))";
		}
		
		if (!listLinhaProduto.equals("")) {
			query += " AND d.linha_produto IN (" + listLinhaProduto + ")";
		}
		
		if (!listArtigo.equals("")) {
			query += " AND d.artigo IN (" + listArtigo + ")";
		}
		
		if (!listArtigoCota.equals("")) {
			query += " AND d.artigo_cotas IN (" + listArtigoCota + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND d.conta_estoque IN (" + listContaEstoq + ")";
		}
		
		if (!listPublicoAlvo.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 w "
					+ "   WHERE w.nivel = a.nivel_estrutura "
					+ "   AND w.grupo = a.grupo_estrutura "
					+ "   AND w.tipo_informacao = 9 "
					+ "   AND w.codigo_informacao IN (" + listPublicoAlvo + "))";
		}
		
		if (!listSegmento.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 j "
					+ "					WHERE j.tipo_informacao = 10 "
				    + "                 AND j.nivel = a.nivel_estrutura "
				    + "                 AND j.grupo = a.grupo_estrutura "
				    + "                 AND j.codigo_informacao IN (" + listSegmento + "))";
		}
		
		if (!listFaixaEtaria.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM basi_400 k "
					+ "					WHERE k.tipo_informacao = 805 "
				    + "                 AND k.nivel = a.nivel_estrutura "
				    + "                 AND k.grupo = a.grupo_estrutura "
				    + "                 AND k.codigo_informacao IN (" + listFaixaEtaria + "))";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND c.complemento IN (" + listComplemento + ")";
		}

		if (!periodoProdInicio.equals("") && !periodoProdFim.equals("")) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 x WHERE x.area_periodo = 1 "
					+ "       AND x.codigo_empresa = 500 "
					+ "       AND x.periodo_producao BETWEEN " + periodoProdInicio + " AND " + periodoProdFim + " )";
		}
		
		if (periodoCartInicio != null && periodoCartFim != null) {
			query += " AND EXISTS (SELECT 1 "
					+ "       FROM pcpc_010 a WHERE a.area_periodo = 1 "
					+ "       AND a.codigo_empresa = 1 "
					+ "       AND a.periodo_producao BETWEEN " + periodoCartInicio + " AND " + periodoCartFim +" )";
		}
		
		if (!listNumInterno.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pedi_110 e, pedi_100 l "
					+ "      WHERE l.pedido_venda = e.pedido_venda "
					+ "      AND e.cd_it_pe_nivel99 = a.nivel_estrutura "
					+ "      AND e.cd_it_pe_grupo = a.grupo_estrutura "
					+ "      AND e.cd_it_pe_subgrupo = a.subgru_estrutura "
					+ "      AND e.cd_it_pe_item = a.item_estrutura "
					+ "      AND e.cod_cancelamento = 0 "
					+ "      AND l.numero_controle IN (" + listNumInterno + "))";
		}
		
		if (bloqueado == 1) {
			query += " AND EXISTS (SELECT 1 FROM pedi_110 f, pedi_100 l "
					+ "      WHERE l.pedido_venda = f.pedido_venda "
					+ "      AND f.cd_it_pe_nivel99 = a.nivel_estrutura "
					+ "      AND f.cd_it_pe_grupo = a.grupo_estrutura "
					+ "      AND f.cd_it_pe_subgrupo = a.subgru_estrutura "
					+ "      AND f.cd_it_pe_item = a.item_estrutura "
					+ "      AND f.cod_cancelamento = 0 "
					+ "      AND l.situacao_venda <> 0 )";
		}
		
		query += "   GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, c.narrativa, "
			  + "	a.periodo_producao, a.nr_pedido_ordem, a.seq_pedido_ordem, a.qtde_reservada, a.qtde_areceber ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findMateriaisPlanejamento(String listNivel, String listReferencia, String listTamanho, String listCor, String listComplemento, 
			String listContaEstoq, String listDeposito, String periodoAReceberInicio, String periodoAReceberFim, String periodoReservaInicio, 
			String periodoReservaFim, String listOrdemProducao, String listEstagio){
		
		String query = " SELECT DADOS.NIVEL || '.' || DADOS.GRUPO || '.' || DADOS.SUBGRUPO || '.' || DADOS.ITEM PRODUTO, "
				+ "       DADOS.DESCRICAO, "
				+ "       DADOS.UNDMEDIDA, "
				+ "       DADOS.ESTOQUE, "
				+ "       SUM(DADOS.RESERVAS) RESERVAS, "
				+ "       SUM(DADOS.RECEBER) RECEBER, "
				+ "       (DADOS.ESTOQUE + SUM(DADOS.RECEBER)) - SUM(DADOS.RESERVAS) SALDO "
				+ "       FROM ( "
				+ "  SELECT a.nivel_estrutura nivel, "
				+ "         a.grupo_estrutura grupo, "
				+ "         a.subgru_estrutura subgrupo, "
				+ "         a.item_estrutura item, "
				+ "         b.narrativa descricao, "
				+ "         b.complemento complemento, "
				+ "         e.conta_estoque contaEstoque, "
				+ "         a.nr_pedido_ordem ordemProducao, "
				+ "         e.unidade_medida undMedida, "
				+ "         a.periodo_producao periodoProd, "
				+ "         a.seq_pedido_ordem ordemConfeccao, "
				+ "         (SELECT NVL(SUM(c.qtde_estoque_atu), 0) FROM estq_040 c WHERE c.cditem_nivel99 = a.nivel_estrutura "
				+ "       AND c.cditem_grupo = a.grupo_estrutura  "
				+ "       AND c.cditem_subgrupo = a.subgru_estrutura "
				+ "       AND c.cditem_item = a.item_estrutura  "
				+ "       AND c.deposito <> 200) estoque, "
				+ "       NVL(SUM(a.qtde_reservada), 0) reservas, "
				+ "       0 receber "
				+ "       FROM tmrp_041 a, basi_010 b, basi_030 e "
				+ "       WHERE b.nivel_estrutura = a.nivel_estrutura "
				+ "       AND b.grupo_estrutura = a.grupo_estrutura "
				+ "       AND b.subgru_estrutura = a.subgru_estrutura  "
				+ "       AND b.item_estrutura = a.item_estrutura "
				+ "       AND e.nivel_estrutura = a.nivel_estrutura "
				+ "       AND e.referencia = a.grupo_estrutura "
				+ "       AND a.nivel_estrutura IN ('2', '4', '7', '9') "
				+ "       AND a.periodo_producao BETWEEN  " + periodoAReceberInicio + " AND " + periodoAReceberFim + " "
				+ "       GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.narrativa, e.unidade_medida, a.nivel_estrutura, "
				+ "       a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.narrativa, b.complemento, e.conta_estoque, a.nr_pedido_ordem, a.periodo_producao, a.seq_pedido_ordem "
				+ "   UNION   "
				+ "   SELECT a.nivel_estrutura nivel, "
				+ "          a.grupo_estrutura grupo, "
				+ "          a.subgru_estrutura subgrupo, "
				+ "          a.item_estrutura item, "
				+ "          b.narrativa descricao, "
				+ "          b.complemento complemento, "
				+ "          e.conta_estoque contaEstoque, "
				+ "          a.nr_pedido_ordem ordemProducao, "
				+ "          e.unidade_medida undMedida, "
				+ "         a.periodo_producao periodoProd, "
				+ "         a.seq_pedido_ordem ordemConfeccao, "
				+ "          (SELECT NVL(SUM(c.qtde_estoque_atu), 0) FROM estq_040 c WHERE c.cditem_nivel99 = a.nivel_estrutura "
				+ "       AND c.cditem_grupo = a.grupo_estrutura "
				+ "       AND c.cditem_subgrupo = a.subgru_estrutura "
				+ "       AND c.cditem_item = a.item_estrutura "
				+ "       AND c.deposito <> 200) estoque, "
				+ "       0 reservas, "
				+ "       NVL(SUM(a.qtde_areceber), 0) receber "
				+ "       FROM tmrp_041 a, basi_010 b, basi_030 e "
				+ "       WHERE b.nivel_estrutura = a.nivel_estrutura "
				+ "       AND b.grupo_estrutura = a.grupo_estrutura "
				+ "       AND b.subgru_estrutura = a.subgru_estrutura "
				+ "       AND b.item_estrutura = a.item_estrutura "
				+ "       AND e.nivel_estrutura = a.nivel_estrutura "
				+ "       AND e.referencia = a.grupo_estrutura "
				+ "       AND a.nivel_estrutura IN ('2', '4', '7', '9') "
				+ "       AND a.periodo_producao BETWEEN " + periodoReservaInicio + " AND " + periodoReservaFim + " "
				+ "       GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.narrativa, e.unidade_medida, a.nivel_estrutura, "
				+ "       a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.narrativa, b.complemento, e.conta_estoque, a.nr_pedido_ordem, a.periodo_producao, a.seq_pedido_ordem "
				+ "     ) DADOS "
				+ "       WHERE DADOS.NIVEL IN (" + listNivel + ") ";				

		
		if (!listReferencia.equals("")) {
			query += " AND DADOS.GRUPO IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND DADOS.SUBGRUPO IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND DADOS.ITEM IN (" + listCor + ")";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND DADOS.COMPLEMENTO IN (" + listComplemento + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND DADOS.CONTAESTOQUE IN (" + listContaEstoq + ")";
		}
		
		if (!listDeposito.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM estq_040 g "
					+ "     WHERE g.cditem_nivel99 = DADOS.NIVEL "
					+ "		AND g.cditem_grupo = DADOS.GRUPO "
					+ "		AND g.cditem_subgrupo = DADOS.SUBGRUPO "
					+ "		AND g.cditem_item = DADOS.ITEM "
					+ "		AND g.deposito IN (" + listDeposito + ")) ";
		}
		
		if (!listOrdemProducao.equals("")) {
			query += " AND  DADOS.ORDEMPRODUCAO IN (" + listOrdemProducao + ") ";
		}
		
		if (!listEstagio.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pcpc_040 z"
					+ "  WHERE  z.periodo_producao = DADOS.PERIODOPROD "
					+ "  AND z.ordem_producao = DADOS.ORDEMPRODUCAO "
					+ "  AND z.ordem_confeccao = DADOS.ORDEMCONFECCAO "
					+ "  AND z.qtde_a_produzir_pacote > 0 "
					+ "  AND z.codigo_estagio IN (" + listEstagio + ")) ";
		}

		query += "   GROUP BY DADOS.NIVEL, DADOS.GRUPO, DADOS.SUBGRUPO, DADOS.ITEM, DADOS.DESCRICAO, DADOS.UNDMEDIDA, DADOS.ESTOQUE ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findMateriaisDetalharEstoque(String listNivel, String listReferencia, String listTamanho, String listCor,String listComplemento, 
			String listContaEstoq, String listDeposito, String periodoAReceberInicio, String periodoAReceberFim, String periodoReservaInicio, 
			String periodoReservaFim, String listOrdemProducao, String listEstagio){
		
		String query = " SELECT a.cditem_nivel99 || '.' || a.cditem_grupo || '.' || a.cditem_subgrupo || '.' || a.cditem_item produto, "
				+ "		b.narrativa descricao, "
				+ "		c.unidade_medida undMedida, "
				+ "		a.deposito || ' - ' || d.descricao deposito, "
				+ "		NVL(SUM(a.qtde_estoque_atu), 0) estoque "
				+ "	  FROM estq_040 a, basi_010 b, basi_030 c, basi_205 d "
				+ "	  WHERE b.nivel_estrutura = a.cditem_nivel99 "
				+ "   AND b.grupo_estrutura = a.cditem_grupo "
				+ "   AND b.subgru_estrutura = a.cditem_subgrupo "
				+ "   AND b.item_estrutura = a.cditem_item "
				+ "   AND c.nivel_estrutura = a.cditem_nivel99 "
				+ "   AND c.referencia = a.cditem_grupo "
				+ "   AND d.codigo_deposito = a.deposito "
				+ "   AND a.cditem_nivel99 IN ('2', '4', '7', '9') "
				+ "   AND a.deposito <> 200 "
				+ "   AND d.descricao NOT LIKE ('%(IN)%') "
				+ "   AND EXISTS (SELECT 1 FROM tmrp_041 g "
				+ "					WHERE g.nivel_estrutura = a.cditem_nivel99 "
				+ "					AND g.grupo_estrutura = a.cditem_grupo "
				+ "					AND g.subgru_estrutura = a.cditem_subgrupo "
				+ "					AND g.item_estrutura = a.cditem_item "
				+ "					AND g.periodo_producao BETWEEN  " + periodoAReceberInicio + " AND " + periodoAReceberFim + " ) ";
		
		if (!listNivel.equals("")) {
			query += " AND a.cditem_nivel99 IN (" + listNivel + ")";
		}
		
		if (!listReferencia.equals("")) {
			query += " AND a.cditem_grupo IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND a.cditem_subgrupo IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND a.cditem_item IN (" + listCor + ")";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND b.complemento IN (" + listComplemento + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND c.conta_estoque IN (" + listContaEstoq + ")";
		}
		
		if (!listDeposito.equals("")) {
			query += " AND a.deposito IN (" + listDeposito + ") ";
		}
		
		if (!listOrdemProducao.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM tmrp_041 j "
					+ "     WHERE j.nivel_estrutura = a.cditem_nivel99 "
					+ "		AND j.grupo_estrutura = a.cditem_grupo "
					+ "		AND j.subgru_estrutura = a.cditem_subgrupo "
					+ "		AND j.item_estrutura = a.cditem_item "
					+ "		AND j.nr_pedido_ordem IN (" + listOrdemProducao + ")) ";
		}
		
		if (!listEstagio.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pcpc_040 z "
					+ "  WHERE  z.proconf_nivel99 = a.cditem_nivel99 "
					+ "  AND z.proconf_grupo = a.cditem_grupo "
					+ "  AND z.proconf_subgrupo = a.cditem_subgrupo "
					+ "  AND z.proconf_item = a.cditem_item"
					+ "  AND z.qtde_a_produzir_pacote > 0 "
					+ "  AND z.codigo_estagio IN (" + listEstagio + ")) ";
		}
		
		query += "  GROUP BY a.cditem_nivel99, a.cditem_grupo, a.cditem_subgrupo, a.cditem_item, b.narrativa, a.deposito, a.qtde_estoque_atu, c.unidade_medida, d.descricao "
			  + "   ORDER BY a.qtde_estoque_atu DESC ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findMateriaisDetalharOrdens(String listNivel, String listReferencia, String listTamanho, String listCor, String listComplemento, 
			String listContaEstoq, String listDeposito, String periodoAReceberInicio, String periodoAReceberFim, String periodoReservaInicio, 
			String periodoReservaFim, String listOrdemProducao, String listEstagio){
		
		String query = " SELECT DADOS.NIVEL || '.' || DADOS.GRUPO || '.' || DADOS.SUBGRUPO || '.' || DADOS.ITEM PRODUTO, "
				+ "			DADOS.DESCRICAO, "
				+ "			DADOS.UNDMEDIDA, "
				+ "         DADOS.PERIODO, "
				+ "         DADOS.ORDEMPROD, "
				+ "         DADOS.ORDEMCONF, "
				+ "         DADOS.QTDEORDEM, "
				+ "         SUM(DADOS.RESERVAS) RESERVAS, "
				+ "			SUM(DADOS.RECEBER) RECEBER "
				+ "  FROM ( "
				+ "  SELECT "
				+ "     a.nivel_estrutura nivel,"
				+ "     a.grupo_estrutura grupo,"
				+ "     a.subgru_estrutura subgrupo, "
				+ "     a.item_estrutura item, "
				+ "		c.narrativa descricao, "
				+ "     c.complemento complemento, "
				+ "		d.unidade_medida undMedida, "
				+ "     d.conta_estoque contaEstoque, "
				+ "		a.periodo_producao periodo, "
				+ "		a.nr_pedido_ordem ordemProd, "
				+ "		a.seq_pedido_ordem ordemConf, "
				+ "		MIN(b.qtde_pecas_prog) qtdeOrdem, "
				+ "		a.qtde_reservada reservas, "
				+ "		0 receber "
				+ "	 FROM tmrp_041 a, pcpc_040 b, basi_010 c, basi_030 d, estq_040 e "
				+ "	 WHERE b.periodo_producao = a.periodo_producao "
				+ "  AND b.ordem_confeccao = a.seq_pedido_ordem "
				+ "  AND c.nivel_estrutura = a.nivel_estrutura "
				+ "  AND c.grupo_estrutura = a.grupo_estrutura "
				+ "  AND c.subgru_estrutura = a.subgru_estrutura "
				+ "  AND c.item_estrutura = a.item_estrutura "
				+ "  AND d.nivel_estrutura = a.nivel_estrutura "
				+ "  AND d.referencia = a.grupo_estrutura "
				+ "  AND e.cditem_nivel99 = a.nivel_estrutura "
				+ "  AND e.cditem_grupo = a.grupo_estrutura "
				+ "  AND e.cditem_subgrupo = a.subgru_estrutura "
				+ "  AND e.cditem_item = a.item_estrutura "
				+ "  AND a.nivel_estrutura IN ('1', '4', '7', '9') "
				+ "  AND a.periodo_producao BETWEEN " + periodoAReceberInicio + " AND " + periodoAReceberFim + " "
		        + "  GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, c.narrativa, c.complemento, d.conta_estoque, "
	 	        + "  a.periodo_producao, a.nr_pedido_ordem, a.seq_pedido_ordem, a.qtde_reservada, a.qtde_areceber, d.unidade_medida "
	 	        + "  UNION "
	 	        + "  SELECT  "
	 	        + "		a.nivel_estrutura nivel, "
	 	        + "		a.grupo_estrutura grupo, "
	 	        + "		a.subgru_estrutura subgrupo, "
	 	        + "		a.item_estrutura item, "
	 	        + "		c.narrativa descricao, "
	 	        + "     c.complemento complemento, "
	 	        + "		d.unidade_medida undMedida, "
	 	        + "     d.conta_estoque contaEstoque, "
	 	        + "		a.periodo_producao periodo, "
	 	        + "		a.nr_pedido_ordem ordemProd, "
	 	        + "		a.seq_pedido_ordem ordemConf, "
	 	        + "		MIN(b.qtde_pecas_prog) qtdeOrdem, "
	 	        + "		0 reservas, "
	 	        + "		a.qtde_areceber receber  "
	 	        + "		FROM tmrp_041 a, pcpc_040 b, basi_010 c, basi_030 d, estq_040 e "
	 	        + "		WHERE b.periodo_producao = a.periodo_producao "
	 	        + "		AND b.ordem_confeccao = a.seq_pedido_ordem "
	 	        + "		AND c.nivel_estrutura = a.nivel_estrutura "
	 	        + "		AND c.grupo_estrutura = a.grupo_estrutura "
	 	        + "		AND c.subgru_estrutura = a.subgru_estrutura "
	 	        + "		AND c.item_estrutura = a.item_estrutura "
	 	        + "		AND d.nivel_estrutura = a.nivel_estrutura "
	 	        + "		AND d.referencia = a.grupo_estrutura "
	 	        + "		AND e.cditem_nivel99 = a.nivel_estrutura "
	 	        + "		AND e.cditem_grupo = a.grupo_estrutura "
	 	        + "		AND e.cditem_subgrupo = a.subgru_estrutura "
	 	        + "		AND e.cditem_item = a.item_estrutura "
	 	        + "		AND a.nivel_estrutura IN ('1', '4', '7', '9') "
	 	        + "		AND a.periodo_producao BETWEEN " + periodoAReceberInicio + " AND " + periodoAReceberFim + " "
	 	        + "		GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, c.narrativa, c.complemento, d.conta_estoque, "
	 	        + "	 	a.periodo_producao, a.nr_pedido_ordem, a.seq_pedido_ordem, a.qtde_reservada, a.qtde_areceber, d.unidade_medida "
	 	        + "    ) DADOS "
	 	        + "    WHERE DADOS.NIVEL IN (" + listNivel + ")";
	
		
		if (!listReferencia.equals("")) {
			query += " AND DADOS.GRUPO IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND DADOS.SUBGRUPO IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND DADOS.ITEM IN (" + listCor + ")";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND DADOS.COMPLEMENTO IN (" + listComplemento + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND DADOS.CONTAESTOQUE IN (" + listContaEstoq + ")";
		}
		
		if (!listDeposito.equals("")) {
			
			query += " AND EXISTS (SELECT 1 FROM estq_040 g "
					+ "	 	 WHERE g.cditem_nivel99 = DADOS.NIVEL "
					+ "	 	 AND g.cditem_grupo = DADOS.GRUPO "
					+ "	 	 AND g.cditem_subgrupo = DADOS.SUBGRUPO "
					+ "	 	 AND g.cditem_item = DADOS.ITEM "
					+ "	 	 AND g.deposito IN (" + listDeposito + ") )";
		}
		
		if (!listEstagio.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pcpc_040 z"
					+ "  WHERE  z.periodo_producao = DADOS.PERIODO "
					+ "  AND z.ordem_producao = DADOS.ORDEMPROD "
					+ "  AND z.ordem_confeccao = DADOS.ORDEMCONF "
					+ "  AND z.qtde_a_produzir_pacote > 0 "
					+ "  AND z.codigo_estagio IN (" + listEstagio + ")) ";
		}
		
		query += "   GROUP BY DADOS.NIVEL, DADOS.GRUPO, DADOS.SUBGRUPO, DADOS.ITEM, DADOS.DESCRICAO, DADOS.UNDMEDIDA, DADOS.PERIODO, DADOS.ORDEMPROD, "
				+ "		DADOS.ORDEMCONF, DADOS.QTDEORDEM, DADOS.RESERVAS, DADOS.RECEBER";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
	public List<ConsultaPainelPlanejamento> findMateriaisDetalharCompras(String listNivel, String listReferencia, String listTamanho, String listCor, String listComplemento, 
			String listContaEstoq, String listDeposito, String listOrdemProducao, String listEstagio){
		
		String query = " SELECT rownum id, "
				+ "     a.item_100_nivel99 || '.' || a.item_100_grupo || '.' || a.item_100_subgrupo || '.' || a.item_100_item produto, "
				+ "		a.descricao_item descricao, "
				+ "		a.unidade_medida undMedida, "
				+ "		a.num_ped_compra pedido, "
				+ "		TO_CHAR(b.dt_emis_ped_comp, 'DD/MM/YYYY') emissao, "
				+ "		DECODE(b.forn_ped_forne4, 0, SUBSTR(LPAD(b.forn_ped_forne9, 9, 0), 0, 3), SUBSTR(LPAD(b.forn_ped_forne4, 9, 0), 4, 3), SUBSTR(LPAD(b.forn_ped_forne2, 9, 0), 7, 3), "
				+ "		LPAD(b.forn_ped_forne9, 8, 0) || '/' || LPAD(b.forn_ped_forne4, 4, 0) || '-' || LPAD(b.forn_ped_forne2, 2, 0) || ' - ' || c.nome_fornecedor) fornecedor, "
				+ "		TO_CHAR(a.data_prev_entr, 'DD/MM/YYYY') entregaPrevista, "
				+ "		a.periodo_compras periodo, "
				+ "		SUM(a.qtde_pedida_item) qtde, "
				+ "		SUM(a.qtde_saldo_item) saldo "
				+ "  FROM supr_100 a, supr_090 b, supr_010 c, basi_010 d, basi_030 e "
				+ "  WHERE b.pedido_compra = a.num_ped_compra "
				+ "  AND c.fornecedor9 = b.forn_ped_forne9 "
				+ "  AND c.fornecedor4 = b.forn_ped_forne4 "
				+ "  AND c.fornecedor2 = b.forn_ped_forne2 "
				+ "  AND d.nivel_estrutura = a.item_100_nivel99 "
				+ "  AND d.grupo_estrutura = a.item_100_grupo "
				+ "  AND d.subgru_estrutura = a.item_100_subgrupo "
				+ "  AND d.item_estrutura = a.item_100_item "
				+ "  AND e.nivel_estrutura = a.item_100_nivel99 "
				+ "  AND e.referencia = a.item_100_grupo "
				+ "  AND a.cod_cancelamento = 0 "
				+ "  AND a.item_100_nivel99 IN ('1', '4', '7', '9') "
				+ "  AND b.situacao_pedido NOT IN (4,8,9,7) "
				+ "  AND a.situacao_item <> 3 ";
		
		if (!listNivel.equals("")) {
			query += " AND a.item_100_nivel99 IN (" + listNivel + ")";
		}
		
		if (!listReferencia.equals("")) {
			query += " AND a.item_100_grupo IN (" + listReferencia + ")";
		}
		
		if (!listTamanho.equals("")) {
			query += " AND a.item_100_subgrupo IN (" + listTamanho + ")";
		}
		
		if (!listCor.equals("")) {
			query += " AND a.item_100_item IN (" + listCor + ")";
		}
		
		if (!listComplemento.equals("")) {
			query += " AND d.complemento IN (" + listComplemento + ")";
		}
		
		if (!listContaEstoq.equals("")) {
			query += " AND e.conta_estoque IN (" + listContaEstoq + ")";
		}
		
		if (!listDeposito.equals("")) {
			query += " AND a.codigo_deposito IN (" + listDeposito + ") ";
		}
		
		
		if (!listOrdemProducao.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM tmrp_041 j "
					+ "     WHERE j.nivel_estrutura = a.item_100_nivel99 "
					+ "		AND j.grupo_estrutura = a.item_100_grupo "
					+ "		AND j.subgru_estrutura = a.item_100_subgrupo "
					+ "		AND j.item_estrutura = a.item_100_item "
					+ "		AND j.nr_pedido_ordem IN (" + listOrdemProducao + ")) ";
		}
		
		if (!listEstagio.equals("")) {
			query += " AND EXISTS (SELECT 1 FROM pcpc_040 z "
					+ "  WHERE  z.proconf_nivel99 = a.item_100_nivel99 "
					+ "  AND z.proconf_grupo = a.item_100_grupo "
					+ "  AND z.proconf_subgrupo = a.item_100_subgrupo "
					+ "  AND z.proconf_item = a.item_100_item "
					+ "  AND z.qtde_a_produzir_pacote > 0 "
					+ "  AND z.codigo_estagio IN (" + listEstagio + ")) ";
		}
		
		query += "  GROUP BY a.item_100_nivel99, a.item_100_grupo, a.item_100_subgrupo, a.item_100_item, a.descricao_item, a.unidade_medida, "
			  + "  a.num_ped_compra, b.dt_emis_ped_comp, b.forn_ped_forne9, b.forn_ped_forne4, b.forn_ped_forne2, c.nome_fornecedor, a.data_prev_entr, a.periodo_compras, rownum ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPainelPlanejamento.class));
	}
	
}
