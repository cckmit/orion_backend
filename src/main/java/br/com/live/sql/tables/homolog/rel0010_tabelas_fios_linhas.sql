----------------------------------------------------------------------------
create table orion_081
(id number(9) default 0,
 descricao varchar2(100),
 titulo number(9) default 0,
 centim_cone number(9) default 0,
 constraint orion_081_pk primary key (id)
);

comment on table orion_081 is 'Tabela de Cadastro de Tipos de Fio';
----------------------------------------------------------------------------
create table orion_082
(id number(9) default 0,
 descricao varchar2(100),
 grupo_maquina varchar2(10),
 sub_grupo_maquina varchar2(10),
 constraint orion_082_pk primary key (id)
);

comment on table orion_082 is 'Tabela de Cadastro de Tipos de Ponto';
----------------------------------------------------------------------------
create table orion_083
(id varchar2(20) default 0,
 sequencia number(6) default 0,
 id_tipo_ponto number(9) default 0,
 tipo_fio number(9) default 0,
 consumo_fio number(9,2) default 0,
 constraint orion_083_pk primary key (id),
 constraint fk_orion_083_orion_082 foreign key (id_tipo_ponto) references orion_082 (id)
);

comment on table orion_083 is 'Tabela de Cadastro de Tipos de Ponto - Fios';
----------------------------------------------------------------------------