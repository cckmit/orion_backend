CREATE TABLE orion_cfc_240(
       id NUMBER(6),
       id_mes VARCHAR2(100),
       nr_semana NUMBER(1),
       dias_uteis NUMBER(2),
       data_inicio DATE,
       data_fim DATE,
       meta_real NUMBER(9),
       meta_real_turno  NUMBER(9),
       meta_ajustada  NUMBER(9),
       meta_ajustada_turno NUMBER(9),
       CONSTRAINT orion_cfc_240_pk PRIMARY KEY (id));