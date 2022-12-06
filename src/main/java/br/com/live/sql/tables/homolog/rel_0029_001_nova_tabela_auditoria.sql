CREATE TABLE orion_exp_310 (
			 id number(9),
			 codigo_rua number(4),
             volume number(9),
             data_auditoria date,
             constraint orion_exp_310_pk PRIMARY KEY(id));

comment on table orion_exp_310 is 'Tabela de Auditoria de Transporte';