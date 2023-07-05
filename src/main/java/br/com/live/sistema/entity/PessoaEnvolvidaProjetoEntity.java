package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_055")
public class PessoaEnvolvidaProjetoEntity {

    @Id
    private long id;

    @Column(name="id_projeto")
    private long idProjeto;

    @Column(name="id_usuario")
    private long idUsuario;

    @Column(name="solicitante_stakeholder")
    private String solicitanteStakeholder;

    @Column(name="id_funcao_pessoa")
    private long idFuncaoPessoa;

    private int area;
    private int departamento;
    private int setor;

    public PessoaEnvolvidaProjetoEntity(long id, long idProjeto, long idUsuario, String solicitanteStakeholder, long idFuncaoPessoa, int area, int departamento, int setor) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.idUsuario = idUsuario;
        this.solicitanteStakeholder = solicitanteStakeholder;
        this.idFuncaoPessoa = idFuncaoPessoa;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
    }

    public PessoaEnvolvidaProjetoEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getSolicitanteStakeholder() {
        return solicitanteStakeholder;
    }

    public void setSolicitanteStakeholder(String solicitanteStakeholder) {
        this.solicitanteStakeholder = solicitanteStakeholder;
    }

    public long getIdFuncaoPessoa() {
        return idFuncaoPessoa;
    }

    public void setIdFuncaoPessoa(long idFuncaoPessoa) {
        this.idFuncaoPessoa = idFuncaoPessoa;
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
}
