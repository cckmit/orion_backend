ALTER TABLE ORION_001
ADD (EMPRESA_SYSTEXTIL NUMBER(6) DEFAULT 0);

ALTER TABLE ORION_011
MODIFY embarques VARCHAR(500);

ALTER TABLE ORION_150
ADD (MODALIDADE VARCHAR2(100));

ALTER TABLE ORION_ADM_001
MODIFY TEMPO_ESTIMADO NUMBER(6,2);

CREATE TABLE ORION_CFC_210
 (ORDEM_PRODUCAO NUMBER(9), 
  LEMBRETE VARCHAR2(100), 
  CONSTRAINT ORION_210_PK PRIMARY KEY (ORDEM_PRODUCAO));

COMMENT ON TABLE ORION_CFC_210 IS 'Tabela de extensão de campos da tabela de ordens - PCPC_020';
  
ALTER TABLE ORION_CFC_230
ADD (NUMERO_EXPEDIDOR NUMBER(3) DEFAULT 0,
	 META_EXPEDIDOR   NUMBER(9) DEFAULT 0);
	 
ALTER TABLE ORION_CFC_230
MODIFY COD_ESTAGIO NUMBER(3);	 

ALTER TABLE ORION_ENG_230
MODIFY DESCRICAO VARCHAR2(200);

CREATE TABLE ORION_TI_015
   (ID NUMBER(5), 
	NOME_SERVICO VARCHAR2(100) NOT NULL ENABLE, 
	OBJETIVO VARCHAR2(300), 
	TIME_RESPONSAVEL VARCHAR2(50), 
	DISPONIBILIDADE NUMBER(1), 
	TECNICOS_FORNECEDORES VARCHAR2(200), 
	GESTOR_RESPONSAVEL NUMBER(3), 
	PRIMARY KEY (ID));

CREATE TABLE ORION_TI_020
   (ID VARCHAR2(20), 
	TIPO NUMBER(2), 
	DATA_CADASTRO DATE, 
	PRIORIDADE NUMBER(2,0), 
	DESCRICAO VARCHAR2(100), 
	OBJETIVO VARCHAR2(4000), 
	CONTEXTUALIZACAO VARCHAR2(4000), 
	DESCRICAO_PROBLEMA VARCHAR2(4000), 
	PERGUNTAS_EM_ABERTO VARCHAR2(4000), 
	RISCOS VARCHAR2(4000), 
	PRIMARY KEY (ID));
