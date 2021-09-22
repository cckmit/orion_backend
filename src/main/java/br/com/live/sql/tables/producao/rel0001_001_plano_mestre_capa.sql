-- Criar no Owner LIVE
create table orion_010
(num_plano_mestre number(9),
 descricao varchar2(200),
 data date,
 situacao number(1),
 constraint orion_010_pk primary key (num_plano_mestre)
);

comment on table orion_010 is 'Tabela plano mestre de produção - capa';

create sequence id_orion_010;