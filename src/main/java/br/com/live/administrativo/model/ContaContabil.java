package br.com.live.administrativo.model;

import java.util.Date;

public class ContaContabil {

    public int codEmpresa;
    public int exercicio;
    public int numeroLancamento;
    public int seqLancamento;
    public int lote;
    public Date dataLancamento;

    public ContaContabil(){}

    public ContaContabil(int codEmpresa, int exercicio, int numeroLancamento, int seqLancamento, int lote, Date dataLancamento) {
        this.codEmpresa = codEmpresa;
        this.exercicio = exercicio;
        this.numeroLancamento = numeroLancamento;
        this.seqLancamento = seqLancamento;
        this.lote = lote;
        this.dataLancamento = dataLancamento;
    }

    public int getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getExercicio() {
        return exercicio;
    }

    public void setExercicio(int exercicio) {
        this.exercicio = exercicio;
    }

    public int getNumeroLancamento() {
        return numeroLancamento;
    }

    public void setNumeroLancamento(int numeroLancamento) {
        this.numeroLancamento = numeroLancamento;
    }

    public int getSeqLancamento() {
        return seqLancamento;
    }

    public void setSeqLancamento(int seqLancamento) {
        this.seqLancamento = seqLancamento;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
}
