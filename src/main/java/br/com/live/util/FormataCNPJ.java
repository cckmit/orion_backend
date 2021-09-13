package br.com.live.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormataCNPJ {
	public static String formatar(String cnpj) {
		String cnpjFormatado = cnpj;
		try {
			MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
			mask.setValueContainsLiteralCharacters(false);
			cnpjFormatado = mask.valueToString(cnpj);
		} catch (ParseException ex) {
			System.out.println("Erro na formatação do CNPJ!");
		}
		return cnpjFormatado;
	}
}
