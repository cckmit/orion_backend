CREATE TABLE orion_060 (
    sequencia number(4) default 0,
    estagio number(4) default 0,
	lead number(4) default 0,
	constraint orion_060_pk primary key (sequencia)
);
comment on table orion_060 is 'Estágios Configurados';