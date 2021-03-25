create or replace view orion_vi_colecoes as
select b.colecao, b.descr_colecao from basi_140 b
where b.descricao_espanhol not like '%COLECAO PERMANENTE%'
  and b.descricao_espanhol not like '%COLECAO ANTIGA%'
  and b.colecao > 0
order by colecao
; 
