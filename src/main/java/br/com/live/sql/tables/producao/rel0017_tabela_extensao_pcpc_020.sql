create table orion_210
(ordem_producao number(9),
 lembrete varchar2(100),
 constraint orion_210_pk primary key (ordem_producao));
 
comment on table orion_210 is 'Tabela de extensão de campos da tabela de ordens - PCPC_020';