create table orion_bi_005
(id varchar2(20),
 cod_usuario number(6), 
 id_programa varchar2(10),
 id_tipo_email varchar2(10),
 constraint orion_bi_005_pk primary key (id),
 constraint fk_orion_bi_005_orion_bi_003 foreign key (cod_usuario) references orion_bi_003 (cod_usuario),
 constraint fk_orion_bi_005_orion_bi_001 foreign key (id_programa) references orion_bi_001 (id),
 constraint fk_orion_bi_005_orion_bi_002 foreign key (id_tipo_email) references orion_bi_002 (id)
);

comment on table orion_bi_005 is 'Tabela de usuários x tipos de email - B.I';