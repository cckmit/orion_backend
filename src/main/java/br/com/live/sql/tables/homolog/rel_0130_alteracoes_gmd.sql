SQL 1) ORION --------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- atualizar mqop_160
delete from mqop_045;
delete from mqop_160;
insert into mqop_160 (select m.codigo_operacao codigo, 'MICROMOVIMENTO GENERICO' descricao, 0 utiliza_maquina, m.tempo_homem tempo, 1 frequencia, 1 quantidade from mqop_040 m where m.categoria_pagamento <> 'P');

-- atualizar mqop_045
insert into mqop_045 (select m.codigo_operacao, m.codigo_operacao codigo_movimento, 1 sequencia, 1 quantidade, 1 frequencia, m.tempo_homem tempo_total  from mqop_040 m where m.categoria_pagamento <> 'P');


delete from mqop_045
where mqop_045.codigo_operacao in (select m.codigo_operacao from mqop_040 m
                                    where m.categoria_pagamento = 'P')
;
delete from mqop_160
where mqop_160.codigo in (select m.codigo_operacao from mqop_040 m
                           where m.categoria_pagamento = 'P')
;

SQL 2) SYSTEXTIL ----------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER "LIVE_TR_MQOP_040_1"
   AFTER INSERT ON mqop_040
   FOR EACH ROW

/* RODRIGO F. - 37988 (GMD). QUANDO CADASTRAR UMA NOVA OPERAÇÃO, DEVE INSERIR AUTOMATICAMENTE UM MICROMOVIMENTO GENERICO. */

BEGIN

   IF (:NEW.categoria_pagamento is null) OR (:NEW.categoria_pagamento <> 'P')
   THEN

      BEGIN
         INSERT INTO mqop_160 (codigo, descricao, utiliza_maquina, tempo, frequencia, quantidade)
                       VALUES (:new.codigo_operacao, 'MICROMOVIMENTO GENERICO', 0, 0, 1, 1);
         EXCEPTION
            WHEN OTHERS THEN NULL;
      END;
      BEGIN              
         INSERT INTO mqop_045 (codigo_operacao, codigo_movimento, sequencia, quantidade, frequencia, tempo_total)
                       VALUES (:new.codigo_operacao, :new.codigo_operacao, 1, 1, 1, 0);
         EXCEPTION
            WHEN OTHERS THEN NULL;                       
      END;
   
   END IF;
END LIVE_TR_MQOP_040_1;

SQL 3) ORION --------------------------------------------------------------------------------------------------------------------------------------------------------------------

alter table orion_eng_240
add (interferencia number(6,3) default 0.00);

SQL 4) ORION --------------------------------------------------------------------------------------------------------------------------------------------------------------------

update orion_eng_240 o 
   set o.interferencia = (select m.interferencia from mqop_020 m 
                           where m.grupo_maquina = o.grupo
                             and m.subgrupo_maquina = o.subgrupo);

SQL 5) ORION --------------------------------------------------------------------------------------------------------------------------------------------------------------------

update mqop_020 set interferencia = 0;