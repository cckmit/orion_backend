create or replace view orion_vi_plano_mestre_tam as
select rownum id, a.num_plano_mestre, a.grupo, a.item, c.ordem_tamanho ordem, b.tamanho_ref sub,
nvl((select m.qtde_estoque
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_estoque,
nvl((select m.qtde_dem_acumulado
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_demanda,
nvl((select m.qtde_proc_acumulado
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_processo,
nvl((select m.qtde_saldo_acumulado
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_saldo,
nvl((select m.qtde_sugestao
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_sugestao,
nvl((select m.qtde_equalizado_sugestao
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_equalizado,
nvl((select m.qtde_dif_sugestao
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_diferenca,
nvl((select m.qtde_programada
   from orion_015 m
  where m.num_plano_mestre = a.num_plano_mestre
    and m.nivel = '1'
    and m.grupo = a.grupo
    and m.sub = b.tamanho_ref
    and m.item = a.item),0) qtde_programada
from orion_016 a, basi_020 b, basi_220 c
 where b.basi030_nivel030 = '1'
   and b.basi030_referenc = a.grupo
   and c.tamanho_ref = b.tamanho_ref   
order by a.num_plano_mestre, a.grupo, a.item, c.ordem_tamanho
;