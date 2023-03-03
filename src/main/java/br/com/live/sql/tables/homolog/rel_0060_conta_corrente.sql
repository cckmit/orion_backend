CREATE TABLE orion_com_290 (
       id varchar2(20),
       cnpj_9 number(9),
       cnpj_4 number(4),
       cnpj_2 number(2),
       data_insercao Date,
       valor  number(12,2),
       observacao varchar2(200),
       usuario varchar2(100),
        CONSTRAINT orion_com_290_pk PRIMARY KEY (id));

        CREATE TABLE orion_com_291 (
               pedido number(9),
               cnpj_9 number(9),
               cnpj_4 number(4),
               cnpj_2 number(2),
               data_insercao Date,
               valor_desconto  number(12,2),
               usuario varchar2(100),
               observacao varchar2(200),
                CONSTRAINT orion_com_291_pk PRIMARY KEY (pedido));

        CREATE TABLE orion_com_292 (
                       id_controle   number(20),
                       cnpj_9 number(9),
                       cnpj_4 number(4),
                       cnpj_2 number(2),
                       valor_desconto  number(12,2),
                        CONSTRAINT orion_com_292_pk PRIMARY KEY (id_controle));


