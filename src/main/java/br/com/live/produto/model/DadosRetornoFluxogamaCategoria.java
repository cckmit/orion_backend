package br.com.live.produto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosRetornoFluxogamaCategoria {

    @JsonProperty("tabela.48.433.ds")
    private String categoria1;
    @JsonProperty("tabela.48.434.ds")
    private String categoria2;
    @JsonProperty("tabela.48.435.ds")
    private String categoria3;
    @JsonProperty("tabela.48.436.ds")
    private String categoria4;
    @JsonProperty("tabela.48.437.ds")
    private String categoria5;

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getCategoria3() {
        return categoria3;
    }

    public void setCategoria3(String categoria3) {
        this.categoria3 = categoria3;
    }

    public String getCategoria4() {
        return categoria4;
    }

    public void setCategoria4(String categoria4) {
        this.categoria4 = categoria4;
    }

    public String getCategoria5() {
        return categoria5;
    }

    public void setCategoria5(String categoria5) {
        this.categoria5 = categoria5;
    }

    public DadosRetornoFluxogamaCategoria() {

    }

    public DadosRetornoFluxogamaCategoria(String categoria1, String categoria2, String categoria3, String categoria4, String categoria5) {
        this.categoria1 = categoria1;
        this.categoria2 = categoria2;
        this.categoria3 = categoria3;
        this.categoria4 = categoria4;
        this.categoria5 = categoria5;
    }
}
