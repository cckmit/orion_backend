package br.com.live.sistema.model;

public class TarefaConcluidaAtividadeProjeto {

    private Long id;
    private Long idAtividade;
    private Long idTarefaTipoAtividade;

    public TarefaConcluidaAtividadeProjeto(Long id, Long idAtividade, Long idTarefaTipoAtividade) {
        this.id = id;
        this.idAtividade = idAtividade;
        this.idTarefaTipoAtividade = idTarefaTipoAtividade;
    }

    public TarefaConcluidaAtividadeProjeto(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Long idAtividade) {
        this.idAtividade = idAtividade;
    }

    public Long getIdTarefaTipoAtividade() {
        return idTarefaTipoAtividade;
    }

    public void setIdTarefaTipoAtividade(Long idTarefaTipoAtividade) {
        this.idTarefaTipoAtividade = idTarefaTipoAtividade;
    }
}