package br.com.live.sistema.model;

import java.util.List;

public class EscopoProjeto {

    private long idProjeto;
    private String mvps;
    private String parteAfetada;
    private String sistemaProcessoAfetado;
    private String exclusaoEscopo;
    private List<AprovadorProjeto> aprovadorProjetoList;

    public EscopoProjeto(long idProjeto, String mvps, String parteAfetada, String sistemaProcessoAfetado, String exclusaoEscopo, List<AprovadorProjeto> aprovadorProjetoList) {
        this.idProjeto = idProjeto;
        this.mvps = mvps;
        this.parteAfetada = parteAfetada;
        this.sistemaProcessoAfetado = sistemaProcessoAfetado;
        this.exclusaoEscopo = exclusaoEscopo;
        this.aprovadorProjetoList = aprovadorProjetoList;
    }

    public EscopoProjeto(){}

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getMvps() {
        return mvps;
    }

    public void setMvps(String mvps) {
        this.mvps = mvps;
    }

    public String getParteAfetada() {
        return parteAfetada;
    }

    public void setParteAfetada(String parteAfetada) {
        this.parteAfetada = parteAfetada;
    }

    public String getSistemaProcessoAfetado() {
        return sistemaProcessoAfetado;
    }

    public void setSistemaProcessoAfetado(String sistemaProcessoAfetado) {
        this.sistemaProcessoAfetado = sistemaProcessoAfetado;
    }

    public String getExclusaoEscopo() {
        return exclusaoEscopo;
    }

    public void setExclusaoEscopo(String exclusaoEscopo) {
        this.exclusaoEscopo = exclusaoEscopo;
    }

    public List<AprovadorProjeto> getAprovadorProjetoList() {
        return aprovadorProjetoList;
    }

    public void setAprovadorProjetoList(List<AprovadorProjeto> aprovadorProjetoList) {
        this.aprovadorProjetoList = aprovadorProjetoList;
    }
}
