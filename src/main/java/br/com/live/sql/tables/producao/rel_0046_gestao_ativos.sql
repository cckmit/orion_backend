-- Cadastro de Servidor
CREATE TABLE ORION_TI_001 (
    ID NUMBER(5) PRIMARY KEY,
    NOME_SERVIDOR VARCHAR2(100) NOT NULL,
    MAQUINA_FISICA NUMBER(1),                             
    SISTEMA_OPERACIONAL VARCHAR2(100),                         
    IP  VARCHAR2(50),                           
    HD  VARCHAR2(100),                          
    MEMORIA VARCHAR2(100),                          
    PROCESSADOR VARCHAR2(200),                          
    APLICACOES VARCHAR2(4000),
    DOCUMENTACAO BLOB,
    STATUS VARCHAR2(100)
);
-- Cadastro de Sistemas
CREATE TABLE ORION_TI_005 (
    ID NUMBER(5) PRIMARY KEY,
    NOME_SISTEMA VARCHAR2(100) NOT NULL,
    OBJETIVO VARCHAR2(300),
    BANCO_DE_DADOS VARCHAR2(100),
    TIPO VARCHAR2(20),
    FORNECEDOR VARCHAR2(100),
    CNPJ VARCHAR2(20),
    ENDERECO VARCHAR2(200),
    FORMA_PAGTO VARCHAR2(50),
    TEM_CONTRATO NUMBER(1),
    CONTRATO BLOB,
    AMBIENTE VARCHAR2(50),
    USUARIOS_ATIVOS NUMBER(10),
    CAPACIDADE_USUARIOS NUMBER(10),
    STATUS VARCHAR2(100)
);
-- Cadastro de Integrações
CREATE TABLE ORION_TI_010 (
    ID NUMBER(5) PRIMARY KEY,
    NOME_INTEGRACAO VARCHAR2(100) NOT NULL,
    OBJETIVO VARCHAR2(300),
    TIPO_INTEGRACAO VARCHAR2(50),
    TIPO_CONEXAO VARCHAR2(20),
    SISTEMA_ORIGEM NUMBER(5),
    SISTEMA_DESTINO NUMBER(5),
    SERVIDOR NUMBER(5),
    FORNECEDOR VARCHAR2(100),
    CNPJ VARCHAR2(20),
    ENDERECO VARCHAR2(200),
    STATUS VARCHAR2(100),
    CONSTRAINT fk_orion_ti_005_sistema_origem FOREIGN KEY (SISTEMA_ORIGEM) REFERENCES ORION_TI_005 (ID),
    CONSTRAINT fk_orion_ti_005_sistema_destino FOREIGN KEY (SISTEMA_DESTINO) REFERENCES ORION_TI_005 (ID),
    CONSTRAINT fk_orion_ti_001_servidor FOREIGN KEY (SERVIDOR) REFERENCES ORION_TI_001 (ID)
);

// Cadastro de Serviços
CREATE TABLE ORION_TI_015 (
    ID NUMBER(5) PRIMARY KEY,
    NOME_SERVICO VARCHAR2(100) NOT NULL,
    OBJETIVO VARCHAR2(300),
    TIME_RESPONSAVEL VARCHAR2(50),
    DISPONIBILIDADE NUMBER(1),
    TECNICOS_FORNECEDORES VARCHAR2(200)
);

-- ALTERADO TAMANNHO DO CAMPO PROCESSADOR PARA 200
ALTER TABLE orion_ti_001
MODIFY processador VARCHAR2(200);


-- ALTERA CAMPO HD E MEMÓRIA PARA VARCHAR2(100)
ALTER TABLE ORION_TI_001 ADD HD_NEW VARCHAR2(100);
ALTER TABLE ORION_TI_001 ADD MEMORIA_NEW VARCHAR2(100);

UPDATE ORION_TI_001 SET HD_NEW = HD;
UPDATE ORION_TI_001 SET MEMORIA_NEW = MEMORIA;

UPDATE ORION_TI_001 SET HD = NULL;
UPDATE ORION_TI_001 SET MEMORIA = NULL;

ALTER TABLE ORION_TI_001 MODIFY HD VARCHAR2(100);
ALTER TABLE ORION_TI_001 MODIFY MEMORIA VARCHAR2(100);

UPDATE ORION_TI_001 SET HD = HD_NEW;
UPDATE ORION_TI_001 SET MEMORIA = MEMORIA_NEW;

ALTER TABLE ORION_TI_001 DROP COLUMN HD_NEW;
ALTER TABLE ORION_TI_001 DROP COLUMN MEMORIA_NEW;


-- Alterar Tabela adicionando a Coluna Status
ALTER TABLE orion_ti_001 ADD COLUMN status VARCHAR2(100)
-- Alterar Tabela adicionando a Coluna Status
ALTER TABLE orion_ti_005 ADD COLUMN status VARCHAR2(100)
-- Alterar Tabela adicionando a Coluna Status
ALTER TABLE orion_ti_010 ADD COLUMN status VARCHAR2(100)

-- Cadastro de Oportunidade
CREATE TABLE ORION_TI_020 (
    ID                  VARCHAR2(20) PRIMARY KEY,
    TIPO                NUMBER(2),
    ID_ATIVO            NUMBER(3),
    SEQUENCIA           NUMBER(3),
    NOME_ATIVO          VARCHAR2(100),
    DATA_CADASTRO       DATE,
    PRIORIDADE          NUMBER(2),
    DESCRICAO           VARCHAR2(100),
    OBJETIVO            VARCHAR2(4000),
    CONTEXTUALIZACAO    VARCHAR2(4000),
    DESCRICAO_PROBLEMA  VARCHAR2(4000),
    PERGUNTAS_EM_ABERTO VARCHAR(4000),
    RISCOS              VARCHAR2(4000)
);

-- Alterar a Tabela Adicionando a Coluna Gestor Responsavel
ALTER TABLE orion_ti_001 ADD gestor_responsavel NUMBER(3);

ALTER TABLE orion_ti_005 ADD gestor_responsavel NUMBER(3);

ALTER TABLE orion_ti_010 ADD gestor_responsavel NUMBER(3);

ALTER TABLE orion_ti_015 ADD gestor_responsavel NUMBER(3);


-- Cria registros default para "Todos":

INSERT INTO ORION_TI_001 (ID, NOME_SERVIDOR, MAQUINA_FISICA, SISTEMA_OPERACIONAL, IP, HD, MEMORIA, PROCESSADOR, APLICACOES, DOCUMENTACAO, STATUS, GESTOR_RESPONSAVEL)
VALUES (0, 'Todos', 0, 'N/A', '0.0.0.0', 'N/A', 'N/A', 'N/A', 'N/A', EMPTY_BLOB(), 'Ativo', 0);

INSERT INTO ORION_TI_005 (ID, NOME_SISTEMA, OBJETIVO, BANCO_DE_DADOS, TIPO, FORNECEDOR, CNPJ, ENDERECO, FORMA_PAGTO, TEM_CONTRATO, CONTRATO, AMBIENTE, USUARIOS_ATIVOS, CAPACIDADE_USUARIOS, STATUS, GESTOR_RESPONSAVEL)
VALUES (0, 'Todos', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 0, EMPTY_BLOB(), 'N/A', 0, 0, 'Ativo', 0);

-- Altera valor dos campos Time Responsável

   UPDATE ORION_TI_015
   SET TIME_RESPONSAVEL = 'Sistemas Atendimentos'
   WHERE TIME_RESPONSAVEL = 'Atendimento'

   UPDATE ORION_TI_015
   SET TIME_RESPONSAVEL = 'Sistemas Projetos'
   WHERE TIME_RESPONSAVEL = 'Inovação'