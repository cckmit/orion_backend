-- Criar no Owner LIVE
create table orion_030
(id varchar2(20),
 nivel varchar2(1),
 grupo varchar2(5),
 item varchar2(6),
 constraint orion_030_pk primary key (id)
);

comment on table orion_030 is 'Tabela de itens sugeridos para cancelar produção';

create INDEX INDX_ORION_30_ITENS on orion_030 (nivel, grupo, item);