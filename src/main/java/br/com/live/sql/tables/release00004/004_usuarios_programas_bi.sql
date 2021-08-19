create table orion_bi_004
(id varchar2(20),
 cod_usuario number(6), 
 id_programa varchar2(10),
 constraint orion_bi_004_pk primary key (id),
 constraint fk_orion_bi_004_orion_bi_003 foreign key (cod_usuario) references orion_bi_003 (cod_usuario),
 constraint fk_orion_bi_004_orion_bi_001 foreign key (id_programa) references orion_bi_001 (id)
);

comment on table orion_bi_004 is 'Tabela de usuários x programas - B.I';
