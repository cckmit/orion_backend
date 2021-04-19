create or replace view orion_vi_plano_mestre_itens as
select a."ID",a."NUM_PLANO_MESTRE",a."CODIGO",a."GRUPO",a."ITEM",a."RANK",a."QTDE_PREVISAO",a."QTDE_ESTOQUE",a."QTDE_DEM_PLANO1",a."QTDE_DEM_PLANO2",a."QTDE_DEM_PLANO3",a."QTDE_DEM_PLANO4",a."QTDE_DEM_PLANO5",a."QTDE_DEM_PLANO6",a."QTDE_DEM_PLANO7",a."QTDE_DEM_PLANO8",a."QTDE_PROC_PLANO1",a."QTDE_PROC_PLANO2",a."QTDE_PROC_PLANO3",a."QTDE_PROC_PLANO4",a."QTDE_PROC_PLANO5",a."QTDE_PROC_PLANO6",a."QTDE_PROC_PLANO7",a."QTDE_PROC_PLANO8",a."QTDE_SALDO_PLANO1",a."QTDE_SALDO_PLANO2",a."QTDE_SALDO_PLANO3",a."QTDE_SALDO_PLANO4",a."QTDE_SALDO_PLANO5",a."QTDE_SALDO_PLANO6",a."QTDE_SALDO_PLANO7",a."QTDE_SALDO_PLANO8",a."QTDE_DEM_ACUMULADO",a."QTDE_PROC_ACUMULADO",a."QTDE_SALDO_ACUMULADO",a."QTDE_SUGESTAO",a."QTDE_EQUALIZADO_SUGESTAO",a."QTDE_DIF_SUGESTAO",a."QTDE_PROGRAMADA",
       nvl((select 'S' from orion_030 o
             where o.nivel = '1'
               and o.grupo = a.grupo
               and o.item = a.item),'N') sug_cancel_prod,
       nvl((select max(b.codigo_cliente) from basi_010 b
             where b.nivel_estrutura = '1'
               and b.grupo_estrutura = a.grupo
               and b.item_estrutura = a.item
             ),' ') embarque
from orion_016 a
;
