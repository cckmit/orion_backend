-- Criar no Owner LIVE
create table orion_025
(id varchar2(20), -- num_plano_mestre - estagio   
 num_plano_mestre number(9), 
 estagio number(2),

 qtde_capacidade_pecas number(9),
 qtde_plano_pecas number(9),
 qtde_programado_pecas number(9),
 perc_ocupacao_pecas number(12,3),
 qtde_falta_sobra_pecas number(9),

 qtde_capacidade_minutos number(9),
 qtde_plano_minutos number(12,3),
 qtde_programado_minutos number(12,3), 
 perc_ocupacao_minutos number(12,3),
 qtde_falta_sobra_minutos number(12,3),
  
 constraint orion_025_pk primary key (id), 
 constraint fk_orion_025_orion_010	foreign key (num_plano_mestre) references orion_010 (num_plano_mestre)
);

comment on table orion_025 is 'Tabela de Ocupação dos Estágios do Plano Mestre';

create INDEX INDX_ORION_25_PLANO_MESTRE on orion_025 (num_plano_mestre);
create INDEX INDX_ORION_25_PLANO_ESTAGIO on orion_025 (num_plano_mestre, estagio);