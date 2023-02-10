ALTER TABLE ORION_210 RENAME TO ORION_CFC_210;

CREATE TABLE ORION_CFC_215 (
   COLUNA NUMBER(1),
   DESCRICAO VARCHAR2(30),
   META NUMBER(6),
   ARTIGOS VARCHAR2(200),
   constraint ORION_CFC_215_PK primary key (COLUNA));

COMMENT ON TABLE ORION_CFC_215 is 'Tabela de configuração das metas por artigos na sugestão de materiais';