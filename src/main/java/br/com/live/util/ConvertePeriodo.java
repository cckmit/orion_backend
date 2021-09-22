package br.com.live.util;

public class ConvertePeriodo {
	public static int parse(int periodo, int empresa) {		
		String periodoStr = Integer.toString(periodo);
		String digito1 = Integer.toString(empresa).substring(0, 1);
		return Integer.parseInt((digito1 + periodoStr.substring(1,4)));
	}
}
