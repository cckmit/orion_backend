package br.com.live.integracao.cigam.service;

import br.com.live.integracao.cigam.custom.IntegracaoCigamCustom;
import br.com.live.integracao.cigam.model.ListaRetornoApiMovimentos;
import br.com.live.integracao.cigam.model.ParametrosRetorno;
import br.com.live.integracao.cigam.model.RetornoApiMovimentos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RetornoMovimentosCigamService {
    public static final String API_ENDPOINT_RETORNO = "https://consulta.cigamgestor.com.br/api/Consulta/ObterCarga";
    public static final String API_TOKEN_RETORNO = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c3JfY2QiOiI2IiwiY2hhdmUiOiJMSVZFISIsImNvZGdydXBvbG9qYSI6IjAiLCJyb2xlcyI6WyI2NC01OC0xIiwiNjQtNTgtMyIsIjUyLTEiLCIxMi0wIiwiMi0xOS0zMCIsIjI4LTUzLTIiLCI2NC03Ny0wIiwiNTItMCIsIjI4LTUzLTEiLCIzLTM3IiwiMjgtMjktMzkiLCIyOC01My0wIiwiMjUtMCIsIjE4LTEiLCI3Mi0yIiwiNjQtNjItMCIsIjQzLTMiLCIzMi0wIiwiNC0xIiwiMy00LTEiLCIzLTQtMSIsIjMtNC0xIiwiMy00LTEiLCI3MC0wIiwiNDctNTAtMCIsIjMzLTIiLCIzNi0wIiwiMjgtMjktMjQiLCIxOC0yIiwiNjQtNjctMSIsIjgtNDEtMCIsIjY0LTY5LTAiLCIzMC0xIiwiNjQtNjgtMSIsIjI4LTI5LTI1IiwiMTgtMyIsIjQyLTIiLCI1MS0wIiwiOS00MCIsIjItMTktMjkiLCI2NC04MC0yIiwiNjQtODEtMCIsIjI4LTI5LTI2IiwiNDYtMiIsIjQ1LTEiLCIzNi0zNy0wIiwiNDMtMCIsIjM2LTM4LTEiLCIzMy0xIiwiMzMtMyIsIjM5LTAiLCI0My00NC0xIiwiNDctMCIsIjMxLTIiLCIzMC0wIiwiNDAtMSIsIjY0LTgwLTAiLCIzMS0xIiwiNjQtNzUtMSIsIjY0LTc2LTIiLCIzMi0xIiwiNDItMyIsIjgtNDEtMiIsIjY0LTY1LTAiLCI3LTUiLCI0Mi0xIiwiNi00MyIsIjI4LTczLTAiLCI0My0zNCIsIjY0LTgwLTEiLCIzNi0zOC0zIiwiNDMtMzYiLCI2NC01Ni0yIiwiMjEtMCIsIjQtMzgiLCI0MC0wIiwiMjctMiIsIjY0LTg2LTAiLCI0My0zNSIsIjIzLTQiLCI3LTE4IiwiNC0zIiwiMy00LTMiLCIzLTQtMyIsIjMtNC0zIiwiMy00LTMiLCI0Ny00OC0wIiwiNjQtODItMCIsIjMwLTIiLCI2NC03NC0xIiwiMjEtMjEiLCIzMS0zIiwiMzEtMCIsIjQ3LTEiLCI0Ni0wIiwiNDMtNDQtMCIsIjM2LTM4LTMyIiwiNDYtMSIsIjI3LTMiLCI2NC01OC0wIiwiNjQtNTctMSIsIjI4LTEiLCI2NC02Ni0yIiwiMzMtMCIsIjM2LTM3LTIiLCIxMy0yMC0wIiwiNjQtNzgtMiIsIjY0LTc3LTIiLCI2NC01NC0wIiwiNjQtNTctMCIsIjQzLTQ0LTMiLCI2NC02OC0yIiwiNjQtNTYtMSIsIjY0LTY3LTIiLCI0LTAiLCIzLTQtMCIsIjMtNC0wIiwiMy00LTAiLCIzLTQtMCIsIjI3LTAiLCI2NC03Ni0xIiwiNzItMCIsIjI1LTEiLCI0NS0zIiwiNjQtNzQtMCIsIjY0LTc2LTAiLCIyMS0yMiIsIjI4LTAiLCI2NC02My0wIiwiNi0zMSIsIjI3LTEiLCIyOC03My0yIiwiNjQtNTgtMiIsIjgtNDEtMSIsIjI1LTIiLCI2NC02OC0wIiwiNjQtMCIsIjQyLTAiLCI2NC04NS0wIiwiMzItMiIsIjEzLTMzIiwiNzAtMSIsIjgtNDEtMyIsIjQwLTIiLCIxMi00NSIsIjY0LTU5LTAiLCI2NC02Ny0wIiwiMjEtMjMiLCI4NC0wIiwiMjgtMjktMCIsIjIzLTAiLCI0NS0wIiwiMzYtMzgtMCIsIjY0LTU2LTAiLCI2NC04MC0zIiwiNjQtNzctMSIsIjQzLTQ0LTIiLCIyMi0wIiwiNDctMiIsIjQzLTIiLCIyOC0yOS0yOCIsIjI4LTI5LTI3IiwiNjQtNTUtMCIsIjQzLTEiLCI2NC03NC0yIiwiNjQtODEtMiIsIjM2LTM3LTMiLCIxOC0wIiwiMTItNDIiLCIyOC0zIiwiMjYtMCIsIjY0LTYwLTAiLCI3OS0wIiwiNjQtNzgtMCIsIjY0LTc1LTAiLCIyLTQxIiwiNjQtNjYtMSIsIjg0LTIiLCI0NS0yIiwiNjQtNTctMiIsIjQ3LTQ5LTAiLCIzNi0zNy0xIiwiMjUtMyIsIjI4LTczLTEiLCIxMi00NCIsIjY0LTY2LTAiLCI2NC02MS0wIiwiNTEtMiIsIjUxLTEiXSwibmJmIjoxNjgxMzIxNDkzLCJleHAiOjE5OTY5NDA2OTMsImlhdCI6MTY4MTMyMTQ5M30.mTrXS2_b_FsFtm-AFlX1yvyip_wZmf8j09sFYsSW7Q8";

    private final IntegracaoCigamCustom integracaoCigamCustom;

    public RetornoMovimentosCigamService(IntegracaoCigamCustom integracaoCigamCustom) {
        this.integracaoCigamCustom = integracaoCigamCustom;
    }

    private void gravarDadosRetornoMovimentos(String dataInicio, String dataFim) {

        try {
            System.out.println("INICIANDO GRAVAÇÃO DOS MOVIMENTOS");

            String url = API_ENDPOINT_RETORNO + "?guid=CIGAM_MOVIMENTOS_CONSOLIDADOS";

            ParametrosRetorno dado = new ParametrosRetorno(dataInicio, dataFim);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dado);

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + API_TOKEN_RETORNO);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Acesso", "live!");
            con.setDoOutput(true);

            try (OutputStream outputStream = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String responseBody = response.toString();
                RetornoApiMovimentos retornoMovimentos = objectMapper.readValue(responseBody,
                        new TypeReference<RetornoApiMovimentos>() {
                        });

                for (ListaRetornoApiMovimentos dadosRetorno : retornoMovimentos.dados) {
                    if (dadosRetorno.numorcamento <= 0) continue;
                    integracaoCigamCustom.inserirDadosRetornoMovimentos(dadosRetorno);
                }
            } else {
                System.out.println("Erro ao fazer requisição à API");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("FIM DA INTEGRAÇÃO DE MOVIMENTOS");
    }

    public void gravarMovimentosRetroativos() {
        LocalDate dataFimLocal = LocalDate.now();
        LocalDate dataInicioLocal = LocalDate.now().minusDays(40);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFim = dataFimLocal.format(dateFormat);
        String dataInicio = dataInicioLocal.format(dateFormat);

        gravarDadosRetornoMovimentos(dataInicio, dataFim);
    }

    public void gravarMovimentos() {
        LocalDate dataFimLocal = LocalDate.now();
        LocalDate dataInicioLocal = LocalDate.now().minusDays(2);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFim = dataFimLocal.format(dateFormat);
        String dataInicio = dataInicioLocal.format(dateFormat);

        gravarDadosRetornoMovimentos(dataInicio, dataFim);
    }

}
