-- Criar no Owner LIVE
create table orion_017
(num_plano_mestre number(9),
 num_item_plano_mestre number(9),
 alternativa number(2),
 roteiro number(2),
 periodo number(4),
 multiplicador number(3),
 constraint orion_017_pk primary key (num_item_plano_mestre),
 constraint fk_orion_017_orion_010	foreign key (num_plano_mestre) references orion_010 (num_plano_mestre),
 constraint fk_orion_017_orion_016	foreign key (num_item_plano_mestre) references orion_016 (id)
);

comment on table orion_017 is 'Tabela plano mestre de produção - Parâmetros para distribuição e geração de ordens';

create INDEX INDX_ORION_17_PLANO_MESTRE on orion_017 (num_plano_mestre);
create INDEX INDX_ORION_17_ITENS on orion_017 (num_plano_mestre, num_item_plano_mestre);