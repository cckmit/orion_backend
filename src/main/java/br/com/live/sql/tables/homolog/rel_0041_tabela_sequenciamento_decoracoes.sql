create table orion_cfc_300
(id number(9),
 sequencia number(9),
 periodo number(4),
 ordem_producao number(9),
 referencia varchar2(5),
 cores varchar2(100),
 cod_estagio number(2),
 quantidade number(6),
 estagios_agrupados varchar2(1000),
 endereco varchar2(10),
 data_entrada date,
 tempo_unit number(10,4),
 tempo_total number(10,4),
 data_inicio date,
 data_termino date,
 constraint orion_cfc_300_pk primary key (id)
 );
 
 create index indx_orion_cfc_300_ordem on orion_cfc_300 (ordem_producao);
 create index indx_orion_cfc_300_estag on orion_cfc_300 (ordem_producao, cod_estagio);
 
 comment on table orion_cfc_300 is 'Tabela com o sequenciamento dos estágios de decorações';