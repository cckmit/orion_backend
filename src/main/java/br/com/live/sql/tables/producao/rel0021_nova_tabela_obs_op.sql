create table orion_cfc_220 (
  id varchar2(50), 
  estagio number(3),
  ordem_producao number(9),
  ordem_confeccao number(9),
  tipo_observacao number(3),
  observacao_adicional varchar2(500),
  constraint orion_cfc_220_pk primary key (id),
  constraint fk_orion_cfc_220_orion_cfc_221 foreign key (tipo_observacao) references orion_cfc_221 (id)
);

comment on table orion_cfc_220 is 'Tabela de Observaçao por Ordem/Pacote';

create table orion_cfc_221 (
  id number(6), 
  descricao varchar2(200),
  constraint orion_cfc_221_pk primary key (id)
);

comment on table orion_cfc_221 is 'Tabela de Tipos de Observaçao';