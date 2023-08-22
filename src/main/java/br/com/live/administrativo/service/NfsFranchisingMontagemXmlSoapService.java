package br.com.live.administrativo.service;

import br.com.live.administrativo.model.ParcelaInfoNfsFranchising;
import br.com.live.administrativo.model.TituloPagamentoNfsFranchising;
import br.com.live.util.service.CertificadoDigitalService;
import org.springframework.stereotype.Service;

@Service
public class NfsFranchisingMontagemXmlSoapService {

    CertificadoDigitalService certificadoDigitalService;

    public NfsFranchisingMontagemXmlSoapService(CertificadoDigitalService certificadoDigitalService) {
        this.certificadoDigitalService = certificadoDigitalService;
    }

    public String createEnvelope() {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:e=\"http://www.betha.com.br/e-nota-contribuinte-ws\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\">";
    }

    public String endEnvelope() {
        return "</soapenv:Envelope>";
    }

    public String createHeader() {
        return "<soapenv:Header/>";
    }

    public String createBody(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) throws Exception {
        return "<soapenv:Body>" + createEnviarLoteRpsEnvio(tituloPagamentoNfsFranchising) + "</soapenv:Body>";
    }

    public String createEnviarLoteRpsEnvio(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) throws Exception {
        return "<e:EnviarLoteRpsEnvio>" + createLoteRps(tituloPagamentoNfsFranchising) + "</e:EnviarLoteRpsEnvio>";
    }

    public String createLoteRps(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) throws Exception {
        return "<LoteRps Id=\"lote" +  tituloPagamentoNfsFranchising.getNumeroLote() + "\">" +
                "<NumeroLote>" + tituloPagamentoNfsFranchising.getNumeroLote() + "</NumeroLote>" +
                "<Cnpj>" + tituloPagamentoNfsFranchising.getCnpjPrestador() + "</Cnpj>" +
                "<InscricaoMunicipal>" + tituloPagamentoNfsFranchising.getInscricaoMunicipalPrestador() + "</InscricaoMunicipal>" +
                "<QuantidadeRps>" + tituloPagamentoNfsFranchising.getQuantidadeRps() + "</QuantidadeRps>" +
                "<CodigoMunicipio>" + tituloPagamentoNfsFranchising.getCodigoMunicipioPrestador() + "</CodigoMunicipio>" +
                createListaRps(tituloPagamentoNfsFranchising) +
                "</LoteRps>";
    }

    public String createListaRps(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) throws Exception {
        return "<ListaRps>" + createRps(tituloPagamentoNfsFranchising) + "</ListaRps>";
    }

    public String createRps(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) throws Exception {
        return "<Rps>" + createInfRps(tituloPagamentoNfsFranchising) + certificadoDigitalService.assinarXmlNfs() + "</Rps>";
    }

    public String createInfRps(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<InfRps Id=\"rps" + tituloPagamentoNfsFranchising.getIdentificacaoRpsNumero() + "\">" +
                createIdentificacaoRps(tituloPagamentoNfsFranchising) +
                "<DataEmissao>" + tituloPagamentoNfsFranchising.getDataEmissao() + "</DataEmissao>" +
                "<NaturezaOperacao>" + tituloPagamentoNfsFranchising.getNaturezaOperacao() + "</NaturezaOperacao>" +
                "<OptanteSimplesNacional>" + tituloPagamentoNfsFranchising.getOptanteSimplesNacional() + "</OptanteSimplesNacional>" +
                "<Status>" + tituloPagamentoNfsFranchising.getStatus() + "</Status>" +
                createServico(tituloPagamentoNfsFranchising) +
                createPrestador(tituloPagamentoNfsFranchising) +
                createTomador(tituloPagamentoNfsFranchising) +
                createCondicaoPagamento(tituloPagamentoNfsFranchising) +
                "</InfRps>";
    }

    public String createIdentificacaoRps(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<IdentificacaoRps>" +
                "<Numero>" + tituloPagamentoNfsFranchising.getIdentificacaoRpsNumero() + "</Numero>" +
                "<Serie>" + tituloPagamentoNfsFranchising.getIdentificacaoRpsSerie() + "</Serie>" +
                "<Tipo>" + tituloPagamentoNfsFranchising.getIdentificacaoRpsTipo() + "</Tipo>" +
                "</IdentificacaoRps>";
    }

    public String createServico(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<Servico>" +
                createValores(tituloPagamentoNfsFranchising) +
                "<ItemListaServico>" + tituloPagamentoNfsFranchising.getItemListaServico() + "</ItemListaServico>" +
                "<CodigoCnae>" + tituloPagamentoNfsFranchising.getCodigoCnaePrestador() + "</CodigoCnae>" +
                "<Discriminacao>" + tituloPagamentoNfsFranchising.getDiscriminacao() + "</Discriminacao>" +
                "<CodigoMunicipio>" + tituloPagamentoNfsFranchising.getCodigoMunicipioPrestador() + "</CodigoMunicipio>" +
                "</Servico>";
    }

    public String createValores(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<Valores>" +
                "<ValorServicos>" + tituloPagamentoNfsFranchising.getValorServicos() + "</ValorServicos>" +
                "<IssRetido>" + tituloPagamentoNfsFranchising.getIssRetido() + "</IssRetido>" +
                "<ValorIss>" + tituloPagamentoNfsFranchising.getValorIss() + "</ValorIss>" +
                "<BaseCalculo>" + tituloPagamentoNfsFranchising.getBaseCalculo() + "</BaseCalculo>" +
                "<Aliquota>" + tituloPagamentoNfsFranchising.getAliquota() + "</Aliquota>" +
                "<ValorLiquidoNfse>" + tituloPagamentoNfsFranchising.getValorLiquidoNfse() + "</ValorLiquidoNfse>" +
                "</Valores>";
    }

    public String createPrestador(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<Prestador>" +
                "<Cnpj>" + tituloPagamentoNfsFranchising.getCnpjPrestador() + "</Cnpj>" +
                "<InscricaoMunicipal>" + tituloPagamentoNfsFranchising.getInscricaoMunicipalPrestador() + "</InscricaoMunicipal>" +
                "</Prestador>";
    }

    public String createTomador(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<Tomador>" +
                createIdentificacaoTomador(tituloPagamentoNfsFranchising) +
                "<RazaoSocial>" + tituloPagamentoNfsFranchising.getRazaoSocialTomador() + "</RazaoSocial>" +
                createEndereco(tituloPagamentoNfsFranchising) +
                createContato(tituloPagamentoNfsFranchising) +
                "</Tomador>";
    }

    public String createIdentificacaoTomador(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<IdentificacaoTomador>" +
                "<CpfCnpj>" +
                "<Cnpj>" + tituloPagamentoNfsFranchising.getCnpjTomador() + "</Cnpj>" +
                "</CpfCnpj>" +
                "</IdentificacaoTomador>";
    }

    public String createEndereco(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<Endereco>" +
                "<Endereco>" + tituloPagamentoNfsFranchising.getEnderecoTomador() + "</Endereco>" +
                "<Numero>" + tituloPagamentoNfsFranchising.getNumeroTomador() + "</Numero>" +
                "<Complemento>" + tituloPagamentoNfsFranchising.getComplementoTomador() + "</Complemento>" +
                "<Bairro>" + tituloPagamentoNfsFranchising.getBairroTomador() + "</Bairro>" +
                "<CodigoMunicipio>" + tituloPagamentoNfsFranchising.getCodigoMunicipioTomador() + "</CodigoMunicipio>" +
                "<Uf>" + tituloPagamentoNfsFranchising.getUfTomador() + "</Uf>" +
                "<Cep>" + tituloPagamentoNfsFranchising.getCepTomador() + "</Cep>" +
                "</Endereco>";
    }

    public String createContato(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {
        return "<Contato>" +
                "<Telefone>" + tituloPagamentoNfsFranchising.getTelefoneTomador() + "</Telefone>" +
                "<Email>" + tituloPagamentoNfsFranchising.getEmailTomador() + "</Email>" +
                "</Contato>";
    }

    public String createCondicaoPagamento(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) {

        int qtdParcelas = tituloPagamentoNfsFranchising.getQtdParcela();

        StringBuilder condicaoPagamento = new StringBuilder();
        condicaoPagamento.append("<CondicaoPagamento>");
        condicaoPagamento.append("<Condicao>" + tituloPagamentoNfsFranchising.getCondicaoPagamento() + "</Condicao>");
        condicaoPagamento.append("<QtdParcela>").append(qtdParcelas).append("</QtdParcela>");

        for (ParcelaInfoNfsFranchising info : tituloPagamentoNfsFranchising.getParcelas()) {
            condicaoPagamento.append(createParcelas(info));
        }

        condicaoPagamento.append("</CondicaoPagamento>");

        return condicaoPagamento.toString();
    }

    public String createParcelas(ParcelaInfoNfsFranchising parcelaInfo) {
        return "<Parcelas>" +
                "<Parcela>" + parcelaInfo.getNumeroParcela() + "</Parcela>" +
                "<DataVencimento>" + parcelaInfo.getDataVencimento() + "</DataVencimento>" +
                "<Valor>" + parcelaInfo.getValor() + "</Valor>" +
                "</Parcelas>";
    }

    public String getFullXml(TituloPagamentoNfsFranchising tituloPagamentoNfsFranchising) throws Exception {
        return createEnvelope() + createHeader() + createBody(tituloPagamentoNfsFranchising) + endEnvelope();
    }

    public String montarXmlConsultaSituacaoLoteRps(String cnpj, String inscricaoMunicipal, String protocolo) {
        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:e=\"http://www.betha.com.br/e-nota-contribuinte-ws\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <e:ConsultarSituacaoLoteRpsEnvio>\n"
                + "         <Prestador>\n"
                + "            <Cnpj>" + cnpj + "</Cnpj>\n"
                + "            <InscricaoMunicipal>" + inscricaoMunicipal + "</InscricaoMunicipal>\n"
                + "         </Prestador>\n"
                + "         <Protocolo>" + protocolo + "</Protocolo>\n"
                + "      </e:ConsultarSituacaoLoteRpsEnvio>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
        return xml;
    }
}
