package com.wey.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	private static final String DATE_PATTERN="yyyy-MM-dd";
	private static final String TIME_PATTERN="hh:mm:ss";
	private static Locale locale;
	
	public static void setLocale(Locale locale){
		locale = locale;
	}
	
	public static String getTimestampPattern(){
		return DATE_PATTERN+" "+TIME_PATTERN;
	}
	
	public static Date convertStringToDate(String source) throws ParseException{
		return convertStringToDate(DATE_PATTERN,source);
	}
	
	public static Date convertStringToDate(String pattern,String source) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(source);
	}
	
	public static Timestamp convertStringToTimestamp(String source) throws ParseException{
		return convertStringToTimestamp(getTimestampPattern(),source);
	}
	
	public static Timestamp convertStringToTimestamp(String pattern,String source) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date _date = format.parse(source);
		Timestamp date = new Timestamp(_date.getTime());
		return date;
	}
	
	public static Calendar getToday() throws ParseException{
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
		String todayAsString = format.format(today);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(convertStringToDate(todayAsString));
		return calendar;
	}
	
	
	
	public static String getDateTime(String pattern,Date date){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static String getTimeNow(Date date){
		return getDateTime(TIME_PATTERN,date);
	}
	
	public static String convertDateToString(Date date){
		return getDateTime(DATE_PATTERN,date);
	}
	
	public static String convertTimestampToString(Timestamp timestamp){
		return getDateTime(getTimestampPattern(),timestamp);
	}
	
	public static Timestamp getCurrentTimestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
}
