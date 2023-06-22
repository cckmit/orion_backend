package br.com.live.administrativo.model;

import java.util.Date;

public class TituloPagamentoIntegrado {
    long id;
    int codEmpresa;
    String cnpjCliente;
    String nomeCliente;
    int numTitulo;
    Date dataEmissao;
    Date dataHoraIntegracao;
    String situacaoIntegracao;
    String descricaoIntegracao;
    String complemento;
    int seqTitulo;
    Date dataVencTitulo;
    double valorTitulo;

    public TituloPagamentoIntegrado(long id, int codEmpresa, String cnpjCliente, String nomeCliente, int numTitulo, Date dataEmissao, Date dataHoraIntegracao, String situacaoIntegracao, String descricaoIntegracao, String complemento, int seqTitulo, Date dataVencTitulo, double valorTitulo) {
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.cnpjCliente = cnpjCliente;
        this.nomeCliente = nomeCliente;
        this.numTitulo = numTitulo;
        this.dataEmissao = dataEmissao;
        this.dataHoraIntegracao = dataHoraIntegracao;
        this.situacaoIntegracao = situacaoIntegracao;
        this.descricaoIntegracao = descricaoIntegracao;
        this.complemento = complemento;
        this.seqTitulo = seqTitulo;
        this.dataVencTitulo = dataVencTitulo;
        this.valorTitulo = valorTitulo;
    }

    public TituloPagamentoIntegrado(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCnpjCliente() {
        return cnpjCliente;
    }

    public void setCnpjCliente(String cnpjCliente) {
        this.cnpjCliente = cnpjCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getNumTitulo() {
        return numTitulo;
    }

    public void setNumTitulo(int numTitulo) {
        this.numTitulo = numTitulo;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataHoraIntegracao() {
        return dataHoraIntegracao;
    }

    public void setDataHoraIntegracao(Date dataHoraIntegracao) {
        this.dataHoraIntegracao = dataHoraIntegracao;
    }

    public String getSituacaoIntegracao() {
        return situacaoIntegracao;
    }

    public void setSituacaoIntegracao(String situacaoIntegracao) {
        this.situacaoIntegracao = situacaoIntegracao;
    }

    public String getDescricaoIntegracao() {
        return descricaoIntegracao;
    }

    public void setDescricaoIntegracao(String descricaoIntegracao) {
        this.descricaoIntegracao = descricaoIntegracao;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getSeqTitulo() {
        return seqTitulo;
    }

    public void setSeqTitulo(int seqTitulo) {
        this.seqTitulo = seqTitulo;
    }

    public Date getDataVencTitulo() {
        return dataVencTitulo;
    }

    public void setDataVencTitulo(Date dataVencTitulo) {
        this.dataVencTitulo = dataVencTitulo;
    }

    public double getValorTitulo() {
        return valorTitulo;
    }

    public void setValorTitulo(double valorTitulo) {
        this.valorTitulo = valorTitulo;
    }
}
