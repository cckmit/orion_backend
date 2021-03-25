create or replace view orion_vi_publicos_alvos as
select h.codigo id, h.descricao from hdoc_001 h
where h.tipo = 9
order by h.codigo;