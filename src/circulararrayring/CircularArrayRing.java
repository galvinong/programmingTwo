package circulararrayring;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Galvin on 2/26/2015.
 */

public class CircularArrayRing<E> extends AbstractCollection<E> implements Ring<E> {
    private E[] ringArray;
    private Integer numElements = 0, rearPointer = 0;

    //The class should also include a default constructors
    public CircularArrayRing() {
        this(10);
    }

    //and a constructor taking the size of the ring as an argument
    public CircularArrayRing(int size) {
        ringArray = (E[]) new Object[size];
    }

    //implement a void add(E e), adds elements to the array
    @Override
    public boolean add(E e) {
        ringArray[rearPointer] = e;
        rearPointer = (rearPointer + 1) % ringArray.length; //+1 when finishing adding element
        System.out.println("Position " + rearPointer + " added Value |" + ringArray[rearPointer] + "|");
        numElements++;
        return true;
    }

    //Get(0) method gets the last added variables first, get(1) gets the previous item etc
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
//        System.out.println("Get iteratorIndex : " + iteratorIndex + " when rear pointer is at " + rearPointerGet);
        //get position, based on rear pointer, iteratorIndex for which position backwards, -1 because +1 in add method
        int indexPosition = rearPointer - index - 1;

        if (index < 0 || index >= ringArray.length || index >= numElements) {
            //if the iteratorIndex is either larger than the number of items added or larger than the ring size
            throw new IndexOutOfBoundsException();
        }

        //This to continue the ring, to backtrack even when it hits zero
        if (indexPosition < 0) {
            indexPosition = ringArray.length + indexPosition;
        }

        return ringArray[indexPosition];

    }

    @Override
    public Iterator<E> iterator() {
        final Iterator iterator = new Iterator() {
            private int iteratorIndex = 0;

            @Override
            public boolean hasNext() {
                return iteratorIndex < size();
            }

            //iterator should iterate backwards from the most recent element added to the ring
            @Override
            public E next() {
                try {
                    return get(iteratorIndex++);
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }

            //remove should throw an UnsupportedOperationException
            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }
        };
        return iterator;
    }

    //Returns size of number of elements in the array
    @Override
    public int size() {
        if (numElements == ringArray.length) {
            //This is to fix the sync with zero indexing arrays
            return numElements--;
        } else if (numElements > ringArray.length) {
            //If num of elements more than the ring array, just set it to back to the total size
            return numElements = ringArray.length;
        } else {
            return numElements;
        }
    }

}


