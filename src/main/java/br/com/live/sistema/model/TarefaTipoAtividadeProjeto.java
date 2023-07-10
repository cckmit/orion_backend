package br.com.live.sistema.model;

public class TarefaTipoAtividadeProjeto {

    private Long id;
    private Long idTipoAtividade;
    private String descricao;
    private int ordenacao;
    private double tempoEstimado;

    public TarefaTipoAtividadeProjeto(Long id, Long idTipoAtividade, String descricao, int ordenacao, double tempoEstimado) {
        this.id = id;
        this.idTipoAtividade = idTipoAtividade;
        this.descricao = descricao;
        this.ordenacao = ordenacao;
        this.tempoEstimado = tempoEstimado;
    }

    public TarefaTipoAtividadeProjeto(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoAtividade() {
        return idTipoAtividade;
    }

    public void setIdTipoAtividade(Long idTipoAtividade) {
        this.idTipoAtividade = idTipoAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(int ordenacao) {
        this.ordenacao = ordenacao;
    }

    public double getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(double tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }
}
