create or replace view orion_vi_itens_sub_colecoes as
select rownum id, a.cd_agrupador colecao, a.grupo_ref grupo, b.subgrupo_ref sub, b.item_ref item from basi_631 a, basi_632 b
where b.cd_agrupador (+) = a.cd_agrupador
  and b.grupo_ref (+) = a.grupo_ref;
