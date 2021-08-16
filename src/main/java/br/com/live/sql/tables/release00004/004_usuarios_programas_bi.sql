create table orion_bi_004
(id varchar2(20)
 cod_usuario number(6), 
 id_programa varchar2(10),
 constraint orion_bi_004_pk primary key (id)
);

comment on table orion_bi_004 is 'Tabela de usuários x programas - B.I';
