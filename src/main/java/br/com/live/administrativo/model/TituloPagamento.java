package br.com.live.administrativo.model;

import java.util.List;

public class TituloPagamento {

    private int nrNota;
    private String dataEmissao;
    private double valorServico;
    private String discriminacao;
    private String cnpjTomador;
    private List<ParcelaTituloPagamento> parcelasTitulo;
    private boolean notaCancelada;

    public TituloPagamento(int nrNota, String dataEmissao, double valorServico, String discriminacao, String cnpjTomador, List<ParcelaTituloPagamento> parcelasTitulo, boolean notaCancelada) {
        this.nrNota = nrNota;
        this.dataEmissao = dataEmissao;
        this.valorServico = valorServico;
        this.discriminacao = discriminacao;
        this.cnpjTomador = cnpjTomador;
        this.parcelasTitulo = parcelasTitulo;
        this.notaCancelada = notaCancelada;
    }

    public TituloPagamento(){

    }

    public boolean isNotaCancelada() {
        return notaCancelada;
    }

    public void setNotaCancelada(boolean notaCancelada) {
        this.notaCancelada = notaCancelada;
    }

    public int getNrNota() {
        return nrNota;
    }

    public void setNrNota(int nrNota) {
        this.nrNota = nrNota;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
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

    public List<ParcelaTituloPagamento> getParcelasTitulo() {
        return parcelasTitulo;
    }

    public void setParcelasTitulo(List<ParcelaTituloPagamento> parcelasTitulo) {
        this.parcelasTitulo = parcelasTitulo;
    }
}
