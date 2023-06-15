package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_085")
public class EstabelecimentoPagSeguroLojaEntity {

    @Id
    @Column(name = "id_estabelecimento")
    private Long idEstabelecimento;

    @Column(name = "cnpj_loja")
    private String cnpjLoja;

    public EstabelecimentoPagSeguroLojaEntity(Long idEstabelecimento, String cnpjLoja) {
        this.idEstabelecimento = idEstabelecimento;
        this.cnpjLoja = cnpjLoja;
    }

    public EstabelecimentoPagSeguroLojaEntity(){
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
