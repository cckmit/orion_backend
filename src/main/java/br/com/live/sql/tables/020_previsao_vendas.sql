-- Criar no Owner LIVE
create table orion_040
(id varchar2(20), -- Coleção / Referência / Cor
 colecao number(3),
 grupo varchar2(5),
 item varchar2(6),
 col_tab_preco_sell_in number(2) default 0,
 mes_tab_preco_sell_in number(2) default 0,
 seq_tab_preco_sell_in number(2) default 0,
 valor_sell_in number(19,6) default 0,
 col_tab_preco_sell_out number(2) default 0,
 mes_tab_preco_sell_out number(2) default 0,
 seq_tab_preco_sell_out number(2) default 0,
 valor_sell_out number(19,6) default 0,
 qtde_previsao number(6) default 0,
 constraint orion_040_pk primary key (id)
);

comment on table orion_040 is 'Tabela de previsão de vendas';