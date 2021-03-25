create or replace view orion_vi_embarques as
select min(rownum) id, b.codigo_cliente descricao from basi_010 b where b.codigo_cliente like '% EMBARQUE'
group by b.codigo_cliente
order by b.codigo_cliente;