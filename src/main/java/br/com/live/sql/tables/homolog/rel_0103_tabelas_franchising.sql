-- DROP TABLE orion_fin_080

 -- Duplicatas enviadas com sucesso ao Portal da Prefeitura
CREATE TABLE orion_fin_090 (
    numero_lote NUMBER(9),
    num_duplicata VARCHAR(255),
    cnpj_tomador VARCHAR(255),
    data_recebimento DATE,
    protocolo VARCHAR(255),
    num_nfs VARCHAR(255),
    cod_retorno VARCHAR(255),
    descricao_retorno VARCHAR(255)
);

-- Log das duplicatas enviadas, com os dados enviados. Independente se eu certo ou errado, este registro fica gravado
CREATE TABLE orion_fin_095 (
    id NUMBER(9),
    numero_lote NUMBER(9),
    num_duplicata VARCHAR(255),
    cnpj_tomador VARCHAR(255),
    data_envio DATE,
    conteudo_xml CLOB,
    status VARCHAR(500),
    motivo VARCHAR(4000)
);

