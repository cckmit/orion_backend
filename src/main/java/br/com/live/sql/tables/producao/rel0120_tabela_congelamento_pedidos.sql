create table orion_com_310  
(pedido_venda number(9),
 data_entr_venda date,
 situacao_venda number(2),
 constraint orion_com_310_pk primary key (pedido_venda)
 );

comment on table orion_com_310 is 'Tabela de congelamento de pedidos de venda';

ALTER TABLE orion_com_310 
ADD (SIT_ALOC_PEDI NUMBER(1) DEFAULT 0,
     SIT_COLETOR NUMBER(1) DEFAULT 0,
     SITUACAO_COLETA  NUMBER(1) DEFAULT 0,
     STATUS_EXPEDICAO NUMBER(1) DEFAULT 0, 
     STATUS_HOMOLOGACAO NUMBER(1) DEFAULT 0,
     STATUS_PEDIDO  NUMBER(1) DEFAULT 0,
     STATUS_COMERCIAL NUMBER(1) DEFAULT 0,
     SIT_CONFERENCIA  NUMBER(1) DEFAULT 0);

CREATE TABLE orion_com_311
(PEDIDO_VENDA NUMBER(9) DEFAULT 0,
 SEQ_SITUACAO NUMBER(2) DEFAULT 0,
 CODIGO_SITUACAO NUMBER(2) DEFAULT 0, 
 FLAG_LIBERACAO VARCHAR2(1) DEFAULT 0,
 DATA_SITUACAO DATE,
 DATA_LIBERACAO DATE,
 CONSTRAINT orion_com_311_pk PRIMARY KEY (PEDIDO_VENDA, SEQ_SITUACAO));

comment on table orion_com_311 is 'Tabela de congelamento de pedidos de venda - Motivos de bloqueio';