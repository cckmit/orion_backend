package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_060")
public class AprovadorProjetoEntity {

    @Id
    private String id;

    @Column(name="id_usuario")
    private long idUsuario;

    @Column(name="id_projeto")
    private long idProjeto;

    public AprovadorProjetoEntity(String id, long idUsuario, long idProjeto) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
    }

    public AprovadorProjetoEntity(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }
}
