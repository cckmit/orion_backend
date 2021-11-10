create or replace view orion_vi_processo as
select rownum id, a.proconf_nivel99 nivel, a.proconf_grupo grupo, a.proconf_subgrupo sub, a.proconf_item item,
       b.periodo_producao periodo,
       nvl(sum(a.qtde_a_produzir_pacote),0) quantidade,
       --nvl(sum(a.qtde_pecas_prog),0) - nvl(sum(a.qtde_pecas_prod),0) - nvl(sum(a.qtde_pecas_2a),0) - nvl(sum(a.qtde_perdas),0) quantidade,
       --nvl(sum(a.qtde_conserto),0),
       c.colecao, c.conta_estoque, c.linha_produto, c.artigo, c.artigo_cotas, d.origem_prod origem, o.permanente, nvl(w.grupo_embarque,0) embarque
from pcpc_040 a, pcpc_020 b, basi_030 c, basi_010 d, orion_vi_produtos o, (select min(t.grupo_embarque) grupo_embarque, t.nivel, t.grupo, t.subgrupo, t.item from basi_590 t group by t.nivel, t.grupo, t.subgrupo, t.item) w
where b.ordem_producao = a.ordem_producao
  and b.ultimo_estagio = a.codigo_estagio
  and b.cod_cancelamento = 0
  and a.proconf_nivel99  = '1'
  and c.nivel_estrutura  = a.proconf_nivel99
  and c.referencia       = a.proconf_grupo
  and d.nivel_estrutura = a.proconf_nivel99
  and d.grupo_estrutura = a.proconf_grupo
  and d.subgru_estrutura = a.proconf_subgrupo
  and d.item_estrutura = a.proconf_item
  and o.grupo = a.proconf_grupo
  and w.nivel (+) = a.proconf_nivel99
  and w.grupo (+) = a.proconf_grupo
  and w.subgrupo (+) = a.proconf_subgrupo
  and w.item (+) = a.proconf_item
group by rownum, a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, b.periodo_producao,
         c.colecao, c.conta_estoque, c.linha_produto, c.artigo, c.artigo_cotas, d.origem_prod, o.permanente, w.grupo_embarque
order by a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, b.periodo_producao
;
