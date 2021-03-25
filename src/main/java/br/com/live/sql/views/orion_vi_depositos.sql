create or replace view orion_vi_depositos as
select b.codigo_deposito id, b.descricao from basi_205 b
where b.descricao like 'DEP N1%'
  and b.tipo_volume in (0,1)
  and b.considera_tmrp = 1
order by b.codigo_deposito  
;