public class rent{
	
	private date rentDate;
	private static int nextNewCode = 1000;
	private int rentCode;
	private item rentable;
	private String person;
	private double rentCost = 3;
	private double extraDayCost = 5;
	
	public rent(){
	
	}
	
	public rent(item rentable, String person, date rentDate){
		this.rentDate = rentDate;
		this.rentCode = nextNewCode;
		this.rentable = rentable;
		this.person = person;
		
	}
	
	public item getRentable(){
		return rentable;
	}
	
	public String getPerson(){
		return person;
	}
	
	public double getRentCost(){
		return rentCost;
	}
	
	public double getExtraDayCost(){
		return extraDayCost;
	}
	
	public int getRentCode(){
		return rentCode;
	}
	
	public static void incrNextNewCode(){
		nextNewCode += 1;
	}
	
	public date getRentDate(){
		return rentDate;
	}
	
	public String toString(){
		return "\nperson : " + person +
			   "\nCode : " + rentCode +
			   "\nrenting period :" + getRentable().getRentTime() + "days" +
			   "\ncost : " + rentCost;
	}
	
	public void setRentable(item i){
		rentable = i;
	}
	
	public void setName(String i){
		person = i;
	}
	
	public void setDate(int d, int m, int y){
		rentDate = new date(d,m,y);
	}
	
	public void setCode(int a){
		rentCode = a;
	}
	
	
}