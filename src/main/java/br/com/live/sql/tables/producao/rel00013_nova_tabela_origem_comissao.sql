create table orion_095
(origem number(9),
 perc_comissao number(9,2) default 0,
 constraint orion_095_pk primary key (origem));
 
comment on table orion_095 is 'Tabela de Origem de Pedido x Percentual de Comissão';
-------------------------------------------------------------------------------------
INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (22,'Percentual de Comissão por Origem do Pedido','Comercial','/perc-comissao-pedido');
-------------------------------------------------------------------------------------
/*
create table orion_096
(id number(9),
 descricao varchar2(100),
 constraint orion_096_pk primary key (id));
 
comment on table orion_096 is 'Tabela de Tipos de Movimentação de Bens'; 
*/
-------------------------------------------------------------------------------------
/*
INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (23,'Tipos de Movimentação','Patrimonial','/tipo-movimentacao');
*/
-------------------------------------------------------------------------------------
/*
create table orion_100
(sequencia number(9),
 id_bem    number(9),
 tipo_movimentacao number(2) default 0,
 cnpj_origem  varchar2(16),
 cnpj_destino varchar2(16),
 data_envio date,
 nota_fiscal varchar2(20),
 observacao  varchar2(200),
 usuario number(6) default 0,
 constraint orion_100_pk primary key (sequencia),
 constraint fk_orion_100_orion_096 foreign key (tipo_movimentacao) references orion_096 (id));
 
comment on table orion_100 is 'Tabela de Movimentação de Bens';
*/
-------------------------------------------------------------------------------------
/*
INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (24,'Movimentação de Bens','Patrimonial','/movimentacao');
*/
-------------------------------------------------------------------------------------
/*
INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (25,'Consultar Movimentações','Patrimonial','/consultar-movimentacoes');
*/
-------------------------------------------------------------------------------------
INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (26,'Liberar Sugestão','Comercial','/liberar-sugestao');
-------------------------------------------------------------------------------------
INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (27,'Consulta de Horas Lançadas','TI','/consulta-lancamento-horas');