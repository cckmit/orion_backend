package br.com.live.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static String obterDataMesAno(int mes, int ano, int tipo) {

        LocalDate data = LocalDate.of(ano, mes, 1);

        if (tipo == 1) {
            // Retorna o primeiro dia do mês
            return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else if (tipo == 2) {
            // Retorna o último dia do mês
            return data.withDayOfMonth(data.lengthOfMonth()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            throw new IllegalArgumentException("Tipo inválido. Deve ser 1 para primeiro dia do mês ou 2 para último dia do mês.");
        }
    }

}
