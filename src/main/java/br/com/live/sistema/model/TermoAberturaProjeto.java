package br.com.live.sistema.model;

public class TermoAberturaProjeto {
    private long idProjeto;
    private String justificativaProjeto;
    private String objetivoSmart;
    private String beneficio;
    private String restricao;
    private String requisito;
    private String entregavel;
    private String riscoAbertura;

    public TermoAberturaProjeto(long idProjeto, String justificativaProjeto, String objetivoSmart, String beneficio, String restricao, String requisito, String entregavel, String riscoAbertura) {
        this.idProjeto = idProjeto;
        this.justificativaProjeto = justificativaProjeto;
        this.objetivoSmart = objetivoSmart;
        this.beneficio = beneficio;
        this.restricao = restricao;
        this.requisito = requisito;
        this.entregavel = entregavel;
        this.riscoAbertura = riscoAbertura;
    }

    public TermoAberturaProjeto(){}

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
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
}
