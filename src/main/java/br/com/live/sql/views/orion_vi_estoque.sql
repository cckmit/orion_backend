create or replace view orion_vi_estoque as
select rownum id, a.cditem_nivel99 nivel, a.cditem_grupo grupo, a.cditem_subgrupo sub, a.cditem_item item, a.deposito, a.qtde_estoque_atu quantidade,
       b.colecao, b.conta_estoque, b.linha_produto, b.artigo, b.artigo_cotas, c.origem_prod origem, o.permanente, c.codigo_cliente embarque
from estq_040 a, basi_030 b, basi_010 c, orion_vi_produtos o
where a.cditem_nivel99  = '1'
  and b.nivel_estrutura = a.cditem_nivel99
  and b.referencia = a.cditem_grupo
  and c.nivel_estrutura = a.cditem_nivel99
  and c.grupo_estrutura = a.cditem_grupo
  and c.subgru_estrutura = a.cditem_subgrupo
  and c.item_estrutura = a.cditem_item
  and o.grupo = a.cditem_grupo
  and exists (select 1
                from basi_205
               where basi_205.codigo_deposito = a.deposito
                 and basi_205.considera_tmrp  = 1)
order by a.cditem_nivel99, a.cditem_grupo, a.cditem_subgrupo, a.cditem_item, a.deposito
;
