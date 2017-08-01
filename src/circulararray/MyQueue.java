package circulararray;

import java.util.NoSuchElementException;

/**
 * Created by Galvin on 2/26/2015.
 */
public interface MyQueue {
    void enqueue(int in);

    int dequeue() throws NoSuchElementException;  // throw exception if isEmpty() is true

    int noItems(); // returns the number of items in the array

    boolean isEmpty();  // true if queue is empty
}
