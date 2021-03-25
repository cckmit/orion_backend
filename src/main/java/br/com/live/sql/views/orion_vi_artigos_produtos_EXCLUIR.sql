create or replace view orion_vi_artigos_produtos as
select b.artigo id, b.descr_artigo descricao from basi_290 b
where b.nivel_estrutura = '1'
  and b.descr_artigo <> '.'
order by b.artigo