package br.com.live.util;

public class ConvertePeriodo {
	public static int parse(int periodo, int empresa) {				
		String digito1;
		
		if (periodo == 0) return 0;		
		if (empresa == 1) return periodo;
		
		if (empresa == 100) digito1 = "3"; 
		else digito1 = Integer.toString(empresa).substring(0, 1);
		
		String periodoStr = Integer.toString(periodo);		
		return Integer.parseInt((digito1 + periodoStr.substring(1,4)));
	}
}
