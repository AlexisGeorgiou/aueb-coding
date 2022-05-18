



public interface ListInterface {
    /**
     * Inserts the data at the front of the list
     *
     * @param data the inserted data
     */
    void insertAtFront(String data);

    /**
     * Inserts the data at the end of the list
     *
     * @param data the inserted item
     */
    void insertAtBack(String data);

    /**
     * Returns and removes the data from the list head
     *
     * @return the data contained in the list head
     * @throws EmptyListException if the list is empty
     */
    String removeFromFront();

    /**
     * Returns and removes the data from the list tail
     *
     * @return the data contained in the list tail
     * @throws EmptyListException if the list is empty
     */
    String removeFromBack();

    /**
     * Determine whether list is empty
     *
     * @return true if list is empty
     */
    boolean isEmpty();
}
