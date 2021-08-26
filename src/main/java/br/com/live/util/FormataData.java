package br.com.live.util;

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
}
