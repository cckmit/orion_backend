package br.com.live.sistema.model;

public class TarefaAtividadeProjeto {

    public long id;
    public long idProjeto;
    public long idAtividade;
    public String descricao;
    public double tempoPrevisto;

    public TarefaAtividadeProjeto(){
    }

    public TarefaAtividadeProjeto(long id, long idProjeto, long idAtividade, String descricao, double tempoPrevisto) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.idAtividade = idAtividade;
        this.descricao = descricao;
        this.tempoPrevisto = tempoPrevisto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(long idAtividade) {
        this.idAtividade = idAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(double tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }
}
