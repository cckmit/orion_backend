package br.com.live.sistema.model;

public class PlanoNegocio {

    public long id;
    public String titulo;
    public String objetivo;
    public int area;
    public int departamento;
    public int setor;
    public String responsavel;
    public int situacao;

    public PlanoNegocio(long id, String titulo, String objetivo, int area, int departamento, int setor, String responsavel, int situacao) {
        this.id = id;
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
        this.responsavel = responsavel;
        this.situacao = situacao;
    }

    public PlanoNegocio(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
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

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }
}
