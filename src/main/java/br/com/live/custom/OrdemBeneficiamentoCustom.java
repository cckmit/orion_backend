package br.com.live.custom;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.AnaliseQualidade;
import br.com.live.model.OrdemBeneficiamentoItens;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class OrdemBeneficiamentoCustom {
	
	private JdbcTemplate jdbcTemplate;
	
	public OrdemBeneficiamentoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveAlfaNum> findMaquinas(String maquina) {
		String query = " SELECT a.grupo_maquina || '.' || b.subgrupo_maquina || '.' || c.numero_maquina value, "
				+ "   a.grupo_maquina || '.' || b.subgrupo_maquina || '.' || c.numero_maquina || ' ' || a.nome_grupo_maq || ' ' || b.nome_sbgrupo_maq || ' ' || c.nome_maquina label "
				+ "   FROM mqop_010 a, mqop_020 b, mqop_030 c "
				+ "   WHERE a.grupo_maquina = b.grupo_maquina "
				+ "   AND c.maq_sub_grupo_mq = a.grupo_maquina "
				+ "   AND c.maq_sub_sbgr_maq = b.subgrupo_maquina "
				+ "   AND a.grupo_maquina NOT LIKE '%.%' "
				+ "   AND a.grupo_maquina || '.' || b.subgrupo_maquina || '.' || c.numero_maquina LIKE '%" + maquina + "%'"
				+ "	  ORDER BY a.grupo_maquina, b.subgrupo_maquina, c.numero_maquina, c.nome_maquina";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<OrdemBeneficiamentoItens> findAllItensOrdens(String usuario) {
		String query = " SELECT a.id,"
				+ "		  a.pano_sbg_nivel99 || '.' || a.pano_sbg_grupo || '.' || a.pano_sbg_subgrupo || '.' || a.pano_sbg_item || ' - ' || b.descr_referencia produto, "
				+ "       a.ordem_producao ordemProducao, "
				+ "       a.alternativa_item alternativaItem, "
				+ "       a.roteiro_item roteiroItem, "
				+ "       a.qtde_rolos rolos, "
				+ "       a.qtde_quilos quilos, "
				+ "       a.und_medida um, "
				+ "       f.largura_1 largura, "
				+ "       f.gramatura_1 gramatura, "
				+ "       f.rendimento rendimento, "
				+ "       a.deposito || ' - ' || g.descricao descDeposito, "
				+ "       a.observacao observacao "
				+ "       FROM orion_bnf_110 a, basi_030 b, basi_020 f, basi_205 g "
				+ "       WHERE b.nivel_estrutura = a.pano_sbg_nivel99 "
				+ "       AND b.referencia = a.pano_sbg_grupo "
				+ "       AND f.tamanho_ref = a.pano_sbg_subgrupo "
				+ "       AND f.basi030_nivel030 = a.pano_sbg_nivel99 "
				+ "       AND f.basi030_referenc = a.pano_sbg_grupo "
				+ "       AND g.codigo_deposito = a.deposito "
				+ "       AND a.usuario = '" + usuario + "'";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemBeneficiamentoItens.class));
	}
	public int findCountItens(String usuario) {
		int numItens = 0;
		String query = " SELECT COUNT(*) FROM orion_bnf_110 a WHERE a.ordem_producao = 0 AND a.usuario = '" + usuario + "'";
		
		try {
			numItens = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			numItens = 0;
		}
		return numItens;
	}
	
	public List<ConteudoChaveAlfaNum> findProdutos(String produto) {
		String query = " SELECT a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura value, "
				+ "       a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura || ' - ' || b.descr_referencia  label FROM basi_010 a, basi_030 b "
				+ "       WHERE b.nivel_estrutura = a.nivel_estrutura "
				+ "       AND b.referencia = a.grupo_estrutura "
				+ "       AND a.nivel_estrutura = 2 "
				+ "       AND a.nivel_estrutura || '.' ||  a.grupo_estrutura || '.' ||  a.subgru_estrutura || '.' || a.item_estrutura LIKE '%" + produto + "%'"
				+ "       GROUP BY a.nivel_estrutura, a.grupo_estrutura, a.subgru_estrutura, a.item_estrutura, b.descr_referencia "
				+ "       ORDER BY a.grupo_estrutura ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findDepositos() {
		String query = " SELECT d.codigo_deposito value, "
				+ "       d.codigo_deposito || ' - ' || d.descricao label FROM basi_205 d "
				+ "       WHERE d.descricao NOT LIKE '%(IN)%' "
				+ "       GROUP BY d.codigo_deposito, d.descricao "
				+ "       ORDER BY d.codigo_deposito ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveNumerica> findAllOrdensBenef(int ordem) {
		
		String query = " SELECT a.ordem_producao value, a.ordem_producao label FROM pcpb_010 a WHERE a.ordem_producao LIKE '%" + ordem + "%'"
				+ "  AND EXISTS (SELECT 1 FROM pcpb_070 s WHERE s.ordem_producao = a.ordem_producao) ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public long findNextId(){
		long nextId = 0;
		
		String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_bnf_110 a ";
		try {
			nextId = jdbcTemplate.queryForObject(query, Long.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}
	
	public List<ConteudoChaveNumerica> findPeriodo(int periodoProducao) {
		String query = " SELECT a.periodo_producao value,"
				+ "             a.periodo_producao || '  -  Dê: ' || TO_CHAR(a.data_ini_periodo, 'DD/MON/YYYY') || ' Até: ' || TO_CHAR(a.data_fim_periodo, 'DD/MON/YYYY') label "
				+ "      FROM pcpc_010 a "
				+ "      WHERE a.periodo_producao LIKE '%" + periodoProducao + "%'";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public int findNextOrdemBeneficiamento(){
		int nextOrdem = 0;
		
		String query = " SELECT a.ordem_benefic + 1 FROM empr_001 a ";
		String queryUpdate = " UPDATE empr_001 a SET a.ordem_benefic = ? ";
		
		try {
			nextOrdem = jdbcTemplate.queryForObject(query, Integer.class);
			jdbcTemplate.update(queryUpdate, nextOrdem);
		} catch (Exception e) {
			nextOrdem = 0;
		}
		return (int) nextOrdem;
	}
	
	public int findNextSequencia(){
		
		int nextSeq = 0;
		String query = " SELECT NVL(MAX(a.sequencia), 0) + 1 FROM orion_bnf_110 a ";
		
		try {
			nextSeq = jdbcTemplate.queryForObject(query, Integer.class);
			
		} catch (Exception e) {
			nextSeq = 0;
		}
		return (int) nextSeq;
	}
	
	public void gerarOrdemBeneficMqop010(int periodoProducao, Date dataPrograma, Date previsaoTermino, String grupo, String subgrupo, int numeroMaquina, int tipoOrdem, int ordem) {
		
		String query = " insert into pcpb_010(ordem_producao, periodo_producao, data_programa, previsao_termino, grupo_maquina, subgrupo_maquina, numero_maquina, tipo_ordem) values (?,?,?,?,?,?,?,?) "; 
		
		try {
			jdbcTemplate.update(query, ordem, periodoProducao, dataPrograma, previsaoTermino, grupo, subgrupo, numeroMaquina, tipoOrdem);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void gerarOrdemBeneficMqop020(String nivel, String referencia, String tamanho, String cor, float rolos, float quilos, float largura, float gramatura, float rendimento, 
			int periodoProducao, String observacao, int alternativa, int roteiro, int ordem) {
		
		String query = " INSERT INTO pcpb_020(pano_sbg_nivel99, pano_sbg_grupo, pano_sbg_subgrupo, pano_sbg_item, alternativa_item, "
				+ "	  roteiro_opcional, qtde_rolos_prog, qtde_quilos_prog, largura_tecido, ordem_producao, gramatura, rendimento, observacao1)"
				+ "   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		
		try {
			jdbcTemplate.update(query, nivel, referencia, tamanho, cor, alternativa, roteiro, rolos, quilos, largura, ordem, gramatura, rendimento, observacao);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void gerarOrdemBeneficMqop030(String nivel, String referencia, String tamanho, String cor, float rolos, float quilos, 
			int periodoProducao, int deposito, int alternativa, int roteiro, int ordem) {
		
		String query = " INSERT INTO pcpb_030(ordem_producao, pano_nivel99, pano_grupo, pano_subgrupo, pano_item, codigo_deposito, periodo_producao, \r\n"
				+ "  qtde_rolos_prog, qtde_quilos_prog, alternativa, roteiro)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		
		try {
			jdbcTemplate.update(query, ordem, nivel, referencia, tamanho, cor, deposito, periodoProducao, rolos, quilos, alternativa, roteiro);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateOrdemProducao(String id, int ordem) {
		
		String query = " UPDATE orion_bnf_110 SET ordem_producao = ? WHERE id = ? "; 
		
		try {
			jdbcTemplate.update(query, ordem, id);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public boolean existsRoteiro(String nivel, String grupo, String sub, String item, int alternativaItem, int roteiroItem) {
		int encontrou = 0;

		String query = " select 1 from mqop_050 "
				+ " where mqop_050.nivel_estrutura = '" + nivel + "' "
				+ " and mqop_050.grupo_estrutura   = '" + grupo + "'"
				+ " and (mqop_050.subgru_estrutura = '" + sub + "' or mqop_050.subgru_estrutura = '000') "
				+ " and (mqop_050.item_estrutura   = '" + item + "' or mqop_050.item_estrutura   = '000000') "
				+ " and mqop_050.numero_alternati  = " + alternativaItem
				+ " and mqop_050.numero_roteiro    = " + roteiroItem
				+ " and rownum = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}
	
	public List<AnaliseQualidade> findInfoRolos(int ordem) {
		
		String query = " SELECT c.codigo_rolo rolo, "
				+ "       c.peso_bruto peso, "
				+ "       c.largura largura, "
				+ "       c.gramatura gramatura, "
				+ "       NVL(c.cod_nuance, 0) nuance "
				+ "     FROM pcpt_020_025 c, pcpt_020 d, basi_010 e "
				+ "		WHERE d.ordem_producao = c.ordem_producao "
				+ "		AND d.area_producao = c.area_producao "
				+ "		AND d.codigo_rolo = c.codigo_rolo "
				+ "		AND e.nivel_estrutura = d.panoacab_nivel99 "
				+ "		AND e.grupo_estrutura = d.panoacab_grupo "
				+ "		AND e.subgru_estrutura = d.panoacab_subgrupo "
				+ "		AND e.item_estrutura = d.panoacab_item "
				+ "		AND c.ordem_producao = ? "
				+ "     ORDER BY c.codigo_rolo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AnaliseQualidade.class), ordem);
	}
	
	public List<AnaliseQualidade> findAnalise(int ordem) {
		
		String query = " SELECT NVL(a.ordem_producao, 0) ordemTecelagem, "
				+ "		a.numero_ob ordemBeneficiamento, "
				+ "		b.nivel || '.' || b.grupo || '.' || b.subgrupo || '.' || b.item  codigoTecido, "
				+ "		c.narrativa descricaoTecido, "
				+ "		b.data_01 dataOrdem "
				+ "		FROM pcpt_020_025 a, basi_400 b, basi_010 c "
				+ "		WHERE b.grupo = a.panoacab_grupo "
				+ "		AND b.subgrupo = a.panoacab_subgrupo "
				+ "		AND b.item = a.panoacab_item "
				+ "		AND c.nivel_estrutura = a.panoacab_nivel99 "
				+ "		AND c.grupo_estrutura = a.panoacab_grupo "
				+ "		AND c.subgru_estrutura = a.panoacab_subgrupo "
				+ "		AND c.item_estrutura = a.panoacab_item "
				+ "		AND b.tipo_informacao = 12 "
				+ "		AND a.numero_ob = " + ordem
				+ "		GROUP BY a.ordem_producao, a.numero_ob, b.nivel, b.grupo, b.subgrupo, b.item, c.narrativa, b.data_01 ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AnaliseQualidade.class));
	}
	
	public List<AnaliseQualidade> findInfoLote(int ordem) {
		
		String query = " SELECT m.codigo_informacao cod, "
				+ "       n.descricao descricaoLote, "
				+ "       m.valor_01 valorLote, "
				+ "       o.valor_02 min, "
				+ "       o.valor_03 max, "
				+ "       DECODE(m.situacao, 0, 'ACEITO/APROVADO', "
				+ "       DECODE(m.situacao, 1, 'REPROCESSAR', "
				+ "       DECODE(m.situacao, 2, 'REJEITADO', "
				+ "       DECODE(m.situacao, 3, 'A SER INFORMADO')))) situacao "
				+ "       FROM pcpb_075 M, basi_400 o, hdoc_001 n "
				+ "       WHERE o.nivel = m.nivel_estrutura "
				+ "       AND o.grupo = m.grupo_estrutura "
				+ "       AND o.subgrupo = m.subgru_estrutura "
				+ "       AND (o.item = m.item_estrutura OR o.item = '000000') "
				+ "       AND o.tipo_informacao = m.tipo_informacao "
				+ "       AND o.codigo_informacao = m.codigo_informacao "
				+ "       AND n.codigo = m.codigo_informacao "
				+ "       AND n.tipo = 33 "
				+ "       AND o.tipo_informacao = 33 "
				+ "       AND m.ordem_producao = ? ";
	
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AnaliseQualidade.class), ordem);
	}
}
