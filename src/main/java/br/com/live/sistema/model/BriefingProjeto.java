package br.com.live.sistema.model;

public class BriefingProjeto {
    private long idProjeto;
    private String objetivoProjeto;
    private String contextualizacao;
    private String descricaoProblema;
    private String perguntasAberta;
    private String riscosBriefing;

    public BriefingProjeto(long idProjeto, String objetivoProjeto, String contextualizacao, String descricaoProblema, String perguntasAberta, String riscosBriefing) {
        this.idProjeto = idProjeto;
        this.objetivoProjeto = objetivoProjeto;
        this.contextualizacao = contextualizacao;
        this.descricaoProblema = descricaoProblema;
        this.perguntasAberta = perguntasAberta;
        this.riscosBriefing = riscosBriefing;
    }

    public BriefingProjeto(){};

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
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
