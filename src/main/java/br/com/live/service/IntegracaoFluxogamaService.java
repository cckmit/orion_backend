package br.com.live.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.live.custom.IntegracaoFluxogamaCustom;
import br.com.live.model.*;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class IntegracaoFluxogamaService {

	public static final int CHAVE_ACESSO = 1;
	public static final String USUARIO = "integracao";
	public static final String SENHA = "prog22";
	private final IntegracaoFluxogamaCustom integracaoFluxogamaCustom;

	public IntegracaoFluxogamaService(IntegracaoFluxogamaCustom integracaoFluxogamaCustom) {
		this.integracaoFluxogamaCustom = integracaoFluxogamaCustom;
	}

	public void obterDadosFluxogamaJob() throws IOException {
		obterDadosModelo(obterToken());
	}

	public String formatarDataUltimaExecucao(LocalDateTime dataHora) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		String formattedDateTime = dataHora.format(formatter);

		return formattedDateTime;
	}

	public void obterDadosModelo(String token) throws IOException {
		int proxPagina = 1;

		while (proxPagina > 0) {
			OkHttpClient client = new OkHttpClient();
			ObjectMapper objectMapper = new ObjectMapper();

			LocalDateTime dataHoraInteg = integracaoFluxogamaCustom.obterUltimaExecucao();
			if (dataHoraInteg == null) {
				dataHoraInteg = LocalDateTime.now().minusMinutes(60);
			}
			String dataHoraFiltro = formatarDataUltimaExecucao(dataHoraInteg);

			String jsonBody = montarJsonRequestBody(proxPagina, dataHoraFiltro);
			MediaType mediaType = MediaType.parse("application/json");

			Request request = new Request.Builder().url("https://live.fluxogama.com.br/rest/api/v1/retorno/modelo")
					.header("Authorization", token).post(RequestBody.create(mediaType, jsonBody)).build();

			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();

			if (response.code() == 200) {
				RetornoAPIModelos retornoModelos = objectMapper.readValue(responseBody, new TypeReference<RetornoAPIModelos>() {
				});

				if (retornoModelos.proximaPagina > 0) {
					proxPagina = retornoModelos.proximaPagina;
				} else {
					proxPagina = 0;
				}

				if ((retornoModelos.dados != null) && (retornoModelos.dados.size() > 0)) {
					for (DadosRetornoFluxogama dadosFluxo : retornoModelos.dados) {

						String referencia = dadosFluxo.getModeloDsReferencia().substring(0,5);
						String descricaoReferencia = dadosFluxo.getUno115();
						String idColecao = dadosFluxo.getUno143();
						String unidadeMedida = dadosFluxo.getUno317();
						String linha = dadosFluxo.getUno98WsId();
						String artigoCotas = dadosFluxo.getUno108WsId();
						String artigoProduto = dadosFluxo.getUno107WsId();
						String classFiscal = dadosFluxo.getUno314WsId();
						String analistaEstrutura = dadosFluxo.getUno318Ds();
						String gradeTamanho = dadosFluxo.getUno102WsId();
						String codContabil = null;
						String compradoFabricado = null;
						String pilar = dadosFluxo.getUno99();
						String idSegemento = dadosFluxo.getUno101();

						if (dadosFluxo.getUno315Ds() != null) {
							compradoFabricado = dadosFluxo.getUno315Ds().substring(0,1);
						}
						if (dadosFluxo.getUno319Ds() != null) {
							codContabil = dadosFluxo.getUno319Ds().substring(0,4);
						}

						integracaoFluxogamaCustom.atualizarInformacoesCapaReferencia(referencia.toUpperCase(), descricaoReferencia, idColecao, unidadeMedida,
								linha, artigoProduto, artigoCotas, classFiscal, compradoFabricado, analistaEstrutura, gradeTamanho, codContabil);

						if (pilar != null) {
							int idPilar = integracaoFluxogamaCustom.obterCodigoPilarSystextil(pilar);
							integracaoFluxogamaCustom.atualizarCamposBasi400(referencia,9,idPilar);
						}
						if (idSegemento != null) {
							integracaoFluxogamaCustom.atualizarCamposBasi400(referencia, 10, Integer.parseInt(idSegemento));
						}
						if (artigoProduto != null) {
							integracaoFluxogamaCustom.atualizarCamposBasi400(referencia, 805, Integer.parseInt(artigoProduto));
						}

						if (gradeTamanho != null) {

							// LISTA TODOS OS TAMANHOS
							List<String> tamanhos = integracaoFluxogamaCustom.obterListaTamanhosPorGrade(Integer.parseInt(gradeTamanho));
							for (String tamanho : tamanhos){
								integracaoFluxogamaCustom.inserirTamanhosReferencia(referencia, tamanho);


								// LISTA TODAS AS CORES
								if (dadosFluxo.cores != null) {
									List<String> coresExclusao = new ArrayList<>();

									for (DadosRetornoFluxogamaCor dadosCor : dadosFluxo.cores) {
										integracaoFluxogamaCustom.atualizarCoresReferencia(referencia,tamanho,dadosCor.getCorId(),classFiscal,artigoCotas,codContabil);
										coresExclusao.add(dadosCor.getCorId());
									}

									// EXCLUIR AS CORES QUE NÃO ESTÃO NO FLUXO
									if (coresExclusao.size() > 0) {
										integracaoFluxogamaCustom.excluirCoresDivergentes(referencia, tamanho, String.join(",", coresExclusao));
									}
								}
							}

							// EXCLUIR TAMANHOS DIVERGENTES
							List<String> tamanhosDivergentes = integracaoFluxogamaCustom.obterListaTamanhosDivergentes(referencia, gradeTamanho);
							for (String tamDivergente : tamanhosDivergentes) {
								integracaoFluxogamaCustom.excluirTamanhosDivergentes(referencia, tamDivergente);
							}
						}

						// LISTA TODAS AS CATEGORIAS
						if (dadosFluxo.categorias != null) {
							for (DadosRetornoFluxogamaCategoria dadosCategoria : dadosFluxo.categorias) {
								if (dadosCategoria.getCategoria1() != null) {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,2,dadosCategoria.getCategoria1());
								} else {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,2,"");
								}

								if (dadosCategoria.getCategoria2() != null) {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,3,dadosCategoria.getCategoria2());
								} else {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,3,"");
								}

								if (dadosCategoria.getCategoria3() != null) {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,4,dadosCategoria.getCategoria3());
								} else {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,4,"");
								}

								if (dadosCategoria.getCategoria4() != null) {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,5,dadosCategoria.getCategoria4());
								} else {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,5,"");
								}

								if (dadosCategoria.getCategoria5() != null) {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,6,dadosCategoria.getCategoria5());
								} else {
									integracaoFluxogamaCustom.atualizarCategoriasReferencia(referencia,6,"");
								}
							}
						}
					}
				} else {
					System.out.println("Lista Vázia");
				}
			} else {
				break;
			}
		}
	}

	public static String obterToken() throws IOException {
		String token = "";

		ParametrosToken paramsToken = new ParametrosToken(CHAVE_ACESSO, USUARIO, SENHA);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(paramsToken);

		URL obj = new URL("https://live.fluxogama.com.br/rest/api/v1/autenticacao");
		HttpURLConnection connApi = (HttpURLConnection) obj.openConnection();
		connApi.setRequestMethod("POST");
		connApi.setRequestProperty("Content-Type", "application/json; utf-8");
		connApi.setDoOutput(true);

		try (OutputStream outputStream = connApi.getOutputStream()) {
			byte[] input = json.getBytes(StandardCharsets.UTF_8);
			outputStream.write(input, 0, input.length);
		}

		int responseCode = connApi.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader bufferReturn = new BufferedReader(new InputStreamReader(connApi.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = bufferReturn.readLine()) != null) {
				response.append(inputLine);
			}
			bufferReturn.close();

			String responseBody = response.toString();
			RetornoTokenFluxogama retornoToken = objectMapper.readValue(responseBody,
					new TypeReference<RetornoTokenFluxogama>() {
					});

			Token objRetorno = retornoToken.getRetorno();

			token = "Bearer " + objRetorno.getToken();
		}
		return token;
	}

	public static String montarJsonRequestBody(int pagina, String dataHoraFiltro) {

		JSONObject requestBody = new JSONObject();

		// Definindo o campo "pagina"
		requestBody.put("pagina", pagina);

		// Definindo a seção "campos"
		JSONArray camposArray = new JSONArray();
		camposArray.put("modelo.ws_id");
		camposArray.put("modelo.ds_referencia");
		camposArray.put("uno.115");
		camposArray.put("colecao.ws_id");
		camposArray.put("uno.260.ws_id");
		camposArray.put("uno.98.ds");
		camposArray.put("uno.98.ws_id");
		camposArray.put("uno.107.ds");
		camposArray.put("uno.107.ws_id");
		camposArray.put("uno.108.ds");
		camposArray.put("uno.108.ws_id");
		camposArray.put("uno.314.ws_id");
		camposArray.put("uno.314.ds");
		camposArray.put("uno.315.ds");
		camposArray.put("uno.315.ws_id");
		camposArray.put("uno.318.ds");
		camposArray.put("uno.319.ds");
		camposArray.put("uno.319.ws_id");
		camposArray.put("uno.102.ds");
		camposArray.put("uno.102.ws_id");
		camposArray.put("uno.317.ws_id");
		camposArray.put("uno.99.ds");
		camposArray.put("uno.101.ws_id");

		JSONObject camposObject = new JSONObject();
		camposObject.put("campos", camposArray);

		// Definindo a seção "listas"
		JSONArray listasArray = new JSONArray();

		JSONObject tabelaObject = new JSONObject();
		tabelaObject.put("nome", "tabela.48");

		JSONArray tabelaCamposArray = new JSONArray();
		tabelaCamposArray.put("tabela.48.433.ds");
		tabelaCamposArray.put("tabela.48.434.ds");
		tabelaCamposArray.put("tabela.48.435.ds");
		tabelaCamposArray.put("tabela.48.436.ds");
		tabelaCamposArray.put("tabela.48.437.ds");

		tabelaObject.put("campos", tabelaCamposArray);
		listasArray.put(tabelaObject);

		JSONObject corObject = new JSONObject();
		corObject.put("nome", "cor");

		JSONArray corCamposArray = new JSONArray();
		corCamposArray.put("cor.ds");
		corCamposArray.put("cor.ws_id");

		corObject.put("campos", corCamposArray);
		listasArray.put(corObject);

		camposObject.put("listas", listasArray);
		requestBody.put("campos", camposObject);

		// Definindo a seção "filtros"
		JSONArray filtrosArray = new JSONArray();

		JSONObject filtroObject = new JSONObject();
		filtroObject.put("campo", "alteracao.modelo.ds_referencia");
		filtroObject.put("operador", ">=");
		filtroObject.put("valor", "{HOJE-360}");

		filtrosArray.put(filtroObject);

		//JSONObject filtro2Object = new JSONObject();
		//filtro2Object.put("campo", "modelo.ds_referencia");
		//filtro2Object.put("operador", "=");
		//filtro2Object.put("valor", "BC704 teste integração");

		//filtrosArray.put(filtro2Object);

		requestBody.put("filtros", filtrosArray);

		// Definindo a seção "filtro-alteracoes"
		JSONObject filtroAlteracoesObject = new JSONObject();

		JSONArray filtroAlteracoesCamposArray = new JSONArray();
		filtroAlteracoesCamposArray.put("alteracao.modelo.ds_referencia");
		filtroAlteracoesCamposArray.put("alteracao.uno.98");
		filtroAlteracoesCamposArray.put("alteracao.uno.99");
		filtroAlteracoesCamposArray.put("alteracao.uno.101");
		filtroAlteracoesCamposArray.put("alteracao.uno.102");
		filtroAlteracoesCamposArray.put("alteracao.uno.107");
		filtroAlteracoesCamposArray.put("alteracao.uno.108");
		filtroAlteracoesCamposArray.put("alteracao.uno.115");
		filtroAlteracoesCamposArray.put("alteracao.uno.314");
		filtroAlteracoesCamposArray.put("alteracao.uno.315");
		filtroAlteracoesCamposArray.put("alteracao.uno.319");
		filtroAlteracoesCamposArray.put("alteracao.tabela.48");

		filtroAlteracoesObject.put("campos", filtroAlteracoesCamposArray);
		filtroAlteracoesObject.put("operador", ">=");

		// TODO - TROCAR PELO PARAMETRO
		filtroAlteracoesObject.put("valor", dataHoraFiltro);

		requestBody.put("filtro-alteracoes", filtroAlteracoesObject);

		// Converte o objeto JSON para uma string formatada
		String formattedRequestBody = requestBody.toString(4);

		return formattedRequestBody;
	}
}
