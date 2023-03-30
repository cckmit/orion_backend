--Tabelas DRE

-- RELACIONAMENTO LOJAS X CENTRO DE CUSTO X SUPERVISOR - CONSIDERA DRE

    -- SUPERVISOR DA LOJA
    CREATE TABLE ORION_FIN_001 (
          cnpjLoja VARCHAR2(18) PRIMARY KEY,
          cnpjSupervisor VARCHAR2(18) NOT NULL
    );

    -- CONTA CONTÁBIL DA LOJA
    CREATE TABLE ORION_FIN_005 (
      cnpjLoja VARCHAR2(18) PRIMARY KEY,
      contaContabil NUMBER(5) NOT NULL );


-- CONTA CONTÁBIL – CONSIDERA DRE

    -- CONTA CONTÁBIL CONSIDERA DRE
    CREATE TABLE ORION_FIN_010 (
        contaContabil NUMBER(5) PRIMARY KEY,
        gastoVariavel NUMBER(1),
        custoOcupacao NUMBER(1),
        despesaFolha NUMBER(1),
        despesaGeral NUMBER(1),
        depreciacao NUMBER(1));

-- ORÇAMENTO LOJAS – DRE

    -- ORÇAMENTOS PARA GERAÇÃO DA DRE
    CREATE TABLE ORION_FIN_015 (
        cnpjLoja VARCHAR2(18),
        anoOrcamento NUMBER(4),
        tipoOrcamento NUMBER(1),
        propriedade VARCHAR2(150),
        valPropJan NUMBER(12,3),
        valPropFev NUMBER(12,3),
        valPropMar NUMBER(12,3),
        valPropAbr NUMBER(12,3),
        valPropMai NUMBER(12,3),
        valPropJun NUMBER(12,3),
        valPropJul NUMBER(12,3),
        valPropAgo NUMBER(12,3),
        valPropSet NUMBER(12,3),
        valPropOut NUMBER(12,3),
        valPropNov NUMBER(12,3),
        valPropDez NUMBER(12,3),
        CONSTRAINT PK_ORION_FIN_015 PRIMARY KEY (cnpjLoja, anoOrcamento, tipoOrcamento, propriedade)
    );

-- GERAR DRE DAS LOJAS

    -- PARÂMETROS GERAIS
    CREATE TABLE ORION_FIN_020 (
        anoDre NUMBER(4),
        mesDre NUMBER(2),
        percEncargos NUMBER(6,2),
        valImpostoPlanejamento NUMBER(12,3),
        valCustoVendaProduto NUMBER(12,3),
        CONSTRAINT PK_ORION_FIN_020 PRIMARY KEY (anoDre, mesDre)
    );

    -- CONCILIAÇÃO LOJAS
    CREATE TABLE ORION_FIN_025 (
        CNPJ VARCHAR2(18) PRIMARY KEY,
        valTaxaCaptura NUMBER(12,3),
        valCustoAntecipacao NUMBER(12,3)
    );

    -- DRE GERADA
    CREATE TABLE ORION_FIN_030 (
      cnpjLoja VARCHAR2(18),
      anoDre NUMBER(4),
      mesDre NUMBER(2),
      propriedade VARCHAR2(150),
      valRealAnoAnt NUMBER(12,3),
      percRealAnoAnt NUMBER(6,2),
      valOrcado NUMBER(12,3),
      percOrcado NUMBER(6,2),
      valReal NUMBER(12,3),
      percReal NUMBER(6,2),
      valDiferencaOrcadoReal NUMBER(12,3),
      percDiferencaOrcadoReal NUMBER(6,2),
      percDiferencaRealVigAnt NUMBER(6,2),
      CONSTRAINT ORION_FIN_020 PRIMARY KEY (cnpjLoja, anoDre, mesDre, propriedade)
    );