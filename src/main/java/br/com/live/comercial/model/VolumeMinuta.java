package br.com.live.comercial.model;

import java.util.Date;

public class VolumeMinuta {

    public int numeroVolume;
    public Date dataMontagem;
    public String horaMontagem;
    public int codUsuario;
    public int localCaixa;


    public VolumeMinuta(){
    }

    public VolumeMinuta(int numeroVolume, Date dataMontagem, String horaMontagem, int codUsuario, int localCaixa) {
        this.numeroVolume = numeroVolume;
        this.dataMontagem = dataMontagem;
        this.horaMontagem = horaMontagem;
        this.codUsuario = codUsuario;
        this.localCaixa = localCaixa;
    }

    public int getNumeroVolume() {
        return numeroVolume;
    }

    public void setNumeroVolume(int numeroVolume) {
        this.numeroVolume = numeroVolume;
    }

    public Date getDataMontagem() {
        return dataMontagem;
    }

    public void setDataMontagem(Date dataMontagem) {
        this.dataMontagem = dataMontagem;
    }

    public String getHoraMontagem() {
        return horaMontagem;
    }

    public void setHoraMontagem(String horaMontagem) {
        this.horaMontagem = horaMontagem;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getLocalCaixa() {
        return localCaixa;
    }

    public void setLocalCaixa(int localCaixa) {
        this.localCaixa = localCaixa;
    }
}
