create or replace view orion_vi_itens_x_colecoes as
 select basi_030.colecao, basi_030.nivel_estrutura nivel, basi_030.referencia, basi_010.subgru_estrutura tamanho, basi_010.item_estrutura cor, basi_010.narrativa descricao
  from basi_030, basi_010
 where basi_030.nivel_estrutura = '1'
   and basi_010.nivel_estrutura = basi_030.nivel_estrutura
   and basi_010.grupo_estrutura = basi_030.referencia
   and basi_010.item_ativo = 0
union all
select c.cd_agrupador colecao, a.nivel_estrutura nivel, a.referencia, b.subgru_estrutura tamanho, b.item_estrutura cor, b.narrativa descricao
  from basi_632 c, basi_030 a, basi_010 b
 where a.nivel_estrutura = '1'
   and a.referencia = c.grupo_ref
   and a.colecao in (select basi_140.colecao from basi_140
                      where basi_140.descricao_espanhol like '%PERMANENTE%')
   and b.nivel_estrutura = '1'
   and b.grupo_estrutura = c.grupo_ref
   and b.subgru_estrutura = c.subgrupo_ref
   and b.item_estrutura = c.item_ref
   and b.item_ativo = 0;
