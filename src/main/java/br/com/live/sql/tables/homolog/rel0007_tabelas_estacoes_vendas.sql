create table orion_070
(cod_estacao number(4),
 descricao varchar2(50),
 catalogo number(4),
 constraint orion_070_pk primary key (cod_estacao)
);

comment on table orion_070 is 'Tabela de Cadastro de Estações';
------------------------------------------------------------------------------
create table orion_071
(id varchar2(15),
 cod_estacao number(4),
 mes number(2),
 tipo_meta number(3),
 ano number(4),
 perc_distribuicao number(4,2),
 constraint orion_071_pk primary key (id),
 constraint fk_orion_071_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao)
);

comment on table orion_071 is 'Tabela de Cadastro de Metas da Estação';
------------------------------------------------------------------------------
create table orion_072
(id varchar2(15),
 cod_estacao number(4),
 cod_representante number(6),
 tipo_meta number(3),
 meta number(9,2),
 constraint orion_072_pk primary key (id),
 constraint fk_orion_072_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao)
);

comment on table orion_072 is 'Tabela de Cadastro de Metas por Representante';
