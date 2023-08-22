package br.com.live.administrativo.model;

import java.util.Date;

public class TituloPagamentoNfsFranchisingConsulta {

    private Date dataEmissao;
    private String cnpjLoja;
    private String descricaoLoja;
    private String numDuplicata;
    private int numPedido;
    private String codNaturezaConcat;
    private double descontoItem1;
    private double descontoItem2;
    private double descontoItem3;
    private double descontoItemCalculado;
    private int qtdParcelas;
    private String motivo;
    private int situacao;
    private int condicaoPagamento;
    private String descrCondicaoPagamento;
    private double valorTotalDuplicata;
    private double valorTotalDuplicataFranchising;
    private String status;

    public TituloPagamentoNfsFranchisingConsulta(){}

    public TituloPagamentoNfsFranchisingConsulta(Date dataEmissao, String cnpjLoja, String descricaoLoja, String numDuplicata, int numPedido, String codNaturezaConcat, double descontoItem1, double descontoItem2, double descontoItem3, double descontoItemCalculado, int qtdParcelas, double valorTotalDuplicata, String motivo, int situacao, int condicaoPagamento, String descrCondicaoPagamento, double valorTotalDuplicataFranchising) {
        this.dataEmissao = dataEmissao;
        this.cnpjLoja = cnpjLoja;
        this.descricaoLoja = descricaoLoja;
        this.numDuplicata = numDuplicata;
        this.numPedido = numPedido;
        this.codNaturezaConcat = codNaturezaConcat;
        this.descontoItem1 = descontoItem1;
        this.descontoItem2 = descontoItem2;
        this.descontoItem3 = descontoItem3;
        this.descontoItemCalculado = descontoItemCalculado;
        this.qtdParcelas = qtdParcelas;
        this.valorTotalDuplicata = valorTotalDuplicata;
        this.motivo = motivo;
        this.situacao = situacao;
        this.condicaoPagamento = condicaoPagamento;
        this.descrCondicaoPagamento = descrCondicaoPagamento;
        this.valorTotalDuplicataFranchising = valorTotalDuplicataFranchising;
    }

    public TituloPagamentoNfsFranchisingConsulta(Date dataEmissao, String cnpjLoja, String descricaoLoja, String numDuplicata, int numPedido, String codNaturezaConcat, double descontoItem1, double descontoItem2, double descontoItem3, double descontoItemCalculado, int qtdParcelas, double valorTotalDuplicata, String motivo, int situacao, int condicaoPagamento, String descrCondicaoPagamento, double valorTotalDuplicataFranchising, String status) {
        this.dataEmissao = dataEmissao;
        this.cnpjLoja = cnpjLoja;
        this.descricaoLoja = descricaoLoja;
        this.numDuplicata = numDuplicata;
        this.numPedido = numPedido;
        this.codNaturezaConcat = codNaturezaConcat;
        this.descontoItem1 = descontoItem1;
        this.descontoItem2 = descontoItem2;
        this.descontoItem3 = descontoItem3;
        this.descontoItemCalculado = descontoItemCalculado;
        this.qtdParcelas = qtdParcelas;
        this.valorTotalDuplicata = valorTotalDuplicata;
        this.motivo = motivo;
        this.situacao = situacao;
        this.condicaoPagamento = condicaoPagamento;
        this.descrCondicaoPagamento = descrCondicaoPagamento;
        this.valorTotalDuplicataFranchising = valorTotalDuplicataFranchising;
        this.status = status;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public String getDescricaoLoja() {
        return descricaoLoja;
    }

    public void setDescricaoLoja(String descricaoLoja) {
        this.descricaoLoja = descricaoLoja;
    }

    public String getNumDuplicata() {
        return numDuplicata;
    }

    public void setNumDuplicata(String numDuplicata) {
        this.numDuplicata = numDuplicata;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public String getCodNaturezaConcat() {
        return codNaturezaConcat;
    }

    public void setCodNaturezaConcat(String codNaturezaConcat) {
        this.codNaturezaConcat = codNaturezaConcat;
    }

    public double getDescontoItem1() {
        return descontoItem1;
    }

    public void setDescontoItem1(double descontoItem1) {
        this.descontoItem1 = descontoItem1;
    }

    public double getDescontoItem2() {
        return descontoItem2;
    }

    public void setDescontoItem2(double descontoItem2) {
        this.descontoItem2 = descontoItem2;
    }

    public double getDescontoItem3() {
        return descontoItem3;
    }

    public void setDescontoItem3(double descontoItem3) {
        this.descontoItem3 = descontoItem3;
    }

    public double getDescontoItemCalculado() {
        return descontoItemCalculado;
    }

    public void setDescontoItemCalculado(double descontoItemCalculado) {
        this.descontoItemCalculado = descontoItemCalculado;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public double getValorTotalDuplicata() {
        return valorTotalDuplicata;
    }

    public void setValorTotalDuplicata(double valorTotalDuplicata) {
        this.valorTotalDuplicata = valorTotalDuplicata;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public int getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(int condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public String getDescrCondicaoPagamento() {
        return descrCondicaoPagamento;
    }

    public void setDescrCondicaoPagamento(String descrCondicaoPagamento) {
        this.descrCondicaoPagamento = descrCondicaoPagamento;
    }

    public double getValorTotalDuplicataFranchising() {
        return valorTotalDuplicataFranchising;
    }

    public void setValorTotalDuplicataFranchising(double valorTotalDuplicataFranchising) {
        this.valorTotalDuplicataFranchising = valorTotalDuplicataFranchising;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
