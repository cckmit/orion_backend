package br.com.live.util;

public class CodigoGrupoItem {
	
	public static String getGrupo(String codigo) {
		String[] codigoSeparado = codigo.split("[.]");
		String grupo = codigoSeparado [0];
		return grupo;
	}

	public static String getItem(String codigo) {
		String[] codigoSeparado = codigo.split("[.]");
		String item = codigoSeparado [1];
		return item;
	}
		
}
