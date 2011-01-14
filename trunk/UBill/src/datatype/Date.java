package datatype;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {

	private int year = 0;
	private int month = 0;
	private int day = 0;
	
	public Date() {
		
	}
	
	public Date(int month, int day, int year) {
		if (checkDate(month, day, year)) {
			this.year = year;
			this.month = month;
			this.day = day;
		}		
	}
	
	public Date(String date) {
		String[] arr = date.split("[-|/|.]");
		this.year = Integer.valueOf(arr[2]);
		this.month = Integer.valueOf(arr[0]);
		this.day = Integer.valueOf(arr[1]);
	}
	
	public static String getCurrentDate() {
		Calendar cal = new GregorianCalendar();
		Date date = new Date();
		date.setDate(cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
		
		return date.getDate('-');
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public String getDate(char c) {
		String month = String.valueOf(this.month);
		String day = String.valueOf(this.day);
		String year = String.valueOf(this.year);
			
		return month+c+day+c+year;
	}
	
	public String getFormattedDate() {
		return getMonth(this.month)+" "+getDay(this.day)+", "+String.valueOf(this.year);
	}
	
	public boolean setDate(int month, int day, int year) {
		if (checkDate(year, month, day)) {		
			this.year = year;
			this.month = month;
			this.day = day;
			
			return true;
		}
		
		return false;
	}
	
	public static String getMonth(int month) {
		
		for (Month m : Month.values()) {
			if (m.ordinal() == month-1)
				return m.toString();
		}
		
		return null;
	}
	
	public static int getMonth(String month) {
		
		for (Month m : Month.values()) {
			if (m.toString().equals(month))
				return m.ordinal()+1;
		}
		
		return -1;
	}
	
	public static String getDay(int day) {
		String text = null;
		
		switch(day) {
		case 1: text = String.valueOf(day)+"st";
				break;
		case 2: text = String.valueOf(day)+"nd";
				break;
		default: text = String.valueOf(day)+"th";
				 break;
		}
		
		return text;
	}
	
	public static boolean checkDate(int month, int day, int year) {
		//TODO da fare il controllo della data
		return true;
	}
	
	public static boolean checkDate(String date) {
		String[] arr = date.split("[-|/|.]");
		int year = Integer.valueOf(arr[2]);
		int month = Integer.valueOf(arr[0]);
		int day = Integer.valueOf(arr[1]);
		//TODO da fare il controllo della data
		return true;
	}
}
