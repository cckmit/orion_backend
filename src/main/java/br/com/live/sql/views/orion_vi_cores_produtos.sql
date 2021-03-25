create or replace view orion_vi_cores_produtos as
select max(rownum) id, a.item_estrutura item, a.descricao_15 descricao
from basi_010 a
where a.nivel_estrutura = '1'
  and a.item_ativo = 1
  and a.descricao_15 <> '.'
group by a.item_estrutura, a.descricao_15
order by a.item_estrutura, a.descricao_15
;
