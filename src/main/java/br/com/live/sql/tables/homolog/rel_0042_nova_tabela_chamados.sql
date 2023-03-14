-- Criação da tabela
CREATE TABLE ORION_ADM_010 (
  COD_CHAMADO NUMBER(9) PRIMARY KEY,
  TITULO_CHAMADO VARCHAR2(300),
  COD_REQUERENTE NUMBER(9),
  COD_TECNICO NUMBER(9),
  COD_AREA NUMBER(4),
  COD_DEPARTAMENTO NUMBER(4),
  COD_SETOR NUMBER(4),
  IMPACTO NUMBER(1) default(0),
  DESCRICAO_CHAMADO VARCHAR2(4000),
  DATA_CHAMADO DATE
);

-- Adicionando coluna nome_requerente
ALTER TABLE ORION_ADM_010 ADD nome_requerente VARCHAR2(200);

-- Atualizando valores da coluna nome_requerente a partir da tabela orion_001
UPDATE ORION_ADM_010 oa
SET oa.nome_requerente = (
    SELECT nome FROM orion_001 o
    WHERE o.id = oa.cod_requerente
);

-- Adicionando colunas data_analise, data_entrega_des e data_entrega_usuario
ALTER TABLE ORION_ADM_010 ADD data_analise DATE;
ALTER TABLE ORION_ADM_010 ADD data_entrega_des DATE;
ALTER TABLE ORION_ADM_010 ADD data_entrega_usuario DATE;

-- Excluindo coluna cod_requerente
ALTER TABLE ORION_ADM_010 DROP COLUMN COD_REQUERENTE;