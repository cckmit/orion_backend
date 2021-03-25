create or replace view orion_vi_roteiros as 
select m.nivel_estrutura nivel, m.grupo_estrutura grupo, m.numero_alternati alternativa, m.numero_roteiro roteiro
  from mqop_050 m
 where m.nivel_estrutura  = '1'
group by m.nivel_estrutura, m.grupo_estrutura, m.numero_alternati, m.numero_roteiro 
;
