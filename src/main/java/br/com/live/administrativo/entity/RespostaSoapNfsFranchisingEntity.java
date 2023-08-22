package br.com.live.administrativo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="orion_fin_090")
public class RespostaSoapNfsFranchisingEntity {

    @Id
    @Column(name = "numero_lote")
    public long numeroLote;

    @Column(name = "num_duplicata")
    public String numDuplicata;

    @Column(name = "cnpj_tomador")
    public String cnpjTomador;

    @Column(name = "data_recebimento")
    public Date dataRecebimento;

    public String protocolo;

    @Column(name = "num_nfs")
    public String numNfs;

    @Column(name = "cod_retorno")
    public String codRetorno;

    @Column(name = "descricao_retorno")
    public String descricaoRetorno;

    public RespostaSoapNfsFranchisingEntity(long numeroLote, String numDuplicata, String cnpjTomador, Date dataRecebimento, String protocolo) {
        this.numeroLote = numeroLote;
        this.numDuplicata = numDuplicata;
        this.cnpjTomador = cnpjTomador;
        this.dataRecebimento = dataRecebimento;
        this.protocolo = protocolo;
    }

    public RespostaSoapNfsFranchisingEntity(long numeroLote, String numDuplicata, String cnpjTomador, Date dataRecebimento, String protocolo, String numNfs) {
        this.numeroLote = numeroLote;
        this.numDuplicata = numDuplicata;
        this.cnpjTomador = cnpjTomador;
        this.dataRecebimento = dataRecebimento;
        this.protocolo = protocolo;
        this.numNfs = numNfs;
    }

    public RespostaSoapNfsFranchisingEntity(long numeroLote, String numDuplicata, String cnpjTomador, Date dataRecebimento, String protocolo, String numNfs, String codRetorno, String descricaoRetorno) {
        this.numeroLote = numeroLote;
        this.numDuplicata = numDuplicata;
        this.cnpjTomador = cnpjTomador;
        this.dataRecebimento = dataRecebimento;
        this.protocolo = protocolo;
        this.numNfs = numNfs;
        this.codRetorno = codRetorno;
        this.descricaoRetorno = descricaoRetorno;
    }

    public RespostaSoapNfsFranchisingEntity(){}

    public long getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(long numeroLote) {
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

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getNumNfs() {
        return numNfs;
    }

    public void setNumNfs(String numNfs) {
        this.numNfs = numNfs;
    }

    public String getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(String codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getDescricaoRetorno() {
        return descricaoRetorno;
    }

    public void setDescricaoRetorno(String descricaoRetorno) {
        this.descricaoRetorno = descricaoRetorno;
    }
}
