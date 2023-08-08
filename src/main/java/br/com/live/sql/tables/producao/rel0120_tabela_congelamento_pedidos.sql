create table orion_com_310  
(pedido_venda number(9),
 data_entr_venda date,
 situacao_venda number(2),
 constraint orion_com_310_pk primary key (pedido_venda)
 );

comment on table orion_com_310 is 'Tabela de congelamento de pedidos de venda';