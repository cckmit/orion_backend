create table orion_003
(id varchar2(50), -- usuario + programa
 id_usuario number(9),
 id_programa number(9),
 constraint orion_003_pk primary key (id),
 constraint fk_orion_003_orion_001  foreign key (id_usuario) references orion_001 (id),
 constraint fk_orion_003_orion_002  foreign key (id_programa) references orion_002 (id)
); 