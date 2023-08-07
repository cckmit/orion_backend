package br.com.live.sistema.model;

public class EnvolvidoTarefaProjeto {

    private String id;
    private Long idUsuario;
    private Long idProjeto;
    private Long idRegistroAtividade;
    private Long idRegistroTarefaAtividade;

    public EnvolvidoTarefaProjeto(){}

    public EnvolvidoTarefaProjeto(String id, Long idUsuario, Long idProjeto, Long idRegistroAtividade, Long idRegistroTarefaAtividade) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
        this.idRegistroAtividade = idRegistroAtividade;
        this.idRegistroTarefaAtividade = idRegistroTarefaAtividade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Long getIdRegistroAtividade() {
        return idRegistroAtividade;
    }

    public void setIdRegistroAtividade(Long idRegistroAtividade) {
        this.idRegistroAtividade = idRegistroAtividade;
    }

    public Long getIdRegistroTarefaAtividade() {
        return idRegistroTarefaAtividade;
    }

    public void setIdRegistroTarefaAtividade(Long idRegistroTarefaAtividade) {
        this.idRegistroTarefaAtividade = idRegistroTarefaAtividade;
    }
}
