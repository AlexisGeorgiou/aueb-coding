import java.util.NoSuchElementException;

class CircularList {
	private Node head; 
	private int size_counter = 0;
	
	
	
	
	public boolean isEmpty(){return head == null;}
	public void insert(String data){
		Node n = new Node(data);
		
		Node iterator = null;
		if (isEmpty()){
			head = n;
			head.setNext(n);
		}	
		
		else {	
			n.setNext(head.getNext());
			head.setNext(n);
			head = n;
			
		}
		size_counter++;
		
	}
	
	
	
	public String remove() {
		
		String data;

		
		if (isEmpty())
			throw new NoSuchElementException();
		else{
			Node n = head.getNext();
			head.setNext(head.getNext().getNext());
			size_counter--;
			return n.getData();
			
		}
		
		
		
		
	}
	
public int getSizeCounter(){return size_counter;}
	
public Node getHead(){return head;}

public String toString() {
        if (isEmpty()) {
            return "List is empty :(";
        }

        Node current = head.getNext();
		
        StringBuilder ret = new StringBuilder();

        // while not at end of list, output current node's data
		ret.append(current.data.toString());
		
		if (current.getNext() != head.getNext())
                ret.append(" -> ");
		current = current.next;

        while (current != head.getNext()) {
            ret.append(current.data.toString());

            if (current.getNext() != head.getNext())
                ret.append(" -> ");

            current = current.next;
        }


        return ret.toString();
    }
}