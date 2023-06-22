package br.com.live.administrativo.model;

public class EstabelecimentoPagSeguroLoja {

    private Long idEstabelecimento;
    private String cnpjLoja;

    public EstabelecimentoPagSeguroLoja(Long idEstabelecimento, String cnpjLoja) {
        this.idEstabelecimento = idEstabelecimento;
        this.cnpjLoja = cnpjLoja;
    }

    public EstabelecimentoPagSeguroLoja(){

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
}
