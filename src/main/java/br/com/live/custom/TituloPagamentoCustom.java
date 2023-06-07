package br.com.live.custom;

import br.com.live.model.TituloPagamentoIntegrado;
import br.com.live.util.ConteudoChaveAlfaNum;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TituloPagamentoCustom {

    private final JdbcTemplate jdbcTemplate;
    private ContabilidadeCustom contabilidadeCustom;


    public TituloPagamentoCustom(JdbcTemplate jdbcTemplate, ContabilidadeCustom contabilidadeCustom) {
        this.jdbcTemplate = jdbcTemplate;
        this.contabilidadeCustom = contabilidadeCustom;
    }

    public boolean existsTituloCadastradoSystextil(int codEmpresa, int tipoTitulo, int numDuplicata, int seqDuplicata, String cnpjLive, String cnpjCliente){

        int cgc9Live = Integer.parseInt(cnpjLive.substring(0, 8));
        int cgc4Live = Integer.parseInt(cnpjLive.substring(8, 12));
        int cgc2Live = Integer.parseInt(cnpjLive.substring(12));

        int cgc9Cli = Integer.parseInt(cnpjCliente.substring(0, 8));
        int cgc4Cli = Integer.parseInt(cnpjCliente.substring(8, 12));
        int cgc2Cli = Integer.parseInt(cnpjCliente.substring(12));

        boolean existsTituloCadastradoSystextil;

        String query=" select 1 from fatu_070 " +
                     " where fatu_070.codigo_empresa = " + codEmpresa +
                     " and fatu_070.tipo_titulo = " + tipoTitulo +
                     " and fatu_070.num_duplicata = " + numDuplicata +
                     " and fatu_070.seq_duplicatas = " + seqDuplicata +
                     " and (fatu_070.cli_dup_cgc_cli9 <> " + cgc9Live +
                     " or fatu_070.cli_dup_cgc_cli4 <> " + cgc4Live +
                     " or fatu_070.cli_dup_cgc_cli2 <> " + cgc2Live + " ) " +
                     " and fatu_070.cli9resptit = " + cgc9Cli +
                     " and fatu_070.cli4resptit = " + cgc4Cli +
                     " and fatu_070.cli2resptit = " + cgc2Cli;

        try {
            existsTituloCadastradoSystextil = jdbcTemplate.queryForObject(query, boolean.class);
        } catch (Exception e) {
            existsTituloCadastradoSystextil = false;
        }
        return existsTituloCadastradoSystextil;
    }

    public int obterCodExercicioConfiguradoDataEmissao(int codEmpresa, String dataEmissao){

        int codExercicioCadastrado = 0;

        String query=" SELECT CONT_500.EXERCICIO " +
                "FROM CONT_500 " +
                "WHERE COD_EMPRESA = " + codEmpresa +
                " AND TO_DATE(' " + dataEmissao +" ', 'YYYY-MM-DD') BETWEEN per_inicial AND per_final";

        try {
            codExercicioCadastrado = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codExercicioCadastrado = 0;
        }
        return codExercicioCadastrado;
    }

    public int obterCodLocalEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codLocal = 0;

        String query = "select empr_070.cod_local " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codLocal = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codLocal = 0;
        }
        return codLocal;
    }

    public int obterCodMoedaEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codMoeda = 0;

        String query = "select empr_070.moeda_titulo " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codMoeda = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codMoeda = 0;
        }
        return codMoeda;
    }

    public int obterRespRecebEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codRespReceb = 0;

        String query = "select empr_070.responsavel_receb " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codRespReceb = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codRespReceb = 0;
        }
        return codRespReceb;
    }

    public int obterPortadorDuplicataEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codPortadorDuplicata = 0;

        String query = "select empr_070.portador_duplic " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codPortadorDuplicata = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codPortadorDuplicata = 0;
        }
        return codPortadorDuplicata;
    }

    public int obterTransacaoEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codTransacao = 0;

        String query = "select empr_070.cod_transacao " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codTransacao = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codTransacao = 0;
        }
        return codTransacao;
    }

    public int obterContaContabilEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codContabil = 0;

        String query = "select empr_070.codigo_contabil " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codContabil = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codContabil = 0;
        }

        return codContabil;
    }

    public int obterContaContabilCnpj(String cnpjCliente){

        int cgc9 = Integer.parseInt(cnpjCliente.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjCliente.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjCliente.substring(12));

        int codContabilCliente = 0;

        String query = " select pedi_010.codigo_contabil " +
                " from pedi_010 " +
                " where pedi_010.cgc_9 = " + cgc9 +
                " and pedi_010.cgc_4 = " + cgc4 +
                " and pedi_010.cgc_2 = " + cgc2;

        try {
            codContabilCliente = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codContabilCliente = 0;
        }

        return codContabilCliente;
    }

    public int obterCodHistoricoEmpTipoTitulo(int codEmpresa, int tipoTitulo){

        int codHistorico = 0;

        String query = "select empr_070.cod_historico " +
                " from empr_070 " +
                " where empr_070.codigo_empresa = " + codEmpresa +
                " and empr_070.tipo_titulo = " + tipoTitulo;

        try {
            codHistorico = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codHistorico = 0;
        }
        return codHistorico;
    }

    public int obterCodEnderessoCobrancaCnpj(String cnpjCliente){

        int cgc9 = Integer.parseInt(cnpjCliente.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjCliente.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjCliente.substring(12));

        int codEnderessoCobranca = 0;

        String query = "SELECT seq_endereco " +
                " FROM ( " +
                "    SELECT pedi_150.seq_endereco " +
                "    FROM pedi_150 " +
                "    WHERE pedi_150.cd_cli_cgc_cli9 = " + cgc9 +
                "        AND pedi_150.cd_cli_cgc_cli4 = " + cgc4 +
                "        AND pedi_150.cd_cli_cgc_cli2 = " + cgc2 +
                "        AND pedi_150.tipo_endereco = 2 " +
                "    ORDER BY pedi_150.seq_endereco ASC " +
                " ) " +
                " WHERE ROWNUM = 1";

        try {
            codEnderessoCobranca = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codEnderessoCobranca = 0;
        }
        return codEnderessoCobranca;
    }

    public int obterRepresentanteCnpj(String cnpjCliente){

        int cgc9 = Integer.parseInt(cnpjCliente.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjCliente.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjCliente.substring(12));

        int codRepresentante = 0;

        String query = "select pedi_010.cdrepres_cliente " +
                " from pedi_010 " +
                " where pedi_010.cgc_9 = " + cgc9 +
                " and pedi_010.cgc_4 = " + cgc4 +
                " and pedi_010.cgc_2 = " + cgc2;

        try {
            codRepresentante = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            codRepresentante = 0;
        }
        return codRepresentante;
    }

    public int obterNrIdentificacaoPortador(int codEmpresa, int codPortador){

        int nrIdentificacao = 0;

        String query = "SELECT NR_IDENTIFICACAO " +
                "FROM ( " +
                " SELECT NR_IDENTIFICACAO  FROM PEDI_051 " +
                " WHERE codigo_banco = " + codPortador +
                " AND CODIGO_EMPRESA = " + codEmpresa +
                " ORDER BY NR_IDENTIFICACAO ASC " +
                ") WHERE ROWNUM = 1";

        try {
            nrIdentificacao = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            nrIdentificacao = 0;
        }
        return nrIdentificacao;
    }

    public void atualizaAcumuladosComercial(int codEmpresa, String dataProrrogacao, double valorDuplicata) throws Exception {
        try {
            String queryInsert = "INSERT INTO exec_020 (codigo_empresa, data_acumulado, contas_receber) VALUES (" + codEmpresa + ", TO_DATE('" + dataProrrogacao + "','YYYY-MM-DD'), " + valorDuplicata + ")";
            jdbcTemplate.update(queryInsert);
        } catch (Exception a) {
            try {
                String queryUpdate = "UPDATE exec_020 SET contas_receber = exec_020.contas_receber + " + valorDuplicata +
                        " WHERE codigo_empresa = " + codEmpresa +
                        " AND data_acumulado = TO_DATE('" + dataProrrogacao + "','YYYY-MM-DD')";
                jdbcTemplate.update(queryUpdate);
            } catch (Exception e) {
                throw new Exception("Ocorreu um erro ao executar a atualização dos acumulados comercial na exec_020.", e);
            }
        }
    }

    public void atualizaInfoFinanceiraCliente(int tipoTitulo, String dataEmissao, String cnpjCliente, double valorDuplicata) throws Exception {

        int atualizaInformFin;
        double maiorTitulo;

        int cgc9 = Integer.parseInt(cnpjCliente.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjCliente.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjCliente.substring(12));

        String query1 = " SELECT at_inform_fin FROM cpag_040 WHERE tipo_titulo = " + tipoTitulo;
        try {
            atualizaInformFin = jdbcTemplate.queryForObject(query1, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            atualizaInformFin = 0;
        }

        String query2 = " SELECT maior_titulo FROM pedi_010 " +
                " WHERE cgc_9 = " + cgc9 +
                " AND cgc_4 = " + cgc4 +
                " AND cgc_2 = " + cgc2;
        try {
            maiorTitulo = jdbcTemplate.queryForObject(query2, Double.class);
        } catch (EmptyResultDataAccessException e) {
            maiorTitulo = 0;
        }

        if ((maiorTitulo < valorDuplicata) && (atualizaInformFin == 1)) {

            String query3 = "UPDATE pedi_010 SET maior_titulo = ?, data_maior_tit = TO_DATE('?','YYYY-MM-DD') " +
                    "WHERE cgc_9 = ? AND cgc_4 = ? AND cgc_2 = ?";
            try {
                jdbcTemplate.update(query3, valorDuplicata, dataEmissao, cgc9, cgc4, cgc2);
            } catch (Exception e) {
                throw new Exception("Ocorreu um erro ao executar a atualização do maior título na pedi_010.", e);
            }
        }
    }

    public String obterNomeCliente(String cnpjCliente){

        int cgc9 = Integer.parseInt(cnpjCliente.substring(0, 8));
        int cgc4 = Integer.parseInt(cnpjCliente.substring(8, 12));
        int cgc2 = Integer.parseInt(cnpjCliente.substring(12));

        String nomeCliente;

        String query = " select pedi_010.nome_cliente " +
                        " from pedi_010 " +
                        " where pedi_010.cgc_9 = " + cgc9 +
                        " and pedi_010.cgc_4 = " + cgc4 +
                        " and pedi_010.cgc_2 = " + cgc2;
        try {
            nomeCliente = jdbcTemplate.queryForObject(query, String.class);
        } catch (Exception e) {
            nomeCliente = "";
        }

        return nomeCliente;
    }

    public int obterPeriodoExercicioAtual(int codEmpresa, int codExercicio, String dataEmissao){

        int periodo = 0;

        String query = "select cont_510.periodo from cont_510 where cod_empresa = " + codEmpresa + " and exercicio= " + codExercicio + " AND TO_DATE(' " + dataEmissao + " ', 'yyyy-mm-dd')  between per_inicial and per_final";

        try {
            periodo = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            periodo = 0;
        }
        return periodo;
    }

    public void inserirLanctoContabilSystextilAllFields(int codEmpresa, int exercicio, int numeroLanc, int seqLanc, int origem, int lote, int periodo, String contaContabil,
                                                        int contaReduzida, int subconta, int centroCusto, String debitoCredito, int histContabil, String complHistor1,
                                                        String complHistor2, Date dataLancto, double valorLancto, int filialLancto, int contabilizado, Date dataContab,
                                                        String prgGerador, String usuario, int banco, int contaCorrente, Date dataControle, int documento, Date dataInsercao,
                                                        int executaTrigger, int cnpj9Participante, int cnpj4Participante, int cnpj2Participante, int clienteFornecedorPart,
                                                        int tipZeramentoFcont, int numDocumento, String parcelaSerie, int tipoTitulo, int seqPagamento, int codImposto,
                                                        int tipoCnpj, int projeto, int subprojeto, int servico) {

        String query = " insert into cont_600(cod_empresa, exercicio, numero_lanc, seq_lanc, origem, lote, periodo, conta_contabil, conta_reduzida, subconta, centro_custo," +
                " debito_credito, hist_contabil, compl_histor1, compl_histor2, data_lancto, valor_lancto, filial_lancto, contabilizado, data_contab, prg_gerador, usuario," +
                " banco, conta_corrente, data_controle, documento, datainsercao, executa_trigger, cnpj9_participante, cnpj4_participante, cnpj2_participante, cliente_fornecedor_part," +
                " tip_zeramento_fcont, num_documento, parcela_serie, tipo_titulo, seq_pagamento, cod_imposto, tipo_cnpj, projeto, subprojeto, servico) "
                     + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        jdbcTemplate.update(query, codEmpresa, exercicio, numeroLanc, seqLanc, origem, lote, periodo, contaContabil, contaReduzida, subconta, centroCusto, debitoCredito, histContabil, complHistor1, complHistor2, dataLancto, valorLancto, filialLancto, contabilizado, dataContab, prgGerador, usuario, banco, contaCorrente, dataControle, documento, dataInsercao, executaTrigger, cnpj9Participante, cnpj4Participante, cnpj2Participante, clienteFornecedorPart, tipZeramentoFcont, numDocumento, parcelaSerie, tipoTitulo, seqPagamento, codImposto, tipoCnpj, projeto, subprojeto, servico);
    }

    public void inserirTituloSystextil(int codigoEmpresa, int cliDupCgcCli9, int cliDupCgcCli4, int cliDupCgcCli2, int tipoTitulo, int numDuplicata, int seqDuplicatas,
                                       Date dataVencDuplic, double valorDuplicata, int situacaoDuplic, double percDescDuplic, int portadorDuplic, String serieNotaFisc,
                                       String tecidoPeca, double percentualComis, double baseCalcComis,int pedidoVenda, String nrTituloBanco, int codRepCliente,
                                       int posicaoDuplic, Date dataEmissao, Date dataTransfTit, int codHistorico, String complHistorico, int codLocal, int previsao,
                                       int moedaTitulo, double valorMoeda, Date dataProrrogacao, int contaCorrente, double percComisCrec, int codTransacao, int codigoContabil,
                                       int seqEndCobranca,int numContabil, int comissaoLancada, int cli9RespTit, int cli4RespTit, int cli2RespTit, int origemPedido,
                                       int tipoComissao, int nrCupom, int codFormaPagto, int nrMtvProrrogacao, double valorDespCobr, int nrIdentificacao, int responsavelReceb,
                                       int cdCentroCusto, int scpcSituacao){

        String query = " insert into fatu_070(codigo_empresa, cli_dup_cgc_cli9, cli_dup_cgc_cli4, cli_dup_cgc_cli2, tipo_titulo, num_duplicata, seq_duplicatas, data_venc_duplic," +
                " valor_duplicata, situacao_duplic, perc_desc_duplic, portador_duplic, serie_nota_fisc, tecido_peca, percentual_comis, base_calc_comis, pedido_venda, nr_titulo_banco," +
                " cod_rep_cliente, posicao_duplic, data_emissao, data_transf_tit, cod_historico, compl_historico, cod_local, previsao, moeda_titulo, valor_moeda, data_prorrogacao," +
                " conta_corrente, perc_comis_crec, cod_transacao, codigo_contabil, seq_end_cobranca, num_contabil, comissao_lancada, cli9resptit, cli4resptit, cli2resptit, origem_pedido," +
                " tipo_comissao, nr_cupom, cod_forma_pagto, nr_mtv_prorrogacao,valor_desp_cobr, nr_identificacao, responsavel_receb, cd_centro_custo, scpc_situacao) " +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        jdbcTemplate.update(query, codigoEmpresa,cliDupCgcCli9,cliDupCgcCli4,cliDupCgcCli2,tipoTitulo,numDuplicata,seqDuplicatas,dataVencDuplic,valorDuplicata,situacaoDuplic,percDescDuplic,portadorDuplic,serieNotaFisc,tecidoPeca,percentualComis,baseCalcComis,pedidoVenda,nrTituloBanco,codRepCliente,posicaoDuplic,dataEmissao,dataTransfTit,codHistorico,complHistorico,codLocal,previsao,moedaTitulo,valorMoeda,dataProrrogacao,contaCorrente,percComisCrec,codTransacao,codigoContabil,seqEndCobranca,numContabil,comissaoLancada,cli9RespTit,cli4RespTit,cli2RespTit,origemPedido,tipoComissao,nrCupom,codFormaPagto,nrMtvProrrogacao,valorDespCobr,nrIdentificacao,responsavelReceb, cdCentroCusto,scpcSituacao);
    }

    public void inserirLogTituloIntegracao(int codEmpresa, String cnpjCliente, int numDuplicata, Date dataEmissaoDuplicata, String situacaoIntegracao,
                                String descricaoIntegracao, String complemento, int seqDuplicata, Date dataVencDuplicata, double valorDuplicata) {

        System.out.println(descricaoIntegracao);

        int id = obterProxIdLogTitulo();
        Date dataHoraAtual = new Date();

        String query = "INSERT INTO orion_integ_nfservico_001 (id, cod_empresa, cnpj_cliente, num_duplicata, " +
                    "data_emissao_duplicata, data_hora_integracao, situacao_integracao, descr_integracao, " +
                    "complemento, seq_duplicata, data_venc_duplicata, valor_duplicata) " +
                    "VALUES (?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(query, id, codEmpresa, cnpjCliente, numDuplicata, dataEmissaoDuplicata, dataHoraAtual, situacaoIntegracao, descricaoIntegracao, complemento, seqDuplicata, dataVencDuplicata, valorDuplicata);
    }

    public int obterProxIdLogTitulo(){

        int nextId = 0;

        String query = " select max(id)+1 id from orion_integ_nfservico_001";

        try {
            nextId = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            nextId = 0;
        }
        return nextId;
    }

    public boolean existsTituloIntegradoSystextil(int codEmpresa, String cnpjCliente, int numDuplicata, int seqDuplicata){

        boolean existsTituloIntegradoSystextil;

        String query=" SELECT 1 FROM orion_integ_nfservico_001 " +
                " WHERE cod_empresa = " + codEmpresa +
                " AND cnpj_cliente = '" + cnpjCliente + "' " +
                " AND num_duplicata = " + numDuplicata +
                " AND seq_duplicata = " + seqDuplicata;
        try {
            existsTituloIntegradoSystextil = jdbcTemplate.queryForObject(query, boolean.class);
        } catch (Exception e) {
            existsTituloIntegradoSystextil = false;
        }
        return existsTituloIntegradoSystextil;
    }

    public List<TituloPagamentoIntegrado> findTitulosIntegradosSystextil(String dataEmissaoInicio, String dataEmissaoFim, String cnpjCliente, int numDuplicata){

        String query = " SELECT a.id, a.cod_empresa AS codEmpresa, a.cnpj_cliente AS cnpjCliente, pedi.fantasia_cliente AS nomeCliente, a.num_duplicata AS numTitulo, " +
                " a.data_emissao_duplicata AS dataEmissao, a.data_hora_integracao AS dataHoraIntegracao, " +
                " a.situacao_integracao AS situacaoIntegracao, a.descr_integracao AS descricaoIntegracao, " +
                " a.complemento, a.seq_duplicata AS seqTitulo, a.data_venc_duplicata AS dataVencTitulo, " +
                " a.valor_duplicata AS valorTitulo " +
                " FROM orion_integ_nfservico_001 a " +
                " JOIN pedi_010 pedi ON SUBSTR(a.cnpj_cliente, 1, 8) = pedi.cgc_9 " +
                " AND SUBSTR(a.cnpj_cliente, 9, 4) = pedi.cgc_4 " +
                " AND SUBSTR(a.cnpj_cliente, 13, 2) = pedi.cgc_2 " +
                " WHERE DATA_EMISSAO_DUPLICATA BETWEEN TO_DATE('" + dataEmissaoInicio + "', 'YYYY-MM-DD') AND TO_DATE('" + dataEmissaoFim + "', 'YYYY-MM-DD') ";

        if (!cnpjCliente.trim().equals("")) {
            query += " AND CNPJ_CLIENTE = '" + cnpjCliente + "' ";
        }

        if (numDuplicata > 0) {
            query += " AND NUM_DUPLICATA = " + numDuplicata;
        }

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TituloPagamentoIntegrado.class));
    }

    public List<ConteudoChaveAlfaNum> findAllLojas() {

        String query = " SELECT (LPAD(pe.cgc_9, 8, '0') || LPAD(pe.cgc_4, 4, '0') || LPAD(pe.cgc_2, 2, '0')) AS value, pe.fantasia_cliente AS label FROM pedi_010 pe " +
                " where pe.tipo_cliente in (4, 17, 33) " +
                " and pe.situacao_cliente = 1 ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
    }
}
