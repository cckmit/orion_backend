create table orion_bi_002
(id varchar2(10), 
 id_programa varchar2(10),
 cod_tipo_email number(3),
 descricao varchar2(100),
 constraint orion_bi_002_pk primary key (id)
);

comment on table orion_bi_002 is 'Tipos de E-mail - B.I';
