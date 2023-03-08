create table orion_cfc_300
(id number(9),
 sequencia number(9) default 0,
 periodo number(4) default 0,
 ordem_producao number(9) default 0,
 referencia varchar2(5) default '',
 cores varchar2(100) default '',
 cod_estagio number(2) default 0,
 quantidade number(6) default 0,
 estagios_agrupados varchar2(1000) default '',
 endereco varchar2(10) default '',
 data_entrada date,
 tempo_unit number(10,4) default 0,
 tempo_total number(10,4) default 0,
 data_inicio date,
 data_termino date,
 confirmado varchar2(1) default 0, --> 0-Não / 1-Sim
 constraint orion_cfc_300_pk primary key (id)
 );
 
 create index indx_orion_cfc_300_ordem on orion_cfc_300 (ordem_producao);
 create index indx_orion_cfc_300_estag on orion_cfc_300 (ordem_producao, cod_estagio);
 
 comment on table orion_cfc_300 is 'Tabela com o sequenciamento dos estágios de decorações';