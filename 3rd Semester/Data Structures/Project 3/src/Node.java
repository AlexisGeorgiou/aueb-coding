

/**
 * ListNode represents a list node
 * Each node contains a generic type T as data and a reference to the next node in the list.
 */
public class Node<T> {
    protected T data;
    protected Node<T> next = null;

    /**
     * Constructor. Sets data
     *
     * @param data the data stored
     * @return
     */
    Node(T data) {
        this.data = data;
    }

    /**
     * Returns this node's data
     *
     * @return the reference to node's data
     */
    public T getData() {
        // return data stored in this node
        return data;
    }

    /**
     * Get reference to next node
     *
     * @return the next node
     */
    public Node<T> getNext() {
        // get next node
        return next;
    }

    /**
     * Set reference to next node
     *
     * @param next reference
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
