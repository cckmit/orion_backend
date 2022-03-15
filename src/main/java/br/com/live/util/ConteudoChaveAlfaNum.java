package br.com.live.util;

import java.util.ArrayList;
import java.util.List;

public class ConteudoChaveAlfaNum {

	public String value;
	public String label;

	public ConteudoChaveAlfaNum (String value, String label) {
		this.value = value;
		this.label = label;
	}
		
	public static String parseValueToString(List<ConteudoChaveAlfaNum> listaChaves) {

		String texto = "";

		if (listaChaves != null) {
			for (ConteudoChaveAlfaNum conteudo : listaChaves) {
				if (texto.equalsIgnoreCase(""))
					texto = "'" + conteudo.value + "'";
				else
					texto = texto + ", '" + conteudo.value + "'";
			}
		}

		return texto;
	}
	
	public static List<String> parseValueToListString(List<ConteudoChaveAlfaNum> listaChaves) {

		List<String> listaStr = new ArrayList<String>();

		if (listaChaves != null) {
			for (ConteudoChaveAlfaNum conteudo : listaChaves) {
				listaStr.add(conteudo.label);
			}
		}

		return listaStr;
	}
}
