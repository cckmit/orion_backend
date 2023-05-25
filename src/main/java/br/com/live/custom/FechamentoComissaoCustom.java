package br.com.live.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaFechamentoComissoes;
import br.com.live.model.Produto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

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
	
	public List<ConsultaFechamentoComissoes> findTitulosAtrasadosAnalitico(String dataInicio, List<ConteudoChaveAlfaNum> listRepresentante) {
		
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
				+ "      AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
		
	}
	
	public List<ConsultaFechamentoComissoes> findTitulosAtrasadosSintetico(String dataInicio, String dataAnterior, List<ConteudoChaveAlfaNum> listRepresentante) {
		
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
				+ "       AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
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
				+ "       AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "       GROUP BY  a.cod_rep_cliente, c.nome_rep_cliente) DADOS "
				+ "       GROUP BY DADOS.REPRESENTANTE ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));	
	}
	
	public List<ConsultaFechamentoComissoes> findLancamentosFaturamento(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante){
		
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
				+ "		AND a.codigo_repr IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "		AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "' "
				+ "     AND a.codigo_historico = 1 "
				+ " 	GROUP BY a.numero_documento, a.data_lancamento, a.codigo_repr, a.codigo_historico, a.cgc_cli9, a.cgc_cli4, a.cgc_cli2, a.numero_documento, "
                + "              a.seq_documento, a.base_calc_comis, a.percentual_comis, a.valor_lancamento, b.pedido_venda, b.quantidade, b.data_emissao, " 
                + "              b.data_venc_duplic, c.cod_ped_cliente, d.nome_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
	}
	
	public List<ConsultaFechamentoComissoes> findLancamentosBaixaTitulos(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante){
		
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
				+ "		AND a.codigo_repr IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "		AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "' "
				+ "     AND a.codigo_historico IN (2, 10, 14) "
				+ " 	GROUP BY a.numero_documento, a.data_lancamento, a.codigo_repr, a.codigo_historico, a.cgc_cli9, a.cgc_cli4, a.cgc_cli2, a.numero_documento, "
                + "              a.seq_documento, a.base_calc_comis, a.percentual_comis, a.valor_lancamento, b.pedido_venda, b.quantidade, b.data_emissao, " 
                + "              b.data_venc_duplic, c.cod_ped_cliente, d.nome_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllEstacoes(){
		
		String query = " SELECT s.descricao value, s.descricao label FROM orion_070 s GROUP BY s.descricao ORDER BY s.descricao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public float findTotalFaturadoPorRepresentanteNoMes(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		
		float total = 0;
		
		String query = " SELECT SUM(DADOS.venda - DADOS.cancelado) FATURADO FROM ( "
				+ "		SELECT NVL(SUM(a.base_calc_comis), 0) venda, "
				+ "       0 cancelado "
				+ "     FROM crec_110 a "
				+ "     WHERE a.codigo_repr IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "     AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.codigo_historico IN (1) "
				+ "	UNION "
				+ "		SELECT 0 venda, "
				+ "     NVL(SUM(a.base_calc_comis), 0) cancelado "
				+ "     FROM crec_110 a "
				+ "     WHERE a.codigo_repr IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "     AND TO_CHAR(TO_DATE(a.data_lancamento), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.codigo_historico IN (12)) DADOS ";
		
		try {
			total = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			total = 0;
		}
		return total;
	}
	
	public String findTabPrecoEstacao(String estacao) {
		
		String tabPreco = "";
		
		String query = " SELECT a.col_tab || '-' || a.mes_tab || '-' || a.seq_tab tabela "
				+ "		FROM orion_073 a, pedi_090 b "
				+ "		WHERE b.col_tabela_preco = a.col_tab "
				+ "		AND b.mes_tabela_preco = a.mes_tab "
				+ "		AND b.seq_tabela_preco = a.seq_tab "
				+ "		AND b.descricao LIKE '%" + estacao + "%'"
				+ "		AND a.mes_tab = 1 "
				+ "	GROUP BY a.col_tab || '-' || a.mes_tab || '-' || a.seq_tab ";
		try {
			tabPreco = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			tabPreco = "";
		}
		return tabPreco;
	}
	
	public float findPrecoProduto(int col, int mes, int seq, String nivel, String grupo, String subgrupo, String item) {
		
		float preco = 0;
		
		String query = " SELECT inter_fn_get_val_unit_tab(" + col + ", " + mes + ", " + seq +", '" + nivel + "', '" + grupo + "', '" + subgrupo + "', '" + item + "') preco "
				+ "		FROM dual";
	
		try {
			preco = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			preco = 0;
		}
			
		return preco;
	}
	
	public int findQtdeDevolvida(String estacao, int codRepres, String nivel, String grupo, String subgrupo, String item) {
		
		int qtdeDevolvida = 0;
		
		String query = " SELECT NVL(a.quantidade, 0) qtde "
				+ "		FROM orion_fin_050 a "
				+ "		WHERE a.estacao LIKE '%" + estacao + "%'"
				+ "		AND a.representante = " + codRepres
				+ "		AND a.nivel = " + nivel
				+ "		AND a.grupo = " + grupo
				+ "		AND a.subgrupo = " + subgrupo
				+ "		AND a.item = " + item;
		
		try {
			qtdeDevolvida = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeDevolvida = 0;
		}
			
		return qtdeDevolvida;
	}
	
	public float findMetaPorRespresentanteFitness(List<ConteudoChaveAlfaNum> listRepresentante, String mes, int ano) {
		
		float metaFitness = 0;
		
		String query = " SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "WHERE x.cod_estacao = w.cod_estacao "
				+ "AND z.cod_estacao = w.cod_estacao "
				+ "AND x.tipo_meta = w.tipo_meta "
				+ "AND w.cod_representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
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
	
	public float findMetaPorRepresentanteEstacaoFitness(List<ConteudoChaveAlfaNum> listRepresentante, String estacao) {
		
		float metaEstacaoFitness = 0;
		
		String query = " SELECT NVL(SUM(a.meta), 0) from orion_072 a, orion_070 b "
				+ "   WHERE b.cod_estacao = a.cod_estacao "
				+ "   AND b.descricao LIKE '%" + estacao + "%'"
				+ "   AND a.tipo_meta = 1 "
				+ "   AND b.catalogo = 1 "
				+ "   AND a.cod_representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";
		
		try {
			metaEstacaoFitness = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			metaEstacaoFitness = 0;
		}
		
		return metaEstacaoFitness;
	}
	
	public float findMetaPorRespresentanteBeach(List<ConteudoChaveAlfaNum> listRepresentante, String mes, int ano) {
		
		float metaBeach = 0;
		
		String query = " SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "WHERE x.cod_estacao = w.cod_estacao "
				+ "AND z.cod_estacao = w.cod_estacao "
				+ "AND x.tipo_meta = w.tipo_meta "
				+ "AND w.cod_representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
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
	
	public float findMetaPorRepresentanteEstacaoBeach(List<ConteudoChaveAlfaNum> listRepresentante, String estacao) {
		
		float metaEstacaoBeach = 0;
		
		String query = " SELECT NVL(SUM(a.meta), 0) from orion_072 a, orion_070 b "
				+ "   WHERE b.cod_estacao = a.cod_estacao "
				+ "   AND b.descricao LIKE '%" + estacao + "%'"
				+ "   AND a.tipo_meta = 2 "
				+ "   AND b.catalogo = 2 "
				+ "   AND a.cod_representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";
		
		try {
			metaEstacaoBeach = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			metaEstacaoBeach = 0;
		}
		
		return metaEstacaoBeach;
	}
	
	public int findAnoInicioEstacaoFitness(String estacao) {
		
		int anoInicioFitness = 0;
		
		String query = " SELECT MIN(a.ano) periodo FROM orion_071 a, orion_070 b "
				+ "WHERE b.cod_estacao = a.cod_estacao "
				+ "AND b.descricao LIKE '%" + estacao + "%'"
				+ "AND a.tipo_meta = 1 "
				+ "AND b.catalogo = 1 ";
		
		try {
			anoInicioFitness = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			anoInicioFitness = 0;
		}
		return anoInicioFitness;
	}
	
	public int findAnoFimEstacaoFitness(String estacao) {
		
		int anoFimFitness = 0;
		
		String query = " SELECT MAX(a.ano) periodo FROM orion_071 a, orion_070 b "
				+ "WHERE b.cod_estacao = a.cod_estacao "
				+ "AND b.descricao LIKE '%" + estacao + "%'"
				+ "AND a.tipo_meta = 1 "
				+ "AND b.catalogo = 1 ";
		
		try {
			anoFimFitness = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			anoFimFitness = 0;
		}
		return anoFimFitness;
	}
	
	public String findDtInicioEstacaoFitness(String estacao, int ano) {
		
		String dtInicioFitness = "";
		
		String query = " SELECT LPAD(MIN(a.mes), 2, 0) periodo FROM orion_071 a, orion_070 b "
				+ "     WHERE b.cod_estacao = a.cod_estacao "
				+ "     AND b.descricao LIKE '%" + estacao + "%'"
				+ "     AND a.tipo_meta = 1 "
				+ "     AND b.catalogo = 1 "
				+ "     AND a.ano = " + ano;
		
		try {
			dtInicioFitness = jdbcTemplate.queryForObject(query, String.class)+ "/" + ano;
		} catch (Exception e) {
			dtInicioFitness = "";
		}
		return dtInicioFitness;
	}
	
	public String findDtFimEstacaoFitness(String estacao) {
		
		String dtFimFitness = "";
		
		String query = " SELECT MAX(LPAD(a.mes, 2, 0) || '/' || MX(a.ano)) periodo FROM orion_071 a, orion_070 b "
				+ "WHERE b.cod_estacao = a.cod_estacao "
				+ "AND b.descricao LIKE '%" + estacao + "%'"
				+ "AND a.tipo_meta = 1 "
				+ "AND b.catalogo = 1 ";
		
		try {
			dtFimFitness = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			dtFimFitness = "";
		}
		return dtFimFitness;
	}
	
	public String findDtFimEstacaoFitness(String estacao, int ano) {
		
		String dtInicioFitness = "";
		
		String query = " SELECT LPAD(MAX(a.mes), 2, 0) periodo FROM orion_071 a, orion_070 b "
				+ "     WHERE b.cod_estacao = a.cod_estacao "
				+ "     AND b.descricao LIKE '%" + estacao + "%'"
				+ "     AND a.tipo_meta = 1 "
				+ "     AND b.catalogo = 1 "
				+ "     AND a.ano = " + ano;
		
		try {
			dtInicioFitness = jdbcTemplate.queryForObject(query, String.class)+ "/" + ano;
		} catch (Exception e) {
			dtInicioFitness = "";
		}
		return dtInicioFitness;
	}
	
	public int findAnoInicioEstacaoBeach(String estacao) {
		
		int anoInicioBeach = 0;
		
		String query = " SELECT MIN(a.ano) periodo FROM orion_071 a, orion_070 b "
				+ "WHERE b.cod_estacao = a.cod_estacao "
				+ "AND b.descricao LIKE '%" + estacao + "%'"
				+ "AND a.tipo_meta = 2 "
				+ "AND b.catalogo = 2 ";
		
		try {
			anoInicioBeach = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			anoInicioBeach = 0;
		}
		return anoInicioBeach;
	}
	
	public String findDtInicioEstacaoBeach(String estacao, int ano) {
		
		String dtInicioBeach = "";
		
		String query = " SELECT LPAD(MIN(a.mes), 2, 0) periodo FROM orion_071 a, orion_070 b "
				+ "     WHERE b.cod_estacao = a.cod_estacao "
				+ "     AND b.descricao LIKE '%" + estacao + "%'"
				+ "     AND a.tipo_meta = 2 "
				+ "     AND b.catalogo = 2 "
				+ "     AND a.ano = " + ano;
		
		try {
			dtInicioBeach = jdbcTemplate.queryForObject(query, String.class)+ "/" + ano;
		} catch (Exception e) {
			dtInicioBeach = "";
		}
		return dtInicioBeach;
	}
	
	public int findAnoFimEstacaoBeach(String estacao) {
		
		int anoFimBeach = 0;
		
		String query = " SELECT MAX(a.ano) periodo FROM orion_071 a, orion_070 b "
				+ "WHERE b.cod_estacao = a.cod_estacao "
				+ "AND b.descricao LIKE '%" + estacao + "%'"
				+ "AND a.tipo_meta = 2 "
				+ "AND b.catalogo = 2 ";
		
		try {
			anoFimBeach = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			anoFimBeach = 0;
		}
		return anoFimBeach;
	}
	
	public String findDtFimEstacaoBeach(String estacao, int ano) {
		
		String dtInicioBeach = "";
		
		String query = " SELECT LPAD(MAX(a.mes), 2, 0) periodo FROM orion_071 a, orion_070 b "
				+ "     WHERE b.cod_estacao = a.cod_estacao "
				+ "     AND b.descricao LIKE '%" + estacao + "%'"
				+ "     AND a.tipo_meta = 2 "
				+ "     AND b.catalogo = 2 "
				+ "     AND a.ano = " + ano;
		
		try {
			dtInicioBeach = jdbcTemplate.queryForObject(query, String.class)+ "/" + ano;
		} catch (Exception e) {
			dtInicioBeach = "";
		}
		return dtInicioBeach;
	}
	
	public List<ConteudoChaveAlfaNum> findUf(List<ConteudoChaveAlfaNum> listRepresentante) {
		
		List<ConteudoChaveAlfaNum> listEstado = new ArrayList<>();
		
		String query = " SELECT f.nome_regiao value,  f.nome_regiao label FROM pedi_023 d, pedi_043 e, pedi_040 f "
				+ "       WHERE e.sub_regiao = d.sub_regiao "
				+ "       AND f.codigo_regiao = e.codigo_regiao "
				+ "       AND d.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "       GROUP BY f.nome_regiao ";
		
		try {
			listEstado = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		} catch (Exception e) {
			listEstado = new ArrayList<>();
		}
		return listEstado;
	}
	
	public List<ConteudoChaveAlfaNum> findSubRegiao(List<ConteudoChaveAlfaNum> listRepresentante) {
		
		List<ConteudoChaveAlfaNum> listSubRegiao = new ArrayList<>();
		
		String query = " SELECT e.descricao value, e.descricao label FROM pedi_023 d, pedi_043 e "
				+ "     WHERE e.sub_regiao = d.sub_regiao "
				+ "     AND d.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "     GROUP BY e.descricao ";
		
		try {
			listSubRegiao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		} catch (Exception e) {
			listSubRegiao = new ArrayList<>();
		}
		return listSubRegiao;
	}
	
	public float findPercAtingidoFitness(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		
		float percentualFitness = 0;
		
		String query = " SELECT NVL(SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) / ( "
				+ "       SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "              WHERE x.cod_estacao = w.cod_estacao "
				+ "                    AND z.cod_estacao = w.cod_estacao "
				+ "                    AND x.tipo_meta = w.tipo_meta "
				+ "                    AND w.cod_representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "                    AND w.tipo_meta = 2 "
				+ "                    AND z.catalogo = 1 "
				+ "                    AND x.mes = " + mesComZero
				+ "                    AND x.ano = " + ano
				+ "       ) * 100, 0) porcAtingido "
				+ "       FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "       WHERE a.pedido_venda = b.pedido_venda "
				+ "       AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "       AND b.cd_it_pe_grupo = c.referencia "
				+ "       AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
				+ "       AND c.linha_produto = 52 "
				+ "       AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "       AND a.colecao_tabela = 1 "
				+ "       AND a.mes_tabela = 1 "
				+ "       AND a.sequencia_tabela = 23 "
				+ "       AND b.cod_cancelamento = 0 "
				+ "       GROUP BY a.cod_rep_cliente ";
		
		try {
			percentualFitness = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percentualFitness = 0;
		}
		return percentualFitness;
		
	}
	
	public float findPercAtingidoBeach(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		
		float percentualBeach = 0;
		
		String query = " SELECT NVL(SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) / ( "
				+ "       SELECT (w.meta * x.perc_distribuicao) / 100 META FROM orion_072 w, orion_071 x, orion_070 z "
				+ "              WHERE x.cod_estacao = w.cod_estacao "
				+ "                    AND z.cod_estacao = w.cod_estacao "
				+ "                    AND x.tipo_meta = w.tipo_meta "
				+ "                    AND w.cod_representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "                    AND w.tipo_meta = 2 "
				+ "                    AND z.catalogo = 2 "
				+ "                    AND x.mes = " + mesComZero
				+ "                    AND x.ano = " + ano
				+ "       ) * 100, 0) porcAtingido"
				+ "       FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "       WHERE a.pedido_venda = b.pedido_venda "
				+ "       AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "       AND b.cd_it_pe_grupo = c.referencia "
				+ "       AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
				+ "       AND c.linha_produto = 53 "
				+ "       AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "       AND a.colecao_tabela = 1 "
				+ "       AND a.mes_tabela = 1 "
				+ "       AND a.sequencia_tabela = 23 "
				+ "       AND b.cod_cancelamento = 0 "
				+ "       GROUP BY a.cod_rep_cliente ";
		
		try {
			percentualBeach = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percentualBeach = 0;
		}
		return percentualBeach;
		
	}
	
	public float findPercAtingidoEstacaoFitness(String dtInicioEstFitness, String dtFimEstFitness, int tabCol, int tabMes, int tabSeq, float metaFitness, List<ConteudoChaveAlfaNum> listRepresentante) {
		
		float percFitness = 0;
		
		String query = "SELECT (SUM(DADOS.PERCENTUAL) / " + metaFitness + ") * 100 FROM "
				+ "     ("
				+ "      SELECT SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) percentual"
				+ "		FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "		WHERE a.pedido_venda = b.pedido_venda "
				+ "		AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "		AND b.cd_it_pe_grupo = c.referencia "
				+ "		AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
				+ "		AND c.linha_produto = 52 "
				+ "		AND a.data_entr_venda BETWEEN TO_DATE('01/" + dtInicioEstFitness + "', 'DD/MM/YYYY') AND LAST_DAY(TO_DATE('" + dtFimEstFitness +"', 'MM/YYYY')) "
				+ "		AND a.colecao_tabela = " + tabCol
				+ "		AND a.mes_tabela = " + tabMes
				+ "		AND a.sequencia_tabela = " + tabSeq
				+ "		AND b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente) DADOS ";
		
		try {
			percFitness = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percFitness = 0;
		}
		return percFitness;
		
	}
	
	public float findPercAtingidoEstacaoBeach(String dtInicioEstBeach, String dtFimEstBeach, int tabCol, int tabMes, int tabSeq, float metaBeach, List<ConteudoChaveAlfaNum> listRepresentante) {
		
		float percBeach = 0;
		
		String query = "SELECT NVL((SUM(DADOS.PERCENTUAL) / " + metaBeach + ") * 100, 0) FROM "
				+ "     ("
				+ "      SELECT SUM(((b.qtde_pedida * b.valor_unitario) - (((b.qtde_pedida * b.valor_unitario) * b.percentual_desc)/100))) percentual"
				+ "		FROM pedi_100 a, pedi_110 b, basi_030 c "
				+ "		WHERE a.pedido_venda = b.pedido_venda "
				+ "		AND b.cd_it_pe_nivel99 = c.nivel_estrutura "
				+ "		AND b.cd_it_pe_grupo = c.referencia "
				+ "		AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
				+ "		AND c.linha_produto = 53 "
				+ "		AND a.data_entr_venda BETWEEN TO_DATE('01/" + dtInicioEstBeach + "', 'DD/MM/YYYY') AND LAST_DAY(TO_DATE('" + dtFimEstBeach +"', 'MM/YYYY')) "
				+ "		AND a.colecao_tabela = " + tabCol
				+ "		AND a.mes_tabela = " + tabMes
				+ "		AND a.sequencia_tabela = " + tabSeq
				+ "		AND b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente) DADOS ";
		
		try {
			percBeach = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			percBeach = 0;
		}
		return percBeach;
		
	}
	
	public List<ConsultaFechamentoComissoes> findBonusPorRepresentante(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante, float totalFaturado, 
			float porcLinhaFitness, float porcLinhaBeach, float percAtingidoFitness, float percAtingidoBeach, float valorProporcional, String estado, String regiao, float metaFitness, float metaBeach){
		
		List<ConsultaFechamentoComissoes> listComissao = null;
		
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
				+ "		AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
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
				+ "		AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
				+ "		AND c.linha_produto = 53 "
				+ "		AND TO_CHAR(TO_DATE(a.data_entr_venda), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "     AND a.colecao_tabela = 1 "
				+ "     AND a.mes_tabela = 1 "
				+ "     AND a.sequencia_tabela = 23 "
				+ "		AND b.cod_cancelamento = 0 "
				+ "		GROUP BY a.cod_rep_cliente ";
	
		try {
			listComissao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
		} catch (Exception e) {
			listComissao = new ArrayList<ConsultaFechamentoComissoes>();
		}
		return listComissao;		
	}
	
	public List<ConsultaFechamentoComissoes> findDevolucoes(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante){
		
		List<ConsultaFechamentoComissoes> listDevolucao = null;
		
		String query = " SELECT f.nome_fornecedor cliente, "
				+ "      	c.num_nota_orig notaSaida, "
				+ "      	b.data_emissao dtEmissaoEntrada, "
				+ "      	a.data_emissao dtEmissaoSaida, "
				+ "      	b.documento notaEntrada, "
				+ "      	e.cod_rep_cliente || '-' || e.nome_rep_cliente representante, "
				+ "      	a.perc_repres percComissao, "
				+ "      	SUM(a.perc_repres * (c.valor_total + c.rateio_despesas) /100) valorComissao, "
				+ "      	SUM(c.valor_total + c.rateio_despesas) valorNf, "
				+ "      	d.descr_motivo motivo, "
				+ "      	h.tp_forne || '-' || h.descricao tipoFornecedor "
				+ "			FROM fatu_050 a, obrf_010 b, obrf_015 c, efic_010 d, pedi_020 e, supr_010 f, pedi_080 g, supr_130 h "
				+ "			WHERE b.documento = c.capa_ent_nrdoc "
				+ "			AND b.serie = c.capa_ent_serie "
				+ "			AND c.num_nota_orig = a.num_nota_fiscal "
				+ "			AND c.capa_ent_forcli9 = a.cgc_9 "
				+ "			AND c.capa_ent_forcli4 = a.cgc_4 "
				+ "			AND c.capa_ent_forcli2 = a.cgc_2 "
				+ "			AND b.cgc_cli_for_9 = c.capa_ent_forcli9 "
				+ "			AND b.cgc_cli_for_4 = c.capa_ent_forcli4 "
				+ "			AND b.cgc_cli_for_2 = c.capa_ent_forcli2 "
				+ "			AND c.motivo_devolucao = d.codigo_motivo "
				+ "			AND a.cod_rep_cliente = e.cod_rep_cliente "
				+ "			AND a.cgc_9 = f.fornecedor9 "
				+ "			AND a.cgc_4 = f.fornecedor4 "
				+ "			AND a.cgc_2 = f.fornecedor2 "
				+ "			AND b.cgc_cli_for_9 = f.fornecedor9 "
				+ "			AND b.cgc_cli_for_4 = f.fornecedor4 "
				+ "			AND b.cgc_cli_for_2 = f.fornecedor2 "
				+ "			AND c.capa_ent_forcli9 = f.fornecedor9 "
				+ "			AND c.capa_ent_forcli4 = f.fornecedor4 "
				+ "			AND c.capa_ent_forcli2 = f.fornecedor2 "
				+ "			AND g.natur_operacao = c.natitem_nat_oper "
				+ "			AND g.estado_natoper = c.natitem_est_oper "
				+ "			AND h.tp_forne = f.tipo_fornecedor "
				+ "			AND a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "			AND TO_CHAR(TO_DATE(b.data_transacao), 'MM/YYYY') = '" + mesComZero + "/" + ano + "'"
				+ "			AND g.cod_natureza || g.operacao_fiscal IN ('1.201', '1.202', '2.201', '2.202') "
				+ "			AND c.num_nota_orig <> 0 "
				+ "			GROUP BY f.nome_fornecedor, c.num_nota_orig, b.documento, e.cod_rep_cliente, e.nome_rep_cliente, a.perc_repres, d.descr_motivo, b.data_emissao, "
				+ "                  a.data_emissao, h.tp_forne, h.descricao "
				+ "         ORDER BY f.nome_fornecedor ";
		
		try {
			listDevolucao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
		} catch (Exception e) {
			listDevolucao = new ArrayList<ConsultaFechamentoComissoes>();
		}
		return listDevolucao;
	}
	
	public List<ConsultaFechamentoComissoes> findLanctoManuaisPorRepresentante(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante){
		
		List<ConsultaFechamentoComissoes> listLactoManuais = null;
		
		String query = "  SELECT f.id id, "
				+ "       f.data_lancto dataLancto, "
				+ "       f.campanha campanha, "
				+ "       DECODE(f.tipo, 1, 'Débito', 'Crédito') tipo, "
				+ "       f.representante || ' - ' || g.nome_rep_cliente representante, "
				+ "       f.observacao observacao, "
				+ "       f.valor valor "
				+ "    FROM orion_fin_040 f, pedi_020 g "
				+ "	   WHERE g.cod_rep_cliente = f.representante "
				+ "    AND f.mes = " + mesComZero
				+ "    AND f.ano =  " + ano
				+ "    AND f.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";

		try {
			listLactoManuais = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
		} catch (Exception e) {
			listLactoManuais = new ArrayList<ConsultaFechamentoComissoes>();
		}
		return listLactoManuais;
	}
	
	public List<ConsultaFechamentoComissoes> findTotaisLanctoManuaisPorRepresentante(String mesComZero, int ano, List<ConteudoChaveAlfaNum> listRepresentante){
		
		List<ConsultaFechamentoComissoes> listLactoManuais = null;
		
		String query = " SELECT TOTAL.REPRESENTANTE REPRESENTANTE, "
				+ "       NVL(SUM(TOTAL.TOTALDEBITO), 0) TOTALDEBITO, "
				+ "       NVL(SUM(TOTAL.TOTALCREDITO), 0) TOTALCREDITO FROM( "
				+ "		SELECT a.representante || ' - ' || b.nome_rep_cliente representante, "
				+ "       SUM(a.valor) totalDebito, "
				+ "       0 totalCredito "
				+ "		FROM orion_fin_040 a, pedi_020 b "
				+ "		WHERE b.cod_rep_cliente = a.representante "
				+ "		AND a.mes = " + mesComZero
				+ "		AND a.ano = " + ano
				+ "		AND a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "		AND a.tipo = 1 "
				+ "		GROUP BY a.representante, b.nome_rep_cliente "
				+ "	UNION "
				+ "		SELECT a.representante || ' - ' || b.nome_rep_cliente representante, "
				+ "       0 totalDebito, "
				+ "       SUM(a.valor) totalCredito "
				+ "		FROM orion_fin_040 a, pedi_020 b "
				+ "		WHERE b.cod_rep_cliente = a.representante "
				+ "		AND a.mes = " + mesComZero
				+ "		AND a.ano = " + ano
				+ "		AND a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "		AND a.tipo = 2 "
				+ "		GROUP BY a.representante, b.nome_rep_cliente) TOTAL "
				+ "		GROUP BY TOTAL.REPRESENTANTE ";

		try {
			listLactoManuais = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
		} catch (Exception e) {
			listLactoManuais = new ArrayList<ConsultaFechamentoComissoes>();
		}
		return listLactoManuais;
	}
	
	public int findCargoRepresentante(List<ConteudoChaveAlfaNum> listRepresentante){
		
		int codCargo = 0;
		
		String query = " SELECT a.cd_cargo_rep "
				+ "       FROM pedi_020 a "
				+ "       WHERE a.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")" 
				+ "       GROUP BY a.cd_cargo_rep ";

		try {
			codCargo = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			codCargo = 0;
		}
		return codCargo;
	}
	
	public int findLinhaProduto(String nivel, String grupo){
		
		int linhaProduto = 0;
		
		String query = " SELECT NVL(a.linha_produto, 0) "
				+ "FROM basi_030 a "
				+ "WHERE a.nivel_estrutura = '" + nivel +"'"
				+ "AND a.referencia = '" + grupo +"'";

		try {
			linhaProduto = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			linhaProduto = 0;
		}
		return linhaProduto;
	}
	
	public String findDataInicialMostruario(String mes, int ano){
		
		String dataInicial = "";
		
		String query = " SELECT TO_CHAR(ADD_MONTHS(TO_DATE('" + mes + "/" + ano + "', 'MM/YYYY'), -6), 'MM/YYYY') AS resultado FROM dual ";
		
		try {
			dataInicial = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			dataInicial = "";
		}
		return "01/" + dataInicial;
	}
	
	public List<ConsultaFechamentoComissoes> findItensDevolvidos(List<ConteudoChaveAlfaNum> listRepresentante, String estacao){
		
		String query = " SELECT a.representante || ' - ' || b.nome_rep_cliente representante, "
				+ "       a.estacao estacao, "
				+ "       a.nivel || '.' || a.grupo || '.' || a.subgrupo || '.' || a.item || ' - ' || c.descr_referencia produto, "
				+ "       LPAD(a.tab_col, 2, 0) || '.' || LPAD(a.tab_mes, 2, 0) || '.' || LPAD(a.tab_seq, 2, 0) tabPreco, "
				+ "       a.quantidade quantidade, "
				+ "       a.preco_unt precoUnt, "
				+ "       a.total total "
				+ "		FROM orion_fin_050 a, pedi_020 b, basi_030 c "
				+ "		WHERE b.cod_rep_cliente = a.representante "
				+ "		AND c.nivel_estrutura = a.nivel "
				+ "		AND c.referencia = a.grupo "
				+ "		AND a.estacao LIKE '%" + estacao + "%'"
				+ "		AND a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
	}
	
	public List<ConsultaFechamentoComissoes> findPedidoVendaMostruario(List<ConteudoChaveAlfaNum> listRepresentante, String dataInicial, String dataFinal, 
			int tabCol, int tabMes, int tabSeq){
		
		String query = " SELECT a.pedido_venda pedido, "
				+ "       a.data_entr_venda, "
				+ "       c.cd_it_pe_nivel99 || '.' || c.cd_it_pe_grupo || '.' || c.cd_it_pe_subgrupo || '.' || c.cd_it_pe_item || ' - ' || d.descr_referencia produto, "
				+ "       c.qtde_pedida quantidade, "
				+ "       c.cd_it_pe_nivel99 nivel, "
				+ "       c.cd_it_pe_grupo grupo, "
				+ "       c.cd_it_pe_subgrupo subgrupo, "
				+ "       c.cd_it_pe_item item, "
				+ "       c.qtde_pedida quantidade, "
				+ "       d.linha_produto linhaProduto, "
				+ "       a.colecao_tabela tabCol, "
				+ "       a.mes_tabela tabMes, "
				+ "       a.sequencia_tabela tabSeq, "
				+ "       inter_fn_get_val_unit_tab(" + tabCol + ", " + tabMes + ", " + tabSeq + ", c.cd_it_pe_nivel99, c.cd_it_pe_grupo, c.cd_it_pe_subgrupo, c.cd_it_pe_item) precoUnt, "
				+ "       c.qtde_pedida * inter_fn_get_val_unit_tab(" + tabCol + ", " + tabMes + ", " + tabSeq + ", c.cd_it_pe_nivel99, c.cd_it_pe_grupo, c.cd_it_pe_subgrupo, c.cd_it_pe_item) total "
				+ "       FROM pedi_100 a, pedi_020 b, pedi_110 c, basi_030 d "
				+ "       WHERE a.cli_ped_cgc_cli9 = b.cgc_9 "
				+ "       AND a.cli_ped_cgc_cli4 = b.cgc_4 "
				+ "       AND a.cli_ped_cgc_cli2 = b.cgc_2 "
				+ "       AND a.pedido_venda = c.pedido_venda "
				+ "       AND d.nivel_estrutura = c.cd_it_pe_nivel99 "
				+ "       AND d.referencia = c.cd_it_pe_grupo "
				+ "       AND a.situacao_venda = 10 "
				+ "       AND a.cond_pgto_venda <> 27 "
				+ "       AND a.cod_forma_pagto <> 99 "
				+ "       AND b.cod_rep_cliente IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "  AND TO_DATE(TO_CHAR(a.data_emis_venda, 'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN TO_DATE('" + dataInicial + "', 'DD/MM/YYYY') AND TO_DATE('" + dataFinal +"', 'DD/MM/YYYY')";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
	}
	
	public int findMostruarioEstacaoPorRepres(List<ConteudoChaveAlfaNum> listRepresentante, String estacao){
		
		int registros = 0;
		
		String query = " SELECT COUNT(*) FROM orion_fin_060 a "
				+ "		WHERE a.estacao LIKE '%" + estacao + "%' "
				+ "		AND a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";
		
		try {
			registros = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			registros = 0;
		}
		return registros;
	}
	
	public List<ConsultaFechamentoComissoes> findItensMostruarioAnalitico(List<ConteudoChaveAlfaNum> listRepresentante, String estacao){
		
		String query = " SELECT a.id, "
				+ "       a.representante || ' - ' || b.nome_rep_cliente representante, "
				+ "       a.estacao estacao, "
				+ "       a.nivel || '.' || a.grupo || '.' || a.subgrupo || '.' || a.item || ' - ' || c.descr_referencia produto, "
				+ "       a.qtde_enviada qtdeEnviada, "
				+ "       a.qtde_devolvida qtdeDevolvida, "
				+ "       a.diferenca diferenca, "
				+ "       a.valor valor, "
				+ "       a.desc_60_porcento desc60Porcento, "
				+ "       a.bonus_30_porcento bonus30Porcento, "
				+ "       a.bonus_100_porcento bonus100Porcento, "
				+ "       a.valor_cobrado valorCobrado, "
				+ "       LPAD(tab_col, 2, 0) || '.' || LPAD(tab_mes, 2, 0) || '.' || LPAD(tab_seq, 2, 0) tabPreco "
				+ "		FROM orion_fin_060 a, pedi_020 b, basi_030 c "
				+ "		WHERE b.cod_rep_cliente = a.representante "
				+ "		AND c.nivel_estrutura = a.nivel "
				+ "		AND c.referencia = a.grupo "
				+ "		AND a.estacao LIKE '%" + estacao + "%' "
				+ "		AND a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
	}
	
	public List<ConsultaFechamentoComissoes> findMostruarioSintetico(List<ConteudoChaveAlfaNum> listRepresentante, String estacao){
		
		String query = " SELECT a.id id, "
				+ "       a.representante || ' - ' || b.nome_rep_cliente representante, "
				+ "       a.estacao estacao, "
				+ "       a.num_parcela numParcela, "
				+ "       a.valor valor, "
				+ "       a.status status "
				+ "		FROM orion_fin_070 a, pedi_020 b "
				+ "     WHERE b.cod_rep_cliente = a.representante"
				+ "		AND a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "		AND a.estacao LIKE  '%" + estacao + "%' "
				+ "     ORDER BY a.num_parcela ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaFechamentoComissoes.class));
	}
	
	public int pagarParcelaMostruario(int id){
		
		int status = 0;
		
		String query = " update orion_fin_070 "
				+ " set status = 1 "
				+ " where id = ? ";
		try {
			status = jdbcTemplate.update(query, id);
		} catch (Exception e) {
			status = 0;
		}
		return status;	
	}
	
	public float findTotalCobradoPorRepres(List<ConteudoChaveAlfaNum> listRepresentante, String estacao) {
		
		float soma = 0;
		
		String query = " SELECT SUM(a.valor_cobrado) FROM orion_fin_060 a "
				+ "		WHERE a.representante IN (" + ConteudoChaveAlfaNum.parseValueToString(listRepresentante) + ")"
				+ "		AND a.estacao LIKE '%" + estacao + "%' ";
		
		try {
			soma = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			soma = 0;
		}
		return soma;
		
	}

}
