create table orion_005 (
id varchar2(20),
id_usuario number(9),
tipo_notificacao number(3),
constraint orion_005 primary key (id)
);
----------------------------------------
ALTER TABLE orion_001
ADD email varchar2(100);
----------------------------------------
INSERT INTO orion_005 (id, id_usuario, tipo_notificacao)
VALUES ('38-1', 38, 1);