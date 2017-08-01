package exercisesix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Galvin on 4/19/2015.
 */
public class PrimeApp {

    public static void main(String[] args) {
        //Creates intlist of some length as the first argument
        //Number of threads specified by the second argument
        //java primeapp 300 20

        int firstArg = 0, secondArg = 0;

        //Argument collection
        if (args.length > 0) {
            try {
                firstArg = Integer.parseInt(args[0]);
                secondArg = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer");
            }
        }

        //Creates a list of first argument length
        IntList intList = new IntList();
        for (int i = 1; i <= firstArg; i++) {
            //Random generated integers according to first argument
            intList.add(i);
        }


        //Second argument creates number of threads will check and print out whether prime or not
        for (int i = 0; i < secondArg; i++) {
            Thread t = new CustomThread(intList);
            t.start();
        }
    }
}

class IntList {
    //Use a private array list to implement IntList
    private ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
    boolean ready = false;

    public void add(Integer o) {
        //Adds an integer to the end of the list
        integerArrayList.add(o);
        //Randomise the position whenever integers are added
        Collections.shuffle(integerArrayList);
    }

    public synchronized int get() {
        //Removes and returns the first integer in the list

        //Any thread which calls method get should block if the list is empty
        if (!isEmpty()) {
            ready = true;
            //Store in a temp integer
            int tempInt = integerArrayList.get(0);
            integerArrayList.remove(0);
            return tempInt;
        } else {
            ready = false;
            return 0;
        }
    }

    public boolean isEmpty() {
        //Returns true if the list is empty and false otherwise
        return (integerArrayList.size() == 0);
    }
}

class CustomThread extends Thread {

    IntList integerArrayList = new IntList();
    int timesRan = 1;
    int numToCheck;

    int primes55[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107,
            109, 113, 127, 131, 137, 139, 149, 151, 157, 163,
            167, 173, 179, 181, 191, 193, 197, 199, 211, 223,
            227, 229, 233, 239, 241, 251, 257};

    public CustomThread(IntList integers) {
        integerArrayList = integers;
    }

        public void run() {
            do {
                numToCheck = integerArrayList.get();

                if(numToCheck == 0){
                    return;
                }

                if (checkPrime(numToCheck)) {
                    System.out.println("Thread " + (this.currentThread().getId()) + " checked " + numToCheck + " is a prime, times ran: " + timesRan++);
                } else {
                    System.out.println("Thread " + (this.currentThread().getId()) + " checked " + numToCheck + " is not a prime, times ran: " + timesRan++);
                }
            } while (numToCheck != 0);
        }

    public boolean checkPrime(int numCheck) {
        //Returns true if its a prime number, false if its not
        for (int i = 0; i < 55; i++) {
            //Added this, as code provided does not check for numbers below 2
            if (numCheck < 2) {
                return false;
            }

            //Check against the int array of prime numbers
            if (numCheck % primes55[i] == 0) {
                if (numCheck == primes55[i]) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        int maxtest = numCheck / 16;
        for (int i = 259; i < maxtest; i += 2) {
            if (numCheck % i == 0) {
                return false;
            }
        }
        return true;
    }
}