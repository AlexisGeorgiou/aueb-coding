


/**
 * Priority Queue interface
 *
 * @param <T>
 */
public interface MaxPQInterface<T> {

    /**
     * Inserts the specified element into this priority queue.
     *
     * @param item
     */
    void insert(T item);


    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    T peek();


    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    T getMax();
	
	/**
	* Returns the number of nodes (minus 1) in the queue
	*
	*@return size of the queue
	*/
	int getSize();
	
}
