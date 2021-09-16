INSERT INTO orion_002 (id, descricao, modulo, path)
VALUES (13,'Exportação de Tags (China)','Confecção','/exportacao-tags');

ALTER TABLE orion_adm_001
ADD num_doc_interno number(9) default 0;

ALTER TABLE orion_adm_001
ADD num_doc_fornecedor number(9) default 0;