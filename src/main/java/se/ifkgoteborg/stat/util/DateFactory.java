package se.ifkgoteborg.stat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFactory {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd yyyy");
	
	public static Calendar get(int year, int month, int day) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(year, month, day);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		System.out.println("Returning date: " + sdf.format(cal.getTime()));
		
		return cal;
	}

	public static String format(Date date) {
		return sdf.format(date);
	}
}
