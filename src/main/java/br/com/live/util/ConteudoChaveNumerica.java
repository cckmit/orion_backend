package br.com.live.util;

import java.util.List;

public class ConteudoChaveNumerica {

	public int value;
	public String label;

	public ConteudoChaveNumerica (int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public static String parseValueToString(List<ConteudoChaveNumerica> listaChaves) {

		String texto = "";

		if (listaChaves != null) {
			for (ConteudoChaveNumerica conteudo : listaChaves) {
				if (texto.equalsIgnoreCase(""))
					texto = Integer.toString(conteudo.value);
				else
					texto = texto + ", " + Integer.toString(conteudo.value);
			}
		}

		return texto;
	}
}