package br.com.live.integracao.cigam.model;

public class ListaRetornoApiFuncionarios {

    public int cod_vendedor;
    public String cpf_vendedor;
    public String nom_vendedor;
    public String cargo;
    public String end_usuario;
    public String loja;
    public String cnpj;
    public String status;
    public String datademissao;
    public String dataadmissao;

    public ListaRetornoApiFuncionarios() {

    }

    public ListaRetornoApiFuncionarios(int cod_vendedor, String cpf_vendedor, String nom_vendedor, String cargo, String end_usuario, String loja, String cnpj, String status,
                                            String datademissao, String dataadmissao) {
        this.cod_vendedor = cod_vendedor;
        this.cpf_vendedor = cpf_vendedor;
        this.nom_vendedor = nom_vendedor;
        this.cargo = cargo;
        this.end_usuario = end_usuario;
        this.loja = loja;
        this.cnpj = cnpj;
        this.status = status;
        this.datademissao = datademissao;
        this.dataadmissao = dataadmissao;
    }
}

