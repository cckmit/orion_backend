package br.com.live.sistema.model;

import javax.persistence.Column;
import java.util.Date;

public class Projeto {

    // Projeto
    private Long id;
    private int codProjeto;
    private String descricao;
    private Date dataCriacao;
    private int area;
    private int departamento;
    private int setor;
    private String origemProjeto;
    private String subOrigemProjeto;
    private String status;

    // Briefing
    private String objetivoProjeto;
    private String contextualizacao;
    private String descricaoProblema;
    private String perguntasAberta;
    private String riscosBriefing;

    // Termo Abertura
    private String justificativaProjeto;
    private String objetivoSmart;
    private String beneficio;
    private String restricao;
    private String requisito;
    private String entregavel;
    private String riscoAbertura;

    // Escopo
    private String mvps;
    private String parteAfetada;
    private String sistemaProcessoAfetado;
    private String exclusaoEscopo;

    public Projeto(){}

    public Projeto(Long id, int codProjeto, String descricao, Date dataCriacao, int area, int departamento, int setor, String origemProjeto, String subOrigemProjeto, String status) {
        this.id = id;
        this.codProjeto = codProjeto;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
        this.origemProjeto = origemProjeto;
        this.subOrigemProjeto = subOrigemProjeto;
        this.status = status;
    }

    public Projeto(Long id, int codProjeto, String descricao, Date dataCriacao, int area, int departamento, int setor, String origemProjeto, String subOrigemProjeto, String status, String objetivoProjeto, String contextualizacao, String descricaoProblema, String perguntasAberta, String riscosBriefing, String justificativaProjeto, String objetivoSmart, String beneficio, String restricao, String requisito, String entregavel, String riscoAbertura, String mvps, String parteAfetada, String sistemaProcessoAfetado, String exclusaoEscopo) {
        this.id = id;
        this.codProjeto = codProjeto;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
        this.origemProjeto = origemProjeto;
        this.subOrigemProjeto = subOrigemProjeto;
        this.status = status;
        this.objetivoProjeto = objetivoProjeto;
        this.contextualizacao = contextualizacao;
        this.descricaoProblema = descricaoProblema;
        this.perguntasAberta = perguntasAberta;
        this.riscosBriefing = riscosBriefing;
        this.justificativaProjeto = justificativaProjeto;
        this.objetivoSmart = objetivoSmart;
        this.beneficio = beneficio;
        this.restricao = restricao;
        this.requisito = requisito;
        this.entregavel = entregavel;
        this.riscoAbertura = riscoAbertura;
        this.mvps = mvps;
        this.parteAfetada = parteAfetada;
        this.sistemaProcessoAfetado = sistemaProcessoAfetado;
        this.exclusaoEscopo = exclusaoEscopo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodProjeto() {
        return codProjeto;
    }

    public void setCodProjeto(int codProjeto) {
        this.codProjeto = codProjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public String getOrigemProjeto() {
        return origemProjeto;
    }

    public void setOrigemProjeto(String origemProjeto) {
        this.origemProjeto = origemProjeto;
    }

    public String getSubOrigemProjeto() {
        return subOrigemProjeto;
    }

    public void setSubOrigemProjeto(String subOrigemProjeto) {
        this.subOrigemProjeto = subOrigemProjeto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjetivoProjeto() {
        return objetivoProjeto;
    }

    public void setObjetivoProjeto(String objetivoProjeto) {
        this.objetivoProjeto = objetivoProjeto;
    }

    public String getContextualizacao() {
        return contextualizacao;
    }

    public void setContextualizacao(String contextualizacao) {
        this.contextualizacao = contextualizacao;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getPerguntasAberta() {
        return perguntasAberta;
    }

    public void setPerguntasAberta(String perguntasAberta) {
        this.perguntasAberta = perguntasAberta;
    }

    public String getRiscosBriefing() {
        return riscosBriefing;
    }

    public void setRiscosBriefing(String riscosBriefing) {
        this.riscosBriefing = riscosBriefing;
    }

    public String getJustificativaProjeto() {
        return justificativaProjeto;
    }

    public void setJustificativaProjeto(String justificativaProjeto) {
        this.justificativaProjeto = justificativaProjeto;
    }

    public String getObjetivoSmart() {
        return objetivoSmart;
    }

    public void setObjetivoSmart(String objetivoSmart) {
        this.objetivoSmart = objetivoSmart;
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public String getRestricao() {
        return restricao;
    }

    public void setRestricao(String restricao) {
        this.restricao = restricao;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getEntregavel() {
        return entregavel;
    }

    public void setEntregavel(String entregavel) {
        this.entregavel = entregavel;
    }

    public String getRiscoAbertura() {
        return riscoAbertura;
    }

    public void setRiscoAbertura(String riscoAbertura) {
        this.riscoAbertura = riscoAbertura;
    }

    public String getMvps() {
        return mvps;
    }

    public void setMvps(String mvps) {
        this.mvps = mvps;
    }

    public String getParteAfetada() {
        return parteAfetada;
    }

    public void setParteAfetada(String parteAfetada) {
        this.parteAfetada = parteAfetada;
    }

    public String getSistemaProcessoAfetado() {
        return sistemaProcessoAfetado;
    }

    public void setSistemaProcessoAfetado(String sistemaProcessoAfetado) {
        this.sistemaProcessoAfetado = sistemaProcessoAfetado;
    }

    public String getExclusaoEscopo() {
        return exclusaoEscopo;
    }

    public void setExclusaoEscopo(String exclusaoEscopo) {
        this.exclusaoEscopo = exclusaoEscopo;
    }
}
