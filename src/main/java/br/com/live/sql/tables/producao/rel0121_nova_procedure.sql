CREATE OR REPLACE PROCEDURE "ORION_PR_CONGELA_PEDIDOS"
IS
   CURSOR cur_pedidos IS
      SELECT a.pedido_venda,       a.data_entr_venda, a.situacao_venda,   a.cod_cancelamento,
             a.sit_aloc_pedi,      a.sit_coletor,     a.situacao_coleta,  a.status_expedicao, 
             a.status_homologacao, a.status_pedido,   a.status_comercial, a.sit_conferencia
        FROM pedi_100 a
       WHERE a.data_entr_venda = TRUNC(SYSDATE-1)
         AND a.qtde_total_pedi > 0;

   CURSOR cur_motiv_bloqueio (p_pedido number) IS
      SELECT a.pedido_venda, a.seq_situacao, a.codigo_situacao, a.flag_liberacao, a.data_situacao, a.data_liberacao
        FROM pedi_135 a
       WHERE a.pedido_venda = p_pedido;

-- Rodrigo F.
-- Congela os pedidos com embarque do dia anterior, para conferência e análise em relatórios e BI´s.

BEGIN

   FOR reg_pedidos IN cur_pedidos
   LOOP

      BEGIN
         INSERT INTO orion_com_310 (PEDIDO_VENDA,                   DATA_ENTR_VENDA,             SITUACAO_VENDA,              COD_CANCELAMENTO,
                                    SIT_ALOC_PEDI,                  SIT_COLETOR,                 SITUACAO_COLETA,             STATUS_EXPEDICAO, 
                                    STATUS_HOMOLOGACAO,             STATUS_PEDIDO,               STATUS_COMERCIAL,            SIT_CONFERENCIA)
                            VALUES (reg_pedidos.pedido_venda,       reg_pedidos.data_entr_venda, reg_pedidos.situacao_venda,  reg_pedidos.cod_cancelamento,                            
	                                  reg_pedidos.sit_aloc_pedi,      reg_pedidos.sit_coletor,     reg_pedidos.situacao_coleta, reg_pedidos.status_expedicao, 
                                    reg_pedidos.status_homologacao, reg_pedidos.status_pedido,   reg_pedidos.status_comercial,reg_pedidos.sit_conferencia);
         EXCEPTION
            WHEN OTHERS THEN NULL;
      END;            
            
      FOR reg_motiv_bloqueio IN cur_motiv_bloqueio(reg_pedidos.pedido_venda)
      LOOP         

         BEGIN
            INSERT INTO orion_com_311 (PEDIDO_VENDA, SEQ_SITUACAO, CODIGO_SITUACAO, FLAG_LIBERACAO, DATA_SITUACAO, DATA_LIBERACAO) 
                               VALUES (REG_MOTIV_BLOQUEIO.PEDIDO_VENDA, REG_MOTIV_BLOQUEIO.SEQ_SITUACAO, REG_MOTIV_BLOQUEIO.CODIGO_SITUACAO, REG_MOTIV_BLOQUEIO.FLAG_LIBERACAO, REG_MOTIV_BLOQUEIO.DATA_SITUACAO, REG_MOTIV_BLOQUEIO.DATA_LIBERACAO);
	          EXCEPTION
               WHEN OTHERS THEN NULL;                  
         END;
         
      END LOOP;

      COMMIT;
   END LOOP;

END ORION_PR_CONGELA_PEDIDOS;
