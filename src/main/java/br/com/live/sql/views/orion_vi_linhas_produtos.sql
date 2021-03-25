create or replace view orion_vi_linhas_produtos as
select b.linha_produto id, b.descricao_linha descricao from basi_120 b
where b.nivel_estrutura = '1'
order by b.linha_produto;