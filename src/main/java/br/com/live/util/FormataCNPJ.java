package br.com.live.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormataCNPJ {

	// TODO: Recebe string '35303139006120' e formata para '35.303.139/0061-20'
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

	// TODO: Recebe string '35303139.0061.20' e formata para '35.303.139/0061-20'
	public static String formatar2(String cnpj){

		String[] cnpjArr = cnpj.split("\\.");
		String cgc9 = String.format("%08d", Integer.parseInt(cnpjArr[0]));
		String cgc4 = String.format("%04d", Integer.parseInt(cnpjArr[1]));
		String cgc2 = String.format("%02d", Integer.parseInt(cnpjArr[2]));
		String cnpjConcat = cgc9 + cgc4 + cgc2;

		String cnpjFormatado = FormataCNPJ.formatar(cnpjConcat);

		return cnpjFormatado;
	}
}
