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
		
		String query = " SELECT SUM(DADOS.venda - DADOS.cancelado) FATURADO FROM ( "
				+ "		SELECT NVL(SUM(a.base_calc_comis), 0) venda, "
				+ "       0 cancelado "
				+ "     FROM crec_110 a "
				+ "     WHERE a.codigo_repr = " + representante
				+ "     AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.codigo_historico IN (1) "
				+ "	UNION "
				+ "		SELECT 0 venda, "
				+ "     NVL(SUM(a.base_calc_comis), 0) cancelado "
				+ "     FROM crec_110 a "
				+ "     WHERE a.codigo_repr = " + representante
				+ "     AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.codigo_historico IN (12)) DADOS ";
		
		try {
			total = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			total = 0;
		}
		return total;
	}
	
	public float findMetaPorRespresentanteFitness(String representante, String mes, int ano) {
		
		float metaFitness = 0;
		
		String query = " SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "WHERE x.cod_estacao = w.cod_estacao "
				+ "AND z.cod_estacao = w.cod_estacao "
				+ "AND x.tipo_meta = w.tipo_meta "
				+ "AND w.cod_representante = " + representante
				+ "AND w.tipo_meta = 2 "
				+ "AND z.catalogo = 1 "
				+ "AND x.mes = " + mes
				+ "AND x.ano = " + ano;
		
		try {
			metaFitness = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			metaFitness = 0;
		}
		
		return metaFitness;
	}
	
	public float findMetaPorRespresentanteBeach(String representante, String mes, int ano) {
		
		float metaBeach = 0;
		
		String query = " SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "WHERE x.cod_estacao = w.cod_estacao "
				+ "AND z.cod_estacao = w.cod_estacao "
				+ "AND x.tipo_meta = w.tipo_meta "
				+ "AND w.cod_representante = " + representante
				+ "AND w.tipo_meta = 2 "
				+ "AND z.catalogo = 2 "
				+ "AND x.mes = " + mes
				+ "AND x.ano = " + ano;
		
		try {
			metaBeach = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			metaBeach = 0;
		}
		return metaBeach;
	}
	
	public List<ConteudoChaveAlfaNum> findUf(String representante) {
		
		List<ConteudoChaveAlfaNum> listEstado = new ArrayList<>();
		
		String query = " SELECT f.nome_regiao value,  f.nome_regiao label FROM pedi_023 d, pedi_043 e, pedi_040 f "
				+ "       WHERE e.sub_regiao = d.sub_regiao "
				+ "       AND f.codigo_regiao = e.codigo_regiao "
				+ "       AND d.cod_rep_cliente = " + representante
				+ "       GROUP BY f.nome_regiao ";
		
		try {
			listEstado = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		} catch (Exception e) {
			listEstado = new ArrayList<>();
		}
		return listEstado;
	}
	
	public List<ConteudoChaveAlfaNum> findSubRegiao(String representante) {
		
		List<ConteudoChaveAlfaNum> listSubRegiao = new ArrayList<>();
		
		String query = " SELECT e.descricao value, e.descricao label FROM pedi_023 d, pedi_043 e "
				+ "     WHERE e.sub_regiao = d.sub_regiao "
				+ "     AND d.cod_rep_cliente = " + representante
				+ "     GROUP BY e.descricao ";
		
		try {
			listSubRegiao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		} catch (Exception e) {
			listSubRegiao = new ArrayList<>();
		}
		return listSubRegiao;
	}
	
	public float findPercAtingidoFitness(String mesComZero, int ano, String representante) {
		
		float percentualFitness = 0;
		
		String query = " SELECT NVL(SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) / ( "
				+ "       SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "              WHERE x.cod_estacao = w.cod_estacao "
				+ "                    AND z.cod_estacao = w.cod_estacao "
				+ "                    AND x.tipo_meta = w.tipo_meta "
				+ "                    AND w.cod_representante = " + representante
				+ "                    AND w.tipo_meta = 2 "
				+ "                    AND z.catalogo = 1 "
				+ "                    AND x.mes = " + mesComZero
				+ "                    AND x.ano = " + ano
				+ "       ) * 100, 0) porcAtingido "
				+ "       FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "       WHERE a.pedido_venda = b.pedido_venda "
				+ "       AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "       AND b.cd_it_pe_grupo = c.referencia "
				+ "       AND a.cod_rep_cliente = " + representante 
				+ "       AND c.linha_produto = 52 "
				+ "       AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "       AND b.cod_cancelamento = 0 "
				+ "       GROUP BY a.cod_rep_cliente ";
		
		try {
			percentualFitness = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percentualFitness = 0;
		}
		return percentualFitness;
		
	}
	
	public float findPercAtingidoBeach(String mesComZero, int ano, String representante) {
		
		float percentualBach = 0;
		
		String query = " SELECT NVL(SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) / ( "
				+ "       SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "              WHERE x.cod_estacao = w.cod_estacao "
				+ "                    AND z.cod_estacao = w.cod_estacao "
				+ "                    AND x.tipo_meta = w.tipo_meta "
				+ "                    AND w.cod_representante = " + representante
				+ "                    AND w.tipo_meta = 2 "
				+ "                    AND z.catalogo = 2 "
				+ "                    AND x.mes = " + mesComZero
				+ "                    AND x.ano = " + ano
				+ "       ) * 100, 0) porcAtingido"
				+ "       FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "       WHERE a.pedido_venda = b.pedido_venda "
				+ "       AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "       AND b.cd_it_pe_grupo = c.referencia "
				+ "       AND a.cod_rep_cliente = " + representante 
				+ "       AND c.linha_produto = 53 "
				+ "       AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "       AND b.cod_cancelamento = 0 "
				+ "       GROUP BY a.cod_rep_cliente ";
		
		try {
			percentualBach = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percentualBach = 0;
		}
		return percentualBach;
		
	}
	
	public List<ConsultaTitulosComissao> findBonusPorRepresentante(String mesComZero, int ano, String representante, String estacao, float totalFaturado, float porcLinhaFitness, 
			float porcLinhaBeach, float percAtingidoFitness, float percAtingidoBeach, float valorProporcional, String estado, String regiao, float metaFitness, float metaBeach){
		
		String query = " SELECT a.cod_rep_cliente representante, "
				+ "    '" +  estado + "' uf,"
				+ "    '" +  regiao + "' regiao,"
				+ "      'FITNESS' linha,  "
				+ "      NVL(" + metaFitness + ", 0) meta, "
				+ "      NVL(" + porcLinhaFitness + ", 0) porcLinha, "
				+ "      SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) vendas, "
				+ "      NVL(" + percAtingidoFitness + ", 0) porcAtingido, "
				+ "      NVL(" + totalFaturado + ", 0) totalFaturado, "
				+ "      NVL(" + totalFaturado * 0.0025 + ", 0) valor, "
				+ "      NVL(" + valorProporcional * 0.0025 + ", 0) valorProporcional " 
				+ "		FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "		where a.pedido_venda = b.pedido_venda "
				+ "		AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "		AND b.cd_it_pe_grupo = c.referencia "
				+ "		AND a.cod_rep_cliente =  " + representante
				+ "		AND c.linha_produto = 52 "
				+ "		AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.colecao_tabela = 1 "
				+ "     AND a.mes_tabela = 1 "
				+ "     AND a.sequencia_tabela = 23 "
				+ "		AND b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente "
				+ "     UNION "
				+ "     SELECT a.cod_rep_cliente representante, "
				+ "    '" +  estado + "' uf,"
				+ "    '" +  regiao + "' regiao,"
				+ "		'BEACH' linha,  "
				+ "     NVL(" + metaBeach + ", 0) meta, "
				+ "     NVL(" + porcLinhaBeach + ",0 ) porcLinha, "
				+ "		SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) vendas, "
				+ "		NVL(" + percAtingidoBeach + ", 0) porcAtingido, "
				+ "     NVL(" + totalFaturado + ", 0) totalFaturado, "
				+ "     NVL(" + totalFaturado * 0.0025 + ", 0) valor, "
				+ "     NVL(" + valorProporcional + ", 0) valorProporcional "
				+ "		FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "		WHERE a.pedido_venda = b.pedido_venda "
				+ "		AND b.cd_it_pe_nivel99 = c.nivel_estrutura  "
				+ "		AND b.cd_it_pe_grupo = c.referencia "
				+ "		AND a.cod_rep_cliente =  " + representante
				+ "		AND c.linha_produto = 53 "
				+ "		AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.colecao_tabela = 1 "
				+ "     AND a.mes_tabela = 1 "
				+ "     AND a.sequencia_tabela = 23 "
				+ "		AND b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente ";
		
		System.out.println(query);
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosComissao.class));
		
	}

}
