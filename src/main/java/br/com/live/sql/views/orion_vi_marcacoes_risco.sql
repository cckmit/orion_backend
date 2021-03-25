create or replace view orion_vi_marcacoes_risco as
select rownum id, p.grupo_estrutura grupo, p.codigo_risco, p.alternativa_item, p.tamanho, p.qtde_marc_proj, b.colecao
from pcpc_200 p, basi_030 b
where p.ordem_estrutura = (select min(a.sequencia)
                             from basi_050 a
                            where a.nivel_item = '1'
                              and a.grupo_item = p.grupo_estrutura
                              --and a.sub_item   = p.tamanho
                              and a.alternativa_item = p.alternativa_item
                              and a.tecido_principal = 1)
 and b.nivel_estrutura = '1'                              
 and b.referencia = p.grupo_estrutura
order by p.grupo_estrutura, p.codigo_risco, p.ordem_estrutura, p.alternativa_item, p.tamanho, p.qtde_marc_proj
;      

