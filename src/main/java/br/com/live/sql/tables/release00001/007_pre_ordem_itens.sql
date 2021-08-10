-- Criar no Owner LIVE
create table orion_021
(id number(9),
 num_id_ordem number(9),
 num_plano_mestre number(9),
 sub varchar2(3),
 item varchar2(6),
 quantidade number(6), 
 constraint orion_021_pk primary key (id),
 constraint fk_orion_021_orion_020	foreign key (num_id_ordem) references orion_020 (id),
 constraint fk_orion_021_orion_010	foreign key (num_plano_mestre) references orion_010 (num_plano_mestre)
);

comment on table orion_021 is 'Tabela de Pré-Ordens - Itens';

--create sequence id_orion_021;

create INDEX INDX_ORION_21_ID_ORDEM on orion_021 (num_id_ordem);
create INDEX INDX_ORION_21_PLANO_MESTRE on orion_021 (num_plano_mestre);