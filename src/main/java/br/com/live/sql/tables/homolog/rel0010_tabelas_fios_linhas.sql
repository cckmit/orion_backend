----------------------------------------------------------------------------
create table orion_081
(id number(9) default 0,
 descricao varchar2(100),
 titulo number(9) default 0,
 centim_cone number(9) default 0,
 centim_cone2 number(9) default 0,
 centim_cone3 number(9) default 0,
 constraint orion_081_pk primary key (id)
);

comment on table orion_081 is 'Tabela de Cadastro de Tipos de Fio';
----------------------------------------------------------------------------
create table orion_082
(id number(9) default 0,
 descricao varchar2(100),
 constraint orion_082_pk primary key (id)
);

comment on table orion_082 is 'Tabela de Cadastro de Tipos de Ponto';
----------------------------------------------------------------------------
create table orion_083
(id varchar2(20) default 0,
 sequencia number(6) default 0,
 id_tipo_ponto number(9) default 0,
 descricao varchar2(50),
 tipo_fio_1 number(6) default 0,
 tipo_fio_2 number(6) default 0,
 tipo_fio_3 number(6) default 0,
 consumo_fio number(9,2) default 0,
 constraint orion_083_pk primary key (id),
 constraint fk_orion_083_orion_082 foreign key (id_tipo_ponto) references orion_082 (id)
);

comment on table orion_083 is 'Tabela de Cadastro de Tipos de Ponto - Fios';
----------------------------------------------------------------------------
create table orion_084
(id varchar2(20) default 0,
 referencia varchar2(10) default 0,
 id_tipo_ponto number(9) default 0,
 comprimento_costura number(9,2) default 0,
 constraint orion_084_pk primary key (id),
 constraint fk_orion_084_orion_082 foreign key (id_tipo_ponto) references orion_082 (id)
);

comment on table orion_083 is 'Tabela de Consumo de Fios e Linhas';
----------------------------------------------------------------------------
create table orion_085
(id varchar2(20) default 0,
 id_referencia varchar2(10),
 sequencia number(9) default 0,
 referencia varchar2(10),
 id_tipo_ponto number(9) default 0,
 pacote number(9) default 0,
 metragem_costura_cm number(9,2) default 0,
 metragem_total number(9,2) default 0,
 metragem_um number(9,2) default 0,
 constraint orion_085_pk primary key (id),
 constraint fk_orion_085_orion_082 foreign key (id_tipo_ponto) references orion_082 (id),
 constraint fk_orion_085_orion_084 foreign key (id_referencia) references orion_084 (id)
);

comment on table orion_085 is 'Tabela de Consumo de Fios e Linhas - Consumo por cada Fio';
----------------------------------------------------------------------------
