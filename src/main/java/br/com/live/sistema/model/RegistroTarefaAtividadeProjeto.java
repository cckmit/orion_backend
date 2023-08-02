package br.com.live.sistema.model;

import java.util.Date;

public class RegistroTarefaAtividadeProjeto {

    private Long id;
    private Long idProjeto;
    private Long idRegistroAtividade;
    private String descricao;
    private String acaoRealizada;
    private Long idResponsavel;
    private Date dataInicio;
    private Date horaInicio;
    private Date dataFim;
    private Date horaFim;
    private String documentoAssociado;
    private double custo;
    private double tempoGasto;
    private long idTarefaAtividade;

    public RegistroTarefaAtividadeProjeto(){}

    public RegistroTarefaAtividadeProjeto(Long id, Long idProjeto, Long idRegistroAtividade, String descricao, String acaoRealizada, Long idResponsavel, Date dataInicio, Date horaInicio, Date dataFim, Date horaFim, String documentoAssociado, double custo, double tempoGasto, long idTarefaAtividade) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.idRegistroAtividade = idRegistroAtividade;
        this.descricao = descricao;
        this.acaoRealizada = acaoRealizada;
        this.idResponsavel = idResponsavel;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.dataFim = dataFim;
        this.horaFim = horaFim;
        this.documentoAssociado = documentoAssociado;
        this.custo = custo;
        this.tempoGasto = tempoGasto;
        this.idTarefaAtividade = idTarefaAtividade;
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

    public Long getIdRegistroAtividade() {
        return idRegistroAtividade;
    }

    public void setIdRegistroAtividade(Long idRegistroAtividade) {
        this.idRegistroAtividade = idRegistroAtividade;
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

    public double getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(double tempoGasto) {
        this.tempoGasto = tempoGasto;
    }

    public long getIdTarefaAtividade() {
        return idTarefaAtividade;
    }

    public void setIdTarefaAtividade(long idTarefaAtividade) {
        this.idTarefaAtividade = idTarefaAtividade;
    }
}
