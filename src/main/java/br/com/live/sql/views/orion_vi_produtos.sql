create or replace view orion_vi_produtos as
select rownum id, b.referencia grupo, b.descr_referencia descricao, b.colecao,  
       nvl((select 1 from basi_140 c 
             where c.colecao = b.colecao
               and c.descricao_espanhol like '%COLECAO PERMANENTE%'),0) permanente
 from basi_030 b
where b.nivel_estrutura = '1'
;