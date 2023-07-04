package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_ti_046")
public class TarefaConcluidaAtividadeProjetoEntity {

    @Id
    private Long id;

    @Column(name = "id_atividade")
    private Long idAtividade;

    @Column(name = "id_tarefa_tipo_atividade")
    private Long idTarefaTipoAtividade;

    public TarefaConcluidaAtividadeProjetoEntity(Long id, Long idAtividade, Long idTarefaTipoAtividade) {
        this.id = id;
        this.idAtividade = idAtividade;
        this.idTarefaTipoAtividade = idTarefaTipoAtividade;
    }

    public TarefaConcluidaAtividadeProjetoEntity(){

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
