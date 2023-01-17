CREATE TABLE orion_com_281(
       id_item NUMBER(9),
       id_capa VARCHAR2(200), 
       catalogo NUMBER(5),
       tipo_cliente NUMBER(9),       
       col_tab_entr NUMBER(9),
       mes_tab_entr NUMBER(9),
       seq_tab_entr NUMBER(9),
       periodo_ini date,
       periodo_fim date,
       CONSTRAINT fk_orion_com_280_281	foreign key (id_capa) references orion_com_280 (id), 
       CONSTRAINT orion_com_281_pk PRIMARY KEY (id_item));