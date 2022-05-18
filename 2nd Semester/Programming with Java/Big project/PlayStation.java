public class PlayStation extends Game{

	private final String console = "PlayStation";
	
	public PlayStation(){
	}
	
	public PlayStation(String name, String category, String producer, String proYear){
		super(name, category, producer, proYear);
	}
	
	public String getConsole(){
		return console;
	}
}	