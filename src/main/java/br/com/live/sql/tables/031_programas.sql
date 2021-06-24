create table orion_002
(id number(9),
 descricao varchar2(100),
 modulo varchar2(50),
 path varchar2(50),
 constraint orion_002_pk primary key (id)
);

comment on table orion_002 is 'Tabela de Programas - capa';