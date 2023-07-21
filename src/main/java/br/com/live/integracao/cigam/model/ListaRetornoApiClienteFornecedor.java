package br.com.live.integracao.cigam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaRetornoApiClienteFornecedor {
    public int portal;
    public int cod_cliente;
    public String razao_cliente;
    public String nome_cliente;
    public String doc_cliente;
    public String tipo_cliente;
    public String endereco_cliente;
    public String numero_rua_cliente;
    public String complement_end_cli;
    public String bairro_cliente;
    public String cep_cliente;
    public String cidade_cliente;
    public String uf_cliente;
    public String pais;
    public String fone_cliente;
    public String email_cliente;
    public String sexo;
    public String data_cadastro;
    public String data_nascimento;
    public String cel_cliente;
    public String ativo;
    public String dt_update;
    public String inscricao_estadual;
    public String incricao_municipal;
    public String identidade_cliente;
    public String cartao_fidelidade;
    public String cod_ibge_municipio;
    public String classe_cliente;
    public String matricula_conveniado;
    public String tipo_cadastro;
    public int id_estado_civil;
    public String fax_cliente;
    public String site_cliente;

    public ListaRetornoApiClienteFornecedor() {

    }

    public ListaRetornoApiClienteFornecedor(int portal, int cod_cliente, String razao_cliente, String nome_cliente,
                                                 String doc_cliente, String tipo_cliente, String endereco_cliente, String numero_rua_cliente,
                                                 String complement_end_cli, String bairro_cliente, String cep_cliente, String cidade_cliente,
                                                 String uf_cliente, String pais, String fone_cliente, String email_cliente, String sexo,
                                                 String data_cadastro, String data_nascimento, String cel_cliente, String ativo, String dt_update,
                                                 String inscricao_estadual, String incricao_municipal, String identidade_cliente, String cartao_fidelidade,
                                                 String cod_ibge_municipio, String classe_cliente, String matricula_conveniado, String tipo_cadastro,
                                                 int id_estado_civil, String fax_cliente, String site_cliente) {
        this.portal = portal;
        this.cod_cliente = cod_cliente;
        this.razao_cliente = razao_cliente;
        this.nome_cliente = nome_cliente;
        this.doc_cliente = doc_cliente;
        this.tipo_cliente = tipo_cliente;
        this.endereco_cliente = endereco_cliente;
        this.numero_rua_cliente = numero_rua_cliente;
        this.complement_end_cli = complement_end_cli;
        this.bairro_cliente = bairro_cliente;
        this.cep_cliente = cep_cliente;
        this.cidade_cliente = cidade_cliente;
        this.uf_cliente = uf_cliente;
        this.pais = pais;
        this.fone_cliente = fone_cliente;
        this.email_cliente = email_cliente;
        this.sexo = sexo;
        this.data_cadastro = data_cadastro;
        this.data_nascimento = data_nascimento;
        this.cel_cliente = cel_cliente;
        this.ativo = ativo;
        this.dt_update = dt_update;
        this.inscricao_estadual = inscricao_estadual;
        this.incricao_municipal = incricao_municipal;
        this.identidade_cliente = identidade_cliente;
        this.cartao_fidelidade = cartao_fidelidade;
        this.cod_ibge_municipio = cod_ibge_municipio;
        this.classe_cliente = classe_cliente;
        this.matricula_conveniado = matricula_conveniado;
        this.tipo_cadastro = tipo_cadastro;
        this.id_estado_civil = id_estado_civil;
        this.fax_cliente = fax_cliente;
        this.site_cliente = site_cliente;
    }
}
