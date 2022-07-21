create table orion_exp_001 (
  id number(6), 
  variacao number(10,2),
  constraint orion_exp_001_pk primary key (id)
);

comment on table orion_exp_001 is 'Tabela de Variação de Peso por Artigo de Prduto';