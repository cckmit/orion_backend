package br.com.live.sistema.model;

import java.util.Date;

public class AtividadeProjeto {

    private long id;
    private String descricao;
    private long idProjeto;
    private long idFase;
    private long idTipoAtividade;
    private long idResponsavel;
    private Date dataPrevInicio;
    private Date dataPrevFim;
    private int tempoPrevisto;

    public AtividadeProjeto(long id, String descricao, long idProjeto, long idFase, long idTipoAtividade, long idResponsavel, Date dataPrevInicio, Date dataPrevFim, int tempoPrevisto) {
        this.id = id;
        this.descricao = descricao;
        this.idProjeto = idProjeto;
        this.idFase = idFase;
        this.idTipoAtividade = idTipoAtividade;
        this.idResponsavel = idResponsavel;
        this.dataPrevInicio = dataPrevInicio;
        this.dataPrevFim = dataPrevFim;
        this.tempoPrevisto = tempoPrevisto;
    }

    public AtividadeProjeto(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public long getIdFase() {
        return idFase;
    }

    public void setIdFase(long idFase) {
        this.idFase = idFase;
    }

    public long getIdTipoAtividade() {
        return idTipoAtividade;
    }

    public void setIdTipoAtividade(long idTipoAtividade) {
        this.idTipoAtividade = idTipoAtividade;
    }

    public long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Date getDataPrevInicio() {
        return dataPrevInicio;
    }

    public void setDataPrevInicio(Date dataPrevInicio) {
        this.dataPrevInicio = dataPrevInicio;
    }

    public Date getDataPrevFim() {
        return dataPrevFim;
    }

    public void setDataPrevFim(Date dataPrevFim) {
        this.dataPrevFim = dataPrevFim;
    }

    public int getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(int tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }
}
