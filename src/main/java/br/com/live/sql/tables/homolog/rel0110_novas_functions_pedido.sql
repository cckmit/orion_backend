CREATE OR REPLACE FUNCTION "ORION_FN_CALCULA_VALOR_LIQ_PED" (p_pedido in number) return number
is
    v_valor_liq_unit      number;
    v_valor_liq_itens     number;
    v_col_tab_preco       number;
    v_mes_tab_preco       number;
    v_seq_tab_preco       number;
    v_casas_decimais      number;
    v_valor_final         number;
    v_desconto1           number;
    v_desconto2           number;
    v_desconto3           number;
begin
   v_valor_liq_unit := 0;
   v_valor_liq_itens := 0;
   v_valor_final := 0;
   v_desconto1 := 0;
   v_desconto2 := 0;
   v_desconto3 := 0;
   v_casas_decimais := 0;

   begin
     select pedi_100.valor_liq_itens, pedi_100.colecao_tabela, pedi_100.mes_tabela, pedi_100.sequencia_tabela,
     pedi_100.desconto1, pedi_100.desconto2, pedi_100.desconto3
            into v_valor_liq_itens, v_col_tab_preco, v_mes_tab_preco, v_seq_tab_preco, v_desconto1, v_desconto2, v_desconto3
     from pedi_100
     where pedi_100.pedido_venda = p_pedido;
   end;

   begin
     select pedi_090.casas_decimais
                INTO v_casas_decimais
     from pedi_090
          where pedi_090.col_tabela_preco = v_col_tab_preco
             and pedi_090.mes_tabela_preco = v_mes_tab_preco
             and pedi_090.seq_tabela_preco = v_seq_tab_preco;
       exception when others then
         v_casas_decimais := 5;
   end;

   for dadosItem in (select pedi_110.qtde_pedida - pedi_110.qtde_faturada qtdePedida, pedi_110.valor_unitario valorUnitario, pedi_110.percentual_desc percDesc
                     from pedi_110
                     where pedi_110.pedido_venda = p_pedido
                     and pedi_110.cod_cancelamento = 0
                     )
   loop
       v_valor_liq_unit := dadosItem.Valorunitario - dadosItem.Valorunitario *(dadosItem.Percdesc) / 100.00;

       begin
         select round( v_valor_liq_unit, v_casas_decimais)
            INTO v_valor_liq_unit
         from dual;
         exception when others then
            v_valor_liq_unit := dadosItem.Valorunitario - dadosItem.Valorunitario * ((dadosItem.Percdesc) /100.00 );
       end;

       v_valor_final := v_valor_final + dadosItem.Qtdepedida * v_valor_liq_unit;
    end loop;

      v_valor_final := v_valor_final - (v_valor_final * v_desconto1 / 100);
      v_valor_final := v_valor_final - (v_valor_final * v_desconto2 / 100);
      v_valor_final := v_valor_final - (v_valor_final * v_desconto3 / 100);

   return v_valor_final;
end;
-----------------------------------------------------------------------------