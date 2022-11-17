CREATE OR REPLACE FUNCTION "LIVE_FN_CONVERTE_MOEDA" (cod_moeda number, valor number)
return number is
  valor_moeda number;
  valor_calc number;

begin

   valor_calc := valor;
   valor_moeda := 1;

   if (cod_moeda > 0)
   then
      begin
         select a.valor_moeda into valor_moeda from basi_270 a
         where a.codigo_moeda = 1
           and a.valor_moeda > 0
           and rownum = 1
         order by a.data_moeda desc;
         exception
         when others then
            valor_moeda := 1;
      end;
      valor_calc := valor * valor_moeda;
   end if;

   return(valor_calc);
   
end LIVE_FN_CONVERTE_MOEDA;
