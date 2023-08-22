package br.com.live.administrativo.service;

import br.com.live.administrativo.custom.TituloPagamentoNfsFranchisingCustom;
import br.com.live.administrativo.entity.LogTituloNfsFranchisingEntity;
import br.com.live.administrativo.entity.RespostaSoapNfsFranchisingEntity;
import br.com.live.administrativo.model.*;
import br.com.live.administrativo.repository.LogTituloNfsFranchisingRepository;
import br.com.live.administrativo.repository.RespostaSoapNfsFranchisingRepository;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.FormataCNPJ;
import br.com.live.util.FormataData;
import br.com.live.util.entity.Parametros;
import br.com.live.util.repository.ParametrosRepository;
import br.com.live.util.service.ComunicacaoSoapService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.tags.Param;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

@Service
public class TituloPagamentoNfsFranchisingService {

    private static final int SITUACAO_VALIDOS = 1;
    private static final int SITUACAO_INCONSISTENCIAS = 2;
    private static final String CNPJ_PRESTADOR = "20026913000194";
    private static final String INSCRICAO_MUNICIPAL_PRESTADOR = "36198";
    private static final int QUANTIDADE_RPS = 1;
    private static final String CODIGO_MUNICIPIO_PRESTADOR = "4208906";
    private static final int NATUREZA_OPERACAO = 1;
    private static final int OPTANTE_SIMPLES_NACIONAL = 2;
    private static final int STATUS = 1;
    private static final String IDENTIFICACAO_RPS_SERIE = "1";
    private static final String IDENTIFICACAO_RPS_TIPO = "3";
    private static final int ISS_RETIDO = 2;
    private static final int ALIQUOTA = 2;
    private static final String ITEM_LISTA_SERVICO = "0302";
    private static final String CONDICAO_PAGAMENTO = "A_PRAZO";
    private static final String CODIGO_CNAE_PRESTADOR = "7740300";

    private static final int PENDENTE_ENVIO = 0;
    private static final int ERRO_PRE_ENVIO = 1;
    private static final int SITUACAO_PROCESSANDO = 2;
    private static final int PROCESSADO_COM_ERRO = 3;
    private static final int SITUACAO_GERADA = 4;

    private static final String ERRO = "ERRO";
    private static final String SUCESSO = "SUCESSO";

    private static final int INATIVO = 0;

    private static final String URL_RECEPCIONAR_LOTE_RPS = "https://e-gov.betha.com.br/e-nota-contribuinte-test-ws/recepcionarLoteRps";
    private static final String URL_CONSULTAR_SITUACAO_LOTE_RPS = "https://e-gov.betha.com.br/e-nota-contribuinte-test-ws/consultarSituacaoLoteRps";

    TituloPagamentoNfsFranchisingCustom tituloPagamentoNfsFranchisingCustom;
    NfsFranchisingMontagemXmlSoapService nfsFranchisingMontagemXmlSoapService;
    ComunicacaoSoapService comunicacaoSoapService;
    RespostaSoapNfsFranchisingRepository respostaSoapNfsFranchisingRepository;
    LogTituloNfsFranchisingRepository logTituloNfsFranchisingRepository;
    ParametrosRepository parametrosRepository;

    public TituloPagamentoNfsFranchisingService(TituloPagamentoNfsFranchisingCustom tituloPagamentoNfsFranchisingCustom, NfsFranchisingMontagemXmlSoapService nfsFranchisingMontagemXmlSoapService, ComunicacaoSoapService comunicacaoSoapService, RespostaSoapNfsFranchisingRepository respostaSoapNfsFranchisingRepository, LogTituloNfsFranchisingRepository logTituloNfsFranchisingRepository, ParametrosRepository parametrosRepository) {
        this.tituloPagamentoNfsFranchisingCustom = tituloPagamentoNfsFranchisingCustom;
        this.nfsFranchisingMontagemXmlSoapService = nfsFranchisingMontagemXmlSoapService;
        this.comunicacaoSoapService = comunicacaoSoapService;
        this.respostaSoapNfsFranchisingRepository = respostaSoapNfsFranchisingRepository;
        this.logTituloNfsFranchisingRepository = logTituloNfsFranchisingRepository;
        this.parametrosRepository = parametrosRepository;
    }

    public List<TituloPagamentoNfsFranchisingConsulta> findTitulosPagamentoNfsFranchising(String dataInicio, String dataFim, int situacao) {

        List<TituloPagamentoNfsFranchisingConsulta> titulosFiltrados = new ArrayList<>();
        List<TituloPagamentoNfsFranchisingConsulta> titulos = tituloPagamentoNfsFranchisingCustom.findAllTituloPagamentoNfsFranchising(dataInicio, dataFim);

        for (TituloPagamentoNfsFranchisingConsulta titulo : titulos) {

            titulo = gravaTituloPagamentoValidado(titulo);

            if (situacao == titulo.getSituacao()){

                int situacaoRespostaSoapNfsFranchising = validaSituacaoRespostaSoapNfsFranchising(titulo.getNumDuplicata(), titulo.getCnpjLoja());

                if (situacaoRespostaSoapNfsFranchising == PENDENTE_ENVIO) {
                    titulo.setStatus("Envio Pendete");
                    titulosFiltrados.add(titulo);
                }
                if (situacaoRespostaSoapNfsFranchising == PROCESSADO_COM_ERRO) {
                    titulo.setStatus("Enviado com Erros");
                    titulosFiltrados.add(titulo);
                }
                if (situacaoRespostaSoapNfsFranchising == ERRO_PRE_ENVIO) {
                    titulo.setStatus("Erro no Pré-Envio");
                    titulosFiltrados.add(titulo);
                }
            }
        }
        return titulosFiltrados;
    }

    public List<LogTituloNfsFranchising> findAllHistoricoTitulosPagamentoNfsFranchising(String dataEmissaoInicio, String dataEmissaoFim, String cnpj, String numDuplicata){
        return tituloPagamentoNfsFranchisingCustom.findAllHistoricoTitulosPagamentoNfsFranchising(dataEmissaoInicio, dataEmissaoFim, cnpj, numDuplicata);
    }

    public List<ConteudoChaveAlfaNum> findAllLojasHistoricoNfsFranchising(){
        return tituloPagamentoNfsFranchisingCustom.findAllLojasHistoricoNfsFranchising();
    }

    public int validaSituacaoRespostaSoapNfsFranchising(String numDuplicata, String cnpj){

        int retorno = PENDENTE_ENVIO;

        String cnpjFormatado = cnpj.replace(".", "");
        Optional<RespostaSoapNfsFranchisingEntity> respostaSoapNfsFranchisingOptional = respostaSoapNfsFranchisingRepository.findTopRespostaByDuplicataCliente(numDuplicata, cnpjFormatado);
        Optional<LogTituloNfsFranchisingEntity> logTituloNfsFranchisingOptional = logTituloNfsFranchisingRepository.findTopLogTituloByDuplicataCliente(numDuplicata, cnpjFormatado);

        if (respostaSoapNfsFranchisingOptional.isPresent()) {
            RespostaSoapNfsFranchisingEntity respostaSoapNfsFranchising = respostaSoapNfsFranchisingOptional.get();
            if (respostaSoapNfsFranchising.getCodRetorno() == null && respostaSoapNfsFranchising.getDescricaoRetorno() == null && respostaSoapNfsFranchising.getNumNfs() == null) retorno = SITUACAO_PROCESSANDO;
            if (respostaSoapNfsFranchising.getCodRetorno() != null && respostaSoapNfsFranchising.getDescricaoRetorno() != null && respostaSoapNfsFranchising.getNumNfs() == null) retorno = PROCESSADO_COM_ERRO;
            if (respostaSoapNfsFranchising.getCodRetorno() == null && respostaSoapNfsFranchising.getDescricaoRetorno() == null && respostaSoapNfsFranchising.getNumNfs() != null) retorno = SITUACAO_GERADA;
        } else {
            if (logTituloNfsFranchisingOptional.isPresent()){
                LogTituloNfsFranchisingEntity logTituloNfsFranchising = logTituloNfsFranchisingOptional.get();
                if (logTituloNfsFranchising.getStatus().equals(ERRO)) retorno = ERRO_PRE_ENVIO;
                if (logTituloNfsFranchising.getStatus().equals(SUCESSO)) retorno = SITUACAO_PROCESSANDO;
            }
        }
        return retorno;
    }

    public void integrarNfsFranchising(List<String> listTitulosSelecionados) throws Exception {

        List<TituloPagamentoNfsFranchising> tituloPagamentoNfsFranchisingList = obterNfsFranchisingValidas(listTitulosSelecionados);

        for (TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising : tituloPagamentoNfsFranchisingList){

            Long numeroLote = respostaSoapNfsFranchisingRepository.findNextId();
            tituloPagamentoNfsFranchising.setNumeroLote(numeroLote);
            tituloPagamentoNfsFranchising.setIdentificacaoRpsNumero(numeroLote);

            String xmlTitulo = nfsFranchisingMontagemXmlSoapService.getFullXml(tituloPagamentoNfsFranchising);
            String numDuplicata =  tituloPagamentoNfsFranchising.getNumDuplicata();
            String cnpjTomador = tituloPagamentoNfsFranchising.getCnpjTomador();

            System.out.println(xmlTitulo);

//            try {
//                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//                InputSource is = new InputSource(new StringReader(xmlTitulo));
//                Document doc = dBuilder.parse(is);
//
//                String loteXml = null;
//                String infRpsXml = null;
//
//                // Obtém o XML do LoteRps
//                Node loteNode = doc.getElementsByTagName("LoteRps").item(0);
//                if(loteNode != null) {
//                    loteXml = nodeToString(loteNode);
//                    loteXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + loteXml;
//                    System.out.println("Lote XML:");
//                    System.out.println(loteXml);
//                }
//
//                // Obtém o XML do InfRps
//                Node infRpsNode = doc.getElementsByTagName("InfRps").item(0);
//                if(infRpsNode != null) {
//                    infRpsXml = nodeToString(infRpsNode);
//                    infRpsXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + infRpsXml;
//                    System.out.println("InfRps XML:");
//                    System.out.println(infRpsXml);
//                }
//
//                KeyStore ks = KeyStore.getInstance("PKCS12");
//                try (FileInputStream fis = new FileInputStream("C:\\tempArquivos\\LIVE!FRANCHISINGLTDA.pfx")) {
//                    ks.load(fis, "123456".toCharArray());
//                }
//
//                String xmlOriginal = xmlTitulo;  // XML original que você quer assinar
//                String rps = infRpsXml;  // RPS que você quer assinar
//                String alias = "live! franchising ltda:20026913000194"; // Normalmente, em certificados PFX/P12, o alias é o número de série do certificado.
//                // Se não tiver certeza, pode ser necessário listar os aliases no keystore para descobrir.
//                String senha = "123456";
//                String id = "rps52"; // ID do elemento que você quer assinar
//                String nomeCertificado = "LIVE!FRANCHISINGLTDA.pfx";
//
//                String xmlAssinado = ProcessSign.execute(xmlOriginal, rps, ks, alias, senha, id, nomeCertificado);
//                System.out.println(xmlAssinado);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            try {
                String response = comunicacaoSoapService.enviaXmlParaUrlSoap(xmlTitulo, URL_RECEPCIONAR_LOTE_RPS);
                try {
                    gravarRetornoSoapNfsFranchising(numDuplicata, cnpjTomador, response);
                    gravarLogNfsFranchising(numeroLote, numDuplicata, cnpjTomador, xmlTitulo, "SUCESSO", "O título foi enviado com sucesso para o portal da prefeitura.");
                } catch (Exception e) {
                    gravarLogNfsFranchising(0, numDuplicata, cnpjTomador, xmlTitulo, "ERRO", "Não foi possível registrar os dados do retorno do portal da prefeitura. Por favor, verifique o formato do XML de resposta.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                gravarLogNfsFranchising(0, numDuplicata, cnpjTomador, xmlTitulo, "ERRO", "Não foi possível estabelecer comunicação com o portal da prefeitura. Houve uma falha na conexão com o serviço da prefeitura.");
                e.printStackTrace();
            }
        }
    }

    private static String nodeToString(Node node) throws Exception {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }

    public void atualizarRetornoTitulosNfsFranchising() throws Exception {

        Parametros paramstTitPagAtivo = parametrosRepository.findByIdParametro("INTEGRACAO_RETORNO_TITULO_FRANCHISING");
        int situacaoTitPagAtivo = paramstTitPagAtivo != null ? paramstTitPagAtivo.getValorInt() : 0;

        if (situacaoTitPagAtivo == INATIVO) {
            // System.out.println("atualizarRetornoTitulosNfsFranchising: Não será possível executar por falta de parâmetros configurados!");
            return;
        }

        List<RespostaSoapNfsFranchisingEntity> respostaSoapNfsFranchisingList = respostaSoapNfsFranchisingRepository.findProtocolosNaoProcessados();

        for (RespostaSoapNfsFranchisingEntity respostaSoapNfsFranchising : respostaSoapNfsFranchisingList){

            String protocolo = respostaSoapNfsFranchising.getProtocolo();
            long numeroLote = respostaSoapNfsFranchising.getNumeroLote();

            String xmlEnvio = nfsFranchisingMontagemXmlSoapService.montarXmlConsultaSituacaoLoteRps(CNPJ_PRESTADOR, INSCRICAO_MUNICIPAL_PRESTADOR, protocolo);

            try {
                String response = comunicacaoSoapService.enviaXmlParaUrlSoap(xmlEnvio, URL_CONSULTAR_SITUACAO_LOTE_RPS);
                try {
                    gravarRetornoSoapConsultaRpsNfsFranchising(numeroLote, response);

                    System.out.println("Protocolo " + protocolo + " foi atualizado com o retorno do portal da prefeitura.");

                } catch (Exception e) {
                    System.out.println("Não foi possível atualizar o protocolo " + protocolo + "com o retorno do portal da prefeitura.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("Não foi possível estabelecer comunicação com o portal da prefeitura. Houve uma falha na conexão com o serviço da prefeitura.");
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void gravarRetornoSoapNfsFranchising(String numDuplicata, String cnpjTomador, String xmlRetorno) throws Exception {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(xmlRetorno)));
        doc.getDocumentElement().normalize();

        Element envelope = doc.getDocumentElement();
        Element body = (Element) envelope.getElementsByTagName("env:Body").item(0);
        Element response = (Element) body.getElementsByTagName("ns3:EnviarLoteRpsEnvioResponse").item(0);
        Element resposta = (Element) response.getElementsByTagName("EnviarLoteRpsResposta").item(0);

        long numeroLote = Long.parseLong(resposta.getElementsByTagName("NumeroLote").item(0).getTextContent());
        Date dataRecebimento = FormataData.parseStringToDateFuso(resposta.getElementsByTagName("DataRecebimento").item(0).getTextContent());
        String protocolo = resposta.getElementsByTagName("Protocolo").item(0).getTextContent();

        RespostaSoapNfsFranchisingEntity respostaSoapNfsFranchisingEntity = new RespostaSoapNfsFranchisingEntity(numeroLote, numDuplicata, cnpjTomador, dataRecebimento, protocolo);
        respostaSoapNfsFranchisingRepository.save(respostaSoapNfsFranchisingEntity);
    }

    public void gravarRetornoSoapConsultaRpsNfsFranchising(long numeroLote, String xmlRetorno) throws Exception {

        saveRetornoSoapConsultaRpsNfsFranchising(numeroLote, xmlRetorno);

        Optional<RespostaSoapNfsFranchisingEntity> respostaOptional = respostaSoapNfsFranchisingRepository.findById(numeroLote);
        if (respostaOptional.isPresent()) {
            RespostaSoapNfsFranchisingEntity respostaSoapNfsFranchisingEntity = respostaOptional.get();
            String numDuplicata = respostaSoapNfsFranchisingEntity.getNumDuplicata();
            String cnpjTomador = respostaSoapNfsFranchisingEntity.getCnpjTomador();
            String codigo = respostaSoapNfsFranchisingEntity.getCodRetorno();
            String mensagem = respostaSoapNfsFranchisingEntity.getDescricaoRetorno();
            gravarLogNfsFranchising(numeroLote, numDuplicata, cnpjTomador, "", "ERRO", "Inconsistência ao gerar a NF no portal da prefeitura. Código " + codigo + ": " + mensagem);
        }
    }

    @Transactional
    public void saveRetornoSoapConsultaRpsNfsFranchising(long numeroLote, String xmlRetorno) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(xmlRetorno)));
        doc.getDocumentElement().normalize();

        Element envelope = doc.getDocumentElement();
        Element body = (Element) envelope.getElementsByTagName("env:Body").item(0);
        Element response = (Element) body.getElementsByTagName("ns2:ConsultarSituacaoLoteRpsEnvioResponse").item(0);
        Element resposta = (Element) response.getElementsByTagName("ConsultarSituacaoLoteRpsResposta").item(0);
        Element listaMensagemRetorno = (Element) resposta.getElementsByTagName("ListaMensagemRetorno").item(0);
        Element mensagemRetorno = (Element) listaMensagemRetorno.getElementsByTagName("MensagemRetorno").item(0);

        String codigo = mensagemRetorno.getElementsByTagName("Codigo").item(0).getTextContent();
        String mensagem = mensagemRetorno.getElementsByTagName("Mensagem").item(0).getTextContent();

        Optional<RespostaSoapNfsFranchisingEntity> respostaOptional = respostaSoapNfsFranchisingRepository.findById(numeroLote);

        if (respostaOptional.isPresent()) {
            RespostaSoapNfsFranchisingEntity respostaSoapNfsFranchisingEntity = respostaOptional.get();
            respostaSoapNfsFranchisingEntity.setCodRetorno(codigo);
            respostaSoapNfsFranchisingEntity.setDescricaoRetorno(mensagem);
            respostaSoapNfsFranchisingRepository.save(respostaSoapNfsFranchisingEntity);
        }
    }


    @Transactional
    public void gravarLogNfsFranchising(long numeroLote, String numDuplicata, String cnpjTomador, String conteudoXml, String status, String motivo){

        long id = logTituloNfsFranchisingRepository.findNextId();
        Date dataHoraAtual = new Date();

        LogTituloNfsFranchisingEntity logTituloNfsFranchisingEntity = new LogTituloNfsFranchisingEntity(id, numeroLote, numDuplicata, cnpjTomador, dataHoraAtual, conteudoXml, status, motivo);

        logTituloNfsFranchisingRepository.save(logTituloNfsFranchisingEntity);
    }

    public List<TituloPagamentoNfsFranchising> obterNfsFranchisingValidas(List<String> listTitulosSelecionados){

        List<TituloPagamentoNfsFranchising> tituloPagamentoNfsFranchisingList = new ArrayList<>();

        for (String titulo : listTitulosSelecionados){

            String[] partes = titulo.split("-");
            String cnpjTomador = partes[0].replace(".", "");
            String numDuplicata = partes[1];

            TituloPagamentoNfsFranchisingConsulta tituloConsulta = tituloPagamentoNfsFranchisingCustom.findTituloPagamentoNfsFranchisingCnpjDuplicata(cnpjTomador, numDuplicata);
            TomadorNfsFranchising tomadorNfsFranchising = tituloPagamentoNfsFranchisingCustom.obterDadosEnderecoTomadorNfsFranchising(cnpjTomador);

            int qtdParcela = tituloConsulta.getQtdParcelas();
            double descontoItemCalculado = calcularPercentualItensPedido(tituloConsulta.getDescontoItem1(), tituloConsulta.getDescontoItem2(), tituloConsulta.getDescontoItem3());
            double valorTotalDuplicataFranchising = aplicarDescontoFranchising(tituloConsulta.getValorTotalDuplicata(), descontoItemCalculado);
            String dataEmissao = FormataData.obterDataHoraAtualFormatada();
            double valorServicos = valorTotalDuplicataFranchising;
            double baseCalculo = valorTotalDuplicataFranchising;
            double valorLiquidoNfse = valorTotalDuplicataFranchising;
            double valorIss = arredondarParaBaixo((valorTotalDuplicataFranchising / 100) * ISS_RETIDO,2);
            String discriminacao = "ROYALTIES REFERENTE NF " + numDuplicata;
            String razaoSocialTomador = tomadorNfsFranchising.getFantasiaCliente();
            String enderecoTomador = tomadorNfsFranchising.getEnderecoCliente();
            int numeroTomador = tomadorNfsFranchising.getNumeroImovel();
            String complementoTomador = tomadorNfsFranchising.getComplemento();
            String bairroTomador = tomadorNfsFranchising.getBairro();
            int codigoMunicipioTomador = tomadorNfsFranchising.getCodCidadeIbge();
            String ufTomador = tomadorNfsFranchising.getEstado();
            String cepTomador = tomadorNfsFranchising.getCepCliente();
            String telefoneTomador = tomadorNfsFranchising.getTelefoneCliente();
            String emailTomador = tomadorNfsFranchising.getEmail();
            List<ParcelaInfoNfsFranchising> parcelasAjustadas = calcularParcelasComValoresAjustados(valorTotalDuplicataFranchising, qtdParcela, cnpjTomador, numDuplicata);

            String msgValidate = validarCampos(cnpjTomador, numDuplicata, descontoItemCalculado, valorTotalDuplicataFranchising, dataEmissao, valorServicos, baseCalculo, valorLiquidoNfse, valorIss, discriminacao, tomadorNfsFranchising, razaoSocialTomador, enderecoTomador, numeroTomador, complementoTomador, bairroTomador, codigoMunicipioTomador, ufTomador, cepTomador, telefoneTomador, emailTomador);

            if (msgValidate.isEmpty()) {
                TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising = criarTituloPagamentoNfsFranchising(numDuplicata, 0, 0, cnpjTomador, dataEmissao, valorServicos, baseCalculo, valorLiquidoNfse, valorIss, discriminacao, razaoSocialTomador, enderecoTomador, numeroTomador, complementoTomador, bairroTomador, codigoMunicipioTomador, ufTomador, cepTomador, telefoneTomador, emailTomador, qtdParcela, parcelasAjustadas);
                tituloPagamentoNfsFranchisingList.add(tituloPagamentoNfsFranchising);
            } else {
                gravarLogNfsFranchising(0, numDuplicata, cnpjTomador, "", "ERRO", msgValidate);
            }
        }
        return tituloPagamentoNfsFranchisingList;
    }

    private List<ParcelaInfoNfsFranchising> calcularParcelasComValoresAjustados(double valorTotalDuplicataFranchising, int qtdParcela, String cnpjTomador, String numDuplicata) {

        List<Double> valorParcelas = calcularValorParcelas(valorTotalDuplicataFranchising, qtdParcela);
        List<ParcelaInfoNfsFranchising> parcelaInfoNfsFranchisingsList = tituloPagamentoNfsFranchisingCustom.obterParcelasCnpjDuplicata(cnpjTomador, numDuplicata);

        for (int i = 0; i < parcelaInfoNfsFranchisingsList.size(); i++) {
            ParcelaInfoNfsFranchising parcela = parcelaInfoNfsFranchisingsList.get(i);
            parcela.setValor(valorParcelas.get(i));
            parcela.setDataVencimento("01/10/2023");
            //parcela.setDataVencimento(FormataData.converterDataFormato(parcela.getDataVencimento())); TODO: REMOVER COMENTARIO QUANDO OFICIALIZADO

        }

        return parcelaInfoNfsFranchisingsList;
    }

    public TituloPagamentoNfsFranchising criarTituloPagamentoNfsFranchising(String numDuplicata, long numeroLote, long identificacaoRpsNumero, String cnpjTomador, String dataEmissao, double valorServicos, double baseCalculo, double valorLiquidoNfse, double valorIss, String discriminacao, String razaoSocialTomador, String enderecoTomador, int numeroTomador, String complementoTomador, String bairroTomador, int codigoMunicipioTomador, String ufTomador, String cepTomador, String telefoneTomador, String emailTomador, int qtdParcela, List<ParcelaInfoNfsFranchising> parcelaInfoNfsFranchisingsList){

        TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising = new TituloPagamentoNfsFranchising();
        tituloPagamentoNfsFranchising.setNumDuplicata(numDuplicata);
        tituloPagamentoNfsFranchising.setNumeroLote(numeroLote);
        tituloPagamentoNfsFranchising.setCnpjPrestador(CNPJ_PRESTADOR);
        tituloPagamentoNfsFranchising.setInscricaoMunicipalPrestador(INSCRICAO_MUNICIPAL_PRESTADOR);
        tituloPagamentoNfsFranchising.setQuantidadeRps(QUANTIDADE_RPS);
        tituloPagamentoNfsFranchising.setCodigoMunicipioPrestador(CODIGO_MUNICIPIO_PRESTADOR);
        tituloPagamentoNfsFranchising.setIdentificacaoRpsNumero(identificacaoRpsNumero);
        tituloPagamentoNfsFranchising.setIdentificacaoRpsSerie(IDENTIFICACAO_RPS_SERIE);
        tituloPagamentoNfsFranchising.setIdentificacaoRpsTipo(IDENTIFICACAO_RPS_TIPO);
        tituloPagamentoNfsFranchising.setDataEmissao(dataEmissao);
        tituloPagamentoNfsFranchising.setNaturezaOperacao(NATUREZA_OPERACAO);
        tituloPagamentoNfsFranchising.setOptanteSimplesNacional(OPTANTE_SIMPLES_NACIONAL);
        tituloPagamentoNfsFranchising.setStatus(STATUS);
        tituloPagamentoNfsFranchising.setValorServicos(valorServicos);
        tituloPagamentoNfsFranchising.setIssRetido(ISS_RETIDO);
        tituloPagamentoNfsFranchising.setValorIss(valorIss);
        tituloPagamentoNfsFranchising.setBaseCalculo(baseCalculo);
        tituloPagamentoNfsFranchising.setAliquota(ALIQUOTA);
        tituloPagamentoNfsFranchising.setValorLiquidoNfse(valorLiquidoNfse);
        tituloPagamentoNfsFranchising.setItemListaServico(ITEM_LISTA_SERVICO);
        tituloPagamentoNfsFranchising.setCodigoCnaePrestador(CODIGO_CNAE_PRESTADOR);
        tituloPagamentoNfsFranchising.setDiscriminacao(discriminacao);
        tituloPagamentoNfsFranchising.setCnpjTomador(cnpjTomador);
        tituloPagamentoNfsFranchising.setRazaoSocialTomador(razaoSocialTomador);
        tituloPagamentoNfsFranchising.setEnderecoTomador(enderecoTomador);
        tituloPagamentoNfsFranchising.setNumeroTomador(numeroTomador);
        tituloPagamentoNfsFranchising.setComplementoTomador(complementoTomador);
        tituloPagamentoNfsFranchising.setBairroTomador(bairroTomador);
        tituloPagamentoNfsFranchising.setCodigoMunicipioTomador(codigoMunicipioTomador);
        tituloPagamentoNfsFranchising.setUfTomador(ufTomador);
        tituloPagamentoNfsFranchising.setCepTomador(cepTomador);
        tituloPagamentoNfsFranchising.setTelefoneTomador(telefoneTomador);
        tituloPagamentoNfsFranchising.setEmailTomador(emailTomador);
        tituloPagamentoNfsFranchising.setCondicaoPagamento(CONDICAO_PAGAMENTO);
        tituloPagamentoNfsFranchising.setQtdParcela(qtdParcela);
        tituloPagamentoNfsFranchising.setParcelas(parcelaInfoNfsFranchisingsList);

        return tituloPagamentoNfsFranchising;
    }

    public String validarCampos(String cnpjTomador, String numDuplicata, double descontoItemCalculado, double valorTotalDuplicataFranchising, String dataEmissao, double valorServicos, double baseCalculo, double valorLiquidoNfse, double valorIss, String discriminacao, TomadorNfsFranchising tomadorNfsFranchising, String razaoSocialTomador, String enderecoTomador, int numeroTomador, String complementoTomador, String bairroTomador, int codigoMunicipioTomador, String ufTomador, String cepTomador, String telefoneTomador, String emailTomador){

        List<String> invalidFields = new ArrayList<>();
        Map<String, Object> fieldsMap = new HashMap<>();
        fieldsMap.put("cnpjTomador", cnpjTomador);
        fieldsMap.put("numDuplicata", numDuplicata);
        fieldsMap.put("descontoItemCalculado", descontoItemCalculado);
        fieldsMap.put("valorTotalDuplicataFranchising", valorTotalDuplicataFranchising);
        fieldsMap.put("dataEmissao", dataEmissao);
        fieldsMap.put("valorServicos", valorServicos);
        fieldsMap.put("baseCalculo", baseCalculo);
        fieldsMap.put("valorLiquidoNfse", valorLiquidoNfse);
        fieldsMap.put("valorIss", valorIss);
        fieldsMap.put("discriminacao", discriminacao);
        fieldsMap.put("tomadorNfsFranchising", tomadorNfsFranchising);
        fieldsMap.put("razaoSocialTomador", razaoSocialTomador);
        fieldsMap.put("endereco", enderecoTomador);
        fieldsMap.put("numero", numeroTomador);
        fieldsMap.put("complemento", complementoTomador);
        fieldsMap.put("bairro", bairroTomador);
        fieldsMap.put("codigoMunicipioTomador", codigoMunicipioTomador);
        fieldsMap.put("ufTomador", ufTomador);
        fieldsMap.put("cepTomador", cepTomador);
        fieldsMap.put("telefoneTomador", telefoneTomador);
        fieldsMap.put("emailTomador", emailTomador);

        for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
            if (entry.getValue() == null || (entry.getValue() instanceof String && ((String) entry.getValue()).isEmpty())) {
                invalidFields.add(entry.getKey());
            }
        }

        if (invalidFields.size() > 0) {
            return "O título " + numDuplicata + " associado ao tomador de CNPJ " + FormataCNPJ.formatar(cnpjTomador) + " apresenta campos não cadastrados corretamente: " + String.join(", ", invalidFields) + ". Devido a isso, a transmissão para o portal da prefeitura não será efetuada. Por favor, faça as correções necessárias nos cadastros e tente o envio novamente.";
        } else {
            return "";
        }
    }

    public TituloPagamentoNfsFranchisingConsulta gravaTituloPagamentoValidado(TituloPagamentoNfsFranchisingConsulta titulo) {

        double descontoItemCalculado = calcularPercentualItensPedido(titulo.getDescontoItem1(), titulo.getDescontoItem2(), titulo.getDescontoItem3());
        boolean naturezasValidas = validarNatureza(titulo.getCodNaturezaConcat());


        String msgMotivo = "";

        if (descontoItemCalculado < 40) {
            msgMotivo = "Desconto é menor que 40%";
        }

        if (!naturezasValidas) {
            if (msgMotivo.length() > 0) {
                msgMotivo += " e a ";
            }
            msgMotivo += "Natureza não é Franchising";
        }

        if (descontoItemCalculado < 40 || !naturezasValidas) {
            titulo.setSituacao(SITUACAO_INCONSISTENCIAS);
        } else {
            titulo.setSituacao(SITUACAO_VALIDOS);
        }

        titulo.setMotivo(msgMotivo);
        titulo.setCodNaturezaConcat(removerRepeticoesNatureza(titulo.getCodNaturezaConcat()));
        titulo.setDescontoItemCalculado(descontoItemCalculado);
        titulo.setValorTotalDuplicataFranchising(aplicarDescontoFranchising(titulo.getValorTotalDuplicata(), descontoItemCalculado));

        return titulo;
    }

    public double calcularPercentualItensPedido(double desconto1, double desconto2, double desconto3) {

        double desc1 = 0.00;
        double desc2 = 0.00;
        double desc3 = 0.00;

        if (desconto1 > 0.00) {
            desc1 = 100.00 * (desconto1 / 100.00);
        }
        if (desconto2 > 0.00) {
            desc2 = (100.00 - desc1) * (desconto2 / 100.00);
        }
        if (desconto3 > 0.00) {
            desc3 = (100.00 - desc1 - desc2) * (desconto3 / 100.00);
        }

        double descontoCalculado = (100.00 - (100.00 - desc1 - desc2 - desc3));

        return arredondarParaBaixo(descontoCalculado, 2);
    }

    public static List<Integer> obterListaNaturezasFranchising() {
        return Arrays.asList(421, 422, 423, 424, 425, 426, 427);
    }

    public static List<Integer> agruparNaturezas (String entrada) {
        String[] partes = entrada.split("-");
        List<Integer> listaSemRepeticoes = new ArrayList<>();

        for (String s : partes) {
            int item = Integer.parseInt(s);
            if (!listaSemRepeticoes.contains(item)) {
                listaSemRepeticoes.add(item);
            }
        }

        return listaSemRepeticoes;
    }

    public static String removerRepeticoesNatureza(String entrada) {
        String[] partes = entrada.split("-");
        List<String> listaSemRepeticoes = new ArrayList<>();

        for (String s : partes) {
            if (!listaSemRepeticoes.contains(s)) {
                listaSemRepeticoes.add(s);
            }
        }

        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < listaSemRepeticoes.size(); i++) {
            resultado.append(listaSemRepeticoes.get(i));
            if (i != listaSemRepeticoes.size() - 1) {
                resultado.append(" - ");
            }
        }

        return resultado.toString();
    }

    public static boolean validarNatureza(String naturezas){

        List<Integer> naturezasFranchising = obterListaNaturezasFranchising();
        List<Integer> naturezasAgrupadas = agruparNaturezas(naturezas);

        boolean naturezasValidas = true;

        for (Integer codNatureza : naturezasAgrupadas){
            if (!naturezasFranchising.contains(codNatureza)) naturezasValidas = false;
        }

        return naturezasValidas;
    }

    public static List<Double> calcularValorParcelas(double valorTotal, int numeroParcelas) {
        List<Double> parcelas = new ArrayList<>();

        double valorParcelaBase = Math.floor((valorTotal / numeroParcelas) * 100) / 100.0;
        double somaParcelas = valorParcelaBase * numeroParcelas;
        double diferencaPrimeiraParcela = valorTotal - somaParcelas;
        double valorCalculadoDiferenca = valorParcelaBase + diferencaPrimeiraParcela;
        parcelas.add(arredondarParaBaixo(valorCalculadoDiferenca,2));

        for (int i = 1; i < numeroParcelas; i++) {
            parcelas.add(valorParcelaBase);
        }
        return parcelas;
    }

    private static double arredondarParaBaixo(double valor, int casasDecimais) {
        double multiplicador = Math.pow(10, casasDecimais);
        return Math.floor(valor * multiplicador) / multiplicador;
    }

    public static double aplicarDescontoFranchising(double valor, double desconto) {
        double valorCalculado = valor * (desconto / 100.00);
        return arredondarParaBaixo(valorCalculado, 2);
    }
}