CREATE TABLE orion_cfc_270 (
			 id number(9),
             descricao varchar2(200),
             constraint orion_cfc_270_pk PRIMARY KEY(id));

comment on table orion_cfc_270 is 'Tabela do cadastro de Restrições';

CREATE TABLE orion_cfc_271 (
			 id number(9),
			 codigo_rolo number(9),
             restricao number(9),
             constraint orion_cfc_271_pk PRIMARY KEY(id),
             constraint fk_orion_cfc_271_orion_cfc_270 foreign key (restricao) references orion_cfc_270 (id)
             );

comment on table orion_cfc_271 is 'Tabela do cadastro de Restrições por Rolo';