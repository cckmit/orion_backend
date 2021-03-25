create or replace view orion_vi_pedidos as
select p.pedido_venda, p.num_periodo_prod from pedi_100 p
where p.tecido_peca      = '1'   
  and p.cod_cancelamento = 0
  and p.situacao_venda  <> 10
order by p.pedido_venda ;  