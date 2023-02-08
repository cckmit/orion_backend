package br.com.live.custom;

import java.util.List;

import br.com.live.model.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PedidoCustomizado;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class ConfeccaoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ConfeccaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int findNextIdOrdem() {
		int nextId = 0;
		
		String query = " select nvl((max(b.id)),0)+1 from orion_cfc_221 b ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}
	
	public List<ConteudoChaveNumerica> findPacotesOrdem(int ordemProducao, boolean flagTodos) {
		String query = "";

		if (flagTodos) {
			query = " select 0 value, '0 - TODOS OS PACOTES' label from dual "
					+ " union all "
					+ " select a.ordem_confeccao value, to_char(a.ordem_confeccao) label "
					+ " from pcpc_040 a, pcpc_020 b "
					+ " where b.ordem_producao = a.ordem_producao "
					+ " and a.ordem_producao = " + ordemProducao
					+ " group by a.ordem_confeccao ";
		} else {
			query = " select a.ordem_confeccao value, to_char(a.ordem_confeccao) label "
					+ " from pcpc_040 a, pcpc_020 b "
					+ " where b.ordem_producao = a.ordem_producao "
					+ " and a.ordem_producao = " + ordemProducao
					+ " group by a.ordem_confeccao ";
		}
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConsultaObservacaoOrdemPacote> findAllObsWithQuantidade() {
		String query = " select c.id, c.estagio || ' - ' || g.descricao estagio, c.ordem_producao ordemProducao, c.ordem_confeccao ordemConfeccao, sum(f.qtde_pecas_prog) quantidade, c.tipo_observacao || ' - ' || d.descricao tipoObservacao, "
				+ " c.observacao_adicional observacaoAdicional from orion_cfc_220 c, orion_cfc_221 d, pcpc_020 e, pcpc_040 f, mqop_005 g "
				+ " where c.tipo_observacao = d.id "
				+ " and e.ordem_producao = c.ordem_producao "
				+ " and f.ordem_producao = c.ordem_producao "
				+ " and (f.ordem_confeccao = c.ordem_confeccao OR c.ordem_confeccao = 0) "
				+ " and f.codigo_estagio = 35 "
				+ " and (f.qtde_a_produzir_pacote > 0 or f.qtde_conserto > 0) "
				+ " and g.codigo_estagio = c.estagio "
				+ " group by c.id, c.estagio, c.ordem_producao, c.ordem_confeccao, c.tipo_observacao, d.descricao, c.observacao_adicional, g.descricao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaObservacaoOrdemPacote.class));
	}

	public long findNextIdRestricao() {
		long nextId = 0;

		String query = " select nvl(max(a.id), 0) + 1 from orion_cfc_270 a ";
		try {
			nextId = jdbcTemplate.queryForObject(query, Long.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public long findNextIdRestricaoRolo() {
		long nextId = 0;

		String query = " select nvl(max(a.id), 0) + 1 from orion_cfc_271 a ";
		try {
			nextId = jdbcTemplate.queryForObject(query, Long.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public int validarRestricaoRolo(int codigoRolo, int restricao) {
		int existeDados = 0;

		String query = " select 1 from orion_cfc_271 b "
				+ " where b.codigo_rolo = " + codigoRolo
				+ " and b.restricao = " + restricao;
		try {
			existeDados = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existeDados = 0;
		}
		return existeDados;
	}

	public List<ConteudoChaveNumerica> findOptionsRestricao() {
		String query = " select a.id value, a.id || ' - ' || a.descricao label from orion_cfc_270 a "
				+ " order by a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<Integer> findRolosByOrdem(int ordermProducao) {
		String query = " select b.codigo_rolo from pcpt_020 b " +
				" where b.ordem_producao = " + ordermProducao;
		return jdbcTemplate.queryForList(query, Integer.class);
	}

	public List<ConteudoChaveNumerica> findOptionLeitorOrdensBeneficiamento(String leitor) {
		String query = " select e.ordem_producao value, e.ordem_producao label from pcpt_020 e, pcpb_010 m " +
				" where e.ordem_producao is not null " +
				" and m.ordem_producao = e.ordem_producao " +
				" and m.situacao_ordem = 1 " +
				" and e.ordem_producao like '%" + leitor + "%' " +
				" group by e.ordem_producao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConteudoChaveNumerica> findOptionLeitorRolos(String leitor) {
		String query = " select a.codigo_rolo value, a.codigo_rolo label from pcpt_020 a " +
				"where a.codigo_rolo like '%" + leitor + "%' ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConsultaRestricoesRolo> findAllRestricoesPorRolo() {
		String query = " select a.id, b.ordem_producao ordemProd, b.codigo_rolo rolo, b.codigo_deposito deposito, c.id || ' - ' || c.descricao restricao " +
				" from orion_cfc_271 a, pcpt_020 b, orion_cfc_270 c " +
				" where a.codigo_rolo = b.codigo_rolo " +
				" and c.id = a.restricao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRestricoesRolo.class));
	}
	
	public List<EstagioProducao> findAllEstagio() {
		String query = " SELECT a.codigo_estagio estagio, a.descricao FROM mqop_005 a GROUP BY a.codigo_estagio, a.descricao "
				+ " ORDER BY a.codigo_estagio ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
	}
	
	public List<EstagioProducao> findAllEstagioMetas() {
		String query = " SELECT ESTAGIOS.estagio, ESTAGIOS.descricao FROM "
				+ " ( "
				+ " SELECT a.codigo_estagio estagio, a.descricao FROM mqop_005 a "
				+ " UNION "
				+ " SELECT 101 estagio, 'COLETA PARA FATURAMENTO' descricao FROM dual ESTAGIOS "
				+ " UNION "
				+ " SELECT 102 estagio, 'ABASTECIMENTO' descricao FROM dual) ESTAGIOS "
				+ " GROUP BY ESTAGIOS.estagio, ESTAGIOS.descricao "
				+ " ORDER BY ESTAGIOS.estagio ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
	}
	
	public List<ConsultaPedidoCustomizado> findAllPainelPedCustomBySolic(int solicitacao) {
		String query = " SELECT a.id, a.solicitacao, a.pedido_venda, a.data_emis_venda, a.data_entr_venda, "
				+ "       DECODE(a.cli_ped_cgc_cli4, 0, LPAD(a.cli_ped_cgc_cli9, 9, 0) || '-' || LPAD(a.cli_ped_cgc_cli2, 2, 0) || ' - ' || b.nome_cliente, "
				+ "       LPAD(a.cli_ped_cgc_cli9, 9, 0) || '/' || LPAD(a.cli_ped_cgc_cli4, 4, 0) || '-' || LPAD(a.cli_ped_cgc_cli2, 2, 0) || ' - ' || b.nome_cliente) cliente, "
				+ "       a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item, a.codigo_deposito, a.qtde_pedida, a.caminho_arquivo, a.ordem_producao, a.periodo, a.situacao, a.selecao, a.data_registro, "
				+ "       a.alternativa, a.roteiro, a.seq_item_pedido, a.flag_imagem, a.ordem_tamanho FROM orion_cfc_280 a, pedi_010 b "
				+ "       WHERE b.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "       AND b.cgc_4 = cli_ped_cgc_cli4 "
				+ "       AND b.cgc_2 = cli_ped_cgc_cli2 "
				+ "       AND a.solicitacao = '" + solicitacao + "'";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPedidoCustomizado.class));
	}
	
	public List<ConsultaPedidoCustomizado> findAllPainelPedCustomByDate() {
		String query = " SELECT a.id, a.solicitacao, a.pedido_venda, a.data_emis_venda, a.data_entr_venda, "
				+ "       DECODE(a.cli_ped_cgc_cli4, 0, LPAD(a.cli_ped_cgc_cli9, 9, 0) || '-' || LPAD(a.cli_ped_cgc_cli2, 2, 0) || ' - ' || b.nome_cliente, "
				+ "       LPAD(a.cli_ped_cgc_cli9, 9, 0) || '/' || LPAD(a.cli_ped_cgc_cli4, 4, 0) || '-' || LPAD(a.cli_ped_cgc_cli2, 2, 0) || ' - ' || b.nome_cliente) cliente, "
				+ "       a.cd_it_pe_grupo, a.cd_it_pe_subgrupo, a.cd_it_pe_item, a.codigo_deposito, a.qtde_pedida, a.caminho_arquivo, a.ordem_producao, a.periodo, a.situacao, a.selecao, a.data_registro, "
				+ "       a.alternativa, a.roteiro, a.seq_item_pedido, a.flag_imagem, a.ordem_tamanho FROM orion_cfc_280 a, pedi_010 b "
				+ "       WHERE b.cgc_9 = a.cli_ped_cgc_cli9 "
				+ "       AND b.cgc_4 = cli_ped_cgc_cli4 "
				+ "       AND b.cgc_2 = cli_ped_cgc_cli2 " 
				+ "       AND a.data_registro = TO_CHAR(sysdate, 'DD/MON/YYYY')";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPedidoCustomizado.class));
	}
	
	public List<ConsultaPedidoCustomizado> findPedidosCustomizado() {
		String query = " SELECT a.pedido_venda, a.data_emis_venda, a.data_entr_venda, a.cli_ped_cgc_cli9, a.cli_ped_cgc_cli4, a.cli_ped_cgc_cli2, b.cd_it_pe_grupo, "
				+ "        b.cd_it_pe_subgrupo, b.cd_it_pe_item, b.codigo_deposito, b.qtde_pedida, b.seq_item_pedido, c.ordem_tamanho, TRUNC(sysdate) data_registro "
				+ "        FROM pedi_100 a , pedi_110 b, basi_220 c "
				+ "        WHERE a.pedido_venda = b.pedido_venda "
				+ "        AND a.tecido_peca  = '1' "
				+ "        AND b.cd_it_pe_grupo like 'LC%' "
				+ "        AND a.cod_cancelamento = 0 "
				+ "		   AND b.cod_cancelamento = 0 "
				+ "		   AND c.tamanho_ref  = b.cd_it_pe_subgrupo "
				+ "		   AND a.data_emis_venda = TRUNC(sysdate) "
				+ "        AND NOT EXISTS (SELECT 1 FROM orion_cfc_280 b "
				+ "                               WHERE TRUNC(b.data_emis_venda) = TRUNC(a.data_emis_venda) "
				+ "                               AND b.pedido_venda = a.pedido_venda) ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPedidoCustomizado.class));
	}
	
	public List<PedidoCustomizado> findAllPedidosCustom() {
		String query = " select s.solicitacao, s.data_registro from  orion_cfc_280 s GROUP BY s.solicitacao, s.data_registro ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PedidoCustomizado.class));
	}
	
	public List<PedidoCustomizado> findSolicitacaoMaxDia() {
		String query = " SELECT s.solicitacao FROM orion_cfc_280 s  WHERE s.data_registro = TRUNC(sysdate) "
				+ "      GROUP BY s.solicitacao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PedidoCustomizado.class));
	}

	public List<EtiquetasDecoracao> consultaEtiquetasDecoracao(int ordemProducao, List<ConteudoChaveNumerica> pacotes) {
		String query = " SELECT p.ORDEM_PRODUCAO ordemProducao, p.ORDEM_CONFECCAO ordemConfeccao, p.PERIODO_PRODUCAO periodo, p.PROCONF_NIVEL99 nivel, p.PROCONF_GRUPO referencia, p.PROCONF_SUBGRUPO tamanho, p.PROCONF_ITEM cor, " +
				" p.QTDE_PECAS_PROG quantidade, c.roteiro_peca roteiro, c.alternativa_peca  alternativa " +
				" FROM PCPC_040 p, BASI_010 b, pcpc_020 c " +
				" WHERE p.ORDEM_PRODUCAO = " + ordemProducao;

				if (pacotes.size() > 0) {
					query += " AND p.ORDEM_CONFECCAO in (" + ConteudoChaveNumerica.parseValueToString(pacotes)+ ") ";
				}

				query += " AND b.NIVEL_ESTRUTURA = p.PROCONF_NIVEL99 " +
				" AND b.GRUPO_ESTRUTURA = p.PROCONF_GRUPO  " +
				" AND b.SUBGRU_ESTRUTURA = p.PROCONF_SUBGRUPO " +
				" AND b.ITEM_ESTRUTURA = p.PROCONF_ITEM " +
				" AND c.ORDEM_PRODUCAO = p.ORDEM_PRODUCAO " +
				" GROUP BY p.ORDEM_PRODUCAO, p.ORDEM_CONFECCAO , p.PERIODO_PRODUCAO ,p.PROCONF_NIVEL99 , p.PROCONF_GRUPO , p.PROCONF_SUBGRUPO , p.PROCONF_ITEM, p.QTDE_PECAS_PROG, c.roteiro_peca, c.alternativa_peca " +
				" ORDER BY p.ORDEM_PRODUCAO, p.ORDEM_CONFECCAO ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EtiquetasDecoracao.class));
	}
}
 