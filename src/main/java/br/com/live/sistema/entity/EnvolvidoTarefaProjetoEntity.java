package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_072")
public class EnvolvidoTarefaProjetoEntity {

    @Id
    private String id;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_projeto")
    private Long idProjeto;

    @Column(name = "id_registro_atividade")
    private Long idRegistroAtividade;

    @Column(name = "id_registro_tarefa_atividade")
    private Long idRegistroTarefaAtividade;

    public EnvolvidoTarefaProjetoEntity() {
    }

    public EnvolvidoTarefaProjetoEntity(String id, Long idUsuario, Long idProjeto, Long idRegistroAtividade, Long idRegistroTarefaAtividade) {
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