public abstract class item{
	
	protected int totalCopies = 3;
	protected int availCopies = 3;
	
	public abstract String getName();
	
	public abstract void incrCopies();
	
	public abstract void decrCopies();
	
	public abstract int getAvailCopies();
	
	public abstract int getRentTime();
}