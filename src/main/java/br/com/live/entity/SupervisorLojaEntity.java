package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_001")
public class SupervisorLojaEntity {
    @Id
    @Column(name = "cnpj_loja")
    public String cnpjLoja;

    @Column(name = "cnpj_supervisor")
    public String cnpjSupervisor;

    public SupervisorLojaEntity(String cnpjLoja, String cnpjSupervisor) {
        this.cnpjLoja = cnpjLoja;
        this.cnpjSupervisor = cnpjSupervisor;
    }

    public SupervisorLojaEntity(){
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public String getCnpjSupervisor() {
        return cnpjSupervisor;
    }

    public void setCnpjSupervisor(String cnpjSupervisor) {
        this.cnpjSupervisor = cnpjSupervisor;
    }
}
