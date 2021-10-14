package br.com.live.util;

public class ConvertePeriodo {
	public static int parse(int periodo, int empresa) {				
		if (periodo == 0) return 0;		
		if (empresa == 1) return periodo;
		String periodoStr = Integer.toString(periodo);
		String digito1 = Integer.toString(empresa).substring(0, 1);
		return Integer.parseInt((digito1 + periodoStr.substring(1,4)));
	}
}
