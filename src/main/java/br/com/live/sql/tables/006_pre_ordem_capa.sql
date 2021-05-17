-- Criar no Owner LIVE
create table orion_020
(id number(9),
 num_plano_mestre number(9),
 referencia varchar2(5),
 periodo number(4) default 0,
 alternativa number(2) default 0, 
 roteiro number(2) default 0,
 quantidade number(6) default 0,
 data date,  
 observacao varchar2(60),
 deposito number(3) default 0,
 situacao number(1) default 0, -- 0-OP não gerada / 1-OP gerada
 ordem_gerada number(9) default 0, -- Numero da OP gerada
 constraint orion_020_pk primary key (id),
 constraint fk_orion_020_orion_010	foreign key (num_plano_mestre) references orion_010 (num_plano_mestre)
);

comment on table orion_020 is 'Tabela de Pré-Ordens - Capa';

--create sequence id_orion_020;

create INDEX INDX_ORION_20_PLANO_MESTRE on orion_020 (num_plano_mestre);

-- ATAUALIZAR PRODUCAO

alter table orion_020
add (status varchar2(50) default 'PENDENTE');