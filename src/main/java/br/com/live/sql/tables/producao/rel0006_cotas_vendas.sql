DROP TABLE orion_047;
DROP TABLE orion_046;
DROP TABLE orion_045;

/*
CREATE TABLE orion_045 (
	id number(9),
	descricao varchar2(100) default '',
	periodo_atual_inicio number(4) default 0,
	periodo_atual_final number(4) default 0,
	periodo_analise_inicio number(4) default 0,
	periodo_analise_final number(4) default 0,
	colecoes varchar2(100) default '',
	depositos varchar2(100) default '',
	minutos_periodo number(9) default 0,
	constraint orion_045_pk primary key (id)
);

comment on table orion_045 is 'Cotas de Vendas - Capa';
*/
------------
/*
CREATE TABLE orion_046 (
    id varchar2(50), -- id_cotas + referencia + tamanho + cor
    id_capacidade_cotas number(9) default 0,    
    referencia varchar2(5) default '',
    tamanho varchar2(3) default '',
    cor varchar2(6) default '',    
	bloqueio_venda number(1) default 0,    
    constraint orion_046_pk primary key (id),
	constraint fk_orion_046_orion_045 foreign key (id_capacidade_cotas) references orion_045 (id)    
);

comment on table orion_046 is 'Cotas de Vendas - Itens';
*/
------------
/*
CREATE TABLE orion_047 (
    id varchar2(50), -- referencia + tamanho + cor + periodo
    referencia varchar2(5) default '',
    tamanho varchar2(3) default '',
    cor varchar2(6) default '',
	periodo number(4) default 0,
    qtde_cota_venda number(9),
    constraint orion_047_pk primary key (id)
);

comment on table orion_047 is 'Cota de Venda por Produto';
*/