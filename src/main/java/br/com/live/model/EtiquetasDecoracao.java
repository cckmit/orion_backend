package br.com.live.model;

public class EtiquetasDecoracao {
    public int ordemProducao;
    public int ordemConfeccao;
    public int alternativa;
    public int roteiro;
    public String nivel;
    public String referencia;
    public String tamanho;
    public String cor;
    public int quantidade;
    public int periodo;
    public String descEstagio;

    public int getOrdemProducao() {
        return ordemProducao;
    }

    public void setOrdemProducao(int ordemProducao) {
        this.ordemProducao = ordemProducao;
    }

    public int getOrdemConfeccao() {
        return ordemConfeccao;
    }

    public void setOrdemConfeccao(int ordemConfeccao) {
        this.ordemConfeccao = ordemConfeccao;
    }

    public int getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(int alternativa) {
        this.alternativa = alternativa;
    }

    public int getRoteiro() {
        return roteiro;
    }

    public void setRoteiro(int roteiro) {
        this.roteiro = roteiro;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getDescEstagio() {
        return descEstagio;
    }

    public void setDescEstagio(String descEstagio) {
        this.descEstagio = descEstagio;
    }

    public EtiquetasDecoracao() {

    }

    public EtiquetasDecoracao(int ordemProducao, int ordemConfeccao, int alternativa, int roteiro, String nivel,
                              String referencia, String tamanho, String cor, int quantidade, int periodo, String descEstagio) {
        this.ordemProducao = ordemProducao;
        this.ordemConfeccao = ordemConfeccao;
        this.alternativa = alternativa;
        this.roteiro = roteiro;
        this.nivel = nivel;
        this.referencia = referencia;
        this.tamanho = tamanho;
        this.cor = cor;
        this.quantidade = quantidade;
        this.periodo = periodo;
        this.descEstagio = descEstagio;
    }
}
