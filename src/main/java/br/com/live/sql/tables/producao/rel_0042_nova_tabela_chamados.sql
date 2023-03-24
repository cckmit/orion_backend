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


-------------------------- EXECUTAR AO LIBERAR A DEMANDA DAS DATAS / REMOVER ESTA LINHA APÓS A EXECUÇÃO -----------------------------------

ALTER TABLE ORION_ADM_010 ADD data_inicio_triagem DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_triagem DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_analise DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_analise DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_aprov_escopo DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_aprov_escopo DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_orcamento DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_orcamento DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_fila_desenv_forn DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_fila_desenv_forn DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_desenv_forn DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_desenv_forn DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_fila_desenv_int DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_fila_desenv_int DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_desenv_int DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_desenv_int DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_qualidade_testes DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_qualidade_testes DATE;

ALTER TABLE ORION_ADM_010 ADD data_inicio_entrega DATE;
ALTER TABLE ORION_ADM_010 ADD data_fim_entrega DATE;

UPDATE ORION_ADM_010 SET data_inicio_triagem = data_chamado;
UPDATE ORION_ADM_010 SET data_fim_analise = data_analise;
UPDATE ORION_ADM_010 SET data_fim_desenv_int = data_entrega_des;
UPDATE ORION_ADM_010 SET data_fim_entrega = data_entrega_usuario;

ALTER TABLE ORION_ADM_010 DROP COLUMN data_chamado;
ALTER TABLE ORION_ADM_010 DROP COLUMN data_analise;
ALTER TABLE ORION_ADM_010 DROP COLUMN data_entrega_des;
ALTER TABLE ORION_ADM_010 DROP COLUMN data_entrega_usuario;