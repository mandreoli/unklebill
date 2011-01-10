package datatype;

public class Date {

	private int year = 0;
	private int month = 0;
	private int day = 0;
	
	public Date() {
		
	}
	
	public Date(int year, int month, int day) {
		if (checkDate(year, month, day)) {
			this.year = year;
			this.month = month;
			this.day = day;
		}		
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
	
	public String getDate() {
		return String.valueOf(this.year)+"-"+String.valueOf(this.month)+"-"+String.valueOf(this.day);
	}
	
	public boolean setDate(int year, int month, int day) {
		if (checkDate(year, month, day)) {		
			this.year = year;
			this.month = month;
			this.day = day;
			
			return true;
		}
		
		return false;
	}
	
	public static boolean checkDate(int year, int month, int day) {
		//TODO da fare il controllo della data
		return true;
	}
}
