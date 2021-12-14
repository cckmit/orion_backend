create table orion_070
(cod_estacao number(9) default 0,
 descricao varchar2(50),
 catalogo number(4) default 0,
 constraint orion_070_pk primary key (cod_estacao)
);

comment on table orion_070 is 'Tabela de Cadastro de Estações';
------------------------------------------------------------------------------
create table orion_071
(id varchar2(30),
 cod_estacao number(9) default 0,
 mes number(2) default 0,
 tipo_meta number(3) default 0,
 ano number(4) default 0,
 perc_distribuicao number(6,2) default 0,
 constraint orion_071_pk primary key (id),
 constraint fk_orion_071_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao)
);

comment on table orion_071 is 'Tabela de Cadastro de Metas da Estação';
------------------------------------------------------------------------------
create table orion_072
(id varchar2(30),
 cod_estacao number(9) default 0,
 cod_representante number(6) default 0,
 tipo_meta number(3) default 0,
 meta number(9,2) default 0,
 descricao_rep varchar2(100),
 constraint orion_072_pk primary key (id),
 constraint fk_orion_072_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao)
);

comment on table orion_072 is 'Tabela de Cadastro de Metas por Representante';
------------------------------------------------------------------------------
create table orion_073
(id varchar2(30),
 cod_estacao number(9) default 0,
 col_tab number(3) default 0,
 mes_tab number(3) default 0,
 seq_tab number(3) default 0,
 constraint orion_073_pk primary key (id),
 constraint fk_orion_073_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao)
);

comment on table orion_073 is 'Tabela de Cadastro de Metas X Tabelas de Preço';
------------------------------------------------------------------------------
create table orion_074
(cod_agrupador number(9) default 0,
 descricao varchar2(50) default 0,
 constraint orion_074_pk primary key (cod_agrupador)
);

comment on table orion_074 is 'Tabela de Cadastro de Agrupador';
------------------------------------------------------------------------------
create table orion_075
(id varchar2(30),
 cod_agrupador number(9) default 0,
 cod_estacao number(9) default 0,
 constraint orion_075_pk primary key (id),
 constraint fk_orion_075_orion_074 foreign key (cod_agrupador) references orion_074 (cod_agrupador),
 constraint fk_orion_075_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao)
);

comment on table orion_075 is 'Tabela de Agrupador X Estação';
------------------------------------------------------------------------------
create table orion_076
(id varchar2(30),
 cod_agrupador number(9) default 0,
 colecao number(3) default 0,
 sub_colecao number(4) default 0,
 constraint orion_076_pk primary key (id),
 constraint fk_orion_076_orion_074 foreign key (cod_agrupador) references orion_074 (cod_agrupador)
);

comment on table orion_076 is 'Tabela de Cadastro de Agrupador - Coleção / Sub-Coleção';
