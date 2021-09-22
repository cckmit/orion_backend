create or replace view orion_vi_itens as
select max(rownum) id, b.grupo_estrutura grupo, b.item_estrutura item, max(f.descr_referencia) || ' - ' || max(b.descricao_15) descricao, max(f.colecao) colecao, max(b.codigo_cliente) embarque,
       max(b.numero_alternati) alternativa_padrao, max(b.numero_roteiro) roteiro_padrao, max(f.risco_padrao) risco_padrao,
       nvl((select 1 from basi_140 c
             where c.colecao = f.colecao
               and c.descricao_espanhol like '%COLECAO PERMANENTE%'),0) permanente
 from basi_030 f, basi_010 b
where f.nivel_estrutura = '1'
  and b.nivel_estrutura = f.nivel_estrutura
  and b.grupo_estrutura = f.referencia
group by b.grupo_estrutura, b.item_estrutura, f.colecao
order by b.grupo_estrutura, b.item_estrutura, f.colecao
;
