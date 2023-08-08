CREATE OR REPLACE PROCEDURE "ORION_PR_CONGELA_PEDIDOS"
IS
   CURSOR cur_pedidos IS
      SELECT a.pedido_venda, a.data_entr_venda, a.situacao_venda, a.cod_cancelamento
        FROM pedi_100 a
       WHERE a.data_entr_venda = TRUNC(SYSDATE-1);

-- Rodrigo F.
-- Congela os pedidos com embarque do dia anterior, para conferência e análise em relatórios e BI´s.

BEGIN

   FOR reg_pedidos IN cur_pedidos
   LOOP

      BEGIN
         INSERT INTO orion_com_310 (PEDIDO_VENDA,             DATA_ENTR_VENDA,             SITUACAO_VENDA,             COD_CANCELAMENTO)
                            VALUES (reg_pedidos.pedido_venda, reg_pedidos.data_entr_venda, reg_pedidos.situacao_venda, reg_pedidos.cod_cancelamento);
         EXCEPTION
            WHEN OTHERS THEN NULL;
      END;

      COMMIT;
   END LOOP;

END ORION_PR_CONGELA_PEDIDOS;
