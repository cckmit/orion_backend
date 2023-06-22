package br.com.live.administrativo.model;

public class ConsultaOrcamentoLojaDre {

    String cnpjLoja;
    String nomeLoja;
    int anoOrcamento;

    public ConsultaOrcamentoLojaDre(String cnpjLoja, String nomeLoja, int anoOrcamento) {
        this.cnpjLoja = cnpjLoja;
        this.nomeLoja = nomeLoja;
        this.anoOrcamento = anoOrcamento;
    }

    public ConsultaOrcamentoLojaDre(){
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

    public int getAnoOrcamento() {
        return anoOrcamento;
    }

    public void setAnoOrcamento(int anoOrcamento) {
        this.anoOrcamento = anoOrcamento;
    }
}
