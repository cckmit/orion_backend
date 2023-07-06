package br.com.live.sistema.model;

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

    public Projeto(Long id, int codProjeto, String descricao, Date dataCriacao, int area, int departamento, int setor, String origemProjeto, String subOrigemProjeto, String status, String objetivoProjeto, String contextualizacao, String descricaoProblema, String perguntasAberta, String riscosBriefing) {
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
    }

    public Projeto(){

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
}
