public class Xbox extends Game{
	
	private final String console = "Xbox";
	
	public Xbox(){
	}
	
	public Xbox(String name, String category, String producer, String proYear){
		super(name, category, producer, proYear);
	}
	
	public String getConsole(){
		return console;
	}

}	