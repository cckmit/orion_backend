package br.com.live.entity;

import javax.persistence.*;

@Entity
@Table(name="orion_fin_005")
public class CentroCustoLoja {

    @Id
    public Long id;

    @Column(name = "cnpj_loja")
    public String cnpjLoja;

    @Column(name = "centro_custo")
    public int centroCusto;

    public CentroCustoLoja(Long id, String cnpjLoja, int centroCusto) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.centroCusto = centroCusto;
    }

    public CentroCustoLoja(){
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
