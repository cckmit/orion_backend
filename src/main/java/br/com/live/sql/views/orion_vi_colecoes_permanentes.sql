create or replace view orion_vi_colecoes_permanentes as
select b.colecao, b.descr_colecao from basi_140 b
where b.descricao_espanhol like '%COLECAO PERMANENTE%'
  and b.colecao > 0 
order by colecao;
