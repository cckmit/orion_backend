create or replace view orion_vi_itens_proc_dem_aberto as
select b.cd_it_pe_nivel99 nivel, b.cd_it_pe_grupo grupo, b.cd_it_pe_subgrupo sub, b.cd_it_pe_item item, c.narrativa descricao,
       a.num_periodo_prod periodo,
       nvl(sum(b.qtde_pedida - b.qtde_faturada),0) qtde_pedido, 0 qtde_producao
from pedi_100 a, pedi_110 b, basi_010 c
where a.tecido_peca = '1'
  and a.situacao_venda <> 10
  and a.cod_cancelamento = 0
  and b.pedido_venda = a.pedido_venda
  and b.cod_cancelamento = 0
  and (b.qtde_pedida - b.qtde_faturada) > 0
  and c.nivel_estrutura = b.cd_it_pe_nivel99
  and c.grupo_estrutura = b.cd_it_pe_grupo
  and c.subgru_estrutura = b.cd_it_pe_subgrupo
  and c.item_estrutura = b.cd_it_pe_item
group by b.cd_it_pe_nivel99, b.cd_it_pe_grupo, b.cd_it_pe_subgrupo, b.cd_it_pe_item, c.narrativa, a.num_periodo_prod
union
select b.proconf_nivel99 nivel, b.proconf_grupo grupo, b.proconf_subgrupo sub, b.proconf_item item, c.narrativa descricao,
       a.periodo_producao periodo,
       0 qtde_pedido, nvl(sum(b.qtde_a_produzir_pacote),0) qtde_producao
from pcpc_020 a, pcpc_040 b, basi_010 c
where a.cod_cancelamento = 0
  and b.ordem_producao = a.ordem_producao
  and b.codigo_estagio = a.ultimo_estagio
  and b.qtde_a_produzir_pacote > 0
  and c.nivel_estrutura = b.proconf_nivel99
  and c.grupo_estrutura = b.proconf_grupo
  and c.subgru_estrutura = b.proconf_subgrupo
  and c.item_estrutura = b.proconf_item
group by b.proconf_nivel99, b.proconf_grupo, b.proconf_subgrupo, b.proconf_item, c.narrativa, a.periodo_producao;
