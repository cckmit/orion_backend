-- criar nova tabela para guardar os minutos médios de produção de uma peça
create table orion_com_151
(id varchar2(10) default '',
 ano number(4) default 0,
 tipo_meta number(1) default 0,
 minutos_mes_1 number(6,2) default 0,
 minutos_mes_2 number(6,2) default 0,
 minutos_mes_3 number(6,2) default 0,
 minutos_mes_4 number(6,2) default 0,
 minutos_mes_5 number(6,2) default 0,
 minutos_mes_6 number(6,2) default 0,
 minutos_mes_7 number(6,2) default 0,
 minutos_mes_8 number(6,2) default 0,
 minutos_mes_9 number(6,2) default 0,
 minutos_mes_10 number(6,2) default 0,
 minutos_mes_11 number(6,2) default 0,
 minutos_mes_12 number(6,2) default 0);

-- inicializar os dados
insert into orion_com_151 values ('2023-6', 2023, 6, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5);
insert into orion_com_151 values ('2023-7', 2023, 7, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5); 