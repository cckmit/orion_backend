create or replace view orion_vi_itens_x_tempo_estagio as
select tempo.estagio, tempo.nivel, tempo.grupo, tempo.sub, tempo.item, max(tempo.tempo) tempo
from (select m.codigo_estagio estagio, m.nivel_estrutura nivel, m.grupo_estrutura grupo, m.subgru_estrutura sub, m.item_estrutura item, m.numero_alternati, m.numero_roteiro, sum(m.minutos_homem) tempo
        from mqop_050 m
       where m.nivel_estrutura  = '1'
         and m.minutos_homem > 0
       group by m.nivel_estrutura, m.grupo_estrutura, m.subgru_estrutura, m.item_estrutura, m.numero_alternati, m.numero_roteiro, m.codigo_estagio
       order by m.nivel_estrutura, m.grupo_estrutura, m.subgru_estrutura, m.item_estrutura, m.numero_alternati, m.numero_roteiro, m.codigo_estagio
) tempo
group by tempo.estagio, tempo.nivel, tempo.grupo, tempo.sub, tempo.item;
