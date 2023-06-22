package br.com.live.producao.custom;


import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.model.ConsultaRejeicaoPecaPorTecido;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Repository
public class RejeicaoPecaPorTecidoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public RejeicaoPecaPorTecidoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveNumerica> findAllEstagios() {
		
		String query = " SELECT a.codigo_estagio value, a.codigo_estagio || ' - ' || a.descricao label FROM mqop_005 a GROUP BY a.codigo_estagio, a.descricao "
				+ " ORDER BY a.codigo_estagio ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllOrdensProducao(int ordem) {
		
		String query = " SELECT a.ordem_producao value, a.ordem_producao label FROM pcpc_020 a WHERE a.ordem_producao LIKE '%" + ordem + "%'"
				+ "  AND rownum < 100 ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public String findPeriodoByOP(int ordem) {
		
		String periodo = "";
		
		String query = "  SELECT m.periodo_producao FROM pcpc_020 m WHERE m.ordem_producao = " + ordem;
		
		try {
			periodo = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			periodo = "";
		}
		return periodo;
	}
	
	public String findReferenciaByOP(int ordem) {
		
		String referencia = "";
		
		String query = "  SELECT j.referencia_peca FROM pcpc_020 j "
				+ "		WHERE j.ordem_producao = " + ordem;
		
		try {
			referencia = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			referencia = "";
		}
		return referencia;
	}
	
	public List<ConsultaRejeicaoPecaPorTecido> findAllRejeicoes(){
		
		String query = " SELECT a.id id, "
				+ "       TO_CHAR(a.data_rejeicao, 'DD/MM/YYYY') dataRejeicao, "
				+ "       b.nome usuario, "
				+ "       c.descricao estagio, "
				+ "       a.turno turno, "
				+ "       a.ordem_producao ordemProducao, "
				+ "       a.periodo periodo, "
				+ "       a.grupo_estrutura grupoEstrutura, "
				+ "       a.subgru_estrutura subgruEstrutura, "
				+ "       a.item_estrutura itemEstrutura, "
				+ "       d.narrativa tecido, "
				+ "       a.parte_peca partePeca, "
				+ "       a.quantidade quantidade, "
				+ "       e.descricao motivo "
				+ "		FROM orion_cfc_330 a, orion_001 b, mqop_005 c, basi_010 d, efic_040 e "
				+ "		WHERE b.id = a.usuario "
				+ "		AND c.codigo_estagio = a.estagio "
				+ "		AND d.nivel_estrutura = a.nivel_estrutura "
				+ "		AND d.grupo_estrutura = a.grupo_estrutura "
				+ "		AND d.subgru_estrutura = a.subgru_estrutura "
				+ "		AND d.item_estrutura = a.item_estrutura "
				+ "		AND e.codigo_motivo = a.motivo "
				+ "		AND e.codigo_estagio = a.estagio ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRejeicaoPecaPorTecido.class));
	}
	
	public List<ConteudoChaveAlfaNum> findTecidosByOrdemProd(int ordem){
		
		String query = " SELECT a.pano_fin_nivel99 || '.' || a.pano_fin_grupo || '.' || a.pano_fin_subgrupo || '.' || a.pano_fin_item value, "
				+ "		a.pano_fin_nivel99 || '.' || a.pano_fin_grupo || '.' || a.pano_fin_subgrupo || '.' || a.pano_fin_item || ' - ' || b.narrativa label "
				+ "		FROM pcpt_025 a, basi_010 b "
				+ "		WHERE b.nivel_estrutura = a.pano_fin_nivel99 "
				+ "		AND b.grupo_estrutura = a.pano_fin_grupo "
				+ "		AND b.subgru_estrutura = a.pano_fin_subgrupo "
				+ "		AND b.item_estrutura = a.pano_fin_item "
				+ "		AND a.ordem_producao = " + ordem
				+ "		GROUP BY a.pano_fin_nivel99, a.pano_fin_grupo, a.pano_fin_subgrupo, a.pano_fin_item, b.narrativa ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findTecidosByOrdemDest(int ordem){
		
		
		String query = " SELECT DECODE(c.nivel_substituto, 0, "
				+ "              c.nivel_origem || '.' || c.grupo_origem || '.' || c.subgru_origem || '.' || c.item_origem, "
				+ "              c.nivel_substituto || '.' || c.grupo_substituto || '.' || c.subgru_substituto || '.' || c.item_substituto) value, "
				+ "       DECODE(c.nivel_substituto, 0, "
				+ "              c.nivel_origem || '.' || c.grupo_origem || '.' || c.subgru_origem || '.' || c.item_origem || ' - ' || d.narrativa, "
				+ "              c.nivel_substituto || '.' || c.grupo_substituto || '.' || c.subgru_substituto || '.' || c.item_substituto || ' - ' || d.narrativa) label "
				+ "    FROM pcpc_029 c, basi_010 d "
				+ "    WHERE d.nivel_estrutura = c.nivel_origem "
				+ "    AND d.grupo_estrutura = c.grupo_origem "
				+ "    AND d.subgru_estrutura = c.subgru_origem "
				+ "    AND d.item_estrutura = c.item_origem "
				+ "    AND c.ordem_destino = " + ordem
				+ "    GROUP BY c.nivel_origem, c.grupo_origem, c.subgru_origem, c.item_origem, c.nivel_substituto, c.grupo_substituto, c.subgru_substituto, c.item_substituto, d.narrativa ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findPartesDaPeca(int ordem) {
		
		String query = "  SELECT f.descricao_parte value, "
				+ "       f.descricao_parte label "
				+ "    FROM pcpc_020 e, basi_060 f "
				+ "    WHERE f.grupo_estrutura = e.referencia_peca "
				+ "    AND f.alternativa_item = e.alternativa_peca "
				+ "    AND e.ordem_producao = " + ordem
				+ "    GROUP BY f.descricao_parte ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveNumerica> findMotivosByEstagio(int estagio) {
		
		String query = "  SELECT g.codigo_motivo value, "
				+ "       g.codigo_motivo || ' - ' || g.descricao label "
				+ "   FROM efic_040 g "
				+ "   WHERE g.codigo_estagio = " + estagio
				+ "   GROUP BY g.codigo_motivo, g.descricao "
				+ "   ORDER BY g.codigo_motivo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findTamanhosByReferenciaOrdemProd(int ordem) {
		
		String query = " SELECT i.subgru_estrutura value, i.subgru_estrutura label FROM pcpc_020 h, basi_010 i "
				+ "		WHERE i.grupo_estrutura = h.referencia_peca "
				+ "		AND h.ordem_producao = " + ordem
				+ "	  GROUP BY i.subgru_estrutura ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findCorByReferenciaOrdemProd(int ordem) {
		
		String query = " SELECT i.item_estrutura value, i.item_estrutura label FROM pcpc_020 h, basi_010 i "
				+ "		WHERE i.grupo_estrutura = h.referencia_peca "
				+ "		AND h.ordem_producao = " + ordem
				+ "	  GROUP BY i.item_estrutura ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public void gravarSystextil(Date dataRejeicao, int codEstagio, int turno, int ordemProducao, int periodo, String nivel, String grupo, 
			String subgrupo, String item, int quantidade, int codMotivo) {	
		
		String query = " INSERT INTO efic_100 (DATA_REJEICAO, PROD_REJ_NIVEL99, PROD_REJ_GRUPO, PROD_REJ_SUBGRUPO, PROD_REJ_ITEM, " +
                " CODIGO_ESTAGIO, CODIGO_MOTIVO, CLASSIFICACAO, PERIODO_PRODUCAO, NUMERO_ORDEM, QUANTIDADE, AREA_PRODUCAO, HORA_INICIO, TURNO, " +
                " ESTAGIO_DIGITACAO, LIVE_TIPO_LCTO) VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, 1, sysdate, ?, 55, '2A_QUALIDADE') ";
		
        jdbcTemplate.update(query, dataRejeicao, nivel, grupo, subgrupo, item, codEstagio, codMotivo, periodo, ordemProducao, quantidade, turno);
	}
	
	public void deletarSystextil(Date dataRejeicao, int estagio, int turno, int ordemProducao, int periodo, String nivelEstrutura, String grupoEstrutura, String subgruEstrutura, 
			String itemEstrutura, int quantidade, int motivo) {
		
		String query = "DELETE FROM efic_100 WHERE TO_CHAR(DATA_REJEICAO, 'DD/MM/YYYY') = '" + FormataData.parseDateToString(dataRejeicao) + "'"
				+ "   AND NUMERO_ORDEM = " + ordemProducao + "" 
				+ "   AND PROD_REJ_NIVEL99 = '" + nivelEstrutura + "'" 
				+ "   AND PROD_REJ_GRUPO = '" + grupoEstrutura + "'" 
				+ "   AND PROD_REJ_SUBGRUPO = '" + subgruEstrutura + "'"
				+ "   AND PROD_REJ_ITEM = '" + itemEstrutura +"'" 
				+ "   AND CODIGO_ESTAGIO = " + estagio +"" 
				+ "   AND CODIGO_MOTIVO = " + motivo + "" 
				+ "   AND PERIODO_PRODUCAO = " + periodo +"" 
				+ "   AND QUANTIDADE = " + quantidade +"" 
				+ "   AND TURNO = " + turno +"";
		
		jdbcTemplate.update(query);		
	}

}
