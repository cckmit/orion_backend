package br.com.live.sistema.model;

public class AprovadorProjeto {

    private String id;
    private long idProjeto;
    private long idUsuario;

    public AprovadorProjeto(String id, long idProjeto, long idUsuario) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.idUsuario = idUsuario;
    }

    public AprovadorProjeto(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
