package br.com.live.util;

import java.util.List;

public class ConverteLista {

	public static String parseInteger(List<Integer> listaIDs) {

		String listaString = "";

		for (Integer id : listaIDs) {
			if (listaString.equalsIgnoreCase(""))
				listaString = Integer.toString(id);
			else
				listaString += ", " + id;
		}

		return listaString;
	}

	
	public static String parseString(List<String> listaIDs) {

		String listaString = "";

		for (String id : listaIDs) {
			if (listaString.equalsIgnoreCase(""))
				listaString = "'" + id + "'";
			else
				listaString += ", " + id;
		}

		return listaString;
	}

}
