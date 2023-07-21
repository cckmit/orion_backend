package br.com.live.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class FormataData {

	public static final int DOMINGO = 1;
	public static final int SEGUNDA = 2;
	public static final int TERCA = 3;
	public static final int QUARTA = 4;
	public static final int QUINTA = 5;
	public static final int SEXTA = 6;
	public static final int SABADO = 7;	
	public static final int JANEIRO = 1;
	public static final int FEVEREIRO = 2;
	public static final int MARCO = 3;
	public static final int ABRIL = 4;
	public static final int MAIO = 5;
	public static final int JUNHO = 6;
	public static final int JULHO = 7;
	public static final int AGOSTO = 8;
	public static final int SETEMBRO = 9;
	public static final int OUTUBRO = 10;
	public static final int NOVEMBRO = 11;
	public static final int DEZEMBRO = 12;
	
	public static Date parseStringToDate(String data) {
		SimpleDateFormat format = null;

		if (data.contains("-")) {
			format = new SimpleDateFormat("dd-MM-yyyy");
		} else {
			format = new SimpleDateFormat("dd/MM/yyyy");
		}

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

	public static int getMonthOfDate(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int month = calendar.get(Calendar.MONTH) + 1;

		return month;		
	}

	public static int getYearOfDate(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int month = calendar.get(Calendar.YEAR);

		return month;		
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
	
	public static Date getStartingDay(int month, int year) {				
		Calendar cal = new GregorianCalendar(year, month-1, 1);
		int startingDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH); 
		String strDate = String.format("%02d",startingDay) + "-" + String.format("%02d",month)  + "-" + year;
		return parseStringToDate(strDate);		
	}

	public static Date getFinalDay(int month, int year) {		
		Calendar cal = new GregorianCalendar(year, month-1, 1);
		int finalDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d",finalDay) + "-" + String.format("%02d",month)  + "-" + year;
		return parseStringToDate(strDate);		
	}
	
    public static int getLastDigitOfYear() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int lastDigit = year % 10;
        return lastDigit;

    }
    
    public static int getDiffInDays(Date dateStart, Date dateFinish) {
	    long diffInMilliseconds = dateFinish.getTime() - dateStart.getTime();
	    long diffInDays = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
	    return (int) diffInDays;
    }
    
	public static String obterDataMesAno(int mes, int ano, int tipo) {

		LocalDate data = LocalDate.of(ano, mes, 1);

		if (tipo == 1) {
			// Retorna o primeiro dia do mês
			return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else if (tipo == 2) {
			// Retorna o último dia do mês
			return data.withDayOfMonth(data.lengthOfMonth()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			throw new IllegalArgumentException("Tipo inválido. Deve ser 1 para primeiro dia do mês ou 2 para último dia do mês.");
		}
	}

	// Recebe data yyyy-mm-dd e retorna dd/mm/yyyy
	public static String converterDataFormato(String dataString) {

		SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date data = formatoEntrada.parse(dataString);
			return formatoSaida.format(data);
		} catch (ParseException e) {
			System.out.println("Erro ao converter a data.");
			e.printStackTrace();
			return null;
		}
	}

	// Recebe data yyyy-MM-dd'T'HH:mm:ss e retorna dd/mm/yyyy
	public static String formatarData(String dataSemFormato) throws ParseException {
		if (dataSemFormato == null) {
			return "";
		}

		DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date data = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataSemFormato);

		return formatoData.format(data);
	}
}
