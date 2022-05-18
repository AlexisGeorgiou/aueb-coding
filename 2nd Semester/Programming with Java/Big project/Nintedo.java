public class Nintedo extends Game{
	
	private final String console = "Nintedo";
	
	public Nintedo(){
	}
	
	public Nintedo(String name, String category, String producer, String proYear){
		super(name, category, producer, proYear);
	}
	
	public String getConsole(){
		return console;
	}	

}