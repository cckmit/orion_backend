-- Criar no Owner LIVE
create table orion_035
(id varchar2(20), -- periodo - estagio
 periodo number(4),
 estagio number(2),
 qtde_pecas number(9),
 qtde_minutos number(9),
 constraint orion_035_pk primary key (id)
);

comment on table orion_035 is 'Tabela de capacidade de produção dos estágios';

create INDEX INDX_ORION_35_PERIODO on orion_035 (periodo);
create INDEX INDX_ORION_35_PER_ESTAG on orion_035 (periodo, estagio);
