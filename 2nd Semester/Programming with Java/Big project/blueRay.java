public class blueRay extends Movie{
	
	private final int rentTime = 1;
	private final String discType = "BlueRay";
	
	public blueRay(){
	}
	
	public blueRay(String name, String category, String director, String screenWriter, String actors, String production, String proYear){
		super(name, category, director, screenWriter, actors, production, proYear);
	}
	
	public int getRentTime(){
		return rentTime;
	}
	public String getDiscType(){
		return discType;
	}
}