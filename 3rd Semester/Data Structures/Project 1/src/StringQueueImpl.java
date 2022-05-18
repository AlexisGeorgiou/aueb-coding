import java.io.PrintStream;
import java.util.NoSuchElementException; 

public class StringQueueImpl implements StringQueue{
	
	private List list = new List();
	public boolean isEmpty(){
		return list.isEmpty();
	}
	public void put(String item){
		list.insertAtBack(item);
	}
	public String get() throws NoSuchElementException{
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return list.removeFromFront();
	}
	public String peek() throws NoSuchElementException{
		String temp;
		if (isEmpty())
			throw new NoSuchElementException();
		else
			temp = get();
			list.insertAtFront(temp);
			return temp;
	}
	public void printQueue(PrintStream stream){
		stream.print("OLDEST -> ");
		stream.print(list);
		stream.println(" -> LATEST");
	}
	public int size(){
		return list.getSizeCounter();
	}
}