public abstract class Movie extends item{
	
	protected String category;
	protected String director;
	protected String screenWriter;
	protected String actors;
	protected String production;
	protected String proYear;
	protected String name;
	
	public Movie(){
	}
	
	public Movie(String name, String category, String director, String screenWriter, String actors, String production, String proYear){
		
		this.name = name;
		this.category = category;
		this.director = director;
		this.screenWriter = screenWriter;
		this.actors = actors;
		this.production = production;
		this.proYear = proYear;
	}
	
	public String getCategory(){
		return category;
	}
	
	public String getDirector(){
		return director;
	}
	
	public String getScreenWriter(){
		return screenWriter;
	}
	
	public String getActors(){
		return actors;
	}
	
	public String getProduction(){
		return production;
	}
	
	public String getProYear(){
		return proYear;
	}
	
	public abstract String getDiscType();
	
	public abstract int getRentTime();
	
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
		return "\nname : " + name +
			   "\ncategory : " + category +
			   "\ndirector : " + director +
			   "\nscreen writer : " + screenWriter +
			   "\nactors : " + actors +
			   "\nproduction : " + production +
			   "\nrelease year : " + proYear;
	}
	
	public void setTitle(String a){
		name = a;
	}
	
	public void setYear(String a){
		proYear = a;
	}
	
	public void setCast(String a){
		actors = a;
	}

	public void setDirector(String a){
		director = a;
	}
	
	public void setCategory(String a){
		category = a;
	}
	
	public void setScreenWriter(String a){
		screenWriter = a;
	}
	
	public void setProduction(String a){
		production = a;
	}
	
	public void setCopies(int a){
		totalCopies = a;
	}
}