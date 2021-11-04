select max(id) from orion_020;
create sequence id_orion_020 INCREMENT BY ?;
select id_orion_020.nextval from dual;
alter sequence id_orion_020 INCREMENT BY 1;
;

select max(id) from orion_021;
create sequence id_orion_021 INCREMENT BY ?;
select id_orion_021.nextval from dual;
alter sequence id_orion_021 INCREMENT BY 1;
