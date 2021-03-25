create or replace view orion_vi_produtos_publico as
select rownum id, b.grupo, b.codigo_informacao codigo from basi_400 b 
where b.nivel = '1'
  and b.tipo_informacao = 9;