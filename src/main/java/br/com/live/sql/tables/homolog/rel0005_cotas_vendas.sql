DROP TABLE orion_045;
DROP TABLE orion_046;

CREATE TABLE orion_045 (
    id varchar2(50),
    periodo number(4) default 0,
	linha number(2) default 0,
	colecao number(3) default 0,
	tempo_total number(9) default 0,
	periodo_inicial number(4) default 0,
	periodo_final number(4) default 0,
	depositos varchar2(100) default '',
	constraint orion_045_pk primary key (id)
);
comment on table orion_045 is 'Capacidade de Cotas de Vendas - Capa';

CREATE TABLE orion_046 (
    id varchar2(50), -- id_capacidade_cotas + referencia + tamanho + cor
    id_capacidade_cotas varchar2(50) default '',    
    referencia varchar2(5) default '',
    tamanho varchar2(3) default '',
    cor varchar2(6) default '',    
    tempo_unitario number(9,4) default 0,   
    qtde_estoque number(6) default 0,
    qtde_demanda number(6) default 0,
    qtde_processo number(6) default 0,
    qtde_saldo number(6) default 0,    
	qtde_minutos number(9,4) default 0,
	qtde_pecas number(6) default 0,	
	bloqueio_venda number(1) default 0,	
	constraint orion_046_pk primary key (id)
);
comment on table orion_046 is 'Capacidade de Cotas de Vendas - Itens';