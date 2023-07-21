package br.com.live.integracao.cigam.service;

import br.com.live.integracao.cigam.custom.IntegracaoCigamCustom;
import br.com.live.integracao.cigam.model.ConsultaNotasNaoEnviadas;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class EnvioXmlCigamService {

    private static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
    public static final String API_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxZGM0MWVlZC0wYWQ2LTQzN2ItYWYwMy05YzdlY2JjNmE3NTQiLCJzdWIiOiJBbGV4IFNhbmRybyBGYWd1bmRlcyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJBTEVYIiwidXNyX2NkIjoiNiIsImlkZW50Zl9yZXRhIjoiUkxJViIsInJvbGVzIjoiNjQtNTgtMSw2NC01OC0zLDUyLTEsMTItMCwyLTE5LTMwLDI4LTUzLTIsNjQtNzctMCw1Mi0wLDI4LTUzLTEsMy0zNywyOC0yOS0zOSwyOC01My0wLDI1LTAsMTgtMSw3Mi0yLDY0LTYyLTAsNDMtMywzMi0wLDQtMSwzLTQtMSwzLTQtMSwzLTQtMSwzLTQtMSw3MC0wLDQ3LTUwLTAsMzMtMiwzNi0wLDI4LTI5LTI0LDE4LTIsNjQtNjctMSw4LTQxLTAsNjQtNjktMCwzMC0xLDY0LTY4LTEsMjgtMjktMjUsMTgtMyw0Mi0yLDUxLTAsOS00MCwyLTE5LTI5LDY0LTgwLTIsNjQtODEtMCwyOC0yOS0yNiw0Ni0yLDQ1LTEsMzYtMzctMCw0My0wLDM2LTM4LTEsMzMtMSwzMy0zLDM5LTAsNDMtNDQtMSw0Ny0wLDMxLTIsMzAtMCw0MC0xLDY0LTgwLTAsMzEtMSw2NC03NS0xLDY0LTc2LTIsMzItMSw0Mi0zLDgtNDEtMiw2NC02NS0wLDctNSw0Mi0xLDYtNDMsMjgtNzMtMCw0My0zNCw2NC04MC0xLDM2LTM4LTMsNDMtMzYsNjQtNTYtMiwyMS0wLDQtMzgsNDAtMCwyNy0yLDY0LTg2LTAsNDMtMzUsMjMtNCw3LTE4LDQtMywzLTQtMywzLTQtMywzLTQtMywzLTQtMyw0Ny00OC0wLDY0LTgyLTAsMzAtMiw2NC03NC0xLDIxLTIxLDMxLTMsMzEtMCw0Ny0xLDQ2LTAsNDMtNDQtMCwzNi0zOC0zMiw0Ni0xLDI3LTMsNjQtNTgtMCw2NC01Ny0xLDI4LTEsNjQtNjYtMiwzMy0wLDM2LTM3LTIsMTMtMjAtMCw2NC03OC0yLDY0LTc3LTIsNjQtNTQtMCw2NC01Ny0wLDQzLTQ0LTMsNjQtNjgtMiw2NC01Ni0xLDY0LTY3LTIsNC0wLDMtNC0wLDMtNC0wLDMtNC0wLDMtNC0wLDI3LTAsNjQtNzYtMSw3Mi0wLDI1LTEsNDUtMyw2NC03NC0wLDY0LTc2LTAsMjEtMjIsMjgtMCw2NC02My0wLDYtMzEsMjctMSwyOC03My0yLDY0LTU4LTIsOC00MS0xLDI1LTIsNjQtNjgtMCw2NC0wLDQyLTAsNjQtODUtMCwzMi0yLDEzLTMzLDcwLTEsOC00MS0zLDQwLTIsMTItNDUsNjQtNTktMCw2NC02Ny0wLDIxLTIzLDg0LTAsMjgtMjktMCwyMy0wLDQ1LTAsMzYtMzgtMCw2NC01Ni0wLDY0LTgwLTMsNjQtNzctMSw0My00NC0yLDIyLTAsNDctMiw0My0yLDI4LTI5LTI4LDI4LTI5LTI3LDY0LTU1LTAsNDMtMSw2NC03NC0yLDY0LTgxLTIsMzYtMzctMywxOC0wLDEyLTQyLDI4LTMsMjYtMCw2NC02MC0wLDc5LTAsNjQtNzgtMCw2NC03NS0wLDItNDEsNjQtNjYtMSw4NC0yLDQ1LTIsNjQtNTctMiw0Ny00OS0wLDM2LTM3LTEsMjUtMywyOC03My0xLDEyLTQ0LDY0LTY2LTAsNjQtNjEtMCw1MS0yLDUxLTEiLCJleHAiOjE5OTY4NTIyOTEsImlzcyI6Imdlc3Rvci5hcGkuYXV0ZW50aWNhY2FvIiwiYXVkIjoiZ2VzdG9yLndlYiJ9.BC-6bhn-qYYwRYHWvnCOd0SWRAxc9Iqm_m02N_MmouI";
    public static final String API_ENDPOINT = "https://api.live.cigamgestor.com.br/Gestor.api.nfe/api/Nfe/envio";

    private final IntegracaoCigamCustom integracaoCigamCustom;

    public EnvioXmlCigamService(IntegracaoCigamCustom integracaoCigamCustom) {
        this.integracaoCigamCustom = integracaoCigamCustom;
    }

    public void enviarNotas() throws IOException {
        List<ConsultaNotasNaoEnviadas> listNotas = integracaoCigamCustom.findNotasParaEnviar();

        for (ConsultaNotasNaoEnviadas nota : listNotas) {
            System.out.println("INICIO DA INTEGRAÇÃO XML'S");

            URL url = new URL(API_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            byte[] xmlBytes = nota.arquivoXml;
            String fileName = nota.numeroDanfe;
            String fileContentType = "application/xml";
            String formData = "--" + BOUNDARY + "\r\n" +
                    "Content-Disposition: form-data; name=\"arquivo\"; filename=\"" + fileName + ".xml\"\r\n" +
                    "Content-Type: " + fileContentType + "\r\n\r\n";

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(formData.getBytes());

            InputStream inputStream = new ByteArrayInputStream(xmlBytes);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.write("\r\n".getBytes());

            String endFormData = "--" + BOUNDARY + "--\r\n";
            OutputStream outputStreamNew = connection.getOutputStream();
            outputStreamNew.write(endFormData.getBytes());

            int statusCode = connection.getResponseCode();
            String mensagem = connection.getResponseMessage();

            connection.disconnect();

            integracaoCigamCustom.inserirNotasEnviadas(nota.notaFiscal, nota.serieFiscal, nota.cnpj9, nota.cnpj4, nota.cnpj2);
        }
        System.out.println("FIM DA INTEGRAÇÃO XML'S");
    }

}
