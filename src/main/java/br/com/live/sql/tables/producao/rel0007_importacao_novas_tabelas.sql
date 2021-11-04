INSERT INTO orion_070
(select prog_560.cod_estacao, prog_560.descricao, prog_560.catalogo from prog_560);

INSERT INTO orion_071
(select b.cod_estacao || '-' || b.mes || '-' || b.ano || '-' || b.tipo id,
b.cod_estacao,
b.mes,
b.tipo,
b.ano,
b.distribuicao from prog_561 b);

INSERT INTO orion_072
(select b.cod_estacao || '-' || b.representante || '-' || b.tipo id,
b.cod_estacao,
b.representante,
b.tipo,
b.meta,
b.descricao_rep from prog_562 b);

INSERT INTO orion_073
(select b.cod_estacao || '-' || b.cod_tab || '-' || b.mes_tab || '-' || b.seq_tab id,
b.cod_estacao,
b.cod_tab,
b.mes_tab,
b.seq_tab from prog_564 b);