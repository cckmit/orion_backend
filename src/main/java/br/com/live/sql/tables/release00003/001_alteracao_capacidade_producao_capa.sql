CREATE TABLE orion_045 (
    id varchar2(50),
    periodo number(4),
	linha number(2),
	categoria number(5),
	constraint orion_045_pk primary key (id)
);
comment on table orion_045 is 'Capacidade de Cotas de Vendas - capa';