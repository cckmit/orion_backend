package br.com.live.administrativo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="orion_fin_095")
public class LogTituloNfsFranchisingEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_lote")
    private Long numeroLote;

    @Column(name = "num_duplicata")
    private String numDuplicata;

    @Column(name = "cnpj_tomador")
    private String cnpjTomador;

    @Column(name = "data_envio")
    private Date dataEnvio;

    @Column(name = "conteudo_xml")
    private String conteudoXml;

    @Column(name = "status")
    private String status;

    @Column(name = "motivo")
    private String motivo;

    public LogTituloNfsFranchisingEntity(){}

    public LogTituloNfsFranchisingEntity(Long id, Long numeroLote, String numDuplicata, String cnpjTomador, Date dataEnvio, String conteudoXml, String status, String motivo) {
        this.id = id;
        this.numeroLote = numeroLote;
        this.numDuplicata = numDuplicata;
        this.cnpjTomador = cnpjTomador;
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
