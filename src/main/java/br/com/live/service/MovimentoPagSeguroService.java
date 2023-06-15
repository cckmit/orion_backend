package br.com.live.service;

import br.com.live.custom.MovimentoPagSeguroCustom;
import br.com.live.entity.MovimentoPagSeguroEntity;
import br.com.live.entity.Parametros;
import br.com.live.repository.MovimentoPagSeguroRepository;
import br.com.live.repository.ParametrosRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MovimentoPagSeguroService {

    public static final int INATIVO = 0;

    MovimentoPagSeguroRepository movimentoPagSeguroRepository;
    MovimentoPagSeguroCustom movimentoPagSeguroCustom;
    ParametrosRepository parametrosRepository;

    public MovimentoPagSeguroService(MovimentoPagSeguroRepository movimentoPagSeguroRepository, MovimentoPagSeguroCustom movimentoPagSeguroCustom, ParametrosRepository parametrosRepository) {
        this.movimentoPagSeguroRepository = movimentoPagSeguroRepository;
        this.movimentoPagSeguroCustom = movimentoPagSeguroCustom;
        this.parametrosRepository = parametrosRepository;
    }

    public void GravarMovimentosPagSeguroJob() {

        String authHeaderValue = getAuthHeaderValue();

        if (authHeaderValue.equals("")) {
            System.out.println("GravarMovimentosPagSeguroJob: Não será possível executar por falta de parâmetros configurados!");
            return;
        }

        try {
            List<String> listaDatas = obterListaDatas();

            for (String dataMovimento : listaDatas) {

                for (int tipoMovimento = 1; tipoMovimento <= 3; tipoMovimento++) { // 1 - Transacional  // 2 - Financeiro // 3 - Antecipação

                    int elements = 10;
                    int currentPage = 0;
                    int totalPages = 0;

                    while (currentPage <= totalPages) {

                        String apiUrl = "https://edi.api.pagseguro.com.br/edi/v1/2.00/movimentos?dataMovimento=" + dataMovimento + "&pageNumber=" + currentPage + "&pageSize=" + elements + "&tipoMovimento=" + tipoMovimento;

                        String jsonResponse = getJsonResponse(apiUrl, authHeaderValue);

                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        totalPages = getTotalPages(jsonObject);
                        elements = getElements(jsonObject);
                        currentPage = getCurrentPage(jsonObject);
                        JSONArray detalhesArray = getDetalhesArray(jsonObject);

                        saveMovimentosPagSeguro(detalhesArray);

                        currentPage++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonResponse(String apiUrl, String authHeaderValue) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authHeaderValue);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    private String getAuthHeaderValue() {

        Parametros paramsAtivo = parametrosRepository.findByIdParametro("INTEGRACAO_MOVIMENTOS_PAGSEGURO_ATIVO");
        Parametros paramsUser = parametrosRepository.findByIdParametro("INTEGRACAO_MOVIMENTOS_PAGSEGURO_USER");
        Parametros paramsPassword = parametrosRepository.findByIdParametro("INTEGRACAO_MOVIMENTOS_PAGSEGURO_PASSWORD");

        int situacaoIntegracao = paramsAtivo != null ? paramsAtivo.getValorInt() : 0;
        String username = paramsUser != null ? paramsUser.getValorStr() : null;
        String password = paramsPassword != null ? paramsPassword.getValorStr() : null;

        if (situacaoIntegracao == INATIVO || username == null || password == null) return "";

        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedAuth;
    }

    private int getElements(JSONObject jsonObject) {
        return jsonObject.getJSONObject("pagination").getInt("elements");
    }

    private int getTotalPages(JSONObject jsonObject) {
        return jsonObject.getJSONObject("pagination").getInt("totalPages");
    }

    private int getCurrentPage(JSONObject jsonObject) {
        return jsonObject.getJSONObject("pagination").getInt("page");
    }

    private JSONArray getDetalhesArray(JSONObject jsonObject) {
        return jsonObject.getJSONArray("detalhes");
    }

    private void saveMovimentosPagSeguro(JSONArray detalhesArray) {
        for (int i = 0; i < detalhesArray.length(); i++) {
            JSONObject detalheObj = detalhesArray.getJSONObject(i);
            String estabelecimento = detalheObj.getString("estabelecimento");
            String codigoTransacao = detalheObj.getString("codigo_transacao");

            if (movimentoPagSeguroCustom.existsRelacionamentoEstabelecimentoPagSeguroLoja(estabelecimento) && !movimentoPagSeguroCustom.existsMovimentoPagSeguroImportado(estabelecimento, codigoTransacao)) {
                MovimentoPagSeguroEntity movimentoPagSeguro = createMovimentoPagSeguro(detalheObj, estabelecimento, codigoTransacao);
                movimentoPagSeguroRepository.save(movimentoPagSeguro);
            }
        }
    }

    private MovimentoPagSeguroEntity createMovimentoPagSeguro(JSONObject detalheObj, String estabelecimento, String codigoTransacao) {

        MovimentoPagSeguroEntity movimentoPagSeguro = new MovimentoPagSeguroEntity();
        movimentoPagSeguro.setId(movimentoPagSeguroRepository.findNextId());
        movimentoPagSeguro.setHoraInicialTransacao(detalheObj.getString("horaInicialTransacao"));
        movimentoPagSeguro.setDataVendaAjuste(detalheObj.getString("dataVendaAjuste"));
        movimentoPagSeguro.setHoraVendaAjuste(detalheObj.getString("horaVendaAjuste"));
        movimentoPagSeguro.setTipoEvento(detalheObj.getString("tipoEvento"));
        movimentoPagSeguro.setTipoTransacao(detalheObj.getString("tipoTransacao"));
        movimentoPagSeguro.setNsu(detalheObj.getString("nsu"));
        movimentoPagSeguro.setFiller1(detalheObj.getString("filler1"));
        movimentoPagSeguro.setCardBin(detalheObj.getString("cardBin"));
        movimentoPagSeguro.setCardHolder(detalheObj.getString("cardHolder"));
        movimentoPagSeguro.setAutorizacao(detalheObj.getString("autorizacao"));
        movimentoPagSeguro.setCv(detalheObj.getString("cv"));
        movimentoPagSeguro.setNumeroSerieLeitor(detalheObj.getString("numeroSerieLeitor"));
        movimentoPagSeguro.setUsoInternoPS(detalheObj.getString("usoInternoPS"));
        movimentoPagSeguro.setTipoRegistro(detalheObj.getString("tipo_registro"));
        movimentoPagSeguro.setEstabelecimento(estabelecimento);
        movimentoPagSeguro.setDataInicialTransacao(detalheObj.getString("data_inicial_transacao"));
        movimentoPagSeguro.setCodigoTransacao(codigoTransacao);
        movimentoPagSeguro.setCodigoVenda(detalheObj.getString("codigo_venda"));
        movimentoPagSeguro.setValorTotalTransacao(detalheObj.getDouble("valor_total_transacao"));
        movimentoPagSeguro.setValorParcela(detalheObj.getDouble("valor_parcela"));
        movimentoPagSeguro.setPagamentoPrazo(detalheObj.getString("pagamento_prazo"));
        movimentoPagSeguro.setPlano(detalheObj.getString("plano"));
        movimentoPagSeguro.setParcela(detalheObj.getString("parcela"));
        movimentoPagSeguro.setQuantidadeParcela(detalheObj.getString("quantidade_parcela"));
        movimentoPagSeguro.setDataMovimentacao(detalheObj.getString("data_movimentacao"));
        movimentoPagSeguro.setTaxaParcelaComprador(detalheObj.getDouble("taxa_parcela_comprador"));
        movimentoPagSeguro.setTarifaBoletoCompra(detalheObj.getDouble("tarifa_boleto_compra"));
        movimentoPagSeguro.setValorOriginalTransacao(detalheObj.getDouble("valor_original_transacao"));
        movimentoPagSeguro.setTaxaParcelaVendedor(detalheObj.getDouble("taxa_parcela_vendedor"));
        movimentoPagSeguro.setTaxaIntermediacao(detalheObj.getDouble("taxa_intermediacao"));
        movimentoPagSeguro.setTarifaIntermediacao(detalheObj.getDouble("tarifa_intermediacao"));
        movimentoPagSeguro.setTarifaBoletoVendedor(detalheObj.getDouble("tarifa_boleto_vendedor"));
        movimentoPagSeguro.setTaxaRepAplicacao(detalheObj.getDouble("taxa_rep_aplicacao"));
        movimentoPagSeguro.setValorLiquidoTransacao(detalheObj.getDouble("valor_liquido_transacao"));
        movimentoPagSeguro.setTaxaAntecipacao(detalheObj.getInt("taxa_antecipacao"));
        movimentoPagSeguro.setValorLiquidoAntecipacao(detalheObj.getDouble("valor_liquido_antecipacao"));
        movimentoPagSeguro.setStatusPagamento(detalheObj.getString("status_pagamento"));
        movimentoPagSeguro.setIdentificadorRevenda(detalheObj.getString("identificador_revenda"));
        movimentoPagSeguro.setMeioPagamento(detalheObj.getString("meio_pagamento"));
        movimentoPagSeguro.setInstituicaoFinanceira(detalheObj.getString("instituicao_financeira"));
        movimentoPagSeguro.setCanalEntrada(detalheObj.getString("canal_entrada"));
        movimentoPagSeguro.setLeitor(detalheObj.getString("leitor"));
        movimentoPagSeguro.setMeioCaptura(detalheObj.getString("meio_captura"));
        movimentoPagSeguro.setCodBanco(detalheObj.getString("cod_banco"));
        movimentoPagSeguro.setBancoAgencia(detalheObj.getString("banco_agencia"));
        movimentoPagSeguro.setContaBanco(detalheObj.getString("conta_banco"));
        movimentoPagSeguro.setNumLogico(detalheObj.getString("num_logico"));

        return movimentoPagSeguro;
    }

    public static List<String> obterListaDatas() {

        String dataInicial = "2023-06-10"; // TODO: OBTER DA REGRA A ULTIMA DATA DA MOVIMENTAÇÃO IMPORTADA.

        List<String> listaDatas = new ArrayList<>();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataInicio = formatter.parse(dataInicial);
            Date dataAtual = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataInicio);

            while (!calendar.getTime().after(dataAtual)) {
                String dataFormatada = formatter.format(calendar.getTime());
                listaDatas.add(dataFormatada);
                calendar.add(Calendar.DATE, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDatas;
    }
}