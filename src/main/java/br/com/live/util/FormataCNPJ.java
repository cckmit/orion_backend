package br.com.live.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormataCNPJ {

	// TODO: Recebe string '35303139006120' e formata para '35.303.139/0061-20'
	public static String formatar(String cnpj) {
		String cnpjFormatado = cnpj.replaceAll("[^0-9]", "");
		
		if (cnpjFormatado.length() < 15) cnpjFormatado = String.format("%15s", cnpjFormatado).replace(' ', '0');  
		
		String blocoInscricao = cnpjFormatado.substring(0, 9);
		String blocoMatrizFilial = cnpjFormatado.substring(9, 13);
		String blocoDigitoVerificador = cnpjFormatado.substring(13, 15);
		
		if (blocoMatrizFilial.equalsIgnoreCase("0000")) {
			String cnpjFormatar = blocoInscricao + "" + blocoDigitoVerificador;  			
			try {
				MaskFormatter mask = new MaskFormatter("###.###.###-##");
				mask.setValueContainsLiteralCharacters(false);
				cnpjFormatado = mask.valueToString(cnpjFormatar);
			} catch (ParseException ex) {
				System.out.println("Erro na formatação do CPF!");
			}
		} else {						
			String cnpjFormatar = blocoInscricao + blocoMatrizFilial + blocoDigitoVerificador;
			cnpjFormatar = (cnpjFormatar.length() > 14) ? cnpjFormatar.substring(1, 15) : cnpjFormatar;			
			try {
				MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
				mask.setValueContainsLiteralCharacters(false);
				cnpjFormatado = mask.valueToString(cnpjFormatar);
			} catch (ParseException ex) {
				System.out.println("Erro na formatação do CNPJ!");
			}
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
