create table orion_001
(id number(9),
 nome varchar2(100),
 usuario varchar2(50),
 senha varchar2(50),
 situacao number(1),
 constraint orion_001_pk primary key (id)
);

comment on table orion_001 is 'Tabela de Usuários - capa';