CREATE TABLE orion_cfc_280(
        ID                NUMBER(9),                            
        SOLICITACAO       NUMBER(6),                 
        PEDIDO_VENDA      NUMBER(6),   
        DATA_EMIS_VENDA   DATE,   
        DATA_ENTR_VENDA   DATE,        
        CLI_PED_CGC_CLI9  NUMBER(9),
        CLI_PED_CGC_CLI4  NUMBER(4),    
        CLI_PED_CGC_CLI2  NUMBER(2),    
        CD_IT_PE_GRUPO    VARCHAR2(5),  
        CD_IT_PE_SUBGRUPO VARCHAR2(3),  
        CD_IT_PE_ITEM     VARCHAR2(6),  
        CODIGO_DEPOSITO   NUMBER(3),    
        QTDE_PEDIDA       NUMBER(3),    
        CAMINHO_ARQUIVO   VARCHAR2(200),
        ORDEM_PRODUCAO    NUMBER(9),    
        PERIODO           NUMBER(4),    
        SITUACAO          NUMBER(1),    
        SELECAO           NUMBER(1),    
        DATA_REGISTRO     DATE,         
        ALTERNATIVA       NUMBER(2),    
        ROTEIRO           NUMBER(2),    
        SEQ_ITEM_PEDIDO   NUMBER(3),    
        FLAG_IMAGEM       NUMBER(1),    
        ORDEM_TAMANHO     NUMBER(3),    
        CONSTRAINT orion_cfc_280_pk PRIMARY KEY (ID))