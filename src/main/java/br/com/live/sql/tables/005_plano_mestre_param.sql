-- Criar no Owner LIVE
create table orion_011
(num_plano_mestre number(9),
 
 -- Global
 periodo_padrao number(4), 
 tipo_distribuicao number(1),
 desc_tipo_distribuicao varchar2(30),
 multiplicador number(2),

 -- Análise Produto
 colecoes varchar2(200),
 colecoes_permanentes varchar2(200),
 linhas_produtos varchar2(200),
 artigos_produtos varchar2(200),
 artigos_cotas varchar2(200),
 publicos_alvos varchar2(200),
 embarques varchar2(200),
 referencias varchar2(200),
 cores varchar2(200),
 origens_produtos varchar2(200),
 
 -- Planejamento
 plano1_dem_inicio number(4) default 0,
 plano2_dem_inicio number(4) default 0,
 plano3_dem_inicio number(4) default 0,
 plano4_dem_inicio number(4) default 0,
 plano5_dem_inicio number(4) default 0,
 plano6_dem_inicio number(4) default 0,
 plano7_dem_inicio number(4) default 0,
 plano8_dem_inicio number(4) default 0,
 
 plano1_dem_fim number(4) default 0,
 plano2_dem_fim number(4) default 0,
 plano3_dem_fim number(4) default 0,
 plano4_dem_fim number(4) default 0,
 plano5_dem_fim number(4) default 0,
 plano6_dem_fim number(4) default 0,
 plano7_dem_fim number(4) default 0,
 plano8_dem_fim number(4) default 0,
 
 plano1_proc_inicio number(4) default 0,
 plano2_proc_inicio number(4) default 0,
 plano3_proc_inicio number(4) default 0,
 plano4_proc_inicio number(4) default 0,
 plano5_proc_inicio number(4) default 0,
 plano6_proc_inicio number(4) default 0,
 plano7_proc_inicio number(4) default 0,
 plano8_proc_inicio number(4) default 0,
 
 plano1_proc_fim number(4) default 0,
 plano2_proc_fim number(4) default 0,
 plano3_proc_fim number(4) default 0,
 plano4_proc_fim number(4) default 0,
 plano5_proc_fim number(4) default 0,
 plano6_proc_fim number(4) default 0,
 plano7_proc_fim number(4) default 0,
 plano8_proc_fim number(4) default 0,
 
 -- Estoque
 considera_deposito number(1),
 considera_prod_sem_estq number(1) default 0,
 depositos varchar2(200),
 
 -- Em processo
 considera_prod_sem_proc number(1) default 0,
 
 -- Demanda
 considera_pedido_bloq number(1) default 0,
 considera_prod_sem_pedi number(1) default 0,
 numero_interno number(4) default 0,
 pedidos varchar2(200),
 
 -- Pré-ordens
 agrupa_por_refer number(1) default 0,
 qtde_maxima_op number(6) default 0,
 qtde_minima_op number(6) default 0,
 qtde_maxima_cor number(6) default 0,
 periodo_op number(4) default 0,
 deposito_op number(3) default 0,
 observacao_op varchar2(60),
 
 constraint orion_011_pk primary key (num_plano_mestre),
 constraint fk_orion_011_orion_010	foreign key (num_plano_mestre) references orion_010 (num_plano_mestre) 
);

comment on table orion_011 is 'Tabela plano mestre de produção - parametros';
