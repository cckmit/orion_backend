create table orion_cfc_300
(id number(9),
 sequencia number(9),
 ordem_producao number(9),
 cod_estagio number(2),
 data_inicio date,
 data_termino date,
 constraint orion_cfc_300_pk primary key (id)
 );
 
 create index indx_orion_cfc_300_ordem on orion_cfc_300 (ordem_producao);
 create index indx_orion_cfc_300_estag on orion_cfc_300 (ordem_producao, cod_estagio);
 
 comment on table orion_cfc_300 is 'Tabela com o sequenciamento dos estágios de decorações';