package br.com.live.integracao.cigam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaRetornoApiDuplicatas {

    public int portal;
    public String loja;
    public String cnpj_emp;
    public int cod_cliente;
    public int numorcamento;
    public String identificador;
    public String codpgto;
    public String forma_pgto;
    public int qtde_parcelas;
    public int ordem_parcela;
    public String data_emissao;
    public String data_vencimento;
    public String data_baixa;
    public double valor_fatura;
    public double valor_pago;
    public double valor_desconto;
    public double valor_juros;
    public int documento;
    public String nsu;
    public String cod_autorizacao;

    public ListaRetornoApiDuplicatas() {

    }

    public ListaRetornoApiDuplicatas(int portal, String loja, String cnpj_emp, int cod_cliente, int numorcamento,
                                          String identificador, String codpgto, String forma_pgto, int qtde_parcelas, int ordem_parcela,
                                          String data_emissao, String data_vencimento, String data_baixa, double valor_fatura, double valor_pago,
                                          double valor_desconto, double valor_juros, int documento, String nsu, String cod_autorizacao) {
        this.portal = portal;
        this.loja = loja;
        this.cnpj_emp = cnpj_emp;
        this.cod_cliente = cod_cliente;
        this.numorcamento = numorcamento;
        this.identificador = identificador;
        this.codpgto = codpgto;
        this.forma_pgto = forma_pgto;
        this.qtde_parcelas = qtde_parcelas;
        this.ordem_parcela = ordem_parcela;
        this.data_emissao = data_emissao;
        this.data_vencimento = data_vencimento;
        this.data_baixa = data_baixa;
        this.valor_fatura = valor_fatura;
        this.valor_pago = valor_pago;
        this.valor_desconto = valor_desconto;
        this.valor_juros = valor_juros;
        this.documento = documento;
        this.nsu = nsu;
        this.cod_autorizacao = cod_autorizacao;
    }
}
