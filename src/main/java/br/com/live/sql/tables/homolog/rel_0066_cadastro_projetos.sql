--DROP
--ALTER TABLE orion_ti_036 DROP CONSTRAINT fk_orion_ti_036_tipo_atividade
--DROP TABLE orion_ti_036

--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_fase
--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_tipo_atividade
--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_projeto
--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_responsavel
--DROP TABLE orion_ti_045

--ALTER TABLE orion_ti_055 DROP CONSTRAINT fk_orion_ti_055_projeto
--ALTER TABLE orion_ti_055 DROP CONSTRAINT fk_orion_ti_055_usuario
--ALTER TABLE orion_ti_055 DROP CONSTRAINT fk_orion_ti_055_funcao
--DROP TABLE orion_ti_055

-- Tabela Fases
CREATE TABLE orion_ti_030 (
    ID NUMBER(9) PRIMARY KEY,
    DESCRICAO VARCHAR2(400)
);

-- Tabela Tipo Atividade
CREATE TABLE orion_ti_035 (
    ID NUMBER(9) PRIMARY KEY,
    DESCRICAO VARCHAR2(400),
    EXIGE_DOC VARCHAR2(3)
);

-- Tabela Funcao de Pessoas
CREATE TABLE orion_ti_050 (
    ID NUMBER(9) PRIMARY KEY,
    DESCRICAO VARCHAR2(400)
);

-- Tabela Tarefa Tipo de Atividade
CREATE TABLE orion_ti_036 (
    ID NUMBER(9) PRIMARY KEY,
    ID_TIPO_ATIVIDADE NUMBER(9),
    DESCRICAO VARCHAR2(400),
    ORDENACAO NUMBER(5),
    CONSTRAINT fk_orion_ti_036_tipo_atividade FOREIGN KEY (ID_TIPO_ATIVIDADE) REFERENCES orion_ti_035(ID) ON DELETE CASCADE
);

-- Cadastro de Projetos
CREATE TABLE orion_ti_040 (
    ID NUMBER(9) PRIMARY KEY,
    COD_PROJETO NUMBER(9),
    DESCRICAO VARCHAR2(400),
    DATA_CRIACAO DATE,
    AREA NUMBER(9),
    DEPARTAMENTO NUMBER(9),
    SETOR NUMBER(9),
    ORIGEM_PROJETO VARCHAR2(50),
    SUB_ORIGEM_PROJETO VARCHAR2(50),
    STATUS VARCHAR2(100),
    OBJETIVO_PROJETO VARCHAR2(4000),
    CONTEXTUALIZACAO VARCHAR2(4000),
    DESCRICAO_PROBLEMA VARCHAR2(4000),
    PERGUNTAS_ABERTA VARCHAR2(4000),
    RISCOS VARCHAR2(4000)
);

-- Tabela Atividades
CREATE TABLE orion_ti_045 (
    ID NUMBER(9) PRIMARY KEY,
    DESCRICAO VARCHAR2(400),
    ID_PROJETO NUMBER(9),
    ID_FASE NUMBER(9),
    ID_TIPO_ATIVIDADE NUMBER(9),
    ID_RESPONSAVEL NUMBER(9),
    DATA_PREV_INICIO DATE,
    DATA_PREV_FIM DATE,
    TEMPO_PREVISTO NUMBER(5),
    CONSTRAINT fk_orion_ti_045_projeto FOREIGN KEY (ID_PROJETO) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
    CONSTRAINT fk_orion_ti_045_fase FOREIGN KEY (ID_FASE) REFERENCES orion_ti_030(ID),
    CONSTRAINT fk_orion_ti_045_tipo_atividade FOREIGN KEY (ID_TIPO_ATIVIDADE) REFERENCES orion_ti_035(ID),
    CONSTRAINT fk_orion_ti_045_responsavel FOREIGN KEY (ID_RESPONSAVEL) REFERENCES orion_001(ID)
);

-- Tabela de envolvidos do projeto
CREATE TABLE orion_ti_055 (
  ID NUMBER PRIMARY KEY,
  ID_PROJETO NUMBER(9),
  ID_USUARIO NUMBER(9),
  SOLICITANTE_STAKEHOLDER VARCHAR2(255),
  ID_FUNCAO_PESSOA NUMBER(9),
  AREA NUMBER(9),
  DEPARTAMENTO NUMBER(9),
  SETOR NUMBER(9),
  CONSTRAINT fk_orion_ti_055_projeto FOREIGN KEY (ID_PROJETO) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
  CONSTRAINT fk_orion_ti_055_usuario FOREIGN KEY (ID_USUARIO) REFERENCES orion_001(ID),
  CONSTRAINT fk_orion_ti_055_funcao FOREIGN KEY (ID_FUNCAO_PESSOA) REFERENCES orion_ti_050(ID)
)