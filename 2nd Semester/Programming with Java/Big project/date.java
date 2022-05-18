public class date {
	
	private int date;
	private int month;
	private int year;
	
	public date(int date, int month, int year){
		
		this.date = date;
		this.month = month;
		this.year = year;
	}
	
	public int getDate(){
		return date;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
}