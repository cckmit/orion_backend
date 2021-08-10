alter table orion_030
add (colecao number(3));

insert into orion_030 (
select a.id || '.' || c.colecao id,
       a.nivel,
       a.grupo,
       a.item,      
       c.colecao
from orion_030 a, basi_030 b, basi_140 c
 where b.nivel_estrutura = '1' 
   and b.referencia = a.grupo
   and c.colecao = b.colecao
   and c.descricao_espanhol not like '%COLECAO PERMANENTE%'   
union
select a.id || '.' || nvl((select max(f.cd_agrupador)
                             from basi_632 f
                            where f.grupo_ref = a.grupo
                              and f.item_ref = a.item),c.colecao) id,
       a.nivel,
       a.grupo,
       a.item,      
       nvl((select max(f.cd_agrupador)
              from basi_632 f
             where f.grupo_ref = a.grupo
               and f.item_ref = a.item),c.colecao) colecao
from orion_030 a, basi_030 b, basi_140 c
 where b.nivel_estrutura = '1' 
   and b.referencia = a.grupo
   and c.colecao = b.colecao
   and c.descricao_espanhol like '%COLECAO PERMANENTE%'   
);

commit;

delete from orion_030 o
where length(o.id) <= 14 ;

commit;