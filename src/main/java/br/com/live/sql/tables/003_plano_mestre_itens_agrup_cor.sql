-- Criar no Owner LIVE
create table orion_016
(id number(9),
num_plano_mestre number(9),
codigo varchar2(20),
grupo varchar2(5),
item varchar2(6),
--embarque varchar2(20),
--familia varchar2(20),
--sug_cancel_prod number(1),
rank number(5),
qtde_previsao number(6),
qtde_estoque number(6),
qtde_dem_plano1 number(6),
qtde_dem_plano2 number(6),
qtde_dem_plano3 number(6),
qtde_dem_plano4 number(6),
qtde_dem_plano5 number(6),
qtde_dem_plano6 number(6),
qtde_dem_plano7 number(6),
qtde_dem_plano8 number(6),	
qtde_proc_plano1 number(6),
qtde_proc_plano2 number(6),
qtde_proc_plano3 number(6),
qtde_proc_plano4 number(6),
qtde_proc_plano5 number(6),
qtde_proc_plano6 number(6),
qtde_proc_plano7 number(6),
qtde_proc_plano8 number(6),	
qtde_saldo_plano1 number(6),
qtde_saldo_plano2 number(6),
qtde_saldo_plano3 number(6),
qtde_saldo_plano4 number(6),
qtde_saldo_plano5 number(6),
qtde_saldo_plano6 number(6),
qtde_saldo_plano7 number(6),
qtde_saldo_plano8 number(6),	
qtde_dem_acumulado number(6),
qtde_proc_acumulado number(6),
qtde_saldo_acumulado number(6),
qtde_sugestao number(6),
qtde_equalizado_sugestao number(6),
qtde_dif_sugestao number(6),
qtde_programada number(6),
 constraint orion_016_pk primary key (id),
 constraint fk_orion_016_orion_010	foreign key (num_plano_mestre) references orion_010 (num_plano_mestre) 
);

comment on table orion_016 is 'Tabela plano mestre de produção - itens por referencia e cor';

create sequence id_orion_016;

create INDEX INDX_ORION_16_PLANO_MESTRE on orion_016 (num_plano_mestre);
create INDEX INDX_ORION_16_ITENS on orion_016 (num_plano_mestre, grupo, item);
