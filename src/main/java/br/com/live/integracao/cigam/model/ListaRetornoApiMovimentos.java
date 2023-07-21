package br.com.live.integracao.cigam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaRetornoApiMovimentos {

    public int portal;
    public String loja;
    public String cnpj_emp;
    public int numorcamento;
    public String identificador;
    public int usuario;
    public int numnf;
    public String serie;
    public String chave_nf;
    public String protocolo_aut_nfe;
    public int nrecf;
    public String modelo_nf;
    public String data_lancamento;
    public String hora_lancamento;
    public int codigo_cliente;
    public String doc_cliente;
    public String id_cfop;
    public int cod_vendedor;
    public String cod_barra;
    public float qtde;
    public float preco_unitario;
    public float valor_liquido;
    public float desconto;
    public float preco_custo;
    public String cst_icms;
    public String cst_pis;
    public String cst_cofins;
    public String cst_ipi;
    public float valor_icms;
    public float aliquota_icms;
    public float base_icms;
    public float valor_pis;
    public float aliquota_pis;
    public float base_pis;
    public float valor_cofins;
    public float aliquota_cofins;
    public float base_cofins;
    public float valor_icms_st;
    public float aliquota_icms_st;
    public float base_icms_st;
    public float valor_ipi;
    public float aliquota_ipi;
    public float base_ipi;
    public String operacao;
    public String tipo_transacao;
    public String cancelado;
    public String datcancel;
    public String excluido;
    public int seqitem;
    public int cod_sefaz_situacao;

    public ListaRetornoApiMovimentos() {

    }

    public ListaRetornoApiMovimentos(int portal, String loja, String cnpj_emp, int numorcamento,
                                          String identificador, int usuario, int numnf, String serie, String chave_nf, String protocolo_aut_nfe,
                                          int nrecf, String modelo_nf, String data_lancamento, String hora_lancamento, int codigo_cliente,
                                          String doc_cliente, String id_cfop, int cod_vendedor, String cod_barra, float qtde, float preco_unitario,
                                          float valor_liquido, float desconto, float preco_custo, String cst_icms, String cst_pis, String cst_cofins,
                                          String cst_ipi, float valor_icms, float aliquota_icms, float base_icms, float valor_pis, float aliquota_pis,
                                          float base_pis, float valor_cofins, float aliquota_cofins, float base_cofins, float valor_icms_st,
                                          float aliquota_icms_st, float base_icms_st, float valor_ipi, float aliquota_ipi, float base_ipi,
                                          String operacao, String tipo_transacao, String cancelado, String datcancel, String excluido, int seqitem, int cod_sefaz_situacao) {
        this.portal = portal;
        this.loja = loja;
        this.cnpj_emp = cnpj_emp;
        this.numorcamento = numorcamento;
        this.identificador = identificador;
        this.usuario = usuario;
        this.numnf = numnf;
        this.serie = serie;
        this.chave_nf = chave_nf;
        this.protocolo_aut_nfe = protocolo_aut_nfe;
        this.nrecf = nrecf;
        this.modelo_nf = modelo_nf;
        this.data_lancamento = data_lancamento;
        this.hora_lancamento = hora_lancamento;
        this.codigo_cliente = codigo_cliente;
        this.doc_cliente = doc_cliente;
        this.id_cfop = id_cfop;
        this.cod_vendedor = cod_vendedor;
        this.cod_barra = cod_barra;
        this.qtde = qtde;
        this.preco_unitario = preco_unitario;
        this.valor_liquido = valor_liquido;
        this.desconto = desconto;
        this.preco_custo = preco_custo;
        this.cst_icms = cst_icms;
        this.cst_pis = cst_pis;
        this.cst_cofins = cst_cofins;
        this.cst_ipi = cst_ipi;
        this.valor_icms = valor_icms;
        this.aliquota_icms = aliquota_icms;
        this.base_icms = base_icms;
        this.valor_pis = valor_pis;
        this.aliquota_pis = aliquota_pis;
        this.base_pis = base_pis;
        this.valor_cofins = valor_cofins;
        this.aliquota_cofins = aliquota_cofins;
        this.base_cofins = base_cofins;
        this.valor_icms_st = valor_icms_st;
        this.aliquota_icms_st = aliquota_icms_st;
        this.base_icms_st = base_icms_st;
        this.valor_ipi = valor_ipi;
        this.aliquota_ipi = aliquota_ipi;
        this.base_ipi = base_ipi;
        this.operacao = operacao;
        this.tipo_transacao = tipo_transacao;
        this.cancelado = cancelado;
        this.datcancel = datcancel;
        this.excluido = excluido;
        this.seqitem = seqitem;
        this.cod_sefaz_situacao = cod_sefaz_situacao;
    }
}
