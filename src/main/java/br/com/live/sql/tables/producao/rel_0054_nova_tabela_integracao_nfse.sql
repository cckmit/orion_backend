CREATE TABLE orion_integ_nfservico_001 (
    id NUMBER PRIMARY KEY,
    cod_empresa NUMBER,
    cnpj_cliente VARCHAR2(20),
    num_duplicata NUMBER,
    data_emissao_duplicata DATE,
    data_hora_integracao TIMESTAMP,
    situacao_integracao VARCHAR2(50),
    descr_integracao VARCHAR2(1000),
    complemento VARCHAR2(1000),
    seq_duplicata NUMBER,
    data_venc_duplicata DATE,
    valor_duplicata NUMBER(15, 2)
);

-- Insere usuário das notificações
INSERT INTO orion_005(id, ID_USUARIO, TIPO_NOTIFICACAO) values ('248-3', 248, 3)