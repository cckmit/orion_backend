package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orion_cfc_310")
public class ControleRelaxadeira {

    @Id
    public String id;

    public int status;

    @Column(name = "cod_rolo")
    public int codRolo;

    @Column(name = "cod_analista")
    public int codAnalista;

    @Column(name = "cod_motivo")
    public int codMotivo;

    public float quantidade;

    @Column(name = "perda_metros")
    public float perdaMetros;

    @Column(name = "sit_sincronizacao")
    public int sitSincronizacao;

    @Column(name = "data_sincronizacao")
    public Date dataSincronizacao;

    @Column(name = "data_hora_bipagem")
    public Date dataHoraBipagem;

    public float largura;
    public float gramatura;
    public float metragem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCodRolo() {
        return codRolo;
    }

    public void setCodRolo(int codRolo) {
        this.codRolo = codRolo;
    }

    public int getCodAnalista() {
        return codAnalista;
    }

    public void setCodAnalista(int codAnalista) {
        this.codAnalista = codAnalista;
    }

    public int getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(int codMotivo) {
        this.codMotivo = codMotivo;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public float getPerdaMetros() {
        return perdaMetros;
    }

    public void setPerdaMetros(float perdaMetros) {
        this.perdaMetros = perdaMetros;
    }

    public int getSitSincronizacao() {
        return sitSincronizacao;
    }

    public void setSitSincronizacao(int sitSincronizacao) {
        this.sitSincronizacao = sitSincronizacao;
    }

    public Date getDataSincronizacao() {
        return dataSincronizacao;
    }

    public void setDataSincronizacao(Date dataSincronizacao) {
        this.dataSincronizacao = dataSincronizacao;
    }

    public Date getDataHoraBipagem() {
        return dataHoraBipagem;
    }

    public void setDataHoraBipagem(Date dataHoraBipagem) {
        this.dataHoraBipagem = dataHoraBipagem;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public float getGramatura() {
        return gramatura;
    }

    public void setGramatura(float gramatura) {
        this.gramatura = gramatura;
    }

    public float getMetragem() {
        return metragem;
    }

    public void setMetragem(float metragem) {
        this.metragem = metragem;
    }

    public ControleRelaxadeira() {

    }

    public ControleRelaxadeira(int status, int codRolo, int codAnalista, int codMotivo, float quantidade, float perdaMetros, int sitSincronizacao, Date dataSincronizacao, Date dataHoraBipagem,
                               float largura, float gramatura, float metragem) {
        this.id = status + "-" + codRolo + "-" + codMotivo;
        this.status = status;
        this.codRolo = codRolo;
        this.codAnalista = codAnalista;
        this.codMotivo = codMotivo;
        this.quantidade = quantidade;
        this.perdaMetros = perdaMetros;
        this.sitSincronizacao = sitSincronizacao;
        this.dataSincronizacao = dataSincronizacao;
        this.dataHoraBipagem = dataHoraBipagem;
        this.largura = largura;
        this.gramatura = gramatura;
        this.metragem = metragem;
    }
}
