import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl implements StringStack{

	private List list = new List();
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public void push(String item){
			 list.insertAtFront(item);
	}
	public String pop() throws NoSuchElementException{
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
			temp = pop();
			push(temp);
			return temp;
			
	}
	public void printStack(PrintStream stream){
		stream.print("TOP -> ");
		stream.print(list);
		stream.println(" -> BOTTOM");
	}
	public int size(){
		return list.getSizeCounter();
	}
}