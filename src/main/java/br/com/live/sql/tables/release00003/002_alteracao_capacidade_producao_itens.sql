CREATE TABLE orion_046 (
    id varchar2(50),
    id_capacidade_cotas varchar2(50), 
   	modelo varchar2(5),
	qtde_minutos number(9),
	qtde_pecas number(9),
	constraint orion_046_pk primary key (id)
);
comment on table orion_046 is 'Capacidade de Cotas de Vendas - itens';