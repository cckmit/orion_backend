package br.com.live.model;

import java.util.Date;

public class ConsultaHistAuditoria {
    public int id;
    public int volume;
    public int rua;
    public int localCaixa;
    public Date dataAuditoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getRua() {
        return rua;
    }

    public void setRua(int rua) {
        this.rua = rua;
    }

    public int getLocalCaixa() {
        return localCaixa;
    }

    public void setLocalCaixa(int localCaixa) {
        this.localCaixa = localCaixa;
    }

    public Date getDataAuditoria() {
        return dataAuditoria;
    }

    public void setDataAuditoria(Date dataAuditoria) {
        this.dataAuditoria = dataAuditoria;
    }

    public ConsultaHistAuditoria(){
    }

    public ConsultaHistAuditoria(int id, int volume, int rua, int localCaixa, Date dataAuditoria) {
        this.id = id;
        this.volume = volume;
        this.rua = rua;
        this.localCaixa = localCaixa;
        this.dataAuditoria = dataAuditoria;
    }
}
