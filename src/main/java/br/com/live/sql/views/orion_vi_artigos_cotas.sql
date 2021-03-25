create or replace view orion_vi_artigos_cotas as
select b.artigo_cotas id, b.descr_artigo descricao from basi_295 b
where b.nivel_estrutura = '1'
  and b.descr_artigo <> '.'
order by b.artigo_cotas