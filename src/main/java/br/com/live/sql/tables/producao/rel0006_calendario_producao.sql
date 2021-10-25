CREATE TABLE orion_060 (
    sequencia number(4) default 0,
    estagio number(4) default 0,
	lead number(4) default 0,
	constraint orion_060_pk primary key (sequencia)
);
comment on table orion_060 is 'Estágios Configurados';

-----------------------------------------------------------------

CREATE TABLE orion_061 (
		ano_calendario number(4) default 0,
		periodo_inicio number(5) default 0,
		periodo_fim number(5) default 0,
		considera_sabado number(1) default 0,
		considera_domingo number(1) default 0,
		considera_feriado number(1) default 0,
	constraint orion_061_pk primary key (ano_calendario)
);
comment on table orion_061 is 'Parâmetros Geração Calendario';

-----------------------------------------------------------------

CREATE TABLE orion_062 (
		id varchar2(10),
		sequencia number(3) default 0,
		ano_calendario number(4) default 0,
		estagio number(3) default 0,
		lead number(3) default 0,
		data_inicio date,
		data_fim date,
	constraint orion_062_pk primary key (id)
);
comment on table orion_062 is 'Estágios Geração Calendario';

-----------------------------------------------------------------

-- ATUALIZAR AMBIENTE DE PRODUCAO

ALTER TABLE orion_061 
ADD (situacao number(1) default 0);

comment on column orion_061.situacao is '0-Prévia / 1-Períodos Gravados';