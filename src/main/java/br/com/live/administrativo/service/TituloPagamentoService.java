package br.com.live.administrativo.service;

import br.com.live.administrativo.custom.TituloPagamentoCustom;
import br.com.live.administrativo.custom.ContabilidadeCustom;
import br.com.live.util.entity.Notificacao;
import br.com.live.util.entity.Parametros;
import br.com.live.util.repository.ParametrosRepository;
import br.com.live.util.service.EmailService;
import br.com.live.util.service.NotificacaoService;
import br.com.live.administrativo.model.AtributoRemessaPortadorEmpresa;
import br.com.live.administrativo.model.ParcelaTituloPagamento;
import br.com.live.administrativo.model.TituloPagamento;
import br.com.live.util.FormataCNPJ;
import br.com.live.util.FormataData;
import br.com.live.util.FormataString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TituloPagamentoService {

    public static final int INATIVO = 0;
    private TituloPagamentoCustom tituloPagamentoCustom;
    private ContabilidadeCustom contabilidadeCustom;
    private ContabilidadeService contabilidadeService;
    private NotificacaoService notificacaoService;
    private EmailService email;
    private ParametrosRepository parametrosRepository;

    public TituloPagamentoService(TituloPagamentoCustom tituloPagamentoCustom, ContabilidadeCustom contabilidadeCustom, ContabilidadeService contabilidadeService, NotificacaoService notificacaoService, EmailService email, ParametrosRepository parametrosRepository) {
        this.tituloPagamentoCustom = tituloPagamentoCustom;
        this.contabilidadeCustom = contabilidadeCustom;
        this.contabilidadeService = contabilidadeService;
        this.notificacaoService = notificacaoService;
        this.email = email;
        this.parametrosRepository = parametrosRepository;
    }

    public String obterBodyTitulosNFSePrefeitura(){

        LocalDate dataAtual = LocalDate.now();
        String dataInicial = dataAtual.minusDays(1).toString();
        String dataFinal = dataAtual.toString();
        String cnpj = "20026913000194";

        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:e=\"http://www.betha.com.br/e-nota-contribuinte-ws\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <e:ConsultarNfseEnvio>\n"
                + "         <Prestador>\n"
                + "            <Cnpj>" + cnpj + "</Cnpj>\n"
                + "         </Prestador>\n"
                + "         <PeriodoEmissao>\n"
                + "            <DataInicial> " + dataInicial + "</DataInicial>\n"
                + "            <DataFinal> " + dataFinal + " </DataFinal>\n"
                + "         </PeriodoEmissao>\n"
                + "      </e:ConsultarNfseEnvio>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        return body;
    }

    public void gerarTitulosNFSePrefeituraJob(){

        Parametros paramstTitPagAtivo = parametrosRepository.findByIdParametro("INTEGRACAO_TITULO_PAGAMENTO_ATIVO");
        int situacaoTitPagAtivo = paramstTitPagAtivo != null ? paramstTitPagAtivo.getValorInt() : 0;

        if (situacaoTitPagAtivo == INATIVO) {
            System.out.println("GerarTitulosNFSePrefeituraJob: Não será possível executar por falta de parâmetros configurados!");
            return;
        }

        if (!tituloPagamentoCustom.validaUsuarioAtivoIntegracao()){
            System.out.println("Não possui usuários configurados na tabela orion_005 / tipo 3. A integração não será executada!");
            return;
        }

        String url = "http://e-gov.betha.com.br/e-nota-contribuinte-ws/consultarNfseV110";
        String body = obterBodyTitulosNFSePrefeitura();

        try {
            URL endpoint = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            connection.setDoOutput(true);
            connection.getOutputStream().write(body.getBytes("UTF-8"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            String xmlContent = response.toString();

            List<TituloPagamento> complNfseList = gerarListaObjetoTitulosNFSePrefeitura(xmlContent);

            gravarTitulosNFSe(complNfseList);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Falha na requisição. Não será integrado as NF.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TituloPagamento> gerarListaObjetoTitulosNFSePrefeitura(String xml) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));

            NodeList complNfseList = document.getElementsByTagName("ComplNfse");

            List<TituloPagamento> nfseList = new ArrayList<>();

            for (int i = 0; i < complNfseList.getLength(); i++) {

                Node complNfseNode = complNfseList.item(i);

                if (complNfseNode.getNodeType() == Node.ELEMENT_NODE) {

                    TituloPagamento nfseValues = new TituloPagamento();
                    List<ParcelaTituloPagamento> nfseParcelaValuesList = new ArrayList<>();

                    Element complNfseElement = (Element) complNfseNode;
                    Element nfseElement = (Element) complNfseElement.getElementsByTagName("Nfse").item(0);
                    Element infNfseElement = (Element) nfseElement.getElementsByTagName("InfNfse").item(0);
                    Element infNfseNumeroElement = (Element) infNfseElement.getElementsByTagName("Numero").item(0);
                    Element infNfseDataEmissaoElement = (Element) infNfseElement.getElementsByTagName("DataEmissao").item(0);
                    Element servicoElement = (Element) infNfseElement.getElementsByTagName("Servico").item(0);
                    Element valoresElement = (Element) servicoElement.getElementsByTagName("Valores").item(0);
                    Element valorServicosElement = (Element) valoresElement.getElementsByTagName("ValorServicos").item(0);
                    Element discriminacaoElement = (Element) servicoElement.getElementsByTagName("Discriminacao").item(0);
                    Element tomadorServicoElement = (Element) infNfseElement.getElementsByTagName("TomadorServico").item(0);
                    Element identificacaoTomadorElement = (Element) tomadorServicoElement.getElementsByTagName("IdentificacaoTomador").item(0);
                    Element cpfCnpjTomadorElement = (Element) identificacaoTomadorElement.getElementsByTagName("CpfCnpj").item(0);
                    Element cnpjTomadorElement = (Element) cpfCnpjTomadorElement.getElementsByTagName("Cnpj").item(0);
                    Element condicaoPagamentoElement = (Element) infNfseElement.getElementsByTagName("CondicaoPagamento").item(0);
                    Element nfseCancelamentoElement = (Element) complNfseElement.getElementsByTagName("NfseCancelamento").item(0);
                    Element nfseConfirmacaontoElement = (Element) nfseCancelamentoElement.getElementsByTagName("Confirmacao").item(0);
                    Element nfseInfConfirmacaoCancelamentoElement = (Element) nfseConfirmacaontoElement.getElementsByTagName("InfConfirmacaoCancelamento").item(0);
                    Element nfseInfConfirmacaoCancelamentoSucessoElement = (Element) nfseInfConfirmacaoCancelamentoElement.getElementsByTagName("Sucesso").item(0);

                    String valorNumero = infNfseNumeroElement.getTextContent();
                    String valorDataEmissao = infNfseDataEmissaoElement.getTextContent();
                    LocalDateTime dateTime = LocalDateTime.parse(valorDataEmissao, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                    String valorServico = valorServicosElement.getTextContent();
                    String valorDiscriminacao = discriminacaoElement.getTextContent();
                    int posicaoInicioDescricao = valorDiscriminacao.indexOf("Descricao=") + 10;
                    int posicaoFimDescricao = valorDiscriminacao.indexOf("]", posicaoInicioDescricao);
                    String valorDescricao = valorDiscriminacao.substring(posicaoInicioDescricao, posicaoFimDescricao);
                    String valorCnpjTomador = cnpjTomadorElement.getTextContent();
                    String notaCancelada = nfseInfConfirmacaoCancelamentoSucessoElement.getTextContent();

                    nfseValues.setNrNota(Integer.parseInt(valorNumero));
                    nfseValues.setDataEmissao(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    nfseValues.setValorServico(Double.parseDouble(valorServico));
                    nfseValues.setDiscriminacao(valorDescricao);
                    nfseValues.setCnpjTomador(valorCnpjTomador);
                    nfseValues.setNotaCancelada(Boolean.parseBoolean(notaCancelada));

                    NodeList parcelasList = condicaoPagamentoElement.getElementsByTagName("Parcelas");

                    for (int ia = 0; ia < parcelasList.getLength(); ia++) {

                        ParcelaTituloPagamento nfseParcelaValues = new ParcelaTituloPagamento();

                        Element parcelaElement = (Element) parcelasList.item(ia);
                        Element numeroParcelaElement = (Element) parcelaElement.getElementsByTagName("Parcela").item(0);
                        String valorNrParcela = numeroParcelaElement.getTextContent();

                        Element dataVencimentoParcelaElement = (Element) parcelaElement.getElementsByTagName("DataVencimento").item(0);
                        if (dataVencimentoParcelaElement != null) {
                            String valorDataVencimentoParcela = dataVencimentoParcelaElement.getTextContent();
                            LocalDateTime dateTimeParcela = LocalDateTime.parse(valorDataVencimentoParcela, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                            nfseParcelaValues.setDataVencimento(dateTimeParcela.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        } else {
                            nfseParcelaValues.setDataVencimento(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }

                        Element valorParcelaElement = (Element) parcelaElement.getElementsByTagName("Valor").item(0);
                        String valorParcela = valorParcelaElement.getTextContent();

                        nfseParcelaValues.setNrParcela(Integer.parseInt(valorNrParcela));
                        nfseParcelaValues.setValorParcela(Double.parseDouble(valorParcela));

                        nfseParcelaValuesList.add(nfseParcelaValues);
                    }
                    nfseValues.setParcelasTitulo(nfseParcelaValuesList);
                    nfseList.add(nfseValues);
                }
            }

            return nfseList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void gravarTitulosNFSe(List<TituloPagamento> complNfseList) throws Exception {

        Parametros paramstSelIndAtivo = parametrosRepository.findByIdParametro("INTEGRACAO_SELECAO_PAGAMENTO_ATIVO");
        int situacaoSelIndAtivo = paramstSelIndAtivo != null ? paramstSelIndAtivo.getValorInt() : 0;

        if (situacaoSelIndAtivo == INATIVO){
            System.out.println("GravarEnvioCobrancaTituloSystextil: Não será possível executar por falta de parâmetros configurados!");
        }

        for (TituloPagamento dadosTituloPagamento : complNfseList) {

            List<ParcelaTituloPagamento> parcelaTituloPagamentoList = dadosTituloPagamento.getParcelasTitulo();

            for (ParcelaTituloPagamento dadosParcelaTituloPagamento : parcelaTituloPagamentoList){

                int codEmpresa = 6;
                int tipoTitulo = 2;
                int origem = 3;

                String cnpjLive = "20026913000194";
                String programa = "Orion";
                String usuario = "Integ. Orion";

                Date dataAtual = new Date();
                int nrNota = dadosTituloPagamento.getNrNota();
                String discriminacao = dadosTituloPagamento.getDiscriminacao();
                String cnpjTomador = dadosTituloPagamento.getCnpjTomador();
                boolean notaCancelada = dadosTituloPagamento.isNotaCancelada();
                int nrParcela = dadosParcelaTituloPagamento.getNrParcela();
                double valorParcela = dadosParcelaTituloPagamento.getValorParcela();
                String dataEmissao = dadosTituloPagamento.getDataEmissao();
                String dataVencimento = dadosParcelaTituloPagamento.getDataVencimento();
                String dataEmissaoFormat = FormataData.converterDataFormato(dataEmissao);
                String dataVencimentoFormat = FormataData.converterDataFormato(dataVencimento);

                int codLocal = tituloPagamentoCustom.obterCodLocalEmpTipoTitulo(codEmpresa, tipoTitulo);
                int respReceb = tituloPagamentoCustom.obterRespRecebEmpTipoTitulo(codEmpresa, tipoTitulo);
                int codEnderessoCobranca = tituloPagamentoCustom.obterCodEnderessoCobrancaCnpj(cnpjTomador);
                int codMoeda = tituloPagamentoCustom.obterCodMoedaEmpTipoTitulo(codEmpresa, tipoTitulo);
                int codRepresentante = tituloPagamentoCustom.obterRepresentanteCnpj(cnpjTomador);
                int codPortador = tituloPagamentoCustom.obterPortadorDuplicataEmpTipoTitulo(codEmpresa, tipoTitulo);
                int nrIdentificacao = tituloPagamentoCustom.obterNrIdentificacaoPortador(codEmpresa, codPortador);
                int codTransacao = tituloPagamentoCustom.obterTransacaoEmpTipoTitulo(codEmpresa, tipoTitulo);
                int contaContabilRdz = tituloPagamentoCustom.obterContaContabilEmpTipoTitulo(codEmpresa, tipoTitulo);
                int contaContabilClienteRdz = tituloPagamentoCustom.obterContaContabilCnpj(cnpjTomador);
                String contaContabil = contabilidadeCustom.findContaContabByContaRed(contaContabilRdz);
                String contaContabilCliente = contabilidadeCustom.findContaContabByContaRed(contaContabilClienteRdz);
                int codHistorico = tituloPagamentoCustom.obterCodHistoricoEmpTipoTitulo(codEmpresa, tipoTitulo);
                int codExercicio = tituloPagamentoCustom.obterCodExercicioConfiguradoDataEmissao(codEmpresa, dataEmissao);
                int codPeriodo = tituloPagamentoCustom.obterPeriodoExercicioAtual(codEmpresa, codExercicio, dataEmissao);
                String nomeCliente = tituloPagamentoCustom.obterNomeCliente(cnpjTomador);
                String complemento = String.format("%06d", nrNota) + "/" + String.format("%02d", nrParcela) + " - " + nomeCliente;

                String msgErroGravacaoTitulo = validaGravacaoTituloSystextil(codEmpresa, tipoTitulo, nrNota, cnpjTomador, nrParcela, valorParcela, dataEmissaoFormat, dataVencimentoFormat, respReceb, codEnderessoCobranca, codRepresentante, codPortador, codTransacao, contaContabilRdz, contaContabilClienteRdz, codHistorico, codExercicio, codPeriodo, cnpjLive, notaCancelada, complemento);

                if (msgErroGravacaoTitulo.equals("")) {
                    gravarTituloSystextil(codEmpresa, tipoTitulo, origem, programa, usuario, dataAtual, nrNota, discriminacao, cnpjTomador, nrParcela, valorParcela, dataEmissao,
                            dataEmissaoFormat, dataVencimentoFormat, codLocal, respReceb, codEnderessoCobranca, codMoeda, codRepresentante, codPortador, nrIdentificacao, codTransacao,
                            contaContabilRdz, contaContabilClienteRdz, contaContabil, contaContabilCliente, codHistorico, codExercicio, codPeriodo, complemento);

                    if (situacaoSelIndAtivo != INATIVO) {

                        String msgErroEnvioCobrancaTitulo = validaEnvioCobrancaTituloSystextil(nrNota, nrParcela, codEmpresa, nrIdentificacao, cnpjTomador, codPortador, notaCancelada);

                        if (msgErroEnvioCobrancaTitulo.equals("")) {
                            gravarEnvioCobrancaTituloSystextil(codEmpresa, codPortador, nrIdentificacao, cnpjTomador, tipoTitulo, nrNota, nrParcela);
                        }
                    }
                }
            }
        }
    }

    public String validaGravacaoTituloSystextil(int codEmpresa, int tipoTitulo, int nrNota, String cnpjTomador, int nrParcela,
                                                double valorParcela, String dataEmissaoFormat, String dataVencimentoFormat, int respReceb, int codEnderessoCobranca,
                                                int codRepresentante, int codPortador, int codTransacao, int contaContabilRdz, int contaContabilClienteRdz, int codHistorico,
                                                int codExercicio, int codPeriodo, String cnpjLive, boolean notaCancelada, String complemento){

        String msgErro = "Não foi possível integrar a Nota: " + nrNota + " / Série: " + nrParcela + " / Cliente: " + cnpjTomador + ". ";
        String msgEmail = "";

        boolean existsTituloIntegradoSystextil = tituloPagamentoCustom.existsTituloIntegradoSystextil(codEmpresa, cnpjTomador, nrNota, nrParcela);
        boolean existsTituloPagamentoSystextil = tituloPagamentoCustom.existsTituloCadastradoSystextil(codEmpresa, tipoTitulo, nrNota, nrParcela, cnpjLive, cnpjTomador);
        String nomeCliente = tituloPagamentoCustom.obterNomeCliente(cnpjTomador);

        if (existsTituloIntegradoSystextil) {
            msgErro += "NF já integrada com o Systêxtil. Realize o detalhamento da mesma para verificar sua situação de integração.";
            return msgErro;
        }

        if (nomeCliente.equals("")){
            msgEmail = "Cnpj não está cadastrado no systêxtil.";
            msgErro += msgEmail;
            tituloPagamentoCustom.inserirLogTituloIntegracao(codEmpresa, cnpjTomador, nrNota, FormataData.parseStringToDate(dataEmissaoFormat), "Erro", msgErro, complemento, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat), valorParcela);
            enviarEmailNotaIntegrada("Erro", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        if (existsTituloPagamentoSystextil && !notaCancelada) {
            msgEmail = "Já possui um título a receber cadastrado para este responsável com mesmo tipo de titulo e empresa no Systêxtil.";
            msgErro += msgEmail;
            tituloPagamentoCustom.inserirLogTituloIntegracao(codEmpresa, cnpjTomador, nrNota, FormataData.parseStringToDate(dataEmissaoFormat), "Erro", msgErro, complemento, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat), valorParcela);
            enviarEmailNotaIntegrada("Erro", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        if (existsTituloPagamentoSystextil && notaCancelada) {
            msgEmail = "A NF está cancelada no portal da prefeitura e a mesma se encontra cadastrada para este responsável com o mesmo tipo de título e empresa no Systêxtil.";
            msgErro += msgEmail;
            tituloPagamentoCustom.inserirLogTituloIntegracao(codEmpresa, cnpjTomador, nrNota, FormataData.parseStringToDate(dataEmissaoFormat), "Erro", msgErro, complemento, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat), valorParcela);
            enviarEmailNotaIntegrada("Cancelamento", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        if (notaCancelada){
            msgEmail = "Situação da NF está cancelada no portal da prefeitura.";
            msgErro += msgEmail;
            tituloPagamentoCustom.inserirLogTituloIntegracao(codEmpresa, cnpjTomador, nrNota, FormataData.parseStringToDate(dataEmissaoFormat), "Erro", msgErro, complemento, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat), valorParcela);
            enviarEmailNotaIntegrada("Cancelamento", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        String msgErroCampos = validarCamposIntegracao(nrNota, cnpjTomador, nrParcela, valorParcela, dataEmissaoFormat, dataVencimentoFormat, respReceb, codEnderessoCobranca, codRepresentante, codPortador, codTransacao, contaContabilRdz, contaContabilClienteRdz, codHistorico, codExercicio, codPeriodo);

        if (!msgErroCampos.equals("")) {
            msgEmail = msgErroCampos;
            msgErro += msgErroCampos;
            tituloPagamentoCustom.inserirLogTituloIntegracao(codEmpresa, cnpjTomador, nrNota, FormataData.parseStringToDate(dataEmissaoFormat), "Erro", msgErro, complemento, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat), valorParcela);
            enviarEmailNotaIntegrada("Erro", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }
        return "";
    }

    public String validaEnvioCobrancaTituloSystextil(int nrNota, int nrParcela, int codEmpresa, int nrIdentificacao, String cnpjTomador, int codPortador, boolean notaCancelada){

        String nomeCliente = tituloPagamentoCustom.obterNomeCliente(cnpjTomador);

        String msgErro = "A NF foi integrada no Systêxtil, mas não foi possível fazer o envio automático para a cobrança: " + nrNota + " / Série: " + nrParcela + " / Cliente: " + cnpjTomador + ". ";
        String msgEmail = "";

        if (codPortador != 341){
            msgEmail = "Portador diferentes de ITAU - 341!";
            msgErro += msgEmail;
            return msgErro;
        }

        if (notaCancelada){
            msgEmail = "Situação da NF está cancelada no portal da prefeitura!";
            msgErro += msgEmail;
            return msgErro;
        }

        if (!tituloPagamentoCustom.verificaPortadorConsideraCobranca(codPortador).equals("S")){
            msgEmail = "Portador " + codPortador + " não está cadastrado para cobrança escritural!";
            msgErro += msgEmail;
            enviarEmailNotaIntegrada("ErroSelecao", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        AtributoRemessaPortadorEmpresa atributoRemessaPortadorEmpresa = null;
        atributoRemessaPortadorEmpresa = tituloPagamentoCustom.obterAtributoRemessaPortadorEmpresa(codEmpresa, codPortador, nrIdentificacao);

        if (atributoRemessaPortadorEmpresa == null) {
            msgEmail = "Portador " + codPortador + " não possui atributo de remessa cadastrado na empresa " + codEmpresa + ", ou não possui agência que considera cobrança!";
            msgErro += msgEmail;
            enviarEmailNotaIntegrada("ErroSelecao", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        int calcNrTituloBanco = atributoRemessaPortadorEmpresa.getCalcNrTituloBanco();

        if (calcNrTituloBanco == 0){
            msgEmail = "Identificação do portador não está configurado para calcular a sequência do Título!";
            msgErro += msgEmail;
            enviarEmailNotaIntegrada("ErroSelecao", nrNota, nrParcela, cnpjTomador, nomeCliente, msgEmail);
            return msgErro;
        }

        return "";
    }

    @Transactional
    public void gravarTituloSystextil(int codEmpresa, int tipoTitulo, int origem, String programa, String usuario, Date dataAtual, int nrNota, String discriminacao, String cnpjTomador,
                                      int nrParcela, double valorParcela, String dataEmissao, String dataEmissaoFormat, String dataVencimentoFormat, int codLocal, int respReceb,
                                      int codEnderessoCobranca, int codMoeda, int codRepresentante, int codPortador, int nrIdentificacao, int codTransacao, int contaContabilRdz,
                                      int contaContabilClienteRdz, String contaContabil, String contaContabilCliente, int codHistorico, int codExercicio, int codPeriodo,
                                      String complemento) throws Exception {

        int cgc9Tomador = Integer.parseInt(cnpjTomador.substring(0, 8));
        int cgc4Tomador = Integer.parseInt(cnpjTomador.substring(8, 12));
        int cgc2Tomador = Integer.parseInt(cnpjTomador.substring(12));
        String nomeCliente = tituloPagamentoCustom.obterNomeCliente(cnpjTomador);

        // Gera num lancamneto e retorna o novo código.
        int numLancamentoContabil = contabilidadeCustom.findNextNumLancto(codEmpresa, codExercicio);

        // Gera num lancamneto e retorna o novo código.
        int lote = contabilidadeService.gerarNumLote(codEmpresa, codExercicio, origem, FormataData.converterDataFormato(dataEmissao), 2);

        tituloPagamentoCustom.atualizaAcumuladosComercial(codEmpresa, dataEmissao, valorParcela);
        tituloPagamentoCustom.atualizaInfoFinanceiraCliente(tipoTitulo, dataEmissao, cnpjTomador, valorParcela);

        tituloPagamentoCustom.inserirLanctoContabilSystextilAllFields(codEmpresa, codExercicio, numLancamentoContabil, 1, origem, lote, codPeriodo,
                contaContabilCliente, contaContabilClienteRdz, 0, 0, "D", codHistorico, complemento, null,
                FormataData.parseStringToDate(dataEmissaoFormat), valorParcela, respReceb, 0, null, programa, usuario, 0, 0,
                FormataData.parseStringToDate(dataEmissaoFormat), 0, dataAtual, 0, cgc9Tomador, cgc4Tomador, cgc2Tomador, 1,
                0, nrNota, Integer.toString(nrParcela), tipoTitulo, 0, 0, 0, 0, 0, 0);

        tituloPagamentoCustom.inserirLanctoContabilSystextilAllFields(codEmpresa, codExercicio, numLancamentoContabil, 2, origem, lote, codPeriodo,
                contaContabil, contaContabilRdz, 0, 0, "C", codHistorico, complemento, null,
                FormataData.parseStringToDate(dataEmissaoFormat), valorParcela, respReceb, 0, null, programa, usuario, 0, 0,
                FormataData.parseStringToDate(dataEmissaoFormat), 0, dataAtual, 0, cgc9Tomador, cgc4Tomador, cgc2Tomador, 1,
                0, nrNota, Integer.toString(nrParcela), tipoTitulo, 0, 0, 0, 0, 0, 0);

        tituloPagamentoCustom.inserirTituloSystextil(codEmpresa, cgc9Tomador, cgc4Tomador, cgc2Tomador, tipoTitulo, nrNota, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat),
                valorParcela, 0, 0, codPortador, " ", "0", 0, valorParcela,0,"0", codRepresentante, 0,
                FormataData.parseStringToDate(dataEmissaoFormat), null, codHistorico, discriminacao, codLocal, 0, codMoeda, valorParcela, FormataData.parseStringToDate(dataVencimentoFormat),
                0, 30, codTransacao, contaContabilRdz, codEnderessoCobranca, numLancamentoContabil, 1, cgc9Tomador, cgc4Tomador, cgc2Tomador,0, 0, 0, 0, 0, 0,
                nrIdentificacao, respReceb, 0, 0);

        tituloPagamentoCustom.inserirLogTituloIntegracao(codEmpresa, cnpjTomador, nrNota, FormataData.parseStringToDate(dataEmissaoFormat), "Sucesso", "", complemento, nrParcela, FormataData.parseStringToDate(dataVencimentoFormat), valorParcela);

        // enviarEmailNotaIntegrada("Sucesso", nrNota, nrParcela, cnpjTomador, nomeCliente, "");
    }

    @Transactional
    public void gravarEnvioCobrancaTituloSystextil(int codEmpresa, int codPortador, int nrIdentificacao, String cnpjTomador, int tipoTitulo, int nrNota, int nrParcela) {

        int cgc9Tomador = Integer.parseInt(cnpjTomador.substring(0, 8));
        int cgc4Tomador = Integer.parseInt(cnpjTomador.substring(8, 12));
        int cgc2Tomador = Integer.parseInt(cnpjTomador.substring(12));

        AtributoRemessaPortadorEmpresa atributoRemessaPortadorEmpresa = tituloPagamentoCustom.obterAtributoRemessaPortadorEmpresa(codEmpresa, codPortador, nrIdentificacao);
        int numeroContrato = atributoRemessaPortadorEmpresa.getNumeroContrato();
        int codAgencia = atributoRemessaPortadorEmpresa.getCodAgencia();
        int contaCorrente = atributoRemessaPortadorEmpresa.getContaCorrente();
        int codCarteira = atributoRemessaPortadorEmpresa.getCodCarteira();
        int seqNrTituloBanco = tituloPagamentoCustom.obterProxSeqNrTituloBanco(codEmpresa, codPortador, nrIdentificacao);

        tituloPagamentoCustom.atualizaSeqNrTituloBancoEmpresa(codEmpresa, codPortador, seqNrTituloBanco, numeroContrato, codAgencia);
        tituloPagamentoCustom.atualizaContaCorrenteTituloSystextil(codEmpresa, contaCorrente, codCarteira, tipoTitulo, nrNota, nrParcela, cgc9Tomador, cgc4Tomador, cgc2Tomador, seqNrTituloBanco);
    }

    public String validarCamposIntegracao(int nrNota, String cnpjTomador, int nrParcela, double valorParcela, String dataEmissao, String dataVencimento, int respReceb,
                                int codEnderessoCobranca, int codRepresentante, int codPortador, int codTransacao, int contaContabilRdz,
                                int contaContabilClienteRdz, int codHistorico, int codExercicio, int codPeriodo) {

        StringBuilder camposFaltantes = new StringBuilder();

        if (nrNota <= 0) {
            camposFaltantes.append("nrNota, ");
        }
        if (cnpjTomador == null || cnpjTomador.isEmpty()) {
            camposFaltantes.append("cnpjTomador, ");
        }
        if (nrParcela <= 0) {
            camposFaltantes.append("nrParcela, ");
        }
        if (valorParcela <= 0) {
            camposFaltantes.append("valorParcela, ");
        }
        if (dataEmissao == null || dataEmissao.isEmpty()) {
            camposFaltantes.append("dataEmissao, ");
        }
        if (dataVencimento == null || dataVencimento.isEmpty()) {
            camposFaltantes.append("dataVencimento, ");
        }
        if (respReceb <= 0) {
            camposFaltantes.append("respReceb, ");
        }
        if (codEnderessoCobranca <= 0) {
            camposFaltantes.append("codEnderessoCobranca, ");
        }
        if (codRepresentante <= 0) {
            camposFaltantes.append("codRepresentante, ");
        }
        if (codPortador <= 0) {
            camposFaltantes.append("codPortador, ");
        }
        if (codTransacao <= 0) {
            camposFaltantes.append("codTransacao, ");
        }
        if (contaContabilRdz <= 0) {
            camposFaltantes.append("contaContabilRdz, ");
        }
        if (contaContabilClienteRdz <= 0) {
            camposFaltantes.append("contaContabilClienteRdz, ");
        }
        if (codHistorico <= 0) {
            camposFaltantes.append("codHistorico, ");
        }
        if (codExercicio <= 0) {
            camposFaltantes.append("codExercicio, ");
        }
        if (codPeriodo <= 0) {
            camposFaltantes.append("codPeriodo, ");
        }

        if (camposFaltantes.length() > 0) {
            camposFaltantes.insert(0, "Campos faltantes: ");
            camposFaltantes.deleteCharAt(camposFaltantes.length() - 2);
            String msgErro = "Não foi possível integrar a Nota: " + nrNota + " / Série: " + nrParcela + " / Cliente: " + cnpjTomador + ". " + camposFaltantes.toString() + ".";
            return msgErro;
        } else {
            return "";
        }
    }

    // tipoEnvioEmail = "Sucesso"
    // tipoEnvioEmail = "Erro"
    // tipoEnvioEmail = "Cancelamento"
    // tipoEnvioEmail = "ErroSelecao"
    public void enviarEmailNotaIntegrada(String tipoEnvioEmail, int nrNota, int nrSerie, String cnpj, String nomeCliente, String msgErro) {

        String assunto= "";
        String corpoEmail= "";
        cnpj = FormataCNPJ.formatar(cnpj);

        if (tipoEnvioEmail.equals("Sucesso")){
            assunto = "Integração de Título a Receber - Título integrado com Sucesso";
            corpoEmail = "<h4>" + FormataString.convertUtf8("Informamos que o Título a Receber foi integrado com sucesso no Systêxtil!") + "</h4>" +
                    "<p>" + FormataString.convertUtf8("Detalhes da integração:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Nota: " + nrNota) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Série: " + nrSerie) + " </li>" +
                    "  <li>" + FormataString.convertUtf8("Loja: " + cnpj + " - " + nomeCliente) + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Em caso de dúvidas, estamos à disposição.") + "</p>";
        }

        if (tipoEnvioEmail.equals("Erro")){
            assunto = "Integração de Título a Receber - Título integrado com Erro";
            corpoEmail = "<h4>" + FormataString.convertUtf8("Identificamos um problema na integração automática do Título a Receber no Systêxtil. Por esse motivo, não foi possível cadastrar o título automaticamente.") + "</h4>" +
                    "<p>" + FormataString.convertUtf8("Ações necessárias:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Será necessário realizar o cadastro manual do Título a Receber no Systêxtil.") + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Detalhes da integração:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Nota: " + nrNota) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Série: " + nrSerie) + " </li>" +
                    "  <li>" + FormataString.convertUtf8("Loja: " + cnpj + " - " + nomeCliente) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Motivo: " + msgErro) + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Em caso de dúvidas, estamos à disposição.") + "</p>";
        }

        if (tipoEnvioEmail.equals("Cancelamento")) {

            assunto = "Integração de Título a Receber - Cancelamento de Nota";
            corpoEmail = "<h4>" + FormataString.convertUtf8("Informamos que a Nota fiscal foi cancelada no portal da Prefeitura.") + "</h4>" +
                    "<p>" + FormataString.convertUtf8("Ações necessárias:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Será necessário realizar o cancelamento manual do título relacionado à nota no Systêxtil.") + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Detalhes da nota cancelada:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Nota: " + nrNota) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Série: " + nrSerie) + " </li>" +
                    "  <li>" + FormataString.convertUtf8("Loja: " + cnpj + " - " + nomeCliente) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Motivo: " + msgErro) + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Em caso de dúvidas, estamos à disposição.") + "</p>";
        }

        if (tipoEnvioEmail.equals("ErroSelecao")) {
            assunto = "Integração de Título a Receber - Erro na Seleção Individual de Titulo";
            corpoEmail = "<h4>" + FormataString.convertUtf8("Informamos que não foi possível fazer a selecção individual do título.") + "</h4>" +
                    "<p>" + FormataString.convertUtf8("Ações necessárias:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Será necessário realizar a seleção manual do título no Systêxtil.") + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Detalhes da nota:") + "</p>" +
                    "<ul>" +
                    "  <li>" + FormataString.convertUtf8("Nota: " + nrNota) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Série: " + nrSerie) + " </li>" +
                    "  <li>" + FormataString.convertUtf8("Loja: " + cnpj + " - " + nomeCliente) + "</li>" +
                    "  <li>" + FormataString.convertUtf8("Motivo: " + msgErro) + "</li>" +
                    "</ul>" +
                    "<p>" + FormataString.convertUtf8("Em caso de dúvidas, estamos à disposição.") + "</p>";
        }

        List<String> emails = notificacaoService.findEmailByTipoNotificacao(Notificacao.INTEGRACAO_TITULOS_RECEBER_NFS);

        for (String destinatario : emails) {
            if (destinatario != null) {

                corpoEmail = FormataString.convertUtf8Html(corpoEmail);

                email.enviar(assunto, corpoEmail, destinatario);
            }
        }
    }
}