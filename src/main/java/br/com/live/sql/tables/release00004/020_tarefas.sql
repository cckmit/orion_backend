create table orion_adm_001
(id number(9), 
 tipo number(1),
 sistema number(1),
 origem number(1),
 usuario_solicitante number(3),
 usuario_atribuido number(3),
 titulo varchar2(100),
 assunto varchar2(500),
 situacao number(1),
 anexo long,
 tempo_estimado number(4,2),
 data_prevista date,
 constraint orion_adm_001_pk primary key (id)
);

comment on table orion_adm_001 is 'Tabela de tarefas - Administrativo';

comment on column orion_adm_001.tipo is '1 - INCIDENTE / 2 - REQUISIÇÃO / 3 - MELHORIA / 4 - CONSULTORIA';
comment on column orion_adm_001.sistema is '1 - SYSTEXTIL / 2 - ORION / 3 - BANCO DE DADOS / 4 - OUTRO SISTEMA';
comment on column orion_adm_001.origem is '1 - PROJETO / 2 - CHAMADO / 3 - PROBLEMA / 4 - MUDANÇA';
comment on column orion_adm_001.situacao is '0 - ABERTO / 1 - FECHADO';
---------------------------------------------------------------------------------------------------------------------
create table orion_adm_002
(id varchar2(30), 
 id_tarefa number(9),
 id_usuario number(9),
 sequencia_lancamento number(4),
 data_lancamento date,
 tempo_gasto number(4,2),
 descricao varchar2(100),
 constraint orion_adm_002_pk primary key (id),
 constraint fk_orion_adm_002_orion_adm_001 foreign key (id_tarefa) references orion_adm_001 (id),
 constraint fk_orion_adm_002_orion_001 foreign key (id_usuario) references orion_001 (id)
);

comment on table orion_adm_001 is 'Tabela de Lançamento de Horas - Administrativo';
