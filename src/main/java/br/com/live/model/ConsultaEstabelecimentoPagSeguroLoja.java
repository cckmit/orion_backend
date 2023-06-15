package br.com.live.model;

public class ConsultaEstabelecimentoPagSeguroLoja {

    private Long idEstabelecimento;
    private String cnpjLoja;
    private String nomeLoja;

    public ConsultaEstabelecimentoPagSeguroLoja(Long idEstabelecimento, String cnpjLoja, String nomeLoja) {
        this.idEstabelecimento = idEstabelecimento;
        this.cnpjLoja = cnpjLoja;
        this.nomeLoja = nomeLoja;
    }

    public ConsultaEstabelecimentoPagSeguroLoja(){
    }

    public Long getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public void setIdEstabelecimento(Long idEstabelecimento) {
        this.idEstabelecimento = idEstabelecimento;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }
}