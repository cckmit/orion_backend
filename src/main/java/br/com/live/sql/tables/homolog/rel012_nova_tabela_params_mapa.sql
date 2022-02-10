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