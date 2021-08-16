create table orion_bi_001
(id varchar2(10), 
 area_modulo varchar2(5),
 atividade number(2),
 descricao varchar2(100),
 ferramenta varchar2(1000),
 frequencia varchar2(1000),
 planilha varchar2(1000),
 extrator varchar2(1000),
 help varchar2(1000),
 constraint orion_bi_001_pk primary key (id)
);

comment on table orion_bi_001 is 'Tabela de programas - B.I';
