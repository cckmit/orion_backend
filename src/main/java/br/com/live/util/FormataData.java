package br.com.live.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormataData {

	public static Date parseStringToDate(String data) {		
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date dataEdit = null;
        try {
        	dataEdit = format.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dataEdit;
	}
	
	public static String parseDateToString(Date dataNaoConvert) {
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(dataNaoConvert);
		
		return dataFormatada;
	}
}
