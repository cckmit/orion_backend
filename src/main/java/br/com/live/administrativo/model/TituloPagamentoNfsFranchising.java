package br.com.live.administrativo.model;

import java.util.List;

public class TituloPagamentoNfsFranchising {

    private String numDuplicata;
    private long numeroLote;
    private String cnpjPrestador;
    private String inscricaoMunicipalPrestador;
    private int quantidadeRps;
    private String codigoMunicipioPrestador;
    private long identificacaoRpsNumero;
    private String identificacaoRpsSerie;
    private String identificacaoRpsTipo;
    private String dataEmissao;
    private int naturezaOperacao;
    private int optanteSimplesNacional;
    private int status;
    private double valorServicos;
    private int issRetido;
    private double valorIss;
    private double baseCalculo;
    private int aliquota;
    private double valorLiquidoNfse;
    private String itemListaServico;
    private String codigoCnaePrestador;
    private String discriminacao;
    private String cnpjTomador;
    private String razaoSocialTomador;
    private String enderecoTomador;
    private int numeroTomador;
    private String complementoTomador;
    private String bairroTomador;
    private int codigoMunicipioTomador;
    private String ufTomador;
    private String cepTomador;
    private String telefoneTomador;
    private String emailTomador;
    private String condicaoPagamento;
    private int qtdParcela;
    private List<ParcelaInfoNfsFranchising> parcelas;

    public TituloPagamentoNfsFranchising(){}

    public TituloPagamentoNfsFranchising(String numDuplicata, long numeroLote, String cnpjPrestador, String inscricaoMunicipalPrestador, int quantidadeRps, String codigoMunicipioPrestador, long identificacaoRpsNumero, String identificacaoRpsSerie, String identificacaoRpsTipo, String dataEmissao, int naturezaOperacao, int optanteSimplesNacional, int status, double valorServicos, int issRetido, double valorIss, double baseCalculo, int aliquota, double valorLiquidoNfse, String itemListaServico, String codigoCnaePrestador, String discriminacao, String cnpjTomador, String razaoSocialTomador, String enderecoTomador, int numeroTomador, String complementoTomador, String bairroTomador, int codigoMunicipioTomador, String ufTomador, String cepTomador, String telefoneTomador, String emailTomador, String condicaoPagamento, int qtdParcela, List<ParcelaInfoNfsFranchising> parcelas) {
        this.numDuplicata = numDuplicata;
        this.numeroLote = numeroLote;
        this.cnpjPrestador = cnpjPrestador;
        this.inscricaoMunicipalPrestador = inscricaoMunicipalPrestador;
        this.quantidadeRps = quantidadeRps;
        this.codigoMunicipioPrestador = codigoMunicipioPrestador;
        this.identificacaoRpsNumero = identificacaoRpsNumero;
        this.identificacaoRpsSerie = identificacaoRpsSerie;
        this.identificacaoRpsTipo = identificacaoRpsTipo;
        this.dataEmissao = dataEmissao;
        this.naturezaOperacao = naturezaOperacao;
        this.optanteSimplesNacional = optanteSimplesNacional;
        this.status = status;
        this.valorServicos = valorServicos;
        this.issRetido = issRetido;
        this.valorIss = valorIss;
        this.baseCalculo = baseCalculo;
        this.aliquota = aliquota;
        this.valorLiquidoNfse = valorLiquidoNfse;
        this.itemListaServico = itemListaServico;
        this.codigoCnaePrestador = codigoCnaePrestador;
        this.discriminacao = discriminacao;
        this.cnpjTomador = cnpjTomador;
        this.razaoSocialTomador = razaoSocialTomador;
        this.enderecoTomador = enderecoTomador;
        this.numeroTomador = numeroTomador;
        this.complementoTomador = complementoTomador;
        this.bairroTomador = bairroTomador;
        this.codigoMunicipioTomador = codigoMunicipioTomador;
        this.ufTomador = ufTomador;
        this.cepTomador = cepTomador;
        this.telefoneTomador = telefoneTomador;
        this.emailTomador = emailTomador;
        this.condicaoPagamento = condicaoPagamento;
        this.qtdParcela = qtdParcela;
        this.parcelas = parcelas;
    }

    public String getNumDuplicata() {
        return numDuplicata;
    }

    public void setNumDuplicata(String numDuplicata) {
        this.numDuplicata = numDuplicata;
    }

    public long getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(long numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getCnpjPrestador() {
        return cnpjPrestador;
    }

    public void setCnpjPrestador(String cnpjPrestador) {
        this.cnpjPrestador = cnpjPrestador;
    }

    public String getInscricaoMunicipalPrestador() {
        return inscricaoMunicipalPrestador;
    }

    public void setInscricaoMunicipalPrestador(String inscricaoMunicipalPrestador) {
        this.inscricaoMunicipalPrestador = inscricaoMunicipalPrestador;
    }

    public int getQuantidadeRps() {
        return quantidadeRps;
    }

    public void setQuantidadeRps(int quantidadeRps) {
        this.quantidadeRps = quantidadeRps;
    }

    public String getCodigoMunicipioPrestador() {
        return codigoMunicipioPrestador;
    }

    public void setCodigoMunicipioPrestador(String codigoMunicipioPrestador) {
        this.codigoMunicipioPrestador = codigoMunicipioPrestador;
    }

    public long getIdentificacaoRpsNumero() {
        return identificacaoRpsNumero;
    }

    public void setIdentificacaoRpsNumero(long identificacaoRpsNumero) {
        this.identificacaoRpsNumero = identificacaoRpsNumero;
    }

    public String getIdentificacaoRpsSerie() {
        return identificacaoRpsSerie;
    }

    public void setIdentificacaoRpsSerie(String identificacaoRpsSerie) {
        this.identificacaoRpsSerie = identificacaoRpsSerie;
    }

    public String getIdentificacaoRpsTipo() {
        return identificacaoRpsTipo;
    }

    public void setIdentificacaoRpsTipo(String identificacaoRpsTipo) {
        this.identificacaoRpsTipo = identificacaoRpsTipo;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(int naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public int getOptanteSimplesNacional() {
        return optanteSimplesNacional;
    }

    public void setOptanteSimplesNacional(int optanteSimplesNacional) {
        this.optanteSimplesNacional = optanteSimplesNacional;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getValorServicos() {
        return valorServicos;
    }

    public void setValorServicos(double valorServicos) {
        this.valorServicos = valorServicos;
    }

    public int getIssRetido() {
        return issRetido;
    }

    public void setIssRetido(int issRetido) {
        this.issRetido = issRetido;
    }

    public double getValorIss() {
        return valorIss;
    }

    public void setValorIss(double valorIss) {
        this.valorIss = valorIss;
    }

    public double getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(double baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public int getAliquota() {
        return aliquota;
    }

    public void setAliquota(int aliquota) {
        this.aliquota = aliquota;
    }

    public double getValorLiquidoNfse() {
        return valorLiquidoNfse;
    }

    public void setValorLiquidoNfse(double valorLiquidoNfse) {
        this.valorLiquidoNfse = valorLiquidoNfse;
    }

    public String getItemListaServico() {
        return itemListaServico;
    }

    public void setItemListaServico(String itemListaServico) {
        this.itemListaServico = itemListaServico;
    }

    public String getCodigoCnaePrestador() {
        return codigoCnaePrestador;
    }

    public void setCodigoCnaePrestador(String codigoCnaePrestador) {
        this.codigoCnaePrestador = codigoCnaePrestador;
    }

    public String getDiscriminacao() {
        return discriminacao;
    }

    public void setDiscriminacao(String discriminacao) {
        this.discriminacao = discriminacao;
    }

    public String getCnpjTomador() {
        return cnpjTomador;
    }

    public void setCnpjTomador(String cnpjTomador) {
        this.cnpjTomador = cnpjTomador;
    }

    public String getRazaoSocialTomador() {
        return razaoSocialTomador;
    }

    public void setRazaoSocialTomador(String razaoSocialTomador) {
        this.razaoSocialTomador = razaoSocialTomador;
    }

    public String getEnderecoTomador() {
        return enderecoTomador;
    }

    public void setEnderecoTomador(String enderecoTomador) {
        this.enderecoTomador = enderecoTomador;
    }

    public int getNumeroTomador() {
        return numeroTomador;
    }

    public void setNumeroTomador(int numeroTomador) {
        this.numeroTomador = numeroTomador;
    }

    public String getComplementoTomador() {
        return complementoTomador;
    }

    public void setComplementoTomador(String complementoTomador) {
        this.complementoTomador = complementoTomador;
    }

    public String getBairroTomador() {
        return bairroTomador;
    }

    public void setBairroTomador(String bairroTomador) {
        this.bairroTomador = bairroTomador;
    }

    public int getCodigoMunicipioTomador() {
        return codigoMunicipioTomador;
    }

    public void setCodigoMunicipioTomador(int codigoMunicipioTomador) {
        this.codigoMunicipioTomador = codigoMunicipioTomador;
    }

    public String getUfTomador() {
        return ufTomador;
    }

    public void setUfTomador(String ufTomador) {
        this.ufTomador = ufTomador;
    }

    public String getCepTomador() {
        return cepTomador;
    }

    public void setCepTomador(String cepTomador) {
        this.cepTomador = cepTomador;
    }

    public String getTelefoneTomador() {
        return telefoneTomador;
    }

    public void setTelefoneTomador(String telefoneTomador) {
        this.telefoneTomador = telefoneTomador;
    }

    public String getEmailTomador() {
        return emailTomador;
    }

    public void setEmailTomador(String emailTomador) {
        this.emailTomador = emailTomador;
    }

    public String getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(String condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public int getQtdParcela() {
        return qtdParcela;
    }

    public void setQtdParcela(int qtdParcela) {
        this.qtdParcela = qtdParcela;
    }

    public List<ParcelaInfoNfsFranchising> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelaInfoNfsFranchising> parcelas) {
        this.parcelas = parcelas;
    }
}
