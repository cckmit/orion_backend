create or replace view orion_vi_demanda as
select rownum id, live_view_demanda."NIVEL",live_view_demanda."GRUPO",live_view_demanda."SUB",live_view_demanda."ITEM",
                  live_view_demanda."DEPOSITO",live_view_demanda."QUANTIDADE",live_view_demanda."PERIODO",
                  live_view_demanda."COLECAO",live_view_demanda."CONTA_ESTOQUE",live_view_demanda."LINHA_PRODUTO",
                  live_view_demanda."ARTIGO",live_view_demanda."ARTIGO_COTAS",  live_view_demanda."ORIGEM", live_view_demanda."NATUREZA",
                  nvl(live_view_demanda."NUMERO_CONTROLE",0) numero_controle, live_view_demanda."PEDIDO",
                  o.permanente, live_view_demanda."EMBARQUE", live_view_demanda."SITUACAO_VENDA"
from (select a.cd_it_pe_nivel99 nivel, a.cd_it_pe_grupo grupo, a.cd_it_pe_subgrupo sub, a.cd_it_pe_item item,
       a.codigo_deposito deposito, (a.qtde_pedida - a.qtde_faturada) quantidade, p.num_periodo_prod periodo,
       b.colecao, b.conta_estoque, b.linha_produto, b.artigo, b.artigo_cotas, c.origem_prod origem, a.cod_nat_op natureza, p.numero_controle,
       p.pedido_venda pedido, w.grupo_embarque embarque, p.situacao_venda
from pedi_100 p, pedi_110 a, basi_030 b, basi_010 c, basi_590 w
where p.situacao_venda  <> 10
  and p.tecido_peca      = '1'
  and a.pedido_venda     = p.pedido_venda
  and a.cod_cancelamento = 0
  and b.nivel_estrutura  = a.cd_it_pe_nivel99
  and b.referencia = a.cd_it_pe_grupo
  and c.nivel_estrutura = a.cd_it_pe_nivel99
  and c.grupo_estrutura = a.cd_it_pe_grupo
  and c.subgru_estrutura = a.cd_it_pe_subgrupo
  and c.item_estrutura = a.cd_it_pe_item
  and w.nivel (+) = a.cd_it_pe_nivel99
  and w.grupo (+) = a.cd_it_pe_grupo
  and w.subgrupo (+) = a.cd_it_pe_subgrupo
  and w.item (+) = a.cd_it_pe_item
  and exists (select 1
                from basi_205
               where basi_205.codigo_deposito = a.codigo_deposito
                 and basi_205.considera_tmrp  = 1)
UNION
select a.item_nivel99 nivel, a.item_grupo grupo, a.item_sub sub, a.item_item item,
       a.codigo_deposito deposito, a.qtde_pedida quantidade, c.periodo_producao periodo,
       b.colecao, b.conta_estoque, b.linha_produto, b.artigo, b.artigo_cotas, d.origem_prod origem, a.cod_nat_op natureza, i.numero_controle,
       i.pedido_venda pedido, w.grupo_embarque embarque, 0 situacao_venda
from inte_100 i, inte_110 a, basi_030 b, pcpc_010 c, basi_010 d, basi_590 w
where i.tecido_peca     = '1'
  and i.tipo_registro   = 1
  and a.pedido_venda    = i.pedido_venda
  and b.nivel_estrutura = a.item_grupo
  and b.referencia = a.item_sub
  and c.codigo_empresa = i.codigo_empresa
  and c.area_periodo   = 1
  and i.data_entrega between c.data_ini_fatu and c.data_fim_fatu
  and d.nivel_estrutura = a.item_nivel99
  and d.grupo_estrutura = a.item_grupo
  and d.subgru_estrutura = a.item_sub
  and d.item_estrutura = a.item_item
  and w.nivel (+) = a.item_nivel99
  and w.grupo (+) =  a.item_grupo
  and w.subgrupo (+) = a.item_sub
  and w.item (+) = a.item_item
  and exists (select 1
                from basi_205
               where basi_205.codigo_deposito = a.codigo_deposito
                 and basi_205.considera_tmrp  = 1)) live_view_demanda, orion_vi_produtos o
where o.grupo = live_view_demanda.grupo
order by live_view_demanda."NIVEL",live_view_demanda."GRUPO",live_view_demanda."SUB",live_view_demanda."ITEM",live_view_demanda."DEPOSITO";