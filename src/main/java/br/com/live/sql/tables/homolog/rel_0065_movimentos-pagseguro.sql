CREATE TABLE orion_fin_080 (
    id NUMBER(10) PRIMARY KEY,
    hora_inicial_transacao VARCHAR2(15),
    data_venda_ajuste VARCHAR2(15),
    hora_venda_ajuste VARCHAR2(15),
    tipo_evento VARCHAR2(5),
    tipo_transacao VARCHAR2(5),
    nsu VARCHAR2(255),
    filler1 VARCHAR2(255),
    card_bin VARCHAR2(255),
    card_holder VARCHAR2(255),
    autorizacao VARCHAR2(255),
    cv VARCHAR2(255),
    numero_serie_leitor VARCHAR2(255),
    uso_interno_ps VARCHAR2(255),
    tipo_registro VARCHAR2(5),
    estabelecimento VARCHAR2(50),
    data_inicial_transacao VARCHAR2(15),
    codigo_transacao VARCHAR2(255),
    codigo_venda VARCHAR2(255),
    valor_total_transacao NUMBER(11,2),
    valor_parcela NUMBER(11,2),
    pagamento_prazo VARCHAR2(255),
    plano VARCHAR2(255),
    parcela VARCHAR2(5),
    quantidade_parcela VARCHAR2(5),
    data_movimentacao VARCHAR2(15),
    taxa_parcela_comprador NUMBER(11,2),
    tarifa_boleto_compra NUMBER(11,2),
    valor_original_transacao NUMBER(11,2),
    taxa_parcela_vendedor NUMBER(11,2),
    taxa_intermediacao NUMBER(11,2),
    tarifa_intermediacao NUMBER(11,2),
    tarifa_boleto_vendedor NUMBER(11,2),
    taxa_rep_aplicacao NUMBER(11,2),
    valor_liquido_transacao NUMBER(11,2),
    taxa_antecipacao NUMBER(11,2),
    valor_liquido_antecipacao NUMBER(11,2),
    status_pagamento VARCHAR2(5),
    identificador_revenda VARCHAR2(255),
    meio_pagamento VARCHAR2(255),
    instituicao_financeira VARCHAR2(255),
    canal_entrada VARCHAR2(255),
    leitor VARCHAR2(255),
    meio_captura VARCHAR2(255),
    cod_banco VARCHAR2(255),
    banco_agencia VARCHAR2(255),
    conta_banco VARCHAR2(255),
    num_logico VARCHAR2(255)
);

CREATE TABLE orion_fin_085 (
    ID_ESTABELECIMENTO NUMBER PRIMARY KEY,
    CNPJ_LOJA VARCHAR2(20)
);
