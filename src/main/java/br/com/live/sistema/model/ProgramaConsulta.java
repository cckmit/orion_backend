package br.com.live.sistema.model;

public class ProgramaConsulta {

    public long id;
    public String descricao;
    public String modulo;

    public ProgramaConsulta(long id, String descricao, String modulo) {
        this.id = id;
        this.descricao = descricao;
        this.modulo = modulo;
    }

    public ProgramaConsulta(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
}
