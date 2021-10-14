package br.com.live.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormataData {

	public static final int DOMINGO = 1;
	public static final int SEGUNDA = 2;
	public static final int TERCA = 3;
	public static final int QUARTA = 4;
	public static final int QUINTA = 5;
	public static final int SEXTA = 6;
	public static final int SABADO = 7;
	
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
	
	public static int getDayOfWeek(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int day = calendar.get(Calendar.DAY_OF_WEEK);

		return day;		
	}
	
	public static String getDescDayOfWeek(int dayOfWeek) {
		String descDay = "";

		if (dayOfWeek == DOMINGO)
			descDay = "DOMINGO";
		if (dayOfWeek == SEGUNDA)
			descDay = "SEGUNDA";
		if (dayOfWeek == TERCA)
			descDay = "TERCA";
		if (dayOfWeek == QUARTA)
			descDay = "QUARTA";
		if (dayOfWeek == QUINTA)
			descDay = "QUINTA";
		if (dayOfWeek == SEXTA)
			descDay = "SEXTA";
		if (dayOfWeek == SABADO)
			descDay = "SABADO";

		return descDay;
	}
	
	public static Date getNextDay(int findDay, Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int dayOfWeek = 0;
		
		do {
			calendar.add(Calendar.DATE, +1);			
			dayOfWeek = getDayOfWeek(calendar.getTime());
		} while(dayOfWeek != findDay);
		
		return calendar.getTime();
	}

	public static Date getPreviousDay(int findDay, Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int dayOfWeek = 0;
		
		do {
			calendar.add(Calendar.DATE, -1);			
			dayOfWeek = getDayOfWeek(calendar.getTime());
		} while(dayOfWeek != findDay);
		
		return calendar.getTime();
	}

	public static Date getSumDate(Date data, int nrDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.DATE, nrDay);
		return calendar.getTime();		
	}
}
