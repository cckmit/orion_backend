create table orion_200
(id varchar2(30), -- ordem_producao - nivel_tecido . grupo_tecido . sub_tecido . item_tecido      
 ordem_producao number(9),
 nivel_tecido varchar2(1),
 grupo_tecido varchar2(5),
 sub_tecido varchar2(3),
 item_tecido varchar2(6),
 quantidade number(9,3),
 constraint orion_200_pk primary key (id));
 
comment on table orion_200 is 'Tabela de tecidos reservados para a ordem pelo PPCP (Estágio 1)';

create INDEX INDX_ORION_200_ORDEM on orion_200 (ordem_producao);
create INDEX INDX_ORION_200_TECIDO on orion_200 (nivel_tecido, grupo_tecido, sub_tecido, item_tecido);