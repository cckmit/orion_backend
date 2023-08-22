package br.com.live.administrativo.model;

import java.util.Date;

public class LogTituloNfsFranchising {

    private Long id;
    private Long numeroLote;
    private String numDuplicata;
    private String cnpjTomador;
    private String descricaoTomador;
    private Date dataEnvio;
    private String conteudoXml;
    private String status;
    private String motivo;

    public LogTituloNfsFranchising(){}

    public LogTituloNfsFranchising(Long id, Long numeroLote, String numDuplicata, String cnpjTomador, String descricaoTomador, Date dataEnvio, String conteudoXml, String status, String motivo) {
        this.id = id;
        this.numeroLote = numeroLote;
        this.numDuplicata = numDuplicata;
        this.cnpjTomador = cnpjTomador;
        this.descricaoTomador = descricaoTomador;
        this.dataEnvio = dataEnvio;
        this.conteudoXml = conteudoXml;
        this.status = status;
        this.motivo = motivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(Long numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getNumDuplicata() {
        return numDuplicata;
    }

    public void setNumDuplicata(String numDuplicata) {
        this.numDuplicata = numDuplicata;
    }

    public String getCnpjTomador() {
        return cnpjTomador;
    }

    public void setCnpjTomador(String cnpjTomador) {
        this.cnpjTomador = cnpjTomador;
    }

    public String getDescricaoTomador() {
        return descricaoTomador;
    }

    public void setDescricaoTomador(String descricaoTomador) {
        this.descricaoTomador = descricaoTomador;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getConteudoXml() {
        return conteudoXml;
    }

    public void setConteudoXml(String conteudoXml) {
        this.conteudoXml = conteudoXml;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
