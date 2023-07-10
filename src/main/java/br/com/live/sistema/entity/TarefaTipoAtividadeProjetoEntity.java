package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_036")
public class TarefaTipoAtividadeProjetoEntity {

    @Id
    private Long id;

    @Column(name="id_tipo_atividade")
    private Long idTipoAtividade;

    private String descricao;
    private int ordenacao;

    @Column(name="tempo_estimado")
    private double tempoEstimado;

    public TarefaTipoAtividadeProjetoEntity(Long id, Long idTipoAtividade, String descricao, int ordenacao, double tempoEstimado) {
        this.id = id;
        this.idTipoAtividade = idTipoAtividade;
        this.descricao = descricao;
        this.ordenacao = ordenacao;
        this.tempoEstimado = tempoEstimado;
    }

    public TarefaTipoAtividadeProjetoEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(int ordenacao) {
        this.ordenacao = ordenacao;
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

    public double getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(double tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }
}
