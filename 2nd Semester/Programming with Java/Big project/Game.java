public abstract class Game extends item{
	
	protected int rentTime = 7;
	protected String category;
	protected String producer;
	protected String proYear;
	protected String name;
	
	public Game(){
	}
	
	public Game(String name, String category, String producer, String proYear){
		
		this.name = name;
		this.category = category;
		this.producer = producer;
		this.proYear = proYear;
	}
	
	public String getCategory(){
		return category;
	}
	
	public String getProducer(){
		return producer;
	}
	
	public String getProYear(){
		return proYear;
	}
	
	public abstract String getConsole();
	
	public int getRentTime(){
		return rentTime;
	}
	
	public String getName(){
		return name;
	}
		
	public void incrCopies(){
		availCopies += 1;
	}
	
	public void decrCopies(){
		availCopies -=1;
	}
	
	public int getAvailCopies(){
		return availCopies;
	}
	

	
	public String toString(){
		return "name : " + name +
			   "\ncategory : " + category +
			   "\nproducer : " + producer +
			   "\nrelease year : " + proYear;
	}
	
	public void setCopies(int a){
		totalCopies = a;
	}
	
	public void setCategory(String a){
		category = a;
	}
	
	public void setCompany(String a){
		producer = a;
	}
	
	public void setTitle(String a){
		name = a;
	}
	
	public void setYear(String a){
		proYear = a;
	}
	
}	
