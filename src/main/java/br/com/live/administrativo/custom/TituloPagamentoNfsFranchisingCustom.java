package br.com.live.administrativo.custom;

import br.com.live.administrativo.model.LogTituloNfsFranchising;
import br.com.live.administrativo.model.ParcelaInfoNfsFranchising;
import br.com.live.administrativo.model.TituloPagamentoNfsFranchisingConsulta;
import br.com.live.administrativo.model.TomadorNfsFranchising;
import br.com.live.util.ConteudoChaveAlfaNum;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TituloPagamentoNfsFranchisingCustom {

    private final JdbcTemplate jdbcTemplate;

    public TituloPagamentoNfsFranchisingCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TituloPagamentoNfsFranchisingConsulta> findAllTituloPagamentoNfsFranchising(String dataInicio, String dataFim) {

        String query = " SELECT sub.dataemissao, " +
                "       sub.cnpjLoja, " +
                "       sub.descricaoloja, " +
                "       sub.numduplicata, " +
                "       count(sub.seqduplicata) qtdParcelas, " +
                "       sub.numpedido, " +
                "       SUM(sub.valorduplicata) valorTotalDuplicata, " +
                "       sub.descontoitem1, " +
                "       sub.descontoitem2, " +
                "       sub.descontoitem3, " +
                "       sub.codNaturezaConcat, " +
                "       sub.condicaoPagamento, " +
                "       sub.descrCondicaoPagamento " +
                "FROM ( " +
                "    SELECT a.DATA_EMISSAO dataEmissao, " +
                "           (lpad(a.CLI_DUP_CGC_CLI9,8,0) ||'.'|| lpad(a.CLI_DUP_CGC_CLI4,4,0) ||'.'|| lpad(a.CLI_DUP_CGC_CLI2,2,0)) cnpjLoja, " +
                "           b.FANTASIA_CLIENTE descricaoLoja, " +
                "           a.NUM_DUPLICATA numDuplicata, " +
                "           a.SEQ_DUPLICATAS seqDuplicata, " +
                "           a.PEDIDO_VENDA numPedido, " +
                "           a.VALOR_DUPLICATA valorDuplicata, " +
                "           c.DESCONTO_ITEM1 descontoItem1, " +
                "           c.DESCONTO_ITEM2 descontoItem2, " +
                "           c.DESCONTO_ITEM3 descontoItem3, " +
                "           LISTAGG(d.NATOPENO_NAT_OPER, '-') WITHIN GROUP (ORDER BY d.NATOPENO_NAT_OPER) AS codNaturezaConcat, " +
                "           c.COND_PGTO_VENDA condicaoPagamento, " +
                "           e.DESCR_PG_CLIENTE descrCondicaoPagamento " +
                "    FROM fatu_070 a " +
                "    JOIN pedi_010 b ON b.CGC_9 = a.CLI_DUP_CGC_CLI9 " +
                "                   AND b.CGC_4 = a.CLI_DUP_CGC_CLI4 " +
                "                   AND b.CGC_2 = a.CLI_DUP_CGC_CLI2 " +
                "    JOIN pedi_100 c ON c.PEDIDO_VENDA = a.PEDIDO_VENDA " +
                "    JOIN fatu_060 d ON d.CH_IT_NF_CD_EMPR = 1 " +
                "                   AND d.CH_IT_NF_NUM_NFIS = a.NUM_DUPLICATA " +
                "    JOIN pedi_070 e ON e.COND_PGT_CLIENTE = c.COND_PGTO_VENDA " +
                "    WHERE a.SITUACAO_DUPLIC = 0 " +
                "      AND b.TIPO_CLIENTE = 4 " +
                "      AND a.DATA_EMISSAO BETWEEN TO_DATE( '" + dataInicio + "', 'yyyy-mm-dd') " +
                "                             AND TO_DATE( '" + dataFim + "', 'yyyy-mm-dd') " +
                "    GROUP BY a.DATA_EMISSAO, " +
                "             a.CLI_DUP_CGC_CLI9, " +
                "             a.CLI_DUP_CGC_CLI4, " +
                "             a.CLI_DUP_CGC_CLI2, " +
                "             b.FANTASIA_CLIENTE, " +
                "             a.NUM_DUPLICATA, " +
                "             a.SEQ_DUPLICATAS, " +
                "             a.TIPO_TITULO, " +
                "             a.PEDIDO_VENDA, " +
                "             a.VALOR_DUPLICATA, " +
                "             c.DESCONTO_ITEM1, " +
                "             c.DESCONTO_ITEM2, " +
                "             c.DESCONTO_ITEM3, " +
                "             c.COND_PGTO_VENDA, " +
                "             e.DESCR_PG_CLIENTE " +
                "    ORDER BY a.NUM_DUPLICATA DESC " +
                ") sub " +
                "GROUP BY sub.dataemissao, " +
                "         sub.cnpjLoja, " +
                "         sub.descricaoloja, " +
                "         sub.numduplicata, " +
                "         sub.numpedido, " +
                "         sub.descontoitem1, " +
                "         sub.descontoitem2, " +
                "         sub.descontoitem3, " +
                "         sub.codNaturezaConcat, " +
                "         sub.condicaoPagamento, " +
                "         sub.descrCondicaoPagamento " +
                "ORDER BY sub.dataemissao, " +
                "         sub.numduplicata";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TituloPagamentoNfsFranchisingConsulta.class));
    }

    public TituloPagamentoNfsFranchisingConsulta findTituloPagamentoNfsFranchisingCnpjDuplicata(String cnpjTomador, String numDuplicata) {

        int cgc9 = Integer.parseInt(cnpjTomador.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjTomador.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjTomador.substring(12));

        String query = " SELECT sub.dataemissao, " +
                "               sub.cnpjLoja, " +
                "               sub.descricaoloja, " +
                "               sub.numduplicata, " +
                "               count(sub.seqduplicata) qtdParcelas, " +
                "               sub.numpedido, " +
                "               SUM(sub.valorduplicata) valorTotalDuplicata, " +
                "               sub.descontoitem1, " +
                "               sub.descontoitem2, " +
                "               sub.descontoitem3, " +
                "               sub.codNaturezaConcat, " +
                "               sub.condicaoPagamento, " +
                "               sub.descrCondicaoPagamento " +
                "        FROM ( " +
                "            SELECT a.DATA_EMISSAO dataEmissao, " +
                "                   (lpad(a.CLI_DUP_CGC_CLI9,8,0) ||'.'|| lpad(a.CLI_DUP_CGC_CLI4,4,0) ||'.'|| lpad(a.CLI_DUP_CGC_CLI2,2,0)) cnpjLoja, " +
                "                   b.FANTASIA_CLIENTE descricaoLoja, " +
                "                   a.NUM_DUPLICATA numDuplicata, " +
                "                   a.SEQ_DUPLICATAS seqDuplicata, " +
                "                   a.PEDIDO_VENDA numPedido, " +
                "                   a.VALOR_DUPLICATA valorDuplicata, " +
                "                   c.DESCONTO_ITEM1 descontoItem1, " +
                "                   c.DESCONTO_ITEM2 descontoItem2, " +
                "                   c.DESCONTO_ITEM3 descontoItem3, " +
                "                   LISTAGG(d.NATOPENO_NAT_OPER, '-') WITHIN GROUP (ORDER BY d.NATOPENO_NAT_OPER) AS codNaturezaConcat, " +
                "                   c.COND_PGTO_VENDA condicaoPagamento, " +
                "                   e.DESCR_PG_CLIENTE descrCondicaoPagamento " +
                "            FROM fatu_070 a " +
                "            JOIN pedi_010 b ON b.CGC_9 = a.CLI_DUP_CGC_CLI9 " +
                "                           AND b.CGC_4 = a.CLI_DUP_CGC_CLI4 " +
                "                           AND b.CGC_2 = a.CLI_DUP_CGC_CLI2 " +
                "            JOIN pedi_100 c ON c.PEDIDO_VENDA = a.PEDIDO_VENDA " +
                "            JOIN fatu_060 d ON d.CH_IT_NF_CD_EMPR = 1 " +
                "                           AND d.CH_IT_NF_NUM_NFIS = a.NUM_DUPLICATA " +
                "            JOIN pedi_070 e ON e.COND_PGT_CLIENTE = c.COND_PGTO_VENDA " +
                "            WHERE a.SITUACAO_DUPLIC = 0 " +
                "               AND b.TIPO_CLIENTE = 4 " +
                "               AND a.CLI_DUP_CGC_CLI9 = " + cgc9 +
                "               AND a.CLI_DUP_CGC_CLI4 = " + cgc4 +
                "               AND a.CLI_DUP_CGC_CLI2 = " + cgc2 +
                "               AND a.NUM_DUPLICATA = " + numDuplicata +
                "            GROUP BY a.DATA_EMISSAO, " +
                "                     a.CLI_DUP_CGC_CLI9, " +
                "                     a.CLI_DUP_CGC_CLI4, " +
                "                     a.CLI_DUP_CGC_CLI2, " +
                "                     b.FANTASIA_CLIENTE, " +
                "                     a.NUM_DUPLICATA, " +
                "                     a.SEQ_DUPLICATAS, " +
                "                     a.TIPO_TITULO, " +
                "                     a.PEDIDO_VENDA, " +
                "                     a.VALOR_DUPLICATA, " +
                "                     c.DESCONTO_ITEM1, " +
                "                     c.DESCONTO_ITEM2, " +
                "                     c.DESCONTO_ITEM3, " +
                "                     c.COND_PGTO_VENDA, " +
                "                     e.DESCR_PG_CLIENTE " +
                "            ORDER BY a.NUM_DUPLICATA DESC " +
                "        ) sub " +
                "        GROUP BY sub.dataemissao, " +
                "                 sub.cnpjLoja, " +
                "                 sub.descricaoloja, " +
                "                 sub.numduplicata, " +
                "                 sub.numpedido, " +
                "                 sub.descontoitem1, " +
                "                 sub.descontoitem2, " +
                "                 sub.descontoitem3, " +
                "                 sub.codNaturezaConcat, " +
                "                 sub.condicaoPagamento, " +
                "                 sub.descrCondicaoPagamento " +
                "        ORDER BY sub.dataemissao, " +
                "                 sub.numduplicata ";

        List<TituloPagamentoNfsFranchisingConsulta> results = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TituloPagamentoNfsFranchisingConsulta.class));
        return results.stream().findFirst().orElseGet(TituloPagamentoNfsFranchisingConsulta::new);
    }

    public List<ParcelaInfoNfsFranchising> obterParcelasCnpjDuplicata(String cnpjTomador, String numDuplicata){

        int cgc9 = Integer.parseInt(cnpjTomador.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjTomador.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjTomador.substring(12));

        String query = " SELECT a.SEQ_DUPLICATAS numeroParcela, " +
                "        a.DATA_VENC_DUPLIC dataVencimento, " +
                "        a.VALOR_DUPLICATA valor       " +
                "FROM fatu_070 a " +
                "JOIN pedi_010 b ON b.CGC_9 = a.CLI_DUP_CGC_CLI9 AND b.CGC_4 = a.CLI_DUP_CGC_CLI4 AND b.CGC_2 = a.CLI_DUP_CGC_CLI2 " +
                "JOIN pedi_100 c ON c.PEDIDO_VENDA = a.PEDIDO_VENDA " +
                "WHERE a.SITUACAO_DUPLIC = 0 " +
                "  AND b.TIPO_CLIENTE = 4 " +
                "  AND a.CLI_DUP_CGC_CLI9 = " + cgc9 +
                "  AND a.CLI_DUP_CGC_CLI4 = " + cgc4 +
                "  AND a.CLI_DUP_CGC_CLI2 = " + cgc2 +
                "  AND a.NUM_DUPLICATA = " + numDuplicata +
                "GROUP BY a.SEQ_DUPLICATAS, a.DATA_VENC_DUPLIC, a.VALOR_DUPLICATA " +
                "ORDER BY a.DATA_VENC_DUPLIC";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ParcelaInfoNfsFranchising.class));
    }

    public TomadorNfsFranchising obterDadosEnderecoTomadorNfsFranchising(String cnpjTomador){

        int cgc9 = Integer.parseInt(cnpjTomador.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjTomador.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjTomador.substring(12));

        String query = " SELECT a.FANTASIA_CLIENTE fantasiaCliente, a.ENDERECO_CLIENTE enderecoCliente, a.NUMERO_IMOVEL numeroImovel, a.COMPLEMENTO complemento, a.BAIRRO bairro, b.COD_CIDADE_IBGE codCidadeIbge, b.ESTADO estado, a.CEP_CLIENTE cepCliente, a.TELEFONE_CLIENTE telefoneCliente, a.E_MAIL email  FROM pedi_010 a, basi_160 b " +
                " WHERE b.COD_CIDADE = a.COD_CIDADE " +
                " AND a.CGC_9 = " + cgc9 +
                " AND a.CGC_4 = " + cgc4 +
                " AND a.CGC_2 = " + cgc2;

        List<TomadorNfsFranchising> results = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TomadorNfsFranchising.class));
        return results.stream().findFirst().orElseGet(TomadorNfsFranchising::new);
    }

    public List<LogTituloNfsFranchising> findAllHistoricoTitulosPagamentoNfsFranchising (String dataEmissaoInicio, String dataEmissaoFim, String cnpj, String numDuplicata){

        String query = "select a.id, a.numero_lote numeroLote, a.num_duplicata numDuplicata, a.cnpj_tomador cnpjTomador, b.fantasia_cliente descricaoTomador, a.data_envio dataEnvio, a.conteudo_xml conteudoXml, a.status, a.motivo " +
                "from orion_fin_095 a, pedi_010 b " +
                "where trunc(a.data_envio) between to_date('" + dataEmissaoInicio + "', 'yyyy-mm-dd') and to_date('" + dataEmissaoFim + "', 'yyyy-mm-dd') ";

        if (!cnpj.equals("0")) {
            query += "and a.cnpj_tomador = '" + cnpj + "' ";
        }

        if (!numDuplicata.equals("0")) {
            query += "and a.num_duplicata = '" + numDuplicata + "' ";
        }

        query += "and a.cnpj_tomador = lpad(b.cgc_9,8,0) || lpad(b.cgc_4,4,0) || lpad(b.cgc_2,2,0) order by a.data_envio desc, a.numero_lote desc";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LogTituloNfsFranchising.class));
    }

    public List<ConteudoChaveAlfaNum> findAllLojasHistoricoNfsFranchising(){

        String query = "select distinct a.cnpj_tomador value, b.fantasia_cliente label from orion_fin_095 a, pedi_010 b " +
                "where a.cnpj_tomador = lpad(b.cgc_9,8,0) || lpad(b.cgc_4,4,0) || lpad(b.cgc_2,2,0) order by b.fantasia_cliente";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
    }
}


