package exercisefive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Galvin on 4/13/2015.
 */
public class AePrime {
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public static void main(String[] args){
        //Arraylist of threads
        ArrayList<Thread> threads = new ArrayList<Thread>();
        //Vector of 100 numbers
        Vector<Integer> numbers = new Vector<Integer>();

        for (int i = 0; i < 100; i++) {
            //Can random the numbers if needed
            numbers.add(i);
        }

        //1 thread takes 10 numbers
        //10 threads will check and print out whether prime or not
        for (int i = 0; i < 10; i++) {
            //+1 to get the range of 10
            Thread t = new CustomThread(numbers.get(i+1));
            t.start();
        }
    }
}

class CustomThread extends Thread {
    int lastNum, startNum;

    int primes55[] = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,
            53,59,61,67,71,73,79,83,89,97,101,103,107,
            109,113,127,131,137,139,149,151,157,163,
            167,173,179, 181,191,193,197,199,211,223,
            227,229,233,239,241,251,257};

    //
    public CustomThread(int num) {
        lastNum = num * 10;
        startNum = lastNum - 10;
    }

    //Take 10 per threading

    public void run() {
        for (int i = startNum ; i < lastNum; i++) {
            if(checkPrime(i)){
                System.out.println("Thread " + (this.currentThread().getId())  + " checked " + i + " is a prime");
            }else {
                System.out.println("Thread " + (this.currentThread().getId()) + " checked " + i + " is not a prime");
            }
        }
    }

    public boolean checkPrime(int numCheck){
        //Returns true if its a prime number, false if its not
        for(int i = 0; i < 55; i++){
            //Added this, as code provided does not check for numbers below 2
            if(numCheck < 2){
                return false;
            }

            //Check against the int array of prime numbers
            if (numCheck % primes55[i] == 0){
                if(numCheck == primes55[i]) {
                    return true;
                }else{
                    return false;
                }
            }
        }

        int maxtest = numCheck / 16;

        for(int i = 259; i < maxtest; i += 2){
            if (numCheck % i == 0){
                return false;
            }
        }
        return true;
    }
}
