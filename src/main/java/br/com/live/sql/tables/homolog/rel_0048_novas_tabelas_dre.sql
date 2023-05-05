-- Tabelas DRE

-- RELACIONAMENTO LOJAS X CENTRO DE CUSTO X SUPERVISOR - CONSIDERA DRE

    -- SUPERVISOR DA LOJA
    CREATE TABLE ORION_FIN_001 (
          CNPJ_LOJA VARCHAR2(18) PRIMARY KEY,
          CNPJ_SUPERVISOR VARCHAR2(18) NOT NULL
    );

    -- CENTRO CUSTO DA LOJA
    CREATE TABLE ORION_FIN_005 (
      ID NUMBER(9) PRIMARY KEY,
      CNPJ_LOJA VARCHAR2(18) NOT NULL,
      CENTRO_CUSTO NUMBER(6) NOT NULL );

    -- DADOS FATURAMENTOS - INTEGRAÇÃO MICROVIX

    CREATE TABLE ORION_FIN_010 (
            ID VARCHAR2(100) PRIMARY KEY,
            CNPJ_LOJA VARCHAR2(18),
            ANO_LANCAMENTO NUMBER(4),
            MES_LANCAMENTO NUMBER(2),
            QTD_PECA_FATURADA NUMBER(9),
            QTD_PECA_CONSUMO NUMBER(9),
            VAL_FATURAMENTO NUMBER(12,3),
            VAL_IMPOSTO_FATURAMENTO NUMBER(12,3)
    );

-- ORÇAMENTO LOJAS – DRE

    -- ORÇAMENTOS PARA GERAÇÃO DA DRE
        CREATE TABLE ORION_FIN_015 (
            ID NUMBER(9) PRIMARY KEY,
            SEQ_CONSULTA VARCHAR2(50),
            CONTA_CONTABIL NUMBER(5),
            CNPJ_LOJA VARCHAR2(18),
            ANO_ORCAMENTO NUMBER(4),
            MES_ORCAMENTO NUMBER(2),
            TIPO_ORCAMENTO NUMBER(1),
            PROPRIEDADE VARCHAR2(150),
            VAL_PROPRIEDADE NUMBER(12,3))

-- GERAR DRE DAS LOJAS

    -- PARÂMETROS GERAIS
    CREATE TABLE ORION_FIN_020 (
        ID NUMBER(9) PRIMARY KEY,
        ANO_DRE NUMBER(4),
        MES_DRE NUMBER(2),
        PERC_ENCARGOS NUMBER(6,2),
        VAL_IMPOSTO_PLANEJAMENTO NUMBER(12,3),
        VAL_CUSTO_VENDA_PRODUTO NUMBER(12,3)
    );

    -- CONCILIAÇÃO LOJAS
    CREATE TABLE ORION_FIN_025 (
        ID NUMBER(9) PRIMARY KEY,
        CNPJ_LOJA VARCHAR2(18),
        ANO_DRE NUMBER(4),
        MES_DRE NUMBER(2),
        VAL_TAXA_CAPTURA NUMBER(12,3),
        VAL_CUSTO_ANTECIPACAO NUMBER(12,3)
    );

    -- DRE GERADA
    CREATE TABLE ORION_FIN_030 (
      ID NUMBER(9) PRIMARY KEY,
      SEQ_CONSULTA VARCHAR2(50),
      CNPJ_LOJA VARCHAR2(18),
      ANO_DRE NUMBER(4),
      MES_DRE NUMBER(2),
      TIPO_DRE NUMBER(1),
      PROPRIEDADE VARCHAR2(150),
      VAL_REAL_ANO_ANT NUMBER(12,3),
      PERC_REAL_ANO_ANT NUMBER(12,3),
      VAL_ORCADO NUMBER(12,3),
      PERC_ORCADO NUMBER(12,3),
      VAL_REAL NUMBER(12,3),
      PERC_REAL NUMBER(12,3),
      VAL_DIFERENCA_ORCADO_REAL NUMBER(12,3),
      PERC_DIFERENCA_ORCADO_REAL NUMBER(12,3),
      PERC_DIFERENCA_REAL_VIG_ANT NUMBER(12,3)
    );

    -- CRIAÇÃO DE NOVO INDICE DA TABELA CONT_600
    CREATE INDEX INDX_CONT_600_2 ON CONT_600(EXERCICIO, PERIODO, CONTA_REDUZIDA, CENTRO_CUSTO)