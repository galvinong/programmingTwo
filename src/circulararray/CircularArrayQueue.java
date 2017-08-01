package circulararray;

/**
 * Created by Galvin on 2/26/2015.
 */

import java.util.NoSuchElementException;

/**
 * @author Galvin
 */
public class CircularArrayQueue implements MyQueue {
    private int[] circArray;
    private int numElements = 0, rearPointer = 0, frontPointer = 0;
    int numTimes = 0;

    public CircularArrayQueue() {
        //Default size is 10, which increases when more are added
        circArray = new int[10];
    }

    public int getCapacityLeft() {
        //How many items can be added before the array needs to be resized
        return circArray.length - numElements;
    }

    //Two pointers to maintain,
    //front will point to the first element, head of the queue and dequeue operation
    //rear will point to last element, and enqueue will happen at rear pointer
    @Override
    public void enqueue(int in) {
        //Relocated the if statement for testResize, because it was complaining i was resizing too early
        //think it is unfair because it would be the same applying it here or at the end of the method
        //if  Number of elements increase in size, create new array, and copy over~

        if (numElements == circArray.length) {
            int[] circArrayCopy = new int[circArray.length * 2];

            for (int i = 0; i < circArray.length; i++) {
                circArrayCopy[i] = circArray[i];
            }

            //Change reference from circArrayCopy to circArray
            circArray = circArrayCopy;
            //This reverts the pointer to continue where it left from, because the array should not start from 0 again
            rearPointer = circArray.length / 2;
        }

        //System.out.println("******Number of times : " + numTimes + "*********");

        //The code to enqueue the number onto the array
        //System.out.print("Enqueuing Position " + rearPointer + " with element " + in);
        circArray[rearPointer] = in;

        //Increases the pointer, was using rearPointer++ however, this causes problems with higher numbers
        //by using modulus by array's length, this never increases beyond the array length, leaving you just the remainder
        rearPointer = (rearPointer + 1) % circArray.length;
        numElements++;
        numTimes++;

        System.out.print(", Tail Pointer After : " + rearPointer);
        System.out.println(", Num of Elements: " + numElements);

    }

    @Override
    public int dequeue() {
        //use modulus, eg (1 + 10) % 10 = 1;
        int headElement = circArray[((circArray.length + frontPointer) % circArray.length)];
        numElements--;

        if (numElements < 0) {
            System.out.println("Unable to dequeue when array is empty!");
            numElements = 0;
            throw new NoSuchElementException();
        }

        System.out.print("Dequeueing Position " + frontPointer + " containing Element : " + headElement);
        System.out.println(" Num of Elements left: " + numElements + "\n");

        frontPointer = (frontPointer + 1) % circArray.length;

        return headElement;
    }

    @Override
    public int noItems() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

}
