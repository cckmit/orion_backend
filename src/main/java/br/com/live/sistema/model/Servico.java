package br.com.live.sistema.model;

public class Servico {

    private int id;
    private String nomeServico;
    private String objetivo;
    private String timeResponsavel;
    private boolean disponibilidade;
    private String tecnicosFornecedores;

    public Servico(int id, String nomeServico, String objetivo, String timeResponsavel, boolean disponibilidade, String tecnicosFornecedores) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.objetivo = objetivo;
        this.timeResponsavel = timeResponsavel;
        this.disponibilidade = disponibilidade;
        this.tecnicosFornecedores = tecnicosFornecedores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getTimeResponsavel() {
        return timeResponsavel;
    }

    public void setTimeResponsavel(String timeResponsavel) {
        this.timeResponsavel = timeResponsavel;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getTecnicosFornecedores() {
        return tecnicosFornecedores;
    }

    public void setTecnicosFornecedores(String tecnicosFornecedores) {
        this.tecnicosFornecedores = tecnicosFornecedores;
    }
}
