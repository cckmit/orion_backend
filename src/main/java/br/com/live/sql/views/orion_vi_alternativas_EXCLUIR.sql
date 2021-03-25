create or replace view orion_vi_alternativas as
select max(rownum) id, b.nivel_item nivel, b.grupo_item grupo, b.item_item item, b.alternativa_item alternativa, c.descricao from basi_050 b, basi_070 c
 where b.nivel_item       = '1'
   and c.nivel = b.nivel_item
   and c.grupo = b.grupo_item
   and c.alternativa = b.alternativa_item
group by b.nivel_item, b.grupo_item, b.item_item, b.alternativa_item, c.descricao
;

