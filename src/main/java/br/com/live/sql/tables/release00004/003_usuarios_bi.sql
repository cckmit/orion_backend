create table orion_bi_003
(cod_usuario number(6), 
 nome varchar2(100),
 usuario varchar2(20),
 senha varchar2(20),
 email varchar2(100),
 situacao number(1),
 administrador number(1),
 constraint orion_bi_003_pk primary key (cod_usuario)
);

comment on table orion_bi_003 is 'Tabela de usuários - B.I';
