package br.com.live.administrativo.model;

public class TomadorNfsFranchising {

    private String fantasiaCliente;
    private String enderecoCliente;
    private int numeroImovel;
    private String complemento;
    private String bairro;
    private int codCidadeIbge;
    private String estado;
    private String cepCliente;
    private String telefoneCliente;
    private String email;

    public TomadorNfsFranchising(String fantasiaCliente, String enderecoCliente, int numeroImovel, String complemento, String bairro, int codCidadeIbge, String estado, String cepCliente, String telefoneCliente, String email) {
        this.fantasiaCliente = fantasiaCliente;
        this.enderecoCliente = enderecoCliente;
        this.numeroImovel = numeroImovel;
        this.complemento = complemento;
        this.bairro = bairro;
        this.codCidadeIbge = codCidadeIbge;
        this.estado = estado;
        this.cepCliente = cepCliente;
        this.telefoneCliente = telefoneCliente;
        this.email = email;
    }

    public TomadorNfsFranchising(){}

    public String getFantasiaCliente() {
        return fantasiaCliente;
    }

    public void setFantasiaCliente(String fantasiaCliente) {
        this.fantasiaCliente = fantasiaCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    public int getNumeroImovel() {
        return numeroImovel;
    }

    public void setNumeroImovel(int numeroImovel) {
        this.numeroImovel = numeroImovel;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getCodCidadeIbge() {
        return codCidadeIbge;
    }

    public void setCodCidadeIbge(int codCidadeIbge) {
        this.codCidadeIbge = codCidadeIbge;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCepCliente() {
        return cepCliente;
    }

    public void setCepCliente(String cepCliente) {
        this.cepCliente = cepCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

