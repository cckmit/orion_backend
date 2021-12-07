create table orion_090
(id number(9),
 descricao varchar2(100) default '',
 situacao number(1) default 0, -- 0-Em digitação, 1-Liberado 
 usuario varchar2(100) default '',
 data date,
 constraint orion_090_pk primary key (id));
 
comment on table orion_090 is 'Tabela de requisição de tecidos para atender a confecção - Capa'; 
 
create table orion_091
(id number(9),
 id_requisicao number(9),
 sequencia number(4),
 nivel varchar2(1) default '',
 grupo varchar2(5) default '',
 sub varchar2(3) default '',
 item varchar2(6) default '',
 alternativa number(2) default 0,
 roteiro number(2) default 0,
 quantidade number(8,2) default 0,
 constraint orion_091_pk primary key (id),
 constraint fk_orion_091_orion_090 foreign key (id_requisicao) references orion_090 (id));
 
comment on table orion_091 is 'Tabela de requisição de tecidos para atender a confecção - Itens'; 

create INDEX INDX_ORION_91_ID_REQ on orion_091 (id_requisicao);