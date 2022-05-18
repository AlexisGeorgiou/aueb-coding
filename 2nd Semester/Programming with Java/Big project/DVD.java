public class DVD extends Movie{
	
	private int rentTime;
	private boolean New = false;
	private final String discType = "DVD";
	
	public DVD(boolean New ){
		this.New = New;
		setRentTime();
		
	}
	
	public DVD(String name, String category, String director, String screenWriter, String actors, String production, String proYear, boolean New){
		super(name, category, director, screenWriter, actors, production, proYear);
		setRentTime();
	}
	
	public String getDiscType(){
		return discType;
	}
	public int getRentTime(){
		return rentTime;
	}
	public void setRentTime(){
		if(New == true){
			rentTime = 1;
		}
		else{
			rentTime = 7;
		}
	}
	
	public void setNew(){
		New = true;
	}
}