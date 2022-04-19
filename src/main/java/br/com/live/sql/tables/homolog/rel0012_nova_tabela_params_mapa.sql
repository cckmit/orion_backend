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

create table orion_130
(numero_caixa number(9),
 situacao_caixa number(1) default 0,
 usuario number(5) default 0,
 usuario_systextil varchar2(100),
 data_hora_inicio Date,
 data_hora_fim Date,
 repositor varchar2(100),
 endereco varchar2(20),
 constraint orion_130_pk primary key (numero_caixa));
 
comment on table orion_130 is 'Tabela de Abertura de Caixas';

create table orion_131
(numero_tag varchar2(22),
 numero_caixa number(9),
 periodo_producao number(4) default 0,
 ordem_producao number(9) default 0,
 ordem_confeccao number(5) default 0,
 sequencia number(4) default 0,
 constraint orion_131_pk primary key (numero_tag));
 
comment on table orion_131 is 'Tabela de Tags Lidos por Caixa';

create table orion_132
(deposito number(9),
 rua_inicio varchar2(4) default '',
 rua_fim varchar2(4) default '',
 box_inicio number(4) default 0,
 box_fim number(4) default 0,
 constraint orion_132_pk primary key (deposito));
 
comment on table orion_132 is 'Tabela de Parâmetros para Geração do Mapa do Pré Endereçamento de Caixas';

CREATE INDEX INDX_ORION_131_CAIXAS ON orion_131 (numero_caixa, numero_tag);

alter table orion_001
add (usuario_repositor number(1) default 0);