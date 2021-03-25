-- Criar no Owner LIVE
create table orion_035
(estagio number(2),
 qtde_pecas number(9),
 qtde_minutos number(9),
 constraint orion_035_pk primary key (estagio)
);

comment on table orion_035 is 'Tabela de capacidade de produção dos estágios';
