drop table orion_exp_360;
drop table orion_exp_361;
drop table orion_exp_362;
drop table orion_exp_363;

create table orion_exp_360 (
id number(9),
situacao number(1) default 0,  
id_usuario number(9) default 0,
constraint orion_exp_360_pk primary key (id) 
);

comment on table orion_exp_360 is 'Tabela de lote de coleta de pedidos';

create table orion_exp_361 (
id number(9),
id_lote number(9) default 0,
id_area number(6) default 0,
constraint orion_exp_361_pk primary key (id),
constraint fk_orion_exp_361_exp_360 foreign key (id_lote) references orion_exp_360 (id)
);

comment on table orion_exp_361 is 'Tabela de lote de coleta de pedidos - Áreas';

create table orion_exp_362 (
id number(9),
id_lote_area number(9) default 0,
pedido_venda number(6) default 0,
nivel varchar2(1) default '',
grupo varchar2(5) default '',
sub varchar2(3) default '',
item varchar2(6) default '',
endereco varchar2(10) default '',
qtde_coletar number(6) default 0,
constraint orion_exp_362_pk primary key (id),
constraint fk_orion_exp_362_exp_361 foreign key (id_lote_area) references orion_exp_361 (id)
);

comment on table orion_exp_362 is 'Tabela de lote de coleta de pedidos - Pedidos';

create table orion_exp_363 (
id number(9),
id_lote_area number(9) default 0,
id_coletor number(9) default 0,
constraint orion_exp_363_pk primary key (id),
constraint fk_orion_exp_363_exp_361 foreign key (id_lote_area) references orion_exp_361 (id)
);

comment on table orion_exp_363 is 'Tabela de lote de coleta de pedidos - Coletores';

create index indx_orion_exp_362_pv on orion_exp_362 (pedido_venda);