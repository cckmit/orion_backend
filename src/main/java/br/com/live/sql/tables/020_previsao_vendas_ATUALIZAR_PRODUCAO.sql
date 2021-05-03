create table orion_040
(id number(9),
 descricao varchar2(100),
 colecao number(3),
 col_tab_preco_sell_in number(2) default 0,
 mes_tab_preco_sell_in number(2) default 0,
 seq_tab_preco_sell_in number(2) default 0,
 col_tab_preco_sell_out number(2) default 0,
 mes_tab_preco_sell_out number(2) default 0,
 seq_tab_preco_sell_out number(2) default 0,

 constraint orion_040_pk primary key (id)
);

comment on table orion_040 is 'Tabela de previsão de vendas - capa';

create table orion_041
(id varchar2(50), -- idPrevisaoVendas - grupo - item
 id_previsao_vendas number(9),
 grupo varchar2(5),
 item varchar2(6),
 valor_sell_in number(19,6) default 0,
 valor_sell_out number(19,6) default 0,
 grupo_base varchar2(5),
 item_base varchar2(6), 
 qtde_vendida_base number(6) default 0,
 percentual_aplicar number(6,2) default 0,
 qtde_previsao number(6) default 0,
 constraint orion_041_pk primary key (id),
 constraint fk_orion_041_orion_040  foreign key (id_previsao_vendas) references orion_040 (id)
); 

comment on table orion_041 is 'Tabela de previsão de vendas - itens';
create INDEX INDX_ORION_41_PREV_VENDAS on orion_041 (id_previsao_vendas);
create INDEX INDX_ORION_41_PRODUTO on orion_041 (id_previsao_vendas, grupo, item);

create table orion_042
(id varchar2(50), -- idPrevisaoVendas - grupo - item - sub
 id_previsao_vendas number(9),
 id_item_previsao_vendas varchar2(50),
 grupo varchar2(5),
 item varchar2(6),
 sub varchar2(3),
 qtde_previsao number(6) default 0,
 constraint orion_042_pk primary key (id),
 constraint fk_orion_042_orion_040  foreign key (id_previsao_vendas) references orion_040 (id),
 constraint fk_orion_042_orion_041  foreign key (id_item_previsao_vendas) references orion_041 (id)
); 

comment on table orion_042 is 'Tabela de previsão de vendas - itens e tamanhos';
create INDEX INDX_ORION_42_PREV_VENDAS on orion_042 (id_previsao_vendas);
create INDEX INDX_ORION_42_ITEM_PREV_VENDAS on orion_042 (id_previsao_vendas, id_item_previsao_vendas);
create INDEX INDX_ORION_42_PRODUTO on orion_042 (id_previsao_vendas, grupo, item, sub);

