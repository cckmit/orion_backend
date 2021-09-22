alter table orion_011
add (previsoes varchar2(200));

update orion_011
set previsoes = id_previsao_vendas;
commit;

ALTER TABLE orion_011
DROP COLUMN id_previsao_vendas;