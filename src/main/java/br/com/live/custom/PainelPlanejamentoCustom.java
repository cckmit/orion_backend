package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
	
	public List<ConteudoChaveNumerica> findAllArtigoCotas(String artigoCotas) {
		
		String query = " SELECT e.artigo_cotas value, e.artigo_cotas || ' - ' || e.descr_artigo label "
				+ "    FROM basi_295 e "
				+ "    WHERE e.artigo_cotas || ' - ' || e.descr_artigo LIKE '%" + artigoCotas + "%'";

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
		
		String query = " select i.codigo_deposito value, i.codigo_deposito || ' - ' || i.descricao label from basi_205 i where i.descricao NOT LIKE '%(IN)%' ";

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
	
	public List<ConteudoChaveNumerica> findAllNumInterno() {
		
		String query = " SELECT y.numero_controle value, y.numero_controle label from pedi_100 y "
				+ "		WHERE y.situacao_venda  <> 10 "
				+ "     AND y.numero_controle IS NOT NULL "
				+ "		GROUP BY y.numero_controle "
				+ "		ORDER BY y.numero_controle ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
}
