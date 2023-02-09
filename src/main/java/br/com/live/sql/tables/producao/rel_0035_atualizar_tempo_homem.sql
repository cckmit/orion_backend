-- EXECUTAR ESSE SCRIPT PRIMEIRO
UPDATE mqop_040 a SET a.tempo_homem = a.tempo_homem + ((1.86 * a.tempo_homem) / 100) 
       WHERE a.codigo_operacao BETWEEN 20000 AND 30000;
COMMIT;
-- EXECUTAR EM SEGUIDA
UPDATE mqop_050 v SET v.minutos_homem = (SELECT b.tempo_homem FROM mqop_040 b WHERE b.codigo_operacao = v.codigo_operacao) WHERE v.codigo_operacao BETWEEN 20000 AND 30000;
COMMIT;