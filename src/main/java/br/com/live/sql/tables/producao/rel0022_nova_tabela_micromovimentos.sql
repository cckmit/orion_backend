create table orion_eng_230 (
  id varchar2(10), 
  descricao varchar2(50),
  tempo number(10,4),
  interferencia number(6,2),
  constraint orion_eng_230_pk primary key (id)
);

comment on table orion_cfc_220 is 'Cadastro de Micromovimentos';