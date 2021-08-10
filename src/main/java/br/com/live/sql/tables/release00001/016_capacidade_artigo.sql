-- Criar no Owner LIVE
create table orion_036
(id varchar2(20), -- periodo - estagio - artigo
 periodo number(4),
 estagio number(2),
 artigo number(4),
 qtde_pecas number(9),
 qtde_minutos number(9),
 constraint orion_036_pk primary key (id)
);

comment on table orion_036 is 'Tabela de capacidade de produção dos artigos de produtos';

create INDEX INDX_ORION_36_PER_ESTAG on orion_036 (periodo, estagio);
create INDEX INDX_ORION_36_PER_ESTAG_ART on orion_036 (periodo, estagio, artigo);