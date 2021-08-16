create table orion_bi_005
(id varchar2(20)
 cod_usuario number(6), 
 id_tipo_email varchar2(10),
 constraint orion_bi_005_pk primary key (id)
);

comment on table orion_bi_005 is 'Tabela de usuários x tipos de email - B.I';