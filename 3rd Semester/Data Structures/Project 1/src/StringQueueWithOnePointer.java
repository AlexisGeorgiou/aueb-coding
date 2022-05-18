import java.io.PrintStream;
import java.util.NoSuchElementException;


public class StringQueueWithOnePointer implements StringQueue{
	private CircularList clist = new CircularList();
	public boolean isEmpty(){
		return clist.isEmpty();
	}
	
	public void put(String item){
		clist.insert(item);
	}
	
	public String get() throws NoSuchElementException{
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return clist.remove();
		
	}

	public String peek() throws NoSuchElementException{
		String temp;
		if (isEmpty())
			throw new NoSuchElementException();
		else{
			return clist.getHead().getNext().getData();
		}
	}
	
	public void printQueue(PrintStream stream){
		stream.print("OLDEST -> ");
		stream.print(clist);
		stream.println(" -> LATEST");
	}
	
	public int size(){
		return clist.getSizeCounter();
	}
}