package br.com.live.sistema.model;

public class PessoaEnvolvidaProjeto {

    private long id;
    private long idProjeto;
    private long idUsuario;
    private String solicitanteStakeholder;
    private long idFuncaoPessoa;
    private int area;
    private int departamento;
    private int setor;

    public PessoaEnvolvidaProjeto(long id, long idProjeto, long idUsuario, String solicitanteStakeholder, long idFuncaoPessoa, int area, int departamento, int setor) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.idUsuario = idUsuario;
        this.solicitanteStakeholder = solicitanteStakeholder;
        this.idFuncaoPessoa = idFuncaoPessoa;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
    }

    public PessoaEnvolvidaProjeto(){}

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
