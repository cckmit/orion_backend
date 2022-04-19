create table orion_140
(id varchar2(15),
 cod_estacao number(9),
 cod_representante number(6) default 0,
 tipo_meta number(1) default 0,
 valor_categoria_1 number(12,4) default 0,
 valor_categoria_2 number(12,4) default 0,
 valor_categoria_3 number(12,4) default 0,
 valor_categoria_4 number(12,4) default 0,
 valor_categoria_5 number(12,4) default 0,
 valor_categoria_6 number(12,4) default 0,
 valor_categoria_7 number(12,4) default 0,
 valor_categoria_8 number(12,4) default 0,
 valor_categoria_9 number(12,4) default 0,
 valor_categoria_10 number(12,4) default 0,
 constraint orion_140_pk primary key (id),
 constraint fk_orion_140_orion_070 foreign key (cod_estacao) references orion_070 (cod_estacao));
 
comment on table orion_140 is 'Tabela de Metas por Categoria - Embarque / Emissao';

create table orion_150
(id varchar2(100),
 ano number(4),
 descricao varchar2(50),
 tipo_meta number(1) default 0,
 valor_mes_1 number(12,4) default 0,
 valor_mes_2 number(12,4) default 0,
 valor_mes_3 number(12,4) default 0,
 valor_mes_4 number(12,4) default 0,
 valor_mes_5 number(12,4) default 0,
 valor_mes_6 number(12,4) default 0,
 valor_mes_7 number(12,4) default 0,
 valor_mes_8 number(12,4) default 0,
 valor_mes_9 number(12,4) default 0,
 valor_mes_10 number(12,4) default 0,
 valor_mes_11 number(12,4) default 0,
 valor_mes_12 number(12,4) default 0,
 constraint orion_150_pk primary key (id));
 
comment on table orion_150 is 'Tabela de Metas do Orçamento - Faturamento / Vendas';

ALTER TABLE pedi_085
ADD LIVE_AGRUP_TIPO_CLIENTE varchar2(50);

update pedi_085 a
set a.live_agrup_tipo_cliente = 'Multimarcas'
where a.tipo_cliente in (0,1,2,3,5,6,7,8,9,10,11,22,23,32,99);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Franquias Nacionais'
where a.tipo_cliente in (4,35);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Exportação Multimarcas'
where a.tipo_cliente in (12,18,19,20,26,27,28,30);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'E-Commerce Varejo'
where a.tipo_cliente in (13,14,24);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Exportação Live! Clothing'
where a.tipo_cliente in (15);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Franquias Internacionais'
where a.tipo_cliente in (16);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Lojas Próprias'
where a.tipo_cliente in (17,37);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Stúdio Live!'
where a.tipo_cliente in (21,36);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Marketing'
where a.tipo_cliente in (25);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'Outlets'
where a.tipo_cliente in (33);
--------------------------------------------------------------------
update pedi_085 a
set a.live_agrup_tipo_cliente = 'BUFFER (PE)'
where a.tipo_cliente in ((34));















