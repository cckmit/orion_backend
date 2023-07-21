package br.com.live.integracao.cigam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaRetornoApiEstoque {

    public String loja;
    public String cnpj;
    public String codbarra;
    public String ean;
    public int qtdestoque;

    public ListaRetornoApiEstoque() {}

    public ListaRetornoApiEstoque(String loja, String cnpj, String codbarra, String ean, int qtdestoque) {
        this.loja = loja;
        this.cnpj = cnpj;
        this.codbarra = codbarra;
        this.ean = ean;
        this.qtdestoque = qtdestoque;
    }
}