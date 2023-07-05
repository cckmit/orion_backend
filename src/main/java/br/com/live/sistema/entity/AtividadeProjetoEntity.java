package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orion_ti_045")
public class AtividadeProjetoEntity {

    @Id
    private Long id;
    private String descricao;

    @Column(name = "id_projeto")
    private Long idProjeto;

    @Column(name = "id_fase")
    private Long idFase;

    @Column(name = "id_tipo_atividade")
    private Long idTipoAtividade;

    @Column(name = "id_responsavel")
    private Long idResponsavel;

    @Column(name = "data_prev_inicio")
    private Date dataPrevInicio;

    @Column(name = "data_prev_fim")
    private Date dataPrevFim;

    @Column(name = "tempo_previsto")
    private int tempoPrevisto;

    public AtividadeProjetoEntity(Long id, String descricao, Long idProjeto, Long idFase, Long idTipoAtividade, Long idResponsavel, Date dataPrevInicio, Date dataPrevFim, int tempoPrevisto) {
        this.id = id;
        this.descricao = descricao;
        this.idProjeto = idProjeto;
        this.idFase = idFase;
        this.idTipoAtividade = idTipoAtividade;
        this.idResponsavel = idResponsavel;
        this.dataPrevInicio = dataPrevInicio;
        this.dataPrevFim = dataPrevFim;
        this.tempoPrevisto = tempoPrevisto;
    }

    public AtividadeProjetoEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Long getIdFase() {
        return idFase;
    }

    public void setIdFase(Long idFase) {
        this.idFase = idFase;
    }

    public Long getIdTipoAtividade() {
        return idTipoAtividade;
    }

    public void setIdTipoAtividade(Long idTipoAtividade) {
        this.idTipoAtividade = idTipoAtividade;
    }

    public long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Date getDataPrevInicio() {
        return dataPrevInicio;
    }

    public void setDataPrevInicio(Date dataPrevInicio) {
        this.dataPrevInicio = dataPrevInicio;
    }

    public Date getDataPrevFim() {
        return dataPrevFim;
    }

    public void setDataPrevFim(Date dataPrevFim) {
        this.dataPrevFim = dataPrevFim;
    }

    public int getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(int tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }
}
