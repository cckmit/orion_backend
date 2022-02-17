create table orion_110
(deposito number(9),
 bloco_inicio varchar2(4) default '',
 bloco_fim varchar2(4) default '',
 corredor_inicio number(4) default 0,
 corredor_fim number(4) default 0,
 box_inicio number(4) default 0,
 box_fim number(4) default 0,
 cesto_inicio number(4) default 0,
 cesto_fim number(4) default 0,
 constraint orion_110_pk primary key (deposito));
 
comment on table orion_110 is 'Tabela de Parâmetros para Geração do Mapa do Deposíto';

create table orion_120
(artigo number(9),
 quant_pecas_cesto number(6) default 0,
 quant_perc_0 number(4) default 0,
 quant_perc_1 number(4) default 0,
 quant_perc_40 number(4) default 0,
 quant_perc_41 number(4) default 0,
 quant_perc_94 number(4) default 0,
 quant_perc_95 number(4) default 0,
 quant_perc_99 number(4) default 0,
 constraint orion_120_pk primary key (artigo));
 
comment on table orion_120 is 'Tabela de Capacidades do Artigo';