alter table orion_051
add (cod_estagio_defeito number(2) default 0);

update orion_051 a 
   set a.cod_estagio_defeito = (select min(e.codigo_estagio) from efic_040 e
                                where e.codigo_motivo = a.cod_motivo);