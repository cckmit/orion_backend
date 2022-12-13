CREATE TABLE orion_exp_320 (
			 id number(9),
			 minuta number(9),
             volume number(9),
             pedido number(9),
             nota number (9),
             serie varchar2(3),
             cliente varchar2(200),
             data_liberacao Date,
             peso_bruto number(12,2),
             valor_nota number(12,2),
             constraint orion_exp_320_pk PRIMARY KEY(id));

comment on table orion_exp_320 is 'Tabela de Volumes da Minuta';