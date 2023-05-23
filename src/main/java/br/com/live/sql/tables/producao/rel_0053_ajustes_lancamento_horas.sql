--insert into orion_002 (ID, DESCRICAO, MODULO, PATH)
--values (11, 'Cadastro de Tarefas', 'TI', '/tarefas');

--insert into orion_002 (ID, DESCRICAO, MODULO, PATH)
--values (12, 'Lançamento de Horas', 'TI', '/lancamento-horas');

DELETE ORION_003
WHERE ID_PROGRAMA IN (11, 12);

DELETE orion_002
WHERE ID IN (11,12);