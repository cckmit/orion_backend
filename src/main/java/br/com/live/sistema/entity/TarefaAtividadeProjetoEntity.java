package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_046")
public class TarefaAtividadeProjetoEntity {

    @Id
    public Long id;

    @Column(name="id_projeto")
    public Long idProjeto;

    @Column(name="id_atividade")
    public Long idAtividade;

    public String descricao;

    @Column(name="tempo_previsto")
    public double tempoPrevisto;

    public TarefaAtividadeProjetoEntity(Long id, Long idProjeto, Long idAtividade, String descricao, double tempoPrevisto) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.idAtividade = idAtividade;
        this.descricao = descricao;
        this.tempoPrevisto = tempoPrevisto;
    }

    public TarefaAtividadeProjetoEntity(){
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

    public Long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Long idAtividade) {
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
