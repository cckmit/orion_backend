package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaTitulosComissao;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class FechamentoComissaoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public FechamentoComissaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveAlfaNum> findAllRepresentantes(String representante) {
		
		String query = " SELECT a.cod_rep_cliente value, "
				+ "    a.cod_rep_cliente || ' - ' || a.nome_rep_cliente label "
				+ "    FROM pedi_020 a WHERE a.tipo_repr = 3 "
				+ "    AND cod_rep_cliente || ' - ' || a.nome_rep_cliente LIKE '%" + representante.toUpperCase() + "%'";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		
	}
	
	public List<ConsultaTitulosComissao> findTitulosAtrasadosAnalitico(String dataInicio, String representante) {
		
		String query = " SELECT a.portador_duplic portador, "
				+ "      a.data_emissao dataEmissao, "
				+ "      a.cod_rep_cliente || ' - ' || c.nome_rep_cliente representante, "
				+ "      a.tipo_titulo tipoTitulo, "
				+ "      LPAD(a.cli_dup_cgc_cli9, 8, 0) || '/' || LPAD(a.cli_dup_cgc_cli4, 4, 0) || '-' || LPAD(a.cli_dup_cgc_cli2, 2, 0) || ' - ' || b.nome_cliente cliente, "
				+ "      a.num_duplicata || '-' || a.seq_duplicatas Titulo, "
				+ "      a.data_venc_duplic vencimento, "
				+ "      a.percentual_comis percComissao, "
				+ "      a.valor_duplicata valorEmAberto, "
				+ "      a.valor_comis valorComissao "
				+ "      FROM fatu_070 a, pedi_010 b, pedi_020 c "
				+ "      WHERE b.cgc_9 = a.cli_dup_cgc_cli9 "
				+ "      AND b.cgc_4 = a.cli_dup_cgc_cli4 "
				+ "      AND b.cgc_2 = a.cli_dup_cgc_cli2 "
				+ "      AND c.cod_rep_cliente = a.cod_rep_cliente "
				+ "      AND a.situacao_duplic IN (0, 3) "
				+ "      AND a.portador_duplic IN (86, 103, 106, 109, 200, 237, 341, 500, 748) "
				+ "      AND a.data_venc_duplic <= TO_DATE('" + dataInicio + "', 'DD/MM/YYYY)') "
				+ "      AND a.cod_rep_cliente = " + representante;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosComissao.class));
		
	}
	
	public List<ConsultaTitulosComissao> findTitulosAtrasadosSintetico(String dataInicio, String dataAnterior, String representante) {
		
		String query = " SELECT DADOS.REPRESENTANTE representante, "
				+ "      SUM(DADOS.MESANTERIOR) mesAnterior, "
				+ "      SUM(DADOS.MESATUAL) mesAtual, "
				+ "      (SUM(DADOS.MESANTERIOR) - SUM(DADOS.MESATUAL)) saldo FROM("
				+ "       SELECT a.cod_rep_cliente || ' - ' || c.nome_rep_cliente representante, "
				+ "              SUM(a.valor_duplicata) mesAnterior, "
				+ "              SUM(0) mesAtual "
				+ "       FROM fatu_070 a, pedi_010 b, pedi_020 c "
				+ "       WHERE b.cgc_9 = a.cli_dup_cgc_cli9 "
				+ "       AND b.cgc_4 = a.cli_dup_cgc_cli4 "
				+ "       AND b.cgc_2 = a.cli_dup_cgc_cli2 "
				+ "       AND c.cod_rep_cliente = a.cod_rep_cliente "
				+ "       AND a.situacao_duplic IN (0, 3) "
				+ "       AND a.portador_duplic IN (86, 103, 106, 109, 200, 237, 341, 500, 748) "
				+ "       AND a.data_venc_duplic <= TO_DATE('" + dataAnterior + "', 'DD/MM/YYYY)') "
				+ "       AND a.cod_rep_cliente = " + representante
				+ "       GROUP BY  a.cod_rep_cliente, c.nome_rep_cliente "
				+ "       UNION "
				+ "       SELECT a.cod_rep_cliente || ' - ' || c.nome_rep_cliente representante, "
				+ "              SUM(0) mesAnterior, "
				+ "              SUM(a.valor_duplicata) mesAtual "
				+ "       FROM fatu_070 a, pedi_020 c "
				+ "       WHERE c.cod_rep_cliente = a.cod_rep_cliente "
				+ "       AND a.situacao_duplic IN (0, 3) "
				+ "       AND a.portador_duplic IN (86, 103, 106, 109, 200, 237, 341, 500, 748) "
				+ "       AND a.data_venc_duplic <= TO_DATE('" + dataInicio + "', 'DD/MM/YYYY)') "
				+ "       AND a.cod_rep_cliente = " + representante
				+ "       GROUP BY  a.cod_rep_cliente, c.nome_rep_cliente) DADOS "
				+ "       GROUP BY DADOS.REPRESENTANTE ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosComissao.class));	
	}
	
	public List<ConsultaTitulosComissao> findLancamentosFaturamento(String mesComZero, int ano, String representante){
		
		String query = " SELECT a.codigo_repr representante, "
				+ "       a.codigo_historico historico, "
				+ "       LPAD(a.cgc_cli9, 8, 0) || '/' || LPAD(a.cgc_cli4, 4, 0) || '-' || LPAD(a.cgc_cli2, 2, 0) || ' - ' || d.nome_cliente cliente, "
				+ "       c.cod_ped_cliente pedidoCliente, "
				+ "       b.pedido_venda pedido, "
				+ "       b.quantidade qtdeFaturada, "
				+ "       a.numero_documento docto, "
				+ "       a.seq_documento seq, "
				+ "       b.data_emissao dataEmissao, "
				+ "       b.data_venc_duplic vencimento, "
				+ "       a.base_calc_comis valorDoc, "
				+ "       a.percentual_comis percComPed, "
				+ "       (a.base_calc_comis * a.percentual_comis) / 100 totComissao, "
				+ "       (SELECT MAX(f.seq_duplicatas) FROM fatu_070 f WHERE f.pedido_venda = b.pedido_venda ) qtdeParcelas, "
				+ "       a.valor_lancamento valorComissao "
				+ "		FROM crec_110 a, fatu_070 b, pedi_100 c, pedi_010 d "
				+ "		WHERE b.num_duplicata = a.numero_documento "
				+ "		AND b.seq_duplicatas = a.seq_documento "
				+ "		AND c.cli_ped_cgc_cli9 (+) = b.cli_dup_cgc_cli9 "
				+ "		AND c.cli_ped_cgc_cli4 (+) = b.cli_dup_cgc_cli4 "
				+ "		AND c.cli_ped_cgc_cli2 (+) = b.cli_dup_cgc_cli2 "
				+ "		AND c.pedido_venda (+) = b.pedido_venda "
				+ "		AND d.cgc_9 (+) = a.cgc_cli9 "
				+ "		AND d.cgc_4 (+) = a.cgc_cli4 "
				+ "		AND d.cgc_2 (+) = a.cgc_cli2 "
				+ "		AND a.codigo_repr = " + representante
				+ "		AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "' "
				+ "     AND a.codigo_historico = 1 "
				+ " 	GROUP BY a.numero_documento, a.data_lancamento, a.codigo_repr, a.codigo_historico, a.cgc_cli9, a.cgc_cli4, a.cgc_cli2, a.numero_documento, "
                + "              a.seq_documento, a.base_calc_comis, a.percentual_comis, a.valor_lancamento, b.pedido_venda, b.quantidade, b.data_emissao, " 
                + "              b.data_venc_duplic, c.cod_ped_cliente, d.nome_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosComissao.class));
	}
	
	public List<ConsultaTitulosComissao> findLancamentosBaixaTitulos(String mesComZero, int ano, String representante){
		
		String query = " SELECT a.codigo_repr representante, "
				+ "       a.codigo_historico historico, "
				+ "       LPAD(a.cgc_cli9, 8, 0) || '/' || LPAD(a.cgc_cli4, 4, 0) || '-' || LPAD(a.cgc_cli2, 2, 0) || ' - ' || d.nome_cliente cliente, "
				+ "       c.cod_ped_cliente pedidoCliente, "
				+ "       b.pedido_venda pedido, "
				+ "       b.quantidade qtdeFaturada, "
				+ "       a.numero_documento docto, "
				+ "       a.seq_documento seq, "
				+ "       b.data_emissao dataEmissao, "
				+ "       b.data_venc_duplic vencimento, "
				+ "       a.base_calc_comis valorDoc, "
				+ "       a.percentual_comis percComPed, "
				+ "       (a.base_calc_comis * a.percentual_comis) / 100 totComissao, "
				+ "       (SELECT MAX(f.seq_duplicatas) FROM fatu_070 f WHERE f.pedido_venda = b.pedido_venda ) qtdeParcelas, "
				+ "       a.valor_lancamento valorComissao "
				+ "		FROM crec_110 a, fatu_070 b, pedi_100 c, pedi_010 d "
				+ "		WHERE b.num_duplicata = a.numero_documento "
				+ "		AND b.seq_duplicatas = a.seq_documento "
				+ "		AND c.cli_ped_cgc_cli9 (+) = b.cli_dup_cgc_cli9 "
				+ "		AND c.cli_ped_cgc_cli4 (+) = b.cli_dup_cgc_cli4 "
				+ "		AND c.cli_ped_cgc_cli2 (+) = b.cli_dup_cgc_cli2 "
				+ "		AND c.pedido_venda (+) = b.pedido_venda "
				+ "		AND d.cgc_9 (+) = a.cgc_cli9 "
				+ "		AND d.cgc_4 (+) = a.cgc_cli4 "
				+ "		AND d.cgc_2 (+) = a.cgc_cli2 "
				+ "		AND a.codigo_repr = " + representante
				+ "		AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "' "
				+ "     AND a.codigo_historico IN (2, 10, 14) "
				+ " 	GROUP BY a.numero_documento, a.data_lancamento, a.codigo_repr, a.codigo_historico, a.cgc_cli9, a.cgc_cli4, a.cgc_cli2, a.numero_documento, "
                + "              a.seq_documento, a.base_calc_comis, a.percentual_comis, a.valor_lancamento, b.pedido_venda, b.quantidade, b.data_emissao, " 
                + "              b.data_venc_duplic, c.cod_ped_cliente, d.nome_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosComissao.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllEstacoes(){
		
		String query = " SELECT s.descricao value, s.descricao label FROM orion_070 s GROUP BY s.descricao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public float findTotalFaturadoPorRepresentanteNoMes(String mesComZero, int ano, String representante) {
		
		float total = 0;
		
		String query = " SELECT SUM(a.valor_lancamento) FROM crec_110 a "
				+ "     WHERE a.codigo_repr =  " + representante
				+ "     AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "' "
				+ "     AND a.codigo_historico IN (1, 2, 10, 14) ";
		
		try {
			total = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			total = 0;
		}
		return total;
	}
	
	public float findMetaPorRespresentanteFitness(String representante, String estacao) {
		
		float metaFitness = 0;
		
		String query = " SELECT NVL(w.meta, 0) META FROM orion_072 w, orion_070 z "
				+ "      WHERE z.cod_estacao = w.cod_estacao "
				+ "      AND w.cod_representante = " + representante
				+ "      AND w.tipo_meta = 1 "
				+ "      AND z.catalogo = 1 "
				+ "      AND z.descricao LIKE '%" + estacao + "%'";
		
		try {
			metaFitness = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			metaFitness = 0;
		}
		return metaFitness;
	}
	
	public float findMetaPorRespresentanteBeach(String representante, String estacao) {
		
		float metaBeach = 0;
		
		String query = " SELECT NVL(w.meta, 0) META FROM orion_072 w, orion_070 z "
				+ "      WHERE z.cod_estacao = w.cod_estacao "
				+ "      AND w.cod_representante = " + representante
				+ "      AND w.tipo_meta = 1 "
				+ "      AND z.catalogo = 2 "
				+ "      AND z.descricao LIKE '%" + estacao + "%'";
		
		try {
			metaBeach = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			metaBeach = 0;
		}
		return metaBeach;
	}
	
	public float findPercAtingidoFitness(String mesComZero, int ano, String representante, String estacao) {
		
		float percentual = 0;
		
		String query = " SELECT (SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) / ( "
				+ "       select w.meta from orion_072 w, orion_070 z "
				+ "               where z.cod_estacao = w.cod_estacao "
				+ "               and w.cod_representante = " + representante
				+ "               and w.tipo_meta = 1 "
				+ "               and z.catalogo = 1 "
				+ "               and z.descricao LIKE '%" + estacao + "%')) * 100 porcAtingido "
				+ "     from pedi_100 a, pedi_110 b, basi_030 c, pedi_023 d, pedi_043 e, pedi_040 f "
				+ "		where a.pedido_venda = b.pedido_venda "
				+ "		and b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "		and b.cd_it_pe_grupo = c.referencia "
				+ "		and d.cod_rep_cliente = a.cod_rep_cliente "
				+ "		and e.sub_regiao = d.sub_regiao "
				+ "		and f.codigo_regiao = e.codigo_regiao "
				+ "		and a.cod_rep_cliente = " + representante
				+ "		and c.linha_produto = 52 "
				+ "		and TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "		and a.colecao_tabela = 02 "
				+ "		and a.mes_tabela = 01 "
				+ "		and a.sequencia_tabela = 22 "
				+ "		and b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente, c.linha_produto, e.descricao, f.nome_regiao";
		
		try {
			percentual = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percentual = 0;
		}
		return percentual;
		
	}
	
	public float findPercAtingidoBeach(String mesComZero, int ano, String representante, String estacao) {
		
		float percentual = 0;
		
		String query = " SELECT (SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) / ( "
				+ "       select w.meta from orion_072 w, orion_070 z "
				+ "               where z.cod_estacao = w.cod_estacao "
				+ "               and w.cod_representante = " + representante
				+ "               and w.tipo_meta = 1 "
				+ "               and z.catalogo = 2 "
				+ "               and z.descricao LIKE '%" + estacao + "%')) * 100 porcAtingido "
				+ "     from pedi_100 a, pedi_110 b, basi_030 c, pedi_023 d, pedi_043 e, pedi_040 f "
				+ "		where a.pedido_venda = b.pedido_venda "
				+ "		and b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "		and b.cd_it_pe_grupo = c.referencia "
				+ "		and d.cod_rep_cliente = a.cod_rep_cliente "
				+ "		and e.sub_regiao = d.sub_regiao "
				+ "		and f.codigo_regiao = e.codigo_regiao "
				+ "		and a.cod_rep_cliente = " + representante
				+ "		and c.linha_produto = 53 "
				+ "		and TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "		and a.colecao_tabela = 02 "
				+ "		and a.mes_tabela = 01 "
				+ "		and a.sequencia_tabela = 22 "
				+ "		and b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente, c.linha_produto, e.descricao, f.nome_regiao";
		
		try {
			percentual = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percentual = 0;
		}
		return percentual;
		
	}
	
	public List<ConsultaTitulosComissao> findBonusPorRepresentante(String mesComZero, int ano, String representante, String estacao, float totalFaturado, float porcLinhaFitness, 
			float porcLinhaBeach, float percAtingidoFitness, float percAtingidoBeach){
		
		String query = " SELECT a.cod_rep_cliente representante, "
				+ "       f.nome_regiao uf, "
				+ "       e.descricao regiao, "
				+ "       'FITNESS' linha,  "
				+ "       (SELECT w.meta FROM orion_072 w, orion_070 z "
				+ "               WHERE z.cod_estacao = w.cod_estacao "
				+ "               AND w.cod_representante = " + representante
				+ "               AND w.tipo_meta = 1 "
				+ "               AND z.catalogo = 1 "
				+ "               AND z.descricao LIKE '%" + estacao + "%') meta,"
				+         porcLinhaFitness + " porcLinha, "
				+ "       SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) vendas, "
				+         percAtingidoFitness + " porcAtingido,"
				+         totalFaturado * 0.25 + " valor"
				+ "		from pedi_100 a, pedi_110 b, basi_030 c, pedi_023 d, pedi_043 e, pedi_040 f "
				+ "		where a.pedido_venda = b.pedido_venda "
				+ "		and b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "		and b.cd_it_pe_grupo = c.referencia "
				+ "		and d.cod_rep_cliente = a.cod_rep_cliente "
				+ "		and e.sub_regiao = d.sub_regiao "
				+ "		and f.codigo_regiao = e.codigo_regiao "
				+ "		and a.cod_rep_cliente =  " + representante
				+ "		and c.linha_produto = 52 "
				+ "		and TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "		and a.colecao_tabela = 02 "
				+ "		and a.mes_tabela = 01 "
				+ "		and a.sequencia_tabela = 22 "
				+ "		and b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente, c.linha_produto, e.descricao, f.nome_regiao"
				+ "     UNION "
				+ "     SELECT a.cod_rep_cliente representante, "
				+ "			f.nome_regiao uf, "
				+ "			e.descricao regiao, "
				+ "			'BEACH' linha,  "
				+ "			(SELECT w.meta FROM orion_072 w, orion_070 z "
				+ "				WHERE z.cod_estacao = w.cod_estacao "
				+ "				AND w.cod_representante = " + representante
				+ "				AND w.tipo_meta = 1 "
				+ "				AND z.catalogo = 2 "
				+ "				AND z.descricao LIKE '%" + estacao + "%') meta, "
				+         porcLinhaBeach + " porcLinha, "
				+ "			SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) vendas, "
				+ 		  percAtingidoBeach + " porcAtingido, "
				+         totalFaturado * 0.25 + " valor "
				+ "			from pedi_100 a, pedi_110 b, basi_030 c, pedi_023 d, pedi_043 e, pedi_040 f "
				+ "			where a.pedido_venda = b.pedido_venda "
				+ "			and b.cd_it_pe_nivel99 = c.nivel_estrutura  "
				+ "			and b.cd_it_pe_grupo = c.referencia "
				+ "			and d.cod_rep_cliente = a.cod_rep_cliente "
				+ "			and e.sub_regiao = d.sub_regiao "
				+ "			and f.codigo_regiao = e.codigo_regiao "
				+ "			and a.cod_rep_cliente =  " + representante
				+ "			and c.linha_produto = 53 "
				+ "			and TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "			and a.colecao_tabela = 02 "
				+ "			and a.mes_tabela = 01 "
				+ "			and a.sequencia_tabela = 22 "
				+ "			and b.cod_cancelamento = 0 "
				+ "			GROUP BY a.cod_rep_cliente, c.linha_produto, e.descricao, f.nome_regiao";
		
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosComissao.class));
		
	}

}
