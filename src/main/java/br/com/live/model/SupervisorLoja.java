package br.com.live.model;

public class SupervisorLoja {

    private String cnpjLoja;
    private String cnpjSupervisor;

    public SupervisorLoja(String cnpjLoja, String cnpjSupervisor) {
        this.cnpjLoja = cnpjLoja;
        this.cnpjSupervisor = cnpjSupervisor;
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
