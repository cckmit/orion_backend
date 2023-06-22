package br.com.live.administrativo.model;

public class CentroCustoLoja {

    private Long id;
    private String cnpjLoja;
    private int centroCusto;

    public CentroCustoLoja(Long id, String cnpjLoja, int centroCusto) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.centroCusto = centroCusto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public int getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(int centroCusto) {
        this.centroCusto = centroCusto;
    }
}