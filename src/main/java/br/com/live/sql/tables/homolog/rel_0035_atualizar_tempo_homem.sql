UPDATE mqop_040 a SET a.tempo_homem = a.tempo_homem + ((1.86 * a.tempo_homem) / 100) WHERE a.codigo_operacao BETWEEN 20000 AND 30000