create table orion_sup_001 (
  id number(6),
  descricao varchar2(100),
  cod_empresa number(3),
  divisao_producao number(4),
  centro_custo number(6),    
  constraint orion_sup_001_pk primary key (id)
);

comment on table orion_sup_001 is 'Tabela capa da pré-requisição de almoxarifado';

create table orion_sup_002 (
  id varchar2(15), -- id_pre_requisicao + '-' + sequencia 
  id_pre_requisicao number(6),
  sequencia number(4),
  nivel varchar2(1),
  grupo varchar2(5),
  sub varchar2(3),
  item varchar2(6),
  cod_deposito number(3),
  quantidade number(17,6),
  constraint orion_sup_002_pk primary key (id),
  constraint fk_orion_sup_002_orion_sup_001	foreign key (id_pre_requisicao) references orion_sup_001 (id)
);

comment on table orion_sup_002 is 'Tabela de itens da pré-requisição de almoxarifado';