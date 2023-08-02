-- orion_ti_030 -- Fases
-- orion_ti_035 -- Tipo de Atividade
-- orion_ti_036 -- Tarefas do Tipo de Atividade
-- orion_ti_040 -- Projetos
-- orion_ti_045 -- Atividades Previstas (Cronograma Previsto)
-- orion_ti_050 -- Função de Pessoas
-- orion_ti_055 -- Envolvidos do Projeto
-- orion_ti_060 -- Aprovadores do Escopo
-- orion_ti_065 -- Custos e Orçamentos
-- orion_ti_070 -- Atividades Realizadas (Acompanhamento do Projeto)
-- orion_ti_071 -- Tarefas das Atividades Realizadas (Acompanhamento do Projeto)
-- orion_anexos -- Imagens/Documentos relacionados ao Projeto

--DROP
--ALTER TABLE orion_ti_036 DROP CONSTRAINT fk_orion_ti_036_tipo_atividade
--DROP TABLE orion_ti_036

--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_fase
--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_tipo_atividade
--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_projeto
--ALTER TABLE orion_ti_045 DROP CONSTRAINT fk_orion_ti_045_responsavel
--DROP TABLE orion_ti_045

--ALTER TABLE orion_ti_046 DROP CONSTRAINT fk_orion_ti_046_projeto
--ALTER TABLE orion_ti_046 DROP CONSTRAINT fk_orion_ti_046_atividade
--DROP TABLE orion_ti_046

--ALTER TABLE orion_ti_055 DROP CONSTRAINT fk_orion_ti_055_projeto
--ALTER TABLE orion_ti_055 DROP CONSTRAINT fk_orion_ti_055_usuario
--ALTER TABLE orion_ti_055 DROP CONSTRAINT fk_orion_ti_055_funcao
--DROP TABLE orion_ti_055

--ALTER TABLE orion_ti_060 DROP CONSTRAINT fk_orion_ti_060_usuario
--ALTER TABLE orion_ti_060 DROP CONSTRAINT fk_orion_ti_060_projeto
--DROP TABLE orion_ti_060

--ALTER TABLE orion_ti_065 DROP CONSTRAINT fk_orion_ti_065_projeto
--DROP TABLE orion_ti_065

--ALTER TABLE orion_ti_070 DROP CONSTRAINT fk_orion_ti_070_projeto
--ALTER TABLE orion_ti_070 DROP CONSTRAINT fk_orion_ti_070_usuario
--ALTER TABLE orion_ti_070 DROP CONSTRAINT fk_orion_ti_070_fase
--DROP TABLE orion_ti_070

--ALTER TABLE orion_ti_071 DROP CONSTRAINT fk_orion_ti_071_projeto
--ALTER TABLE orion_ti_071 DROP CONSTRAINT fk_orion_ti_071_usuario
--ALTER TABLE orion_ti_071 DROP CONSTRAINT fk_orion_ti_071_registro_atividade
--DROP TABLE orion_ti_071

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

-- Tabela Tarefa Tipo de Atividade
CREATE TABLE orion_ti_036 (
    ID NUMBER(9) PRIMARY KEY,
    ID_TIPO_ATIVIDADE NUMBER(9),
    DESCRICAO VARCHAR2(400),
    ORDENACAO NUMBER(5),
    TEMPO_ESTIMADO NUMBER(8,2),
    CONSTRAINT fk_orion_ti_036_tipo_atividade FOREIGN KEY (ID_TIPO_ATIVIDADE) REFERENCES orion_ti_035(ID) ON DELETE CASCADE
);

-- Cadastro de Projetos
CREATE TABLE orion_ti_040 (
    ID NUMBER(9) PRIMARY KEY,                   -- Projeto
    COD_PROJETO NUMBER(9),                      -- Projeto
    DESCRICAO VARCHAR2(400),                    -- Projeto
    DATA_CRIACAO DATE,                          -- Projeto
    AREA NUMBER(9),                             -- Projeto
    DEPARTAMENTO NUMBER(9),                     -- Projeto
    SETOR NUMBER(9),                            -- Projeto
    ORIGEM_PROJETO VARCHAR2(50),                -- Projeto
    SUB_ORIGEM_PROJETO VARCHAR2(50),            -- Projeto
    STATUS VARCHAR2(100),                       -- Projeto
    OBJETIVO_PROJETO VARCHAR2(4000),            -- Briefing
    CONTEXTUALIZACAO VARCHAR2(4000),            -- Briefing
    DESCRICAO_PROBLEMA VARCHAR2(4000),          -- Briefing
    PERGUNTAS_ABERTA VARCHAR2(4000),            -- Briefing
    RISCOS_BRIEFING VARCHAR2(4000),             -- Briefing
    JUSTIFICATIVA_PROJETO VARCHAR2(4000),       -- Termo Abertura
    OBJETIVO_SMART VARCHAR2(4000),              -- Termo Abertura
    BENEFICIO VARCHAR2(4000),                   -- Termo Abertura
    RESTRICAO VARCHAR2(4000),                   -- Termo Abertura
    REQUISITO VARCHAR2(4000),                   -- Termo Abertura
    ENTREGAVEL VARCHAR2(4000),                  -- Termo Abertura
    RISCO_ABERTURA VARCHAR2(4000),              -- Termo Abertura
    MVPS VARCHAR2(4000),                        -- Escopo
    PARTE_AFETADA VARCHAR2(4000),             -- Escopo
    SISTEMA_PROCESSO_AFETADO VARCHAR2(4000),    -- Escopo
    EXCLUSAO_ESCOPO VARCHAR2(4000)              -- Escopo
);

-- Tabela Atividades Previstas
CREATE TABLE orion_ti_045 (
    ID NUMBER(9) PRIMARY KEY,
    DESCRICAO VARCHAR2(400),
    ID_PROJETO NUMBER(9),
    ID_FASE NUMBER(9),
    ID_TIPO_ATIVIDADE NUMBER(9),
    ID_RESPONSAVEL NUMBER(9),
    DATA_PREV_INICIO DATE,
    DATA_PREV_FIM DATE,
    TEMPO_PREVISTO NUMBER(8,2),
    CONSTRAINT fk_orion_ti_045_projeto FOREIGN KEY (ID_PROJETO) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
    CONSTRAINT fk_orion_ti_045_fase FOREIGN KEY (ID_FASE) REFERENCES orion_ti_030(ID),
    CONSTRAINT fk_orion_ti_045_tipo_atividade FOREIGN KEY (ID_TIPO_ATIVIDADE) REFERENCES orion_ti_035(ID),
    CONSTRAINT fk_orion_ti_045_usuario FOREIGN KEY (ID_RESPONSAVEL) REFERENCES orion_001(ID)
);

-- Tabela Funcao de Pessoas
CREATE TABLE orion_ti_050 (
    ID NUMBER(9) PRIMARY KEY,
    DESCRICAO VARCHAR2(400)
);

-- Tabela de envolvidos do projeto
CREATE TABLE orion_ti_055 (
  ID NUMBER(9) PRIMARY KEY,
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

-- Tabela de aprovadores do escopo
CREATE TABLE orion_ti_060 (
  ID VARCHAR2(100),
  ID_USUARIO NUMBER(9),
  ID_PROJETO NUMBER(9),
  CONSTRAINT fk_orion_ti_060_projeto FOREIGN KEY (ID_PROJETO) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
  CONSTRAINT fk_orion_ti_060_usuario FOREIGN KEY (ID_USUARIO) REFERENCES orion_001(ID)
)

-- Tabela de custos e orçamentos
CREATE TABLE orion_ti_065 (
  ID NUMBER(9) PRIMARY KEY,
  ID_PROJETO NUMBER(9),
  ITEM VARCHAR(400),
  QUANTIDADE NUMBER(9),
  VALOR_UNITARIO NUMBER(12,3),
  PAGAR_PARA VARCHAR(400),
  CONSTRAINT fk_orion_ti_065_projeto FOREIGN KEY (ID_PROJETO) REFERENCES orion_ti_040(ID) ON DELETE CASCADE
);

-- Tabela: Atividades Realizadas acompanhamento projeto
CREATE TABLE orion_ti_070 (
  ID NUMBER(9) PRIMARY KEY,
  ID_PROJETO NUMBER(9),
  DESCRICAO VARCHAR2(500),
  ACAO_REALIZADA VARCHAR2(4000),
  ID_RESPONSAVEL NUMBER(9),
  ID_FASE NUMBER(9),
  DATA_INICIO DATE,
  HORA_INICIO DATE,
  DATA_FIM DATE,
  HORA_FIM DATE,
  DOCUMENTO_ASSOCIADO VARCHAR2(4000),
  CUSTO NUMBER(12,3),
  ID_ATIVIDADE NUMBER(9),
  TEMPO_GASTO NUMBER(8,2),
  CONSTRAINT fk_orion_ti_070_projeto FOREIGN KEY (id_projeto) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
  CONSTRAINT fk_orion_ti_070_usuario FOREIGN KEY (id_responsavel) REFERENCES orion_001(ID),
  CONSTRAINT fk_orion_ti_070_fase FOREIGN KEY (id_fase) REFERENCES orion_ti_030(ID)
);

-- Tabela: Tarefas Atividades Realizadas acompanhamento projeto
CREATE TABLE orion_ti_071 (
  ID NUMBER(9) PRIMARY KEY,
  ID_PROJETO NUMBER(9),
  ID_REGISTRO_ATIVIDADE NUMBER(9),
  DESCRICAO VARCHAR2(500),
  ACAO_REALIZADA VARCHAR2(4000),
  ID_RESPONSAVEL NUMBER(9),
  DATA_INICIO DATE,
  HORA_INICIO DATE,
  DATA_FIM DATE,
  HORA_FIM DATE,
  DOCUMENTO_ASSOCIADO VARCHAR2(4000),
  CUSTO NUMBER(12,3),
  TEMPO_GASTO NUMBER(8,2),
  CONSTRAINT fk_orion_ti_071_projeto FOREIGN KEY (id_projeto) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
  CONSTRAINT fk_orion_ti_071_registro_atividade FOREIGN KEY (ID_REGISTRO_ATIVIDADE ) REFERENCES orion_ti_070(ID) ON DELETE CASCADE,
  CONSTRAINT fk_orion_ti_071_usuario FOREIGN KEY (id_responsavel) REFERENCES orion_001(ID)
);


-- Ajustes após oficialização
------------------------------

UPDATE ORION_002
SET DESCRICAO = 'Gerenciador de Projetos',
    "PATH"  = '/gerenciador-projetos'
WHERE id = 108;

ALTER TABLE ORION_TI_055
DROP COLUMN SOLICITANTE_STAKEHOLDER;

ALTER TABLE orion_ti_045
ADD MARCO NUMBER(1);

UPDATE orion_ti_045
SET MARCO = 0

ALTER TABLE orion_ti_070
ADD MARCO NUMBER(1);

UPDATE orion_ti_070
SET MARCO = 0


-- Cadastro de tarefa de atividade prevista. -- OFICIALIZAR

ALTER TABLE orion_ti_071
ADD ID_TAREFA_ATIVIDADE NUMBER(9);

-- Tabela: Tarefas Atividades Prevista Projeto
CREATE TABLE orion_ti_046 (
  ID NUMBER(9) PRIMARY KEY,
  ID_PROJETO NUMBER(9),
  ID_ATIVIDADE NUMBER(9),
  DESCRICAO VARCHAR2(500),
  TEMPO_PREVISTO NUMBER(8,2),
  CONSTRAINT fk_orion_ti_046_projeto FOREIGN KEY (ID_PROJETO) REFERENCES orion_ti_040(ID) ON DELETE CASCADE,
  CONSTRAINT fk_orion_ti_046_atividade FOREIGN KEY (ID_ATIVIDADE ) REFERENCES orion_ti_045(ID) ON DELETE CASCADE
);

-- Inicializa a sequencia, e faz a inserção dos dados na tabela nova.

INSERT INTO ORION_TI_046 (ID, ID_PROJETO, ID_ATIVIDADE, DESCRICAO, TEMPO_PREVISTO)
SELECT
    ROW_NUMBER() OVER (ORDER BY a.ID_PROJETO, a.ID, b.ORDENACAO) AS ID, -- Gerando um novo ID sequencial usando a função ROW_NUMBER() e incrementando pelo valor máximo atual da coluna ID na tabela ORION_TI_046, ou retornando 1 se for NULL
    a.ID_PROJETO,
    a.ID,
    b.DESCRICAO,
    b.TEMPO_ESTIMADO
FROM
    orion_ti_045 a
JOIN
    ORION_TI_036 b ON a.ID_TIPO_ATIVIDADE = b.ID_TIPO_ATIVIDADE
ORDER BY
    a.ID_PROJETO,
    a.ID,
    b.ORDENACAO;