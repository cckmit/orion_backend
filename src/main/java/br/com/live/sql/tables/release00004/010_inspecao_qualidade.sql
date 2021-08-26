create table orion_050
(id_inspecao number(9),
 data date,
 turno number(1),
 usuario varchar2(100),
 cod_estagio number(2), 
 periodo number(4),
 ordem_producao number(9),
 ordem_confeccao number(5),
 perc_inspecionar_pcs number(3),
 qtde_inspecionar_pcs number(6),
 qtde_inspecionada_pcs number(6),
 qtde_rejeitada_pcs number(6), 
 perc_rejeitada_pcs number(3),
 constraint orion_050_pk primary key (id_inspecao));

comment on table orion_050 is 'Tabela inspeção de qualidade - pecas / paineis';

create table orion_051
(id_lancamento number(9),
 id_inspecao number(9),
 cod_motivo number(3),
 quantidade number(6),
 usuario varchar2(100),
 data_hora date,
 constraint orion_051_pk primary key (id_lancamento),
 constraint fk_orion_051_orion_050  foreign key (id_inspecao) references orion_050 (id_inspecao));

comment on table orion_051 is 'Tabela inspeção de qualidade - pecas / paineis - lançamentos';