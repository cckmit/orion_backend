package br.com.live.sistema.model;

import java.util.Date;

public class CronogramaRealizadoProjeto {

    private Long id;
    private Long idProjeto;
    private String descricao;
    private Long idFase;
    private Long idResponsavel;
    private String dataInicioPrev;
    private String dataFimPrev;
    private double tempoPrev;
    private String dataInicioReal;
    private String dataFimReal;
    private double tempoReal;
    private String status;
    private double percentualConclusao;
    private double custoReal;

    public CronogramaRealizadoProjeto(){}

    public CronogramaRealizadoProjeto(Long id, Long idProjeto, String descricao, Long idFase, Long idResponsavel, String dataInicioPrev, String dataFimPrev, double tempoPrev, String dataInicioReal, String dataFimReal, double tempoReal, String status, double percentualConclusao, double custoReal) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.descricao = descricao;
        this.idFase = idFase;
        this.idResponsavel = idResponsavel;
        this.dataInicioPrev = dataInicioPrev;
        this.dataFimPrev = dataFimPrev;
        this.tempoPrev = tempoPrev;
        this.dataInicioReal = dataInicioReal;
        this.dataFimReal = dataFimReal;
        this.tempoReal = tempoReal;
        this.status = status;
        this.percentualConclusao = percentualConclusao;
        this.custoReal = custoReal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdFase() {
        return idFase;
    }

    public void setIdFase(Long idFase) {
        this.idFase = idFase;
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getDataInicioPrev() {
        return dataInicioPrev;
    }

    public void setDataInicioPrev(String dataInicioPrev) {
        this.dataInicioPrev = dataInicioPrev;
    }

    public String getDataFimPrev() {
        return dataFimPrev;
    }

    public void setDataFimPrev(String dataFimPrev) {
        this.dataFimPrev = dataFimPrev;
    }

    public double getTempoPrev() {
        return tempoPrev;
    }

    public void setTempoPrev(double tempoPrev) {
        this.tempoPrev = tempoPrev;
    }

    public String getDataInicioReal() {
        return dataInicioReal;
    }

    public void setDataInicioReal(String dataInicioReal) {
        this.dataInicioReal = dataInicioReal;
    }

    public String getDataFimReal() {
        return dataFimReal;
    }

    public void setDataFimReal(String dataFimReal) {
        this.dataFimReal = dataFimReal;
    }

    public double getTempoReal() {
        return tempoReal;
    }

    public void setTempoReal(double tempoReal) {
        this.tempoReal = tempoReal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPercentualConclusao() {
        return percentualConclusao;
    }

    public void setPercentualConclusao(double percentualConclusao) {
        this.percentualConclusao = percentualConclusao;
    }

    public double getCustoReal() {
        return custoReal;
    }

    public void setCustoReal(double custoReal) {
        this.custoReal = custoReal;
    }
}
