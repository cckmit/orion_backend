create table orion_sup_005 
(pedido_compra number(9) default 0,
 num_requisicao number(9) default 0,
 email_enviado number(1) default 0);
 
 create index indx_orion_sup_005_ped_compra on orion_sup_005 (pedido_compra, num_requisicao_gerada);
 
 comment on table orion_sup_005 is 'Tabela com as requisições geradas automaticamente para os pedidos de compras';
 comment on column orion_sup_005.email_enviado is '0-Não, 1-Sim';