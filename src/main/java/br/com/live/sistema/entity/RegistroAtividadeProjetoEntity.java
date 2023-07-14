package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="orion_ti_070")
public class RegistroAtividadeProjetoEntity {

    @Id
    private Long id;

    @Column(name="id_projeto")
    private Long idProjeto;

    private String descricao;

    @Column(name="acao_realizada")
    private String acaoRealizada;

    @Column(name="id_responsavel")
    private Long idResponsavel;

    @Column(name="data_inicio")
    private Date dataInicio;

    @Column(name="hora_inicio")
    private Date horaInicio;

    @Column(name="data_fim")
    private Date dataFim;

    @Column(name="hora_fim")
    private Date horaFim;

    @Column(name="documento_associado")
    private String documentoAssociado;

    private double custo;

    @Column(name="id_fase")
    private Long idFase;

    @Column(name="id_atividade")
    private Long idAtividade;

    @Column(name="tempo_gasto")
    private double tempoGasto;

    public RegistroAtividadeProjetoEntity(){}

    public RegistroAtividadeProjetoEntity(Long id, Long idProjeto, String descricao, String acaoRealizada, Long idResponsavel, Date dataInicio, Date horaInicio, Date dataFim, Date horaFim, String documentoAssociado, double custo, Long idFase, Long idAtividade, double tempoGasto) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.descricao = descricao;
        this.acaoRealizada = acaoRealizada;
        this.idResponsavel = idResponsavel;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.dataFim = dataFim;
        this.horaFim = horaFim;
        this.documentoAssociado = documentoAssociado;
        this.custo = custo;
        this.idFase = idFase;
        this.idAtividade = idAtividade;
        this.tempoGasto = tempoGasto;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAcaoRealizada() {
        return acaoRealizada;
    }

    public void setAcaoRealizada(String acaoRealizada) {
        this.acaoRealizada = acaoRealizada;
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public String getDocumentoAssociado() {
        return documentoAssociado;
    }

    public void setDocumentoAssociado(String documentoAssociado) {
        this.documentoAssociado = documentoAssociado;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public Long getIdFase() {
        return idFase;
    }

    public void setIdFase(Long idFase) {
        this.idFase = idFase;
    }

    public Long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Long idAtividade) {
        this.idAtividade = idAtividade;
    }

    public double getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(double tempoGasto) {
        this.tempoGasto = tempoGasto;
    }
}
