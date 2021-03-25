-- Criar no Owner LIVE
create table orion_036
(estagio_artigo varchar2(10),
 estagio number(2),
 artigo number(4),
 qtde_pecas number(9),
 qtde_minutos number(9),
 constraint orion_036_pk primary key (estagio_artigo)
);

comment on table orion_036 is 'Tabela de capacidade de produção dos artigos de produtos';