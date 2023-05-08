package br.com.live.custom;

import br.com.live.entity.ParametroGeralDreEntity;
import br.com.live.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DreLojaCustom {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DreLojaCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ParametroGeralDreEntity findParametrosDreByMesAno(int mesDre, int anoDre){

        String query="select a.perc_encargos percEncargos, a.val_imposto_planejamento valImpostoPlanejamento, a.val_custo_venda_produto valCustoVendaProduto from orion_fin_020 a " +
                "where a.mes_dre = " + mesDre +
                " and a.ano_dre = " + anoDre;

        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ParametroGeralDreEntity.class));

    }

    public List<ConciliacaoLojaDreConsulta> findAllDreConciliacaoDreMesAno(int mesDre, int anoDre) {
        String query = "select a.id, a.cnpj_loja cnpjLoja, b.fantasia_cliente nomeLoja, a.mes_dre mesDre, a.ano_dre anoDre, a.val_taxa_captura valTaxaCaptura, a.val_custo_antecipacao valCustoAntecipacao from orion_fin_025 a, pedi_010 b " +
                " where a.cnpj_loja = (b.cgc_9 ||'.'|| b.cgc_4 ||'.'|| b.cgc_2) " +
                " and a.mes_dre = ? " +
                " and a.ano_dre = ?" +
                " order by a.id";
        return jdbcTemplate.query(query, new Object[]{mesDre, anoDre}, BeanPropertyRowMapper.newInstance(ConciliacaoLojaDreConsulta.class));
    }

    public List<DreLojaConsulta> findAllDreLoja(){
        String query = "select a.cnpj_loja cnpjLoja, b.fantasia_cliente nomeLoja, a.mes_dre mesDre, a.ano_dre anoDre, d.nome_fornecedor nomeSupervisor from orion_fin_030 a, pedi_010 b, orion_fin_001 c, supr_010 d " +
                " where a.cnpj_loja = (b.cgc_9 ||'.'|| b.cgc_4 ||'.'|| b.cgc_2) " +
                " and a.cnpj_loja = c.cnpj_loja " +
                " and c.cnpj_supervisor = (d.fornecedor9 ||'.'|| d.fornecedor4 ||'.'|| d.fornecedor2) " +
                " group by a.cnpj_loja, b.fantasia_cliente, a.mes_dre, a.ano_dre, d.nome_fornecedor ";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DreLojaConsulta.class));
    }

    public void deleteParametrosLojaByMesAno(int mesDre, int anoDre) {
        String queryDelete = " delete from orion_fin_025 a " +
                " where a.mes_dre = " + mesDre +
                " and a.ano_dre = " + anoDre;
        jdbcTemplate.update(queryDelete);
    }

    public void deleteDreLoja(String cnpjLoja, int mesDre, int anoDre) {
        String queryDelete = " delete from orion_fin_030 a " +
                " where a.mes_dre = " + mesDre +
                " and a.ano_dre = " + anoDre +
                " and a.cnpj_loja = '" + cnpjLoja + "'";
        jdbcTemplate.update(queryDelete);
    }

    public double obterValorLancamentosContaContabilMesAno(int codContaContabil, String centroCustoConcat, int mesDre, int anoDre){

        double totalLancamentosContaContabil = 0;

        String query = "select sum(case when a.debito_credito = 'D' then -valor_lancto else a.valor_lancto end) as totalValorLancamento from cont_600 a " +
                " where a.exercicio = " + anoDre +
                " and a.periodo = " + mesDre +
                " and a.centro_custo in (" + centroCustoConcat + ") " +
                " and a.conta_reduzida = " + codContaContabil +
                " group by a.conta_reduzida";

        try {
            totalLancamentosContaContabil = jdbcTemplate.queryForObject(query, Double.class);
        } catch (Exception e) {
            totalLancamentosContaContabil = 0;
        }

        return totalLancamentosContaContabil;
    }

    public double obterValorLancamentosContasContabeisMesAno(String contaContabilConcat, String centroCustoConcat, int mesDre, int anoDre){

        double totalLancamentosContasContabeis = 0;

        String query = "select sum(case when a.debito_credito = 'D' then -valor_lancto else a.valor_lancto end) as totalValorLancamento from cont_600 a " +
                " where a.exercicio = " + anoDre +
                " and a.periodo = " + mesDre +
                " and a.centro_custo in (" + centroCustoConcat + ") " +
                " and a.conta_reduzida in (" + contaContabilConcat + ") ";
        try {
            totalLancamentosContasContabeis = jdbcTemplate.queryForObject(query, Double.class);
        } catch (Exception e) {
            totalLancamentosContasContabeis = 0;
        }

        return totalLancamentosContasContabeis;
    }

    public int validaContaContabilSystextil(int contaContabil){

        String query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM cont_535 WHERE cod_reduzido = " + contaContabil + ") THEN 1 ELSE 0 END FROM DUAL";

        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public ConciliacaoLojaDre findConciliacaoLojaDreCnpjMesAno(String cnpjLoja, int mesConciliacao, int anoConciliacao){

        String query = "select a.id, a.cnpj_loja cnpjLoja, a.ano_dre anoDre, a.mes_dre mesDre, a.val_taxa_captura valTaxaCaptura, a.val_custo_antecipacao valCustoAntecipacao from orion_fin_025 a " +
                "where a.cnpj_loja = ? " +
                "and a.mes_dre = ? " +
                "and a.ano_dre = ? ";

        try {
            return jdbcTemplate.queryForObject(query, new Object[] {cnpjLoja, mesConciliacao, anoConciliacao}, BeanPropertyRowMapper.newInstance(ConciliacaoLojaDre.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public double obterValorTotalFaturamentoMesAno(int mesDre, int anoDre, String cnpjLoja) {

        double valorTotalFaturamentoMesAno = 0;

        String query2 = "";

        if (cnpjLoja != null) {
            String[] cnpj = cnpjLoja.split("\\.");
            int cgc9 = Integer.parseInt(cnpj[0]);
            int cgc4 = Integer.parseInt(cnpj[1]);
            int cgc2 = Integer.parseInt(cnpj[2]);

            query2 = " AND f.cgc_9 = '" + cgc9 + "' " +
                    " AND f.cgc_4 = '" + cgc4 + "' " +
                    " AND f.cgc_2 = '" + cgc2 + "'";
        }

        String query = "SELECT SUM(f.valor_itens_nfis) " +
                " FROM fatu_050 f " +
                " WHERE f.codigo_empresa = 100 " +
                " AND f.natop_nf_nat_oper IN (643,354,690,683) " +
                " AND TO_CHAR(f.data_emissao, 'YYYY') = " + anoDre +
                " AND TO_CHAR(f.data_emissao, 'MM') = " + mesDre +
                query2;

        try {
            valorTotalFaturamentoMesAno = jdbcTemplate.queryForObject(query, Double.class);
        } catch (Exception e) {
            valorTotalFaturamentoMesAno = 0;
        }

        return valorTotalFaturamentoMesAno;
    }

    public DreLojaCalculo obterDadosDreAcumulado(String seqConsulta, String cnpjLoja, int mesDre, int anoDre){

        String query="SELECT " +
                "  SUM(CASE WHEN MES_DRE <= " + mesDre + " THEN VAL_REAL_ANO_ANT ELSE 0 END) AS valPropriedadeMesAnoAnterior, " +
                "  SUM(CASE WHEN MES_DRE <= " + mesDre + " THEN VAL_ORCADO ELSE 0 END) AS valPropriedadeOrcadoMesAnoAtual, " +
                "  SUM(CASE WHEN MES_DRE <= " + mesDre + " THEN VAL_REAL ELSE 0 END) AS valPropriedadeMesAnoAtual " +
                "FROM " +
                "  ORION_FIN_030 " +
                "WHERE " +
                "  SEQ_CONSULTA = '" + seqConsulta + "' " +
                "  AND CNPJ_LOJA = '" + cnpjLoja + "' " +
                "  AND MES_DRE <= " + mesDre +
                "  AND ANO_DRE = " + anoDre +
                "  AND TIPO_DRE = 1";

        try {
            return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(DreLojaCalculo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<DreLoja> obterCamposDreLojaCnpjMesAnVigente(String cnpjLoja, int mesDre, int anoDre){

        String query = "SELECT ID, SEQ_CONSULTA seqConsulta, CNPJ_LOJA cnpjLoja, ANO_DRE anoDre, MES_DRE mesDre, TIPO_DRE tipoDre, PROPRIEDADE, VAL_REAL_ANO_ANT valRealAnoAnt, PERC_REAL_ANO_ANT percRealAnoAnt, VAL_ORCADO valOrcado, PERC_ORCADO percOrcado, VAL_REAL valReal, PERC_REAL percReal, VAL_DIFERENCA_ORCADO_REAL valDiferencaOrcadoReal, PERC_DIFERENCA_ORCADO_REAL percDiferencaOrcadoReal, PERC_DIFERENCA_REAL_VIG_ANT percDiferencaRealVigAnt " +
                " FROM ORION_FIN_030 " +
                " WHERE CNPJ_LOJA = '" + cnpjLoja + "' " +
                " AND ANO_DRE = " + anoDre +
                " AND MES_DRE = " + mesDre +
                " AND TIPO_DRE = 1";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DreLoja.class));
    }

    public List<DreLoja> findAllFieldsDreLojaByCnpjMesAno(String cnpjLoja, int mesDre, int anoDre) {

        String query = "SELECT ID, SEQ_CONSULTA seqConsulta, CNPJ_LOJA cnpjLoja, ANO_DRE anoDre, MES_DRE mesDre, TIPO_DRE tipoDre, PROPRIEDADE, VAL_REAL_ANO_ANT valRealAnoAnt, PERC_REAL_ANO_ANT percRealAnoAnt, VAL_ORCADO valOrcado, PERC_ORCADO percOrcado, VAL_REAL valReal, PERC_REAL percReal, VAL_DIFERENCA_ORCADO_REAL valDiferencaOrcadoReal, PERC_DIFERENCA_ORCADO_REAL percDiferencaOrcadoReal, PERC_DIFERENCA_REAL_VIG_ANT percDiferencaRealVigAnt " +
                " FROM ORION_FIN_030 " +
                " WHERE CNPJ_LOJA = '" + cnpjLoja + "' " +
                " AND ANO_DRE = " + anoDre +
                " AND MES_DRE = " + mesDre +
                " ORDER BY SEQ_CONSULTA";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DreLoja.class));
    }

    public LancamentoLojaMesAno obterDadosLancamentoLojaMesAno(String cnpjLoja, int mesLancamento, int anoLancamento){

        String query = "SELECT ID, CNPJ_LOJA cnpjLoja, ANO_LANCAMENTO anoLancamento, MES_LANCAMENTO mesLancamento, QTD_PECA_FATURADA qtdPecaFaturada, QTD_PECA_CONSUMO qtdPecaConsumo, VAL_FATURAMENTO valFaturamento, VAL_IMPOSTO_FATURAMENTO valImpostoFaturamento FROM ORION_FIN_010 " +
                "WHERE CNPJ_LOJA = '" + cnpjLoja + "' " +
                "AND MES_LANCAMENTO = " + mesLancamento +
                "AND ANO_LANCAMENTO = " + anoLancamento;

        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(LancamentoLojaMesAno.class));
    }

    public DreLoja obterDadosDreAcumuladoLojaCnpjMesSeq(String seqConsulta, String cnpjLoja, int mesDre, int anoDre){

        String query = "SELECT ID, SEQ_CONSULTA seqConsulta, CNPJ_LOJA cnpjLoja, ANO_DRE anoDre, MES_DRE mesDre, TIPO_DRE tipoDre, PROPRIEDADE, VAL_REAL_ANO_ANT valRealAnoAnt, PERC_REAL_ANO_ANT percRealAnoAnt, VAL_ORCADO valOrcado, PERC_ORCADO percOrcado, VAL_REAL valReal, PERC_REAL percReal, VAL_DIFERENCA_ORCADO_REAL valDiferencaOrcadoReal, PERC_DIFERENCA_ORCADO_REAL percDiferencaOrcadoReal, PERC_DIFERENCA_REAL_VIG_ANT percDiferencaRealVigAnt " +
                " FROM ORION_FIN_030 " +
                " WHERE CNPJ_LOJA = '" + cnpjLoja + "' " +
                " AND ANO_DRE = " + anoDre +
                " AND MES_DRE = " + mesDre +
                " AND SEQ_CONSULTA = '" + seqConsulta + "' " +
                " AND TIPO_DRE = 2";

        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(DreLoja.class));
    }
}